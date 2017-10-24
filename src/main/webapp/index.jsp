<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="css/index.css">
        <link rel="stylesheet" type="text/css" href="css/editing.css">
        <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="css/navbar.css">
        <link rel="stylesheet" type="text/css" href="css/header.css">
        <title>User page</title>
    </head>
    <body>
        <!--        <header class="page-header">
                    <h1>Contact Directory</h1>
                    <div class="ctrlBtn">
                        <button class="btn btn-search" type="button" onclick="searchPage()">
                            <span class="fa fa-search" />Search
                        </button>
                    </div>
                </header>-->
        <jsp:include page="navbar.jsp" />

        <form id="contacts" action="ServletController" method="GET">
            <input type="hidden" name="action" value="${action}">
            <h6>Record on the page:</h6>
            <select name="recordsOnPage" id="recordsOnPage" 
                    onchange="calcPagesAmount(${recordsCount});">
                <option>10</option>
                <option>20</option>
            </select>

            <h6>Page:</h6>
            <select name="pageNumber" id="pagesCount">
            </select>
            <button type="button" class="btn btn-sm" onclick="changePage()">Go</button>

            <div class="container" id="table">
                <div class="btn-group">
                    <button type="button" class="btn btn-info" onclick="addNewContact()">Add</button>
                    <button class="btn btn-info" type="button" onclick="sendMails()">Send mail</button>
                    <button class="btn btn-info" type="button" onclick="deleteContact()()">Delete</button>
                </div>
                <table class="table table-striped">
                    <c:forEach var="contact" items="${contactList}">
                        <tr id="${contact.id}">
                            <td valign>
                                <!--<input type="checkbox" onchange="checkBoxAction(this)">-->
                                <label class="custom-control custom-checkbox mb-2 mr-sm-2 mb-sm-0">
                                    <input type="checkbox" class="custom-control-input" onchange="checkBoxAction(this)" >
                                    <span class="custom-control-indicator"></span>
                                </label>
                            </td>
                            <td>
                                <a href="?action=updateContact&contactID=${contact.id}">Full Name: ${contact.name} ${contact.surname}  
                                    ${contact.patronymic}</a>
                                <h6>Birthday: ${contact.dob}</h6>
                                <h6>Current job: ${contact.job}</h6>
                            </td>
                            <td>
                                <div class="ctrlBtn btn-group">
                                    <button class="btn btn-info" 
                                            onclick="editContact(this)">Edit</button>
                                    <button class="btn btn-info" onclick="deleteContact(this)">Delete</button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </form>
        <script src="js/index.js"></script>
        <script type="text/javascript">
            loadPage(${recordsCount},${pageNumber},${recordsOnPage});
        </script>
    </body>
</html>