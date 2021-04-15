import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cern.colt.matrix.DoubleMatrix2D;

import java.util.Set;
import java.util.Stack;

public class Team {
	public String [] skillName;        	// skill name [i]
	public String [] skill2expertId;	// skill to expert id [i]  
	public int expertIds [];	// expert id
	public String network; // network output
	
    	
	public Team(Map<String, Integer> bestTeam, Map<Integer, Set<Integer>> neighborMap,Map<Integer, TwoHop[]> twoHopMap, DoubleMatrix2D finalScoreVector, int prtype, String[] experts) {
        
		Set<Integer> experId = new HashSet<Integer>(); 
		int i = 0;
		
			
		skillName = new String[bestTeam.size()];
		skill2expertId = new String[bestTeam.size()];

		for (Entry<String, Integer> pair : bestTeam.entrySet()){
            //System.out.println(pair.getKey()+" "+pair.getValue());
            
            skillName[i] = pair.getKey();
            skill2expertId[i] = Integer.toString(pair.getValue()); 
            experId.add(pair.getValue());
            i++;
        }
		
		expertIds = new int[experId.size()];

		int j = 0;
		Iterator<Integer> iter = experId.iterator();
		while (iter.hasNext()) {
			expertIds[j++] = iter.next();
		}
		
		
		
		Set<Integer> nodes=null;
		
		boolean good;
		switch(prtype){
			case 0:
				nodes= getlocalNodes(neighborMap);		//BFS
				good = true;
				for(i = 1; i < expertIds.length; i ++) {
					if(!inSameGraph(expertIds[0],expertIds[i],nodes,neighborMap)) {
						good = false;
					}
				}
				//System.out.println("BFS valid visualization " + good);
				System.out.println("BFS node length " + nodes.size());
				break;
			case 1:
				nodes= getlocalNodes(neighborMap);	// BFS
				nodes = getTopNeighbors(nodes,finalScoreVector, 10); //BFS + top rank
				good = true;
				for(i = 1; i < expertIds.length; i ++) {
					if(!inSameGraph(expertIds[0],expertIds[i],nodes,neighborMap)) {
						good = false;
					}
				}
				//System.out.println("Pruned BFS valid visualization " + good);
				//System.out.println("Pruned BFS node length " + nodes.size());
				break;
			case 2:
				nodes = CreatSpGraph(twoHopMap); // Clique
				good = true;
				for(i = 1; i < expertIds.length; i ++) {
					if(!inSameGraph(expertIds[0],expertIds[i],nodes,neighborMap)) {
						good = false;
					}
				}
				//System.out.println("Clique valid visualization " + good);
				System.out.println("Clique node length " + nodes.size());
				break;
			case 3: 
				nodes = CreatExtSpGraph(twoHopMap,neighborMap); // Clique + Neighbor
				good = true;
				for(i = 1; i < expertIds.length; i ++) {
					if(!inSameGraph(expertIds[0],expertIds[i],nodes,neighborMap)) {
						good = false;
					}
				}
				//System.out.println("Clique + Neighbour valid visualization " + good);
				//System.out.println("Clique + Neighbour node length " + nodes.size());
				break;
			case 4://ManyPath
				//here we go, moment of truth 
				//nodes = getAllPaths(neighborMap, twoHopMap, finalScoreVector);
				
				nodes = CreateWSPGraph(twoHopMap, finalScoreVector);
				good = true;
				for(i = 1; i < expertIds.length; i ++) {
					if(!inSameGraph(expertIds[0],expertIds[i],nodes,neighborMap)) {
						good = false;
					}
				}
				System.out.println("ManyPath valid visualization " + good);
				System.out.println("ManyPath node length " + nodes.size());
				break;
			case 5://Pruned ManyPath
				nodes = getBestPaths(neighborMap, twoHopMap, finalScoreVector);
				good = true;
				for(i = 1; i < expertIds.length; i ++) {
					if(!inSameGraph(expertIds[0],expertIds[i],nodes,neighborMap)) {
						good = false;
					}
				}
				//System.out.println("Pruned ManyPath valid visualization " + good);
				//System.out.println("Pruned ManyPath node length " + nodes.size());
				break;
			case 6://AK-Master Node
				nodes = getTreeStylePaths(neighborMap, twoHopMap, finalScoreVector);
				good = true;
				for(i = 1; i < expertIds.length; i ++) {
					if(!inSameGraph(expertIds[0],expertIds[i],nodes,neighborMap)) {
						good = false;
					}
				}
				//System.out.println("AK-Master Node valid visualization " + good);
				//System.out.println("AK-Master node length " + nodes.size());
				break;
			case 7:// Improved Pruned BFS
				
				int nodeCount = 10;
				nodes = getlocalNodes(neighborMap);
				nodes = getTopNeighbors(nodes,finalScoreVector, nodeCount);
				for( i = 1; i < expertIds.length; i ++) {
					while(inSameGraph(expertIds[0], expertIds[i], nodes, neighborMap) == false) {
						nodeCount += 1;
						nodes = getlocalNodes(neighborMap);
						nodes = getTopNeighbors(nodes,finalScoreVector, nodeCount);
					}
				}
				//System.out.println("Improved Pruned BFS node length " + nodeCount);
				break;
		}
		
		
		if(finalScoreVector == null) {
		
			if(experts==null)
				setNetwork1(nodes, expertIds,neighborMap); // network by id
			else
				setNetwork2(nodes, expertIds,neighborMap,experts);
		
		} else {
			if(experts==null)
				setNetwork1(nodes, expertIds,neighborMap, finalScoreVector); // network by id
			else
				setNetwork2(nodes, expertIds,neighborMap,experts, finalScoreVector);
			
		}
		//System.out.println("Net:"+network);
	}
	
	private Set<Integer> getTopNeighbors(Set<Integer> nodes, DoubleMatrix2D finalScoreVector,int k) {
		double [] x = new double[nodes.size()]; 
		int [] idx = new int[nodes.size()]; 
		int i = 0;
		for(Integer n:nodes){
			x[i] = finalScoreVector.get(n, 0);
			idx[i] = n;
			i++;
		}
		doSelectionSort(x,idx);
		
		Set<Integer> result = new HashSet<Integer>();
		
		for(int j = 0 ; j < expertIds.length; j++) {
			result.add(expertIds[j]);			
		}
		
		for (int j = 0; j < k && j < nodes.size() ;j++) {
			result.add(idx[j]);			
		}
			
		return result;
	}
	
	public  void doSelectionSort(double [] arr, int [] idx){        
	//why does this function exist?
	//how poorly this code is documented 
	//is making me want to not exist 
		for (int i = 0; i < arr.length - 1; i++){
            int index = i;
            for (int j = i + 1; j < arr.length; j++) {            	
                if (arr[j] > arr[index]) {
                	index = j;                	
                }
            }
            
            double t = arr[index];       
            arr[index] = arr[i];            
            arr[i] = t; 
            
            int tt = idx[index];
            idx[index] = idx[i];
            idx[i] = tt;         
         }
    }
	
	private Set<Integer> CreatExtSpGraph(Map<Integer, TwoHop[]> twoHopMap, Map<Integer, Set<Integer>> neighborMap) {
		
		Set<Integer> temp = CreatSpGraph(twoHopMap);
		Set<Integer> nodes = new HashSet<Integer>();
		
		for (Integer x : temp) {
			nodes.add(x);
			Set<Integer> neigh = neighborMap.get(x);
			for (Integer n : neigh)
				nodes.add(n);
			}	
		return nodes;
	}

	private Set<Integer> CreatSpGraph(Map<Integer, TwoHop[]> twoHopMap) {
		TestOne util = new TestOne();
		Set<Integer> nodes = new HashSet<Integer>();
		List<Integer> path;
		for(int i=0 ; i<expertIds.length; i++)
			for(int j= i; j <expertIds.length;j++){
				path = util.getFullPathTwoHopArray(twoHopMap, expertIds[i], expertIds[j], null, -1, -1);
				//System.out.println(expertIds[i]);
				//System.out.println(expertIds[j]);
				//System.out.println(path);
				//System.out.println("new path clique");
	            for(Integer id:path) {
	            	nodes.add(id);	            	
	            }
		}
		
		return nodes;
	}
	
	
	private Set<Integer> CreateWSPGraph(Map<Integer, TwoHop[]> twoHopMap, DoubleMatrix2D finalScoreVector ){
		TestOne util = new TestOne();
		Set<Integer> nodes = new HashSet<Integer>();
		List<Integer> path;
		for(int i = 0; i < expertIds.length; i ++) {
			for(int j = 0; j < expertIds.length; j++) {
				path = util.weightedVariant(twoHopMap, expertIds[i], expertIds[j], null, finalScoreVector, -1, -1 );
				
				for(Integer id:path) {
					nodes.add(id);
				}
			}
			
		}
		
		
		return nodes;
	}

	@SuppressWarnings("unused") //if this is unused, why is it here?
	private Set<Integer> prunetheNodes(Set<Integer> nodes, int [] tops) {
		Set<Integer> pruneNodes = new HashSet<Integer>();
		
		for(int i=0;i<expertIds.length;i++)
			pruneNodes.add(expertIds[i]);
		
		for(int i=0; i<tops.length;i++)
			if(nodes.contains(tops[i]))
				pruneNodes.add(tops[i]);
		
		return pruneNodes;
	}
	
	public int[] gettopNodes(DoubleMatrix2D finalScoreVector, int K){
		
		int [] topScore = new int[K];
		
		// find the top score neighbors
		double [][] s = finalScoreVector.toArray();
		//System.out.println("s:"+s.length);
		for (int i =0 ; i < K; i++){
			double max = -1.0; 
			int maxIdx = 0;
			for(int j =0 ; j < s.length; j++){
				if (s[j][0] > max){
					max = s[j][0] ;
				    maxIdx = j;
				}
			}
			s[maxIdx][0] = -1;
			topScore[i] = maxIdx;
		}
		return topScore;	
	}
	
	public boolean inSameGraph(int nodeA, int nodeB, Set<Integer> nodeSet, Map<Integer, Set<Integer>> neighbourMap ) {
 		
 		/*
 		 * this function will take two node ID's, the set of nodes to check, and the neighbourMap
 		 * 
 		 * it will return true if it can find a path from nodeA to nodeB with all nodes within the node set,
 		 * 
 		 * and will return false otherwise. 
 		 * 
 		 * */
 		
 		Set<Integer> toDo =  new HashSet<Integer>();
 		Set<Integer> done = new HashSet<Integer>();
 		toDo.add(nodeA);
 		while(toDo.isEmpty() == false) {
 			Iterator<Integer> iter = toDo.iterator();
 			int curr = (int)iter.next();
 			toDo.remove(curr);
 			done.add(curr);
 			for(Integer n:neighbourMap.get(curr)) {
 				if(nodeSet.contains(n)) {
 					if(n == nodeB) {
 						return true;
 					}
 					if(done.contains(n) == false) {
 						toDo.add(n);
 					}
 				}
 			}
 		}
 		return false;
 	}
	
	public void setNetwork1(Set<Integer> nodes, int [] expertIds, Map<Integer, Set<Integer>> neighborMap){
		
		StringBuilder r = new StringBuilder("graph { size = 15 ;node [fixedsize=false margin=0.01 fontcolor=blue fontsize=10 width = 0 height = 0];");
		Set<String> edges = new HashSet<String>(); 
		for (Integer s : nodes) {
			Set<Integer> neigh = neighborMap.get(s);
			for (Integer n : neigh){
				if(nodes.contains(n)){
					if(edges.contains(s+"--"+n) ||edges.contains(n+"--"+s))
						continue;
					edges.add(s+"--"+n);
					r.append(s+"--"+n+";");					
				}
			}
		}
		for(int i =0 ; i< expertIds.length;i++)
			r.append(expertIds[i]+"[style=filled,fillcolor=red fontcolor=white];");
		r.append("}");	
		network = r.toString();
	}
	
	public void setNetwork2(Set<Integer> nodes, int [] expertIds, Map<Integer, Set<Integer>> neighborMap, String[] experts){
		
		StringBuilder r = new StringBuilder("graph { size = 15 ;node [fixedsize=false margin=0.01 fontcolor=blue fontsize=10 width = 0 height = 0];");
		Set<String> edges = new HashSet<String>(); 
		for (Integer s : nodes) {
			Set<Integer> neigh = neighborMap.get(s);
			for (Integer n : neigh){
				if(nodes.contains(n)){
					if(edges.contains(s+"--"+n) ||edges.contains(n+"--"+s))
						continue;
					edges.add(s+"--"+n);
					r.append("\""+experts[s]+"\"--\""+experts[n]+"\";");
				}
			}
		}
		for(int i =0 ; i< expertIds.length;i++)
			r.append("\""+experts[expertIds[i]]+"\"[style=filled,fillcolor=red fontcolor=white];");
		r.append("}");	
		network = r.toString();
	}
	
	public void setNetwork1(Set<Integer> nodes, int [] expertIds, Map<Integer, Set<Integer>> neighborMap , DoubleMatrix2D finalScoreVector){
		
		double[] normScores = new double[nodes.size()];
		
		double scoreSum = 0.0;
		
		for(Integer s:nodes) {
			scoreSum += finalScoreVector.get(s, 0);
			
		}
		
		//System.out.println("Score Sum " + scoreSum);
		
		int i = 0;
		
		double modScoreSum = 0.0;
		double maxScore = 0.0;
		
		for(Integer s:nodes) {
			normScores[i] = finalScoreVector.get(s,  0) / scoreSum;
			normScores[i] += 0.05;
			
			if(normScores[i] > 0.3) {
				normScores[i] -= 0.1;
			}
			
			normScores[i] *= 25;
			//System.out.println(normScores[i]);
			if(normScores[i] > maxScore) {
				maxScore = normScores[i];
			}
			modScoreSum += normScores[i];
			i ++;
		}
		
		//System.out.println("Stat Dev: " + getSD(normScores));
		
		//System.out.println("Average Score: " + (modScoreSum / normScores.length));
		
		//System.out.println("Max Score: " + maxScore);
		
		if(maxScore > ( (modScoreSum / normScores.length) + (getSD(normScores) * 3))) {
		
			double threshold = (modScoreSum / normScores.length) +(getSD(normScores)*2);
		
			for(i = 0; i < normScores.length; i ++) {
				if (normScores[i] > threshold) {
					normScores[i] = (modScoreSum / normScores.length);
				}
			}
		}
		
		
		StringBuilder r = new StringBuilder("graph { size = 15 ;");
		Set<String> edges = new HashSet<String>(); 
		i = 0;
		for (Integer s : nodes) {
			
			r.append("node [fixedsize=false margin=0.01 fontcolor=blue fontsize=25 width="+normScores[i]+" height ="+normScores[i]+"];");
			Set<Integer> neigh = neighborMap.get(s);
			for (Integer n : neigh){
				if(nodes.contains(n)){
					if(edges.contains(s+"--"+n) ||edges.contains(n+"--"+s))
						continue;
					edges.add(s+"--"+n);
					r.append(s+"--"+n+";");
					
				}
			}
			i ++;
		}
		for(i =0 ; i< expertIds.length;i++)
			r.append(expertIds[i]+"[style=filled,fillcolor=red fontcolor=white];");
		r.append("}");	
		network = r.toString();
	}
	
	public void setNetwork2(Set<Integer> nodes, int [] expertIds, Map<Integer, Set<Integer>> neighborMap, String[] experts, DoubleMatrix2D finalScoreVector){
		
		double[] normScores = new double[nodes.size()];
		
		double scoreSum = 0.0;
		
		for(Integer s:nodes) {
			scoreSum += finalScoreVector.get(s, 0);
			
		}
		
		//System.out.println("Score Sum " + scoreSum);
		
		int i = 0;
		
		double modScoreSum = 0.0;
		double maxScore = 0.0;
		
		for(Integer s:nodes) {
			normScores[i] = finalScoreVector.get(s,  0) / scoreSum;
			normScores[i] += 0.05;
			
			if(normScores[i] > 0.3) {
				normScores[i] -= 0.1;
			}
			
			normScores[i] *= 25;
			if(normScores[i] > maxScore) {
				maxScore = normScores[i];
			}
			//System.out.println(normScores[i]);
			modScoreSum += normScores[i];
			i ++;
		}
		
		//System.out.println("Stat Dev: " + getSD(normScores));
		
		//System.out.println("Average Score: " + (modScoreSum / normScores.length));
		
		//System.out.println("Max Score: " + maxScore);
		
		if(maxScore > ( (modScoreSum / normScores.length) + (getSD(normScores) * 3))) {
		
			double threshold = (modScoreSum / normScores.length) +(getSD(normScores)*2);
		
			for(i = 0; i < normScores.length; i ++) {
				if (normScores[i] > threshold) {
					normScores[i] = (modScoreSum / normScores.length);
				}
			}
		}
		
		StringBuilder r = new StringBuilder("graph { size = 15 ;");
		Set<String> edges = new HashSet<String>(); 
		i = 0;
		for (Integer s : nodes) {
			//System.out.println("Final Score Vector Check " + finalScoreVector.get(s, 0));
			
			r.append("node [fixedsize=false margin=0.01 fontcolor=blue fontsize=25 width="+normScores[i]+" height="+normScores[i]+"];");
			Set<Integer> neigh = neighborMap.get(s);
			for (Integer n : neigh){
				if(nodes.contains(n)){
					if(edges.contains(s+"--"+n) ||edges.contains(n+"--"+s))
						continue;
					edges.add(s+"--"+n);
					r.append("\""+experts[s]+"\"--\""+experts[n]+"\";");					
				}
			}
			i++;
		}
		for(i =0 ; i< expertIds.length;i++)
			r.append("\""+experts[expertIds[i]]+"\"[style=filled,fillcolor=red fontcolor=white];");
		r.append("}");	
		network = r.toString();
	}
	
	public double getSD(double numArray[]) {
		double sum = 0.0, standardDeviation = 0.0;
        int length = numArray.length;

        for(double num : numArray) {
            sum += num;
        }

        double mean = sum/length;

        for(double num: numArray) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation/length);
	}
	
	@SuppressWarnings("rawtypes")
	public Set<Integer> getTreeStylePaths(Map<Integer, Set<Integer>> neighbourMap,Map<Integer, TwoHop[]> twoHopMap, DoubleMatrix2D finalScoreVector){
		//this is where i'm going to do AK-Master Node Visualizations. I think it'll be really easy to do, and be similar to getAllPaths
		//it should just be different for loops at the end 
		
		Set<Integer> nodes = new HashSet<Integer>();
		
		Set<Integer> AKnodes = new HashSet<Integer>();
        
		for(int i = 0; i < expertIds.length; i ++) {
        	AKnodes.add(expertIds[i]);
        }
        
		for(Integer key:neighbourMap.keySet()) {
			nodes.add(key);
		}
		
		
        double [] scoreArr = new double[nodes.size()];
        int i = 0;
        for (Integer n:nodes) {
        	scoreArr[i] = finalScoreVector.get(n, 0);
        	i++;
        }        
        
        Map<Integer, Double> scoreMap = new HashMap<Integer, Double>();
        i = 0;
        for(Integer n:nodes) {
        	scoreMap.put(n, scoreArr[i]);
        	i ++;
        }
        
        Set<Integer> nodesToReturn = new HashSet<Integer>();
        
        int MasterNode = expertIds[0];
        
        int CurrNode = -1; //default node value
        
        for(i = 1; i < expertIds.length; i ++) {
        	
        	CurrNode = expertIds[i];
        	
        	Stack currStack = dijkstraPath(MasterNode, CurrNode, neighbourMap, scoreMap);
        	
        	currStack.pop();
        	
        	for(Object item:currStack) {
        		
    			nodesToReturn.add((Integer)item);
    		
    		}
        	
        }
        
        return nodesToReturn;
		
		
	}
	
 	public Set<Integer> getBestPaths(Map<Integer, Set<Integer>> neighbourMap,Map<Integer, TwoHop[]> twoHopMap, DoubleMatrix2D finalScoreVector){
 		
 		Set<Integer> nodes = new HashSet<Integer>();

		double[][] weights = new double[expertIds.length][expertIds.length]; //weights at [i][j] will be the weight of the path between nodes a and b

		Stack[][] paths = new Stack[expertIds.length][expertIds.length]; //paths at [i][j] will be the stack of nodes for the path between nodes a and b
		
		for(int i = 0; i < expertIds.length; i ++) {
        	for (int j = 0; j < expertIds.length; j++) {
        		paths[i][j] = new Stack();
        	}
        }
        
        for(int i = 0; i < expertIds.length; i ++) {
        	
        	weights[i][i] = Double.MAX_VALUE;//the weight for a path from a node to itself is inf;
        	
        	paths[i][i].push(expertIds[i]);//the path for a node to itself is just that node
        
        }
		
		Set<Integer> AKnodes = new HashSet<Integer>();
        
		for(int i = 0; i < expertIds.length; i ++) {
        		AKnodes.add(expertIds[i]);
        }
		
		for(Integer key:neighbourMap.keySet()) {
			nodes.add(key);
		}
        
        double [] scoreArr = new double[nodes.size()];
        int i = 0;
        for (Integer n:nodes) {
        	scoreArr[i] = finalScoreVector.get(n, 0);
        	i++;
        }        
        
        Map<Integer, Double> scoreMap = new HashMap<Integer, Double>();
        i = 0;
        for(Integer n:nodes) {
        	scoreMap.put(n, scoreArr[i]);
        	i ++;
        }
        
        //path selection part
        
        Set<Integer> nodesToReturn = new HashSet<Integer>();
        
        Set<Integer> nodesCopy = AKnodes;
        
        Set<Integer> usedNodes = new HashSet<Integer>();
        
        int j = 0;
        
        i = 0;
        
        for(Integer n:AKnodes) {
        	
        	j = 0;
        	
        	for(Integer nC:nodesCopy) {
        		
        		if(!n.equals(nC)) {
        			
        			//System.out.println("Node Pair " + n + " " + nC);
        			
        			Stack currentPath = dijkstraPath(n, nC, neighbourMap, scoreMap);
        			
        			weights[i][j] = (double)currentPath.pop();
        			
        			for(Object item:currentPath) {
        				
        				if(paths[i][j].contains(item) == false) {
        					paths[i][j].push(item);
        				}
        				
        			}
        			
        			usedNodes.add(n);
        			
        			usedNodes.add(nC);
        		}
        		
        		j ++;
        		
        	}
        	
        	i ++;
        	
        }
 		
        
        /*
         * What I have: Paths from every AKnode to every other AKnode, and weights of said paths 
         * 
         * What I want: The group of paths that'll connect all AKnodes with the lowest weight
         * 
         * To do that: 
         * 
         * 	Find the path with the lowest weight where either AK node for the path is not visited
         * 		
         * 	Add that path to the nodes to return
         * 
         * 	Mark the AKnodes for that path as visited
         * 
         * 	Find the next path with the lowest weight where either AK node for the path is not visited 
         * 
         * 	Repeat until all AKnodes have been visited 
         * 
         * */
        
        
        //we're going to need something to keep track of which AKnodes have been visited
        boolean[] coveredNodes = new boolean [expertIds.length];
        boolean allNodesCovered = false;
        for(i = 0; i < coveredNodes.length; i ++) {
        	coveredNodes[i] = false;
        }
        
        while(allNodesCovered == false) {
        
        	// find the path with the lowest weight 
        	i = 0;
        	j = 0;
        	double minWeight = Double.MAX_VALUE;
        	int minWeightStart = 0;
        	int minWeightEnd = 0;
        	for(i = 0; i < expertIds.length; i ++) {
        		for(j = 0; j < expertIds.length; j ++) {
        			if(weights[i][j] < minWeight ) {
        				minWeight = weights[i][j];
        				minWeightStart = i;
        				minWeightEnd = j;
        			}
        		}
        	}
        
        	//add that path to the nodes to return
        	if(coveredNodes[minWeightStart] == false || coveredNodes[minWeightEnd] == false) {
        		for(Object item:paths[minWeightStart][minWeightEnd]) {
        			nodesToReturn.add((Integer) item);
        	
        		}
        	}
        	//mark the AKnodes for that path as visited
        	coveredNodes[minWeightStart] = true;
        	coveredNodes[minWeightEnd] = true;
        	weights[minWeightStart][minWeightEnd] = Double.MAX_VALUE;
        
        	//repeat until all AKnodes have been visited
        	allNodesCovered = true;
        	for(i = 0; i < coveredNodes.length; i ++) {
        		if(coveredNodes[i] == false) {
        			allNodesCovered = false;
        		}
        	}
        
        }
        return nodesToReturn;
        
	}
 	
	public Set<Integer> getAllPaths(Map<Integer, Set<Integer>> neighbourMap,Map<Integer, TwoHop[]> twoHopMap, DoubleMatrix2D finalScoreVector){
		
		/*
		 * I'm having an issue where way too many nodes are added to the team display 
		 * 
		 * The total number of nodes included in this display should be close to the number of nodes included in the clique display
		 * 
		 * I think the issue may be with my dijkstraPath algorithm implementation 
		 * 
		 * createSpGraph is what the clique technique uses, perhaps that would be a good starting point on reworking my manyPath
		 * 
		 * */
		
		Set<Integer> nodes = new HashSet<Integer>();
		
		Set<Integer> AKnodes = new HashSet<Integer>();
        
		for(int i = 0; i < expertIds.length; i ++) {
        		AKnodes.add(expertIds[i]);
        }
		
		for(Integer key:neighbourMap.keySet()) {
			nodes.add(key);
		}
        
        double [] scoreArr = new double[nodes.size()];
        int i = 0;
        for (Integer n:nodes) {
        	scoreArr[i] = finalScoreVector.get(n, 0);
        	i++;
        }        
        
        Map<Integer, Double> scoreMap = new HashMap<Integer, Double>();
        i = 0;
        for(Integer n:nodes) {
        	scoreMap.put(n, scoreArr[i]);
        	i ++;
        }
        
        //path selection part
        
        Set<Integer> nodesToReturn = new HashSet<Integer>();
        
        Set<Integer> nodesCopy = AKnodes;
        
        for(Integer n:AKnodes) {
        	
        	for(Integer nC:nodesCopy) {
        		
        		if(!n.equals(nC)) {
        			
        			//System.out.println("Node Pair " + n + " " + nC);
        			
        			Stack placeholder = dijkstraPath(n, nC, neighbourMap, scoreMap);
        			
        			placeholder.pop();
        			
        			for(Object item:placeholder) {
        				
        				nodesToReturn.add( (Integer) item);
        				
        			}
        		}
        	}
        }
        
        return nodesToReturn;
	}
	
	public Set<Integer> getAccidental(Map<Integer, Set<Integer>> neighbourMap,Map<Integer, TwoHop[]> twoHopMap, DoubleMatrix2D finalScoreVector){
		/*
		 * So this was my original getAllPaths function, which had some unintentional pruning with the used nodes stuff
		 * 
		 * However, it does make a visualization really similar to AK-Master Node
		 * 
		 * I'm not sure if it's completely the same, so I decided to leave it in like this even though i'm not using it anywhere
		 * 
		 * */
		Set<Integer> nodes = new HashSet<Integer>();
		
		Set<Integer> AKnodes = new HashSet<Integer>();
        
		for(int i = 0; i < expertIds.length; i ++) {
        		AKnodes.add(expertIds[i]);
        }
		
		for(Integer key:neighbourMap.keySet()) {
			nodes.add(key);
		}
        
        double [] scoreArr = new double[nodes.size()];
        int i = 0;
        for (Integer n:nodes) {
        	scoreArr[i] = finalScoreVector.get(n, 0);
        	i++;
        }        
        
        Map<Integer, Double> scoreMap = new HashMap<Integer, Double>();
        i = 0;
        for(Integer n:nodes) {
        	scoreMap.put(n, scoreArr[i]);
        	i ++;
        }
        
        //path selection part
        
        Set<Integer> nodesToReturn = new HashSet<Integer>();
        
        Set<Integer> nodesCopy = AKnodes;
        
        Set<Integer> usedNodes = new HashSet<Integer>();
        
        for(Integer n:AKnodes) {
        	
        	for(Integer nC:nodesCopy) {
        		
        		if(!n.equals(nC) && (usedNodes.contains(n) == false || usedNodes.contains(nC) == false)) {
        			
        			//System.out.println("Node Pair " + n + " " + nC);
        			
        			Stack placeholder = dijkstraPath(n, nC, neighbourMap, scoreMap);
        			
        			placeholder.pop();
        			
        			for(Object item:placeholder) {
        				
        				nodesToReturn.add( (Integer) item);
        				
        			}
        			
        			usedNodes.add(n);
        			
        			usedNodes.add(nC);
        		}
        	}
        }
        
        return nodesToReturn;
	}
	
	@SuppressWarnings("unchecked")
	public Stack dijkstraPath(int startNode, int destNode, Map<Integer, Set<Integer>> neighbourMap, Map<Integer, Double> scoreMap) {
		/*
		 * This seems to currently be adding way too many nodes. It should be adding a number of nodes similar to that of CreateSpGraph
		 * 
		 * */
		
		Set<Integer> unvisitedNodes = new HashSet<Integer>();
		
		for(Integer key:neighbourMap.keySet()) {
			unvisitedNodes.add(key);
			
		}
		
		Map<Integer, Double> weight = new HashMap<Integer, Double>();
		Map<Integer, Stack> path = new HashMap<Integer, Stack>();
		for(Integer n:unvisitedNodes) {
			weight.put(n,  Double.MAX_VALUE);
			path.put(n, new Stack());
			path.get(n).push(startNode);
		}
		weight.put(startNode,  0.0);
		
		int currNode = startNode;
		
		int prevCurrNode = -1;
		
		double minWeight = Double.MAX_VALUE;

		while(unvisitedNodes.isEmpty() == false) {
			
			if(prevCurrNode == currNode) {
				System.out.println("A new current node has not been selected");
			}
			
			for(Integer n:neighbourMap.get(currNode)) {
				
				if(unvisitedNodes.contains(n)) {
				
					double tenDist = weight.get(currNode) + (1.0 - scoreMap.get(n));
			
					if(tenDist < weight.get(n)) {
				
						weight.put(n,  tenDist);
						
						path.put(n, path.get(currNode));
						path.get(n).push(n);
						
					}
					
					if(n == destNode) {
						path.get(n).push(weight.get(n));
						return path.get(n);
			
					}
					
				}
			
			}
			
			unvisitedNodes.remove(currNode);
			
			prevCurrNode = currNode;
			minWeight = Double.MAX_VALUE;
			for(int n:unvisitedNodes) {
				if(weight.get(n) < minWeight) {
					minWeight = weight.get(n);
					currNode = n;
				} 
			}
			
		}
		
		Stack ret = new Stack();
		
		ret.push(startNode);
		ret.push(destNode);
		ret.push(1.0);
		
		return ret;
		
		
	}
	
	public Set<Integer> getlocalNodes( Map<Integer, Set<Integer>> neighborMap){
		//oh thank god, this is commented 
		Map<Integer, Set<Integer>> visited = new HashMap<Integer, Set<Integer>>() ; // visited 
        LinkedList<Pair> queue = new LinkedList<Pair>(); // visited but waiting for neighbors to be visited 
        
        // visit all team members to queue
		for (int i = 0 ; i < expertIds.length; i++){
			
			Pair p= new Pair(); //wut 
			
			p.first = expertIds[i];  // id to be visited (in the queue) 
			p.second = expertIds[i]; // where we come from
			
			queue.add(p);    // starting point
			
			Set<Integer> x = new HashSet<Integer>();
			x.add(expertIds[i]);      
			
			visited.put(expertIds[i],x); // visited from itself
			
        	if(x.size() == expertIds.length)    
        		return getVisited(visited); // get the results   (meet after update: expertIds.length==1)  
		}
		
		while (queue.size() != 0){ // extension by BFS
			Pair c = queue.poll(); 
            
			Set<Integer> neigh =  neighborMap.get(c.first); // id
            
            for(Integer y :neigh){  // go over neighbors of c.first (current)
            	
            	int r = checkvisited(visited,y,expertIds.length, c.second); // y: neighbor of c
            	
            	if(r==3)
            		return getVisited(visited); // get the results     (already meet)       		
            	
            	if(r == 0){
	    			// no any visit => visit[y] = True
	            	Set<Integer> x = new HashSet<Integer>();
	    			x.add(c.second); // visited from c.second
	            	
	    			visited.put(y,x);  // set visited from c.second
	                
	            	// update the queue
	    			Pair p= new Pair();
	    			p.first = y;
    			    p.second = c.second;
    			    queue.add(p); // add p to queue for visiting neighbor of p
    			    
            	} else if(r == 1){
            		// y is not visited form c.second => visit[y] = True
	            	Set<Integer> x = visited.get(y);
	    			x.add(c.second);
	            	visited.put(y,x);  // set visited from c.second
	            	
	            	if(x.size() == expertIds.length)    
	            		return getVisited(visited); // get the results   (meet after update)         		
	            	
	            	// update the queue
	    			Pair p= new Pair();
	    			p.first = y;
    			    p.second = c.second;
    			    queue.add(p); // add them to queue for visiting neighbor
    			    
            	}
            }
		}
				
		return getVisited(visited);
		
	}
		
	private Set<Integer> getVisited(Map<Integer, Set<Integer>> visited) {
    	
		Set<Integer> r = new HashSet<Integer>();

		for(Entry<Integer, Set<Integer>> e : visited.entrySet()){
			r.add(e.getKey());		
		}
		return r;
	}

	// y : a neighbor
	// fromExpert : one team member
	private int checkvisited(Map<Integer, Set<Integer>> visited, Integer y, int teamSize, int fromExpert) {
		
		if(visited.containsKey(y)){ 	
			Set<Integer> s = visited.get(y); // y is visited from s ={experts}
			
			if (s.size() == teamSize) 
				return 3; // visited from all experts
			if(s.contains(fromExpert)) 
				return 2;  // y visited from fromExpert
			else 
				return 1; // visited but not visited from fromExpert
		}
		else
			return 0; // need new entry (y is not visited at all)
	}
}

