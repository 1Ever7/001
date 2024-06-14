    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package com.emergentes.controlador;

    import com.emergentes.dao.loginDAOimpl;
    import com.emergentes.modelos.login;
    import java.io.IOException;
    import java.io.PrintWriter;
    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import javax.servlet.http.HttpSession;

    /**
     *
     * @author ALVARO
     */
    @WebServlet(name = "loginservlet", urlPatterns = {"/loginservlet"})
    public class loginservlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

        }

        /**
         * Handles the HTTP <code>POST</code> method.
         *
         * @param request servlet request
         * @param response servlet response
         * @throws ServletException if a servlet-specific error occurs
         * @throws IOException if an I/O error occurs
         */
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
           String username = request.getParameter("username");
            String password = request.getParameter("password");

            try {
                loginDAOimpl userDAO = new loginDAOimpl(); // Instancia del DAO
                login user = userDAO.getUser(username, password); // Obtener usuario desde la base de datos

                if (user != null) {
                     
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);

                    if ("Admin".equals(user.getRole())) {
                        response.sendRedirect("index.jsp");
                    } else {
                        response.sendRedirect("index1.jsp");
                    }
                } else {
                    response.sendRedirect("login.jsp?error=true");
                }

            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }
        }



    }
