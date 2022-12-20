document.addEventListener("DOMContentLoaded",()=>{handleNewGame()})
var gameId;
var numbers;
var expressionList = [];
function handleNewGame(){
    fetch('http://' + window.location.host + '/MojBroj/Init')
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
                            expressionList.push(event.target.innerText)
                        })
                        elems1[i].addEventListener("click",(event)=>{
                            userExpression.innerText+=event.target.innerText
                            expressionList.push(event.target.innerText)
                        })
                    }
                    document.getElementById("btnDelete").addEventListener("click",()=>{
                        elm = expressionList.pop();
                        userExpression.innerText = userExpression.innerText.slice(0, userExpression.innerText.length - elm.length)
                    })
                    document.getElementById("btnSubmit").addEventListener("click",submit)
                });
            }
        )
        .catch((error) => {
            console.log('Fetch error: ', error);
        })

}
async function submit(){
    var requestBody = {
            gameId: gameId,
            expression: document.getElementById("userExpression").innerText
        }

        try {
            const response = await fetch('http://' + window.location.host + '/MojBroj/Submit', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody)
            });

            if (response.status == 200) {
                const data = await response.json();
                document.getElementById("solution").innerText=data.solution
                if(data.msg==""){
                    alert("Osvojili ste "+data.numOfPoints+" poena\n Vas rezultat je: " + data.result)
                }
                else{
                    alert(data.msg)
                }
            }

        }catch (error) {
            console.log(error)
        }
}