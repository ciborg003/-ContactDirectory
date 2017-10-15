<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="css/navbar.css" />
        <link rel="stylesheet" type="text/css" href="css/header.css">
        <link rel="stylesheet" type="text/css" href="css/editing.css">
        <link rel="stylesheet" type="text/css" href="css/popup.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css">
        <title>User Page</title>
    </head>
    <body>
        <div id="wrap">
            <input type="hidden" id="popUpAction" value="none">
            <div id="popupPhone">
                <div class="container-fluid">
                    <h4>Editing Phone Number</h4>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-3">
                                <input id="cCode" type="text" class="form-control" placeholder="+375">
                            </div>
                            <div class="col-md-2">
                                <input id="oCode" type="text" class="form-control" placeholder="29">
                            </div>
                            <div class="col-md-7">
                                <input id="pNumber" type="text" class="form-control" placeholder="1234567">
                            </div>
                        </div>
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
                    <br>
                    <label class="form-control-label" for="formGroupExampleInput2">
                        Comment:
                    </label><span class="badge badge-info">Max length 255 symbols</span>
                    <div id="comment">
                        <textarea class="form-control" id="phoneComment" rows="5"></textarea>
                    </div>
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary" onclick="savePhone();">Save</button>
                        <button type="button" class="btn" onclick="showPhonePopup('none');">Cancel</button>
                    </div>
                </div>
            </div>
            <div id="popupAttachment">
                <h4>Editing Attachment</h4>
                <div class="container">
                    <div class="form-group">
                        <!--                        <div class="col-md-auto">
                                                    <label class="form-control-label">Select File:</label>
                                                </div>
                                                <div class="col-md-auto">
                                                    <input type="file" name="data" id="file" class="form-control">
                                                </div>-->
                        <div class="form-group">
                            <label for="file">Select file:</label>
                            <input type="file" name="data" class="form-control-file" id="file">
                        </div>
                    </div>
                </div>
                <br>
                <label class="form-control-label" for="formGroupExampleInput2">
                    Comment:
                </label><span class="badge badge-info">Max length 255 symbols</span>
                <div id="comment">
                    <textarea class="form-control" id="attachmentComment" rows="5"></textarea>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary" onclick="saveAttachment();">Save</button>
                    <button type="button" class="btn" onclick="showAttachmentPopup('none');">Cancel</button>
                </div>
            </div>
        </div>

        <jsp:include page="navbar.jsp" />

        <h1 class="h1">Editing Contact</h1>       
        <form id="form" action="ServletController" method="POST" enctype="multipart/form-data">
            <input type="hidden" value="${action}" name="action" />
            <input type="hidden" name="contactId" value="${contact.id}"/>
            <div id="save">
                <button type="button" class="btn btn-primary btn-md" padding = 20px onclick="saveContact()">
                    Save Contact
                </button>
            </div>
            <input type="image" src="photos/noimage.jpg"  alt="photo" class="thumbnail">
            <div class="input-group">
                <span class="input-group-addon">FirstName</span>
                <input name="fName" type="text" class="form-control" 
                       placeholder="FirstName" value="${contact.name}">
            </div>

            <div class="input-group">
                <span class="input-group-addon">LastName</span>
                <input name="lName" type="text" class="form-control" 
                       placeholder="LastName" value="${contact.surname}">
            </div>
            <div class="input-group">
                <span class="input-group-addon">Patronymic</span>
                <input name="patronymic" type="text" class="form-control" 
                       placeholder="Patronymic" value="${contact.patronymic}">
            </div>
            <div class="input-group">
                <span class="input-group-addon">Birthday</span>
                <input name="birthday" type="date" class="form-control"
                       value="${contact.dob}">
            </div>
            <label class="form-control-label" for="formGroupExampleInput2">
                Gender: 
            </label>
            <div class="form-group">
                <select class="form-control" name="gender" value="${contact.gender}">
                    <c:forEach items="${genders}" var="gender">
                        <<c:set var="option" value="${gender}"/>
                        <c:set var="sex" value="${contact.gender}"/>
                        <c:if test="${option==sex}">
                            <option selected>${gender}</option>
                        </c:if>
                        <c:if test="${option!=sex}">
                            <option>${gender}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <br>
            </div>
            <div class="input-group">
                <span class="input-group-addon">Nationality: </span>
                <input name="nation" type="text" class="form-control" 
                       value="${contact.nationality}" placeholder="Nationality">
            </div>
            <label class="form-control-label" for="formGroupExampleInput2">
                Family State: 
            </label>
            <div class="form-group">
                <select class="form-control" name="familyState">
                    <c:forEach items="${familyStates}" var="familyState">
                        <c:set var="option" value="${familyState}"/>
                        <c:set var="fState" value="${contact.familyState}"/>
                        <c:if test="${option==fState}">
                            <option selected>${familyState}</option>
                        </c:if>
                        <c:if test="${option!=fState}">
                            <option>${familyState}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
            <div class="input-group">
                <span class="input-group-addon">Web Site: </span>
                <input name="webSite" type="text" class="form-control" 
                       value="${contact.webSite}" placeholder="Web Site">
            </div>
            <div class="input-group">
                <span class="input-group-addon">Email: </span>
                <input name="email" type="text" class="form-control" 
                       value="${contact.email}" placeholder="Email">
            </div>
            <div class="input-group">
                <span class="input-group-addon">Current Work: </span>
                <input name="job" type="text" class="form-control" 
                       value="${contact.job}" placeholder="Current Work">
            </div><div class="input-group">
                <span class="input-group-addon">Country: </span>
                <input name="country" type="text" class="form-control" 
                       value="${contact.country}" placeholder="Country">
            </div>
            <div class="input-group">
                <span class="input-group-addon">City: </span>
                <input name="city" type="text" class="form-control" 
                       value="${contact.city}" placeholder="City">
            </div>
            <div class="input-group">
                <span class="input-group-addon">Street, House, Room:</span>
                <input name="streetHouseRoom" type="text" class="form-control" 
                       value="${contact.streetHouseRoom}" placeholder="Street, House, Room">
            </div>
            <div class="input-group">
                <span class="input-group-addon">Index: </span>
                <input name="index" type="text" class="form-control" 
                       value="${contact.indexNumber}" placeholder="Index">
            </div>


            <div id="editingPhones">
                <div id="phoneActions">
                    <button type="button" class="btn btn-info btn-md" onclick="createPhone()">Add</button>
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
                            <td>Actions</td>
                        </tr>
                    </thead>
                    <tbody  id="phoneList"class="table table-bordered">
                        <c:forEach items="${phoneList}" var="phone">
                            <tr>
                        <input type="hidden" name="phoneAction" value="none"/>
                        <input type="hidden" name="phoneNumber"  value="${phone.countryCode}-${phone.operatorCode}-${phone.phoneNumber}" />
                        <input type="hidden" name="phoneComment" value="${phone.comment}" />
                        <input type="hidden" name="phoneType" value="${phone.phoneType}"/>
                        <input type="hidden" name="phoneID" value="${phone.id}"/>
                        <td>
                            <input type="checkbox" />
                        </td>
                        <td>
                            <h6>${phone.countryCode}-${phone.operatorCode}-${phone.phoneNumber}</h6>

                        </td>
                        <td>
                            <h6>
                                ${phone.phoneType}
                            </h6>
                        </td>
                        <td>
                            <h6>${phone.comment}</h6>
                        </td>
                        <td>
                            <div class='ctrlBtn btn-group'>
                                <button type="button" class="btn btn-info btn-md" onclick="editPhone(this)">Edit</button>
                                <button class='btn btn-info'>Delete</button>
                            </div>
                        </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div id="editingAttachments">
                <div id="attachmentActions">
                    <button type="button" class="btn btn-info btn-md" onclick="showAttachmentPopup('block')">
                        <span class="glyphicon glyphicon-paperclip"></span> Add</button>
                    <button type="button" class="btn btn-info btn-md">Delete</button>
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
                        <c:forEach items="${attachmentList}" var="attachment">
                            <tr id="${attachment.id}">
                        <input type="hidden" name="idAttachment" value="${attachment.id}">
                        <input type="hidden" name="filePath" value="${attachment.url}">
                        <input type="hidden" name="loadDate" value="${attachment.loadDate}">
                        <input type="hidden" name="comment" value="${attachment.comment}">
                        <td>
                            <button type="button" class="btn btn-info" onclick="downloadFile(this)"><span class="glyphicon glyphicon-download"></span> ${attachment.url}</button>
                        </td>
                        <td><h6>${attachment.loadDate}</h6></td>
                        <td><h6>${attachment.comment}</h6></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </form>
        <script type="text/javascript" src="js/editing.js" />
    </body>
</html>
