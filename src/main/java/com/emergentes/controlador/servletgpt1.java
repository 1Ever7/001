/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emergentes.controlador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ALVARO
 */
@WebServlet(name = "servletgpt1", urlPatterns = {"/servletgpt1"})
public class servletgpt1 extends HttpServlet {

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
            request.getRequestDispatcher("api/consultasIA.jsp").forward(request, response);
            

        } catch (IOException e) {
            e.printStackTrace();
            // Manejar errores si la llamada a la API falla
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar la solicitud");
        }
 }
}
