<%@page import="com.emergentes.modelos.Consulta"%>
<%@page import="com.emergentes.modelos.Especialidad"%>
<%@page import="com.emergentes.modelos.MedicalConsulta"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Consulta Detallada</title>

    <form action="MainServlet3?action=buscar" method="post">
        <label for="busqueda">Buscar:</label>
        <input type="text" id="busqueda" name="busqueda">
        <button type="submit">Buscar</button>
    </form>

</head>
<body>
    <h2>Detalles de la Consulta</h2>
    <c:if test="${not empty detalles}">

        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre Completo</th>
                    <th>Fecha de Creación</th>
                    <th>Diagnóstico</th>
                    <th>Tratamiento</th>
                    <th>Celular</th>
                    <th>Cosnulta IA</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="detalles" items="${detalles}">
                    <tr>
                        <td  >${detalles.id}</td>
                        <td  >${detalles.nombrePaciente} ${detalles.apellidoPaciente}</td>
                        <td >${detalles.fechaConsulta}</td>
                        <td>${detalles.diagnostico}</td>
                        <td>${detalles.tratamiento}</td>
                        <td>${detalles.cel}</td>
                        <td>${detalles.gptcon}</td>


                        <td>
                            <form action="MainServlet3?action=ver" method="post">
                                <input type="hidden" name="action" value="ver">
                                <input type="hidden" name="id" value="${detalles.id}">

                                <input type="hidden" name="medicalConsultaId" value="${detalles.medicalConsultaId}">
                                <input type="hidden" name="detalleConsultaId" value="${detalles.detalleConsultaId}">
                                <input type="hidden" name="firstname" value="${detalles.nombrePaciente}">
                                <input type="hidden" name="lastname" value="${detalles.apellidoPaciente}">

                                <button type="submit">Editar</button>
                            </form>
                            <form action="MainServlet3?action=select" method="post">
                                <input type="hidden" name="action" value="select">
                                <input type="hidden" name="id" value="${detalles.id}">

                                <input type="hidden" name="medicalConsultaId" value="${detalles.medicalConsultaId}">
                                <input type="hidden" name="detalleConsultaId" value="${detalles.detalleConsultaId}">
                                <input type="hidden" name="firstname" value="${detalles.nombrePaciente}">
                                <input type="hidden" name="lastname" value="${detalles.apellidoPaciente}">

                                <button type="submit">Eliminar</button>
                            </form>
                            <form action="MainServlet3?action=mostrarEs" method="post">
                                <input type="hidden" name="action" value="mostrarEs">
                                <input type="hidden" name="id" value="${detalles.id}">

                                <input type="hidden" name="medicalConsultaId" value="${detalles.medicalConsultaId}">
                                <input type="hidden" name="detalleConsultaId" value="${detalles.detalleConsultaId}">
                                <input type="hidden" name="firstname" value="${detalles.nombrePaciente}">
                                <input type="hidden" name="lastname" value="${detalles.apellidoPaciente}">

                                <button type="submit">Agregar Consulta</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <%-- Si no se encontró la consulta --%>
    <c:if test="${empty detalles}">
        <p>No se encontró ninguna consulta con el ID proporcionado.</p>
    </c:if>
</body>
</html>

ChatGPT
