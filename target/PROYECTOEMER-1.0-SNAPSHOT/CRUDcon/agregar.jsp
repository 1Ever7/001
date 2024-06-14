
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
    <script>
        function seleccionarPaciente() {
            var select = document.getElementById("selectPaciente");
            var selectedOption = select.options[select.selectedIndex];
            document.getElementById("firstname").value = selectedOption.dataset.firstname;
            document.getElementById("lastname").value = selectedOption.dataset.lastname;
            document.getElementById("pacienteId").value = selectedOption.value;
        }
    </script>
</head>
<body>

<h1>Detalles de la Consulta</h1>
<form action="MainServlet3?action=insertar" method="post">
    <input type="hidden" name="id" value="${detalles.id}">
    <input type="hidden" name="medicalConsultaId" value="${detalles.medicalConsultaId}">
    <input type="hidden" name="detalleConsultaId" value="${detalles.detalleConsultaId}">
    <input type="hidden" name="action" value="insertar">

    <!-- Lista desplegable de pacientes -->
    <label for="selectPaciente">Seleccione un paciente:</label>
    <select id="selectPaciente" onchange="seleccionarPaciente()">
        <option value="">Seleccione un paciente</option>
        <c:forEach var="paciente" items="${pacientes}">
            <option value="${paciente.id}" data-firstname="${paciente.firstname}" data-lastname="${paciente.lastname}" >
                ${paciente.firstname} ${paciente.lastname}
            </option>
        </c:forEach>
    </select><br>

    
    
    <!-- Campos de formulario autocompletados -->
    Nombre del Paciente: <input type="text" id="firstname" name="firstname" readonly><br>
    Apellido del Paciente: <input type="text" id="lastname" name="lastname" readonly><br>

    <!-- Otros campos del formulario -->
   Especialidad:
            <select name="nombre_especialidad">
                <option value="">Seleccione una especialidad</option>
                <c:forEach var="especialidad" items="${especialidades}">
                    <option value="${especialidad}">${especialidad}</option>
                </c:forEach>
            </select><br>
    Fecha de Creación: <input type="Date" name="createdate"><br>
    Diagnóstico: <input type="text"  name="diagnostic"><br>
    Tratamiento:<input type="text" name="treatment"><br>
    Celular:<input type="text" name="celular"><br>
    Analisis IA:<textarea type="text" name="analisis_medico"></textarea><br>

    <button type="submit">Añadir Nueva Consulta</button>
</form>

</body>
</html>
