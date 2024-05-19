package com.emergentes.dao;

import com.emergentes.modelos.Especialidad;
import com.emergentes.conDB.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class especieDAOimpl extends ConexionDB implements especialidadDAO {

    @Override
    public void insert(Especialidad especie) throws Exception {
        try ( Connection conn = ConexionDB.getConnection();  PreparedStatement ps = conn.prepareStatement("INSERT INTO especialidad (nombre) VALUES (?)")) {
            ps.setString(1, especie.getNombre());
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            Connection conn = null;
            this.desconectar(conn);

        }
    }

    @Override
    public void update(Especialidad especie) throws Exception {
        try ( Connection conn = ConexionDB.getConnection();  
                PreparedStatement ps = conn.prepareStatement("UPDATE especialidad SET nombre = ? WHERE id = ?")) {
            ps.setString(1, especie.getNombre());
            ps.setInt(2, especie.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            Connection conn = null;
            this.desconectar(conn);

        }
    }

    @Override
    public void delete(int id) throws Exception {
        try ( Connection conn = ConexionDB.getConnection();  
                PreparedStatement ps = conn.prepareStatement("DELETE FROM especialidad WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            Connection conn = null;
            this.desconectar(conn);

        }
    }

    @Override
    public Especialidad getById(int id) throws Exception {
        Especialidad especie = null;
        try ( Connection conn = ConexionDB.getConnection();  PreparedStatement ps = conn.prepareStatement("SELECT * FROM especialidad WHERE id = ?")) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    especie = new Especialidad();
                    especie.setId(rs.getInt("id"));
                    especie.setNombre(rs.getString("nombre"));
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            Connection conn = null;
            this.desconectar(conn);

        }
        return especie;
    }

    @Override
    public List<Especialidad> getAll() throws Exception {
        List<Especialidad> especies = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexionDB.getConnection();
            String sql = "SELECT * FROM especialidad";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Especialidad especie = new Especialidad();
                especie.setId(rs.getInt("id"));
                especie.setNombre(rs.getString("nombre"));
                especies.add(especie);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al obtener las especialidades");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return especies;
    }

}
