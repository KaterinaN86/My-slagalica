regbtn=document.getElementById("registerBtn")
regbtn.onclick=()=>{window.location.href="http://localhost:8080/register"}
document.addEventListener("DOMContentLoaded", checkError);
function checkError(){
    if(window.location.search=="?error"){
        alert("wrong username or password")
    }
}