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

<link rel="stylesheet" href="jquery-ui.css">

<script src="javascripts/node_modules/viz.js/viz.js"></script>
<script src="javascripts/node_modules/viz.js/full.render.js"></script>
<script src="javascripts/TeamScore.js"></script>
<script src="javascripts/GenerateTeamDisplay.js"></script>

</head>
<body>
	<div>
		<h3>Team Formation:</h3>
	</div>


	<br>

	<table style="width: 100%;">
		<col width="30%">
		<%
			String[] experts = (String[]) getServletContext().getAttribute("experts");

		String[] skillName = (String[]) request.getAttribute("skillName");

		String[] skill2expertId = (String[]) request.getAttribute("skill2expertIdR1A");
		int[] expertIds = (int[]) request.getAttribute("expertIdsR");
		String[] skill2expertIdS = (String[]) request.getAttribute("skill2expertIdS");
		int[] expertIdsS = (int[]) request.getAttribute("expertIdsS");

		String[] perfR = (String[]) request.getAttribute("perfR");
		String[] perfS = (String[]) request.getAttribute("perfS");
		String[] perfF = (String[]) request.getAttribute("perfF");
		%>
		<tr>
			<th style='border: solid 1px'>Team Effectiveness</th>
			<th style='border: solid 1px'>Existing Project Skills</th>
		</tr>

		<tr>
			<td rowspan="2">
				<table>
					<%
						out.print(
							"<tr><th style='text-decoration: underline;'> Method</th><th align=\"center\" style='text-decoration: underline;'>Citation</th></tr>");
					out.print("<tr><td>Random Walk<td align=\"center\">" + perfR[1] + "</td></tr>");
					out.print("<tr><td>Shortest Path</td><td align=\"center\">" + perfS[1] + "</td></tr>");
					out.print("<tr><td>Fast Random Walk</td><td align=\"center\">" + perfF[1] + "</td></tr>");
					%>
				</table>
			</td>
			<td>
				<%
					out.print("<tr><td td style='border:solid 1px'>");
				for (int i = 0; i < skillName.length; i++) {
					out.print("&nbsp;&nbsp;&nbsp;&nbsp;" + capIt(skillName[i]) + "<br>");
				}
				%>
			</td>
		</tr>

		<%!String capIt(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}%>
	</table>

	<p>Team Discovery Techniques</p>
	<div id="tabTeamTypes">
		<ul>
			<li><a href="#fastRWR">Fast Random Walk</a></li>
			<li><a href="#slowRWR">Non-Fast Random Walk</a></li>
			<li><a href="#shortPath">Shortest Path</a></li>
		</ul>
		<div id="fastRWR">
			<p>Expert in the Network (Fast Random Walk)</p>
			<p>Later there will be a description about how this works. I
				don't know the difference between how Fast Random Walk and Non-Fast
				Random Walk works</p>
			<p>Which is partially why i'm leaving this blank</p>
			<div id="tabsFRWTeams">
				<ul>
					<li><a href="#teamFRW1">Team 1</a></li>
					<li><a href="#teamFRW2">Team 2</a></li>
					<li><a href="#teamFRW3">Team 3</a></li>
				</ul>

				<div id="teamFRW1">
					<table style="width: 100%;">
						<tr>
							<th style='border: solid 1px'>Skill Coverage</th>
							<th style='border: solid 1px'>Team Members (ID: Name)</th>
							<th style='border: solid 1px'>Team Score</th>
							
						</tr>
						<tr>
							<%
								experts = (String[]) getServletContext().getAttribute("experts");

							skillName = (String[]) request.getAttribute("skillName");

							skill2expertId = (String[]) request.getAttribute("skill2expertIdF1A");
							expertIds = (int[]) request.getAttribute("expertIdsF1A");

							//perfR = (String[]) request.getAttribute("perfR");
							//perfS = (String[]) request.getAttribute("perfS");
							//perfF = (String[]) request.getAttribute("perfF");

							out.print("</td><td style='border:solid 1px'>");
							for (int i = 0; i < skill2expertId.length; i++) {
								out.print("&nbsp;&nbsp;&nbsp;&nbsp;" + capIt(skillName[i])
								+ " <span style=\"color:blue;font-weight:bold\">Covered by</span> "
								+ experts[Integer.parseInt(skill2expertId[i])] + "<br>");
							}

							out.print("</td> <td style='border:solid 1px'>");
							for (int i = 0; i < expertIds.length; i++) {
								out.print("&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:red\">" + expertIds[i] + "</span> : " + experts[expertIds[i]]
								+ "<br>");
							}
							out.print("</td>");
							%>
							<td style='border:solid 1px'>
								<p id="F1Score" > 0 </p>
								<button type='button' class="button button2" onclick="minusButton(F1Score)"  id="F1Bad" value='-1'>-1</button>
								<button type='button' class="button button2" onclick="plusButton(F1Score)" id="F1Good"value='+1'>+1</button>
							</td>
						</tr>

					</table>



					<div id="tabsFRW1Types">
						<ul>
							<li><a href="#f1A">BFS</a></li>
							<li><a href="#f2A">Pruned BFS </a></li>
							<li><a href="#f3A">Clique</a></li>
							<li><a href="#f4A">Clique+Neighbors</a></li>
							<li><a href="#f5A">ManyPath</a>
							<li><a href="#f6A">Pruned ManyPath</a>
							<li><a href="#f7A">AK-Master Node</a>
							<li><a href="#f8A">Improved Pruned BFS</a>
						</ul>
						<div id="f1A"></div>
						<div id="f2A"></div>
						<div id="f3A"></div>
						<div id="f4A"></div>
						<div id="f5A"></div>
						<div id="f6A"></div>
						<div id="f7A"></div>
						<div id="f8A"></div>
					</div>
				</div>

				<div id="teamFRW2">
					<table style="width: 100%;">
						<tr>
							<th style='border: solid 1px'>Skill Coverage</th>
							<th style='border: solid 1px'>Team Members (ID: Name)</th>
							<th style='border: solid 1px'>Team Score</th>
						</tr>
						<tr>
							<%
								experts = (String[]) getServletContext().getAttribute("experts");

							skillName = (String[]) request.getAttribute("skillName");

							skill2expertId = (String[]) request.getAttribute("skill2expertIdF1B");
							expertIds = (int[]) request.getAttribute("expertIdsF1B");

							//perfR = (String[]) request.getAttribute("perfR");
							//perfS = (String[]) request.getAttribute("perfS");
							//perfF = (String[]) request.getAttribute("perfF");

							out.print("</td><td style='border:solid 1px'>");
							for (int i = 0; i < skill2expertId.length; i++) {
								out.print("&nbsp;&nbsp;&nbsp;&nbsp;" + capIt(skillName[i])
								+ " <span style=\"color:blue;font-weight:bold\">Covered by</span> "
								+ experts[Integer.parseInt(skill2expertId[i])] + "<br>");
							}

							out.print("</td> <td style='border:solid 1px'>");
							for (int i = 0; i < expertIds.length; i++) {
								out.print("&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:red\">" + expertIds[i] + "</span> : " + experts[expertIds[i]]
								+ "<br>");
							}
							out.print("</td>");
							%>
							<td style='border:solid 1px'>
								<p id="F2Score"> 0 </p>
								<button type='button' class="button button2" onclick="minusButton(F2Score)"id="F2Bad" value='-1'>-1</button>
								<button type='button' class="button button2" onclick="plusButton(F2Score)" id="F2Good"value='+1'>+1</button>
							</td>
							
						</tr>
					</table>

					<div id="tabsFRW2Types">
						<ul>
							<li><a href="#f1B">BFS</a></li>
							<li><a href="#f2B">Pruned BFS </a></li>
							<li><a href="#f3B">Clique</a></li>
							<li><a href="#f4B">Clique+Neighbors</a></li>
							<li><a href="#f5B">ManyPath</a>
							<li><a href="#f6B">Pruned ManyPath</a>
							<li><a href="#f7B">AK-Master Node</a>
							<li><a href="#f8B">Improved Pruned BFS</a>
						</ul>
						<div id="f1B"></div>
						<div id="f2B"></div>
						<div id="f3B"></div>
						<div id="f4B"></div>
						<div id="f5B"></div>
						<div id="f6B"></div>
						<div id="f7B"></div>
						<div id="f8B"></div>
					</div>
				</div>


				<div id="teamFRW3">
					<table style="width: 100%;">
						<tr>
							<th style='border: solid 1px'>Skill Coverage</th>
							<th style='border: solid 1px'>Team Members (ID: Name)</th>
							<th style='border: solid 1px'>Team Score</th>
						</tr>
						<tr>
							<%
								experts = (String[]) getServletContext().getAttribute("experts");

							skillName = (String[]) request.getAttribute("skillName");

							skill2expertId = (String[]) request.getAttribute("skill2expertIdF1C");
							expertIds = (int[]) request.getAttribute("expertIdsF1C");

							//perfR = (String[]) request.getAttribute("perfR");
							//perfS = (String[]) request.getAttribute("perfS");
							//perfF = (String[]) request.getAttribute("perfF");

							out.print("</td><td style='border:solid 1px'>");
							for (int i = 0; i < skill2expertId.length; i++) {
								out.print("&nbsp;&nbsp;&nbsp;&nbsp;" + capIt(skillName[i])
								+ " <span style=\"color:blue;font-weight:bold\">Covered by</span> "
								+ experts[Integer.parseInt(skill2expertId[i])] + "<br>");
							}

							out.print("</td> <td style='border:solid 1px'>");
							for (int i = 0; i < expertIds.length; i++) {
								out.print("&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:red\">" + expertIds[i] + "</span> : " + experts[expertIds[i]]
								+ "<br>");
							}
							out.print("</td>");
							%>
							<td style='border:solid 1px'>
								<p id="F3Score"> 0 </p>
								<button type='button' class="button button2" onclick="minusButton(F3Score)" id="F3Bad" value='-1'>-1</button>
								<button type='button' class="button button2" onclick="minusButton(F3Score)"id="F3Good"value='+1'>+1</button>
							</td>
						</tr>
					</table>

					<div id="tabsFRW3Types">
						<ul>
							<li><a href="#f1C">BFS</a></li>
							<li><a href="#f2C">Pruned BFS </a></li>
							<li><a href="#f3C">Clique</a></li>
							<li><a href="#f4C">Clique+Neighbors</a></li>
							<li><a href="#f5C">ManyPath</a>
							<li><a href="#f6C">Pruned ManyPath</a>
							<li><a href="#f7C">AK-Master Node</a>
							<li><a href="#f8C">Improved Pruned BFS</a>
						</ul>
						<div id="f1C"></div>
						<div id="f2C"></div>
						<div id="f3C"></div>
						<div id="f4C"></div>
						<div id="f5C"></div>
						<div id="f6C"></div>
						<div id="f7C"></div>
						<div id="f8C"></div>
					</div>
				</div>
			</div>
		</div>


		<div id="slowRWR">
			<p>Expert in the Network (Random Walk):</p>
			<p>Later there will be a description about how this works. I
				don't know the difference between how Fast Random Walk and Non-Fast
				Random Walk works</p>
			<p>Which is partially why i'm leaving this blank</p>
			<div id="tabsRWTeams">
				<ul>
					<li><a href="#teamRW1">Team 1</a></li>
					<li><a href="#teamRW2">Team 2</a></li>
					<li><a href="#teamRW3">Team 3</a></li>
				</ul>

				<div id="teamRW1">
					<table style="width: 100%;">
						<col>

						<tr>
							<th style='border: solid 1px'>Skill Coverage</th>
							<th style='border: solid 1px'>Team Members (ID: Name)</th>
							<th style='border: solid 1px'>Team Score</th>
						</tr>
						<tr>
							<%
								experts = (String[]) getServletContext().getAttribute("experts");

							skillName = (String[]) request.getAttribute("skillName");

							skill2expertId = (String[]) request.getAttribute("skill2expertIdR1A");
							expertIds = (int[]) request.getAttribute("expertIdsR1A");

							out.print("</td><td style='border:solid 1px'>");
							for (int i = 0; i < skill2expertId.length; i++) {
								out.print("&nbsp;&nbsp;&nbsp;&nbsp;" + capIt(skillName[i])
								+ " <span style=\"color:blue;font-weight:bold\">Covered by</span> "
								+ experts[Integer.parseInt(skill2expertId[i])] + "<br>");
							}

							out.print("</td> <td style='border:solid 1px'>");
							for (int i = 0; i < expertIds.length; i++) {
								out.print("&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:red\">" + expertIds[i] + "</span> : " + experts[expertIds[i]]
								+ "<br>");
							}
							out.print("</td>");
							%>
							<td style='border:solid 1px'>
								<p id="R1Score"> 0 </p>
								<button type='button' class="button button2" onclick="minusButton(R1Score)" id="R1Bad" value='-1'>-1</button>
								<button type='button' class="button button2" onclick="plusButton(R1Score)" id="R1Good"value='+1'>+1</button>
							</td>
						</tr>
					</table>
					<div id="tabsRW1Types">
						<ul>
							<li><a href="#r1A">BFS</a></li>
							<li><a href="#r2A">Pruned BFS </a></li>
							<li><a href="#r3A">Clique</a></li>
							<li><a href="#r4A">Clique+Neighbors</a></li>
							<li><a href="#r5A">ManyPath</a>
							<li><a href="#r6A">Pruned ManyPath</a>
							<li><a href="#r7A">AK-Master Node</a>
							<li><a href="#r8A">Improved Pruned BFS</a>
						</ul>
						<div id="r1A"></div>
						<div id="r2A"></div>
						<div id="r3A"></div>
						<div id="r4A"></div>
						<div id="r5A"></div>
						<div id="r6A"></div>
						<div id="r7A"></div>
						<div id="r8A"></div>
					</div>
				</div>
				<div id="teamRW2">
					<table style="width: 100%;">
						<col>

						<tr>
							<th style='border: solid 1px'>Skill Coverage</th>
							<th style='border: solid 1px'>Team Members (ID: Name)</th>
							<th style='border: solid 1px'>Team Score</th>
						</tr>
						<tr>
						<%
							experts = (String[]) getServletContext().getAttribute("experts");

						skillName = (String[]) request.getAttribute("skillName");

						skill2expertId = (String[]) request.getAttribute("skill2expertIdR1B");
						expertIds = (int[]) request.getAttribute("expertIdsR1B");

						out.print("</td><td style='border:solid 1px'>");
						for (int i = 0; i < skill2expertId.length; i++) {
							out.print("&nbsp;&nbsp;&nbsp;&nbsp;" + capIt(skillName[i])
							+ " <span style=\"color:blue;font-weight:bold\">Covered by</span> "
							+ experts[Integer.parseInt(skill2expertId[i])] + "<br>");
						}

						out.print("</td> <td style='border:solid 1px'>");
						for (int i = 0; i < expertIds.length; i++) {
							out.print("&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:red\">" + expertIds[i] + "</span> : " + experts[expertIds[i]]
							+ "<br>");
						}
						out.print("</td>");
						%>
						<td style='border:solid 1px'>
								<p id="R2Score"> 0 </p>
								<button type='button' class="button button2" onclick="minusButton(R2Score)"id="R2Bad" value='-1'>-1</button>
								<button type='button' class="button button2" onclick="plusButton(R2Score)" id="R2Good"value='+1'>+1</button>
							</td>
						</tr>

					</table>
					<div id="tabsRW2Types">
						<ul>
							<li><a href="#r1B">BFS</a></li>
							<li><a href="#r2B">Pruned BFS </a></li>
							<li><a href="#r3B">Clique</a></li>
							<li><a href="#r4B">Clique+Neighbors</a></li>
							<li><a href="#r5B">ManyPath</a>
							<li><a href="#r6B">Pruned ManyPath</a>
							<li><a href="#r7B">AK-Master Node</a>
							<li><a href="#r8B">Improved Pruned BFS</a>
						</ul>
						<div id="r1B"></div>
						<div id="r2B"></div>
						<div id="r3B"></div>
						<div id="r4B"></div>
						<div id="r5B"></div>
						<div id="r6B"></div>
						<div id="r7B"></div>
						<div id="r8B"></div>
					</div>
				</div>
				<div id="teamRW3">
					<table style="width: 100%;">
						<col>

						<tr>
							<th style='border: solid 1px'>Skill Coverage</th>
							<th style='border: solid 1px'>Team Members (ID: Name)</th>
							<th style='border: solid 1px'>Team Score</th>
						</tr>
						<tr>
						<%
							experts = (String[]) getServletContext().getAttribute("experts");

						skillName = (String[]) request.getAttribute("skillName");

						skill2expertId = (String[]) request.getAttribute("skill2expertIdR1C");
						expertIds = (int[]) request.getAttribute("expertIdsR1C");

						out.print("</td><td style='border:solid 1px'>");
						for (int i = 0; i < skill2expertId.length; i++) {
							out.print("&nbsp;&nbsp;&nbsp;&nbsp;" + capIt(skillName[i])
							+ " <span style=\"color:blue;font-weight:bold\">Covered by</span> "
							+ experts[Integer.parseInt(skill2expertId[i])] + "<br>");
						}

						out.print("</td> <td style='border:solid 1px'>");
						for (int i = 0; i < expertIds.length; i++) {
							out.print("&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:red\">" + expertIds[i] + "</span> : " + experts[expertIds[i]]
							+ "<br>");
						}
						out.print("</td>");
						%>
						
						<td style='border:solid 1px'>
								<p id="R3Score"> 0 </p>
								<button type='button' class="button button2" onclick="minusButton(R3Score)"id="R3Bad" value='-1'>-1</button>
								<button type='button' class="button button2" onclick="plusButton(R3Score)" id="R3Good"value='+1'>+1</button>
							</td>
						</tr>

					</table>
					<div id="tabsRW3Types">

						<ul>
							<li><a href="#r1C">BFS</a></li>
							<li><a href="#r2C">Pruned BFS </a></li>
							<li><a href="#r3C">Clique</a></li>
							<li><a href="#r4C">Clique+Neighbors</a></li>
							<li><a href="#r5C">ManyPath</a>
							<li><a href="#r6C">Pruned ManyPath</a>
							<li><a href="#r7C">AK-Master Node</a>
							<li><a href="#r8C">Improved Pruned BFS</a>
						</ul>
						<div id="r1C"></div>
						<div id="r2C"></div>
						<div id="r3C"></div>
						<div id="r4C"></div>
						<div id="r5C"></div>
						<div id="r6C"></div>
						<div id="r7C"></div>
						<div id="r8C"></div>
					</div>
				</div>

			</div>
		</div>

		<div id="shortPath">
			<p>Expert in the Network (Shortest Path)</p>
			<p>This works by finding team members with the shortest paths
				between them and forming a team around them</p>
			<p>This is a traditional method, and is a lot faster than both
				random walk techniques. It also does not generate</p>
			<p>A score set, and therefore cannot use the team display
				techniques that require a score set</p>
			<p>(These include Pruned BFS, ManyPath, Pruned ManyPath,
				AK-Master Node, and Improved Pruned BFS)</p>
			<div id="tabsSPTeams">
				<ul>
					<li><a href="#teamSP1">Team 1</a></li>
					<li><a href="#teamSP2">Team 2</a></li>
					<li><a href="#teamSP3">Team 3</a></li>
				</ul>

				<div id="teamSP1">
					<table style="width: 100%;">
						<tr>
							<th style='border: solid 1px'>Skill Coverage</th>
							<th style='border: solid 1px'>Team Members (ID: Name)</th>
							
							<th style='border: solid 1px'>Team Score</th>
						</tr>
						<tr>
						<%
							skill2expertIdS = (String[]) request.getAttribute("skill2expertIdS1A");
						expertIdsS = (int[]) request.getAttribute("expertIdsS1A");
						out.print("<tr>"); // first column
						out.print("<td style='border:solid 1px'>");
						for (int i = 0; i < skill2expertId.length; i++) {
							out.print("&nbsp;&nbsp;&nbsp;&nbsp;" + capIt(skillName[i])
							+ " <span style=\"color:blue;font-weight:bold\">Covered by</span> "
							+ experts[Integer.parseInt(skill2expertIdS[i])] + "<br>");
						}
						out.print("</td> <td style='border:solid 1px'>");
						for (int i = 0; i < expertIds.length; i++) {
							out.print("&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:red\">" + expertIdsS[i] + "</span> : "
							+ experts[expertIdsS[i]] + "<br>");
						}
						out.print("</td>");
						%>
						<td style='border:solid 1px'>
								<p id="S1Score"> 0 </p>
								<button type='button' class="button button2" onclick="minusButton(S1Score)"id="S1Bad" value='-1'>-1</button>
								<button type='button' class="button button2" onclick="plusButton(S1Score)" id="S1Good"value='+1'>+1</button>
							</td>
						</tr>
					</table>
					<div id="tabsSP1Types">
						<ul>
							<li><a href="#s1A">BFS</a></li>
							<li><a href="#s2A">Clique</a></li>
							<li><a href="#s3A">Clique+Neighbors</a></li>
						</ul>
						<div id="s1A"></div>
						<div id="s2A"></div>
						<div id="s3A"></div>
					</div>
				</div>
				<div id="teamSP2">
					<table style="width: 100%;">
						<tr>
							<th style='border: solid 1px'>Skill Coverage</th>
							<th style='border: solid 1px'>Team Members (ID: Name)</th>
							
							<th style='border: solid 1px'>Team Score</th>
						</tr>
						<tr>
						<%
							skill2expertIdS = (String[]) request.getAttribute("skill2expertIdS1B");
						expertIdsS = (int[]) request.getAttribute("expertIdsS1B");
						out.print("<tr>"); // first column
						out.print("<td style='border:solid 1px'>");
						for (int i = 0; i < skill2expertId.length; i++) {
							out.print("&nbsp;&nbsp;&nbsp;&nbsp;" + capIt(skillName[i])
							+ " <span style=\"color:blue;font-weight:bold\">Covered by</span> "
							+ experts[Integer.parseInt(skill2expertIdS[i])] + "<br>");
						}
						out.print("</td> <td style='border:solid 1px'>");
						for (int i = 0; i < expertIds.length; i++) {
							out.print("&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:red\">" + expertIdsS[i] + "</span> : "
							+ experts[expertIdsS[i]] + "<br>");
						}
						out.print("</td>");
						%>
						<td style='border:solid 1px'>
								<p id="S2Score"> 0 </p>
								<button type='button' class="button button2" onclick="minusButton(S2Score)"id="S2Bad" value='-1'>-1</button>
								<button type='button' class="button button2" onclick="plusButton(S2Score)" id="S2Good" value='+1'>+1</button>
							</td>
							</tr>
					</table>
					<div id="tabsSP2Types">
						<ul>
							<li><a href="#s1B">BFS</a></li>
							<li><a href="#s2B">Clique</a></li>
							<li><a href="#s3B">Clique+Neighbors</a></li>
						</ul>
						<div id="s1B"></div>
						<div id="s2B"></div>
						<div id="s3B"></div>
					</div>
				</div>
				<div id="teamSP3">
					<table style="width: 100%;">
						<tr>
							<th style='border: solid 1px'>Skill Coverage</th>
							<th style='border: solid 1px'>Team Members (ID: Name)</th>
							
							<th style='border: solid 1px'>Team Score</th>
						</tr>
						<tr>
						<%
							skill2expertIdS = (String[]) request.getAttribute("skill2expertIdS1C");
						expertIdsS = (int[]) request.getAttribute("expertIdsS1C");
						out.print("<tr>"); // first column
						out.print("<td style='border:solid 1px'>");
						for (int i = 0; i < skill2expertId.length; i++) {
							out.print("&nbsp;&nbsp;&nbsp;&nbsp;" + capIt(skillName[i])
							+ " <span style=\"color:blue;font-weight:bold\">Covered by</span> "
							+ experts[Integer.parseInt(skill2expertIdS[i])] + "<br>");
						}
						out.print("</td> <td style='border:solid 1px'>");
						for (int i = 0; i < expertIds.length; i++) {
							out.print("&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:red\">" + expertIdsS[i] + "</span> : "
							+ experts[expertIdsS[i]] + "<br>");
						}
						out.print("</td>");
						%>
						
						<td style='border:solid 1px'>
								<p id="S3Score"> 0 </p>
								<button type='button' class="button button2" onclick="minusButton(S3Score)" id="S3Bad" value='-1'>-1</button>
								<button type='button' class="button button2" onclick="plusButton(S3Score)" id="S3Good" value='+1'>+1</button>
							</td>
							
							</tr>
					</table>
					<div id="tabsSP3Types">
						<ul>
							<li><a href="#s1C">BFS</a></li>
							<li><a href="#s2C">Clique</a></li>
							<li><a href="#s3C">Clique+Neighbors</a></li>
						</ul>
						<div id="s1C"></div>
						<div id="s2C"></div>
						<div id="s3C"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<script>
		//i hate this so much
		//oh my god writing this is giving me pain
		//there has to be a way i can iterate through this or something

		var myvar = '${networkR1A}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r1A').appendChild(element)
		});

		var myvar = '${networkR1B}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r1B').appendChild(element)
		});

		var myvar = '${networkR1C}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r1C').appendChild(element)
		});

		var myvar = '${networkR2A}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r2A').appendChild(element)
		});

		var myvar = '${networkR2B}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r2B').appendChild(element)
		});

		var myvar = '${networkR2C}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r2C').appendChild(element)
		});

		var myvar = '${networkR3A}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r3A').appendChild(element)
		});

		var myvar = '${networkR3B}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r3B').appendChild(element)
		});

		var myvar = '${networkR3C}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r3C').appendChild(element)
		});

		var myvar = '${networkR4A}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r4A').appendChild(element)
		});

		var myvar = '${networkR4B}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r4B').appendChild(element)
		});

		var myvar = '${networkR4B}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r4C').appendChild(element)
		});

		var myvar = '${networkR5A}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r5A').appendChild(element)
		});

		var myvar = '${networkR5B}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r5B').appendChild(element)
		});

		var myvar = '${networkR5C}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r5C').appendChild(element)
		});

		var myvar = '${networkR6A}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r6A').appendChild(element)
		});

		var myvar = '${networkR6B}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r6B').appendChild(element)
		});

		var myvar = '${networkR6C}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r6C').appendChild(element)
		});

		var myvar = '${networkR7A}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r7A').appendChild(element)
		});

		var myvar = '${networkR7B}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r7B').appendChild(element)
		});

		var myvar = '${networkR7C}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r7C').appendChild(element)
		});

		var myvar = '${networkR8A}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r8A').appendChild(element)
		});

		var myvar = '${networkR8B}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r8B').appendChild(element)
		});

		var myvar = '${networkR8C}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('r8C').appendChild(element)
		});

		
		var myvar = '${networkF1A}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f1A').appendChild(element)
		});
		
		var myvar = '${networkF1B}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f1B').appendChild(element)
		});

		var myvar = '${networkF1C}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f1C').appendChild(element)
		});

		var myvar = '${networkF2A}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f2A').appendChild(element)
		});

		var myvar = '${networkF2B}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f2B').appendChild(element)
		});

		var myvar = '${networkF2C}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f2C').appendChild(element)
		});

		var myvar = '${networkF3A}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f3A').appendChild(element)
		});

		var myvar = '${networkF3B}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f3B').appendChild(element)
		});

		var myvar = '${networkF3C}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f3C').appendChild(element)
		});

		var myvar = '${networkF4A}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f4A').appendChild(element)
		});

		var myvar = '${networkF4B}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f4B').appendChild(element)
		});

		var myvar = '${networkF4C}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f4C').appendChild(element)
		});

		var myvar = '${networkF5A}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f5A').appendChild(element)
		});

		var myvar = '${networkF5B}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f5B').appendChild(element)
		});

		var myvar = '${networkF5C}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f5C').appendChild(element)
		});

		var myvar = '${networkF6A}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f6A').appendChild(element)
		});

		var myvar = '${networkF6B}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f6B').appendChild(element)
		});

		var myvar = '${networkF6C}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f6C').appendChild(element)
		});

		var myvar = '${networkF7A}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f7A').appendChild(element)
		});

		var myvar = '${networkF7B}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f7B').appendChild(element)
		});

		var myvar = '${networkF7C}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f7C').appendChild(element)
		});

		var myvar = '${networkF8A}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f8A').appendChild(element)
		});

		var myvar = '${networkF8B}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f8B').appendChild(element)
		});

		var myvar = '${networkF8C}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('f8C').appendChild(element)
		});

		var myvar = '${networkS1A}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('s1A').appendChild(element)
		});

		var myvar = '${networkS1B}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('s1B').appendChild(element)
		});

		var myvar = '${networkS1C}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('s1C').appendChild(element)
		});

		var myvar = '${networkS2A}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('s2A').appendChild(element)
		});

		var myvar = '${networkS2B}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('s2B').appendChild(element)
		});

		var myvar = '${networkS2C}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('s2C').appendChild(element)
		});

		var myvar = '${networkS3A}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('s3A').appendChild(element)
		});

		var myvar = '${networkS3B}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('s3B').appendChild(element)
		});

		var myvar = '${networkS3C}'
		var viz = new Viz();
		var svg = viz.renderSVGElement(myvar);
		svg.then(function(element) {
			document.getElementById('s3C').appendChild(element)
		});
	</script>
	<script src="javascripts/jquery.js"></script>
	<script src="javascripts/jquery-ui.js"></script>
	
	<script>
		$("#tabTeamTypes").tabs();
		$("#tabsSPTeams").tabs();
		$("#tabsSP1Types").tabs();
		$("#tabsSP2Types").tabs();
		$("#tabsSP3Types").tabs();
		$("#tabsFRWTeams").tabs();
		$("#tabsFRW1Types").tabs();
		$("#tabsFRW2Types").tabs();
		$("#tabsFRW3Types").tabs();
		$("#tabsRWTeams").tabs();
		$("#tabsRW1Types").tabs();
		$("#tabsRW2Types").tabs();
		$("#tabsRW3Types").tabs();
	</script>
</body>
</html>