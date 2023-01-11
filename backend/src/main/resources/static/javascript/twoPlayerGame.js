document.addEventListener("DOMContentLoaded",()=>{init()})
var data=null
function init(){
    fetch('http://' + window.location.host + '/TwoPlayer/init')
        .then(
            (response) => {
                if (response.status !== 200) {
                    console.log('Error: ' + response.status);
                    return;
                }

                response.json().then((responseData) => {
                    data=responseData
                    console.log(data)
                    document.getElementById("username1").innerText=data.username1
                    document.getElementById("numOfPoints1").innerText=data.numOfPointsSum1
                    document.getElementById("numOfPointsSlagalica1").innerText=data.numOfPointsSlagalica1
                    document.getElementById("numOfPointsMojBroj1").innerText=data.numOfPointsMojBroj1
                    document.getElementById("numOfPointsSkocko1").innerText=data.numOfPointsSkocko1
                    document.getElementById("numOfPointsKoZnaZna1").innerText=data.numOfPointsKoZnaZna1
                    document.getElementById("numOfPointsSpojnice1").innerText=data.numOfPointsSpojnice1
                    document.getElementById("numOfPointsAsocijacije1").innerText=data.numOfPointsAsocijacija1

                    document.getElementById("username2").innerText=data.username2
                    document.getElementById("numOfPoints2").innerText=data.numOfPointsSum2
                    document.getElementById("numOfPointsSlagalica2").innerText=data.numOfPointsSlagalica2
                    document.getElementById("numOfPointsMojBroj2").innerText=data.numOfPointsMojBroj2
                    document.getElementById("numOfPointsSkocko2").innerText=data.numOfPointsSkocko2
                    document.getElementById("numOfPointsKoZnaZna2").innerText=data.numOfPointsKoZnaZna2
                    document.getElementById("numOfPointsSpojnice2").innerText=data.numOfPointsSpojnice2
                    document.getElementById("numOfPointsAsocijacije2").innerText=data.numOfPointsAsocijacija2
                });
            }
        )
        .catch((error) => {
            console.log('Fetch error: ', error);
        })
}

function rematch() {
    fetch('http://' + window.location.host + '/TwoPlayer/rematch').then(r => {
        console.log(r);
        window.location.reload();
    }).catch((error) => {
        console.log(error);
    })
}

function goBack(){
    if(confirm("If you exit game will be finished.\nAre you sure you want to leave?")){
        fetch('http://' + window.location.host + '/finishGame')
        history.back()
    }
}
