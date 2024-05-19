/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emergentes.controlador;

import com.emergentes.conDB.ConexionDB;
import com.emergentes.dao.loginDAOimpl;
import com.emergentes.modelos.login;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ALVARO
 */
@WebServlet(name = "userservlet", urlPatterns = {"/userservlet"})
public class userservlet extends HttpServlet {
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       String action = request.getParameter("action");
        
        
        if (action == null) {
            action = "list";
        }

        try {
            loginDAOimpl dao = new loginDAOimpl();
        Connection conn = ConexionDB.getConnection();
            switch (action) {
                case "list":
                    List<login> list = dao.getAll();
                    request.setAttribute("users", list);
                    request.getRequestDispatcher("addlog.jsp").forward(request, response);
                    break;
                case "edit":
                    int id = Integer.parseInt(request.getParameter("id"));
                    login user = dao.getById(id);
                    request.setAttribute("log", user);
                    request.getRequestDispatcher("crudlog/editar.jsp").forward(request, response);
                    break;
                case "delete":
                    int userId = Integer.parseInt(request.getParameter("id"));
                    dao.delete(userId);
                    response.sendRedirect("userservlet?action=list");
                    break;
                case "new":
                    request.getRequestDispatcher("crudlog/editar.jsp").forward(request, response);
                    break;
                default:
                    response.sendRedirect("userservlet");
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        } catch (Exception ex) {
            Logger.getLogger(userservlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     String action = request.getParameter("action");

        if (action != null) {
            try {
                loginDAOimpl dao = new loginDAOimpl();
                Connection conn = ConexionDB.getConnection();
                if ("insert".equals(action) || "update".equals(action)) {
                  
                    String username = request.getParameter("codi");
                    String password = request.getParameter("password1");
                    String role = request.getParameter("tipo_usuario");
                    

                    login user = new login();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setRole(role);

                    if ("insert".equals(action)) {
                        dao.insert(user);
                    } else if ("update".equals(action)) {
                         int id = Integer.parseInt(request.getParameter("id"));
                        user.setId(id);
                        dao.update(user);
                    }
                    response.sendRedirect("userservlet?action=list");

                }
            } catch (SQLException e) {
                throw new ServletException(e);
            }catch (ClassNotFoundException ex) {
            Logger.getLogger(userservlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
}
