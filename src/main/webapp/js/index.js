var contact = {};
contact.selectedContacts = [];

function loadPage(recordsCount, pageNumber, recordsOnPage) {
    var selectRecordsOnPage = document.getElementById("recordsOnPage");

    for (var i = 0; i < selectRecordsOnPage.length; i++) {
        if (+(selectRecordsOnPage.options[i].innerHTML) === (+recordsOnPage)) {
            selectRecordsOnPage.options[i].selected = "selected";
            break;
        }
    }

    var selectPages = document.getElementById("pagesCount");
    selectPages.innerHTML = "";

    for (var i = 1; i <= Math.ceil((+recordsCount) / (+recordsOnPage)); i++) {
        var newOption = document.createElement("OPTION");

        if ((+pageNumber) === i) {
            newOption.innerHTML = i;
            newOption.selected = "true";
        } else {
            newOption.innerHTML = i;
        }

        selectPages.appendChild(newOption);
    }
}

function calcPagesAmount(recordsCount) {
    var selectRecordsOnPage = document.getElementById("recordsOnPage");
    var recordsOnPage;

    for (var i = 0; i < selectRecordsOnPage.length; i++) {
        if (selectRecordsOnPage.options[i].selected) {
            recordsOnPage = +(selectRecordsOnPage.options[i].innerHTML);
            break;
        }
    }

    var selectPages = document.getElementById("pagesCount");
    selectPages.innerHTML = "";

    for (var i = 1; i <= Math.ceil((+recordsCount) / (+recordsOnPage)); i++) {
        var newOption = document.createElement("OPTION");
        newOption.innerHTML = i;

        selectPages.appendChild(newOption);
    }
}

function sendMails() {
//    if(contact.selectedContacts.length < 1){
//        alert("Check Some Contacts");
//        return;
//    }


    var form = document.forms[0];
    form.method = 'GET';
    form.action = 'ServletController';

    for (var i = 0; i < contact.selectedContacts.length; i++) {
        var input = document.createElement('input');
        input.name = 'contactId';
        input.value = contact.selectedContacts[i];

        form.appendChild(input);
    }

    var inputAction = document.getElementsByName('action')[0];
    inputAction.value = 'getMailPage';

    form.submit();
}

function addNewContact() {
    var action = document.getElementsByName("action")[0];
    action.value = "addContact";
    document.forms[0].submit();
}

function changePage() {
    var page = document.getElementById("pagesCount").value;
    var records = document.getElementById("recordsOnPage").value;
    if (document.getElementsByName("action")[0].value !== 'search') {
        document.getElementsByName("action")[0].value = "changePage";
    }
    document.forms[0].submit();
}

function editContact(button) {
    var input = document.createElement('INPUT');
    input.style.type = 'hidden';
    input.value = button.parentElement.parentElement.parentElement.id;
    input.name = 'contactID';
    document.forms[0].appendChild(input);
    var action = document.getElementsByName("action")[0];
    action.value = "updateContact";
    document.forms[0].submit();
}

function checkBoxAction(checkBox) {
    //1
    var id = checkBox.parentElement.parentElement.parentElement.id;

    //2
    if (checkBox.checked) {
        //3
        contact.selectedContacts.push(id);
    } else {
        //4
        contact.selectedContacts = deleteElementFromArray(contact.selectedContacts, id);
    }
    //5
}

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

function deleteContact(btn) {
    if (btn) {
        contact.selectedContacts = [+btn.parentElement.parentElement.parentElement.id];
    }

    if (contact.selectedContacts.length < 1) {
        alert("Check Some Contacts");
        return;
    }


    var form = document.forms[0];
    form.method = 'GET';
    form.action = 'ServletController';

    for (var i = 0; i < contact.selectedContacts.length; i++) {
        var input = document.createElement('input');
        input.name = 'contactId';
        input.value = contact.selectedContacts[i];

        form.appendChild(input);
    }

    var inputAction = document.getElementsByName('action')[0];
    inputAction.value = 'deleteContact';

    form.submit();
}