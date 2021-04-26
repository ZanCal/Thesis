
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int RWR_RunNum = 20; // what is this number used for?

		// Get the input
		String net = request.getParameter("net");
		System.out.println("net:" + net);

		String app = request.getParameter("app");
		System.out.println("app:" + app);

		String pn = request.getParameter("pn");
		System.out.println("pn:" + pn);

		String prj = request.getParameter("prj");
		System.out.println("prj:" + prj);

		String sample = request.getParameter("sample");
		System.out.println("sample:" + sample);

		String prtype = request.getParameter("prtype");
		System.out.println("prtype:" + prtype);

		String[] skills = new String[6];

		skills[0] = request.getParameter("skill0");
		System.out.println("skill1:" + skills[0]);

		skills[1] = request.getParameter("skill1");
		System.out.println("skill2:" + skills[1]);

		skills[2] = request.getParameter("skill2");
		System.out.println("skill3:" + skills[2]);

		skills[3] = request.getParameter("skill3");
		System.out.println("skill4:" + skills[3]);

		skills[4] = request.getParameter("skill4");
		System.out.println("skill5:" + skills[4]);

		skills[5] = request.getParameter("skill5");
		System.out.println("skill6:" + skills[5]);

		String[] experts = (String[]) getServletContext().getAttribute("experts");

		
		RWR rwr = (RWR) getServletContext().getAttribute("rwr");
		ShortestPath sp = (ShortestPath) getServletContext().getAttribute("sp");
		SmallestEmbedding emb = (SmallestEmbedding) getServletContext().getAttribute("emb");

		Map<String, Set<Integer>> skillExpertMap = (Map<String, Set<Integer>>) getServletContext().getAttribute("skillExpertMap");
		DoubleMatrix2D M = (DoubleMatrix2D) getServletContext().getAttribute("M");
		Map<Integer, Set<Integer>> neighborMap = (Map<Integer, Set<Integer>>) getServletContext().getAttribute("neighborMap");
		Map<Integer, TwoHop[]> twoHopMap = (Map<Integer, TwoHop[]>) getServletContext().getAttribute("twoHopMap");
		double beta = (double) getServletContext().getAttribute("beta");
		Map<Integer, Integer[]> hMap = (Map<Integer, Integer[]>) getServletContext().getAttribute("hMap");

		Set<String> project = null;

		// set the project (skill set)

		Map<Integer, Set<String>> projects = null;


		if (prj.equals("prj2")) { // sample project

			projects = rwr.createSampleProjects();
			System.out.println("Sample prj selected:" + sample);
			project = projects.get(Integer.parseInt(sample));

		} else if (prj.equals("prj1")) { // custom project
			project = new HashSet<String>();
			for (int i = 0; i < Integer.parseInt(pn); i++) {
				if (skills[i] != null && !skills[i].equals("") && isSkill(skills[i], skillExpertMap.keySet())) {
					// System.out.println("==>skill " +i + " " + skills[i]);
					project.add(skills[i].toLowerCase());
				}
			}
		}
		
		String kTeamSelection = request.getParameter("K-Selection");
		// for some reason, parseInt does not to work
		// i'm going to do it by hand

		int kTeams = 0;

		switch (kTeamSelection) {
		case "1":
			kTeams = 1;
			break;
		case "3":
			kTeams = 3;
			break;
		case "5":
			kTeams = 5;
			break;
		}
		
		request.setAttribute("K-Selection", kTeams);
		
		String methodSelection = request.getParameter("teamMethodSelection");
		
		TeamGenerationMethod teamGen = new TeamGenerationMethod();

		Set<Integer> usedIds = new HashSet<Integer>();
		
		Map<String, Integer> bestTeam1, bestTeam2, bestTeam3, bestTeam4, bestTeam5;
		
		Team team1A, team2A, team3A, team4A, team5A, team6A, team7A, team8A;
		Team team1B, team2B, team3B, team4B, team5B, team6B, team7B, team8B;
		Team team1C, team2C, team3C, team4C, team5C, team6C, team7C, team8C;
		Team team1D, team2D, team3D, team4D, team5D, team6D, team7D, team8D;
		Team team1E, team2E, team3E, team4E, team5E, team6E, team7E, team8E;
		
		String[] performance1;
		String[] performance2;
		String[] performance3;
		String[] performance4;
		String[] performance5;
		switch(methodSelection) {
		
		case "s":
			
			teamGen = (ShortestPath) getServletContext().getAttribute("sp");
			System.out.println("Running SP 1....");
			bestTeam1 = teamGen.buildShortestPathTeam(project, skillExpertMap, twoHopMap);
			System.out.println(bestTeam1.toString());
			
			
			team1A = new Team(bestTeam1, neighborMap, twoHopMap, null, 0, experts); // BFS
			team2A = new Team(bestTeam1, neighborMap, twoHopMap, null, 2, experts); // Clique 
			team3A = new Team(bestTeam1, neighborMap, twoHopMap, null, 3, experts); // Clique + Neighbour
			request.setAttribute("network1A", team1A.network);
			request.setAttribute("network2A", team2A.network);
			request.setAttribute("network3A", team3A.network);
			
			request.setAttribute("skill2expertId1A", team1A.skill2expertId);
			request.setAttribute("expertIds1A", team1A.expertIds);
			performance1 = calPerf(hMap, team1A.expertIds);
			request.setAttribute("performance1", performance1);
			request.setAttribute("skillName", team1A.skillName);
			
			if(kTeams > 1) {
				
				for (Integer id : team1A.expertIds) {
					usedIds.add(id);
				}
				
				System.out.println("Running SP 2....");
				bestTeam2 = teamGen.buildShortestPathTeam(project, skillExpertMap, twoHopMap, usedIds);
				System.out.println(bestTeam2.toString());
				team1B = new Team(bestTeam2, neighborMap, twoHopMap, null, 0, experts); // BFS
				team2B = new Team(bestTeam2, neighborMap, twoHopMap, null, 2, experts); // Clique 
				team3B = new Team(bestTeam2, neighborMap, twoHopMap, null, 3, experts); // Clique + Neighbour
				request.setAttribute("network1B", team1B.network);
				request.setAttribute("network2B", team2B.network);
				request.setAttribute("network3B", team3B.network);

				request.setAttribute("skill2expertId1B", team1B.skill2expertId);
				request.setAttribute("expertIds1B", team1B.expertIds);
				performance2 = calPerf(hMap, team1B.expertIds);
				request.setAttribute("performance2", performance2);
				for (Integer id : team1B.expertIds) {
					usedIds.add(id);
				}
				
				System.out.println("Running SP 3....");
				bestTeam3 = teamGen.buildShortestPathTeam(project, skillExpertMap, twoHopMap, usedIds);
				System.out.println(bestTeam2.toString());
				team1C = new Team(bestTeam3, neighborMap, twoHopMap, null, 0, experts); // BFS
				team2C = new Team(bestTeam3, neighborMap, twoHopMap, null, 2, experts); // Clique 
				team3C = new Team(bestTeam3, neighborMap, twoHopMap, null, 3, experts); // Clique + Neighbour
				request.setAttribute("network1C", team1C.network);
				request.setAttribute("network2C", team2C.network);
				request.setAttribute("network3C", team3C.network);
				
				request.setAttribute("skill2expertId1C", team1C.skill2expertId);
				request.setAttribute("expertIds1C", team1C.expertIds);
				performance3 = calPerf(hMap, team1C.expertIds);
				request.setAttribute("performance3", performance3);
				
				
				if (kTeams > 3) {
					for (Integer id : team1C.expertIds) {
						usedIds.add(id);
					}
					
					System.out.println("Running SP 4....");
					bestTeam4 = teamGen.buildShortestPathTeam(project, skillExpertMap, twoHopMap, usedIds);
					System.out.println(bestTeam4.toString());
					team1D = new Team(bestTeam4, neighborMap, twoHopMap, null, 0, experts); // BFS
					team2D = new Team(bestTeam4, neighborMap, twoHopMap, null, 2, experts); // Clique 
					team3D = new Team(bestTeam4, neighborMap, twoHopMap, null, 3, experts); // Clique + Neighbour
					request.setAttribute("network1D", team1D.network);
					request.setAttribute("network2D", team2D.network);
					request.setAttribute("network3D", team3D.network);
					
					request.setAttribute("skill2expertId1D", team1D.skill2expertId);
					request.setAttribute("expertIds1D", team1D.expertIds);
					performance4 = calPerf(hMap, team1D.expertIds);
					request.setAttribute("performance4", performance4);
					
					for (Integer id : team1D.expertIds) {
						usedIds.add(id);
					}
					
					System.out.println("Running SP 5....");
					bestTeam5 = teamGen.buildShortestPathTeam(project, skillExpertMap, twoHopMap, usedIds);
					System.out.println(bestTeam5.toString());
					team1E = new Team(bestTeam5, neighborMap, twoHopMap, null, 0, experts); // BFS
					team2E = new Team(bestTeam5, neighborMap, twoHopMap, null, 2, experts); // Clique 
					team3E = new Team(bestTeam5, neighborMap, twoHopMap, null, 3, experts); // Clique + Neighbour
					request.setAttribute("network1E", team1E.network);
					request.setAttribute("network2E", team2E.network);
					request.setAttribute("network3E", team3E.network);
					
					request.setAttribute("skill2expertId1E", team1E.skill2expertId);
					request.setAttribute("expertIds1E", team1E.expertIds);
					performance5 = calPerf(hMap, team1E.expertIds);
					request.setAttribute("performance5", performance5);
				}
			}
			break;
		case "r":
			
			teamGen = (RWR) getServletContext().getAttribute("rwr");
			System.out.println("Running RWR 1....");
			bestTeam1 = teamGen.buildRWRTeam(project, skillExpertMap, M, beta, RWR_RunNum);
			System.out.println(bestTeam1.toString());
			team1A = new Team(bestTeam1, neighborMap, twoHopMap, teamGen.finalScoreVector, 0, experts); // BFS
			team2A = new Team(bestTeam1, neighborMap, twoHopMap, teamGen.finalScoreVector, 1, experts); // Pruned BFS
			team3A = new Team(bestTeam1, neighborMap, twoHopMap, teamGen.finalScoreVector, 2, experts); // Clique
			team4A = new Team(bestTeam1, neighborMap, twoHopMap, teamGen.finalScoreVector, 3, null); // BFS + Neighbor
			team5A = new Team(bestTeam1, neighborMap, twoHopMap, teamGen.finalScoreVector, 4, experts); // ManyPath
			team6A = new Team(bestTeam1, neighborMap, twoHopMap, teamGen.finalScoreVector, 5, experts); // Pruned ManyPath
			team7A = new Team(bestTeam1, neighborMap, twoHopMap, teamGen.finalScoreVector, 6, experts); // AK-Master Node
			team8A = new Team(bestTeam1, neighborMap, twoHopMap, teamGen.finalScoreVector, 7, experts); // NK-Master Node

			request.setAttribute("network1A", team1A.network);
			request.setAttribute("network2A", team2A.network);
			request.setAttribute("network3A", team3A.network);
			request.setAttribute("network4A", team4A.network);
			request.setAttribute("network5A", team5A.network);
			request.setAttribute("network6A", team6A.network);
			request.setAttribute("network7A", team7A.network);
			request.setAttribute("network8A", team8A.network);
			
			request.setAttribute("skill2expertId1A", team1A.skill2expertId);
			request.setAttribute("expertIds1A", team1A.expertIds);
			performance1 = calPerf(hMap, team1A.expertIds);
			request.setAttribute("performance1", performance1);
			request.setAttribute("skillName", team1A.skillName);
			
			if(kTeams > 1) {
			
				for (Integer id : team1A.expertIds) {
					usedIds.add(id);
				} 
			
				System.out.println("Running RWR 2....");
				
				bestTeam2 = teamGen.buildRWRTeam(project, skillExpertMap, M, beta, RWR_RunNum, usedIds);
				System.out.println(bestTeam2.toString());
				team1B = new Team(bestTeam2, neighborMap, twoHopMap, teamGen.finalScoreVector, 0, experts); // BFS
				team2B = new Team(bestTeam2, neighborMap, twoHopMap, teamGen.finalScoreVector, 1, experts); // Pruned BFS
				team3B = new Team(bestTeam2, neighborMap, twoHopMap, teamGen.finalScoreVector, 2, experts); // Clique
				team4B = new Team(bestTeam2, neighborMap, twoHopMap, teamGen.finalScoreVector, 3, null); // BFS + Neighbor
				team5B = new Team(bestTeam2, neighborMap, twoHopMap, teamGen.finalScoreVector, 4, experts); // ManyPath
				team6B = new Team(bestTeam2, neighborMap, twoHopMap, teamGen.finalScoreVector, 5, experts); // Pruned ManyPath
				team7B = new Team(bestTeam2, neighborMap, twoHopMap, teamGen.finalScoreVector, 6, experts); // AK-Master Node
				team8B = new Team(bestTeam2, neighborMap, twoHopMap, teamGen.finalScoreVector, 7, experts); // NK-Master Node

				request.setAttribute("network1B", team1B.network);
				request.setAttribute("network2B", team2B.network);
				request.setAttribute("network3B", team3B.network);
				request.setAttribute("network4B", team4B.network);
				request.setAttribute("network5B", team5B.network);
				request.setAttribute("network6B", team6B.network);
				request.setAttribute("network7B", team7B.network);
				request.setAttribute("network8B", team8B.network);
				
				request.setAttribute("skill2expertId1B", team1B.skill2expertId);
				request.setAttribute("expertIds1B", team1B.expertIds);
				performance2 = calPerf(hMap, team1B.expertIds);
				request.setAttribute("performance2", performance2);
				for (Integer id : team1B.expertIds) {
					usedIds.add(id);
				} 
			
				System.out.println("Running RWR 3....");
				
				bestTeam3 = teamGen.buildRWRTeam(project, skillExpertMap, M, beta, RWR_RunNum, usedIds);
				System.out.println(bestTeam3.toString());
				team1C = new Team(bestTeam3, neighborMap, twoHopMap, teamGen.finalScoreVector, 0, experts); // BFS
				team2C = new Team(bestTeam3, neighborMap, twoHopMap, teamGen.finalScoreVector, 1, experts); // Pruned BFS
				team3C = new Team(bestTeam3, neighborMap, twoHopMap, teamGen.finalScoreVector, 2, experts); // Clique
				team4C = new Team(bestTeam3, neighborMap, twoHopMap, teamGen.finalScoreVector, 3, null); // BFS + Neighbor
				team5C = new Team(bestTeam3, neighborMap, twoHopMap, teamGen.finalScoreVector, 4, experts); // ManyPath
				team6C = new Team(bestTeam3, neighborMap, twoHopMap, teamGen.finalScoreVector, 5, experts); // Pruned ManyPath
				team7C = new Team(bestTeam3, neighborMap, twoHopMap, teamGen.finalScoreVector, 6, experts); // AK-Master Node
				team8C = new Team(bestTeam3, neighborMap, twoHopMap, teamGen.finalScoreVector, 7, experts); // NK-Master Node

				request.setAttribute("network1C", team1C.network);
				request.setAttribute("network2C", team2C.network);
				request.setAttribute("network3C", team3C.network);
				request.setAttribute("network4C", team4C.network);
				request.setAttribute("network5C", team5C.network);
				request.setAttribute("network6C", team6C.network);
				request.setAttribute("network7C", team7C.network);
				request.setAttribute("network8C", team8C.network);
				
				request.setAttribute("skill2expertId1C", team1C.skill2expertId);
				request.setAttribute("expertIds1C", team1C.expertIds);
				performance3 = calPerf(hMap, team1C.expertIds);
				request.setAttribute("performance3", performance3);
				
				if (kTeams > 3) {
					
					for (Integer id : team1C.expertIds) {
						usedIds.add(id);
					} 
				
					System.out.println("Running RWR 4....");
					
					bestTeam4 = teamGen.buildRWRTeam(project, skillExpertMap, M, beta, RWR_RunNum, usedIds);
					System.out.println(bestTeam4.toString());
					team1D = new Team(bestTeam4, neighborMap, twoHopMap, teamGen.finalScoreVector, 0, experts); // BFS
					team2D = new Team(bestTeam4, neighborMap, twoHopMap, teamGen.finalScoreVector, 1, experts); // Pruned BFS
					team3D = new Team(bestTeam4, neighborMap, twoHopMap, teamGen.finalScoreVector, 2, experts); // Clique
					team4D = new Team(bestTeam4, neighborMap, twoHopMap, teamGen.finalScoreVector, 3, null); // BFS + Neighbor
					team5D = new Team(bestTeam4, neighborMap, twoHopMap, teamGen.finalScoreVector, 4, experts); // ManyPath
					team6D = new Team(bestTeam4, neighborMap, twoHopMap, teamGen.finalScoreVector, 5, experts); // Pruned ManyPath
					team7D = new Team(bestTeam4, neighborMap, twoHopMap, teamGen.finalScoreVector, 6, experts); // AK-Master Node
					team8D = new Team(bestTeam4, neighborMap, twoHopMap, teamGen.finalScoreVector, 7, experts); // NK-Master Node

					request.setAttribute("network1D", team1D.network);
					request.setAttribute("network2D", team2D.network);
					request.setAttribute("network3D", team3D.network);
					request.setAttribute("network4D", team4D.network);
					request.setAttribute("network5D", team5D.network);
					request.setAttribute("network6D", team6D.network);
					request.setAttribute("network7D", team7D.network);
					request.setAttribute("network8D", team8D.network);
					
					request.setAttribute("skill2expertId1D", team1D.skill2expertId);
					request.setAttribute("expertIds1D", team1D.expertIds);
					performance4 = calPerf(hMap, team1D.expertIds);
					request.setAttribute("performance4", performance4);
					
					for (Integer id : team1D.expertIds) {
						usedIds.add(id);
					} 
				
					System.out.println("Running RWR 5....");
					
					bestTeam5 = teamGen.buildRWRTeam(project, skillExpertMap, M, beta, RWR_RunNum, usedIds);
					System.out.println(bestTeam5.toString());
					team1E = new Team(bestTeam5, neighborMap, twoHopMap, teamGen.finalScoreVector, 0, experts); // BFS
					team2E = new Team(bestTeam5, neighborMap, twoHopMap, teamGen.finalScoreVector, 1, experts); // Pruned BFS
					team3E = new Team(bestTeam5, neighborMap, twoHopMap, teamGen.finalScoreVector, 2, experts); // Clique
					team4E = new Team(bestTeam5, neighborMap, twoHopMap, teamGen.finalScoreVector, 3, null); // BFS + Neighbor
					team5E = new Team(bestTeam5, neighborMap, twoHopMap, teamGen.finalScoreVector, 4, experts); // ManyPath
					team6E = new Team(bestTeam5, neighborMap, twoHopMap, teamGen.finalScoreVector, 5, experts); // Pruned ManyPath
					team7E = new Team(bestTeam5, neighborMap, twoHopMap, teamGen.finalScoreVector, 6, experts); // AK-Master Node
					team8E = new Team(bestTeam5, neighborMap, twoHopMap, teamGen.finalScoreVector, 7, experts); // NK-Master Node

					request.setAttribute("network1E", team1E.network);
					request.setAttribute("network2E", team2E.network);
					request.setAttribute("network3E", team3E.network);
					request.setAttribute("network4E", team4E.network);
					request.setAttribute("network5E", team5E.network);
					request.setAttribute("network6E", team6E.network);
					request.setAttribute("network7E", team7E.network);
					request.setAttribute("network8E", team8E.network);
					
					request.setAttribute("skill2expertId1E", team1E.skill2expertId);
					request.setAttribute("expertIds1E", team1E.expertIds);
					performance5 = calPerf(hMap, team1E.expertIds);
					request.setAttribute("performance5", performance5);
				}
			}
			break;
		case "f":
			
			teamGen = (RWR) getServletContext().getAttribute("rwr");
			
			System.out.println("Running Fast RWR 1....");
			bestTeam1 = teamGen.buildFASTRWRTeam(project, skillExpertMap, M, beta, RWR_RunNum);
			System.out.println(bestTeam1.toString());
			
			team1A = new Team(bestTeam1, neighborMap, twoHopMap, teamGen.finalScoreVector, 0, experts); // BFS
			team2A = new Team(bestTeam1, neighborMap, twoHopMap, teamGen.finalScoreVector, 1, experts); // Pruned BFS
			team3A = new Team(bestTeam1, neighborMap, twoHopMap, teamGen.finalScoreVector, 2, experts); // Clique
			team4A = new Team(bestTeam1, neighborMap, twoHopMap, teamGen.finalScoreVector, 3, null); // BFS + Neighbor
			team5A = new Team(bestTeam1, neighborMap, twoHopMap, teamGen.finalScoreVector, 4, experts); // ManyPath
			team6A = new Team(bestTeam1, neighborMap, twoHopMap, teamGen.finalScoreVector, 5, experts); // Pruned ManyPath
			team7A = new Team(bestTeam1, neighborMap, twoHopMap, teamGen.finalScoreVector, 6, experts); // AK-Master Node
			team8A = new Team(bestTeam1, neighborMap, twoHopMap, teamGen.finalScoreVector, 7, experts); // NK-Master Node
			
			request.setAttribute("network1A", team1A.network);
			request.setAttribute("network2A", team2A.network);
			request.setAttribute("network3A", team3A.network);
			request.setAttribute("network4A", team4A.network);
			request.setAttribute("network5A", team5A.network);
			request.setAttribute("network6A", team6A.network);
			request.setAttribute("network7A", team7A.network);
			request.setAttribute("network8A", team8A.network);
			
			request.setAttribute("skill2expertId1A", team1A.skill2expertId);
			request.setAttribute("expertIds1A", team1A.expertIds);
			performance1 = calPerf(hMap, team1A.expertIds);
			request.setAttribute("performance1", performance1);
			request.setAttribute("skillName", team1A.skillName);
			
			if(kTeams > 1) {
				
				for (Integer id : team1A.expertIds) {
					usedIds.add(id);
				}
				
				System.out.println("Running Fast RWR 2....");
				bestTeam2 = teamGen.buildFASTRWRTeam(project, skillExpertMap, M, beta, RWR_RunNum, usedIds);
				System.out.println(bestTeam2.toString());
				
				team1B = new Team(bestTeam2, neighborMap, twoHopMap, teamGen.finalScoreVector, 0, experts); // BFS
				team2B = new Team(bestTeam2, neighborMap, twoHopMap, teamGen.finalScoreVector, 1, experts); // Pruned BFS
				team3B = new Team(bestTeam2, neighborMap, twoHopMap, teamGen.finalScoreVector, 2, experts); // Clique
				team4B = new Team(bestTeam2, neighborMap, twoHopMap, teamGen.finalScoreVector, 3, null); // BFS + Neighbor
				team5B = new Team(bestTeam2, neighborMap, twoHopMap, teamGen.finalScoreVector, 4, experts); // ManyPath
				team6B = new Team(bestTeam2, neighborMap, twoHopMap, teamGen.finalScoreVector, 5, experts); // Pruned ManyPath
				team7B = new Team(bestTeam2, neighborMap, twoHopMap, teamGen.finalScoreVector, 6, experts); // AK-Master Node
				team8B = new Team(bestTeam2, neighborMap, twoHopMap, teamGen.finalScoreVector, 7, experts); // NK-Master Node
				
				request.setAttribute("network1B", team1B.network);
				request.setAttribute("network2B", team2B.network);
				request.setAttribute("network3B", team3B.network);
				request.setAttribute("network4B", team4B.network);
				request.setAttribute("network5B", team5B.network);
				request.setAttribute("network6B", team6B.network);
				request.setAttribute("network7B", team7B.network);
				request.setAttribute("network8B", team8B.network);
				
				request.setAttribute("skill2expertId1B", team1B.skill2expertId);
				request.setAttribute("expertIds1B", team1B.expertIds);
				performance2 = calPerf(hMap, team1B.expertIds);
				request.setAttribute("performance2", performance2);
				
				for (Integer id : team1B.expertIds) {
					usedIds.add(id);
				}
				
				System.out.println("Running Fast RWR 3....");
				bestTeam3 = teamGen.buildFASTRWRTeam(project, skillExpertMap, M, beta, RWR_RunNum, usedIds);
				System.out.println(bestTeam3.toString());
				
				team1C = new Team(bestTeam3, neighborMap, twoHopMap, teamGen.finalScoreVector, 0, experts); // BFS
				team2C = new Team(bestTeam3, neighborMap, twoHopMap, teamGen.finalScoreVector, 1, experts); // Pruned BFS
				team3C = new Team(bestTeam3, neighborMap, twoHopMap, teamGen.finalScoreVector, 2, experts); // Clique
				team4C = new Team(bestTeam3, neighborMap, twoHopMap, teamGen.finalScoreVector, 3, null); // BFS + Neighbor
				team5C = new Team(bestTeam3, neighborMap, twoHopMap, teamGen.finalScoreVector, 4, experts); // ManyPath
				team6C = new Team(bestTeam3, neighborMap, twoHopMap, teamGen.finalScoreVector, 5, experts); // Pruned ManyPath
				team7C = new Team(bestTeam3, neighborMap, twoHopMap, teamGen.finalScoreVector, 6, experts); // AK-Master Node
				team8C = new Team(bestTeam3, neighborMap, twoHopMap, teamGen.finalScoreVector, 7, experts); // NK-Master Node
				
				request.setAttribute("network1C", team1C.network);
				request.setAttribute("network2C", team2C.network);
				request.setAttribute("network3C", team3C.network);
				request.setAttribute("network4C", team4C.network);
				request.setAttribute("network5C", team5C.network);
				request.setAttribute("network6C", team6C.network);
				request.setAttribute("network7C", team7C.network);
				request.setAttribute("network8C", team8C.network);
				
				request.setAttribute("skill2expertId1C", team1C.skill2expertId);
				request.setAttribute("expertIds1C", team1C.expertIds);
				performance3 = calPerf(hMap, team1C.expertIds);
				request.setAttribute("performance3", performance3);
				
				if (kTeams > 3) {
					
					for (Integer id : team1C.expertIds) {
						usedIds.add(id);
					}
					
					System.out.println("Running Fast RWR 4....");
					bestTeam4 = teamGen.buildFASTRWRTeam(project, skillExpertMap, M, beta, RWR_RunNum, usedIds);
					System.out.println(bestTeam4.toString());
					
					team1D = new Team(bestTeam4, neighborMap, twoHopMap, teamGen.finalScoreVector, 0, experts); // BFS
					team2D = new Team(bestTeam4, neighborMap, twoHopMap, teamGen.finalScoreVector, 1, experts); // Pruned BFS
					team3D = new Team(bestTeam4, neighborMap, twoHopMap, teamGen.finalScoreVector, 2, experts); // Clique
					team4D = new Team(bestTeam4, neighborMap, twoHopMap, teamGen.finalScoreVector, 3, null); // BFS + Neighbor
					team5D = new Team(bestTeam4, neighborMap, twoHopMap, teamGen.finalScoreVector, 4, experts); // ManyPath
					team6D = new Team(bestTeam4, neighborMap, twoHopMap, teamGen.finalScoreVector, 5, experts); // Pruned ManyPath
					team7D = new Team(bestTeam4, neighborMap, twoHopMap, teamGen.finalScoreVector, 6, experts); // AK-Master Node
					team8D = new Team(bestTeam4, neighborMap, twoHopMap, teamGen.finalScoreVector, 7, experts); // NK-Master Node
					
					request.setAttribute("network1D", team1D.network);
					request.setAttribute("network2D", team2D.network);
					request.setAttribute("network3D", team3D.network);
					request.setAttribute("network4D", team4D.network);
					request.setAttribute("network5D", team5D.network);
					request.setAttribute("network6D", team6D.network);
					request.setAttribute("network7D", team7D.network);
					request.setAttribute("network8D", team8D.network);
					
					request.setAttribute("skill2expertId1D", team1D.skill2expertId);
					request.setAttribute("expertIds1D", team1D.expertIds);
					performance4 = calPerf(hMap, team1D.expertIds);
					request.setAttribute("performance4", performance4);
					
					for (Integer id : team1D.expertIds) {
						usedIds.add(id);
					}
					
					System.out.println("Running Fast RWR 5....");
					bestTeam5 = teamGen.buildFASTRWRTeam(project, skillExpertMap, M, beta, RWR_RunNum, usedIds);
					System.out.println(bestTeam5.toString());
					
					team1E = new Team(bestTeam5, neighborMap, twoHopMap, teamGen.finalScoreVector, 0, experts); // BFS
					team2E = new Team(bestTeam5, neighborMap, twoHopMap, teamGen.finalScoreVector, 1, experts); // Pruned BFS
					team3E = new Team(bestTeam5, neighborMap, twoHopMap, teamGen.finalScoreVector, 2, experts); // Clique
					team4E = new Team(bestTeam5, neighborMap, twoHopMap, teamGen.finalScoreVector, 3, null); // BFS + Neighbor
					team5E = new Team(bestTeam5, neighborMap, twoHopMap, teamGen.finalScoreVector, 4, experts); // ManyPath
					team6E = new Team(bestTeam5, neighborMap, twoHopMap, teamGen.finalScoreVector, 5, experts); // Pruned ManyPath
					team7E = new Team(bestTeam5, neighborMap, twoHopMap, teamGen.finalScoreVector, 6, experts); // AK-Master Node
					team8E = new Team(bestTeam5, neighborMap, twoHopMap, teamGen.finalScoreVector, 7, experts); // NK-Master Node
					
					request.setAttribute("network1E", team1E.network);
					request.setAttribute("network2E", team2E.network);
					request.setAttribute("network3E", team3E.network);
					request.setAttribute("network4E", team4E.network);
					request.setAttribute("network5E", team5E.network);
					request.setAttribute("network6E", team6E.network);
					request.setAttribute("network7E", team7E.network);
					request.setAttribute("network8E", team8E.network);
					
					request.setAttribute("skill2expertId1E", team1E.skill2expertId);
					request.setAttribute("expertIds1E", team1E.expertIds);
					performance5 = calPerf(hMap, team1E.expertIds);
					request.setAttribute("performance5", performance5);
				}
			}
			break;
		case "e":
			
			GraphEmbedding gem = new GraphEmbedding();
			if(net.equals("net1")) {
				gem.loadEmbedding("dblp", 20, 100);			
			} else if (net.equals("net2")) {
				gem.loadEmbedding("ppt", 20, 100);
			}
			
			teamGen = (SmallestEmbedding) getServletContext().getAttribute("emb");
			
			System.out.println("Running Embedding 1....");
			bestTeam1 = teamGen.buildEMBTeam(project, skillExpertMap, gem);
			System.out.println(bestTeam1.toString());
			
			team1A = new Team(bestTeam1, neighborMap, twoHopMap, null, 0, experts); // BFS
			team2A = new Team(bestTeam1, neighborMap, twoHopMap, null, 2, experts); // Clique 
			team3A = new Team(bestTeam1, neighborMap, twoHopMap, null, 3, experts); // Clique + Neighbour
			request.setAttribute("network1A", team1A.network);
			request.setAttribute("network2A", team2A.network);
			request.setAttribute("network3A", team3A.network);
			
			request.setAttribute("skill2expertId1A", team1A.skill2expertId);
			request.setAttribute("expertIds1A", team1A.expertIds);
			performance1 = calPerf(hMap, team1A.expertIds);
			request.setAttribute("performance1", performance1);
			request.setAttribute("skillName", team1A.skillName);
			
			if(kTeams > 1) {
				
				for (Integer id : team1A.expertIds) {
					usedIds.add(id);
				}
				
				System.out.println("Running Embedding 2....");
				bestTeam2 = teamGen.buildEMBTeam(project, skillExpertMap, gem, usedIds);
				System.out.println(bestTeam2.toString());
				team1B = new Team(bestTeam2, neighborMap, twoHopMap, null, 0, experts); // BFS
				team2B = new Team(bestTeam2, neighborMap, twoHopMap, null, 2, experts); // Clique 
				team3B = new Team(bestTeam2, neighborMap, twoHopMap, null, 3, experts); // Clique + Neighbour
				request.setAttribute("network1B", team1B.network);
				request.setAttribute("network2B", team2B.network);
				request.setAttribute("network3B", team3B.network);
				
				request.setAttribute("skill2expertId1B", team1B.skill2expertId);
				request.setAttribute("expertIds1B", team1B.expertIds);
				performance2 = calPerf(hMap, team1B.expertIds);
				request.setAttribute("performance2", performance2);
				
				for (Integer id : team1B.expertIds) {
					usedIds.add(id);
				}
				
				System.out.println("Running Embedding 3....");
				bestTeam3 = teamGen.buildEMBTeam(project, skillExpertMap, gem, usedIds);
				System.out.println(bestTeam2.toString());
				team1C = new Team(bestTeam3, neighborMap, twoHopMap, null, 0, experts); // BFS
				team2C = new Team(bestTeam3, neighborMap, twoHopMap, null, 2, experts); // Clique 
				team3C = new Team(bestTeam3, neighborMap, twoHopMap, null, 3, experts); // Clique + Neighbour
				request.setAttribute("network1C", team1C.network);
				request.setAttribute("network2C", team2C.network);
				request.setAttribute("network3C", team3C.network);
				
				request.setAttribute("skill2expertId1C", team1C.skill2expertId);
				request.setAttribute("expertIds1C", team1C.expertIds);
				performance3 = calPerf(hMap, team1C.expertIds);
				request.setAttribute("performance3", performance3);
				
				
				if (kTeams > 3) {
					for (Integer id : team1C.expertIds) {
						usedIds.add(id);
					}
					
					System.out.println("Running Embedding 4....");
					bestTeam4 = teamGen.buildEMBTeam(project, skillExpertMap, gem, usedIds);
					System.out.println(bestTeam4.toString());
					team1D = new Team(bestTeam4, neighborMap, twoHopMap, null, 0, experts); // BFS
					team2D = new Team(bestTeam4, neighborMap, twoHopMap, null, 2, experts); // Clique 
					team3D = new Team(bestTeam4, neighborMap, twoHopMap, null, 3, experts); // Clique + Neighbour
					request.setAttribute("network1D", team1D.network);
					request.setAttribute("network2D", team2D.network);
					request.setAttribute("network3D", team3D.network);
					
					request.setAttribute("skill2expertId1D", team1D.skill2expertId);
					request.setAttribute("expertIds1D", team1D.expertIds);
					performance4 = calPerf(hMap, team1D.expertIds);
					request.setAttribute("performance4", performance4);
					
					for (Integer id : team1D.expertIds) {
						usedIds.add(id);
					}
					
					System.out.println("Running Embedding 5....");
					bestTeam5 = teamGen.buildEMBTeam(project, skillExpertMap, gem, usedIds);
					System.out.println(bestTeam5.toString());
					team1E = new Team(bestTeam5, neighborMap, twoHopMap, null, 0, experts); // BFS
					team2E = new Team(bestTeam5, neighborMap, twoHopMap, null, 2, experts); // Clique 
					team3E = new Team(bestTeam5, neighborMap, twoHopMap, null, 3, experts); // Clique + Neighbour
					request.setAttribute("network1E", team1E.network);
					request.setAttribute("network2E", team2E.network);
					request.setAttribute("network3E", team3E.network);
					
					request.setAttribute("skill2expertId1E", team1E.skill2expertId);
					request.setAttribute("expertIds1E", team1E.expertIds);
					performance5 = calPerf(hMap, team1E.expertIds);
					request.setAttribute("performance5", performance5);
				
				}
			}
			break;
		case "h":
			//until i have hybrid up and running, default to shortest path
			teamGen = (ShortestPath) getServletContext().getAttribute("sp");
			System.out.println("Running Hybrid 1....");
			bestTeam1 = teamGen.buildShortestPathTeam(project, skillExpertMap, twoHopMap);
			System.out.println(bestTeam1.toString());
			team1A = new Team(bestTeam1, neighborMap, twoHopMap, null, 0, experts); // BFS
			team2A = new Team(bestTeam1, neighborMap, twoHopMap, null, 2, experts); // Clique 
			team3A = new Team(bestTeam1, neighborMap, twoHopMap, null, 3, experts); // Clique + Neighbour
			request.setAttribute("network1A", team1A.network);
			request.setAttribute("network2A", team2A.network);
			request.setAttribute("network3A", team3A.network);
			
			request.setAttribute("skill2expertId1A", team1A.skill2expertId);
			request.setAttribute("expertIds1A", team1A.expertIds);
			performance1 = calPerf(hMap, team1A.expertIds);
			request.setAttribute("performance1", performance1);
			request.setAttribute("skillName", team1A.skillName);
			
			if(kTeams > 1) {
				
				for (Integer id : team1A.expertIds) {
					usedIds.add(id);
				}
				
				System.out.println("Running Hybrid 2....");
				bestTeam2 = teamGen.buildShortestPathTeam(project, skillExpertMap, twoHopMap, usedIds);
				System.out.println(bestTeam2.toString());
				team1B = new Team(bestTeam2, neighborMap, twoHopMap, null, 0, experts); // BFS
				team2B = new Team(bestTeam2, neighborMap, twoHopMap, null, 2, experts); // Clique 
				team3B = new Team(bestTeam2, neighborMap, twoHopMap, null, 3, experts); // Clique + Neighbour
				request.setAttribute("network1B", team1B.network);
				request.setAttribute("network2B", team2B.network);
				request.setAttribute("network3B", team3B.network);
				
				request.setAttribute("skill2expertId1B", team1B.skill2expertId);
				request.setAttribute("expertIds1B", team1B.expertIds);
				performance2 = calPerf(hMap, team1B.expertIds);
				request.setAttribute("performance2", performance2);
				
				for (Integer id : team1B.expertIds) {
					usedIds.add(id);
				}
				
				System.out.println("Running Hybrid 3....");
				bestTeam3 = teamGen.buildShortestPathTeam(project, skillExpertMap, twoHopMap, usedIds);
				System.out.println(bestTeam2.toString());
				team1C = new Team(bestTeam3, neighborMap, twoHopMap, null, 0, experts); // BFS
				team2C = new Team(bestTeam3, neighborMap, twoHopMap, null, 2, experts); // Clique 
				team3C = new Team(bestTeam3, neighborMap, twoHopMap, null, 3, experts); // Clique + Neighbour
				request.setAttribute("network1C", team1C.network);
				request.setAttribute("network2C", team2C.network);
				request.setAttribute("network3C", team3C.network);
				
				request.setAttribute("skill2expertId1C", team1C.skill2expertId);
				request.setAttribute("expertIds1C", team1C.expertIds);
				performance3 = calPerf(hMap, team1C.expertIds);
				request.setAttribute("performance3", performance3);
				
				
				if (kTeams > 3) {
					for (Integer id : team1C.expertIds) {
						usedIds.add(id);
					}
					
					System.out.println("Running Hybrid 4....");
					bestTeam4 = teamGen.buildShortestPathTeam(project, skillExpertMap, twoHopMap, usedIds);
					System.out.println(bestTeam4.toString());
					team1D = new Team(bestTeam4, neighborMap, twoHopMap, null, 0, experts); // BFS
					team2D = new Team(bestTeam4, neighborMap, twoHopMap, null, 2, experts); // Clique 
					team3D = new Team(bestTeam4, neighborMap, twoHopMap, null, 3, experts); // Clique + Neighbour
					request.setAttribute("network1D", team1D.network);
					request.setAttribute("network2D", team2D.network);
					request.setAttribute("network3D", team3D.network);
					
					request.setAttribute("skill2expertId1D", team1D.skill2expertId);
					request.setAttribute("expertIds1D", team1D.expertIds);
					performance4 = calPerf(hMap, team1D.expertIds);
					request.setAttribute("performance4", performance4);
					
					for (Integer id : team1D.expertIds) {
						usedIds.add(id);
					}
					
					System.out.println("Running Hybrid 5....");
					bestTeam5 = teamGen.buildShortestPathTeam(project, skillExpertMap, twoHopMap, usedIds);
					System.out.println(bestTeam5.toString());
					team1E = new Team(bestTeam5, neighborMap, twoHopMap, null, 0, experts); // BFS
					team2E = new Team(bestTeam5, neighborMap, twoHopMap, null, 2, experts); // Clique 
					team3E = new Team(bestTeam5, neighborMap, twoHopMap, null, 3, experts); // Clique + Neighbour
					request.setAttribute("network1E", team1E.network);
					request.setAttribute("network2E", team2E.network);
					request.setAttribute("network3E", team3E.network);
					
					request.setAttribute("skill2expertId1E", team1E.skill2expertId);
					request.setAttribute("expertIds1E", team1E.expertIds);
					performance5 = calPerf(hMap, team1E.expertIds);
					request.setAttribute("performance5", performance5);
				}
			}
			break;
		
		}
		
		
		//System.out.println(methodSelection);


		
		// get essential variables from context

		

		// run the model


		request.getRequestDispatcher("/WEB-INF/jsp/result.jsp").forward(request, response);
	}

	private String[] calPerf(Map<Integer, Integer[]> hMap, int[] expertIds) {
		String[] r = new String[3];
		int r0 = 0, r1 = 0, r2 = 0;
		for (int i = 0; i < expertIds.length; i++) {
			Integer[] l = hMap.get(expertIds[i]);
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
		if (eligibleSkillsSet.contains(skill.toLowerCase()))
			return true;
		else
			return false;
	}

}
