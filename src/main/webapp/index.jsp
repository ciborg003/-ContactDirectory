<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="css/index.css">
        <link rel="stylesheet" type="text/css" href="css/editing.css"
              <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css">
        <title>User page</title>
    </head>
    <body>
        <header class="page-header">
            <h1>Contact Directory</h1>
            <div class="ctrlBtn">
                <a href="http://localhost:8080/ServletController/search.jsp">
                    <button class="btn btn-search" type="button">
                        <i class="fa fa-search fa-fw"></i> Search
                    </button>
                </a>
            </div>
        </header>

        <h6>Record on the page:</h6>
        <select>
            <option>10</option>
            <option>20</option>
        </select>

        <h6>Page:</h6>
        <select>
            <option>10</option>
            <option>20</option>
        </select>

        <div class="ctrlBtn btn-group">
            <a href="http://localhost:8080/ServletController/editing.jsp">
                <button class="btn btn-info">Add</button>
            </a>
            <button class="btn btn-info">Send mail</button>
        </div>
        <div class="container" id="table">
            <table class="table table-bordered">
                <c:forEach var="contact" items="${contactList}">
                    <tr>
                        <td valign><input type="checkbox"></td>
                        <td>
                            <a href="#">Full Name: ${contact.name} ${contact.surname}  
                                ${contact.patronymic}</a>
                            <h6>Birthday: ${contact.dob}</h6>
                            <h6>Current job: ${contact.job}</h6>
                        </td>
                        <td>
                            <div class="ctrlBtn btn-group">
                                <button class="btn btn-info">Edit</button>
                                <button class="btn btn-info">Delete</button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>

