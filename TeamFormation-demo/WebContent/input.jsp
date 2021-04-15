<!doctype html>
<!--[if lt IE 7 ]><html class="ie ie6" lang="en"> <![endif]-->
<!--[if IE 7 ]><html class="ie ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]><html class="ie ie8" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<html lang="en">
<!--<![endif]-->
<head>

<!-- General Metas -->
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<!-- Force Latest IE rendering engine -->
<title>Input Information Form</title>
<meta name="description" content="">
<meta name="author" content="">
<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

<!-- Mobile Specific Metas -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />

<link rel="stylesheet" href="styles/InputInfoForm.css" >

<link href="jquery-ui.css" rel="stylesheet">

<script>
function dis() {
	var options= document.getElementById('presentid').options;
	var x = document.getElementById("appid").value;
  	if(x == "m3" ){ // shortest path
 	   options[1].disabled = true;
 	   options[0].selected = true;
  	} else {
  		options[1].disabled = false;  
  		options[0].selected = false;
  	}
}

</script>
</head>

<body>
	<div id ='header'>
		<h1>RW-Team: Robust Team Formation Using Random Walk</h1>
				
		<p id="BasicInfo">An expert network is a social network that represents professionals and their skills.
		Expert networks can be modeled as graphs whose nodes correspond to experts (labeled with their skills) 
		   and whose edges represent past collaborations e.g., co-authoring a paper in DBLP or collaborating on 
		   the same project in GitHub.</p>
	</div>

	<div>
		<form action="input" method="post" id="formId">
			<table style="width: 100%; ">
				<col width="30%">
				<tr id = 'expertNetwork'>
					<td>
						<h3> Expert Network:</h3>
						<br>
					</td>
					<td valign=top>
					    <select name="net" id="netid" style="width:250px;">
							<option value="net1"> DBLP Expert Network</option>
							<option value="net2"> GitHub Expert Network</option>
					    </select>
					    <br><br>
				   </td>
				</tr>
				<tr id = 'projectSkillNeeds'>
					<td align="left" valign=top>
						<h3>Project Skill Needs:</h3>
					</td>
					<td align="left">
					<input id='radio1' type="radio" name="prj"  value="prj2"  onclick="document.getElementById('pnid').disabled = true; document.getElementById('sampleid').disabled = false;"  > 
  					Select the Skill from Samples:<br><br>
  					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
  					
  					<select name="sample" id="sampleid"  disabled>
							<option selected="selected" value="0">Sample 0 </option>
							<option value="1">Sample 1</option>
							<option value="2">Sample 2</option>
							<option value="3">Sample 3</option>
						    <option value="4">Sample 4</option>
						    <option value="5">Sample 5</option>			
					</select> <br><br>	
					<input id = 'radio2' type="radio" name="prj"  value="prj1" checked onclick="document.getElementById('pnid').disabled = false; document.getElementById('sampleid').disabled = true;"> 
					Select the Arbitrary Skills: <br>
					&nbsp;&nbsp;&nbsp;&nbsp;
					Choose Number of Skills: &nbsp;&nbsp;
					<select name="pn" id="pnid" style="width:65px;">
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
					</select> <br><br>
					<div id="skills"> </div>
  					<br>
  					
					</td>
				</tr>
				<tr id = 'kTeams'>
					<td align="left" valign=top>
						<h3>Display Top-K teams: </h3>
					</td>
					<td>
						<select name="K-Selection" id="K-Selection"  > 
							<option value="1">1 Team </option>
							<option value="3">3 Teams</option>
							<option value="5">5 Teams</option>
						</select>
					</td>
				</tr>
				<tr id = 'teamGenMethod'>
					<td align="left" valign=top>
						<h3>Team Generation Method: </h3>
					</td>
					<td>
						<select name="teamMethodSelection" id = "teamMethodSelection">
							<option value = "f">Fast Random Walkers with Restart</option>
							<option value = "r">Random Walkers with Restart </option>
							<option value = "s">Shortest Path</option>
							<option value = "e">Embedding</option>
							<!--<option value = "h">Hybrid Shortest Path and Random Walkers with Reset</option>-->
						</select>
					</td>
				</tr>
			</table>
			<br>
			<div style="text-align:center; width: 100%;">  
			 <!--  button type="submit" class="button button2" value="true" name="loginbutt" > Discover Team</button-->
			 <!--  lol butt -->								
			  <input type='button' class="button button2" id = "mysub" value=' Discover Team'>
			  											
			</div>
		</form>		
	<!-- input type='button' name = "mysub" value='Submit form' onClick='submitDetailsForm()' /-->
	</div> 
	<div id ="tst"> </div>

<script src="javascripts/jquery.js"></script>
<script src="javascripts/jquery-ui.js"></script>

<script>
	$("#netid").selectmenu({ width : $("#netid").width() });
	$("#appid").selectmenu({ width : $("#appid").width() });
	$("#pnid").selectmenu({ width : $("#pnid").width() });
	$("#sampleid").selectmenu({ width : $("#sampleid").width() });
	$("#presentid").selectmenu({ width : $("#presentid").width() });
	
    var text1 = '<input type="text" name="skill#" id="in0" value="bioinformatics" />';
    var text2 = '<input type="text" name="skill#" id="in1" value="optimizations" />';

    $('#skills').append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Skill 1 : '+text1.replace("#", 0)+'<span id="spn0" style="color:red;font-weight:bold">X (Not exist)</span><br/>');
    $('#skills').append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Skill 2 : '+text2.replace("#", 1)+'<span id="spn1" style="color:red;font-weight:bold">X (Not exist)</span><br/>');
    
    $('#spn0').hide();
    $('#spn1').hide();
    
	//$("#prjid").buttonset();
</script> 
 
<script>


$('input[type=radio][name=prj]').change(function() {
    if (this.value == 'prj1') {
    	
    	$('input[type=text]').prop('disabled', false);
    	
        $("#pnid").prop('disabled', false);
        $("#sampleid").prop('disabled', true);
        $("#sampleid").selectmenu("refresh");
        $("#pnid").selectmenu("refresh");
    }
    else if (this.value == 'prj2') {
		
    	$('input[type=text]').prop('disabled', true);
		
        $("#pnid").prop('disabled', true);
        $("#sampleid").prop('disabled', false);
        $("#sampleid").selectmenu("refresh");
        $("#pnid").selectmenu("refresh");
    }
});
$('#appid').on('selectmenuchange', function() {
	if($(this).find('option:selected').val()=="m3"){
        $("#opid").prop('disabled', true);
        $("#presentid").val("0");
        $("#presentid").selectmenu("refresh");
	}
	else{
        $("#opid").prop('disabled', false);
        $("#presentid").selectmenu("refresh");
	}
});
$('#sampleid').on('selectmenuchange', function(){
	$('#skills').empty();
    var text2 = '<span id="spn#" style="color:red;font-weight:bold">X (Not exist)</span><br/>';
    let selection = parseInt($(this).find('option:selected').val())
	let textA = '<input type="text" name="skill#" value="" disabled />';
	let textB = '<input type="text" name="skill#" value="" disabled />';
	let textC = '<input type="text" name="skill#" value="" disabled />';

    switch(selection){
    case 0:
    	textA = '<input type="text" name="skill#" value="expressive" disabled />';
    	textB = '<input type="text" name="skill#" value="reformulation"  disabled/>';
        
		$('#skills').append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Skill '+(1)+' : ' +textA.replace('#', 0)+text2.replace('#', 0));
    	$('#spn'+0).hide();
        $('#skills').append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Skill '+(2)+' : ' +textB.replace('#', 1)+text2.replace('#', 1));
        $('#spn'+1).hide();
    	break;
    case 1:
    	textA = '<input type="text" name="skill#" value="bioinformatics" disabled />';
    	textB = '<input type="text" name="skill#" value="tensor" disabled />';
        
    	$('#skills').append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Skill '+(1)+' : ' +textA.replace('#', 0)+text2.replace('#', 0));
    	$('#spn'+0).hide();
        $('#skills').append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Skill '+(2)+' : ' +textB.replace('#', 1)+text2.replace('#', 1));
        $('#spn'+1).hide();
    	break;
    case 2:
    	textA = '<input type="text" name="skill#" value="enhancing" disabled />';
    	textB = '<input type="text" name="skill#" value="snapshot" disabled />';
        
    	$('#skills').append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Skill '+(1)+' : ' +textA.replace('#', 0)+text2.replace('#', 0));
    	$('#spn'+0).hide();
        $('#skills').append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Skill '+(2)+' : ' +textB.replace('#', 1)+text2.replace('#', 1));
        $('#spn'+1).hide();
    	break;
    case 3:
    	textA = '<input type="text" name="skill#" value="expressive" disabled />';
    	textB = '<input type="text" name="skill#" value="embedding" disabled />';
        
    	$('#skills').append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Skill '+(1)+' : ' +textA.replace('#', 0)+text2.replace('#', 0));
    	$('#spn'+0).hide();
        $('#skills').append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Skill '+(2)+' : ' +textB.replace('#', 1)+text2.replace('#', 1));
        $('#spn'+1).hide();
    	break;
    case 4:
    	textA = '<input type="text" name="skill#" value="reformulation" disabled />';
    	textB = '<input type="text" name="skill#" value="autonomous" disabled />';
        
    	$('#skills').append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Skill '+(1)+' : ' +textA.replace('#', 0)+text2.replace('#', 0));
    	$('#spn'+0).hide();
        $('#skills').append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Skill '+(2)+' : ' +textB.replace('#', 1)+text2.replace('#', 1));
        $('#spn'+1).hide();
    	break;
    case 5:
    	textA = '<input type="text" name="skill#" value="bioinformatics" disabled />';
    	textB = '<input type="text" name="skill#" value="autonomous" disabled />';
    	textC = '<input type="text" name="skill#" value="embedding" disabled />';
        
    	$('#skills').append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Skill '+(1)+' : ' +textA.replace('#', 0)+text2.replace('#', 0));
    	$('#spn'+0).hide();
        $('#skills').append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Skill '+(2)+' : ' +textB.replace('#', 1)+text2.replace('#', 1));
        $('#spn'+1).hide();
        $('#skills').append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Skill '+(3)+' : ' +textC.replace('#', 2)+text2.replace('#', 2));
        $('#spn'+2).hide();
    	break;
    }
});
$('#pnid').on('selectmenuchange', function() {
	$('#skills').empty();
    var text = '<input type="text" name="skill#" value="" />';
    var text2 = '<span id="spn#" style="color:red;font-weight:bold">X (Not exist)</span><br/>';
    for (var i = 0; i < parseInt($(this).find('option:selected').val()); i++) {
    	$('#skills').append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Skill '+(i+1)+' : ' +text.replace('#', i)+text2.replace('#', i));
        $('#spn'+i).hide();
	}
});


$(document).on("click", "#mysub", function() { // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...

	if( (document.getElementById('sampleid').disabled == true) && (document.getElementById('radio1').checked)){
		console.log('null error condition')
		document.getElementById('sampleid').disabled = false;
		
	}
		
	 	$.get("validate", $('form#formId').serialize() , function(responseText) {   // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
			console.log(responseText); //response text is "OK"
		 
			if(responseText!='OK'){
				for ( var i = 0; i < responseText.length; i++ ){	
					if(responseText.charAt(i)=="0") // not there
						$('#spn'+i).show();	
					else
						$('#spn'+i).hide();	
				}
        	}else{
				$('#spn'+0).hide();
				$('#spn'+1).hide();	
				$('#spn'+2).hide();	
				$('#spn'+3).hide();	
				$('#spn'+4).hide();	
				$('#spn'+5).hide();	
            	$("#formId").submit();       
        	}
    	});
	
});
</script>
<script>

</script>
<script >
var s = 'some#multi#word#string'.replace(/\#/g, 1);
//alert(s);
    function submitDetailsForm() {
       $("#formId").submit();       
    }
</script>
</body>
</html>