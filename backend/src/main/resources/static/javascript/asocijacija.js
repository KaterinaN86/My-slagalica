//Modal related code

// Get the modal
var modal = document.getElementById("myModal");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

var displayTimer = document.getElementById("timer")
var seconds = 120;
var isActiveGame=true;
// When the user clicks the button, open the modal 
function openModal(pressedButton) {
  modal.style.display = "block";
  document.getElementById("inputField").focus()
  pressedButtonId = pressedButton.id;
  console.log(pressedButtonId);
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
  modal.style.display = "none";
}

// When the user clicks anywhere outside the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        closeModal();
      }
}
window.addEventListener('beforeunload', (event) => {
    event.preventDefault();
    event.returnValue = 'Do you want to finish the game?';
});


const closeModal = () =>{
      modal.style.display = "none";
}



//Game related code
var gameId;
var pressedButtonId;
var numberOfOpenedFields=0;

document.addEventListener("DOMContentLoaded", ()=>{
    displayTimer.textContent='120';
    handleNewGame();
});

const handleNewGame = () =>{
    fetch('http://' + window.location.host + '/asocijacija/play')
    .then(
        (response) => {
            if(response.status == 404){
                history.back();
            }
            else if (response.status !== 200) {
                console.log('Error: ' + response.status);
                return;
            }

            response.json().then((data) => {
                gameId = data.gameId;
                timer();
                console.log(gameId);
            });
        }
    )
    .catch((error) => {
        console.log('Fetch error: ', error);
    })
}

function goBack() {
    if(!isActiveGame){
             window.location.href = "OnePlayer";
        }
    else if(confirm("Do you want to finish the game?")){
       finishGame().then(function(){

 //        window.removeEventListener('beforeunload')
         history.back();
       })
    }
}

function leaveGame(){
     if(!isActiveGame){
         history.back();
     }
    timer("stop");
    finishGame().then(function(){
        history.back();
    })
}

const showWord = async (pressedButton)=>{
    console.log(pressedButton)
    var sendObject={
        gameId:gameId,
        fieldName:pressedButton.id
    }
    fetch('http://' + window.location.host + '/asocijacija/getField/' + sendObject.fieldName)
    .then(
        (response) => {
            if (response.status !== 200) {
                console.log('Error: ' + response.status);
                return;
            }

            response.json().then((data) => {
                console.log(numberOfOpenedFields);
                pressedButton.innerHTML = data.fieldValue;
                pressedButton.disabled = true;
                console.log(data.fieldValue);
            });
        }
    )
    .catch((error) => {
        console.log('Fetch error: ', error);
    })
}

document.onkeydown = function () {
    if (window.event.keyCode == '13') {
        sendSubmit();
    }
}

const sendSubmit = async () =>{
    let enteredWord = document.getElementById('inputField').value;

    var submitObject = {
        gameId: gameId,
        fieldName: pressedButtonId,
        word:enteredWord
    }

    try {
        const response = await fetch('http://' + window.location.host + '/asocijacija/submitWord', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(submitObject)
        });

        if (response.status == 200) {
            const data = await response.json();
            if(data.correct){
                document.getElementById(pressedButtonId).innerHTML = enteredWord;
                document.getElementById(pressedButtonId).disabled = true;
                if (pressedButtonId == 'A5' || pressedButtonId == 'B5' || pressedButtonId == 'C5' || pressedButtonId == 'D5') {
                    var column = pressedButtonId.replace(/[0-9]/g, '');
                    console.log(column)
                    let columnButton;
                    for (let i = 1; i <= 4; i++) {
                        columnButton = document.getElementById(column + i);
                        await showWord(columnButton)
                    }
                }
                if(pressedButtonId == 'final'){
                    showAll();
                    timer("stop");
                    await finishGame();
                }
            }
        }

    }catch (error) {
        console.log(error);
    }finally{
        closeModal();
        document.getElementById('inputField').value = "";//sredi ovo
    }
}

function showAll(){
    let buttons = document.getElementsByClassName('disable');
    for(let button of buttons){
        button.disabled=true;
        button.classList.add("modalColor")
        showWord(button);
    }
}

async function finishGame(){
    const response = await fetch('http://' + window.location.host + '/asocijacija/finishGame', {
            method: 'PUT'
    });
    if (response.status !== 200) {
        console.log('Error: ' + response.status);
        return;
    }
    isActiveGame=false;
    closeModal();
}


const timer = (order) => {
    if (order == "stop") {
        clearInterval(countdown);
        isActiveGame=false;
    } else {
        countdown = setInterval(function () {
            if(isActiveGame){
                seconds--;
            }
            displayTimer.textContent=seconds;
            if (seconds === 0) {
                timer("stop");
                finishGame()
                displayTimer.textContent='0';
                //return displayTimer.innerHTML = '0';
            }
            //displayTimer.innerHTML = seconds;
        }, 1000);
    }
}