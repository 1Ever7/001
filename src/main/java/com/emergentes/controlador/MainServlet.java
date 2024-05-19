package com.emergentes.controlador;


import com.emergentes.modelos.Doctor;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MainServlet", urlPatterns = {"/MainServlet"})
public class MainServlet extends HttpServlet {
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5433/hospital";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";
@Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Doctor> doctores = new ArrayList<>();
        
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "";

                    sql = "SELECT * FROM doctor";
                    ps = conn.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        Doctor doc = new Doctor();
                        doc.setId(rs.getInt("id"));
                        doc.setIdEspecialidad(rs.getInt("id_especialidad"));
                        doc.setFirstname(rs.getString("firstname"));
                        doc.setLastname(rs.getString("lastname"));
                        doc.setDni(rs.getString("dni"));
                        doc.setCodi(rs.getString("codi"));
                        doctores.add(doc);
                    }
                    
    request.setAttribute("doctores", doctores);
    request.getRequestDispatcher("tablas/doctor.jsp").forward(request, response);
    
        
    } catch (ClassNotFoundException | SQLException e) {
          response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al conectar con la base de datos");
        
         } catch (IOException e) {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al conectar con la base de datos");
    }
    }

  
@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            
            if (action != null) {
                switch (action) {
                    case "insertar":
                        insertarDoctor(request);
                        break;
                    case "actualizar":
                        actualizarDoctor(request);
                        break;
                    case "eliminar":
                        eliminarDoctor(request);
                        break;
                    default:
                        break;
                }
            }
            
            response.sendRedirect("MainServlet");
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
   private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    private ArrayList<Doctor> obtenerDoctores(Connection conn) throws SQLException {
        ArrayList<Doctor> doctores = new ArrayList<>();
        String sql = "SELECT * FROM doctor";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Doctor doctor = new Doctor();
            doctor.setId(rs.getInt("id"));
            doctor.setIdEspecialidad(rs.getInt("id_especialidad"));
            doctor.setFirstname(rs.getString("firsname"));
            doctor.setLastname(rs.getString("lastname"));
            doctor.setDni(rs.getString("dni"));
        doctor.setCodi(rs.getString("codi"));
            doctores.add(doctor);
        }
        rs.close();
        ps.close();
        return doctores;
    }
    
    private void insertarDoctor(HttpServletRequest request) throws ClassNotFoundException, SQLException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String especialidad = request.getParameter("especialidad");
        Connection conn = getConnection();
        String sql = "INSERT INTO doctor (nombre, apellido, especialidad) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nombre);
        ps.setString(2, apellido);
        ps.setString(3, especialidad);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }
    
    private void actualizarDoctor(HttpServletRequest request) throws ClassNotFoundException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String especialidad = request.getParameter("idespecialidad");
        Connection conn = getConnection();
        String sql = "UPDATE doctor SET nombre = ?, apellido = ?, especialidad = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nombre);
        ps.setString(2, apellido);
        ps.setString(3, especialidad);
        ps.setInt(4, id);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }
    
    private void eliminarDoctor(HttpServletRequest request) throws ClassNotFoundException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Connection conn = getConnection();
        String sql = "DELETE FROM doctor WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }
}
