<%@page import="com.emergentes.modelos.Doctor"%>
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
        <form action="MainServlet1?action=insertar" method="post">
            <input type="hidden" name="action" value="insertar">

            Especialidad:
            <select name="nombre_especialidad">
                
                <c:forEach var="especi" items="${especi}">
                    <option value="${especi}">${especi}</option>
                </c:forEach>
            </select><br>
            Nombre: <input type="text" name="firstname"><br>
            Apellido: <input type="text" name="lastname"><br>
            DNI: <input type="text" name="dni"><br>
            Código: <input type="text" name="codi"><br>
            <button type="submit">Agregar Doctor</button>
        </form>
    </body>
</html>
