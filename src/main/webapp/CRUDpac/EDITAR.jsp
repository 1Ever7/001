<%@page import="com.emergentes.modelos.Paciente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Editar Paciente</title>
    </head>
    <body>
        <h1>Editar Paciente</h1>
        <form action="MainServlet2?action=actualizar" method="post">
            <input type="hidden" name="action" value="actualizar">
            <input type="hidden" name="id" value="${pacientes.id}">

            Nombre: <input type="text" name="firstname" value="${pacientes.firstname}"><br>
            Apellido: <input type="text" name="lastname" value="${pacientes.lastname}"><br>
            DNI: <input type="text" name="dni" value="${pacientes.dni}"><br>
            Number Clinical History: <input type="text" name="numberclinicalhistory" value="${pacientes.numberClinicalHistory}"><br>
            <button type="submit">Actualizar</button>
        </form>
    </body>
</html>

