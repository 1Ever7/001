<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.ParseException"%>
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
        <style>
            body {
                background-image: url('../imagenes/ani.jpeg');
                background-size: cover; /* Ajusta la imagen al tamaño de la ventana */
                background-repeat: no-repeat; /* Evita la repetición de la imagen */
            }
            .styled-table {
                width: 100%;
                border-collapse: collapse;
                border: 1px solid #ddd;
                background-color: #fff; /* Establece un color de fondo para la tabla */
            }

            .styled-table th, .styled-table td {
                padding: 10px;
                border: 1px solid #ddd;
                text-align: left;
            }

            .styled-table th {
                background-color: #f2f2f2;
            }

            .styled-table tr:nth-child(even) {
                background-color: #f9f9f9;
            }

            .styled-table tr:hover {
                background-color: #f2f2f2;
            }
            /* Estilos para botones */
            .styled-button {
                padding: 8px 12px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                margin-right: 5px; /* Espacio entre botones */
            }

            .editar-button {
                background-color: #007bff;
                color: white;
            }

            .eliminar-button {
                background-color: #dc3545;
                color: white;
            }

            .agregar-button {
                background-color: #28a745;
                color: white;
            }

            .styled-button:hover {
                opacity: 0.8;
            }

            /* Estilo para el buscador de fecha */
            #fecha {
                padding: 8px 12px;
                border-radius: 5px;
                border: 1px solid #ccc;
                margin-right: 5px; /* Espacio entre el buscador y los botones */
            }

            /* Estilos para el botón y los cuadros */
            .container {
                display: flex;
                align-items: center;
                justify-content: flex-end;
                margin-right: 30px; /* Espacio a la derecha */
            }

            .date-input {
                margin-right: 10px; /* Espacio entre el cuadro de fecha y el botón */
            }

            .label-input {
                margin-right: 10px; /* Espacio entre el label y el cuadro de texto */
            }

            /* Estilos mejorados para el h2 */
            h2 {
                margin-bottom: -45px; /* Espacio entre elementos */
                margin-right: 20px; /* Espacio a la derecha */
                font-family: Arial, sans-serif; /* Familia de fuente */
                font-size: 28px; /* Tamaño de letra */
                font-weight: bold; /* Negrita */
                color: #333; /* Color de texto */
                text-transform: uppercase; /* Convertir texto a mayúsculas */
                padding: 10px; /* Espaciado interno */
                background-color: #f0f0f0; /* Color de fondo */
                border-bottom: 2px solid #ccc; /* Línea de abajo */
                border-top: 2px solid #ccc; /* Línea de arriba */
                box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1); /* Sombra suave */
            }

            .a {
                font-weight: bold; /* Hacer el texto en negrita */
                color: #808080; /* Color gris (plomo) */
                font-size: 0.9em; /* Tamaño de la fuente */

            }
            .s {
                width: 0px;
                height: 0px;
            }

            .bus{
                background-color: #add8e6;
                color: #333;
            }


        </style>
    </head>
    <body>

        <h2>Consultas</h2>
        <form action="MainServlet3" method="post" class="container" >
            <label for="fecha" class="label-input">Buscar por fecha:</label>
            <input type="date" id="fecha" name="fecha" class="date-input">
            <input type="hidden" name="action" value="buscar">
            <button class="styled-button bus" type="submit">Buscar</button>
        </form>
        <h1> </h1>
        
        <div>
    <form action="servletpdfmedi" method="get" style="display: inline-block; margin-right: 10px;">
        <input type="submit" value="Generar Reporte PDF Historial Medico">
    </form>
    <form action="servletconmed" method="get" style="display: inline-block; margin-right: 10px;">
        <input type="submit" value="Generar Reporte PDF Atencion Medica">
    </form>
    <form action="MainServlet3?action=añadir" method="post" style="display: inline-block;">
        <input type="hidden" name="action" value="añadir">
        <button type="submit">Agregar Nuevo Paciente</button>
    </form>
</div>

        <h1> </h1>
        
        <c:if test="${not empty detalles}">
            <table class="styled-table">
                <thead>
                    <tr>
                        <th>Fecha de Consulta</th>
                        <th>Nombre Completo</th>
                        <th>Diagnóstico</th>
                        <th>Tratamiento</th>
                        <th>Celular</th>
                        <th>Consulta IA</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="previousDate" value="" />
                    <c:forEach var="detalle" items="${detalles}">
                        <c:if test="${detalle.fechaConsulta ne previousDate}">
                            <c:set var="previousDate" value="${detalle.fechaConsulta}" />
                            <tr>
                                <td colspan="7" class="s"><strong class="a">Fecha de Consulta: ${detalle.fechaConsulta}</strong></td>
                            </tr>
                        </c:if>
                        <tr>
                            <td>${detalle.fechaConsulta}</td>
                            <td>${detalle.nombrePaciente} ${detalle.apellidoPaciente}</td>
                            <td>${detalle.diagnostico}</td>
                            <td>${detalle.tratamiento}</td>
                            <td>${detalle.cel}</td>
                            <td>${detalle.gptcon}</td>
                            <td>
                                <form action="MainServlet3?action=ver" method="post">
                                    <input type="hidden" name="action" value="ver">
                                    <input type="hidden" name="id" value="${detalle.id}">
                                    <input type="hidden" name="medicalConsultaId" value="${detalle.medicalConsultaId}">
                                    <input type="hidden" name="detalleConsultaId" value="${detalle.detalleConsultaId}">
                                    <input type="hidden" name="firstname" value="${detalle.nombrePaciente}">
                                    <input type="hidden" name="lastname" value="${detalle.apellidoPaciente}">
                                    
                                    <button class="styled-button editar-button" type="submit">Editar</button>
                                </form>
                                <form action="MainServlet3?action=select" method="post">
                                    <input type="hidden" name="action" value="select">
                                    <input type="hidden" name="id" value="${detalle.id}">
                                    <input type="hidden" name="medicalConsultaId" value="${detalle.medicalConsultaId}">
                                    <input type="hidden" name="detalleConsultaId" value="${detalle.detalleConsultaId}">
                                    <input type="hidden" name="firstname" value="${detalle.nombrePaciente}">
                                    <input type="hidden" name="lastname" value="${detalle.apellidoPaciente}">
                                    <button class="styled-button eliminar-button" type="submit">Eliminar</button>
                                </form>
                                <form action="MainServlet3?action=mostrarEs" method="post">
                                    <input type="hidden" name="action" value="mostrarEs">
                                    <input type="hidden" name="id" value="${detalle.id}">

                                    <input type="hidden" name="medicalConsultaId" value="${detalle.medicalConsultaId}">
                                    <input type="hidden" name="detalleConsultaId" value="${detalle.detalleConsultaId}">
                                    <input type="hidden" name="firstname" value="${detalle.nombrePaciente}">
                                    <input type="hidden" name="lastname" value="${detalle.apellidoPaciente}">
                                    <button class="styled-button agregar-button" type="submit">Agregar Consulta</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty detalles}">
            <p>No consulta con esa fecha.</p>
        </c:if>
    </body>
</html>