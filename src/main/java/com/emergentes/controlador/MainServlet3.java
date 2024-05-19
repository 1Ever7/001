package com.emergentes.controlador;

import static com.emergentes.conDB.ConexionDB.getConnection;
import com.emergentes.dao.consultaDAOimpl;
import com.emergentes.modelos.Consulta;
import com.emergentes.modelos.Especialidad;
import com.emergentes.modelos.MedicalConsulta;
import com.emergentes.modelos.Paciente;
import com.google.protobuf.TextFormat.ParseException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.*;


@WebServlet(name = "MainServlet3", urlPatterns = {"/MainServlet3"})
public class MainServlet3 extends HttpServlet {
  

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
             consultaDAOimpl dao = new consultaDAOimpl();
             
             List<Object> result = dao.getAll1();
        ArrayList<Consulta> detalle = (ArrayList<Consulta>) result.get(0);
        ArrayList<String> especi = (ArrayList<String>) result.get(1);
        
        // Establecer los atributos en el request
       // request.setAttribute("doctores", doctores);
        request.setAttribute("especi", especi);
           // ArrayList<Consulta> detalles = (ArrayList<Consulta>) dao.getAll();
            
            request.setAttribute("detalles", detalle);
            request.getRequestDispatcher("tablas/consulta.jsp").forward(request, response);
        }catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al conectar con la base de datos");
        } 
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
               
            String action = request.getParameter("action");
            consultaDAOimpl dao =new consultaDAOimpl();
            if (action != null) {
                switch (action) {
                    case "mostrarEs":
                        mostrarEs(request,response,dao);
                        break;
                    case "insertar":
                        insertarConsulta(request, dao);
                        break;
                    case "ver":
                        mostrarConsulta(request, response , dao);
                        break;
                    case "actualizar":
                        actualizarConsulta(request,dao);
                        break;
                    case "eliminar":
                        eliminarConsulta(request,dao);
                        break;
                    case "select":
                        mostrarDetallesConsulta(request, response,dao);
                        break;
                    case "buscar":
                        busqueda(request,response, dao);
                        
                        break;
                    default:
                        break;
                }
            }

            response.sendRedirect("MainServlet3");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(MainServlet3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

 private void mostrarConsulta(HttpServletRequest request, HttpServletResponse response,consultaDAOimpl dao)
            throws IOException, ServletException, ClassNotFoundException, SQLException, Exception {
     try {
        int id = Integer.parseInt(request.getParameter("id"));
        int medicalConsultaId = Integer.parseInt(request.getParameter("medicalConsultaId"));
        int detalleConsultaId = Integer.parseInt(request.getParameter("detalleConsultaId"));
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
         
//consultaDAOimpl dao = new consultaDAOimpl();
        Consulta detalle = dao.getBynomape(id,medicalConsultaId,detalleConsultaId,firstName, lastName);
        
         ArrayList<String> especialidad = dao.obtenerEspecialidades();
        request.setAttribute("especialidades", especialidad);
        
        request.setAttribute("detalles", detalle);
        request.getRequestDispatcher("CRUDcon/EDITAR.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error al mostrar el formulario de agregar doctor");
        }
    }

   

    private void mostrarDetallesConsulta(HttpServletRequest request, HttpServletResponse response , consultaDAOimpl dao)
            throws ServletException, IOException {
         try {
        int id = Integer.parseInt(request.getParameter("id"));
        int medicalConsultaId = Integer.parseInt(request.getParameter("medicalConsultaId"));
        int detalleConsultaId = Integer.parseInt(request.getParameter("detalleConsultaId"));
        String nombrePaciente = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
         
         
        //consultaDAOimpl dao = new consultaDAOimpl();

//sk-proj-q2KIwqzAbqHWX3xXPihjT3BlbkFJeHxhLoV0XlQwjGUo4z4f  

        Consulta detalle = dao.getBynomape(id,medicalConsultaId,detalleConsultaId,nombrePaciente, lastName);
        request.setAttribute("detalles", detalle);
        request.getRequestDispatcher("CRUDcon/ELIMINAR.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error al mostrar el formulario de agregar doctor");
        }
    }

    
    
    
    
    private void mostrarEs(HttpServletRequest request, HttpServletResponse response , consultaDAOimpl dao) 
            throws ServletException , IOException{
         try {
            
        int id = Integer.parseInt(request.getParameter("id"));
        int medicalConsultaId = Integer.parseInt(request.getParameter("medicalConsultaId"));
        int detalleConsultaId = Integer.parseInt(request.getParameter("detalleConsultaId"));
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
         
//consultaDAOimpl dao = new consultaDAOimpl();
        Consulta detalles = dao.getBynomape(id,medicalConsultaId,detalleConsultaId,firstName, lastName);
       
        ArrayList<String> especialidad = dao.obtenerEspecialidades();
        request.setAttribute("especialidades", especialidad);
        
        
        request.setAttribute("detalles", detalles);
        request.getRequestDispatcher("CRUDcon/AÑADIR.jsp").forward(request, response);
    } catch (Exception e) {
        e.printStackTrace();
        throw new ServletException("Error al mostrar el formulario de agregar doctor");
    }
        
        
    }
    private void insertarConsulta(HttpServletRequest request,consultaDAOimpl dao) throws ClassNotFoundException, SQLException, Exception {
        try {
        String nombreEspecialidad = request.getParameter("nombre_especialidad");
        //int idEspecilaidad = dao.obtenerIdEspecialidad(nombreEspecialidad);
        int idDoc = dao.obtenerIdDoctorPorEspecialidad(nombreEspecialidad);

        if (idDoc != -1) {
               
       String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        Date createDate = Date.valueOf(request.getParameter("createdate"));
        String diagnostic = request.getParameter("diagnostic");
        String treatment = request.getParameter("treatment");
        String celular = request.getParameter("celular");
        String gptcon = request.getParameter("analisis_medico");
       
        
        
        Consulta detalles = new Consulta();
        
        //detalles.setId(id);
        detalles.setNombrePaciente(firstName);
        detalles.setApellidoPaciente(lastName);
        detalles.setFechaConsulta(createDate);
        detalles.setDiagnostico(diagnostic);
        detalles.setTratamiento(treatment);
        detalles.setCel(celular);
        detalles.setGptcon(gptcon);
        detalles.setIdoc(idDoc);
        dao.insert(detalles);
            
            
        } else {
            throw new ServletException("La especialidad especificada no existe");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new ServletException("Error al insertar el doctor");
    }
        
    }

   private void actualizarConsulta(HttpServletRequest request, consultaDAOimpl dao)
        throws ClassNotFoundException, SQLException, ServletException, IOException {
        try {
          String nombreEspecialidad = request.getParameter("nombre_especialidad");
        //int idEspecilaidad = dao.obtenerIdEspecialidad(nombreEspecialidad);
        int idDoc = dao.obtenerIdDoctorPorEspecialidad(nombreEspecialidad);

        if (idDoc != -1) {
            
            int id = Integer.parseInt(request.getParameter("id"));
            int medicalConsultaId = Integer.parseInt(request.getParameter("medicalConsultaId"));
            int detalleConsultaId = Integer.parseInt(request.getParameter("detalleConsultaId"));
       String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        Date createDate = Date.valueOf(request.getParameter("createdate"));
        String diagnostic = request.getParameter("diagnostic");
        String treatment = request.getParameter("treatment");
        String celular = request.getParameter("celular");
        String gptcon = request.getParameter("analisis_medico");
         
        

        Consulta detalles = new Consulta();
        detalles.setId(id);
        detalles.setNombrePaciente(firstName);
        detalles.setApellidoPaciente(lastName);
        detalles.setFechaConsulta(createDate);
        detalles.setDiagnostico(diagnostic);
        detalles.setTratamiento(treatment);
        detalles.setCel(celular);
        detalles.setGptcon(gptcon);
        detalles.setMedicalConsultaId(medicalConsultaId);
        detalles.setDetalleConsultaId(detalleConsultaId);
        detalles.setIdoc(idDoc);
        
            dao.update(detalles);
              } else {
            throw new ServletException("La especialidad especificada no existe");
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void eliminarConsulta(HttpServletRequest request, consultaDAOimpl dao)
            throws ClassNotFoundException, SQLException, Exception {
        try {
        int medicalConsultaId = Integer.parseInt(request.getParameter("medicalConsultaId"));
        int detalleConsultaId = Integer.parseInt(request.getParameter("detalleConsultaId"));
        dao.delete(medicalConsultaId,detalleConsultaId);
    } catch (SQLException e) {
        e.printStackTrace();
        throw new ServletException("Error al eliminar el paciente");
    }
    }


    
    
    private void busqueda(HttpServletRequest request, HttpServletResponse response, consultaDAOimpl dao) 
        throws ServletException, IOException {
    try {
    // Obtener la fecha de la solicitud
    String fechaStr = request.getParameter("fecha");

    // Convertir la cadena de fecha a un objeto Date
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date fecha = sdf.parse(fechaStr);

    // Convertir la fecha java.util.Date a java.sql.Date
    java.sql.Date sqlFecha = new java.sql.Date(fecha.getTime());

    // Obtener los detalles de la consulta por fecha utilizando el DAO
    List<Consulta> consultas = dao.getByDate(sqlFecha);

    // Establecer los detalles en el request para pasarlos a la página JSP
    request.setAttribute("detalles", consultas);

    // Redirigir a la página JSP para mostrar los resultados
    RequestDispatcher dispatcher = request.getRequestDispatcher("tablas/consulta.jsp");
    dispatcher.forward(request, response);
} catch (ParseException e) {
    // Manejar errores de conversión de fecha
    e.printStackTrace();
    // Puedes redirigir a una página de error o mostrar un mensaje de error en la misma página
} catch (Exception e) {
    // Manejar otros errores
    e.printStackTrace();
    // Puedes redirigir a una página de error o mostrar un mensaje de error en la misma página
}
    
    
    }
}
