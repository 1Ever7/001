package com.emergentes.controlador;

import com.emergentes.dao.docDAOimpl;
import com.emergentes.modelos.Doctor;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MainServlet1", urlPatterns = {"/MainServlet1"})
public class MainServlet1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            docDAOimpl dao = new docDAOimpl();
            // Obtener la lista de doctores y especialidades
        List<Object> result = dao.getAll();
        ArrayList<Doctor> doctores = (ArrayList<Doctor>) result.get(0);
        ArrayList<String> especia = (ArrayList<String>) result.get(1);
        
        // Establecer los atributos en el request
        request.setAttribute("doctores", doctores);
        request.setAttribute("especia", especia);
        
        request.getRequestDispatcher("tablas/doctor.jsp").forward(request, response);
        } catch ( Exception   ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al conectar con la base de datos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      try {
            String action = request.getParameter("action");
            docDAOimpl dao = new docDAOimpl();

            if (action != null) {
                switch (action) {
                    case "mostrarEs":
                        mostrarListaes(request, response, dao);
                        break;
                    case "insertar":
                        insertarDoctor(request, dao);
                    
                        break;
                    case "ver":
                        mostrarDoctor(request, response, dao);
                        break;
                    case "actualizar":
                        actualizarDoctor(request, dao);
                        break;
                    case "eliminar":
                        eliminarDoctor(request, dao);
                        break;
                    case "select":
                        seleccionarDoctor(request, response, dao);
                        break;
                    default:
                        break;  
                }
            }
   response.sendRedirect("MainServlet1");
   
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        private void insertarDoctor(HttpServletRequest request, docDAOimpl dao) 
                throws ServletException, IOException, ClassNotFoundException {
            try {
        String nombreEspecialidad = request.getParameter("nombre_especialidad");
        int idEspecialidad = dao.obtenerIdEspecialidad(nombreEspecialidad);

        if (idEspecialidad != -1) {
            String nombre = request.getParameter("firstname");
            String apellido = request.getParameter("lastname");
            String dni = request.getParameter("dni");
            String codi = request.getParameter("codi");

            Doctor doctor = new Doctor();
            doctor.setIdEspecialidad(idEspecialidad);
            doctor.setFirstname(nombre);
            doctor.setLastname(apellido);
            doctor.setDni(dni);
            doctor.setCodi(codi);

            dao.insert(doctor);
        } else {
            throw new ServletException("La especialidad especificada no existe");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new ServletException("Error al insertar el doctor");
    }
      }

      private void mostrarDoctor(HttpServletRequest request, HttpServletResponse response, docDAOimpl dao)
              throws ServletException, IOException, ClassNotFoundException {
          try {
             int id = Integer.parseInt(request.getParameter("id"));
             
        // Obtener el doctor específico por su ID
        Doctor doctor = dao.getById(id);
        // Obtener todas las especialidades disponibles
        ArrayList<String> especia = dao.obtenerEspecialidades();
        
        // Establecer los atributos en el request
        request.setAttribute("doctor", doctor);
        request.setAttribute("especia", especia);
        
        
        request.getRequestDispatcher("CRUD/EDITAR.jsp").forward(request, response);
          
        
          //request.getRequestDispatcher("CRUD/EDITAR.jsp").forward(request, response);
          
      } catch (Exception e) {
          e.printStackTrace();
          throw new ServletException("Error al ver la especialidad");
      }
      }

      private void actualizarDoctor(HttpServletRequest request, docDAOimpl dao) 
              throws ServletException, IOException, ClassNotFoundException {
          try {
               String nombreEspecialidad = request.getParameter("nombre_especialidad");
                int idEspecialidad = dao.obtenerIdEspecialidad(nombreEspecialidad);

        if (idEspecialidad != 0) {
            int id = Integer.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("firstname");
            String apellido = request.getParameter("lastname");
            String dni = request.getParameter("dni");
            String codi = request.getParameter("codi");

            Doctor doctor = new Doctor();
            doctor.setId(id);
            doctor.setNombreEspecialidad(nombreEspecialidad);
            doctor.setIdEspecialidad(idEspecialidad);
            doctor.setFirstname(nombre);
            doctor.setLastname(apellido);
            doctor.setDni(dni);
            doctor.setCodi(codi);

            dao.update(doctor);
        } else {
            throw new ServletException("La especialidad especificada no existe");
        }
          } catch (SQLException e) {
              e.printStackTrace();
              throw new ServletException("Error al actualizar el doctor");
          }
      }

    private void eliminarDoctor(HttpServletRequest request, docDAOimpl dao) 
            throws ServletException, IOException, ClassNotFoundException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Error al eliminar el doctor");
        }
    }

    private void seleccionarDoctor(HttpServletRequest request, HttpServletResponse response, docDAOimpl dao) 
            throws ServletException, IOException, ClassNotFoundException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Doctor doctor = dao.getById(id);
            request.setAttribute("doctor", doctor);
            request.getRequestDispatcher("CRUD/ELIMINAR.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Error al seleccionar el doctor");
        }
    }
private void mostrarListaes(HttpServletRequest request, HttpServletResponse response, docDAOimpl dao)
        throws ServletException, IOException, ClassNotFoundException {
    try {
        ArrayList<String> especi = dao.obtenerEspecialidades();
        request.setAttribute("especi", especi);
        request.getRequestDispatcher("CRUD/AÑADIR.jsp").forward(request, response);
    } catch (Exception e) {
        e.printStackTrace();
        throw new ServletException("Error al mostrar el formulario de agregar doctor");
    }
}

}
