package com.emergentes.controlador;

import com.emergentes.dao.docDAO;
import com.emergentes.dao.docDAOimpl;
import com.emergentes.modelos.Doctor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "servletpdfdoc", urlPatterns = {"/servletpdfdoc"})
public class servletpdfdoc extends HttpServlet {

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


            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Lista de Médicos", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            title.setSpacingBefore(20);
            title.setSpacingAfter(20);
            
            document.add(title);

            document.add(new Paragraph(" "));
            
            docDAO doctorDAO = new docDAOimpl();
            List<Object> result = doctorDAO.getAll();
            ArrayList<Doctor> doctores = (ArrayList<Doctor>) result.get(0);
            ArrayList<String> especia = (ArrayList<String>) result.get(1);

            // Establecer los atributos en el request
            request.setAttribute("doctores", doctores);
            request.setAttribute("especia", especia);

            if (doctores != null && !doctores.isEmpty()) {
                PdfPTable table = new PdfPTable(6); // 6 columnas para los detalles de médicos
                table.setWidthPercentage(100);

                Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                addHeaderCellToTable(table, "ID", headerFont);
                addHeaderCellToTable(table, "Especialidad", headerFont);
                addHeaderCellToTable(table, "Nombre", headerFont);
                addHeaderCellToTable(table, "Apellido", headerFont);
                addHeaderCellToTable(table, "DNI", headerFont);
                addHeaderCellToTable(table, "Código", headerFont);

                Font dataFont = new Font(Font.FontFamily.HELVETICA, 12);
                for (Doctor doctor : doctores) {
                    addCellToTable(table, String.valueOf(doctor.getId()), dataFont);
                    addCellToTable(table, doctor.getNombreEspecialidad(), dataFont);
                    addCellToTable(table, doctor.getFirstname(), dataFont);
                    addCellToTable(table, doctor.getLastname(), dataFont);
                    addCellToTable(table, doctor.getDni(), dataFont);
                    addCellToTable(table, doctor.getCodi(), dataFont);
                }

                document.add(table);
            } else {
                document.add(new Paragraph("No se encontraron médicos en la base de datos."));
            }

            document.close();
        } catch (DocumentException | IOException ex) {
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
        } catch (DocumentException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(servletpdfdoc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (DocumentException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(servletpdfdoc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Genera un reporte en PDF de la lista de médicos";
    }

    private void addHeaderCellToTable(PdfPTable table, String header, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(header, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
    }

    private void addCellToTable(PdfPTable table, String value, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(value, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
    }
}
