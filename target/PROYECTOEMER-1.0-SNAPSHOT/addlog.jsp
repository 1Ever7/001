<%@page import="com.emergentes.modelos.login"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Usuarios</title>
    <style>
        body {
             background-image: url('imagenes/ani.jpeg');
            background-size: cover; /* Ajusta la imagen al tamaño de la ventana */
            background-repeat: no-repeat; /* Evita la repetición de la imagen */
            font-family: 'Helvetica Neue', Arial, sans-serif;
            background-color: #f0f8ff; /* AliceBlue for a calm and professional look */
            margin: 0;
            padding: 20px;
        }

        h1 {
            color: #2c3e50; /* Darker shade of blue for the heading */
            text-align: center;
        }

        a {
            display: inline-block;
            margin-bottom: 20px;
            padding: 10px 20px;
            background-color: #3498db; /* Medical blue */
            color: white;
            text-decoration: none;
            border-radius: 4px;
            text-align: center;
        }

        a:hover {
            background-color: #2980b9; /* Slightly darker blue on hover */
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #3498db; /* Medical blue */
            color: white;
        }

        tr:nth-child(even) {
            background-color: #eaf2f8; /* Light blue for even rows */
        }

        tr:hover {
            background-color: #d1e0e5; /* Slightly darker blue on hover */
        }

        .actions a {
            margin-right: 10px;
            padding: 5px 10px;
            color: white;
            border-radius: 4px;
            text-decoration: none;
        }

        .edit {
            background-color: #f1c40f; /* Bright yellow for edit */
        }

        .edit:hover {
            background-color: #d4ac0d; /* Slightly darker yellow on hover */
        }

        .delete {
            background-color: #e74c3c; /* Red for delete */
        }

        .delete:hover {
            background-color: #c0392b; /* Darker red on hover */
        }
    </style>
    
    
     <script>
        function confirmarEliminacion() {
            return confirm("¿Está seguro de que desea eliminar este doctor?");
        }
        
// Function to validate form fields
function validateForm() {
    // Get the values from the input fields
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;
    var role = document.getElementById('role').value;

    // Check if username is not empty
    if(username.trim() === '') {
        alert('El campo de usuario es obligatorio.');
        return false;
    }

    // Check if password meets length requirements
    if(password.length < 6) {
        alert('La contraseña debe tener al menos 6 caracteres.');
        return false;
    }

    // Check if role is selected (assuming it's a dropdown)
    if(role === '') {
        alert('Por favor, seleccione un rol para el usuario.');
        return false;
    }

    // Add more validation checks as needed

    // If all checks pass, return true to allow form submission
    return true;
}

// Attach the validateForm function to the form's onsubmit event
document.getElementById('yourFormId').onsubmit = validateForm;

    </script>
    
</head>
<body>
    <h1>Lista de Usuarios</h1>
    <a href="userservlet?action=new">Nuevo Usuario</a>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Usuario</th>
                <th>Contraseña</th>
                <th>Rol</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.password}</td>
                    <td>${user.role}</td>
                    <td class="actions" >
                        <a href="userservlet?action=edit&id=${user.id}" class="edit" onclick="return validateEditForm();">Editar</a>
                       <c:if test="${user.role != 'Admin'}" >
                            <a href="userservlet?action=delete&id=${user.id}" class="delete" onclick="return confirmarEliminacion()">Eliminar</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
