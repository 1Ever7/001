package com.emergentes.controlador;

import com.emergentes.dao.pacienteDAOimpl;
import com.emergentes.modelos.Paciente;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MainServlet2", urlPatterns = {"/MainServlet2"})
public class MainServlet2 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            pacienteDAOimpl dao = new pacienteDAOimpl();
            ArrayList<Paciente> pacientes = (ArrayList<Paciente>) dao.getAll();
            request.setAttribute("pacientes", pacientes);
            request.getRequestDispatcher("tablas/paciente.jsp").forward(request, response);
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
            pacienteDAOimpl dao = new pacienteDAOimpl();
            if (action != null) {
                switch (action) {
                    case "insertar":
                        insertarPaciente(request,dao);
                        break;
                    case "ver":
                        mostrarPaciente(request, response, dao);
                        break;
                    case "actualizar":
                        actualizarPaciente(request,dao);
                        break;
                    case "eliminar":
                        eliminarPaciente(request,dao);
                        break;
                    case "select":
                        seleccionarPaciente(request, response, dao);
                        return;
                    default:
                        break;
                }
            }

            response.sendRedirect("MainServlet2");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void seleccionarPaciente(HttpServletRequest request, HttpServletResponse response, pacienteDAOimpl dao)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Paciente pacientes = dao.getById(id);
            request.setAttribute("pacientes", pacientes);
            request.getRequestDispatcher("CRUDpac/ELIMINAR.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error al seleccionar el doctor");
        }
    }

    private void mostrarPaciente(HttpServletRequest request, HttpServletResponse response, pacienteDAOimpl dao)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Paciente pacientes = dao.getById(id);
            request.setAttribute("pacientes", pacientes);
            request.getRequestDispatcher("CRUDpac/EDITAR.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error al mostrar el formulario de agregar doctor");
        }
    }
private void insertarPaciente(HttpServletRequest request, pacienteDAOimpl dao) 
        throws ServletException, IOException, Exception {
    try {
        String nombre = request.getParameter("firstname");
        String apellido = request.getParameter("lastname");
        String dni = request.getParameter("dni");
        String numeroHistoriaClinica = request.getParameter("numberclinicalhistory");

        Paciente paciente = new Paciente();
        paciente.setFirstname(nombre);
        paciente.setLastname(apellido);
        paciente.setDni(dni);
        paciente.setNumberClinicalHistory(numeroHistoriaClinica);

        dao.insert(paciente);
    } catch (SQLException e) {
        e.printStackTrace();
        throw new ServletException("Error al insertar el paciente");
    }
}

private void actualizarPaciente(HttpServletRequest request, pacienteDAOimpl dao) 
        throws ServletException, IOException, Exception {
    try {
        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("firstname");
        String apellido = request.getParameter("lastname");
        String dni = request.getParameter("dni");
        String numeroHistoriaClinica = request.getParameter("numberclinicalhistory");

        Paciente paciente = new Paciente();
        paciente.setId(id);
        paciente.setFirstname(nombre);
        paciente.setLastname(apellido);
        paciente.setDni(dni);
        paciente.setNumberClinicalHistory(numeroHistoriaClinica);

        dao.update(paciente);
    } catch (SQLException e) {
        e.printStackTrace();
        throw new ServletException("Error al actualizar el paciente");
    }
}

private void eliminarPaciente(HttpServletRequest request, pacienteDAOimpl dao) 
        throws ServletException, IOException, Exception {
    try {
        int id = Integer.parseInt(request.getParameter("id"));
        dao.delete(id);
    } catch (SQLException e) {
        e.printStackTrace();
        throw new ServletException("Error al eliminar el paciente");
    }
}

}
