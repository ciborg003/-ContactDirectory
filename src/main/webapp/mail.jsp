<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap-grid.css">
        <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="css/mail.css">
        <link rel="stylesheet" type="text/css" href="css/navbar.css">
        <link rel="stylesheet" type="text/css" href="css/header.css">
        <title>JSP Page</title>
    </head>
    <body>
        <!--        <header class="page-header">
                    <h1><a href="http://localhost:8080/ServletController/app">Contact Directory</a></h1>
                </header>-->
        <jsp:include page="navbar.jsp" />
        <h1 class="h1">Send Mail</h1>
        <form method="GET" action="ServletController">
            <input type="hidden" name="action" value="sendEmail">
            <div class="container">
                <div class="row ">
                    <div class="col-md-1">
                        <label>Add email:</label>
                    </div>
                    <div class="col-md-11">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <button type="button" class="btn btn-info" onclick="addEmail()">
                                    <span class="fa fa-save" />
                                </button>
                            </span>
                            <input id="attachEmail" type="text" class="form-control">
                        </div>
                    </div>
                </div>

                <ul class="list-group">
                    <div id="emailList" class="row">
                        <c:forEach items="${contactList}" var="contact">
                            <div class="col-md-4">
                                <li class="list-group-item">
                                    ${contact.email}
                                    <button type="button" class="btn btn-info button-add" onclick="deleteEmail(this)">
                                        <span class="fa fa-remove" />
                                    </button>
                                    <input type="hidden" name="email" value="${contact.email}" />
                                </li>
                            </div>
                        </c:forEach>
                    </div>
                </ul>

                <div class="row">
                    <div class="col-md-6">
                        <label>Title:</label>
                        <div class="form-group">
                            <input class="form-control" type="text" name="title">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <label>Template:</label>
                        <select class="form-control">
                            <option>None</option>
                            <option>Template 1</option>
                            <option>Template 2</option>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <label>Message:</label>
                    <textarea class="form-control" rows="3" name="mailContent"></textarea>
                </div>
                <div class="row">
                    <div class="form-group">
                        <button class="btn btn-info" type="button" onclick="sendMail()"><span class="fa fa-send" /></button>
                    </div>
                </div>
            </div>
        </form>
        <script type="text/javascript" src="js/validation.js" />
        <script type="text/javascript" src="js/mail.js" />
    </body>
</html>
