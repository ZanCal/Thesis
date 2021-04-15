

$(document).ready(function(){
	//i want to get the k value into here so i know how many graphs to pre-draw
	//pre draw assuming max k for now
	//assuming max k leads to issues, try doing something with the on click of the tabs instead
	$('#tab1A').click();
	//$('#tab1B').click();
	//$('#tab1C').click();
	//$('#tab1D').click();
	//$('#tab1E').click();
	
	
	
	// i could use a hug
	//and someone who knows how to program
	
});

function checksetVis(id,  kTeams){
	//console.log(id);
	//console.log(kTeams);
	
	//console.log(Number(id.charAt(4)) <= Number(kTeams))
	if(Number(id.charAt(4)) <= Number(kTeams) == false){
		document.getElementById(id).style.visibility = 'hidden';
		//document.getElementById(id).setVisible(false);
	}
}



function genR1A(){
	console.log("genR1A");
	var myvar = '${networkR1A}';
	console.log(myvar);
	var viz = new Viz();
	var svg = viz.renderSVGElement('${networkR1A}')
	.then(function(element) {
		document.getElementById('r1A').appendChild(element);
	})
	.catch(error => {
		console.log(error);
	});
}

