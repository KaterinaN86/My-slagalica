function exit() {
    window.location.href="/";
}

async function findGame() {
    const response = await fetch("http://" + window.location.host + "/findingGame", {
        method: 'POST'
    });
    if (response.status == 200) {
        let btn = document.getElementById("findGameId")
        if (btn.innerText == "Find Game") {
            btn.innerText = "Cancel"

            document.getElementById("waitingAnimation").style.visibility ="visible"
            //document.getElementById("waitingAnimation").style.display="flex"
        } else {
            btn.innerText = "Find Game"
            document.getElementById("waitingAnimation").style.visibility ="hidden"
            //document.getElementById("waitingAnimation").style.display="none"
        }
    }
}
