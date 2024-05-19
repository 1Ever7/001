<%@page import="com.emergentes.modelos.login"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>
        <c:choose>
            <c:when test="${param.action eq 'edit' || param.action eq 'update'}">Editar Usuario</c:when>
            <c:otherwise>Añadir Usuario</c:otherwise>
        </c:choose>
    </title>
    <style>
        body {
            font-family: 'Helvetica Neue', Arial, sans-serif;
            background-image: url('../imagenes/ani.jpeg');
            background-size: cover;
            background-repeat: no-repeat;
            background-attachment: fixed;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .form-container {
            background-color: rgba(255, 255, 255, 0.8); /* Fondo blanco semitransparente */
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 300px;
            text-align: center;
        }

        .form-container img {
            width: 100px;
            margin-bottom: 20px;
        }

        h1 {
            color: #2c3e50; /* Darker shade of blue for the heading */
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin: 10px 0 5px;
            color: #34495e;
            font-weight: bold;
        }

        input[type="text"],
        input[type="password"],
        select {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[type="submit"] {
            background-color: #3498db; /* Medical blue */
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
        }

        input[type="submit"]:hover {
            background-color: #2980b9; /* Slightly darker blue on hover */
        }

        a {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            color: #3498db;
        }

        a:hover {
            color: #2980b9;
        }
    </style>
</head>
<body>
    <div class="form-container">
        
        <h1>
            <c:choose>
                <c:when test="${param.action eq 'edit' || param.action eq 'update'}">Editar Usuario</c:when>
                <c:otherwise>Añadir Usuario</c:otherwise>
            </c:choose>
        </h1>
        <form action="userservlet" method="post">
            <input type="hidden" name="id" value="${log.id}">
            <label for="username">Usuario:</label>
            <input type="text" name="codi" value="${log.username}" 
                   <c:if test="${param.action eq 'edit' || param.action eq 'update'}">readonly</c:if>
                   >
            <label for="password">Contraseña:</label>
            <input type="password" name="password1" value="${log.password}">
            <label for="role">Rol:</label>
            <select name="tipo_usuario">
                <option value="Doctor" <c:if test="${log.role eq 'Doctor'}">selected</c:if>>Doctor</option>
                <option value="Admin" <c:if test="${log.role eq 'Admin'}">selected</c:if>>Admin</option>
            </select>
            <input type="hidden" name="action" value="${param.action eq 'edit' ? 'update' : 'insert'}">
            <input type="submit" value="Guardar">
        </form>
        <a href="userservlet?action=list">Volver</a>
    </div>
</body>
</html>
