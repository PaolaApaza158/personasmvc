<%@page import="com.emergentes.modelo.Persona"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Persona item = (Persona) request.getAttribute("miPersona");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1><%= (item.getId() == 0) ? "Nuevo registro" : "Editar registro" %></h1>
        <form action="MainController" method="post">
            <input type="hidden" name="id" value="<%= item.getId()%>"/>
            <table>
                
                <tr>
                    <td>Nombres</td>
                    <td><input type="text" name="nombres"></td>
                </tr>                
                                <tr>
                    <td>Apellidos</td>
                    <td><input type="text" name="apellidos"></td>
                </tr>
                                <tr>
                    <td>Edad</td>
                    <td><input type="number" name="edad"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Enviar"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
