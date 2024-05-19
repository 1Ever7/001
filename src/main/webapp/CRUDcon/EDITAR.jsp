
<%@page import="java.sql.Date"%>
<%@page import="com.emergentes.modelos.Consulta"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Editar Consulta Médica</title>
    </head>
    <body>
        <h1>Detalles de la Consulta</h1>
        <form action="MainServlet3" method="post">
            <input type="hidden" name="id" value="${detalles.id}">
            <input type="hidden" name="medicalConsultaId" value="${detalles.medicalConsultaId}">
            <input type="hidden" name="detalleConsultaId" value="${detalles.detalleConsultaId}">
            <input type="hidden" name="action" value="actualizar">
            Nombre del Paciente: <input type="text"name="firstname" value="${detalles.nombrePaciente}" readonly><br>
            Apellido del Paciente: <input type="text" name="lastname" value="${detalles.apellidoPaciente}" readonly><br>
            
             Especialidad:<select name="nombre_especialidad" id="especialidad">
                  <option value="">Seleccione una especialidad</option>
                <c:forEach var="especialidad" items="${especialidades}">
                    <option value="${especialidad}" ${especialidad == idoc ? 'selected' : ''}>${especialidad}</option>
                </c:forEach>
            </select>
            <br>
            
            Fecha de Creación: <input type="Date" name="createdate" value="${detalles.fechaConsulta}" ><br>
            Diagnóstico: <input type="text"  name="diagnostic" value="${detalles.diagnostico}"><br>
            Tratamiento:<input type="text" name="treatment" value="${detalles.tratamiento}"><br>
            Celular:<input type="text" name="celular" value="${detalles.cel}"><br>
            Analisis IA:<textarea type="text" name="analisis_medico" value="${detalles.gptcon}"></textarea><br>


            <button type="submit">Actualizar</button>
        </form>
    </body>
</html>
