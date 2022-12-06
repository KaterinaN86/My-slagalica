document.addEventListener("DOMContentLoaded",()=>{init()})

function init(){
    fetch('http://localhost:8080/OnePlayer/init')
            .then(
                (response) => {
                    if (response.status !== 200) {
                        console.log('Error: ' + response.status);
                        return;
                    }

                    response.json().then((data) => {
                    console.log(data)
                    });
                }
            )
            .catch((error) => {
                console.log('Fetch error: ', error);
            })
}