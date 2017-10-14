<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="css/search.css">
        <link rel="stylesheet" type="text/css" href="css/navbar.css">
        <link rel="stylesheet" type="text/css" href="css/header.css">
        <title>Search</title>
    </head>
    <body>
        <!--        <header class="page-header">
                    <h1><a href="http://localhost:8080/ServletController/m" class="">Contact Directory</a></h1>
                </header>-->
        <jsp:include page="navbar.jsp" />
        <h1 class="h1">Contact searching</h1>

        <form id="form" method="GET" action="ServletController">
            <input type="hidden" name="action" value="search">
            <div id="search">
                <button type="submit" class="btn btn-primary btn-md" padding = 20px>
                    Search
                </button>
            </div>
            <div class="input-group">
                <span class="input-group-addon">FirstName</span>
                <input type="text" name="fName" class="form-control" placeholder="FirstName">
            </div>

            <div class="input-group">
                <span class="input-group-addon">LastName</span>
                <input type="text" name="lName" class="form-control" placeholder="LastName">
            </div>
            <div class="input-group">
                <span class="input-group-addon">Patronymic</span>
                <input type="text" name="patronymic" class="form-control" placeholder="Patronymic">
            </div>
            <div class="input-group">
                <span class="input-group-addon">From</span>
                <input type="date" name="birthdayFrom" class="form-control" placeholder="FirstName">
                <span class="input-group-addon">To</span>
                <input type="date" name="birthdayTo" class="form-control" placeholder="FirstName">
            </div>
            <label class="form-control-label">
                Gender: 
            </label>
            <div class="form-group">
                <select class="form-control" name="gender" value="${contact.gender}">
                    <c:forEach items="${genders}" var="gender">
                        <option>${gender}</option>
                    </c:forEach>
                </select>
                <br>
            </div>
            <div class="input-group">
                <span class="input-group-addon">Nationality: </span>
                <input type="text" name="nation" class="form-control" placeholder="Nationality">
            </div>
            <label class="form-control-label" for="formGroupExampleInput2">
                Family State: 
            </label>
            <div class="form-group">
                <select class="form-control" name="familyState">
                    <c:forEach items="${familyStates}" var="familyState">
                        <option>${familyState}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="input-group">
                <span class="input-group-addon" name="job">Current Work: </span>
                <input type="text" class="form-control" placeholder="Current Work">
            </div><div class="input-group">
                <span class="input-group-addon" name="country">Country: </span>
                <input type="text" name="country" class="form-control" placeholder="Country">
            </div>
            <div class="input-group">
                <span class="input-group-addon">City: </span>
                <input type="text" name="city" class="form-control" placeholder="City">
            </div>
            <div class="input-group">
                <span class="input-group-addon">Street, House, Room:</span>
                <input type="text" name="streetHouseRoom" class="form-control" placeholder="Street, House, Room">
            </div>
            <div class="input-group">
                <span class="input-group-addon">Index: </span>
                <input type="text" name="index" class="form-control" placeholder="Index">
            </div>
        </form>

    </body>
</html>
