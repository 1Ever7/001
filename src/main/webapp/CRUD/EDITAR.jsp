<%@page import="com.emergentes.modelos.Doctor"%>

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
        <form action="MainServlet1?action=actualizar" method="post">
            <input type="hidden" name="action" value="actualizar">
            <input type="hidden" name="id" value="${doctor.id}">

            Especialidad:<select name="nombre_especialidad" id="especialidad">
                <c:forEach var="especialidad" items="${especia}">
                    <option value="${especialidad}" ${especialidad == idEspecialidad ? 'selected' : ''}>${especialidad}</option>
                </c:forEach>
            </select>
            
            <br>
            Nombre: <input type="text" name="firstname" value="${doctor.firstname}"><br>
            Apellido: <input type="text" name="lastname" value="${doctor.lastname}"><br>
            DNI: <input type="text" name="dni" value="${doctor.dni}"><br>
            Código: <input type="text" name="codi" value="${doctor.codi}"><br>
            <button type="submit">Actualizar</button>
        </form>
    </body>
</html>

