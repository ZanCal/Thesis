
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import cern.colt.matrix.DoubleMatrix2D;

/**
 * Application Lifecycle Listener implementation class MyListener
 *
 */
@WebListener
public class MyListener implements ServletContextListener {
    
    public MyListener() {

    }


    public void contextDestroyed(ServletContextEvent arg0)  { 
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  {
    		
    	ServletContext  sc = arg0.getServletContext();
		
		// Initializing the hyper parameters

		String DBLPLocation = "/WEB-INF/datasets/";
		
        int minCardinality = 2; //25
        int maxCardinality = 200; //50
        double beta = 0.8;
        
		//Build essential Data Structures
  
        RWR rwr = new RWR();  
        
        ShortestPath sp = new ShortestPath();
                
        Map<String, Set<Integer>> skillExpertMap =
                rwr.buildEligibleSkillExpertMap(minCardinality, maxCardinality, sc.getRealPath(DBLPLocation+"SC_invertedTermCount.txt"), sc.getRealPath(DBLPLocation+"SC_invertedTermMap.txt"));

        DoubleMatrix2D M = rwr.buildMatrixM(beta, sc.getRealPath(DBLPLocation+"SC_G1.txt"));

        Map<Integer, TwoHop[]> twoHopMap = sp.readTwoHopIndexArray(sc.getRealPath(DBLPLocation+"2HopCoverDist.txt"));
        
        // read the experts file
        ExpertGraph g = new ExpertGraph();
        String[] experts = g.createExpertMap(sc.getRealPath(DBLPLocation+"SC_authorNameId.txt"));
        Map<Integer,Set<Integer>>  neighborMap = g.createNeighborMap(M);
        
        Map<Integer,Integer[]>  hMap = g.createHindexMap(sc.getRealPath(DBLPLocation+"SC_authorHIndex.txt"));

        sc.setAttribute("hMap", hMap);
        sc.setAttribute("neighborMap", neighborMap);
        sc.setAttribute("experts", experts);
        sc.setAttribute("rwr", rwr);         
        sc.setAttribute("sp", sp); 
        sc.setAttribute("skillExpertMap", skillExpertMap);
        sc.setAttribute("M", M);
        sc.setAttribute("twoHopMap", twoHopMap); 
        sc.setAttribute("beta", beta);
    }
   
}
