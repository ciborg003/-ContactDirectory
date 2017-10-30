<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="css/search.css">
        <link rel="stylesheet" type="text/css" href="css/navbar.css">
        <link rel="stylesheet" type="text/css" href="css/header.css">
        <link rel="stylesheet" type="text/css" href="css/button-style.css">
        <title>Search</title>
    </head>

    <body>
        
        <jsp:include page="navbar.jsp" />
        <h1 class="h1">Contact searching</h1>

        <form id="form" method="GET" action="ServletController">
            <input type="hidden" name="action" value="search">

            <div class="main-container-info">
                <div class="container-info">
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
                    <div class="input-group">
                        <span class="input-group-addon">Gender</span>
                        <select class="form-control" name="gender" value="${contact.gender}">
                            <option selected="">Any</option>
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
                </div>
                <div class="container-info">

                    <div class="input-group">
                        <span class="input-group-addon">Family State</span>
                        <select class="form-control" name="familyState">
                            <option selected="">Any</option>
                            <c:forEach items="${familyStates}" var="familyState">
                                <option>${familyState}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon" name="job">Current Work: </span>
                        <input type="text" class="form-control" placeholder="Current Work">
                    </div>
                    <div class="input-group">
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
                </div>
            </div>
            <div id="search">
                <button type="submit" class="button-red btn btn-primary btn-md" padding=2 0px>
                    Search
                </button>
            </div>
        </form>

    </body>

</html>
