<%--
  Created by IntelliJ IDEA.
  User: aldri
  Date: 14/08/2023
  Time: 01:50 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Student</title>
    <jsp:include page="/layouts/head.jsp"/>
</head>
<body>
<nav class="navbar" style="background-color: #e3f2fd;">
    <div class="container-fluid">
        <a class="navbar-brand">Encargado</a>
        <form class="d-flex" role="search" action="/api/auth/logout">
            <button class="btn btn-outline-success" type="submit">Salir</button>
        </form>
    </div>
</nav>


<div class="container-fluid">
    <div class="row">
        <div class="col text-center mt-5">
            <h2>Carga o incidencia</h2>
            <div class="card">
                <div class="card-header">
                    <div class="row">
                        <div class="col">Listado de justificaciones</div>
                    </div>
                </div>
                <div class="card-body">
                    <table class="table table-stripped">
                        <thead>
                        <th>#</th>
                        <th>Nombre</th>
                        <th>Titulo</th>
                        <th>Descripcion</th>
                        <th>Estado</th>
                        <th>Tipo</th>
                        <th>Acciones</th>
                        </thead>
                        <tbody>
                        <c:forEach var="leak" items="${leaks}" varStatus="s">
                            <tr>
                                <td>
                                    <c:out value="${s.count}"/>
                                </td>
                                <td>
                                     <c:out value="${leak.name}"/>
                                </td>
                                <td>
                                    <c:out value="${leak.title}"/>
                                </td>
                                <td>
                                    <c:out value="${leak.description}"/>
                                </td>
                                <td>
                                    <c:out value="${leak.status}"/>
                                </td>
                                <td>
                                    <c:out value="${leak.tipo}"/>
                                </td>
                                <td>
                                    <form method="post" action="/leak/accept">
                                        <input hidden value="${leak.id}" name="id">
                                        <button type="submit"
                                                class="btn btn-outline-warning btn-sm">
                                            Aceptar
                                        </button>
                                    </form>
                                    <form method="post" action="/leak/decline">
                                        <input hidden value="${leak.id}" name="id">
                                        <button type="submit"
                                                class="btn btn-outline-danger btn-sm">
                                            Rechazar
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="6">
                                Sin registros
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/layouts/footer.jsp"/>
</body>
</html>
