<%@page import="com.emergentes.modelos.Especialidad"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Agregar Doctor</title>
    </head>
    <body>
        <h1>Agregar Doctor</h1>
        <form action="../MainServlet4?action=insertar" method="post">
            <input type="hidden" name="action" value="insertar">
            Especialidad: <input type="text" name="nombre"><br>
            <button type="submit">Agregar Doctor</button>
        </form>
    </body>
</html>
