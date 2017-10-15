var phoneRaw;

function showPhonePopup(state) {
    document.getElementById("wrap").style.display = state;
    document.getElementById("popupPhone").style.display = state;

    document.getElementById("cCode").value = '';
    document.getElementById("oCode").value = '';
    document.getElementById("pNumber").value = '';
    document.getElementById("mobileType").checked = 'true';
    document.getElementById("comment").value = "";
}

function showAttachmentPopup(state) {
    document.getElementById("wrap").style.display = state;
    document.getElementById("popupAttachment").style.display = state;
}

function savePhone(button) {
    var countryCode = document.getElementById("cCode").value;
    var operatorCode = document.getElementById("oCode").value;
    var phoneNumber = document.getElementById("pNumber").value;
    var isMobile = document.getElementById("mobileType").checked;
    var isHome = document.getElementById("homeType").checked;
    var comment = document.getElementById("phoneComment").value;

    if (!(+countryCode) || countryCode.length < 3 || countryCode.length > 4) {
        alert("Enter valid country code");
        return;
    }
    if (!(+operatorCode) || operatorCode.length !== 2) {
        alert("Enter valid operator code");
        return;
    }
    if (!(+phoneNumber) || phoneNumber.length !== 7) {
        alert("Enter valid phone numberrr");
        return;
    }
    if (comment.length > 255) {
        alert("comment is too big");
        return;
    }

    var tr;
    var inputAction, inputPNumber, inputComment, inputPType;
    var td1, td2, td3, td4, td5;

    if (document.getElementById("popUpAction").value == 'create') {
        tr = document.createElement("TR");

        inputAction = document.createElement("INPUT");
        inputPNumber = document.createElement("INPUT");
        inputComment = document.createElement("INPUT");
        inputPType = document.createElement("INPUT");

        td1 = document.createElement("TD");
        td2 = document.createElement("TD");
        td3 = document.createElement("TD");
        td4 = document.createElement("TD");
        td5 = document.createElement("TD");

        inputAction.type = "hidden";
        inputAction.name = "phoneAction";
        inputAction.value = "create";
        inputPNumber.type = "hidden";
        inputPNumber.name = "phoneNumber";
        inputComment.type = "hidden";
        inputComment.name = "comment";
        inputPType.type = "hidden";
        inputPType.name = "phoneType";

        tr.appendChild(inputAction);
        tr.appendChild(inputPNumber);
        tr.appendChild(inputComment);
        tr.appendChild(inputPType);
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

        if (inputAction.value != 'create') {
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
    td1.innerHTML = "<input type='checkbox'>";
    td2.innerHTML = "<h6>" + inputPNumber.value + "</h6>";
    if (isMobile) {
        td3.innerHTML = "<h6>Mobile</h6>";
    } else {
        td3.innerHTML = "<h6>Home</h6>";
    }
    td4.innerHTML = "<h6>" + comment + "</h6>";
    td5.innerHTML = "<div class='ctrlBtn btn-group'><button type='button' class='btn btn-info btn-md' onclick='editPhone(this)'>Edit</button><button class='btn btn-info'>Delete</button></div>";
    inputAction = null;
    phoneRaw = null;
    showPhonePopup('none');
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
    if (inputs[3].value == 'Mobile') {
        document.getElementById("mobileType").checked = 'true';
    } else {
        document.getElementById("homeType").checked = 'true';
    }
    document.getElementById("comment").value = inputs[2].value;
}

function createPhone() {
    showPhonePopup('block');
    document.getElementById("popUpAction").value = 'create';
}

function saveAttachment() {
    var now = new Date();

    var file = document.getElementById("file").value;
    var comment = document.getElementById("attachmentComment").value;
//    if (!comment){
//        comment = '';
//    }

    var tr = document.createElement("TR");
    var td1 = document.createElement("TD");
    var td2 = document.createElement("TD");
    var td3 = document.createElement("TD");

    td1.innerHTML = "<h6>" + file + "<\h6>";
    td2.innerHTML = "<h6>" + now + "</h6>";
    td3.innerHTML = "<h6>" + comment + "</h6>";

    tr.appendChild(td1);
    tr.appendChild(td2);
    tr.appendChild(td3);

    var table = document.getElementById("attachmentList");
    table.appendChild(tr);

    showAttachmentPopup('none');
}

function checkForm() {
//    var flag = false;
//    var msg = "";
    alert("checkForm");
    document.form.submit();
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
    var action = document.getElementsByName('action')[0];
    document.forms[0].submit();
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