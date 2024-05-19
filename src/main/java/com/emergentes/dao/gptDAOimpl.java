/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emergentes.dao;

import com.emergentes.conDB.ConexionDB;
import static com.emergentes.conDB.ConexionDB.getConnection;

import com.emergentes.modelos.gptmod;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ALVARO
 */
public class gptDAOimpl extends ConexionDB implements gptDAO {

    @Override
    public gptmod getBynomape(int id) throws Exception {
        gptmod detalle = null;
        try ( Connection conn = ConexionDB.getConnection();  PreparedStatement ps = conn.prepareStatement("SELECT p.id, p.firstname, p.lastname, dc.analisis_medico"
                + "                    FROM paciente p "
                + "                    INNER JOIN medical_consulta mc ON p.id = mc.id_paciente "
                + "                    INNER JOIN detalle_consulta dc ON mc.id = dc.id_medcon "
                + "					where p.id=?")) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    detalle = new gptmod();
                    detalle.setId(rs.getInt("id"));
                    detalle.setNombre(rs.getString("firstname"));
                    detalle.setApellido(rs.getString("firstname"));
                    detalle.setGptcon(rs.getString("analisis_medico"));
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            Connection conn = null;
            this.desconectar(conn);

        }
        return detalle;
    }

    @Override
    public void update(gptmod gptmod) throws Exception {
        //Connection conn = null;
        //PreparedStatement ps = null;
        //String sql = "UPDATE detalle_consulta SET analisis_medico = ? " +
//"WHERE id_medcon IN (SELECT id FROM medical_consulta WHERE id_paciente = ?)";

        try ( Connection conn = ConexionDB.getConnection();  PreparedStatement ps = conn.prepareStatement("UPDATE detalle_consulta SET analisis_medico = ? "
                + "WHERE id_medcon IN (SELECT id FROM medical_consulta WHERE id_paciente = ?)")) {

            ps.setString(1, gptmod.getGptcon());
            ps.setInt(2, gptmod.getId());
            ps.execute();
        } catch (Exception e) {
            throw e;
        } finally {
            Connection conn = null;
            this.desconectar(conn);

        }
    }

    @Override
    public List<gptmod> getAll() throws Exception {
        List<gptmod> gptmod = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "SELECT p.id AS id, p.firstname, p.lastname, dc.analisis_medico "
                    + "FROM paciente p "
                    + "INNER JOIN medical_consulta mc ON p.id = mc.id_paciente "
                    + "INNER JOIN detalle_consulta dc ON mc.id = dc.id_medcon";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                gptmod gptmods = new gptmod(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("analisis_medico")
                        
                );

                gptmod.add(gptmods);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            //Connection conn = null;
            this.desconectar(conn);

        }
        return gptmod;

    }

}
