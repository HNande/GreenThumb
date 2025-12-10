const tasks = document.getElementById("recordedTaskTable")

fetch('../json/recordedTaskJsonFile.json')
.then(response => response.json())
.then(data=>{
    for (let i = 0; i < data.recordedTaskList.length; i++) {
    let row = document.createElement("tr");
    
    let nameCell = document.createElement("td");
    nameCell.innerHTML = data.recordedTaskList[i].name;
    row.appendChild(nameCell);

    let pointCell = document.createElement("td");
    pointCell.innerHTML = data.recordedTaskList[i].pointAmount;
    row.appendChild(pointCell);

    let typeCell = document.createElement("td");
    const taskType = data.recordedTaskList[i].taskType
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
    let taskOwnerCell = document.createElement("td");
    taskOwnerCell.innerHTML = data.recordedTaskList[i].taskOwner;
    row.appendChild(taskOwnerCell);

    let timeOfRecordCell = document.createElement("td");
    timeOfRecordCell.innerHTML = 
    data.recordedTaskList[i].timeOfRecord.day+"/"
    +data.recordedTaskList[i].timeOfRecord.month+"/"
    +data.recordedTaskList[i].timeOfRecord.year;
    row.appendChild(timeOfRecordCell);

    


    tasks.appendChild(row);
  }
})
  .catch(error => console.error('Error fetching tasks:', error));