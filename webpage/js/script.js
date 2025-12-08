const tasks = document.getElementsByClassName("block")

fetch('./json/taskJsonFile')
.then(response => response.json())
.then(data=>{
    for (let i = 0; i < data.taskList.length; i++) {
    let row = document.createElement("tr");
    
    let nameCell = document.createElement("td");
    nameCell.innerHTML = fetchedTasks.taskList[i].name;
    row.appendChild(nameCell);

    let pointCell = document.createElement("td");
    pointCell.innerHTML = fetchedTasks.taskList[i].pointAmount;
    row.appendChild(pointCell);

    let typeCell = document.createElement("td");
    const taskType = fetchedTasks.taskList[i].taskType
    switch(taskType) {
        case 1:
            typeCell.innerHTML = "Community";
            row.appendChild(listElement);
            break;
        case 2:
            typeCell.innerHTML = "Individual";
            row.appendChild(listElement);
            break;
        default:
            break;
        } 
    let totalCell = document.createElement("td");
    totalCell.innerHTML = fetchedTasks.taskList[i].totalCount;
    row.appendChild(totalCell);

    tasks.appendChild(row);
  }
})
  .catch(error => console.error('Error fetching tasks:', error));


