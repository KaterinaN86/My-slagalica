var gameLetters = ""
var gameId
var lastClickedButtons = []
var timer;

document.addEventListener("DOMContentLoaded", () => {
    handleNewGame();
    fastAlphabetSwitch();
});


const handleNewGame = () => {

    fetch('http://localhost:8080/slagalica/play')

        .then(

            (response) => {

                if (response.status !== 200) {

                    console.log('Error: ' + response.status);

                    return;

                }

                response.json().then((data) => {

                    gameLetters = data.lettersForFindingTheWord
                    gameId = data.id
                    console.log(gameLetters)
                    setLettersToButtons(data)

                });

            }

        )

        .catch((error) => {

            console.log('Fetch error: ', error);

        })

}

const submitUserWord = async (submitedUserWord, lettersForUserWord) => {

    var combinationObject = {

        gameId: gameId,
        lettersForFindingTheWord: lettersForUserWord,
        userWord: submitedUserWord,


    }

    try {

        const response = await fetch('http://localhost:8080/slagalica/wordSubmit', {

            method: 'POST',

            headers: {

                'Content-Type': 'application/json'

            },

            body: JSON.stringify(combinationObject)

        });

        if (response.status == 200) {

            const data = await response.json();
            console.log(data)
            //alert("Time out, you earned " + data + " points")
            document.getElementById('finalGameResult').textContent = 'Igra je završena, osvojili ste ' + data + ' bodova'
            disableButtons()

        }

    } catch (error) {

        console.log(error)

    }



}

function handleResponse() {

    var userWord = document.getElementById('userWord').textContent
    submitUserWord(userWord, gameLetters)
}



const setLettersToButtons = (data) => {


    document.getElementById('stopButton').onclick = function () {

        clearInterval(timer)
        const buttons = ['btn1', 'btn2', 'btn3', 'btn4', 'btn5', 'btn6', 'btn7', 'btn8', 'btn9', 'btn10', 'btn11', 'btn12']

        for (let i = 0; i < data.lettersForFindingTheWord.length; i++) {

            document.getElementById(buttons[i]).textContent = data.lettersForFindingTheWord[i]

        }

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
            console.log(lastClickedButtons)


        }
    });
}

function deleteOnClick() {

    var wordUpdate = document.getElementById('userWord').textContent;
    document.getElementById('userWord').textContent = wordUpdate.substring(0, wordUpdate.length - 1)

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

    }, 150);

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