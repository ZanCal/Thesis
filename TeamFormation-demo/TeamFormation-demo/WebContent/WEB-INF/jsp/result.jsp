<!doctype html>
<!--[if lt IE 7 ]><html class="ie ie6" lang="en"> <![endif]-->
<!--[if IE 7 ]><html class="ie ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]><html class="ie ie8" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> 	<html lang="en"> <!--<![endif]-->
<head>
	<!-- style type="text/css">  
		table, th, td {
		  border: 1px solid black;
		  border-collapse: collapse;
		}            
	</style-->
	
	<!-- General Metas -->
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	<!-- Force Latest IE rendering engine -->
	<title>Performance</title>
	<meta name="description" content="">
	<meta name="author" content="">
	<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
	
	<!-- Mobile Specific Metas -->
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" /> 
	
    <link rel="stylesheet" href="jquery-ui.css">

    <script src="javascripts/node_modules/viz.js/viz.js"></script>
    <script src="javascripts/node_modules/viz.js/full.render.js"></script>
    
</head>
<body>
    <div>   
    	<h3> Team Formation:</h3>
    </div>  
  <table style="width: 100%;">
	  <col width="30%">
	  <tr>
	    <th>  </th><th style='border:solid 1px' colspan="2"> Fast Random Walk</th >
	  </tr>
	  <tr>
	    <th style='border:solid 1px'> Existing Project Skills  </th><th style='border:solid 1px'> Skill Coverage </th > <th style='border:solid 1px'> Team Members (ID: Name) </th>
	  </tr>  
	    <%
		String [] experts = (String []) getServletContext().getAttribute("experts");
		
	    String [] skillName = (String []) request.getAttribute("skillName");
		
		String [] skill2expertId = (String []) request.getAttribute("skill2expertIdR");
		int [] expertIds = (int []) request.getAttribute("expertIdsR");
		
		String[] perfR = (String []) request.getAttribute("perfR");
		String[] perfS = (String []) request.getAttribute("perfS");
		String[] perfV = (String []) request.getAttribute("perfB");
		

	    out.print("<tr><td td style='border:solid 1px'>");
	    for (int i=0; i<skillName.length;i++)
	    	out.print("&nbsp;&nbsp;&nbsp;&nbsp;"+capIt(skillName[i])+"<br>");
	    out.print("</td><td style='border:solid 1px'>");
	    for (int i=0; i<skill2expertId.length;i++)
	    	out.print("&nbsp;&nbsp;&nbsp;&nbsp;"+capIt(skillName[i])+" <span style=\"color:blue;font-weight:bold\">Covered by</span> "+experts[Integer.parseInt(skill2expertId[i])]+"<br>");
	    	
	    out.print("</td> <td style='border:solid 1px'>");
	    for (int i=0; i<expertIds.length;i++)
	    	out.print("&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:red\">"+expertIds[i]+"</span> : "+experts[expertIds[i]]+"<br>");
	    out.print("</td></tr>");

	    %>

  </table>
  <br>
  <table style="width: 100%;">
	  <col width="30%">
	  <tr>
	    <th>  </th>
	    <th style='border:solid 1px' colspan="2"> Random Walk</th >
	  </tr>
	  <tr>
	   <th style='border:solid 1px'>  </th>
	   <th style='border:solid 1px'> Skill Coverage </th > <th style='border:solid 1px'> Team Members (ID: Name) </th>
	  </tr>  
	    <%
		
		out.print("<tr><td style='border:solid 1px'>");
	    out.print("</td><td style='border:solid 1px'>");
	    for (int i=0; i<skill2expertId.length;i++)
	    	out.print("&nbsp;&nbsp;&nbsp;&nbsp;"+capIt(skillName[i])+" <span style=\"color:blue;font-weight:bold\">Covered by</span> "+experts[Integer.parseInt(skill2expertId[i])]+"<br>");
	    	
	    out.print("</td> <td style='border:solid 1px'>");
	    for (int i=0; i<expertIds.length;i++)
	    	out.print("&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:red\">"+expertIds[i]+"</span> : "+experts[expertIds[i]]+"<br>");
	    out.print("</td></tr>");

	    %>

  </table>
  <br>
  <!--  div>   
    	<h3> Team Formation (Shortest Path):</h3>
   </div-->  
  <table style="width: 100%;">
	  <col width="30%">
	  <tr>
	    <th style='border:solid 1px'> Team Effectiveness </th>
	    <th style='border:solid 1px' colspan="2"> Shortest Path</th >
	  </tr>
	  <tr>
	    <td rowspan="2">
	    	<table>
	    	  <col width="25%">	
	    	  <%
	    		//out.print("<tr><td></td><td align=\"center\">No. of Papers</td><td align=\"center\">Citation</td></tr>");
	    		//out.print("<tr><td>Random Walk</td><td align=\"center\">"+perfR[0]+"</td><td align=\"center\">"+perfR[1]+"</td></tr>");
	    		//out.print("<tr><td>Shortest Path</td><td align=\"center\">"+perfS[0]+"</td><td align=\"center\">"+perfS[1]+"</td></tr>");
	    		
	    		out.print("<tr><th style='text-decoration: underline;'> Method</th><th align=\"center\" style='text-decoration: underline;'>Citation</th></tr>");
	    		out.print("<tr><td>Random Walk<td align=\"center\">"+perfR[1]+"</td></tr>");
	    		out.print("<tr><td>Shortest Path</td><td align=\"center\">"+perfS[1]+"</td></tr>");
	    	  %>
	    	</table>
	    </td>
	    <th style='border:solid 1px'> Skill Coverage </th> <th style='border:solid 1px'> Team Members (ID: Name) </th>
	  </tr>  
	    <%
				
		String [] skill2expertIdS = (String []) request.getAttribute("skill2expertIdS");
		int [] expertIdsS = (int []) request.getAttribute("expertIdsS");

	    //out.print("<tr><td></td>"); // first column
	    out.print("<tr>"); // first column
	    out.print("<td style='border:solid 1px'>");
	    for (int i=0; i<skill2expertId.length;i++)
	    	out.print("&nbsp;&nbsp;&nbsp;&nbsp;"+capIt(skillName[i])+" <span style=\"color:blue;font-weight:bold\">Covered by</span> "+experts[Integer.parseInt(skill2expertIdS[i])]+"<br>");
	    	
	    out.print("</td> <td style='border:solid 1px'>");
	    for (int i=0; i<expertIds.length;i++)
	    	out.print("&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:red\">"+expertIdsS[i]+"</span> : "+experts[expertIdsS[i]]+"<br>");
	    out.print("</td></tr>");

	    %>
	    <%!
	    String capIt (String str){
	    	return str.substring(0, 1).toUpperCase() + str.substring(1);
	    }
	    %>
  </table>
  
  
  
  <h3>Expert in the Network (Fast Random Walk):</h3>
  <div id="svg_div2">
  </div>
  <div id="tabs1">
	<ul>
		<li><a href="#r1">BFS</a></li>
		<li><a href="#r2">Pruned BFS </a></li>
		<li><a href="#r3">Clique</a></li>
		<li><a href="#r4">Clique+Neighbors</a></li>
		<li><a href="#r5">ManyPath</a>
		<li><a href="#r6">Pruned ManyPath</a>
		<li><a href="#r7">AK-Master Node</a>
		<li><a href="#r8">Improved Pruned BFS</a>
		
		
	</ul>
	<div id="r1"> </div>
	<div id="r2"> </div>
	<div id="r3"> </div>
	<div id="r4"> </div>
	<div id="r5"> </div>
	<div id="r6"> </div>
	<div id="r7"> </div>
	<div id="r8"> </div>
	
</div>
 <h3>Expert in the Network (Shortest Path):</h3>
  <div id="tabs2">
	<ul>
		<li><a href="#s1">BFS</a></li>
		<li><a href="#s3">Clique</a></li>
		<li><a href="#s4">Clique+Neighbors</a></li>
		
	</ul>
	<div id="s1"> </div>
	<div id="s3"> </div>
	<div id="s4"> </div>
	
</div>
<h3>Expert in the Network (Random Walk):</h3>
  <div id="tabs3">
  <ul>
		<li><a href="#b1">BFS</a></li>
		<li><a href="#b2">Pruned BFS </a></li>
		<li><a href="#b3">Clique</a></li>
		<li><a href="#b4">Clique+Neighbors</a></li>
		<li><a href="#b5">ManyPath</a>
		<li><a href="#b6">Pruned ManyPath</a>
		<li><a href="#b7">AK-Master Node</a>
		<li><a href="#b8">Improved Pruned BFS</a>
		
		
	</ul>
	<div id="b1"> </div>
	<div id="b2"> </div>
	<div id="b3"> </div>
	<div id="b4"> </div>
	<div id="b5"> </div>
	<div id="b6"> </div>
	<div id="b7"> </div>
	<div id="b8"> </div>
  </div>
  
  <script>
    
    var myvar = '${networkR1}'
    var viz = new Viz();
    var svg = viz.renderSVGElement(myvar);
    svg.then(function(element){document.getElementById('r1').appendChild(element)});

    
    var myvar = '${networkR2}'
    var viz = new Viz();
    var svg = viz.renderSVGElement(myvar);
    svg.then(function(element){document.getElementById('r2').appendChild(element)});    
    
    var myvar = '${networkR3}'
    var viz = new Viz();
    var svg = viz.renderSVGElement(myvar);
    svg.then(function(element){document.getElementById('r3').appendChild(element)});     
    
    var myvar = '${networkR4}'
    var viz = new Viz();
    var svg = viz.renderSVGElement(myvar);
    svg.then(function(element){document.getElementById('r4').appendChild(element)}); 
        
    var myvar = '${networkR5}'
    var viz = new Viz();
    var svg = viz.renderSVGElement(myvar);
    svg.then(function(element){document.getElementById('r5').appendChild(element)}); 
    
    var myvar = '${networkR6}'
    var viz = new Viz();
    var svg = viz.renderSVGElement(myvar);
    svg.then(function(element){document.getElementById('r6').appendChild(element)}); 
    
    var myvar = '${networkR7}'
    var viz = new Viz();
    var svg = viz.renderSVGElement(myvar);
    svg.then(function(element){document.getElementById('r7').appendChild(element)}); 

    var myvar = '${networkR8}'
    var viz = new Viz();
    var svg = viz.renderSVGElement(myvar);
    svg.then(function(element){document.getElementById('r8').appendChild(element)}); 

    var myvar = '${networkR1b}'
	var viz = new Viz();
    var svg = viz.renderSVGElement(myvar);
    svg.then(function(element){document.getElementById('b1').appendChild(element)});

    var myvar = '${networkR2b}'
    var viz = new Viz();
    var svg = viz.renderSVGElement(myvar);
    svg.then(function(element){document.getElementById('b2').appendChild(element)});    
        
    var myvar = '${networkR3b}'
    var viz = new Viz();
    var svg = viz.renderSVGElement(myvar);
    svg.then(function(element){document.getElementById('b3').appendChild(element)});     
        
    var myvar = '${networkR4b}'
    var viz = new Viz();
    var svg = viz.renderSVGElement(myvar);
    svg.then(function(element){document.getElementById('b4').appendChild(element)}); 
            
    var myvar = '${networkR5b}'
    var viz = new Viz();
    var svg = viz.renderSVGElement(myvar);
    svg.then(function(element){document.getElementById('b5').appendChild(element)}); 
    
    var myvar = '${networkR6b}'
    var viz = new Viz();
    var svg = viz.renderSVGElement(myvar);
    svg.then(function(element){document.getElementById('b6').appendChild(element)}); 
        
	var myvar = '${networkR7b}'
    var viz = new Viz();
    var svg = viz.renderSVGElement(myvar);
    svg.then(function(element){document.getElementById('b7').appendChild(element)}); 

    var myvar = '${networkR8b}'
    var viz = new Viz();
    var svg = viz.renderSVGElement(myvar);
    svg.then(function(element){document.getElementById('b8').appendChild(element)}); 
    
    
    var myvar = '${networkS1}'
    var viz = new Viz();
    var svg = viz.renderSVGElement(myvar);
    svg.then(function(element){document.getElementById('s1').appendChild(element)});
    
    var myvar = '${networkS3}'
    var viz = new Viz();
    var svg = viz.renderSVGElement(myvar);
    svg.then(function(element){document.getElementById('s3').appendChild(element)});
    
    var myvar = '${networkS4}'
    var viz = new Viz();
    var svg = viz.renderSVGElement(myvar);
    svg.then(function(element){document.getElementById('s4').appendChild(element)});
    
    
    </script>
  <script src="javascripts/jquery.js"></script>
  <script src="javascripts/jquery-ui.js"></script>
  <script>
  	$( "#tabs1" ).tabs();
  	$( "#tabs2" ).tabs();
  	$( "#tabs3" ).tabs();

  </script>
</body>
</html>