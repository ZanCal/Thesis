

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cern.colt.matrix.DoubleMatrix2D;

/**
 * Servlet implementation class Getinput
 */
@WebServlet("/input")
public class Getinput extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Getinput() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        int RWR_RunNum = 20; // what is this number used for?
		
		// Get the input
		String net = request.getParameter("net");
		System.out.println("net:"+net);

		String app = request.getParameter("app");
		System.out.println("app:"+app);
		
		String pn = request.getParameter("pn");
		System.out.println("pn:"+pn);
		
		String prj = request.getParameter("prj");
		System.out.println("prj:"+prj);

		String sample = request.getParameter("sample");
		System.out.println("sample:"+sample);
		
		String prtype = request.getParameter("prtype");
		System.out.println("prtype:"+prtype);
		
		String [] skills = new String[6]; 
		
		skills[0] = request.getParameter("skill0");
		System.out.println("skill1:"+skills[0]);

		skills[1] = request.getParameter("skill1");
		System.out.println("skill2:"+skills[1]);
		
		skills[2] = request.getParameter("skill2");
		System.out.println("skill3:"+skills[2]);
		
		skills[3] = request.getParameter("skill3");
		System.out.println("skill4:"+skills[3]);
		
		skills[4] = request.getParameter("skill4");
		System.out.println("skill5:"+skills[4]);
		
		 skills[5] = request.getParameter("skill5");
		System.out.println("skill6:"+skills[5]);
		
		String [] experts = (String []) getServletContext().getAttribute("experts");

		// get essential variables from context
		
		RWR rwr = (RWR) getServletContext().getAttribute("rwr");
		ShortestPath sp = (ShortestPath)  getServletContext().getAttribute("sp");
		Map<String, Set<Integer>> skillExpertMap = (Map<String, Set<Integer>>) getServletContext().getAttribute("skillExpertMap");
		DoubleMatrix2D M = (DoubleMatrix2D) getServletContext().getAttribute("M"); 
		Map<Integer,Set<Integer>> neighborMap = ( Map<Integer,Set<Integer>>) getServletContext().getAttribute("neighborMap"); 
		Map<Integer, TwoHop[]> twoHopMap = (Map<Integer, TwoHop[]>) getServletContext().getAttribute("twoHopMap");
		double beta = (double)  getServletContext().getAttribute("beta");
		
		Map<Integer,Integer []>  hMap = (Map<Integer,Integer []>)  getServletContext().getAttribute("hMap");
	    
		Set<String> project = null;
		
		// set the project (skill set)
        
		Map<Integer, Set<String>> projects = null; 
		
		//if (prj.equals("prj1")){ // random project
		//  int numRequiredSkils;
		//	numRequiredSkils = Integer.parseInt(pn);
        //	project = rwr.createRandomProject(numRequiredSkils, skillExpertMap.keySet());
        // 	System.out.println("Random prj selected => "+project.toString());
		//} else 
		
		if(prj.equals("prj2")){  // sample project
        	
        	projects = rwr.createSampleProjects();
        	System.out.println("Sample prj selected:"+sample);        	
            project = projects.get(Integer.parseInt(sample));
        
		} else if(prj.equals("prj1")){  // custom project
	    	project = new HashSet<String>();
	    	for(int i=0; i< Integer.parseInt(pn);i++){
				if(skills[i] != null && !skills[i].equals("") && isSkill(skills[i],skillExpertMap.keySet())){	
					//System.out.println("==>skill " +i + " " + skills[i]);
					project.add(skills[i].toLowerCase());				
				}
			}        	
        }
        
        // run the model
			
		Set<Integer> usedIds = new HashSet<Integer>();
		
		System.out.println("Running RWR 1....");
		//make a new buildRWRTeam function that takes another argument of which skills to ignore 
		//or maybe it's easier to change an argument before the next function call?
		//what if we remove experts from the skillExpertMap?
		
		Map<String, Integer> bestTeamRWR1 = rwr.buildRWRTeam(project, skillExpertMap, M, beta, RWR_RunNum);
		System.out.println(bestTeamRWR1.toString());
		
	    Team teamR1A = new Team(bestTeamRWR1,neighborMap,twoHopMap,rwr.finalScoreVector,0,experts); //BFS
	    Team teamR2A = new Team(bestTeamRWR1,neighborMap,twoHopMap,rwr.finalScoreVector,1,experts); //Pruned BFS
	    Team teamR3A = new Team(bestTeamRWR1,neighborMap,twoHopMap,rwr.finalScoreVector,2,experts); //Clique
	    Team teamR4A = new Team(bestTeamRWR1,neighborMap,twoHopMap,rwr.finalScoreVector,3,null); //BFS + Neighbor
	    Team teamR5A = new Team(bestTeamRWR1,neighborMap,twoHopMap,rwr.finalScoreVector,4,experts); //ManyPath
	    Team teamR6A = new Team(bestTeamRWR1,neighborMap,twoHopMap,rwr.finalScoreVector,5,experts); //Pruned ManyPath
	    Team teamR7A = new Team(bestTeamRWR1,neighborMap,twoHopMap,rwr.finalScoreVector,6,experts); //AK-Master Node
	    Team teamR8A = new Team(bestTeamRWR1,neighborMap,twoHopMap,rwr.finalScoreVector,7,experts); //NK-Master Node
	    
	    System.out.println("Expert Ids");
	    for (Integer id:teamR1A.expertIds) {
	    	System.out.println(id);
	    	usedIds.add(id);
	    }
	    
	    System.out.println("Running RWR 2....");
	    Map<String, Integer> bestTeamRWR2 = rwr.buildRWRTeam(project, skillExpertMap, M, beta, RWR_RunNum, usedIds);
		System.out.println(bestTeamRWR2.toString());
	    Team teamR1B = new Team(bestTeamRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,0,experts); //BFS
	    Team teamR2B = new Team(bestTeamRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,1,experts); //Pruned BFS
	    Team teamR3B = new Team(bestTeamRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,2,experts); //Clique
	    Team teamR4B = new Team(bestTeamRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,3,null); //BFS + Neighbor
	    Team teamR5B = new Team(bestTeamRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,4,experts); //ManyPath
	    Team teamR6B = new Team(bestTeamRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,5,experts); //Pruned ManyPath
	    Team teamR7B = new Team(bestTeamRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,6,experts); //AK-Master Node
	    Team teamR8B = new Team(bestTeamRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,7,experts); //NK-Master Node
	    
	    System.out.println("Expert Ids");
	    for (Integer id:teamR1B.expertIds) {
	    	System.out.println(id);
	    	usedIds.add(id);
	    }
	    
	    System.out.println("Running RWR 3....");
	    Map<String, Integer> bestTeamRWR3 = rwr.buildRWRTeam(project, skillExpertMap, M, beta, RWR_RunNum, usedIds);
		System.out.println(bestTeamRWR2.toString());
	    Team teamR1C = new Team(bestTeamRWR3,neighborMap,twoHopMap,rwr.finalScoreVector,0,experts); //BFS
	    Team teamR2C = new Team(bestTeamRWR3,neighborMap,twoHopMap,rwr.finalScoreVector,1,experts); //Pruned BFS
	    Team teamR3C = new Team(bestTeamRWR3,neighborMap,twoHopMap,rwr.finalScoreVector,2,experts); //Clique
	    Team teamR4C = new Team(bestTeamRWR3,neighborMap,twoHopMap,rwr.finalScoreVector,3,null); //BFS + Neighbor
	    Team teamR5C = new Team(bestTeamRWR3,neighborMap,twoHopMap,rwr.finalScoreVector,4,experts); //ManyPath
	    Team teamR6C = new Team(bestTeamRWR3,neighborMap,twoHopMap,rwr.finalScoreVector,5,experts); //Pruned ManyPath
	    Team teamR7C = new Team(bestTeamRWR3,neighborMap,twoHopMap,rwr.finalScoreVector,6,experts); //AK-Master Node
	    Team teamR8C = new Team(bestTeamRWR3,neighborMap,twoHopMap,rwr.finalScoreVector,7,experts); //NK-Master Node
	    
	    System.out.println("Expert Ids");
	    for (Integer id:teamR1C.expertIds) {
	    	System.out.println(id);
	    }
	    
	    
	    
	    System.out.println("Running Fast RWR 1....");
		Map<String, Integer> bestTeamFRWR1 = rwr.buildFASTRWRTeam(project, skillExpertMap, M, beta, RWR_RunNum);
		System.out.println(bestTeamRWR2.toString());
	    Team teamF1A = new Team(bestTeamFRWR1,neighborMap,twoHopMap,rwr.finalScoreVector,0,experts); //BFS
	    Team teamF2A = new Team(bestTeamFRWR1,neighborMap,twoHopMap,rwr.finalScoreVector,1,experts); //Pruned BFS
	    Team teamF3A = new Team(bestTeamFRWR1,neighborMap,twoHopMap,rwr.finalScoreVector,2,experts); //Clique
	    Team teamF4A = new Team(bestTeamFRWR1,neighborMap,twoHopMap,rwr.finalScoreVector,3,null); //BFS + Neighbor
	    Team teamF5A = new Team(bestTeamFRWR1,neighborMap,twoHopMap,rwr.finalScoreVector,4,experts); //ManyPath
	    Team teamF6A = new Team(bestTeamFRWR1,neighborMap,twoHopMap,rwr.finalScoreVector,5,experts); //Pruned ManyPath
	    Team teamF7A = new Team(bestTeamFRWR1,neighborMap,twoHopMap,rwr.finalScoreVector,6,experts); //AK-Master Node
	    Team teamF8A = new Team(bestTeamFRWR1,neighborMap,twoHopMap,rwr.finalScoreVector,7,experts); //NK-Master Node

	    usedIds = new HashSet<Integer>();
	    
	    System.out.println("Expert Ids");
	    for (Integer id:teamF1A.expertIds) {
	    	System.out.println(id);
	    	usedIds.add(id);
	    }
	    
	    System.out.println("Running Fast RWR 2....");
		Map<String, Integer> bestTeamFRWR2 = rwr.buildFASTRWRTeam(project, skillExpertMap, M, beta, RWR_RunNum, usedIds);
		System.out.println(bestTeamRWR2.toString());
	    Team teamF1B = new Team(bestTeamFRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,0,experts); //BFS
	    Team teamF2B = new Team(bestTeamFRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,1,experts); //Pruned BFS
	    Team teamF3B = new Team(bestTeamFRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,2,experts); //Clique
	    Team teamF4B = new Team(bestTeamFRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,3,null); //BFS + Neighbor
	    Team teamF5B = new Team(bestTeamFRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,4,experts); //ManyPath
	    Team teamF6B = new Team(bestTeamFRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,5,experts); //Pruned ManyPath
	    Team teamF7B = new Team(bestTeamFRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,6,experts); //AK-Master Node
	    Team teamF8B = new Team(bestTeamFRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,7,experts); //NK-Master Node
	    
	    System.out.println("Expert Ids");
	    for (Integer id:teamF1B.expertIds) {
	    	System.out.println(id);
	    	usedIds.add(id);
	    }
	    
	    System.out.println("Running Fast RWR 3....");
		Map<String, Integer> bestTeamFRWR3 = rwr.buildFASTRWRTeam(project, skillExpertMap, M, beta, RWR_RunNum, usedIds);
		System.out.println(bestTeamRWR2.toString());
	    Team teamF1C = new Team(bestTeamFRWR3,neighborMap,twoHopMap,rwr.finalScoreVector,0,experts); //BFS
	    Team teamF2C = new Team(bestTeamFRWR3,neighborMap,twoHopMap,rwr.finalScoreVector,1,experts); //Pruned BFS
	    Team teamF3C = new Team(bestTeamFRWR3,neighborMap,twoHopMap,rwr.finalScoreVector,2,experts); //Clique
	    Team teamF4C = new Team(bestTeamFRWR3,neighborMap,twoHopMap,rwr.finalScoreVector,3,null); //BFS + Neighbor
	    Team teamF5C = new Team(bestTeamFRWR3,neighborMap,twoHopMap,rwr.finalScoreVector,4,experts); //ManyPath
	    Team teamF6C = new Team(bestTeamFRWR3,neighborMap,twoHopMap,rwr.finalScoreVector,5,experts); //Pruned ManyPath
	    Team teamF7C = new Team(bestTeamFRWR3,neighborMap,twoHopMap,rwr.finalScoreVector,6,experts); //AK-Master Node
	    Team teamF8C = new Team(bestTeamFRWR3,neighborMap,twoHopMap,rwr.finalScoreVector,7,experts); //NK-Master Node
	    
	    
	    System.out.println("Expert Ids");
	    for (Integer id:teamF1C.expertIds) {
	    	System.out.println(id);
	    	usedIds.add(id);
	    }
	    usedIds = new HashSet<Integer>();
	    
	    
	    System.out.println("Running SP 1....");
	    Map<String, Integer> bestTeamSP1 = sp.buildShortestPathTeam(project, skillExpertMap, twoHopMap);
	    System.out.println(bestTeamSP1.toString());
	    Team teamS1A = new Team(bestTeamSP1,neighborMap,twoHopMap,null,0,experts);  //BFS
	    Team teamS2A = new Team(bestTeamSP1,neighborMap,twoHopMap,null,2,experts);  //Clique
	    Team teamS3A = new Team(bestTeamSP1,neighborMap,twoHopMap,null,3,null);  //BFS + Neighbor

	    System.out.println("Expert Ids");
	    for (Integer id:teamS1A.expertIds) {
	    	System.out.println(id);
	    	usedIds.add(id);
	    }
	    
	    System.out.println("Running SP 2....");
	    Map<String, Integer> bestTeamSP2 = sp.buildShortestPathTeam(project, skillExpertMap, twoHopMap, usedIds);
	    System.out.println(bestTeamSP2.toString());
	    Team teamS1B = new Team(bestTeamSP2,neighborMap,twoHopMap,null,0,experts);  //BFS
	    Team teamS2B = new Team(bestTeamSP2,neighborMap,twoHopMap,null,2,experts);  //Clique
	    Team teamS3B = new Team(bestTeamSP2,neighborMap,twoHopMap,null,3,null);  //BFS + Neighbor
	    
	    System.out.println("Expert Ids");
	    for (Integer id:teamS1B.expertIds) {
	    	System.out.println(id);
	    	usedIds.add(id);
	    }
	    
	    System.out.println("Running SP 3....");
	    Map<String, Integer> bestTeamSP3 = sp.buildShortestPathTeam(project, skillExpertMap, twoHopMap, usedIds);
	    System.out.println(bestTeamSP3.toString());
	    Team teamS1C = new Team(bestTeamSP3,neighborMap,twoHopMap,null,0,experts);  //BFS
	    Team teamS2C = new Team(bestTeamSP3,neighborMap,twoHopMap,null,2,experts);  //Clique
	    Team teamS3C = new Team(bestTeamSP3,neighborMap,twoHopMap,null,3,null);  //BFS + Neighbor
        // build the variables for visualization  
        
	    System.out.println("Expert Ids");
	    for (Integer id:teamS1C.expertIds) {
	    	System.out.println(id);
	    	usedIds.add(id);
	    }
	    
        request.setAttribute("skillName", teamR1A.skillName);
        
        
        request.setAttribute("expertIdsR", teamR1A.expertIds);
        
        // different presentation
        request.setAttribute("networkR1A", teamR1A.network);
        request.setAttribute("networkR2A", teamR2A.network);
        request.setAttribute("networkR3A", teamR3A.network);
        request.setAttribute("networkR4A", teamR4A.network);
        request.setAttribute("networkR5A", teamR5A.network);
        request.setAttribute("networkR6A", teamR6A.network);
        request.setAttribute("networkR7A", teamR7A.network);
        request.setAttribute("networkR8A", teamR8A.network);
        
        request.setAttribute("networkR1B", teamR1B.network);
        request.setAttribute("networkR2B", teamR2B.network);
        request.setAttribute("networkR3B", teamR3B.network);
        request.setAttribute("networkR4B", teamR4B.network);
        request.setAttribute("networkR5B", teamR5B.network);
        request.setAttribute("networkR6B", teamR6B.network);
        request.setAttribute("networkR7B", teamR7B.network);
        request.setAttribute("networkR8B", teamR8B.network);
        
        request.setAttribute("networkR1C", teamR1C.network);
        request.setAttribute("networkR2C", teamR2C.network);
        request.setAttribute("networkR3C", teamR3C.network);
        request.setAttribute("networkR4C", teamR4C.network);
        request.setAttribute("networkR5C", teamR5C.network);
        request.setAttribute("networkR6C", teamR6C.network);
        request.setAttribute("networkR7C", teamR7C.network);
        request.setAttribute("networkR8C", teamR8C.network);
        
        request.setAttribute("skill2expertIdR1A", teamR1A.skill2expertId);
        request.setAttribute("expertIdsR1A", teamR1A.expertIds);
        request.setAttribute("skill2expertIdR1B", teamR1B.skill2expertId);
        request.setAttribute("expertIdsR1B", teamR1B.expertIds);
        request.setAttribute("skill2expertIdR1C", teamR1C.skill2expertId);
        request.setAttribute("expertIdsR1C", teamR1C.expertIds);
        request.setAttribute("expertIdsS", teamR1A.expertIds);
       
        //I have no idea what i'm doing. Here we go! >:D
        
        
        request.setAttribute("networkF1A", teamF1A.network);
        request.setAttribute("networkF2A", teamF2A.network);
        request.setAttribute("networkF3A", teamF3A.network);
        request.setAttribute("networkF4A", teamF4A.network);
        request.setAttribute("networkF5A", teamF5A.network);
        request.setAttribute("networkF6A", teamF6A.network);
        request.setAttribute("networkF7A", teamF7A.network);
        request.setAttribute("networkF8A", teamF8A.network);
        
        request.setAttribute("networkF1B", teamF1B.network);
        request.setAttribute("networkF2B", teamF2B.network);
        request.setAttribute("networkF3B", teamF3B.network);
        request.setAttribute("networkF4B", teamF4B.network);
        request.setAttribute("networkF5B", teamF5B.network);
        request.setAttribute("networkF6B", teamF6B.network);
        request.setAttribute("networkF7B", teamF7B.network);
        request.setAttribute("networkF8B", teamF8B.network);
        
        request.setAttribute("networkF1C", teamF1C.network);
        request.setAttribute("networkF2C", teamF2C.network);
        request.setAttribute("networkF3C", teamF3C.network);
        request.setAttribute("networkF4C", teamF4C.network);
        request.setAttribute("networkF5C", teamF5C.network);
        request.setAttribute("networkF6C", teamF6C.network);
        request.setAttribute("networkF7C", teamF7C.network);
        request.setAttribute("networkF8C", teamF8C.network);
        
        
        request.setAttribute("skill2expertIdF1A", teamF1A.skill2expertId);
        request.setAttribute("expertIdsF1A", teamF1A.expertIds);
        
        request.setAttribute("skill2expertIdF1B", teamF1B.skill2expertId);
        request.setAttribute("expertIdsF1B", teamF1B.expertIds);
        
        request.setAttribute("skill2expertIdF1C", teamF1C.skill2expertId);
        request.setAttribute("expertIdsF1C", teamF1C.expertIds);
        
        // different presentation
        request.setAttribute("networkS1A", teamS1A.network); 
        request.setAttribute("networkS2A", teamS2A.network);
        request.setAttribute("networkS3A", teamS3A.network);
        
        request.setAttribute("networkS1B", teamS1B.network); 
        request.setAttribute("networkS2B", teamS2B.network);
        request.setAttribute("networkS3B", teamS3B.network);
        
        request.setAttribute("networkS1C", teamS1C.network); 
        request.setAttribute("networkS2C", teamS2C.network);
        request.setAttribute("networkS3C", teamS3C.network);
        

        request.setAttribute("skill2expertIdS", teamR1A.skill2expertId);
        request.setAttribute("skill2expertIdS1A", teamS1A.skill2expertId);
        request.setAttribute("expertIdsS1A", teamS1A.expertIds);
        request.setAttribute("skill2expertIdS1B", teamS1B.skill2expertId);
        request.setAttribute("expertIdsS1B", teamS1B.expertIds);
        request.setAttribute("skill2expertIdS1C", teamS1C.skill2expertId);
        request.setAttribute("expertIdsS1C", teamS1C.expertIds);
        
        String [] perfR= calPerf(hMap,teamR1A.expertIds);
        String [] perfS= calPerf(hMap,teamS1A.expertIds);
        String [] perfF = calPerf(hMap, teamF1A.expertIds);
        
        request.setAttribute("perfR", perfR);
        request.setAttribute("perfS", perfS);       

        request.setAttribute("perfF", perfF);
        
		request.getRequestDispatcher("/WEB-INF/jsp/result.jsp").forward(request, response);	
	}
	private String[] calPerf(Map<Integer, Integer[]> hMap, int[] expertIds) {
		String [] r = new String[3];
		int r0=0,r1=0,r2=0;
		for(int i=0;i<expertIds.length;i++){
			Integer [] l = hMap.get(expertIds[i]);
			r0 += l[0];
			r1 += l[1];
			r2 += l[2];
		}
		
		r[0] = Integer.toString(r0);
		r[1] = Integer.toString(r1);
		r[2] = Integer.toString(r2);
		return r;
	}

	private boolean isSkill(String skill, Set<String> eligibleSkillsSet) {
		if(eligibleSkillsSet.contains(skill.toLowerCase()))
			return true;
		else
			return false;
	}

}
