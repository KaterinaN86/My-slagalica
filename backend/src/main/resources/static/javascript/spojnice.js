document.addEventListener("DOMContentLoaded",()=>{init()})

let dataForSubmit = {}
let selectedLeftBtn = null;

function init() {
    document.getElementById("exitBtn").addEventListener("click",()=>{
        window.location.href="/OnePlayer"
    })
    fetch("http://'+window.location.host+'/spojnice/start").then((response) => {
        response.json().then((data) => {
            document.getElementById("submitBtn").addEventListener("click", submitData)

            let tbody = document.getElementById("tbody");

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
    let buttons=document.getElementsByTagName("button")
    for(let i=0 ; i<buttons.length-1;i++){
        buttons[i].disabled=true
    }
    fetch("http://'+window.location.host+'/spojnice/submit", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dataForSubmit)
    }).then((response) => {
       response.json().then((data) => {
           console.log(data)
       });
    }).catch((error) => {
        console.log(error)
    });
}
