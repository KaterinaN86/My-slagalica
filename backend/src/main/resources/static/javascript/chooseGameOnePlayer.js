document.addEventListener("DOMContentLoaded",()=>{init()})

function init(){
    fetch('http://localhost:8080/OnePlayer/Init')
            .then(
                (response) => {
                    if (response.status !== 200) {
                        console.log('Error: ' + response.status);
                        return;
                    }

                    response.json().then((data) => {
                        gameId = data.id;
                        numbers=data.numbers;
                        userExpression=document.getElementById("userExpression")
                        document.getElementById("targetNumber").innerHTML=numbers[0].toString();
                        var elems=document.getElementsByClassName("availableNumber")
                        var elems1=document.getElementsByClassName("btnChar")
                        for(let i=0;i<6;i++){
                            elems[i].innerText=numbers[i+1].toString()
                            elems[i].addEventListener("click",(event)=>{
                                userExpression.innerText+=event.target.innerText
                            })
                            elems1[i].addEventListener("click",(event)=>{
                                userExpression.innerText+=event.target.innerText
                            })
                        }
                        document.getElementById("btnDelete").addEventListener("click",()=>{
                            userExpression.innerText=userExpression.innerText.slice(0,userExpression.innerText.length-1)
                        })
                        document.getElementById("btnSubmit").addEventListener("click",submit)
                    });
                }
            )
            .catch((error) => {
                console.log('Fetch error: ', error);
            })
}