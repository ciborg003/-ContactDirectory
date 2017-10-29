const patternEmail = /^[0-9a-z-\.]+\@[0-9a-z-]{2,}\.[a-z]{2,}$/i;
const patternWord = /^[A-Za-z]+'?[A-Za-z]+$|^[А-Яа-я]+$/g;

function validateEmail(email) {
//    var pattern = /^[0-9a-z-\.]+\@[0-9a-z-]{2,}\.[a-z]{2,}$/i;
    var r = email.match(patternEmail);
    if (!r) {
        return false;
    }
    return true;
}

function validateWord(word){
    if (word.match(patternWord)){
        return true;
    }
    return false;
}