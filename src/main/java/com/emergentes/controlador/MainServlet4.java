package com.emergentes.controlador;

import com.emergentes.conDB.ConexionDB;
import com.emergentes.dao.especieDAOimpl;
import com.emergentes.modelos.Especialidad;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MainServlet4", urlPatterns = {"/MainServlet4"})
public class MainServlet4 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Connection conn = ConexionDB.getConnection();
            especieDAOimpl dao = new especieDAOimpl();
            ArrayList<Especialidad> especies = (ArrayList<Especialidad>) dao.getAll();
            request.setAttribute("especies", especies);
            request.getRequestDispatcher("tablas/especialidades.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al conectar con la base de datos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          try {
        String action = request.getParameter("action");
        especieDAOimpl dao = new especieDAOimpl();
        if (action != null) {
            switch (action) {
                case "insertar":
                    insertarEspecialidad(request, dao);
                    break;
                case "ver":
                    // Lógica para ver
                    verEspecialidad(request, response, dao);
                    break;
                case "actualizar":
                    actualizarEspecialidad(request, dao);
                    break;
                case "eliminar":
                    eliminarEspecialidad(request, dao);
                    break;
                case "select":
                    seleccionarEspecialidad(request, response, dao);
                    break;
                default:
                    break;
            }

        }
        
        response.sendRedirect("MainServlet4");
         } catch (Exception ex) {
            Logger.getLogger(MainServlet3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private void insertarEspecialidad(HttpServletRequest request, especieDAOimpl dao) 
             throws ServletException, IOException {
        try {
            String nombre = request.getParameter("nombre");
            Especialidad especie = new Especialidad();
            especie.setNombre(nombre);
            dao.insert(especie);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error al insertar la especialidad");
        }
    }
    private void verEspecialidad(HttpServletRequest request, HttpServletResponse response, especieDAOimpl dao) 
            throws ServletException, IOException {
    try {
        int id = Integer.parseInt(request.getParameter("id"));
        Especialidad especialidad = dao.getById(id);
        request.setAttribute("especialidad", especialidad);
        request.getRequestDispatcher("CRUDespe/EDITAR.jsp").forward(request, response);
    } catch (Exception e) {
        e.printStackTrace();
        throw new ServletException("Error al ver la especialidad");
    }
}

private void actualizarEspecialidad(HttpServletRequest request, especieDAOimpl dao) throws ServletException, IOException {
    try {
        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        Especialidad especialidad = new Especialidad();
        especialidad.setId(id);
        especialidad.setNombre(nombre);
        dao.update(especialidad);
    } catch (Exception e) {
        e.printStackTrace();
        throw new ServletException("Error al actualizar la especialidad");
    }
}
private void eliminarEspecialidad(HttpServletRequest request, especieDAOimpl dao) throws ServletException, IOException {
    try {
        int id = Integer.parseInt(request.getParameter("id"));
        dao.delete(id);
    } catch (Exception e) {
        e.printStackTrace();
        throw new ServletException("Error al eliminar la especialidad");
    }
}
private void seleccionarEspecialidad(HttpServletRequest request, HttpServletResponse response, especieDAOimpl dao) throws ServletException, IOException {
    try {
        int id = Integer.parseInt(request.getParameter("id"));
        Especialidad especialidad = dao.getById(id);
        request.setAttribute("especialidad", especialidad);
        request.getRequestDispatcher("CRUDespe/ELIMINAR.jsp").forward(request, response);
    } catch (Exception e) {
        e.printStackTrace();
        throw new ServletException("Error al seleccionar la especialidad");
    }
}   


}
