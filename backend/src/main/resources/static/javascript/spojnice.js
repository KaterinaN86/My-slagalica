var matchedPairsCount = 0;

var addedSendButton = false;
var clickedButton;
var gameId;

var SpojnicePairs = [];

function $(id) {
	return document.getElementById(id);
}

var start = $()
var button1 = $("btn1");
var button2 = $("btn2");
var button3 = $("btn3");
var button4 = $("btn4");
var button5 = $("btn1");
var button6 = $("btn2");
var button7 = $("btn3");
var button8 = $("btn4");
var button9 = $("btn1");
var button12 = $("btn2");
var button11 = $("btn3");
var button12 = $("btn4");
var button13 = $("btn1");
var button14 = $("btn2");
var button15 = $("btn3");
var button16 = $("btn4");
var timer=$("timer");


document.addEventListener("DOMContentLoaded", ()=>{
    createNewGame();

    function getSpojnicePairs() {
    fetch(('http://localhost:8080/spojnice/start').then(
        (response) => {
            if (response.status !== 200) {
            console.log('Error: ' response.status);
            return;
            }

            response.json().then((data) => {
            gameId = data.id;
            matchedPairsCount = data.matchedPairsCount;
            console.log(gameId);
            console.log(matchedPairsCount);
            });
        }
    }
    )

    .catch((error) => {
    console.log('Error fetch:' error)
    })
}

function setGame(gameObject) {
    gameId = gameObject.id
    isActiveGame = gameObject.isActiveGame;
    numberOfPoints = gameObject.numberOfPoints;
    currentPairsModel = gameObject.currentPairsModel;
 }
}


function getNumberOfPoints(){
     fetch('http://localhost:8080/spojnice/points'+gameId).then(
        (response) => {
                if (response.status !== 200) {
                    console.log('Error: ' + response.status);
                    return;
                }

                response.json().then((data) => {
                    alert("Score : " + data);
                });
            }
        )
}

const startTheTimer = function(){
    let time = 90;
    const timerInterval = setInterval (function() {
        const sec=String(time%60).padStart(2,0);
        timer.textContent = ` ${sec} `;
        if(time === 0){
            clearInterval(timerInterval);
            getNumberOfPoints();
        }
        time--;
    },1000)
    getQuizSet(0);
}

window.onload = startTheTimer();

const finishGame = () =>{
    closeModal();
    let buttons = document.getElementsByClassName('disable');
    for(let button of buttons){
        button.disabled=true;
        showPairs(button);
    }