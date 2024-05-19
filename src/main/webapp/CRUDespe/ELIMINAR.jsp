<%@page import="com.emergentes.modelos.Especialidad"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Detalles del Doctor</title>
    </head>
    <body>
        <h1>Detalles del Doctor</h1>
        <div>
            <p>ID: ${especialidad.id}</p>
            <p>Especialidad: ${especialidad.nombre}</p>

            <form action="MainServlet4?action=eliminar" method="post">
                <input type="hidden" name="action" value="eliminar">
                <input type="hidden" name="id" value="${especialidad.id}">
                <button type="submit">Eliminar</button>
            </form>
        </div>
    </body>
</html>
