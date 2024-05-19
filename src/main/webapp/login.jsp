<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        body {
             background-image: url('imagenes/ani.jpeg');
            background-size: cover; /* Ajusta la imagen al tamaño de la ventana */
            background-repeat: no-repeat; /* Evita la repetición de la imagen */
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .login-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            width: 100%;
            text-align: center;
        }

        h2 {
            margin-top: 0;
            color: #333;
        }

        label {
            font-weight: bold;
            color: #666;
        }

        input[type="text"],
        input[type="password"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin: 8px 0;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        input[type="submit"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin: 8px 0;
            border: none;
            border-radius: 4px;
            background-color: #4CAF50;
            color: white;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        .error-message {
            color: red;
            font-size: 14px;
            margin-top: 8px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>Login</h2>
        <form action="loginservlet" method="post">
            <label for="username">Usuario:</label><br>
            <input type="text" id="username" name="username"><br>
            <label for="password">Contraseña:</label><br>
            <input type="password" id="password" name="password"><br><br>
            <input type="submit" value="Login">
        </form>
        <% if (request.getParameter("error") != null && request.getParameter("error").equals("true")) { %>
            <p class="error-message">Credenciales incorrectas</p>
        <% } %>
    </div>
</body>
</html>
