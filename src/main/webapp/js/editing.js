var phoneRaw;
var selectedPhoneRows = [];
var fileForms = [];

function deleteElementFromArray(arr, el) {
    //1
    for (var i = 0; i < arr.length; i++) {
        //2
        if (arr[i] === el) {
            //3
            arr = arr.slice(0, i).concat(arr.slice(i + 1, arr.length));
        }
        //4
    }
    //5
    return arr;
}
//--------------PHONE_ACTIONS------------------------------

function showPhonePopup(state) {
    document.getElementById("wrap").style.display = state;
    document.getElementById("popupPhone").style.display = state;

    document.getElementById("cCode").value = '';
    document.getElementById("oCode").value = '';
    document.getElementById("pNumber").value = '';
    document.getElementById("mobileType").checked = 'true';
    document.getElementById("phoneComment").value = "";

    document.getElementById("cCode").style.borderColor = "";
    document.getElementById("oCode").style.borderColor = "";
    document.getElementById("pNumber").style.borderColor = "";
    document.getElementById("phoneComment").style.borderColor = "";
}

function savePhone(button) {
    var isValidForm = true;

    var countryCode = document.getElementById("cCode").value;
    var operatorCode = document.getElementById("oCode").value;
    var phoneNumber = document.getElementById("pNumber").value;
    var isMobile = document.getElementById("mobileType").checked;
    var isHome = document.getElementById("homeType").checked;
    var comment = document.getElementById("phoneComment").value;

    if (!(+countryCode) || countryCode.length < 3 || countryCode.length > 4) {
        document.getElementById("cCode").style.borderColor = "red";
        isValidForm = false;
    } else {
        document.getElementById("cCode").style.borderColor = "";
    }
    if (!(+operatorCode) || operatorCode.length !== 2) {
        document.getElementById("oCode").style.borderColor = "red";
        isValidForm = false;
    } else {
        document.getElementById("oCode").style.borderColor = "";
    }
    if (!(+phoneNumber) || phoneNumber.length !== 7) {
        document.getElementById("pNumber").style.borderColor = "red";
        isValidForm = false;
    } else {
        document.getElementById("pNumber").style.borderColor = "";
    }
    if (comment.length > 255) {
        document.getElementById("phoneComment").style.borderColor = "red";
        isValidForm = false;
    } else {
        document.getElementById("phoneComment").style.borderColor = "";
    }

    if (!isValidForm) {
        return;
    }

    var tr;
    var inputAction, inputPNumber, inputComment, inputPType;
    var td1, td2, td3, td4, td5;

    if (document.getElementById("popUpAction").value === 'create') {
        tr = document.createElement("TR");
        tr.className = 'tr-custom';

        inputAction = document.createElement("INPUT");
        inputPNumber = document.createElement("INPUT");
        inputComment = document.createElement("INPUT");
        inputPType = document.createElement("INPUT");
        var inputPhoneID = document.createElement("INPUT");
        inputPhoneID.name = 'phoneID';
        inputPhoneID.type = 'hidden';
        inputPhoneID.value = 'null';

        td1 = document.createElement("TD");
        td2 = document.createElement("TD");
        td3 = document.createElement("TD");
        td4 = document.createElement("TD");
        td5 = document.createElement("TD");
        
        td1.innerHTML = "<label class='custom-control custom-checkbox mb-2 mr-sm-2 mb-sm-0'>"
            + "<input type='checkbox' class='custom-control-input' onchange='checkBoxPhoneAction(this)'>"
            + "<span class='custom-control-indicator'></span></label>";

        inputAction.type = "hidden";
        inputAction.name = "phoneAction";
        inputAction.value = "create";
        inputPNumber.type = "hidden";
        inputPNumber.name = "phoneNumber";
        inputComment.type = "hidden";
        inputComment.name = "phoneComment";
        inputPType.type = "hidden";
        inputPType.name = "phoneType";

        tr.appendChild(inputAction);
        tr.appendChild(inputPNumber);
        tr.appendChild(inputComment);
        tr.appendChild(inputPType);
        tr.appendChild(inputPhoneID);
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tr.appendChild(td4);
        tr.appendChild(td5);

        var table = document.getElementById("phoneList");
        table.appendChild(tr);

    } else {

        tr = phoneRaw;

        inputPNumber = tr.getElementsByTagName('INPUT')[1];
        inputComment = tr.getElementsByTagName('INPUT')[2];
        inputPType = tr.getElementsByTagName('INPUT')[3];
        inputAction = tr.getElementsByTagName('INPUT')[0];

        if (inputAction.value !== 'create') {
            inputAction.value = 'update';
        }

        td1 = tr.getElementsByTagName('td')[0];
        td2 = tr.getElementsByTagName('td')[1];
        td3 = tr.getElementsByTagName('td')[2];
        td4 = tr.getElementsByTagName('td')[3];
        td5 = tr.getElementsByTagName('td')[4];
    }

    inputPNumber.value = countryCode + "-" + operatorCode + "-" + phoneNumber;
    inputComment.value = comment;

    if (isMobile) {
        inputPType.value = "Mobile";
    } else {
        inputPType.value = "Home";
    }
//    td1.innerHTML = "<label class='custom-control custom-checkbox mb-2 mr-sm-2 mb-sm-0'>"
//            + "<input type='checkbox' class='custom-control-input' onchange='checkBoxPhoneAction(this)'>"
//            + "<span class='custom-control-indicator'></span></label>";
    td2.innerHTML = "<h6>" + inputPNumber.value + "</h6>";
    if (isMobile) {
        td3.innerHTML = "<h6>Mobile</h6>";
    } else {
        td3.innerHTML = "<h6>Home</h6>";
    }
    td4.innerHTML = "<h6>" + comment + "</h6>";
    td5.innerHTML = "<div class='ctrlBtn btn-group'>"
            + "<button type='button' class='btn btn-info btn-md' onclick='editPhone(this)'>"
            + "Edit</button><button type='button' class='btn btn-info' onclick='deletePhone(this)'>Delete</button></div>";
    inputAction = null;
    phoneRaw = null;
    showPhonePopup('none');
}

function editPhoneOnCheckbox() {
    if (selectedPhoneRows.length === 1) {
        phoneRaw = selectedPhoneRows[0];
    } else {
        return;
    }
    
    showPhonePopup('block');
    
    var inputs = phoneRaw.getElementsByTagName('INPUT');

    var cCode = inputs[1].value.split('-')[0];
    var oCode = inputs[1].value.split('-')[1];
    var pNumber = inputs[1].value.split('-')[2];

    document.getElementById("popUpAction").value = 'edit';
    document.getElementById("cCode").value = cCode;
    document.getElementById("oCode").value = oCode;
    document.getElementById("pNumber").value = pNumber;
    if (inputs[3].value === 'Mobile') {
        document.getElementById("mobileType").checked = 'true';
    } else {
        document.getElementById("homeType").checked = 'true';
    }
    document.getElementById("phoneComment").value = inputs[2].value;
}

function editPhone(button) {
    showPhonePopup('block');

    phoneRaw = button.parentElement.parentElement.parentElement;
    var inputs = phoneRaw.getElementsByTagName('INPUT');

    var cCode = inputs[1].value.split('-')[0];
    var oCode = inputs[1].value.split('-')[1];
    var pNumber = inputs[1].value.split('-')[2];

    document.getElementById("popUpAction").value = 'edit';
    document.getElementById("cCode").value = cCode;
    document.getElementById("oCode").value = oCode;
    document.getElementById("pNumber").value = pNumber;
    if (inputs[3].value === 'Mobile') {
        document.getElementById("mobileType").checked = 'true';
    } else {
        document.getElementById("homeType").checked = 'true';
    }
    document.getElementById("phoneComment").value = inputs[2].value;
}

function createPhone() {
    showPhonePopup('block');
    document.getElementById("popUpAction").value = 'create';
}

function checkBoxPhoneAction(checkBox) {
    if (checkBox.checked) {
        //3
        selectedPhoneRows.push(checkBox.parentElement.parentElement.parentElement);
    } else {
        //4
        selectedPhoneRows = deleteElementFromArray(selectedPhoneRows,
                checkBox.parentElement.parentElement.parentElement);
    }

    if (selectedPhoneRows.length < 1) {
        document.getElementById("phoneDelete").disabled = true;
    } else {
        document.getElementById("phoneDelete").disabled = false;
    }

    if (selectedPhoneRows.length === 1) {
        document.getElementById("phoneEdit").disabled = false;
    } else {
        document.getElementById("phoneEdit").disabled = true;
    }
}

function deletePhone(btn) {
    var tr = btn.parentElement.parentElement.parentElement;

    if (tr.getElementsByTagName('input')[0].value === 'create') {
        tr.parentElement.removeChild(tr);
    } else {
        tr.getElementsByTagName('input')[0].value = 'delete';
        tr.style.display = 'none';
    }
}

function deletePhones() {
    if (selectedPhoneRows.length < 1) {
        alert("Check Some Contacts");
        return;
    }

    for (var i = 0; i < selectedPhoneRows.length; i++) {
        if (selectedPhoneRows[i]) {
            if (selectedPhoneRows[i].getElementsByTagName('input')[0] === 'create') {
                selectedPhoneRows[i].parentElement.removeChild(selectedPhoneRows[i]);
            } else {
                alert(selectedPhoneRows[i].getElementsByTagName('input')[0].value);
                selectedPhoneRows[i].getElementsByTagName('input')[0].value = 'delete';
                selectedPhoneRows[i].style.display = 'none';
            }
        }
    }
}


//---------------ATACHMENTS_ACTIONS---------------------------------
function showAttachmentPopup(state) {

    document.getElementById("wrap").style.display = state;
    document.getElementById("popupAttachment").style.display = state;

    document.getElementById('file').value = '';
    document.getElementById('attachmentComment').value = '';

    document.getElementById("attachmentComment").style.borderColor = '';
}

function saveAttachment() {
    var isValidForm = true;
    var now = new Date();

    var file = document.getElementById("file");
    var comment = document.getElementById("attachmentComment").value;
    if (!file.value) {
        document.getElementById("file").style.border = '1px solid red';
        return;
    }

    if (!comment) {
        comment = '';
    } else if (comment.length > 255) {
        document.getElementById("attachmentComment").style.borderColor = 'red';
        isValidForm = false;
        return;
    } else {
        document.getElementById("attachmentComment").style.borderColor = '';
    }

    if (!isValidForm) {
        return;
    }

    var tr;
    var inputAction, inputLoadDate, inputComment;
    var td0, td1, td2, td3, td4;


    if (document.getElementById("popUpAction").value === 'create') {
        if (file.value.trim().length < 1) {
            alert('Please select any file...');
            return;
        }

        inputAction = document.createElement("INPUT");
        inputAction.type = 'hidden';
        inputAction.name = 'attachmentAction';
        inputAction.value = 'create';
        inputLoadDate = document.createElement("INPUT");
        inputLoadDate.type = 'hidden';
        inputLoadDate.name = 'loadDate';
        inputComment = document.createElement("INPUT");
        inputComment.type = 'hidden';
        inputComment.name = 'attachmentComment';

        tr = document.createElement("TR");
        tr.className = 'tr-custom';
        td0 = document.createElement("TD");
        td0.hidden = 'true';
        td1 = document.createElement("TD");
        td2 = document.createElement("TD");
        td3 = document.createElement("TD");
        td4 = document.createElement("TD");

        tr.appendChild(inputAction);
        tr.appendChild(inputLoadDate);
        tr.appendChild(inputComment);
        tr.appendChild(td0);
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tr.appendChild(td4);

        var table = document.getElementById("attachmentList");
        table.appendChild(tr);

//        inputFile.value = file.value;
        inputLoadDate.value = now;
        inputComment.value = comment;

        td1.innerHTML = "<h6>" + file.value + "<\h6>";
        td2.innerHTML = "<h6>" + now + "</h6>";
        td3.innerHTML = "<h6>" + comment + "</h6>";
        td4.innerHTML = "<div class='ctrlBtn btn-group'>"
                + '<button type="button" class="btn btn-info btn-md" onclick="editAttachment(this)">Edit</button>'
                + "<button class='btn btn-info' onclick='deleteAttachment(this)'>Delete</button>"
                + "</div>"
    } else {
        tr = attachmentRaw;

        inputAction = tr.getElementsByTagName('INPUT')[0];
        inputLoadDate = tr.getElementsByTagName('INPUT')[1];
        inputComment = tr.getElementsByTagName('INPUT')[2];

        if (inputAction.value !== 'create') {
            inputAction.value = 'edit';
        }

        td0 = tr.getElementsByTagName('td')[0];
        td1 = tr.getElementsByTagName('td')[1];
        td2 = tr.getElementsByTagName('td')[2];
        td3 = tr.getElementsByTagName('td')[3];
        td4 = tr.getElementsByTagName('td')[4];

        if (file.value.trim().length > 0) {
//            inputFile.value = file;
            inputLoadDate.value = now;

            td1.innerHTML = "<h6>" + file.value + "<\h6>";
            td2.innerHTML = "<h6>" + now + "</h6>";
        }


        inputComment.value = comment;



        td3.innerHTML = "<h6>" + comment + "</h6>";
        td4.innerHTML = "<div class='ctrlBtn btn-group'>"
                + '<button type="button" class="btn btn-info btn-md" onclick="editAttachment(this)">Edit</button>'
                + "<button class='btn btn-info' onclick='deletePhone()'>Delete</button>"
                + "</div>";
    }
    td0.innerHTML = "";
    td0.appendChild(file);
    file.removeAttribute('id');

    document.getElementById('select-file').innerHTML = "<label for='file'>Select file:</label>"
            + "<input type='file' id='file' name='data' class='form-control-file'>";

    tr.appendChild(td0);
    tr.appendChild(td1);
    tr.appendChild(td2);
    tr.appendChild(td3);
    tr.appendChild(td4);

    attachmentRaw = null;
    showAttachmentPopup('none');
}

function editAttachment(button) {
    showAttachmentPopup('block');

    attachmentRaw = button.parentElement.parentElement.parentElement;
    var inputs = attachmentRaw.getElementsByTagName('INPUT');

    var file = inputs[1].value;
    var comment = inputs[2].value;

    document.getElementById("popUpAction").value = 'edit';
    document.getElementById('file').value = file;
    document.getElementById("attachmentComment").value = comment;
}

function createAttachment() {
    showAttachmentPopup('block');
    document.getElementById("popUpAction").value = 'create';
}

function deleteAttachment(btn) {
    var tr = btn.parentElement.parentElement.parentElement;

    if (tr.getElementsByTagName('input')[0].value === 'create') {
        tr.parentElement.removeChild(tr);
    } else {
        tr.getElementsByTagName('input')[0].value = 'delete';
        tr.style.display = 'none';
    }
}


//----------------------------------------------------------------
function changePhoto() {
    var pattern = /\.(gif|jpg|jpeg|tiff|png)$/i;

    var photoSelect = document.getElementById('selectPhoto');
    var result = photoSelect.value.match(pattern);

    if (!result) {
        alert('invalid image');
        var newPhotoSelect = document.createElement('input');
        newPhotoSelect.type = 'file';
        newPhotoSelect.id = 'selectPhoto';
        newPhotoSelect.name = 'selectPhoto';
        newPhotoSelect.onchange = 'changePhoto()';
        photoSelect = newPhotoSelect;
        return;
    }

    var photoAction = document.getElementById("photoAction");
    photoAction.value = 'change';
}

function changeGender() {
    var gender = document.getElementsByName("gender")[0];

    if (gender.value === 'Female') {
        gender.value = 'Male';
    } else {
        gender.value = 'Female';
    }
}

function changeFamilyState() {
    var gender = document.getElementsByName("famState")[0];

    if (gender.value === 'Single') {
        gender.value = 'Married';
    } else {
        gender.value = 'Single';
    }
}

function getPhoneType(isMobile) {
    if (isMobile) {
        return 'Mobile';
    } else {
        return 'Home';
    }
}

function saveContact() {
    var isValidForm = true;

    if (!validateWord(document.getElementById('fName').value)
            || document.getElementById('fName').value.length < 1
            || document.getElementById('fName').value.length > 20) {
        document.getElementById('fName').style.borderColor = 'red';
        isValidForm = false;
    } else {
        document.getElementById('fName').style.borderColor = '';
    }
    if (!validateWord(document.getElementById('lName').value)
            || document.getElementById('lName').value.length < 1
            || document.getElementById('lName').value.length > 20) {
        document.getElementById('lName').style.borderColor = 'red';
        isValidForm = false;
    } else {
        document.getElementById('lName').style.borderColor = '';
    }
    if ((document.getElementById('patronymic').value.length > 0
            && !validateWord(document.getElementById('patronymic').value))
            || document.getElementById('patronymic').value.length > 20) {
        document.getElementById('patronymic').style.borderColor = 'red';
        isValidForm = false;
    } else {
        document.getElementById('fName').style.borderColor = 'patronymic';
    }
    if ((document.getElementById('nation').value.length > 0
            && !validateWord(document.getElementById('nation').value))
            || document.getElementById('nation').value.length > 45) {
        document.getElementById('nation').style.borderColor = 'red';
        isValidForm = false;
    } else {
        document.getElementById('nation').style.borderColor = '';
    }
    if (document.getElementById('webSite').value.length > 100) {
        document.getElementById('webSIte').style.borderColor = 'red';
        isValidForm = false;
    } else {
        document.getElementById('webSite').style.borderColor = '';
    }
    if (document.getElementById('job').value.length > 45) {
        document.getElementById('job').style.borderColor = 'red';
        isValidForm = false;
    } else {
        document.getElementById('job').style.borderColor = '';
    }
    if ((document.getElementById('email').value.length > 0
            && !validateEmail(document.getElementById('email').value))
            || document.getElementById('email').value.length > 45) {
        document.getElementById('email').style.borderColor = 'red';
        isValidForm = false;
    } else {
        document.getElementById('email').style.borderColor = '';
    }
    if ((document.getElementById('country').value.length > 1
            && !validateWord(document.getElementById('country').value))
            || document.getElementById('country').value.length > 20) {
        document.getElementById('country').style.borderColor = 'red';
        isValidForm = false;
    } else {
        document.getElementById('country').style.borderColor = '';
    }
    if (document.getElementById('city').value.length > 20) {
        document.getElementById('city').style.borderColor = 'red';
        isValidForm = false;
    } else {
        document.getElementById('city').style.borderColor = '';
    }
    if (document.getElementById('streetHouseRoom').value.length > 45) {
        document.getElementById('streetHouseRoom').style.borderColor = 'red';
        isValidForm = false;
    } else {
        document.getElementById('streetHouseRoom').style.borderColor = '';
    }
    if (document.getElementById('index').value.length > 45) {
        document.getElementById('index').style.borderColor = 'red';
        isValidForm = false;
    } else {
        document.getElementById('index').style.borderColor = '';
    }

    if (isValidForm) {
        document.forms[0].submit();
    }
}

function downloadFile(button) {
    var form = document.createElement('form');
    form.method = 'get';
    form.action = 'ServletController';

    var inputAction = document.createElement('input');
    inputAction.name = 'action';
    inputAction.value = 'downloadFile';

    var tr = button.parentElement.parentElement;
    var attachmentID = document.createElement('input');
    attachmentID.name = 'attachmentID';
    attachmentID.value = tr.id;

    form.appendChild(inputAction);
    form.appendChild(attachmentID);
    form.submit();
}