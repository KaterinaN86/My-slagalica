function $(id) {
	return document.getElementById(id);
}

function getRangList(){
    fetch('http://'+window.location.host+'/GetRangList').then(
        (response) => {
                    if (response.status !== 200) {
                        console.log('Error: ' + response.status);
                        return;
                    }

                    response.json().then((data) => {
                        buildTable(data);
                    });
                }
        )
}

function buildTable(games){
    var table=$("ranglistatable");
    for (var i = 0; i < games.length; i++){
        var row = `<tr>
                        <td>${games[i].userName}</td>
                        <td>${games[i].numberOfPoints}</td>
                  </tr>`
        table.innerHTML += row

    }
}



window.onload = getRangList();