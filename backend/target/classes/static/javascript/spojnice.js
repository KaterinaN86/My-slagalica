let dataForSubmit = {}
let selectedLeftBtn = null;
var timer=document.getElementById("timer");

function init() {
    document.getElementById("exitBtn").addEventListener("click",()=>{
        window.location.href="/OnePlayer"
    })
    fetch('http://' + window.location.host + '/spojnice/start').then((response) => {
        response.json().then((data) => {
            document.getElementById("submitBtn").addEventListener("click", submitData)
            console.log(data);
            let tbody = document.getElementById("tbody");
            document.getElementById("headline").textContent=data[16];
            for (let i = 0 ; i < 8; i++) {
                let tr = document.createElement("tr")
                let td1 = document.createElement("td")

                let btn1 = document.createElement("button")
                btn1.addEventListener("click",(event) => {
                    if (selectedLeftBtn != null){
                        selectedLeftBtn.classList.remove("selected")
                    }
                    selectedLeftBtn=event.target
                    event.target.classList.add("selected")
                })
                btn1.innerText=data[i]

                td1.appendChild(btn1)
                let td2 = document.createElement("td")
                let btn2 = document.createElement("button")
                btn2.addEventListener("click", (event) =>{
                    if (selectedLeftBtn == null){
                        return
                    }
                    selectedLeftBtn.disabled=true
                    event.target.disabled=true
                    dataForSubmit[selectedLeftBtn.innerText] = event.target.innerText
                    event.target.classList.add("selected")
                    selectedLeftBtn=null
                })
                btn2.innerText=data[i+8]

                td2.appendChild(btn2)
                tr.appendChild(td1)
                tr.appendChild(td2)
                tbody.appendChild(tr)
            }
        });
    })
}

function submitData(event){
    event.target.disabled=true;
    disableButtons();
    submitAndGetPoints();
}
function disableButtons(){
    let buttons=document.getElementsByTagName("button")
    for(let i=0 ; i<buttons.length-1;i++){
        buttons[i].disabled=true
    }
}

async function submitAndGetPoints(){
    fetch('http://' + window.location.host + '/spojnice/submit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dataForSubmit)
        }).then((response) => {
           response.json().then((data) => {
               console.log(data)
               alert("Score : "+data);
           });
        }).catch((error) => {
            console.log(error)
        });
}

var display = document.getElementById("timer")

const timer = (order) => {
    if (order == "stop") {
        clearInterval(countdown);
        seconds = 120;
    } else {
        countdown = setInterval(function() {
            if (seconds === 0) {
                timer("stop");
                handleLosingGame();
                return display.innerHTML = '0';
            }
            display.innerHTML = seconds;
            seconds--;
        }, 1000);
    }
}
