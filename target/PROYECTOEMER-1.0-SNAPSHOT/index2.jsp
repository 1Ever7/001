<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Menú Principal</title>
        <link href="misEstilos.css" rel="stylesheet" type="text/css"/>
        <style>

            .cropped-image {
                width: 150px;
                height: 150px;

            }


            .image-container {
                overflow: hidden;
                width: 150px;
                height: 150px;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            .rotating-image {
                width: 100%;
                height: 100%;
                object-fit: cover;
                clip-path: polygon(
                    50% 0%, 
                    100% 25%, 
                    100% 100%, 
                    0% 100%, 
                    0% 25%
                    );
                animation: rotate 10s linear infinite;
            }

            @keyframes rotate {
                from {
                    transform: rotateY(0deg);
                }
                to {
                    transform: rotateY(360deg);
                }
            }
            .login-container {
              position: absolute; /* Establecer posición absoluta */
            bottom: 10px; /* Alinear al fondo */
            left: 10px; /* Alinear a la izquierda */

        }
        </style>

        <script>
            function cargarPagina(url) {
                var iframe = document.getElementById("iframe");
                iframe.src = url;
            }
        </script>

    </head>
    <body>


        <div class="container" >
            <div class="linea-vertical-container">
                <div class="linea-vertical"></div>
                <div class="fondo-color"></div>
            </div>

            <div class="menu">
                <div class="image-container">
                    <img src="imagenes/logo.png" alt="Imagen sin fondo" class="rotating-image">
                </div>
                <h9>Menú Principal</h9>
                <ul>
                   <%-- <li><a href="javascript:cargarPagina('${pageContext.request.contextPath}/MainServlet1?action=doctor')">Administrar Doctores</a></li>--%>
                    <%--<li><a href="javascript:cargarPagina('${pageContext.request.contextPath}/MainServlet2?action=paciente')">Administrar Pacientes</a></li>--%>
                    <%--<li><a href="javascript:cargarPagina('${pageContext.request.contextPath}/MainServlet3?action=consulta')">Administrar Consultas Médicas</a></li>--%>
                    <%--<li><a href="javascript:cargarPagina('${pageContext.request.contextPath}/MainServlet4?action=especie')">Especialidades</a></li>--%>
                    <li><a href="javascript:cargarPagina('${pageContext.request.contextPath}/api/consultasIA.jsp')">Consulta a IA</a></li>
                  <%-- <li><a href="javascript:cargarPagina('${pageContext.request.contextPath}/userservlet')">Agregar Usuario pacientes-doctor</a></li>--%>
                   
                </ul>
                <!-- Contenedor adicional para la línea y el fondo de color -->

            </div>
            <div class="contenido" >
                <iframe id="iframe" name="iframe" src="" width="100%" height="100%" style="background-image: url('../imagenes/ani.jpeg');"></iframe>
                  
            </div>
                
               
        </div>
          <div class="login-container">
            <a href="login.jsp" class="login-button">login Hospital</a>
        </div>

    </body>
</html>
