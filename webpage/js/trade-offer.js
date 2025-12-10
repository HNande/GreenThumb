const tradeOffers = document.getElementById("tradeOfferTable")

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
