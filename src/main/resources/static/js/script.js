function getGrids() {
    fetch('http://localhost:8080/grids', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }

        })
        .then(response => response.json())
        .then(data => {
            let array = data;
            const table = document.getElementById('grids');
            for (var i = 0; i < array.length; i++) {
                let tr = document.createElement('tr');
                for (var j = 0; j < array.length; j++) {
                    let td = document.createElement('td');
                    if (array[i][j] == 1) {
                        td.style.backgroundColor = "orange";
                    }
                    tr.appendChild(td);

                }
                table.appendChild(tr);
            }
        })
}

function getNextGen() {
    fetch('http://localhost:8080/moveToNext', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }

        })
        .then(response => response.json())
        .then(data => {
            let array = data;
            const table = document.getElementById('grids');

            table.innerHTML = "";
            for (var i = 0; i < array.length; i++) {
                let tr = document.createElement('tr');
                for (var j = 0; j < array.length; j++) {
                    let td = document.createElement('td');
                    if (array[i][j] == 1) {
                        td.style.backgroundColor = "orange";
                    }
                    tr.appendChild(td);

                }
                table.appendChild(tr);
            }
            let gen = parseInt(document.getElementById("generation").innerHTML);
            document.getElementById("generation").innerHTML = gen + 1;
        })

}

function moveBack() {
    fetch('http://localhost:8080/moveBack', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }

        })
        .then(response => response.json())
        .then(data => {
            let array = data;
            const table = document.getElementById('grids');

            table.innerHTML = "";
            for (var i = 0; i < array.length; i++) {
                let tr = document.createElement('tr');
                for (var j = 0; j < array.length; j++) {
                    let td = document.createElement('td');
                    if (array[i][j] == 1) {
                        td.style.backgroundColor = "orange";
                    }
                    tr.appendChild(td);

                }
                table.appendChild(tr);
            }
            let gen = parseInt(document.getElementById("generation").innerHTML);
            document.getElementById("generation").innerHTML = gen - 1;
        })

}

var animate = null;

function play() {

    if (animate != null) {
        stop();
    } else {
        start();
    }
    change();
}

var playBtn = document.getElementById('play');

function change() {
    if (playBtn.textContent == "Play") {
        playBtn.textContent = "Stop";
    } else {
        playBtn.textContent = "Play";
    }
}

 function start() {
	getNextGen()
    animate =  setTimeout(start, 50);
}

function stop() {
    clearTimeout(animate);
    animate = null;
}

window.onload = function() {
    isPlay = false;
    document.getElementById("generation").innerHTML = 0;
    getGrids();

};