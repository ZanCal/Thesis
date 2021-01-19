

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


					
		System.out.println("Running RWR....");
		Map<String, Integer> bestTeamRWR = rwr.buildRWRTeam(project, skillExpertMap, M, beta, RWR_RunNum);
		System.out.println(bestTeamRWR.toString());
	    Team teamR1 = new Team(bestTeamRWR,neighborMap,twoHopMap,rwr.finalScoreVector,0,experts); //BFS
	    Team teamR2 = new Team(bestTeamRWR,neighborMap,twoHopMap,rwr.finalScoreVector,1,experts); //Pruned BFS
	    Team teamR3 = new Team(bestTeamRWR,neighborMap,twoHopMap,rwr.finalScoreVector,2,experts); //Clique
	    Team teamR4 = new Team(bestTeamRWR,neighborMap,twoHopMap,rwr.finalScoreVector,3,null); //BFS + Neighbor
	    //and here I go adding things and hoping that it works! 
	    //it didn't work
	    Team teamR5 = new Team(bestTeamRWR,neighborMap,twoHopMap,rwr.finalScoreVector,4,experts); //ManyPath
	    Team teamR6 = new Team(bestTeamRWR,neighborMap,twoHopMap,rwr.finalScoreVector,5,experts); //Pruned ManyPath
	    Team teamR7 = new Team(bestTeamRWR,neighborMap,twoHopMap,rwr.finalScoreVector,6,experts); //AK-Master Node
	    Team teamR8 = new Team(bestTeamRWR,neighborMap,twoHopMap,rwr.finalScoreVector,7,experts); //NK-Master Node

	    System.out.println("Running Fast RWR....");
		Map<String, Integer> bestTeamRWR2 = rwr.buildFASTRWRTeam(project, skillExpertMap, M, beta, RWR_RunNum);
		System.out.println(bestTeamRWR2.toString());
	    Team teamR1b = new Team(bestTeamRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,0,experts); //BFS
	    Team teamR2b = new Team(bestTeamRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,1,experts); //Pruned BFS
	    Team teamR3b = new Team(bestTeamRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,2,experts); //Clique
	    Team teamR4b = new Team(bestTeamRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,3,null); //BFS + Neighbor
	    //and here I go adding things and hoping that it works! Again!
	    Team teamR5b = new Team(bestTeamRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,4,experts); //ManyPath
	    Team teamR6b = new Team(bestTeamRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,5,experts); //Pruned ManyPath
	    Team teamR7b = new Team(bestTeamRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,6,experts); //AK-Master Node
	    Team teamR8b = new Team(bestTeamRWR2,neighborMap,twoHopMap,rwr.finalScoreVector,7,experts); //NK-Master Node
	    
	    
	    System.out.println("Running SP....");
	    Map<String, Integer> bestTeamSP = sp.buildShortestPathTeam(project, skillExpertMap, twoHopMap);
	    System.out.println(bestTeamSP.toString());
	    Team teamS1 = new Team(bestTeamSP,neighborMap,twoHopMap,null,0,experts);  //BFS
	    Team teamS3 = new Team(bestTeamSP,neighborMap,twoHopMap,null,2,experts);  //Clique
	    Team teamS4 = new Team(bestTeamSP,neighborMap,twoHopMap,null,3,null);  //BFS + Neighbor

			
        // build the variables for visualization  
        
        request.setAttribute("skillName", teamR1.skillName);
        
        request.setAttribute("skill2expertIdR", teamR1.skill2expertId);
        request.setAttribute("expertIdsR", teamR1.expertIds);
        
        // different presentation
        request.setAttribute("networkR1", teamR1.network);
        request.setAttribute("networkR2", teamR2.network);
        request.setAttribute("networkR3", teamR3.network);
        request.setAttribute("networkR4", teamR4.network);
        request.setAttribute("networkR5", teamR5.network);
        request.setAttribute("networkR6", teamR6.network);
        request.setAttribute("networkR7", teamR7.network);
        request.setAttribute("networkR8", teamR8.network);
        
        
        request.setAttribute("skill2expertIdS", teamS1.skill2expertId);
        request.setAttribute("expertIdsS", teamS1.expertIds);
       
        //I have no idea what i'm doing. Here we go! >:D
        
        
        request.setAttribute("networkR1b", teamR1b.network);
        request.setAttribute("networkR2b", teamR2b.network);
        request.setAttribute("networkR3b", teamR3b.network);
        request.setAttribute("networkR4b", teamR4b.network);
        request.setAttribute("networkR5b", teamR5b.network);
        request.setAttribute("networkR6b", teamR6b.network);
        request.setAttribute("networkR7b", teamR7b.network);
        request.setAttribute("networkR8b", teamR8b.network);
        
        
        
        
        // different presentation
        request.setAttribute("networkS1", teamS1.network); // we don't have the second method
        request.setAttribute("networkS3", teamS3.network);
        request.setAttribute("networkS4", teamS4.network);
        
        String [] perfR= calPerf(hMap,teamR1.expertIds);
        String [] perfS= calPerf(hMap,teamS1.expertIds);
        
        //added code
        String [] perfB = calPerf(hMap, teamR1b.expertIds);
        
        request.setAttribute("perfR", perfR);
        request.setAttribute("perfS", perfS);       
        
        //added code
        request.setAttribute("perfB", perfB);
        
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
