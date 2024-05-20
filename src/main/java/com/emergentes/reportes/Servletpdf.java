/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emergentes.reportes;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.emergentes.dao.pacienteDAO;
import com.emergentes.dao.pacienteDAOimpl;
import com.emergentes.modelos.DetalleConsulta;
import com.emergentes.modelos.Paciente;
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
import com.sun.org.apache.xml.internal.serializer.ElemDesc;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ALVARO
 */
@WebServlet(name = "Servletpdf", urlPatterns = {"/Servletpdf"})
public class Servletpdf extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

          response.setContentType("application/pdf");
        OutputStream out = response.getOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();
            
            
                // Agregar título al documento
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("CONSULTORIO MEDICO HUAYNA POTOSI", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            
              // Ajustar el margen izquierdo para mover el título hacia la derecha
            title.setIndentationLeft(200); // Ajustar este valor según sea necesario
            
                    
            document.add(title);

           
            

            // Agregar logo en la esquina superior izquierda
            Image logo = Image.getInstance(getServletContext().getRealPath("/imagenes/logo.png"));

            logo.scaleToFit(100, 100); // Ajusta el tamaño del logo
            logo.setAlignment(Image.LEFT | Image.TEXTWRAP);
            document.add(logo);

            // Agregar espacio en blanco
            document.add(new Paragraph(" "));

            // Obtener el ID del paciente de los parámetros de la solicitud
            int pacienteId = Integer.parseInt(request.getParameter("id"));

            // Obtener los datos del paciente de la base de datos
            pacienteDAO pacienteDAO = new pacienteDAOimpl();
            Paciente paciente = pacienteDAO.getById(pacienteId);

            // Verificar si se encontró el paciente
            if (paciente != null) {
                // Crear un PdfPTable para organizar los datos del paciente
                PdfPTable table = new PdfPTable(2); // 2 columnas para los pares atributo-valor
                table.setWidthPercentage(100); // Ancho de la tabla al 100% del documento

                // Agregar los datos del paciente al documento con estilo
                Font dataFont = new Font(Font.FontFamily.HELVETICA, 12);
document.add(new Paragraph(" "));
document.add(new Paragraph(" "));
                // Agregar celdas a la tabla para cada atributo del paciente
                //addCellToTable(table, "ID:", Integer.toString(paciente.getId()), dataFont);
                addCellToTable(table, "Nombre:", paciente.getFirstname(), dataFont);
                addCellToTable(table, "Apellido:", paciente.getLastname(), dataFont);
                addCellToTable(table, "DNI:", paciente.getDni(), dataFont);
                addCellToTable(table, "Número de Historia Clínica:", paciente.getNumberClinicalHistory(), dataFont);

                // Agregar la tabla al documento
                document.add(table);
                
                  document.add(new Paragraph(" "));
                  
                  // Agregar título para la tabla de detalles de consulta
            Paragraph title1 = new Paragraph("Detalles de Consulta Médica", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD));
            document.add(title1);
            document.add(new Paragraph(" "));

            // Obtener la lista de detalles de consulta médica del paciente
             pacienteDAO detalleConsultaDAO = new pacienteDAOimpl();
            List<DetalleConsulta> detallesConsulta = detalleConsultaDAO.getAnte(pacienteId);

            if (!detallesConsulta.isEmpty()) {
                // Crear un PdfPTable para organizar los detalles de la consulta médica
                PdfPTable tableConsulta = new PdfPTable(4); // 4 columnas para los datos de la consulta
                tableConsulta.setWidthPercentage(100); // Ancho de la tabla al 100% del documento

              // Agregar encabezados a la tabla de detalles de consulta
                 //   addHeaderCellToTable(tableConsulta, "ID", dataFont);
                    addHeaderCellToTable(tableConsulta, "Especialidad", dataFont);
                    addHeaderCellToTable(tableConsulta, "Diagnóstico", dataFont);
                    addHeaderCellToTable(tableConsulta, "Tratamiento", dataFont);
                    addHeaderCellToTable(tableConsulta, "Fecha de Creación", dataFont);


                    // Agregar filas a la tabla de detalles de consulta
                    for (DetalleConsulta consulta : detallesConsulta) {
                       // tableConsulta.addCell(new PdfPCell(new Phrase(Integer.toString(consulta.getId()), dataFont)));
                        tableConsulta.addCell(new PdfPCell(new Phrase(consulta.getIdMedCon(), dataFont)));
                        tableConsulta.addCell(new PdfPCell(new Phrase(consulta.getDiagnostic(), dataFont)));
                        tableConsulta.addCell(new PdfPCell(new Phrase(consulta.getTreatment(), dataFont)));
                        tableConsulta.addCell(new PdfPCell(new Phrase(consulta.getDate(), dataFont)));
                    }


                // Agregar la tabla de detalles de consulta al documento
                document.add(tableConsulta);
            } else {
                document.add(new Paragraph("No hay detalles de consulta médica disponibles para este paciente."));
            }
        } else {
            document.add(new Paragraph("No se encontró ningún paciente con el ID especificado."));
        }
            document.close();
        } catch (DocumentException ex) {
            throw new IOException(ex.getMessage());
        } finally {
            out.close();
        }
    }
    
    // Método para agregar una celda de encabezado a una tabla PdfPTable
    private void addHeaderCellToTable(PdfPTable table, String label, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(label, font));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); // Centramos el texto del encabezado
        table.addCell(cell);
    }

    
    
     // Método para agregar una celda a una tabla PdfPTable
    private void addCellToTable(PdfPTable table, String label, String value, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(label, font));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(value, font));
        table.addCell(cell);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Servletpdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Servletpdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Genera un reporte en PDF de la tabla de pacientes";
    }
}
