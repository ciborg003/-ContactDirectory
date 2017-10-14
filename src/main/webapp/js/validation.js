function validateEmail(email) {
    var pattern = /^[0-9a-z-\.]+\@[0-9a-z-]{2,}\.[a-z]{2,}$/i;
    var r = email.match(pattern);
    if (!r) {
        return false;
    }
    return true;
}