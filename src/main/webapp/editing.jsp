<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/editing.css">
        <link rel="stylesheet" type="text/css" href="css/popup.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css">
        <link type="text/javascript" href="js/editing.js">
        <title>User Page</title>
    </head>
    <body>
        <header class="page-header">
            <h1><a href="http://localhost:8080/ServletController/app" class="">Contact Directory</a></h1>
        </header>

        <div id="wrap">
            <div id="popupPhone">
                <h4>Editing Phone Number</h4>
                <div class="input-group">
                    <input id="cCode" type="text" class="form-control col-lg-1" placeholder="CountryCode">
                    <input id="oCode" type="text" class="form-control col-lg-1" placeholder="OperatorCode">
                    <input id="pNumber" type="text" class="form-control col-lg-1" placeholder="PhoneNumber">
                </div>
                <label class="form-control-label" for="formGroupExampleInput2">
                    Phone Type: 
                </label>
                <div class="form-check form-check-inline">
                    <label class="form-check-label">
                        <input id="mobileType" class="form-check-input" 
                               type="radio" name="inlineRadioOptions" 
                               id="inlineRadio1" value="option1" checked="true"> 
                        Mobile
                    </label>
                    <label class="form-check-label">
                        <input id="homeType" class="form-check-input" 
                               type="radio" name="inlineRadioOptions" 
                               id="inlineRadio2" value="option2"> 
                        Home
                    </label>

                </div>
                <label class="form-control-label" for="formGroupExampleInput2">
                    Comment:
                </label><span class="badge">Max length 255 symbols</span>
                <div id="phoneComment">
                    <textarea id="comment" rows="5" cols="55"></textarea>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary" onclick="savePhone();">Save</button>
                    <button type="button" class="btn" onclick="showPhonePopup('none');">Cancel</button>
                </div>
            </div>
            <div id="popupAttachment">
                <h4>Editing Attachment</h4>
                <div class="input-group">
                    <label class="form-control-label">Select File:</label>
                    <input id="file" name="data" type="file"><br>
                </div>
                <label class="form-control-label" for="formGroupExampleInput2">
                    Comment:
                </label><span class="badge">Max length 255 symbols</span>
                <div id="attachmentComment">
                    <textarea id="comment" rows="5" cols="55"></textarea>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary" onclick="saveAttachment();">Save</button>
                    <button type="button" class="btn" onclick="showAttachmentPopup('none');">Cancel</button>
                </div>
            </div>
        </div>

        <h1>Editing Contact</h1>        
        <form id="form" method="GET">
            <div id="save">
                <button type="submit" class="btn btn-primary btn-md" padding = 20px onclick="saveAttachment()">
                    Save Contact
                </button>
            </div>
            <input type="image" src="photos/noimage.jpg"  alt="photo" class="thumbnail">
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
                <span class="input-group-addon">Birthday</span>
                <input type="date" class="form-control" placeholder="FirstName">
            </div>
            <label class="form-control-label" for="formGroupExampleInput2">
                Gender: 
            </label>
            <div class="form-check form-check-inline">
                <label class="form-check-label">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"> Male
                </label>
                <label class="form-check-label">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2"> Female
                </label>
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
                <span class="input-group-addon">Web Site: </span>
                <input type="text" class="form-control" placeholder="Web Site">
            </div>
            <div class="input-group">
                <span class="input-group-addon">Email: </span>
                <input type="text" class="form-control" placeholder="Email">
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

        <div id="editingPhones">
            <div id="phoneActions">
                <button type="button" class="btn btn-info btn-md" onclick="showPhonePopup('block')">Add</button>
                <button type="button" class="btn btn-info btn-md">Delete</button>
                <button type="button" class="btn btn-info btn-md">Edit</button>
            </div>

            <table class="table table-bordered">
                <thead>
                    <tr>
                        <td>CheckBox</td>
                        <td>Phone Number</td>
                        <td>Phone Type</td>
                        <td>Comment</td>
                    </tr>
                </thead>
                <tbody  id="phoneList"class="table table-bordered">
                </tbody>
            </table>
        </div>
        <div id="editingAttachments">
            <div id="attachmentActions">
                <button type="button" class="btn btn-info btn-md" 
                        onclick="showAttachmentPopup('block');">Add</button>
                <button type="button" class="btn btn-info btn-md">Delete</button>
                <button type="button" class="btn btn-info btn-md">Edit</button>
            </div>

            <table class="table table-bordered">
                <thead>
                    <tr>
                        <td>File Name</td>
                        <td>Load Date</td>
                        <td>Comment</td>
                    </tr>
                </thead>
                <tbody id="attachmentList">

                </tbody>
            </table>
        </div>
        <script src="js/editing.js" />
    </body>
</html>
