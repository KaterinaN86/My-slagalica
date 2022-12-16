//Modal related code

// Get the modal
var modal = document.getElementById("myModal");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal 
function openModal(pressedButton) {
  modal.style.display = "block";
  pressedButtonId = pressedButton.id;
  console.log(pressedButtonId);
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
  modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        closeModal();
      }
}

const closeModal = () =>{
      modal.style.display = "none";
}



//Game related code
var gameId;
var pressedButtonId;
var numberOfOpenedFields=0;

document.addEventListener("DOMContentLoaded", ()=>{
    handleNewGame();
});

const handleNewGame = () =>{
    fetch('http://' + window.location.host + '/asocijacija/play')
    .then(
        (response) => {
            if (response.status !== 200) {
                console.log('Error: ' + response.status);
                return;
            }

            response.json().then((data) => {
                gameId = data.gameId;
                console.log(gameId);
                //timer();
            });
        }
    )
    .catch((error) => {
        console.log('Fetch error: ', error);
    })
}

const showWord = async (pressedButton)=>{
    var sendObject={
        gameId:gameId,
        fieldName:pressedButton.id
    }
    fetch('http://' + window.location.host + '/asocijacija/' + sendObject.gameId + '/getField/' + sendObject.fieldName)
    .then(
        (response) => {
            if (response.status !== 200) {
                console.log('Error: ' + response.status);
                return;
            }

            response.json().then((data) => {
                console.log(numberOfOpenedFields);
                pressedButton.innerHTML = data.fieldValue;
                console.log(data.fieldValue);
                //timer();
            });
        }
    )
    .catch((error) => {
        console.log('Fetch error: ', error);
    })
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
                document.getElementById(pressedButtonId).disabled = true; //ovo iskombinovati sa onim dole disable
                if(pressedButtonId=='final'){
                    showAll();
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
    closeModal();
}




