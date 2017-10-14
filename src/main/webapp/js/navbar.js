function searchPage(){
    alert(document.getElementsByName('action')[0].value);
    var inputAction = document.getElementsByName('action')[0];
    inputAction.value = 'getSearchPage';
    alert(document.getElementsByName('action')[0].value);
    document.forms[0].submit();
}