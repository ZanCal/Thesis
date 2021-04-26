

$(document).ready(function(){
	$('#tab1A').click();
});

function checksetVis(id,  kTeams){
	
	if(Number(id.charAt(4)) <= Number(kTeams) == false){
		document.getElementById(id).style.visibility = 'hidden';
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

