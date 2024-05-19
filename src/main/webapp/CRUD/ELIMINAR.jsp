<%@page import="com.emergentes.modelos.Doctor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>

<%
    ArrayList<Doctor> doctores = (ArrayList<Doctor>) request.getAttribute("doctores");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Detalles del Doctor</title>
    </head>
    <body>
        <h1>Detalles del Doctor</h1>
        <div>
            <p>ID: ${doctor.id}</p>
            <p>Especialidad: ${doctor.idEspecialidad}</p>
            <p>Nombre: ${doctor.firstname}</p>
            <p>Apellido: ${doctor.lastname}</p>
            <p>DNI: ${doctor.dni}</p>
            <p>Código: ${doctor.codi}</p>
            <form action="MainServlet1?action=eliminar" method="post">
                <input type="hidden" name="action" value="eliminar">
                <input type="hidden" name="id" value="${doctor.id}">
                <button type="submit">Eliminar</button>
            </form>
        </div>
    </body>
</html>
