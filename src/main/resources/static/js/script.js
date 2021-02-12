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

                const row = document.createElement('tr');
                table.appendChild(row);
                for (var j = 0; j < array.length; j++) {
                    console.log("asd");
                    const col = document.createElement('td');
                    if (array[i][j]) {
                        col.style.backgroundColor = "orange";
                    }
                    row.appendChild.col;


                }
            }
        })
}

window.onload = function() {
    getGrids();

};