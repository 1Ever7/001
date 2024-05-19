<%@page import="com.emergentes.modelos.Paciente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Detalles del Paciente</title>
    </head>
    <body>
        <h1>Detalles del Paciente</h1>
        <div>
            <p>ID: ${pacientes.id}</p>

            <p>Nombre: ${pacientes.firstname}</p>
            <p>Apellido: ${pacientes.lastname}</p>
            <p>DNI: ${pacientes.dni}</p>
            <p>Number Clinical History: ${pacientes.numberClinicalHistory}</p>
            <form action="MainServlet2?action=eliminar" method="post">
                <input type="hidden" name="action" value="eliminar">
                <input type="hidden" name="id" value="${pacientes.id}">
                <button type="submit">Eliminar</button>
            </form>
        </div>
    </body>
</html>
