<%--
  Created by IntelliJ IDEA.
  User: angry
  Date: 22/08/2023
  Time: 08:47 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>User</title>
    <jsp:include page="/layouts/head.jsp"/>

</head>
<body>

<nav class="navbar" style="background-color: #e3f2fd;">
    <div class="container-fluid">
        <a class="navbar-brand">Usuario</a>
        <form class="d-flex" role="search" action="/api/auth/logout">
            <button class="btn btn-outline-success" type="submit">Salir</button>
        </form>
    </div>
</nav>
<div class="card translate-m" style="width: 18rem;">
    <div class="card-body justify-content-center">
        <h5 class="card-title">Registra tu falta o Retardo</h5>
        <p class="card-text">Registra AQUI tu falta o retardo.</p>
        <form class="d-flex" role="search" action="/leak/view-create">
            <button class="btn btn-outline-success" type="submit">Registro</button>
        </form>
    </div>
</div>

<div class="container-fluid ">
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
                            </tr>
                        </c:forEach>
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
