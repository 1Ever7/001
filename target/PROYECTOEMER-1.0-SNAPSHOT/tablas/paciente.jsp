
<%@page import="com.emergentes.modelos.Paciente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Detalles del Doctor</title>
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
        <h1>Detalles del Doctor</h1>
        <table class="styled-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>DNI</th>
                    <th>Number Clinical History</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="pacientes" items="${pacientes}">
                    <tr>
                        <td>${pacientes.id}</td>
                        <td>${pacientes.firstname}</td>
                        <td>${pacientes.lastname}</td>
                        <td>${pacientes.dni}</td>
                        <td>${pacientes.numberClinicalHistory}</td>
                        <td>
                            <form action="MainServlet2?action=ver" method="post">
                                <input type="hidden" name="action" value="ver">
                                <input type="hidden" name="id" value="${pacientes.id}">
                                <button type="submit" class="styled-button editar-button">Editar</button>
                            </form>
                            <form action="MainServlet2?action=select" method="post">
                                <input type="hidden" name="action" value="select">
                                <input type="hidden" name="id" value="${pacientes.id}">
                                <button type="submit" class="styled-button eliminar-button">Eliminar</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <form action="CRUDpac/AÑADIR.jsp" method="post">
            <input type="hidden" name="action" value="insertar">
            <button type="submit"class="styled-button agregar-button">Agregar Paciente</button>
        </form>
    </body>
</html>
