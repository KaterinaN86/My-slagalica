var gameLetters = ""
var computerWord = ""
var gameId
var lastClickedButtons = []
var timer;
function $(id) {
	return document.getElementById(id);
}
var mainTimer=$('timer');
var stopButton=$('stopButton');
var backButton=$('backButton');
var isActiveGame=true;
let time=90;

backButton.addEventListener("click", goBack);
document.addEventListener("DOMContentLoaded", () => {
    stopButton.disabled = true;
    fastAlphabetSwitch();
    handleNewGame();
});


const handleNewGame = () => {
    fetch('http://' + window.location.host + '/slagalica/play')
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
                    gameLetters = data.lettersForFindingTheWord
                    console.log(gameLetters)
                    stopButton.disabled=false;
                    setLettersToButtons(data)
                });

            }

        )

        .catch((error) => {

            console.log('Fetch error: ', error);

        })

}

const submitUserWord = async (submitedUserWord, lettersForUserWord) => {
    startTimer("stop");
    var combinationObject = {
        gameId: gameId,
        lettersForFindingTheWord: lettersForUserWord,
        userWord: submitedUserWord,
    }

    try {

        const response = await fetch('http://' + window.location.host + '/slagalica/wordSubmit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(combinationObject)
        });

        if (response.status == 200) {
            const data = await response.json();
            if(data!=-1){
                //alert("Time out, you earned " + data + " points")
                document.getElementById('finalPoints').textContent = "You earned " + data.points + " points."
                document.getElementById('computerWordTxt').textContent += "Computer word is: " + data.longestWord
            }else{
                document.getElementById('finalPoints').textContent = "You can only submit once."
                document.getElementById('computerWordTxt').textContent += ""
            }
            disableButtons()
        }

    } catch (error) {
        console.log(error)
    }



}

function goBack() {
     if(!isActiveGame){
            history.back();
    }
    if(confirm("Do you want to finish the game?")){
            finishGame().then(function () {
                history.back();
            });
    }
}
async function finishGame(){
    const response = await fetch('http://' + window.location.host + '/slagalica/finishGame', {
        method: 'PUT'
    });
    if (response.status !== 200) {
        console.log('Error: ' + response.status);
        return;
    }
    isActiveGame=false;
}

function leaveGame(){
    startTimer("stop");
    finishGame().then(function(){
        history.back();
    })
}

function handleResponse() {

    var userWord = document.getElementById('userWord').textContent
    submitUserWord(userWord, gameLetters)
}



const setLettersToButtons = (data) => {
    document.getElementById('stopButton').onclick = function () {

        clearInterval(timer)
        timer=null;
        startTimer();
        const buttons = ['btn1', 'btn2', 'btn3', 'btn4', 'btn5', 'btn6', 'btn7', 'btn8', 'btn9', 'btn10', 'btn11', 'btn12']
        let m=0;
        for (let i = 0; i < data.lettersForFindingTheWord.length; i++) {

            if(m!=0 && m < data.lettersForFindingTheWord.length){
                if(m != data.lettersForFindingTheWord.length-1 && (data.lettersForFindingTheWord[m+1] == 'j' || data.lettersForFindingTheWord[m+1] == 'ž')){
                        document.getElementById(buttons[i]).textContent = data.lettersForFindingTheWord[m]+data.lettersForFindingTheWord[m+1]
                        m=m+2
                }
                else{
                    document.getElementById(buttons[i]).textContent = data.lettersForFindingTheWord[m]
                    m++;
                }
           }
           else if(i != data.lettersForFindingTheWord.length-1 && (data.lettersForFindingTheWord[i+1] == 'j' || data.lettersForFindingTheWord[i+1] == 'ž')){
               document.getElementById(buttons[i]).textContent = data.lettersForFindingTheWord[i]+data.lettersForFindingTheWord[i+1]
               m=i+2;
           }
           else if(m==0){
               document.getElementById(buttons[i]).textContent = data.lettersForFindingTheWord[i]
           }
        }
        document.getElementById('stopButton').setAttribute("disabled", "disabled")
    }


}

function parseButtonText(elem) {

    const btnWord = document.getElementById('userWord');
    const buttons = ['btn1', 'btn2', 'btn3', 'btn4', 'btn5', 'btn6', 'btn7', 'btn8', 'btn9', 'btn10', 'btn11', 'btn12']

    buttons.forEach(buttonLetter => {
        if (elem.id == buttonLetter) {

            var buttonLetter = document.getElementById(buttonLetter)
            btnWord.textContent += buttonLetter.textContent
            buttonLetter.setAttribute("disabled", "disabled")
            lastClickedButtons.push(buttonLetter)
            //console.log(lastClickedButtons)


        }
    });
}

function deleteOnClick() {

    var wordUpdate = document.getElementById('userWord').textContent;
    if(wordUpdate.charAt(wordUpdate.length-1)=='j' || wordUpdate.charAt(wordUpdate.length-1)=='ž'){
        document.getElementById('userWord').textContent = wordUpdate.substring(0, wordUpdate.length - 2)
    }
    else{
        document.getElementById('userWord').textContent = wordUpdate.substring(0, wordUpdate.length - 1)
    }

    lastClickedButtons[lastClickedButtons.length - 1].removeAttribute("disabled")
    lastClickedButtons.pop(lastClickedButtons[lastClickedButtons.length - 1])


}

function fastAlphabetSwitch() {
    var alphabet = ['A', 'B', 'C', 'Č', 'Ć', 'D', 'Dž', 'Đ', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'Lj', 'M', 'N', 'Nj', 'O', 'P', 'R', 'S', 'Š', 'T', 'U', 'V', 'Z', 'Ž']

    const buttons = ['btn1', 'btn2', 'btn3', 'btn4', 'btn5', 'btn6', 'btn7', 'btn8', 'btn9', 'btn10', 'btn11', 'btn12']
    var idx = 0;


    timer = setInterval(() => {
        buttons.forEach(buttonElem => {
            document.getElementById(buttonElem).textContent = alphabet[idx++ % alphabet.length];

        });

    }, 200);

}

function disableButtons() {

    const buttons = ['btn1', 'btn2', 'btn3', 'btn4', 'btn5', 'btn6', 'btn7', 'btn8', 'btn9', 'btn10', 'btn11', 'btn12']

    buttons.forEach(buttonLetter => {

        document.getElementById(buttonLetter).setAttribute("disabled", "disabled")


    });

    document.getElementById('submitButton').setAttribute("disabled", "disabled")
    document.getElementById('deleteLetter').setAttribute("disabled", "disabled")
    document.getElementById('stopButton').setAttribute("disabled", "disabled")

}
const startTimer=(order) =>{
    if (order == "stop") {
        clearInterval(timerInterval);
        isActiveGame=false;
    }
    else {
        timerInterval=setInterval(function(){
                const min = String(Math.trunc(time/60)).padStart(2,0);
                const sec=String(time%60).padStart(2,0);
                mainTimer.textContent=`${min} : ${sec}`;
                if(time === 0){
                    clearInterval(timerInterval);
                    handleResponse();
                }
                time--;
            },1000)
    }
}