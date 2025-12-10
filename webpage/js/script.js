const tradeOffers = document.getElementById("tradeOfferTable")
const tasks = document.getElementById("recordedTaskTable")
const communityDesc = document.getElementById("communityDesc")
const communityPoints = document.getElementById("communityPoints")
const communityThreshold = document.getElementById("communityThreshold")

//Fetching for task.html
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



//fetching for trade-offer.html
fetch('../json/tradeOfferjsonFile.json')
.then(response => response.json())
.then(data=>{
    for (let i = 0; i < data.tradeOfferList.length; i++) {
    let row = document.createElement("tr");
    
    let nameCell = document.createElement("td");
    nameCell.innerHTML = data.tradeOfferList[i].name;
    row.appendChild(nameCell);

    let proposerCell = document.createElement("td");
    proposerCell.innerHTML = data.tradeOfferList[i].proposer.firstName+" "+data.tradeOfferList[i].proposer.lastName;
    row.appendChild(proposerCell);

    let costCell = document.createElement("td");
    costCell.innerHTML = data.tradeOfferList[i].cost;
    row.appendChild(costCell);

    let descCell = document.createElement("td");
    descCell.innerHTML = data.tradeOfferList[i].description;
    row.appendChild(descCell);

    tradeOffers.appendChild(row);
  }
})
  .catch(error => console.error('Error fetching tasks:', error));

  //fetching for community.html
fetch('../json/communityJsonFile.json')
.then(response => response.json())
.then(data=>{
    communityDesc.innerHTML = data.rewardDescription;
    communityPoints.innerHTML = data.communityPoints;
    communityThreshold.innerHTML = data.rewardThreshold;


})
  .catch(error => console.error('Error fetching tasks:', error));


