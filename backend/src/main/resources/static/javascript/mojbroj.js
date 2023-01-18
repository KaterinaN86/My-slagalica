document.addEventListener("DOMContentLoaded",()=>{
        handleNewGame();
    }
)
var gameId;
var numbers;
var expressionList = [];
var selectedNumber = [];
var symbols = ['(', ')', '-', '+', '/', '*']
let time=100;
var isActiveGame=true;
var backButton=document.getElementById('backButton');

backButton.addEventListener("click", goBack);

function handleNewGame(){
    fetch('http://' + window.location.host + '/MojBroj/Init')
        .then(
            (response) => {
                if(response.status == 404){
                    history.back()
                }
                else if (response.status !== 200) {
                    console.log('Error: ' + response.status);
                    return;
                }
                response.json().then((data) => {
                    startTimer();
                    gameId = data.id;
                    numbers=data.numbers;
                    userExpression=document.getElementById("userExpression")
                    document.getElementById("targetNumber").innerHTML=numbers[0].toString();
                    var elems=document.getElementsByClassName("availableNumber")
                    var elems1=document.getElementsByClassName("btnChar")
                    for(let i=0;i<6;i++){
                        elems[i].innerText=numbers[i+1].toString()
                        elems[i].addEventListener("click",(event) => {
                            if (userExpression.innerText.length == 0 ||
                                symbols.includes(userExpression.innerText[userExpression.innerText.length - 1])) {
                                elems[i].setAttribute("disabled", "true")
                                selectedNumber.push(elems[i])
                                userExpression.innerText += event.target.innerText
                                expressionList.push(event.target.innerText)
                            }
                        })
                        elems1[i].addEventListener("click",(event) => {
                            userExpression.innerText+=event.target.innerText
                            expressionList.push(event.target.innerText)
                        })
                    }
                    document.getElementById("btnDelete").addEventListener("click",()=>{
                        if (!symbols.includes(userExpression.innerText[userExpression.innerText.length - 1]) ) {
                            selectedNumber[selectedNumber.length - 1].removeAttribute("disabled")
                            selectedNumber.pop()
                        }
                        elm = expressionList.pop();
                        userExpression.innerText = userExpression.innerText.slice(0, userExpression.innerText.length - elm.length) !== "undefined" ? userExpression.innerText.slice(0, userExpression.innerText.length - elm.length) : "";
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
    startTimer("stop")
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
                if(data.msg == ""){
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
function goBack() {

    if(confirm("Do you want to finish the game?")){
            finishGame().then(function () {
                history.back();
            });
    }
}

async function finishGame(){
    const response = await fetch('http://' + window.location.host + '/MojBroj/finishGame', {
            method: 'PUT'
        });
        if (response.status !== 200) {
            console.log('Error: ' + response.status);
            return;
        }
        isActiveGame=false;
}

function leaveGame(){
    if(!isActiveGame){
        history.back()
    }
    startTimer("stop");
    finishGame().then(function(){
        history.back();
    })
}

const startTimer = (order) => {
    if (order == "stop") {
        clearInterval(timerInterval);
        isActiveGame=false;
    }
    else {
        timerInterval = setInterval(function(){
             if(isActiveGame){
                time--;
             }
            const min = String(Math.trunc(time/60)).padStart(2,0);
            const sec = String(time%60).padStart(2,0);
            timer.textContent=`${min} : ${sec}`;
            if(time === 0){
                submit()
            }
        },1000)
    }
}