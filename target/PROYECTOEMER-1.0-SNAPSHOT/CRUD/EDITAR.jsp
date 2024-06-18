<%@page import="com.emergentes.modelos.Doctor"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Editar Doctor</title>
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
    
    <script>
    function validarFormulario() {
        var error = "";
        var nombreEspecialidad = document.getElementById("especialidad");
        var firstname = document.getElementsByName("firstname")[0];
        var lastname = document.getElementsByName("lastname")[0];
        var dni = document.getElementsByName("dni")[0];
        var codi = document.getElementsByName("codi")[0];
        var errorDiv = document.getElementById("errorDiv");

        if (nombreEspecialidad.value === "") {
            error += "Debe seleccionar una especialidad.\n";
        }
        if (firstname.value.trim() === "") {
            error += "Debe ingresar el nombre del doctor.\n";
        }
        if (lastname.value.trim() === "") {
            error += "Debe ingresar el apellido del doctor.\n";
        }
        if (dni.value.trim() === "") {
            error += "Debe ingresar el DNI del doctor.\n";
        }
        if (codi.value.trim() === "") {
            error += "Debe ingresar el código del doctor.\n";
        }

        if (error !== "") {
            errorDiv.innerHTML = error;
            errorDiv.style.display = "block";
            return false; // Evita que el formulario se envíe
        }

        return true; // Permite que el formulario se envíe
    }
</script>

    
    </head>
    <body>
        
        
        <div class="form-container">
           <h1>Editar Doctor</h1> 
        <form action="MainServlet1?action=actualizar" method="post"onsubmit="return validarFormulario()">
              <div id="errorDiv" style="display: none; color: red; margin-bottom: 10px;"></div>
            <input type="hidden" name="action" value="actualizar">
            <input type="hidden" name="id" value="${doctor.id}">
            Especialidad:<select name="nombre_especialidad" id="especialidad">
                <option value="">Seleccione una especialidad</option>
                <c:forEach var="especialidad" items="${especia}">
                    <option value="${especialidad}" <c:if test="${especialidad == doctor.nombreEspecialidad}">selected</c:if>>${especialidad}</option>
                    
                </c:forEach>
            </select>
            
            <br>
            Nombre: <input type="text" name="firstname" value="${doctor.firstname}"><br>
            Apellido: <input type="text" name="lastname" value="${doctor.lastname}"><br>
            DNI: <input type="text" name="dni" value="${doctor.dni}"><br>
            Código: <input type="text" name="codi" value="${doctor.codi}"><br>
            <button type="submit">Actualizar</button>
        </form>
            </div>
    </body>
</html>

