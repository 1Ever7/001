<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inicio de Carga</title>
    <style>
        /* Estilos para el corazón */
        .heart-container {
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
        }

        .heart {
            animation: pulse 1s infinite;
            color: red;
            font-size: 48px;
        }
        
        /* Animación del latido */
        @keyframes pulse {
            0% {
                transform: scale(1);
            }
            50% {
                transform: scale(1.3);
            }
            100% {
                transform: scale(1);
            }
        }
        
        /* Estilos generales */
        body, html {
            height: 100%;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: #add8e6; /* Fondo celeste */
        }
        
        
    </style>
</head>
<body>
    <div class="heart-container">
        <div class="heart">❤️</div>
    </div>
    <script>
        // Espera 2 segundos y luego redirige a index.jsp
        setTimeout(function() {
            window.location.href = "index2.jsp";
        }, 2000);
    </script>
</body>
</html>
