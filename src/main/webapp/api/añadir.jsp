<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.emergentes.modelos.Consulta"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agregar Datos</title>
</head>
<body>
    <h1>Agregar Datos</h1>
    <form action="../servletgpt" method="post">
        <input type="hidden" name="action" value="actualizar">
        
        <p>ID: ${detalle.id}</p>

            <p>Nombre: ${detalle.firstname}</p>
            <p>Apellido: ${detalle.lastname}</p>

        <label for="analisis_medico">Análisis Médico:</label>
        <textarea id="analisis_medico" name="analisis_medico" rows="4" cols="50"></textarea><br><br>
        <button type="submit">Actualizar</button>
    </form>
</body>
</html>
