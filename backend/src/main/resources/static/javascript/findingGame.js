window.onbeforeunload=dequeue

function exit() {
    window.location.href="/";
    dequeue()
}
let interval
async function findGame() {
    const response = await fetch("http://" + window.location.host + "/queue");
    if (response.status == 200) {
        let btn = document.getElementById("findGameId")
        data = await response.json()
        if (btn.innerText == "Find Game" && data) {
            btn.innerText = "Cancel"
            document.getElementById("waitingAnimation").style.visibility ="visible"
            interval=window.setInterval(isGameFound,1000)
        } else {
            btn.innerText = "Find Game"
            document.getElementById("waitingAnimation").style.visibility ="hidden"
            window.clearInterval(interval)
            dequeue()
        }

    }
}
async function isGameFound(){
    const response = await fetch("http://" + window.location.host + "/isInGame");
    if (response.status == 200) {
        data = await response.json()
        if(data){
            window.location="http://" + window.location.host + "/twoPlayerGame"
            window.clearInterval(interval)
        }
    }
}
function dequeue(){
    const response = fetch("http://" + window.location.host + "/dequeue");
}

