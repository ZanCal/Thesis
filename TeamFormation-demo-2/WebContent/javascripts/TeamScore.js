	

function pressButton(){
	console.log("A button has been pressed");
}

function loadScore(id, key){
	
	if(Number.isNaN(Number(window.localStorage.getItem(key)))){
		document.getElementById(id).innerHTML = 0;
	} else {
		document.getElementById(id).innerHTML =  Number( window.localStorage.getItem(key));
	}
}


function plusButton(team, ids){
	
	let selfID = 'Good' + team.id.charAt(5);
	let otherID = 'Bad' + team.id.charAt(5);
	
	let self = document.getElementById(selfID);
	let other = document.getElementById(otherID);
	
	let score = parseInt(team.innerHTML);
	if(self.classList.contains('selected') == false){
		score += 1;
		
	}
	
	//document.getElementById(selfID).disabled = true;
	$(self).addClass('selected');
	
	if(other.classList.contains('selected')){
		other.classList.remove('selected');
		score += 1;
	}
	//if(document.getElementById(otherID).disabled == true){
	//	document.getElementById(otherID).disabled = false;
	//	score += 1;
	//}
	team.innerHTML = score;

	console.log(ids);
	
	window.localStorage.setItem(ids, score);
	
	console.log(Number(window.localStorage.getItem(ids)));
	
	
}

function minusButton(team, ids){
	let selfID = 'Bad' + team.id.charAt(5);
	let otherID = 'Good' + team.id.charAt(5);
	
	let self = document.getElementById(selfID);
	let other = document.getElementById(otherID);
	
	
	let score = parseInt(team.innerHTML);

	if(self.classList.contains('selected') == false){
		score -= 1;		
	}
	
	
	$(self).addClass('selected');
	
	if(other.classList.contains('selected')){
		other.classList.remove('selected');
		score -= 1;
	}
	
	//document.getElementById(selfID).disabled = true;
	
	
	//if(document.getElementById(otherID).disabled == true){
	//	document.getElementById(otherID).disabled = false;
	//	score -= 1;
	//}
	
	team.innerHTML = score;
	
	console.log(ids);
	
	window.localStorage.setItem(ids, score);
	
	console.log(Number(window.localStorage.getItem(ids)));
	
}

