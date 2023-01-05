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
        } else {
            btn.innerText = "Find Game"
        }
    }
}
