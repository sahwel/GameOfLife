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
                    console.log("asd");
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

window.onload = function() {
    getGrids();

};