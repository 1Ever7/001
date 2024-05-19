/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emergentes.dao;

import com.emergentes.conDB.ConexionDB;
import static com.emergentes.conDB.ConexionDB.getConnection;
import com.emergentes.modelos.login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ALVARO
 */
public class loginDAOimpl implements loginDAO {

    private Connection connection;

    // Constructor que recibe la conexión a la base de datos
    public loginDAOimpl() throws ClassNotFoundException, SQLException {
        this.connection = ConexionDB.getConnection();
    }

    @Override
    public login getUser(String username, String password) {
        login user = null;
        String query = "select id,id_doctor,password1,tipo_usuario "
                + "from usuario where id_doctor=(select doc.id from doctor as doc where codi= ?) and"
                + "					password1= ?";

        try ( PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                user = new login();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("id_doctor"));
                user.setPassword(rs.getString("password1"));
                user.setRole(rs.getString("tipo_usuario"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public login insert(login log) throws SQLException {
        String query = "insert into usuario (id_doctor, password1,tipo_usuario) "
                + "values((select id from doctor where codi=?),?,?)";
        try ( PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, log.getUsername());
            statement.setString(2, log.getPassword());
            statement.setString(3, log.getRole());
            statement.executeUpdate();
        }
        return log;
    }

    @Override
    public List<login> getAll() throws SQLException {
        List<login> users = new ArrayList<>();
        String query = "select u.id,d.codi,u.password1,u.tipo_usuario from usuario as u "
                + "inner join doctor  d on u.id_doctor = d.id";
        try ( PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                login user = new login();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("codi"));
                user.setPassword(rs.getString("password1"));
                user.setRole(rs.getString("tipo_usuario"));
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public void update(login log) throws SQLException {
        String query = "UPDATE usuario SET id_doctor = "
                + "(select doc.id from doctor as doc where codi= ?),"
                + " password1 = ?, tipo_usuario = ? WHERE id = ?";
        try ( PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, log.getUsername());
            statement.setString(2, log.getPassword());
            statement.setString(3, log.getRole());
            statement.setInt(4, log.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM usuario WHERE id = ?";
        try ( PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public login getById(int id) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        login log = null;

        try {
            conn = getConnection();
            String sql = "SELECT u.id AS id, doc.codi, u.password1, u.tipo_usuario "
                    + "FROM usuario AS u "
                    + "INNER JOIN doctor doc ON u.id_doctor = doc.id "
                    + "WHERE u.id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                log = new login();
                log.setId(rs.getInt("id"));
                log.setUsername(rs.getString("codi"));
                log.setPassword(rs.getString("password1"));
                log.setRole(rs.getString("tipo_usuario"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return log;
    }

    
    
    }
