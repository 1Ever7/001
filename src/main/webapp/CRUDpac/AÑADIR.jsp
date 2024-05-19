<%@page import="com.emergentes.modelos.Paciente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Agregar Paciente</title>
    </head>
    <body>
        <h1>Agregar Paciente</h1>
        <form action="../MainServlet2?action=insertar" method="post">
            <input type="hidden" name="action" value="insertar">

            Nombre: <input type="text" name="firstname"><br>
            Apellido: <input type="text" name="lastname"><br>
            DNI: <input type="text" name="dni"><br>
            Number Clinical History: <input type="text" name="numberclinicalhistory"><br>
            <button type="submit">Agregar Paciente</button>
        </form>
    </body>
</html>
