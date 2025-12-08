const tasks = document.getElementById("table")

fetch('../json/taskJsonFile.json')
.then(response => response.json())
.then(data=>{
    for (let i = 0; i < data.taskList.length; i++) {
    let row = document.createElement("tr");
    
    let nameCell = document.createElement("td");
    nameCell.innerHTML = data.taskList[i].name;
    row.appendChild(nameCell);

    let pointCell = document.createElement("td");
    pointCell.innerHTML = data.taskList[i].pointAmount;
    row.appendChild(pointCell);

    let typeCell = document.createElement("td");
    const taskType = data.taskList[i].taskType
    switch(taskType) {
        case 1:
            typeCell.innerHTML = "Community";
            row.appendChild(typeCell);
            break;
        case 2:
            typeCell.innerHTML = "Individual";
            row.appendChild(typeCell);
            break;
        default:
            break;
        } 
    let totalCell = document.createElement("td");
    totalCell.innerHTML = data.taskList[i].totalCount;
    row.appendChild(totalCell);

    tasks.appendChild(row);
  }
})
  .catch(error => console.error('Error fetching tasks:', error));


