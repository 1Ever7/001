<%@page import="com.emergentes.modelos.Paciente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Detalles del Paciente</title>
         <style>
        body {
            font-family: 'Helvetica Neue', Arial, sans-serif;
            background-image: url('../imagenes/ani.jpeg');
            background-size: cover;
            background-repeat: no-repeat;
            background-attachment: fixed;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            width: 80%;
            margin: auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
        }
        form {
            max-width: 500px;
            margin: auto;
        }
        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }
        select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }
        button {
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background-color: #0056b3;
        }
         .form-container {
            background-color: rgba(255, 255, 255, 0.8); /* Fondo blanco semitransparente */
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 300px;
            text-align: center;
        }

        .form-container img {
            width: 100px;
            margin-bottom: 20px;
        }
    </style>
    </head>
    <body>
        <div class="form-container">
        <h1>Detalles del Paciente</h1>
        <div>
            <p>ID: ${pacientes.id}</p>

            <p>Nombre: ${pacientes.firstname}</p>
            <p>Apellido: ${pacientes.lastname}</p>
            <p>DNI: ${pacientes.dni}</p>
            <p>Number Clinical History: ${pacientes.numberClinicalHistory}</p>
            <form action="MainServlet2?action=eliminar" method="post">
                <input type="hidden" name="action" value="eliminar">
                <input type="hidden" name="id" value="${pacientes.id}">
                <button type="submit">Eliminar</button>
            </form>
        </div>
                </div>
    </body>
</html>
