<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>



<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página JSP</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }

        main {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-image: url('imagenes/fondo.jpeg');
            background-size: cover;
            background-position: center;
            background-color: #f2f2f2;
        }

        .container {
            background-color: #add8e6;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: 400px;
            text-align: center; /* Centro los elementos dentro del contenedor */
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
        }

        label {
            font-weight: bold;
        }

        input[type="text"] {
            width: 95%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 10px;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        textarea {
            width: 95%;
            padding: 10px;
            margin-top: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            resize: vertical;
        }

        .button-link {
            display: inline-block;
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            text-decoration: none;
            border: 2px solid #007bff;
            border-radius: 5px;
            transition: background-color 0.3s, border-color 0.3s, color 0.3s;
            margin-right: 20px;
        }

        .button-link:hover {
            background-color: #0056b3;
            border-color: #0056b3;
        }
        
       

        .rotating-image {
            max-width: 100px; /* Establece el ancho máximo de la imagen */
            margin: 0 auto; /* Centra la imagen horizontalmente */
             width: 100%;
                height: 100%;
                object-fit: cover;
                clip-path: polygon(
                    50% 0%, 
                    100% 25%, 
                    100% 100%, 
                    0% 100%, 
                    0% 25%
                    );
                animation: rotate 10s linear infinite;
        }
        
        
        
         @keyframes rotate {
                from {
                    transform: rotateY(0deg);
                }
                to {
                    transform: rotateY(360deg);
                }
            }
    </style>
</head>
<body >
    <main style="background-image: url('../imagenes/ani.jpeg');">
        <div class="container" >
            <img src="../imagenes/ia.jpg" alt="Imagen sin fondo" class="rotating-image">
            <h1>Hola como puedo ayurate </h1>
            <form action="${pageContext.request.contextPath}/servletgpt" method="post">
                <label for="pregunta">Diagnóstico:</label><br>
                <input type="text" id="diagnostico" name="pregunta" <% if (request.getAttribute("botonBloqueado") != null && (boolean) request.getAttribute("botonBloqueado")) { %>disabled<% } %>><br>
                <input type="submit" id="enviar" value="Enviar" <% if (request.getAttribute("botonBloqueado") != null && (boolean) request.getAttribute("botonBloqueado")) { %>disabled<% } %>>
            </form>
            <form >
                <%-- Mostrar la respuesta si está disponible --%>
                <% String apiResponse = (String) request.getAttribute("apiResponse"); %>
                <% if (apiResponse != null && !apiResponse.isEmpty()) { %>
                    <% JSONObject jsonResponse = new JSONObject(apiResponse); %>
                    <% JSONArray candidates = jsonResponse.getJSONArray("candidates"); %>
                    <% for (int i = 0; i < candidates.length(); i++) { %>
                        <% JSONObject candidate = candidates.getJSONObject(i); %>
                        <% JSONArray parts = candidate.getJSONObject("content").getJSONArray("parts"); %>
                        <textarea rows="10" readonly>
                            <% for (int j = 0; j < parts.length(); j++) { %>
                                <% JSONObject part = parts.getJSONObject(j);%>
                                <%= new String(part.getString("text").getBytes("ISO-8859-1"), "UTF-8")%>\n
                            <% } %>
                        </textarea>
                    <% } %>
                    <h5 ></h5>
            <a href="api/api.jsp" class="button-link">Volver</a>
            <%-- Enlace para volver a api.jsp --%>
            <h5>Copia la respuesta para guardar</h5>
            <a href="MainServlet3?action=consulta" class="button-link">Guardar</a>
                <% } %>
            </form>
            
        </div>
    </main>
</body>
</html>
    
    <%--<h1>Consulta de Datos</h1>
  <table border="1">
            <thead>
   
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>analisis_consulta</th>
                    
                </tr>
            </thead>
            <tbody>
                <c:forEach var="detalle" items="${detalles}">

                    <tr>
                        <td>${detalle.id}</td>
                        <td>${detalle.firstname}</td>
                        <td>${detalle.lastname}</td>
                        <td>${detalle.gptcon}</td>
                        <td>
                            <form action="../servletgpt" method="post">
                                <input type="hidden" name="action" value="ver">
                                <input type="hidden" name="id" value="${detalle.id}">
                                <button type="submit" >Añadir</button>
                            </form>
                       
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>--%>
    
