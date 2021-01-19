import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import cern.colt.list.DoubleArrayList;
import cern.colt.list.IntArrayList;
import cern.colt.matrix.DoubleMatrix2D;

public class ExpertGraph {
	public String [] createExpertMap(String location){
		Scanner s;
		List<String> lines;
		String[] experts = null;			
		try {
			s = new Scanner(new File(location));
			lines = new ArrayList<String>();
			while (s.hasNextLine()) {
				String t = s.nextLine();
				t = t.split("\t")[1];
				lines.add(t);
			}
			experts = lines.toArray(new String[0]);	
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return experts;
	}
	
	public Map<Integer,Set<Integer>>  createNeighborMap(DoubleMatrix2D m){
		
        Map<Integer,Set<Integer>> graphMap = new HashMap<Integer,Set<Integer>>();
 
        IntArrayList r = new IntArrayList() ;
		IntArrayList c = new IntArrayList();
		DoubleArrayList v = new DoubleArrayList();
		m.getNonZeros(r, c, v);
		
		for(int i=0; i < r.size();i++) // all pairs
		{
			int x = r.get(i); // x -> y
			int y = c.get(i);
			
			if(graphMap.get(x) == null){
				Set<Integer> neighbors = new HashSet<Integer>();
				neighbors.add(y);
                graphMap.put(x, neighbors);  // first neighbor
			}else{
				Set<Integer> neighbors = graphMap.get(x); 
				neighbors.add(y);
			}			
		}
		
		return graphMap;
		 
	 }

	public Map<Integer,Integer[]> createHindexMap(String realPath) {
		Scanner s;
		Map<Integer,Integer[]>  hIndex = new HashMap<Integer,Integer[]>  () ;			
		
		try {
			s = new Scanner(new File(realPath));
			while (s.hasNextLine()) {
				String t = s.nextLine();
				
				Integer[] l = new Integer[3];
				String [] x = t.split("\t");
				
				l[0] = Integer.parseInt(x[1]);
				l[1] = Integer.parseInt(x[2]);
				l[2] = Integer.parseInt(x[3]);
				
				hIndex.put(Integer.parseInt(x[0]), l);

			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return hIndex;
	}


}
