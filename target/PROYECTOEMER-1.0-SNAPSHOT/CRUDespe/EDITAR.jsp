<%@page import="com.emergentes.modelos.Especialidad"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Editar Doctor</title>
    </head>
    <body>
        <h1>Editar Doctor</h1>
        <form action="MainServlet4?action=actualizar" method="post">
            <input type="hidden" name="action" value="actualizar">
            <input type="hidden" name="id" value="${especialidad.id}">
            <p>ID: ${especialidad.id}</p>
            Especialidad: <input type="text" name="nombre" value="${especialidad.nombre}"><br>
            <button type="submit">Actualizar</button>
        </form>
    </body>
</html>

