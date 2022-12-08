var row_count = 0;
var cell_count = 0;

// Pointers to table elements to be manipulated later
var table = document.getElementById("guessTable");
var guess_rows = table.getElementsByClassName("tableRow");
var currentRow = guess_rows[0];
var cells = currentRow.getElementsByClassName("tableCell");

var numberOfAttempts = 0;
var addedSendButton = false;
var clickedButton;
var gameId;

// User picked combination to be filled with symbols from the buttons clicked
var combination = [];

var display = document.getElementById("display");
var seconds = 120;
var countdown = null;


document.addEventListener("DOMContentLoaded", ()=>{
    handleNewGame();
});

const handleNewGame = () =>{
    fetch('http://localhost:8080/skocko/play')
    .then(
        (response) => {
            if (response.status !== 200) {
                console.log('Error: ' + response.status);
                return;
            }

            response.json().then((data) => {
                gameId = data.id;
                timer();
            });
        }
    )
    .catch((error) => {
        console.log('Fetch error: ', error);
    })
}

function displaySymbol(object) {
    var text = object.innerHTML;

    if (text == " ⇐ ") {
        if (cell_count != 0) {
            cells[cell_count - 1].innerHTML = "";
            cell_count--;
        }
    } else if (text == " ↺ ") {
        //timer("stop");
        window.location.href="OnePlayer";
    } else if (text == " ⚐ ") {
        handleLosingGame();
    } else {
        cells[cell_count].innerHTML = text;
        cells[cell_count].style.backgroundColor = "#00897B";
        if (text == " ♥ ") {
            cells[cell_count].style.color = "darkred";
            combination[cell_count] = 0;
            console.log(combination);
        } else if (text == " ♦ ") {
            cells[cell_count].style.color = "darkred";
            combination[cell_count] = 1;
            console.log(combination);
        } else if (text == " ★ ") {
            cells[cell_count].style.color = "gold";
            combination[cell_count] = 2;
            console.log(combination);
        } else if (text == " ☻ ") {
            cells[cell_count].style.color = "gold";
            combination[cell_count] = 3;
            console.log(combination);
        } else if (text == ' ♠ ') {
            cells[cell_count].style.color = "black";
            combination[cell_count] = 4;
            console.log(combination);
        } else {
            cells[cell_count].style.color = "black";
            combination[cell_count] = 5;
            console.log(combination);
        }

        if (cell_count == 3) {
            handleAddingSendButton();
        } else {
            cell_count++;
        }
    }
}

const handleAddingSendButton = () => {
    cell_count++;
    childToAppend = document.createElement('div');
    childToAppend.setAttribute("class", "resultCell");
    childToAppend.innerHTML = "SEND";
    childToAppend.setAttribute("class", "sendCell buttons commands emojis");
    childToAppend.addEventListener("click", () => { handleSend(event.target) })
    if (!addedSendButton) {
        currentRow.append(childToAppend);
        addedSendButton = true;
    }
}

const handleSend = (button) => {
    button.setAttribute("disabled", true);
    clickedButton = button;
    submitCombination(combination);
    if (numberOfAttempts < 5) {
        cell_count = 0;
        row_count += 1;
        currentRow = guess_rows[row_count];
        cells = currentRow.getElementsByClassName("tableCell");
        addedSendButton = false;
        combination = [];
    }
}

const submitCombination = async (submittedCombination) => {
    var combinationObject = {
        gameId: gameId,
        combination: submittedCombination,
        attempt: numberOfAttempts
    }

    try {
        const response = await fetch('http://localhost:8080/skocko/submit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(combinationObject)
        });

        if (response.status == 200) {
            const data = await response.json();
            isWinningCombination(data);
        }

    }catch (error) {
        console.log(error)
    }finally{
        numberOfAttempts++;
    }

}

const isWinningCombination = (data) => {
    if (data.winning) {
        handleWinningCombination(data);
    } else {
        handleNotWinningCombination(data);
    }
}

const handleWinningCombination = (data) => {
    disableSymbolsButtons();
    disableGiveUpButton();
    cleanElementContent(clickedButton);
    timer("stop");
    printDots(clickedButton, 4, 'red');
    printMessages(true,data.points);
    printCombination(data.combination, document.getElementById("finalTable").children[0].children);
}

const handleNotWinningCombination = (data) => {
    cleanElementContent(clickedButton);
    clickedButton.style.pointerEvents = 'none';
    let emptyPositions =  4 - (data.goodPositions + data.badPositions);
    printDots(clickedButton,data.goodPositions, 'red');
    printDots(clickedButton,data.badPositions, 'yellow')
    printDots(clickedButton,emptyPositions, 'white');
    if(numberOfAttempts==5){
        handleLosingGame();
    }
}

const handleLosingGame = async () => {
    fetch('http://localhost:8080/skocko/getCombination')
        .then(
            (response) => {
                if (response.status !== 200) {
                    console.log('Error: ' + response.status);
                    return;
                }

                response.json().then((data) => {
                    printMessages(false,0);
                    printCombination(data, document.getElementById('finalTable').children[0].children);
                    timer("stop");
                    disableSymbolsButtons();
                });
            }
        )
        .catch((error) => {
            console.log('Fetch error: ', error);
        })
} 

const printCombination = (data, element) => {//data nek bude kombinacija, tj. onaj niz brojeva, element je niz divova gdje želimo da printamo kombinaciju
    for (let i = 0; i < data.length; i++) {
        if (data[i] == 0) {
            element[i].innerHTML = " ♥ ";
            element[i].style.color = "darkred";
        } else if (data[i] == 1) {
            element[i].innerHTML = " ♦ ";
            element[i].style.color = "darkred";
        } else if (data[i] == 2) {
            element[i].innerHTML = " ★ ";
            element[i].style.color = "gold";
        } else if (data[i] == 3) {
            element[i].innerHTML = " ☻ ";
            element[i].style.color = "gold";
        } else if (data[i] == 4) {
            element[i].innerHTML = " ♠ ";
            element[i].style.color = "black";
        } else {
            element[i].innerHTML = " ♣ ";
            element[i].style.color = "black";
        }
    }
}

const printDots = (element, numberOfDots, color)=>{//printanje emoji-a
    if(color=='red'){
        for (let i = 0; i < numberOfDots; i++) {
            element.innerHTML += "🔴";
        }
    }else if(color=='yellow'){
        for (let i = 0; i < numberOfDots; i++) {
            element.innerHTML +="🟡";
        }
    }else{
        for (let i = 0; i < numberOfDots; i++) {
            element.innerHTML +="⚪";
        }
    }
}

const cleanElementContent = (element) =>{//ciscenje innerHTML-a proslijedjenog elementa
    element.innerHTML="";
}

const printMessages = (isWinning, points) =>{
    var messageElement = document.getElementById('message');
    var pointsElement = document.getElementById('result');
    if(isWinning){
        messageElement.innerHTML = 'Čestitamo, pogodili ste traženu kombinaciju !';
    }else{
        messageElement.innerHTML = 'Izgubili ste. Tražena kombinacija izgleda ovako:';
    }
    pointsElement.innerHTML = 'Osvojili ste ' + points + ' bodova.';
    messageElement.style.visibility = 'visible';
    pointsElement.style.visibility = 'visible';
}

const timer = (order) => {
	if (order == "stop") {
		clearInterval(countdown);
		seconds = 120;
	} else {
		countdown = setInterval(function() {
			if (seconds === 0) {
				timer("stop");
				handleLosingGame();
				return;
			}
			display.innerHTML = seconds;
			seconds--;
		}, 1000);
	}
}

const disableSymbolsButtons = () =>{
    let buttons = document.querySelectorAll(".buttons");
    buttons.forEach((button) => {
        button.style.pointerEvents = 'none';
    })
}

const disableGiveUpButton = ()=>{
    let button = document.getElementById('giveupbutton');
    button.style.pointerEvents = 'none';
}



