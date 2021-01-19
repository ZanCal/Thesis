import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GraphEmbedding {
	
    Map<Integer,double[]> nodeEmbeddingMap = new HashMap<Integer,double[]>();
    Map<String, Set<Integer>> termNodeMap = new HashMap<String,Set<Integer>>();

	String path = "./datasets-1/";
	
  
	
	public static void main(String[] args) {
		GraphEmbedding gem = new GraphEmbedding();
		//gem.loadEmbedding("ppt",10,100);
		//gem.loadTermNode("ppt");
		
		double [] d =gem.embVec(4);
		
		for (int i=0;i < d.length ; i++)
			System.out.println(d[i]+"");

	}
	
	public double [] embVec(int key) {
		return nodeEmbeddingMap.get(key); 
	}

	public void loadTermNode(String ds) {
		
		String invFile = path +ds+"/"+ds+"-invertedTermMap.txt";
		System.out.println("Loading...."+ invFile);
		
		String sCurrentLine;
        try{
            BufferedReader br = new BufferedReader(new FileReader(invFile));
            
            while ((sCurrentLine = br.readLine()) != null) {

                String[] ss = sCurrentLine.split("\t");

                String term = ss[0];
                
                //System.out.println(term);
                

                Set<Integer> nodes = new HashSet<Integer>(); 
                
                for(int i=1; i< ss.length; i++)	{
                	nodes.add(Integer.parseInt(ss[i]));
                	//System.out.println(nodes.toString());
                }
                
                termNodeMap.put(term,nodes);     
            }
            System.out.println(termNodeMap.size()+"");
            br.close();

        }
        catch(Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
	 	System.out.println("DONE");	
	}
		

	public void loadEmbedding(String ds,int r, int d){ 
		//String embFile = path +ds+"/"+ds+"-10-100.emb";
		String embFile = path +ds+"/emb/"+ds+"-"+r+"-"+d+".emb";
		System.out.print("Loading...."+ embFile);
		
		String sCurrentLine;
        try{
            BufferedReader br = new BufferedReader(new FileReader(embFile));

            br.readLine();
            
            while ((sCurrentLine = br.readLine()) != null) {

                String[] ss = sCurrentLine.split(" ");

                int nodeId = Integer.parseInt(ss[0]);

                double[] embVec = new double[ss.length-1]; 
                
                for(int i=1; i< ss.length; i++){
                	embVec[i-1] = Double.parseDouble(ss[i])  ;         	
                	//System.out.println(embVec[i-1]+"");
                }
                
                nodeEmbeddingMap.put(nodeId,embVec);                
            }
            //System.out.println(nodeEmbeddingMap.size()+"");
            br.close();

        }
        catch(Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
	 	System.out.println(" DONE");
		
	}
}
