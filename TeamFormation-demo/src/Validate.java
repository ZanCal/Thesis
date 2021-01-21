

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class Validate
 */
@WebServlet("/validate")
public class Validate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Validate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	    
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
		
		Map<String, Set<Integer>> skillExpertMap = (Map<String, Set<Integer>>) getServletContext().getAttribute("skillExpertMap");
		Set<String> eligibleSkillsSet = skillExpertMap.keySet();
		
		//String text = "Error";
		
		StringBuilder res=new StringBuilder("");  

		
		// validation code 
	    int c = 0;
	    Set<String> sSet = new HashSet<String>();
	    
		if (prj.equals("prj2")) // // from sample => OK
			res.append("OK"); 
		else { 		
			// n < 2 => 010100..1
			for(int i=0;i<Integer.parseInt(pn);i++)
		    	if(skills[i]==null || skills[i].equals(""))
		    		res.append("0");
		    	else if(isSkill(skills[i], eligibleSkillsSet)){  
		    		res.append("1");
		    		if(!sSet.contains(skills[i].toLowerCase())){
		    			sSet.add(skills[i].toLowerCase());
		    			c++;
		    		}
		    	}
		    	else
		    		res.append("0");
		}
				
	    response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
	    
	    if(c < 2)
	    	response.getWriter().write(res.toString());       // Write response body.
	    else
	    	response.getWriter().write("OK");
	}

	private boolean isSkill(String skill, Set<String> eligibleSkillsSet) {
		if(eligibleSkillsSet.contains(skill.toLowerCase()))
			return true;
		else
			return false;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
