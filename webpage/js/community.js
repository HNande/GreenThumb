const communityDesc = document.getElementById("communityDesc")
const communityPoints = document.getElementById("communityPoints")
const communityThreshold = document.getElementById("communityThreshold")
const bar = document.getElementById("progressBar")

fetch('../json/communityJsonFile.json')
.then(response => response.json())
.then(data=>{
    communityDesc.innerHTML = data.rewardDescription;
    communityPoints.innerHTML = data.communityPoints;
    communityThreshold.innerHTML = data.rewardThreshold;
    var pointDifference = data.communityPoints/data.rewardThreshold;
    pointDifference = Math.trunc(pointDifference*100);
    console.log(pointDifference);
    if(pointDifference < 1){
      var width = 1;
      bar.style.width = width + "%";
    }else{
      bar.style.width = pointDifference + "%";
      }
})
  .catch(error => console.error('Error fetching tasks:', error));
