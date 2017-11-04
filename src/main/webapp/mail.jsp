<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="navbar.jsp" />
        <h1 class="h1">Send Mail</h1>
        <div class="main-container">
            <form method="POST" action="ServletController">
                <input type="hidden" name="action" value="sendEmail">
                <div class="container">
                    <div class="row ">
                        <div class="col-md-12">
                            <div class="input-group">
                                <span class="span-input_group-email input-group-addon">
                                    <button type="button" class="button-input_group-email btn btn-info" onclick="addEmail()">
                                        Add email
                                    </button>
                                </span>
                                <input id="attachEmail" type="text" class="form-control">
                            </div>
                        </div>
                    </div>

                    <ul class="list-group">
                        <div id="emailList" class="row">
                            <c:forEach items="${contactList}" var="contact">
                                <div style="flex-direction: row" class="col-md-4">
                                    <li class="list-group-item">${contact.email}
                                        <button style="margin-left: auto; margin-right: 0em;" type="button" class="btn btn-info " onclick="deleteEmail(this)">
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
                            <select class="form-control" name="msgTemplate" onchange="changeTemplate()">
                                <option>None</option>
                                <c:forEach items="${templates}" var="template">
                                    <option>${template.msgName}</option>
                                </c:forEach>
                            </select>
                            <c:forEach items="${templates}" var="template">
                                <input type="hidden" value="${template.msg}" id="${template.msgName}">
                            </c:forEach>
                        </div>
                    </div>
                    <div class="row">
                        <label>Message:</label>
                        <textarea class="form-control" rows="3" name="mailContent"></textarea>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <button class="btn btn-info button-red" type="button" onclick="sendMail()"><span class="fa fa-send" /></button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap-grid.css">
        <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="css/mail.css">
        <link rel="stylesheet" type="text/css" href="css/navbar.css">
        <link rel="stylesheet" type="text/css" href="css/button-style.css">
        <script type="text/javascript" src="js/mail.js"></script>
        <script type="text/javascript" src="js/validation.js"></script>
    </body>
</html>
