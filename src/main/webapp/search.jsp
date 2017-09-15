<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="css/search.css">
        <title>Search</title>
    </head>
    <body>
        <header class="page-header">
            <h1><a href="http://localhost:8080/ServletController/app" class="">Contact Directory</a></h1>
        </header>
        <h2>Contact searching</h2>

        <form id="form" method="GET">
            <div id="search">
                <button type="submit" class="btn btn-primary btn-md" padding = 20px onclick="show('block')">
                    Search
                </button>
            </div>
            <div class="input-group">
                <span class="input-group-addon">FirstName</span>
                <input type="text" class="form-control" placeholder="FirstName">
            </div>

            <div class="input-group">
                <span class="input-group-addon">LastName</span>
                <input type="text" class="form-control" placeholder="LastName">
            </div>
            <div class="input-group">
                <span class="input-group-addon">Patronymic</span>
                <input type="text" class="form-control" placeholder="Patronymic">
            </div>
            <div class="input-group">
                <span class="input-group-addon">From</span>
                <input type="date" class="form-control" placeholder="FirstName">
                <span class="input-group-addon">To</span>
                <input type="date" class="form-control" placeholder="FirstName">
            </div>
            
            <div class="input-group">
                <span class="input-group-addon">Nationality: </span>
                <input type="text" class="form-control" placeholder="Nationality">
            </div>
            <div class="input-group">
                <span class="input-group-addon">Family State: </span>
                <input type="text" class="form-control" placeholder="Family State">
            </div>
            <div class="input-group">
                <span class="input-group-addon">Current Work: </span>
                <input type="text" class="form-control" placeholder="Current Work">
            </div><div class="input-group">
                <span class="input-group-addon">Country: </span>
                <input type="text" class="form-control" placeholder="Country">
            </div>
            <div class="input-group">
                <span class="input-group-addon">City: </span>
                <input type="text" class="form-control" placeholder="City">
            </div>
            <div class="input-group">
                <span class="input-group-addon">Street, House, Room:</span>
                <input type="text" class="form-control" placeholder="Street, House, Room">
            </div>
            <div class="input-group">
                <span class="input-group-addon">Index: </span>
                <input type="text" class="form-control" placeholder="Index">
            </div>
        </form>
        
    </body>
</html>
