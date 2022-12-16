document.addEventListener("DOMContentLoaded",()=>{init()})
var data=null
function init(){
    fetch('http://'+window.location.host+'/OnePlayer/init')
            .then(
                (response) => {
                    if (response.status !== 200) {
                        console.log('Error: ' + response.status);
                        return;
                    }

                    response.json().then((responseData) => {
                    data=responseData
                    document.getElementById("numOfPoints").innerText=data.numOfPointsSum
                        document.getElementById("numOfPointsSlagalica").innerText=data.numOfPointsSlagalica
                        document.getElementById("numOfPointsMojBroj").innerText=data.numOfPointsMojBroj
                        document.getElementById("numOfPointsSkocko").innerText=data.numOfPointsSkocko
                        document.getElementById("numOfPointsKoZnaZna").innerText=data.numOfPointsKoZnaZna
                        document.getElementById("numOfPointsSpojnice").innerText=data.numOfPointsSpojnice
                        document.getElementById("numOfPointsAsocijacije").innerText=data.numOfPointsAsocijacija
                    });
                }
            )
            .catch((error) => {
                console.log('Fetch error: ', error);
            })
}

function newGame() {
    fetch("http://'+window.location.host+'/OnePlayer/newGame").then(r => {
        console.log(r);
        window.location.reload();
    }).catch((error) => {
        console.log(error);
    })
}