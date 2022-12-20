
var questionCount = 0;
var selectedOption=0;
var selectedOptionIndex=0;

function $(id) {
	return document.getElementById(id);
}

var quizSet = $("quizSet");
var question = $("question");
var submit = $("submit");
var button1 = $("btn1");
var button2 = $("btn2");
var button3 = $("btn3");
var button4 = $("btn4");
var progress = $("progress");
var nextQuestion = $("next");
var timer=$("timer");
var currentQuestionIndex=0;
var questionList={};
var gameId=0;
var activeGame=false;
var numberOfPoints=0;


function getQuestions(){
    fetch('http://' + window.location.host + '/koZnaZna/play').then(
        (response) => {
                    if (response.status !== 200) {
                        console.log('Error: ' + response.status);
                        return;
                    }

                    response.json().then((data) => {
                        setGameInfo(data.koZnaZnaGame);
                        setQuestions(data.koZnaZnaGame.questions);
                    });
                }
        )
}
function setGameInfo(gameObject){
    gameId=gameObject.id;
    activeGame=gameObject.activeGame;
    numberOfPoints=gameObject.numberOfPoints;
    currentQuestionIndex=gameObject.indexOfTheCurrentQuestion;
}

function setQuestions(questions){
    questionList=questions;
    setQuestion(0);
}

function setQuestion(questionNumber){
    progress.textContent=questionNumber+1+". pitanje od 10";
    question.textContent=questionList[questionNumber].questionContent;
    button1.textContent=questionList[questionNumber].options[0];
    button2.textContent=questionList[questionNumber].options[1];
    button3.textContent=questionList[questionNumber].options[2];
    button4.textContent=questionList[questionNumber].options[3];
}

button1.addEventListener("click",optionSelect);
button2.addEventListener("click",optionSelect);
button3.addEventListener("click",optionSelect);
button4.addEventListener("click",optionSelect);
submit.addEventListener("click", submitSelect);
nextQuestion.addEventListener("click", nextQuestionSelect);


async function submitSelect(){
        var result=0;
        var submitQuestionObject={
            gameId : gameId,
            questionIndex : questionCount,
            questionId : questionList[questionCount].id,
            selectedQuestion : selectedOptionIndex
        }
        const response = await fetch('http://' + window.location.host + '/koZnaZna/submitQuestion', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(submitQuestionObject)
        });
         if (response.status == 200) {
            const data = await response.json();
            switch(data.answer){
            case 1 : button1.style.backgroundColor = "green";
                    break;
            case 2 : button2.style.backgroundColor = "green";
                    break;
            case 3 : button3.style.backgroundColor = "green";
                    break;
            case 4 : button4.style.backgroundColor = "green";
                    break;
            }

        }
}


function nextQuestionSelect(e){
    defaultOptionColors();
    updateQuestionNumber();
    selectedOptionIndex=0;
    if(questionCount==9){
       getNumberOfPoints();
       finishGame();
    }
    else if(questionCount==8){
        questionCount++;
        nextQuestion.textContent="Finish the game";
        setQuestion(questionCount);
    }
    else{
        questionCount++;
        setQuestion(questionCount);
    }
}
async function finishGame(){
    const response = await fetch('http://' + window.location.host + '/koZnaZna/finishGame', {
            method: 'PUT'
        });
        if (response.status !== 200) {
            console.log('Error: ' + response.status);
            return;
        }
}

async function updateQuestionNumber(){
    var submitQuestionNUmber={
        gameId : gameId,
    }
    const response = await fetch('http://' + window.location.host + '/koZnaZna/nextQuestion', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(submitQuestionNUmber)
    });
    if (response.status !== 200) {
        console.log('Error: ' + response.status);
        return;
    }
}
function getNumberOfPoints(){
     fetch('http://' + window.location.host + '/koZnaZna/numberOfPoints').then(
        (response) => {
                if (response.status !== 200) {
                    console.log('Error: ' + response.status);
                    return;
                }

                response.json().then((data) => {
                    alert("Score : "+data);
                });
            }
        )
}

function optionSelect(e) {
    if(selectedOption!=0){
        $(selectedOption).style.backgroundColor="gray";
    }
	e.target.style.backgroundColor = "orange";
    selectedOption=e.target.id;
    selectedOptionIndex=e.target.id.replace("btn","");
}
function defaultOptionColors() {
	button1.style.backgroundColor = "gray";
	button2.style.backgroundColor = "gray";
	button3.style.backgroundColor = "gray";
	button4.style.backgroundColor = "gray";
}

function getQuizSet(questionNumber){
    defaultOptionColors();
    getQuestions();
}

const startTimer=function(){
    let time=120;
    const timerInterval=setInterval(function(){
        const min = String(Math.trunc(time/60)).padStart(2,0);
        const sec=String(time%60).padStart(2,0);
        timer.textContent=`${min} : ${sec}`;
        if(time === 0){
            clearInterval(timerInterval);
            getNumberOfPoints();
            finishGame();
        }
        time--;
    },1000)
    getQuizSet(0);
}

window.onload = startTimer();