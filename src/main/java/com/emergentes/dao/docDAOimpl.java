package com.emergentes.dao;

import com.emergentes.conDB.ConexionDB;
import com.emergentes.modelos.Doctor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class docDAOimpl extends ConexionDB implements docDAO {

    @Override
    public void insert(Doctor doctor) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            String sql = "INSERT INTO doctor (id_especialidad, firstname, lastname, dni, codi) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, doctor.getIdEspecialidad());
            ps.setString(2, doctor.getFirstname());
            ps.setString(3, doctor.getLastname());
            ps.setString(4, doctor.getDni());
            ps.setString(5, doctor.getCodi());
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            //Connection conn = null;
            this.desconectar(conn);

        }
    }

    @Override
    public void update(Doctor doctor) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            String sql = "UPDATE doctor SET id_especialidad = ?, firstname = ?, lastname = ?, dni = ?, codi = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, doctor.getIdEspecialidad());
            ps.setString(2, doctor.getFirstname());
            ps.setString(3, doctor.getLastname());
            ps.setString(4, doctor.getDni());
            ps.setString(5, doctor.getCodi());
            ps.setInt(6, doctor.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            //Connection conn = null;
            this.desconectar(conn);

        }
    }

    @Override
    public void delete(int id) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            String sql = "DELETE FROM doctor WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            // Connection conn = null;
            this.desconectar(conn);

        }
    }

    @Override
    public Doctor getById(int id) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Doctor doctor = null;

        try {
            conn = getConnection();
            String sql = "SELECT * FROM doctor WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                doctor = new Doctor();
                doctor.setId(rs.getInt("id"));
                doctor.setIdEspecialidad(rs.getInt("id_especialidad"));
                doctor.setFirstname(rs.getString("firstname"));
                doctor.setLastname(rs.getString("lastname"));
                doctor.setDni(rs.getString("dni"));
                doctor.setCodi(rs.getString("codi"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            //  Connection conn = null;
            this.desconectar(conn);

        }
        return doctor;
    }

    @Override
    public List<Object> getAll() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Doctor> doctores = new ArrayList<>();
        List<String> especia = new ArrayList<>();
        try {
            conn = getConnection();
            String sql = "SELECT * FROM doctor";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt("id"));
                doctor.setIdEspecialidad(rs.getInt("id_especialidad"));
                doctor.setFirstname(rs.getString("firstname"));
                doctor.setLastname(rs.getString("lastname"));
                doctor.setDni(rs.getString("dni"));
                doctor.setCodi(rs.getString("codi"));
                doctores.add(doctor);
            }

            // Obtener la lista de nombres de especialidades disponibles
            sql = "SELECT DISTINCT nombre FROM especialidad";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                especia.add(rs.getString("nombre"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            //   Connection conn = null;
            this.desconectar(conn);

        }
// Devolver la lista de doctores y la lista de nombres de especialidades
        List<Object> result = new ArrayList<>();
        result.add(doctores);
        result.add(especia);
        return result;
        //return doctores;
    }

    // Método para obtener el ID de la especialidad por nombre
    public int obtenerIdEspecialidad(String nombreEspecialidad)
            throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int idEspecialidad = -1; // Valor por defecto si no se encuentra la especialidad

        try {
            conn = getConnection();
            String sql = "SELECT id FROM especialidad WHERE nombre = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, nombreEspecialidad);
            rs = ps.executeQuery();
            if (rs.next()) {
                idEspecialidad = rs.getInt("id");
            }else {
            throw new SQLException("La especialidad '" + nombreEspecialidad + "' no existe en la base de datos");
        }
        } catch (Exception e) {
            throw e;
        } finally {
            this.desconectar(conn);
        }

        return idEspecialidad;
    }
    
    
public ArrayList<String> obtenerEspecialidades() throws SQLException, ClassNotFoundException {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    ArrayList<String> especialidades = new ArrayList<>();
    
    try {
        conn = getConnection();
        String sql = "SELECT nombre FROM especialidad";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        
        while (rs.next()) {
            especialidades.add(rs.getString("nombre"));
        }
    } catch (Exception e) {
        throw e;
    } finally {
        this.desconectar(conn);
    }
    
    return especialidades;
}

}
