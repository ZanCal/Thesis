function generateTeamDisplay(network, id){
	network = "'" + network + "'";
	id = "'" + id + "'";
	
	console.log("Network " +  network)
	console.log("ID "  + id );
	
	var viz = new Viz();
	var svg = viz.renderSVGElement( network );
	svg.then(function(element) {
		document.getElementById(id).appendChild(element)
	});
}

function generateTeamDisplay(){
	var myvar = '${networkF1A}';
	var viz = new Viz();
	var svg = viz.renderSVGElement(myvar);
	svg.then(function(element) { document.getElementById('f1A').appendChild(element)});
	
}