logbtn=document.getElementById("loginBtn")
logbtn.onclick=()=>{window.location.href="http://localhost:8080/login"}
async function addUser(){
    var requestBody = {
                userName: document.getElementById("inputUserName").value,
                password: document.getElementById("inputPassword").value}
    console.log(requestBody)
    const response = await fetch('http://localhost:8080/NewUser', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(requestBody)
                });
    data=await response.json()
    if(data.msg=="Uspesno ste se registrovali"){
        window.location.href="http://localhost:8080/login"
    }
    alert(data.msg)



}