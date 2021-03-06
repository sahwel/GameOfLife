function getGrids() {
    fetch('http://localhost:8080/grids', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }
        })
        .then(response => response.json())
        .then(data => {
            document.getElementById("generation").innerHTML = 0;
            let array = data;
            let table = document.getElementById('grids');
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
        })
}

function getNewGrids(name) {
    fetch(`http://localhost:8080//newExample/${name}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }
        })
        .then(response => response.json())
        .then(data => {
            document.getElementById("generation").innerHTML = 0;
            let array = data;
            let table = document.getElementById('grids');
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
            let table = document.getElementById('grids');
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
    let gen = parseInt(document.getElementById("generation").innerHTML);
    if (gen > 0) {
        fetch('http://localhost:8080/moveBack', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                }
            })
            .then(response => response.json())
            .then(data => {
                let array = data;
                let table = document.getElementById('grids');
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
}

function getFilesName() {
    fetch('http://localhost:8080/getFilesName', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }
        })
        .then(response => response.json())
        .then(data => {
            let array = data;
            let examples = document.getElementById('examples');
            for (var i = 0; i < array.length; i++) {
                let line = array[i].split('.');
                let example = document.createElement('div');
                example.onclick = () => {
                    getNewGrids(example.innerHTML);
                };
                example.classList.add("example");
                example.innerHTML = line[0];
                examples.appendChild(example);
            }
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
    animate = setTimeout(start, 10);
}

function stop() {
    clearTimeout(animate);
    animate = null;
}

window.onload = function() {
    getFilesName();
    isPlay = false;
    getGrids();


};