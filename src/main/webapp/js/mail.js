function addEmail() {
    var email = document.getElementById("attachEmail").value;
    document.getElementById("attachEmail").value = '';
    if (!validateEmail(email)) {
        alert("Input valid email!!!");
        return;
    }
    var emailList = document.getElementById("emailList");
    var div = document.createElement("div");
    div.className = "col-md-4";
    var li = document.createElement("li");
    li.className = "list-group-item";
    li.innerHTML = email + '<button style="margin-left: auto; margin-right: 0em;" type="button" class="btn btn-info " onclick="deleteEmail(this)"><span class="fa fa-remove" /></button>';
    li.innerHTML += '<input type="hidden" name="email" value="' + email + '" />';
    div.appendChild(li);
    emailList.appendChild(div);
}

function deleteEmail(btn) {
    var div = btn.parentElement.parentElement;
    div.parentNode.removeChild(div);
}

function sendMail() {
    var emails = document.getElementsByName('email');

    if (document.getElementsByName('title')[0].value.trim().length < 1) {
        document.getElementsByName('title')[0].style.backgroundColor = 'red';
        return;
    } else{
        document.getElementsByName('title')[0].style.backgroundColor = 'red';
    }
    if (document.getElementsByName('mailContent')[0].value.trim().length < 1) {
        document.getElementsByName('mailContent')[0].style.backgroundColor = 'red';
        return;
    } else {
        document.getElementsByName('mailContent')[0].style.backgroundColor = '';
    }
    if (emails.length < 1) {
        alert("Email list is empty");
        return;
    } else {
        document.forms[0].submit();
    }
}

function validateEmail(email) {
    var pattern = /^[0-9a-z-\.]+\@[0-9a-z-]{2,}\.[a-z]{2,}$/i;
    var r = email.match(pattern);
    if (!r) {
        return false;
    }
    return true;
}

function changeTemplate() {
    var options = document.getElementsByTagName('option');
    var msg;

    for (var i = 0; i < options.length; i++) {
        if (options[i].selected) {
            if (document.getElementById(options[i].value)) {
                msg = document.getElementById(options[i].value).value;
            }
        }
    }

    if (!msg) {
        msg = '';
    }

    document.getElementsByName('mailContent')[0].value = msg;
}