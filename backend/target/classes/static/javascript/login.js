regbtn=document.getElementById("registerBtn")
regbtn.onclick=()=>{window.location.href='http://' + window.location.host + '/register'}
document.addEventListener("DOMContentLoaded", checkError);
function checkError(){
    document.getElementById("form").action="http://" + window.location.host + "/login"
    if(window.location.search=="?error"){
        alert("wrong username or password")
    }
    else if(window.location.search=="?logout"){
    window.location.href='http://' + window.location.host + '/login'
    }
}