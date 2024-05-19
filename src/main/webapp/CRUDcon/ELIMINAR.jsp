<%@page import="java.sql.Date"%>
<%@page import="com.emergentes.modelos.Consulta"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Detalles del Consulta</title>
    </head>
    <body>
        <h1>Detalles del Consulta</h1>
        <div>
            
            <p>Nombre: ${detalles.nombrePaciente}</p>
            <p>Apellido: ${detalles.apellidoPaciente}</p>
            <p>Fecha: ${detalles.fechaConsulta}</p>
            <p>Diagnostico : ${detalles.diagnostico}</p>
            <p>Tratamiento : ${detalles.tratamiento}</p>
            <p>Celular : ${detalles.cel}</p>
            <p>Analisis IA : ${detalles.gptcon}</p>
            <form action="MainServlet3?action=eliminar" method="post">
                <input type="hidden" name="action" value="eliminar">
                
                <input type="hidden" name="medicalConsultaId" value="${detalles.medicalConsultaId}">
                <input type="hidden" name="detalleConsultaId" value="${detalles.detalleConsultaId}">
                <button type="submit">Eliminar</button>
            </form>
        </div>
    </body>
</html>
