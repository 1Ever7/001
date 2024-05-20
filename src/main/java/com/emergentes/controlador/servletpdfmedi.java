/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emergentes.controlador;

import com.emergentes.dao.consultaDAO;
import com.emergentes.dao.consultaDAOimpl;
import com.emergentes.modelos.Consulta;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
@WebServlet(name = "servletpdfmedi", urlPatterns = {"/servletpdfmedi"})
public class servletpdfmedi extends HttpServlet {

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
            throws ServletException, IOException, DocumentException, Exception {

        response.setContentType("application/pdf");
        OutputStream out = response.getOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            // Agregar logo en la esquina superior izquierda
            Image logo = Image.getInstance(getServletContext().getRealPath("/imagenes/logo.png"));
            logo.scaleToFit(100, 100); // Ajusta el tamaño del logo
            logo.setAlignment(Image.LEFT | Image.TEXTWRAP);
            document.add(logo);

// Agregar título centrado al documento
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("CONSULTORIO MEDICO HUAYNA POTOSI", titleFont);
            Paragraph title1 = new Paragraph("REPORTES ATENCION MEDICA", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            title.setSpacingBefore(20);
            title.setSpacingAfter(20);
            document.add(title);
            document.add(title1);
            
             document.add(new Paragraph(" "));

            consultaDAOimpl dao = new consultaDAOimpl();

            List<Object> result = dao.getAll1();
            ArrayList<Consulta> detalles = (ArrayList<Consulta>) result.get(0);

            // Verificar si se encontraron detalles de consulta
            if (detalles != null && !detalles.isEmpty()) {
                // Crear una tabla para los detalles de consulta
                PdfPTable consultaTable = new PdfPTable(6); // 6 columnas para los detalles de consulta
                consultaTable.setWidthPercentage(100);

                // Agregar encabezados a la tabla de detalles de consulta
                Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                addHeaderCellToTable(consultaTable, "Fecha de Consulta", headerFont);
                addHeaderCellToTable(consultaTable, "Nombre Completo", headerFont);
                addHeaderCellToTable(consultaTable, "Diagnóstico", headerFont);
                addHeaderCellToTable(consultaTable, "Tratamiento", headerFont);
                addHeaderCellToTable(consultaTable, "Celular", headerFont);
                addHeaderCellToTable(consultaTable, "Consulta IA", headerFont);

                // Agregar los detalles de consulta a la tabla
                Font dataFont = new Font(Font.FontFamily.HELVETICA, 12);
                SimpleDateFormat outputSdf = new SimpleDateFormat("dd/MM/yyyy"); // Formato de salida de la fecha
                for (Consulta detalle : detalles) {
                    addCellToTable(consultaTable, outputSdf.format(detalle.getFechaConsulta()), dataFont);
                    addCellToTable(consultaTable, detalle.getNombrePaciente() + " " + detalle.getApellidoPaciente(), dataFont);
                    addCellToTable(consultaTable, detalle.getDiagnostico(), dataFont);
                    addCellToTable(consultaTable, detalle.getTratamiento(), dataFont);
                    addCellToTable(consultaTable, detalle.getCel(), dataFont);
                    addCellToTable(consultaTable, detalle.getGptcon(), dataFont);
                }

                // Agregar la tabla de detalles de consulta al documento
                document.add(consultaTable);
            } else {
                document.add(new Paragraph("No se encontraron detalles de consulta en la base de datos."));
            }

            document.close();
        } catch (DocumentException ex) {
            throw new IOException(ex.getMessage());
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Genera un reporte en PDF de todos los detalles de consulta";
    }

    private void addHeaderCellToTable(PdfPTable table, String header, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(header, font));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.addCell(cell);
    }

    private void addCellToTable(PdfPTable table, String value, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(value, font));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.addCell(cell);
    }
}
