/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emergentes.controlador;

import com.emergentes.dao.consultaDAO;
import com.emergentes.dao.consultaDAOimpl;
import com.emergentes.modelos.contarconsultas;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ALVARO
 */
@WebServlet(name = "servletconmed", urlPatterns = {"/servletconmed"})
public class servletconmed extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("application/pdf");
        OutputStream out = response.getOutputStream();

        try {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, out);

            document.open();
            
            
            
             // Agregar logo en la esquina superior izquierda
            Image logo = Image.getInstance(getServletContext().getRealPath("/imagenes/logo.png"));
            logo.scaleToFit(100, 100); // Ajusta el tamaño del logo
            logo.setAlignment(Image.LEFT | Image.TEXTWRAP);
            document.add(logo);
            
            // Agregar título centrado al documento
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("CONSULTAS MEDICAS HUAYNA POTOSI", titleFont);
            
            title.setAlignment(Paragraph.ALIGN_CENTER);
            title.setSpacingBefore(20);
            title.setSpacingAfter(20);
            document.add(title);
            
            
            document.add(new Paragraph(" "));
// Obtener los datos del DAO
            consultaDAO dao = new consultaDAOimpl();
            List<contarconsultas> reportes = dao.getReporteConsultas();

// Crear una lista para almacenar las fechas únicas
            List<Date> fechasUnicas = new ArrayList<>();

// Extraer fechas únicas de la lista de reportes
            for (contarconsultas reporte : reportes) {
                Date fechaConsulta = reporte.getFecha();
                if (!fechasUnicas.contains(fechaConsulta)) {
                    fechasUnicas.add(fechaConsulta);
                }
            }

// Ordenar las fechas únicas
            Collections.sort(fechasUnicas);

// Crear una tabla para cada fecha única
            for (Date fecha : fechasUnicas) {
                PdfPTable table = new PdfPTable(3);
                table.setWidthPercentage(100);

                // Añadir encabezados
                Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                addCellToTable(table, "Especialidad", headerFont);
                addCellToTable(table, "Fecha de Consulta", headerFont);
                addCellToTable(table, "Total Consultas", headerFont);

                // Añadir datos al reporte para la fecha actual
                for (contarconsultas reporte : reportes) {
                    if (reporte.getFecha().equals(fecha)) {
                        Font dataFont = new Font(Font.FontFamily.HELVETICA, 10);
                        addCellToTable(table, reporte.getEspecie(), dataFont);
                        addCellToTable(table, reporte.getFecha().toString(), dataFont);
                        addCellToTable(table, String.valueOf(reporte.getTotal()), dataFont);
                    }
                }

                // Agregar la tabla al documento
                document.add(new Paragraph("\n")); // Espacio entre tablas
                document.add(table);
            }
            document.close();
        } catch (DocumentException ex) {
            throw new IOException(ex.getMessage());
        } finally {
            out.close();
        }
    }

    private void addCellToTable(PdfPTable table, String value, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(value, font));
        table.addCell(cell);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(servletconmed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(servletconmed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Genera un reporte en PDF del total de consultas por especialidad y fecha";
    }
}
