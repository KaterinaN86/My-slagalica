regbtn=document.getElementById("registerBtn")
regbtn.onclick=()=>{window.location.href="http://localhost:8080/register"}
document.addEventListener("DOMContentLoaded", checkError);
function checkError(){
    if(window.location.search=="?error"){
        alert("wrong username or password")
    }
    else if(window.location.search=="?logot"){
    window.location.href="http://localhost:8080/login"
    }
}