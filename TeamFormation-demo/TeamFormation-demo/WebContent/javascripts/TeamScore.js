function pressButton(){
	console.log("A button has been pressed");
	
}

function plusButton(team){
	let score = parseInt(team.innerHTML);
	score += 1;
	team.innerHTML = score;
}

function minusButton(team){
	let score = parseInt(team.innerHTML);
	score -= 1;
	team.innerHTML = score;
}