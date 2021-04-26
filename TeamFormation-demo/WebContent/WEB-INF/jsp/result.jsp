<!doctype html>
<!--[if lt IE 7 ]><html class="ie ie6" lang="en"> <![endif]-->
<!--[if IE 7 ]><html class="ie ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]><html class="ie ie8" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<!-- style type="text/css">  
		table, th, td {
		  border: 1px solid black;
		  border-collapse: collapse;
		}            
	</style-->

<!-- General Metas -->
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<!-- Force Latest IE rendering engine -->
<title>Performance</title>
<meta name="description" content="">
<meta name="author" content="">
<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

<!-- Mobile Specific Metas -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<link rel="stylesheet" href="styles/results.css" >
<link rel="stylesheet" href="jquery-ui.css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script src="javascripts/node_modules/viz.js/viz.js"></script>
<script src="javascripts/node_modules/viz.js/full.render.js"></script>
<script src="javascripts/TeamScore.js"></script>
<script src="javascripts/GenerateTeamDisplay.js"></script>
<script>
window.onload = function(){
	<% int kTeams = (int) request.getAttribute("K-Selection"); %>
	console.log('this is a test to see if i can get a thing to happen' )
	console.log('<%= kTeams %>');
	
}
</script>
	
</head>
<body>
	<div id='header'>
		<%
		//<h3>Team Formation:</h3>
		
		out.print("<h3> Team Formation using ");
		
		String method = (String) request.getParameter("teamMethodSelection");
		
		switch(method){
		case "s":
			out.print("Shortest Path </h3>");
			out.print("<p> Simply try to find experts with the smallest number of intermediary connections  </p>");
			break;
		case "r":
			out.print("Random Walkers with Restarts </h3>");
			out.print("<p> Try to give connections between experts scores through Random Walkers with Restarts  </p>");
			break;
		case "f":
			out.print("Fast Random Walkers with Restarts </h3>");
			out.print("<p> A fast alternative to the normal Random Walkers with Restarts technique  </p>");
			break;
		case "e":
			out.print("Embedding </h3>");
			out.print("<p> Attempt to find experts by using the embedding technique  </p>");
			break;
		case "h":
			out.print("Shortest Path and Random Walkers with Restarts Hybrid </h3>");
			out.print("<p> Placeholder text describing how the Hybrid Technique works </p>");
			break;
		}
		
		%>
	</div>


<script>
		<% String network; %>
		var viz = new Viz();
		var myNetwork;
		function genTeamDisplayLocal(id){
			switch(id.charAt(0)){
			case '1':
				switch(id.charAt(1)){
				case 'A': 
					myNetwork = '<%= request.getAttribute("network1A")%>'
				break;
				case 'B': 
					myNetwork = '<%= request.getAttribute("network1B")%>' 
				break;
				case 'C': 
					myNetwork = '<%= request.getAttribute("network1C")%>'
				break;
				case 'D': 
					myNetwork = '<%= request.getAttribute("network1D")%>'
				break;
				case 'E': 
					myNetwork = '<%= request.getAttribute("network1E")%>'
				break;
				}
			break;
			case '2':
				switch(id.charAt(1)){
				case 'A': 
					myNetwork = '<%= request.getAttribute("network2A")%>'
				break;
				case 'B': 
					myNetwork = '<%= request.getAttribute("network2B")%>'
				break;
				case 'C': 
					myNetwork = '<%= request.getAttribute("network2C")%>'
				break;
				case 'D': 
					myNetwork = '<%= request.getAttribute("network2D")%>'
				break;
				case 'E': 
					myNetwork = '<%= request.getAttribute("network2E")%>'
				break;
				}	
			break;
			case '3':
				switch(id.charAt(1)){
				case 'A': 
					myNetwork = '<%= request.getAttribute("network3A")%>'
				break;
				case 'B': 
					myNetwork = '<%= request.getAttribute("network3B")%>'
				break;
				case 'C': 
					myNetwork = '<%= request.getAttribute("network3C")%>'
				break;
				case 'D': 
					myNetwork = '<%= request.getAttribute("network3D")%>'
				break;
				case 'E': 
					myNetwork = '<%= request.getAttribute("network3E")%>'
				break;
				}			
			break;
			case '4':
				switch(id.charAt(1)){
				case 'A': 
					myNetwork = '<%= request.getAttribute("network4A")%>'
				break;
				case 'B': 
					myNetwork = '<%= request.getAttribute("network4B")%>'
				break;
				case 'C': 
					myNetwork = '<%= request.getAttribute("network4C")%>'
				break;
				case 'D': 
					myNetwork = '<%= request.getAttribute("network4D")%>' 
				break;
				case 'E': 
					myNetwork = '<%= request.getAttribute("network4E")%>'
				break;
				}	
			break;
			case '5':
				switch(id.charAt(1)){
				case 'A': 
					myNetwork = '<%= request.getAttribute("network5A")%>'
				break;
				case 'B': 
					myNetwork = '<%= request.getAttribute("network5B")%>'
				break;
				case 'C': 
					myNetwork = '<%= request.getAttribute("network5C")%>'
				break;
				case 'D': 
					myNetwork = '<%= request.getAttribute("network5D")%>'
				break;
				case 'E': 
					myNetwork = '<%= request.getAttribute("network5E")%>'
				break;
				}				
			break;
			case '6':
				switch(id.charAt(1)){
				case 'A':
					myNetwork = '<%= request.getAttribute("network6A")%>'
				break;
				case 'B':
					myNetwork = '<%= request.getAttribute("network6B")%>'
				break;
				case 'C':
					myNetwork = '<%= request.getAttribute("network6C")%>'
				break;
				case 'D':
					myNetwork = '<%= request.getAttribute("network6D")%>'
				break;
				case 'E':
					myNetwork = '<%= request.getAttribute("network6E")%>'
				break;
				}			
			break;
			case '7':
				switch(id.charAt(1)){
				case 'A':
					myNetwork = '<%= request.getAttribute("network7A")%>'
				break;
				case 'B': 
					myNetwork = '<%= request.getAttribute("network7B")%>'
				break;
				case 'C':
					myNetwork = '<%= request.getAttribute("network7C")%>'
				break;
				case 'D': 
					myNetwork = '<%= request.getAttribute("network7D")%>'
				break;
				case 'E': 
					myNetwork = '<%= request.getAttribute("network7E")%>'
				break;
				}				
			break;
			case '8':
				switch(id.charAt(1)){
				case 'A':
					myNetwork = '<%= request.getAttribute("network8A")%>'
				break;
				case 'B': 
					myNetwork = '<%= request.getAttribute("network8B")%>'
				break;
				case 'C': 
					myNetwork = '<%= request.getAttribute("network8C")%>'
				break;
				case 'D': 
					myNetwork = '<%= request.getAttribute("network8D")%>'
				break;
				case 'E':
					myNetwork = '<%= request.getAttribute("network8E")%>'
				break;
				}				
			break;
			}
		
			//make sure we aren't creating another display if a display already exists 
			let currElement = document.getElementById(id);
			if(currElement.childElementCount == 0){
				var svg = viz.renderSVGElement(myNetwork)
				.then(function(element) {
					currElement.appendChild(element);
					
				})
				.catch(error => {
					console.log(error);
					viz = new Viz();
					let node = document.createElement("LI");
					let textNode = document.createTextNode("Something went wrong, and the team cannot be displayed with this technique. Please select a different technique");
					node.appendChild(textNode);
					
					currElement.appendChild(node);
				});
			}
			
			//get rid of the displays that are no longer going to be visible to the user 
			var idLetters = ['A','B','C','D','E'];
			for(let i = 1; i <= 8; i ++){
				for (let j = 0; j < 5; j ++){
					let tempId = '' + i + idLetters[j];
					if(tempId != id){
						let tempElement = document.getElementById(tempId);
						if(tempElement.childElementCount == 1){
							tempElement.removeChild(tempElement.firstChild);
							console.log('test with ' + tempId);						
						}
					}
				}
			}
			
		}
</script>
	<br>

	<table style="width: 100%;">
		<col width="30%">
		<%
		
			String[] experts = (String[]) getServletContext().getAttribute("experts");

		String[] skillName = (String[]) request.getAttribute("skillName");

		String[] skill2expertId = (String[]) request.getAttribute("skill2expertId1A");
		int[] expertIds = (int[]) request.getAttribute("expertIds1A");
		
		String parsedIds = (String)"";
		for (int i = 0; i < expertIds.length; i++) {
			parsedIds += expertIds[i];
			
		}
		
		//this is intentional for k > 3 cases
		String[] performance1 = (String[]) request.getAttribute("performance1");
		String[] performance2 = (String[]) request.getAttribute("performance1");
		String[] performance3 = (String[]) request.getAttribute("performance1");
		String[] performance4 = (String[]) request.getAttribute("performance1");
		String[] performance5 = (String[]) request.getAttribute("performance1");
		
		
		%>

		<%!String capIt(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}%>
	</table>
	
	
	
	<div id="tabsTeams">
		
		<ul>
			<li id = 'tab1' onclick=$('#tab1A').click();><a href="#team1">Team 1</a></li>
			<li id = 'tab2' onclick=$('#tab1B').click();><a href="#team2">Team 2</a></li>
			<li id = 'tab3' onclick=$('#tab1C').click();><a href="#team3">Team 3</a></li>
			<li id = 'tab4' onclick=$('#tab1D').click();><a href="#team4">Team 4</a></li>
			<li id = 'tab5' onclick=$('#tab1E').click();><a href="#team5">Team 5</a></li>
		</ul>
	
		<div class='team' id ="team1">
			<table class='teamTable' id="team1Table">
				<tr>
					<th style='border: solid 1px'>Skill Coverage</th>
					<th style='border: solid 1px'>Team Members (ID: Name)</th>
					<th style='border: solid 1px'>Team Score</th>
					<th style='border: solid 1px'>Team Performance</th>
				</tr>
				<tr>
					<%
					experts = (String[]) getServletContext().getAttribute("experts");
					skillName = (String[]) request.getAttribute("skillName");

					skill2expertId = (String[]) request.getAttribute("skill2expertId1A");
					expertIds = (int[]) request.getAttribute("expertIds1A");
					parsedIds = (String)"";
					for (int i = 0; i < expertIds.length; i++) {
						parsedIds += expertIds[i];		
					}
					out.print("</td><td style='border:solid 1px'>");
					for (int i = 0; i < skill2expertId.length; i++) {
						out.print( "&nbsp;&nbsp;&nbsp;&nbsp;" + 
							capIt(skillName[i]) + 
							" <span style=\"color:blue;font-weight:bold\">Covered by</span> " + 
							experts[Integer.parseInt(skill2expertId[i])] + "<br>");
					}
	
					out.print("</td> <td style='border:solid 1px'>");
					for (int i = 0; i < expertIds.length; i++) {
						out.print("&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:red\">" + expertIds[i] + "</span> : " + experts[expertIds[i]] + "<br>");
					}
					out.print("</td>");
					%>
					<td class ='scoreHeader' id="scoreInfo1" style='border:solid 1px'>
						<p id="Score1"  style="display:none" onmouseover="loadScore('Score1','<%= parsedIds %>')" >0</p>
						<button type='button' class="button button2" onclick="minusButton(Score1, <%= parsedIds %>)"  id="Bad1" value='-1'> -1 </button>
						<button type='button' class="button button2" onclick="plusButton(Score1, <%= parsedIds %>)" id="Good1"value='+1'> +1 </button>
					</td>
					<td style='border:solid 1px'>
						<%= performance1[1] %>
					</td>
				</tr>
			</table>
			<div class='tabTypes' id = "tabs1Types">
				<ul>
					<li id='tab1A' onclick="genTeamDisplayLocal(`1A`)"><a href="#1A">BFS</a></li>
					<li id='tab2A' onclick="genTeamDisplayLocal(`2A`)"><a href="#2A">Pruned BFS </a></li>
					<li id='tab3A' onclick="genTeamDisplayLocal(`3A`)"><a href="#3A">Clique</a></li>
					<li id='tab4A' onclick="genTeamDisplayLocal(`4A`)"><a href="#4A">Clique+Neighbors</a></li>
					<li id='tab5A' onclick="genTeamDisplayLocal(`5A`)"><a href="#5A">Weighted Path</a>
					<!-- 
					<li id='tab6A' onclick="genTeamDisplayLocal(`6A`)"><a href="#6A">Pruned Weighted Path</a>
					<li id='tab7A' onclick="genTeamDisplayLocal(`7A`)"><a href="#7A">AK-Master Node</a>
					<li id='tab8A' onclick="genTeamDisplayLocal(`8A`)"><a href="#8A">Improved Pruned BFS</a>
				 	-->
				 </ul>
				<div class='teamGraph' id="1A"></div>
				<div class='teamGraph' id="2A"></div>
				<div class='teamGraph' id="3A"></div>
				<div class='teamGraph' id="4A"></div>
				<div class='teamGraph' id="5A"></div>
				<div class='teamGraph' id="6A"></div>
				<div class='teamGraph' id="7A"></div>
				<div class='teamGraph' id="8A"></div>	
			</div>
		</div>
		<div class='team' id ="team2">
			<table class='teamTable' id="team2Table">
				<tr>
					<th style='border: solid 1px'>Skill Coverage</th>
					<th style='border: solid 1px'>Team Members (ID: Name)</th>
					<th style='border: solid 1px'>Team Score</th>
					<th style='border: solid 1px'>Team Performance</th>
				</tr>
				<tr>
					<%
					
					if (kTeams > 1){				

						performance2 = (String[]) request.getAttribute("performance2");
						experts = (String[]) getServletContext().getAttribute("experts");
						skillName = (String[]) request.getAttribute("skillName");

						skill2expertId = (String[]) request.getAttribute("skill2expertId1B");
						expertIds = (int[]) request.getAttribute("expertIds1B");
						parsedIds = (String)"";
						for (int i = 0; i < expertIds.length; i++) {
							parsedIds += expertIds[i];		
						}
						out.print("</td><td style='border:solid 1px'>");
						for (int i = 0; i < skill2expertId.length; i++) {
							out.print( "&nbsp;&nbsp;&nbsp;&nbsp;" + capIt(skillName[i]) + " <span style=\"color:blue;font-weight:bold\">Covered by</span> " + experts[Integer.parseInt(skill2expertId[i])] + "<br>");
						}
	
						out.print("</td> <td style='border:solid 1px'>");
						for (int i = 0; i < expertIds.length; i++) {
							out.print("&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:red\">" + expertIds[i] + "</span> : " + experts[expertIds[i]] + "<br>");
						}
						out.print("</td>");
					} else {
						
						out.print("<td></td><td></td>");
					}
					%>
					<td class ='scoreHeader' id='scoreInfo2' style='border:solid 1px'>
						<p id="Score2" style="display:none" onmouseover="loadScore('Score2','<%= parsedIds %>')" >0</p>
						<button type='button' style='width:100px; height:50px; font-size: 20px;' class="button button2" onclick="minusButton(Score2, '<%= parsedIds %>')"  id="Bad2" value='-1'>-1</button>
						<button type='button' style='width:100px; height:50px; font-size: 20px;' class="button button2" onclick="plusButton(Score2, '<%= parsedIds %>')" id="Good2"value='+1'>+1</button>
					</td>
					<td style='border:solid 1px'>
						<%= performance2[1] %>
					</td>
				</tr>
			</table>
			<div class='tabTypes' id = "tabs2Types">
				<ul>
					<li id='tab1B' onclick="genTeamDisplayLocal(`1B`)"><a href="#1B">BFS</a></li>
					<li id='tab2B' onclick="genTeamDisplayLocal(`2B`)"><a href="#2B">Pruned BFS </a></li>
					<li id='tab3B' onclick="genTeamDisplayLocal(`3B`)"><a href="#3B">Clique</a></li>
					<li id='tab4B' onclick="genTeamDisplayLocal(`4B`)"><a href="#4B">Clique+Neighbors</a></li>
					<li id='tab5B' onclick="genTeamDisplayLocal(`5B`)"><a href="#5B">Weighted Path</a>
					<!-- 
					<li id='tab6B' onclick="genTeamDisplayLocal(`6B`)"><a href="#6B">Pruned Weighted Path</a>
					<li id='tab7B' onclick="genTeamDisplayLocal(`7B`)"><a href="#7B">AK-Master Node</a>
					<li id='tab8B' onclick="genTeamDisplayLocal(`8B`)"><a href="#8B">Improved Pruned BFS</a>
					 -->
				</ul>
				<div class='teamGraph' id="1B"></div>
				<div class='teamGraph' id="2B"></div>
				<div class='teamGraph' id="3B"></div>
				<div class='teamGraph' id="4B"></div>
				<div class='teamGraph' id="5B"></div>
				<div class='teamGraph' id="6B"></div>
				<div class='teamGraph' id="7B"></div>
				<div class='teamGraph' id="8B"></div>	
			</div>
		</div>
		<div class='team'id ="team3">
			<table class='teamTable' id = 'team3Table'>
				<tr>
					<th style='border: solid 1px'>Skill Coverage</th>
					<th style='border: solid 1px'>Team Members (ID: Name)</th>
					<th style='border: solid 1px'>Team Score</th>
					<th style='border: solid 1px'>Team Performance</th>
				</tr>
				<tr>
					<%
					if (kTeams > 1){						
						
					performance3 = (String[]) request.getAttribute("performance3");
					experts = (String[]) getServletContext().getAttribute("experts");
					skillName = (String[]) request.getAttribute("skillName");

					skill2expertId = (String[]) request.getAttribute("skill2expertId1C");
					expertIds = (int[]) request.getAttribute("expertIds1C");
					parsedIds = (String)"";
					for (int i = 0; i < expertIds.length; i++) {
						parsedIds += expertIds[i];		
					}
					out.print("</td><td style='border:solid 1px'>");
					for (int i = 0; i < skill2expertId.length; i++) {
						out.print( "&nbsp;&nbsp;&nbsp;&nbsp;" + capIt(skillName[i]) + " <span style=\"color:blue;font-weight:bold\">Covered by</span> " + experts[Integer.parseInt(skill2expertId[i])] + "<br>");
					}
	
					out.print("</td> <td style='border:solid 1px'>");
					for (int i = 0; i < expertIds.length; i++) {
						out.print("&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:red\">" + expertIds[i] + "</span> : " + experts[expertIds[i]] + "<br>");
					}
					out.print("</td>");
					} else {
						
						out.print("<td></td><td></td>");
					}
					%>
					<td class ='scoreHeader' id='scoreInfo3' style='border:solid 1px'>
						<p id="Score3" style="display:none" onmouseover="loadScore('Score3','<%= parsedIds %>')" >0</p>
						<button type='button' style='width:100px; height:50px; font-size: 20px;' class="button button2" onclick="minusButton(Score3, '<%= parsedIds %>')"  id="Bad3" value='-1'>-1</button>
						<button type='button' style='width:100px; height:50px; font-size: 20px;' class="button button2" onclick="plusButton(Score3, '<%= parsedIds %>')" id="Good3"value='+1'>+1</button>
					</td>
					<td style='border:solid 1px'>
						<%= performance3[1] %>
					</td>
				</tr>
			</table>
			<div class='tabTypes' id = "tabs3Types">
				<ul>
					<li id='tab1C' onclick="genTeamDisplayLocal(`1C`)"><a href="#1C">BFS</a></li>
					<li id='tab2C' onclick="genTeamDisplayLocal(`2C`)"><a href="#2C">Pruned BFS </a></li>
					<li id='tab3C' onclick="genTeamDisplayLocal(`3C`)"><a href="#3C">Clique</a></li>
					<li id='tab4C' onclick="genTeamDisplayLocal(`4C`)"><a href="#4C">Clique+Neighbors</a></li>
					<li id='tab5C' onclick="genTeamDisplayLocal(`5C`)"><a href="#5C">Weighted Path</a>
					<!-- 
					<li id='tab6C' onclick="genTeamDisplayLocal(`6C`)"><a href="#6C">Pruned Weighted Path</a>
					<li id='tab7C' onclick="genTeamDisplayLocal(`7C`)"><a href="#7C">AK-Master Node</a>
					<li id='tab8C' onclick="genTeamDisplayLocal(`8C`)"><a href="#8C">Improved Pruned BFS</a>
					-->
				</ul>
				<div class='teamGraph' id="1C"></div>
				<div class='teamGraph' id="2C"></div>
				<div class='teamGraph' id="3C"></div>
				<div class='teamGraph' id="4C"></div>
				<div class='teamGraph' id="5C"></div>
				<div class='teamGraph' id="6C"></div>
				<div class='teamGraph' id="7C"></div>
				<div class='teamGraph' id="8C"></div>	
			</div>
		</div>
		<div class='team' id ="team4">
			<table class='teamTable' id='team4Table'>
				<tr>
					<th style='border: solid 1px'>Skill Coverage</th>
					<th style='border: solid 1px'>Team Members (ID: Name)</th>
					<th style='border: solid 1px'>Team Score</th>
					<th style='border: solid 1px'>Team Performance</th>
				</tr>
				<tr>
					<%
					if(kTeams > 3){
					performance4 = (String[]) request.getAttribute("performance4");
						
					experts = (String[]) getServletContext().getAttribute("experts");
					skillName = (String[]) request.getAttribute("skillName");

					skill2expertId = (String[]) request.getAttribute("skill2expertId1D");
					expertIds = (int[]) request.getAttribute("expertIds1D");
					parsedIds = (String)"";
					for (int i = 0; i < expertIds.length; i++) {
						parsedIds += expertIds[i];		
					}
					out.print("</td><td style='border:solid 1px'>");
					for (int i = 0; i < skill2expertId.length; i++) {
						out.print( "&nbsp;&nbsp;&nbsp;&nbsp;" + capIt(skillName[i]) + " <span style=\"color:blue;font-weight:bold\">Covered by</span> " + experts[Integer.parseInt(skill2expertId[i])] + "<br>");
					}
	
					out.print("</td> <td style='border:solid 1px'>");
					for (int i = 0; i < expertIds.length; i++) {
						out.print("&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:red\">" + expertIds[i] + "</span> : " + experts[expertIds[i]] + "<br>");
					}
					out.print("</td>");
					} else {
						out.print("<td></td><td></td>");
					}
					%>
					<td class ='scoreHeader' id='scoreInfo4' style='border:solid 1px'>
						<p id="Score4" style="display:none" onmouseover="loadScore('Score4','<%= parsedIds %>')" >0</p>
						<button type='button' style='width:100px; height:50px; font-size: 20px;' class="button button2" onclick="minusButton(Score4, '<%= parsedIds %>')"  id="Bad4" value='-1'>-1</button>
						<button type='button' style='width:100px; height:50px; font-size: 20px;' class="button button2" onclick="plusButton(Score4, '<%= parsedIds %>')" id="Good4"value='+1'>+1</button>
					</td>
					<td style='border:solid 1px'>
						<%= performance4[1] %>
					</td>
				</tr>
			</table>
			<div class='tabTypes' id = "tabs4Types">
				<ul>
					<li id='tab1D' onclick="genTeamDisplayLocal(`1D`)"><a href="#1D">BFS</a></li>
					<li id='tab2D' onclick="genTeamDisplayLocal(`2D`)"><a href="#2D">Pruned BFS </a></li>
					<li id='tab3D' onclick="genTeamDisplayLocal(`3D`)"><a href="#3D">Clique</a></li>
					<li id='tab4D' onclick="genTeamDisplayLocal(`4D`)"><a href="#4D">Clique+Neighbors</a></li>
					<li id='tab5D' onclick="genTeamDisplayLocal(`5D`)"><a href="#5D">Weighted Path</a>
					<!-- 
					<li id='tab6D' onclick="genTeamDisplayLocal(`6D`)"><a href="#6D">Pruned Weighted Path</a>
					<li id='tab7D' onclick="genTeamDisplayLocal(`7D`)"><a href="#7D">AK-Master Node</a>
					<li id='tab8D' onclick="genTeamDisplayLocal(`8D`)"><a href="#8D">Improved Pruned BFS</a>
					 -->
				</ul>
				<div class='teamGraph' id="1D"></div>
				<div class='teamGraph' id="2D"></div>
				<div class='teamGraph' id="3D"></div>
				<div class='teamGraph' id="4D"></div>
				<div class='teamGraph' id="5D"></div>
				<div class='teamGraph' id="6D"></div>
				<div class='teamGraph' id="7D"></div>
				<div class='teamGraph' id="8D"></div>	
			</div>
		</div>
		<div class='team' id ="team5">
			<table class='teamTable' id = 'team5Table'>
				<tr>
					<th style='border: solid 1px'>Skill Coverage</th>
					<th style='border: solid 1px'>Team Members (ID: Name)</th>
					<th style='border: solid 1px'>Team Score</th>
					<th style='border: solid 1px'>Team Performance</th>
				</tr>
				<tr>
					<%
					if(kTeams > 3){
						
					performance5 = (String[]) request.getAttribute("performance5");
					experts = (String[]) getServletContext().getAttribute("experts");
					skillName = (String[]) request.getAttribute("skillName");

					skill2expertId = (String[]) request.getAttribute("skill2expertId1E");
					expertIds = (int[]) request.getAttribute("expertIds1E");
					parsedIds = (String)"";
					for (int i = 0; i < expertIds.length; i++) {
						parsedIds += expertIds[i];		
					}
					out.print("</td><td style='border:solid 1px'>");
					for (int i = 0; i < skill2expertId.length; i++) {
						out.print( "&nbsp;&nbsp;&nbsp;&nbsp;" + capIt(skillName[i]) + " <span style=\"color:blue;font-weight:bold\">Covered by</span> " + experts[Integer.parseInt(skill2expertId[i])] + "<br>");
					}
	
					out.print("</td> <td style='border:solid 1px'>");
					for (int i = 0; i < expertIds.length; i++) {
						out.print("&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:red\">" + expertIds[i] + "</span> : " + experts[expertIds[i]] + "<br>");
					}
					out.print("</td>");
					} else {
						out.print("<td></td><td></td>");
					}
					%>
					<td class ='scoreHeader' id='scoreInfo5' style='border:solid 1px'>
						<p id="Score5"  style="display:none" onmouseover="loadScore('Score5','<%= parsedIds %>')" >0</p>
						<button type='button' class="button button2" onclick="minusButton(Score5, '<%= parsedIds %>')"  id="Bad5" value='-1'>-1</button>
						<button type='button' class="button button2" onclick="plusButton(Score5, '<%= parsedIds %>')" id="Good5"value='+1'>+1</button>
					</td>
					<td style='border:solid 1px'>
						<%= performance5[1] %>
					</td>
				</tr>
			</table>
			<div class='tabTypes' id = "tabs5Types">
				<ul>
					<li id = 'tab1E' onclick="genTeamDisplayLocal(`1E`)"><a href="#1E">BFS</a></li>
					<li id = 'tab2E' onclick="genTeamDisplayLocal(`2E`)"><a href="#2E">Pruned BFS </a></li>
					<li id = 'tab3E' onclick="genTeamDisplayLocal(`3E`)"><a href="#3E">Clique</a></li>
					<li id = 'tab4E' onclick="genTeamDisplayLocal(`4E`)"><a href="#4E">Clique+Neighbors</a></li>
					<li id = 'tab5E' onclick="genTeamDisplayLocal(`5E`)"><a href="#5E">Weighted Path</a>
					<!-- 
					<li id = 'tab6E' onclick="genTeamDisplayLocal(`6E`)"><a href="#6E">Pruned Weighted Path</a>
					<li id = 'tab7E' onclick="genTeamDisplayLocal(`7E`)"><a href="#7E">AK-Master Node</a>
					<li id = 'tab8E' onclick="genTeamDisplayLocal(`8E`)"><a href="#8E">Improved Pruned BFS</a>
					 -->
				</ul>
				<div class='teamGraph' id="1E"></div>
				<div class='teamGraph' id="2E"></div>
				<div class='teamGraph' id="3E"></div>
				<div class='teamGraph' id="4E"></div>
				<div class='teamGraph' id="5E"></div>
				<div class='teamGraph' id="6E"></div>
				<div class='teamGraph' id="7E"></div>
				<div class='teamGraph' id="8E"></div>	
			</div>	
		</div>
	
	</div>
	
	
	<script src="javascripts/jquery.js"></script>
	<script src="javascripts/jquery-ui.js"></script>
	
	<script>
		$(document).ready(function(){
			// this part is to set the team tab visiblilty 
			$('#tab1A').click();
			
			let teamCount = <%= kTeams %>; 
			for(let i = teamCount + 1; i < 6; i ++){
				let teamTab = document.getElementById('tab' + i);
				teamTab.style.display = 'none';
			}
			var idLetters = ['A','B','C','D','E'];
			
			let teamType = '<%= method %>';
			
			if(teamType != 'r' && teamType != 'f'){
				console.log('wsp not applicable');
				for(let i = 1; i <= 8; i ++){
					for(let j = 0; j < teamCount; j ++){
						let curTab = document.getElementById('tab' + i + idLetters[j]);
						if(i == 1){
							curTab.firstChild.innerText = 'BFS';
						} else if (i == 2) {
							curTab.firstChild.innerText = 'Clique';
						} else if (i == 3){
							curTab.firstChild.innerText = 'Clique + Neighbours';
						} else {
							curTab.style.display = 'none';
						}
					}
				}
			}
			
			
		});
	</script>
	
	<script>
		$("#tabsTeams").tabs();
		$("#tabs1Types").tabs();
		$("#tabs2Types").tabs();
		$("#tabs3Types").tabs();
		$("#tabs4Types").tabs();
		$("#tabs5Types").tabs();
	</script>
</body>
</html>
