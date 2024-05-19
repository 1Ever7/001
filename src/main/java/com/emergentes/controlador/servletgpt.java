/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emergentes.controlador;

import com.emergentes.dao.consultaDAOimpl;
import com.emergentes.dao.gptDAO;
import com.emergentes.dao.gptDAOimpl;

import com.emergentes.modelos.gptmod;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
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
@WebServlet(name = "servletgpt", urlPatterns = {"/servletgpt"})
public class servletgpt extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        // Obtener la pregunta enviada desde el formulario
        String pregunta = request.getParameter("pregunta");

        // Llamar al método para manejar la pregunta y obtener la respuesta
        manejarPregunta(request, response, pregunta);
   
    }
    
    
    
    
 private void manejarPregunta(HttpServletRequest request, HttpServletResponse response, String pregunta)
            throws ServletException, IOException {
        // Llamar a la API para obtener la respuesta
        String apiKey = "";
        String endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + apiKey;

        try {
            // Establecer la conexión con la API
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Establecer el cuerpo de la solicitud con la pregunta
            String requestBody = "{\"contents\":[{\"parts\":[{\"text\":\"" + pregunta + "\"}]}]}";

            // Enviar la solicitud
            OutputStream os = conn.getOutputStream();
            os.write(requestBody.getBytes());
            os.flush();

            // Obtener la respuesta de la API
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder apiResponse = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                apiResponse.append(output);
            }

            // Cerrar conexiones
            os.close();
            br.close();
            conn.disconnect();

            boolean botonBloqueado = true;
            request.setAttribute("botonBloqueado", botonBloqueado);

            // Enviar la respuesta de la API al JSP
            request.setAttribute("apiResponse", apiResponse.toString());
            request.getRequestDispatcher("api/api.jsp").forward(request, response);
            

        } catch (IOException e) {
            e.printStackTrace();
            // Manejar errores si la llamada a la API falla
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar la solicitud");
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void actualizarConsulta(HttpServletRequest request, gptDAO dao)
            throws ClassNotFoundException, SQLException, ServletException, IOException {
        try {

            int id = Integer.parseInt(request.getParameter("id"));
            String firstName = request.getParameter("firstname");
            String lastName = request.getParameter("lastname");

            String gptcon = request.getParameter("analisis_medico");

            gptmod gptmods = new gptmod();
            gptmods.setId(id);
            gptmods.setNombre(firstName);
            gptmods.setApellido(lastName);

            gptmods.setGptcon(gptcon);

            dao.update(gptmods);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarConsulta(HttpServletRequest request, HttpServletResponse response, gptDAOimpl dao)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));

//consultaDAOimpl dao = new consultaDAOimpl();
            gptmod gptmods = dao.getBynomape(id);

            request.setAttribute("gptmods", gptmods);
            request.getRequestDispatcher("api/añadir.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error al mostrar el formulario de agregar doctor");
        }
    }
    
 
}
