/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emergentes.dao;

import com.emergentes.conDB.ConexionDB;
import static com.emergentes.conDB.ConexionDB.getConnection;
import com.emergentes.modelos.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class pacienteDAOimpl extends ConexionDB implements pacienteDAO{

    @Override
    public void insert(Paciente paciente) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            String sql = "INSERT INTO paciente (firstname, lastname, dni, numberclinicalhistory) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, paciente.getFirstname());
            ps.setString(2, paciente.getLastname());
            ps.setString(3, paciente.getDni());
            ps.setString(4, paciente.getNumberClinicalHistory());
            ps.executeUpdate();
        }catch (Exception e) {
            throw e;
        } finally {
            //Connection conn = null;
            this.desconectar(conn);

        }
    }

    @Override
    public void update(Paciente paciente) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            String sql = "UPDATE paciente SET firstname=?, lastname=?, dni=?, numberclinicalhistory=? WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, paciente.getFirstname());
            ps.setString(2, paciente.getLastname());
            ps.setString(3, paciente.getDni());
            ps.setString(4, paciente.getNumberClinicalHistory());
            ps.setInt(5, paciente.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            //Connection conn = null;
            this.desconectar(conn);

        }
    }

    @Override
    public void delete(int id) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            String sql = "DELETE FROM paciente WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            //Connection conn = null;
            this.desconectar(conn);

        }
    }

    @Override
    public Paciente getById(int id) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Paciente paciente = null;
        try {
            conn = getConnection();
            String sql = "SELECT * FROM paciente WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setFirstname(rs.getString("firstname"));
                paciente.setLastname(rs.getString("lastname"));
                paciente.setDni(rs.getString("dni"));
                paciente.setNumberClinicalHistory(rs.getString("numberclinicalhistory"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            //Connection conn = null;
            this.desconectar(conn);

        }
        return paciente;
    }

    @Override
    public List<Paciente> getAll() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Paciente> pacientes = new ArrayList<>();
        try {
            conn = getConnection();
            String sql = "SELECT * FROM paciente";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setFirstname(rs.getString("firstname"));
                paciente.setLastname(rs.getString("lastname"));
                paciente.setDni(rs.getString("dni"));
                paciente.setNumberClinicalHistory(rs.getString("numberclinicalhistory"));
                pacientes.add(paciente);
            }
        }catch (Exception e) {
            throw e;
        } finally {
            //Connection conn = null;
            this.desconectar(conn);

        }
        return pacientes;
    }
}
