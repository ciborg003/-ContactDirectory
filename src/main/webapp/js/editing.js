function showPhonePopup(state) {
    document.getElementById("wrap").style.display = state;
    document.getElementById("popupPhone").style.display = state;
}

function showAttachmentPopup(state) {
    document.getElementById("wrap").style.display = state;
    document.getElementById("popupAttachment").style.display = state;
}

function savePhone() {
    var countryCode = document.getElementById("cCode").value;
    var operatorCode = document.getElementById("oCode").value;
    var phoneNumber = document.getElementById("pNumber").value;
    var isMobile = document.getElementById("mobileType").checked;
    var isHome = document.getElementById("homeType").checked;
    var comment = document.getElementById("phoneComment").textContent;
    
    if (!(+countryCode) || countryCode.length<3 || countryCode.length > 4){
        alert("Enter valid country code");
        return;
    }
    if (!(+operatorCode) || operatorCode.length !== 2){
        alert("Enter valid operator code");
        return;
    }
    
    if (!(+phoneNumber) || phoneNumber.length !== 7){
        alert("Enter valid phone numberrr");
        return;
    }
    
    if (comment.length > 255){
        alert("comment is too big");
        return;
    }
    
    var tr = document.createElement("TR");
    var td1 = document.createElement("TD");
    var td2 = document.createElement("TD");
    var td3 = document.createElement("TD");
    var td4 = document.createElement("TD");
    
    td1.innerHTML = "<input type='checkbox'>";
    td2.innerHTML = "<h6>" + countryCode + "-" + operatorCode + "-" + phoneNumber + "</h6>";

    if(isMobile){
        td3.innerHTML = "<h6>Mobile</h6>";
    } else {
        td3.innerHTML = "<h6>Home</h6>";
    }
    
    td4.innerHTML = "<h6>" + comment + "</h6>";
    
    var table = document.getElementById("phoneList");
    
    tr.appendChild(td1);
    tr.appendChild(td2);
    tr.appendChild(td3);
    tr.appendChild(td4);
    table.appendChild(tr);
    
    showPhonePopup('none');
}

function saveAttachment(){
    var now = new Date();
    
    var file = document.getElementById("file").value;
    var comment = document.getElementById("attachmentComment").textContent;
    
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