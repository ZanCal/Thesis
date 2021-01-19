//package ca.uwindsor.cs.mkargar.graphsearch.WWW;

import java.util.*;
import java.io.*;

/**
 * Created by karga on 12/2/2017.
 */
public class SmallestEmbedding {

    public static double MaxCalDouble = 100000;

    public Map<String, Integer> buildEMBTeam(
            Set<String> project, Map<String, Set<Integer>> skillExpertMap, GraphEmbedding gem){

        Map<String, Integer> bestTeam = new HashMap<String, Integer>();
        double leastWeight = Double.MAX_VALUE;

        
        String minSkill = null;
        for (String skill : project)
        {
            if (minSkill == null )
            {
                minSkill = skill;
            }
            if(skillExpertMap.get(skill).size() < skillExpertMap.get(minSkill).size())
            {
                minSkill=skill;
            }
        }
        Set<Integer> potentialRootIdList = skillExpertMap.get(minSkill);

        for (int rootId : potentialRootIdList) {

            Map<String, Integer> team = new HashMap<String, Integer>();
            
            double weight = 0;
            
            team.put(minSkill, rootId);
            double [] curTeamVec = gem.embVec(rootId);

            for(String skill : project) { // all skills

                if(!minSkill.equals(skill)) {
                    //-----------------------------------------------
                	double shortestDistValue = Double.MAX_EXPONENT;
                    int bestExpert = -1;
                    for (int expertId : skillExpertMap.get(skill)) { // for all candidates having this skill

                         double [] canVec = gem.embVec(expertId);
                    	 double dist = distance(canVec,curTeamVec);
                    	                                
                            if (dist < shortestDistValue) { 		// find one who is the closest to team
                                shortestDistValue = dist;
                                bestExpert = expertId;
                            }
                    }
                    //-----------------------------------------------

                    if (bestExpert != -1) {
                        team.put(skill, bestExpert);
                           curTeamVec = addVec(curTeamVec,gem.embVec(bestExpert));
                           weight += shortestDistValue;  			// add the shortest path to the team cast SUM                      
                    }
                }
            }
           
            if(team.size() == project.size()){ // if the team is complete  check if it is better than the current teams?
                if(weight < leastWeight) { //MIN
                    leastWeight = weight;
                    bestTeam = team;
                }
            }
        }

        return bestTeam;
    }

    private double[] addVec(double[] curTeamVec, double[] embVec) {
    	double [] d = new double[embVec.length];
    	for (int i = 0; i < embVec.length ; i++){
    		d[i] = curTeamVec[i] + embVec[i];
    	}
		return d;
	}

	private double distance(double[] canVec, double[] curTeamVec) {
    	
    	double s = 0;
    	for (int i = 0; i < canVec.length ; i++){
    		s += canVec[i]*curTeamVec[i];
    	}
    		
		return -s; // - canVec.curTeamVec
	}

	public double queryTwoHopArray(Map<Integer, TwoHop[]> twoHopMap, int sId, int tId){

        double resultDist = MaxCalDouble;

        TwoHop[] sList = twoHopMap.get(sId);
        TwoHop[] tList = twoHopMap.get(tId);

        int si = 0, ti = 0;

        while (si < sList.length && ti < tList.length) {

            int nodeSId = sList[si].nodeId;

            int nodeTId = tList[ti].nodeId;

            if(nodeSId < nodeTId){ // s < t
                si++;
            } else if (nodeSId > nodeTId) {    // s > t
                ti++;
            } else {
                double currentDistS = sList[si].dist;
                double currentDistT = tList[ti].dist;
                double currentDistTotal = currentDistS + currentDistT;

                if(currentDistTotal < resultDist)
                    resultDist = currentDistTotal;
                si++; ti++;
            }
        }

        return resultDist;
    }

    public Map<Integer, TwoHop[]> readTwoHopIndexArray(String twoHopIndexFileName){
        String lineD = null;

        Map<Integer, TwoHop[]> twoHopMap = new HashMap<Integer, TwoHop[]>();

        try {
            BufferedReader inputDist =  new BufferedReader(new FileReader(new File(twoHopIndexFileName)));

            while ( (( lineD = inputDist.readLine()) != null ) ){

                lineD = lineD.trim();

                String []rowD = lineD.split(" ");

                int currentId = Integer.parseInt(rowD[0]);

                twoHopMap.put(currentId, new TwoHop[rowD.length - 1]);

                for(int i=1; i<rowD.length; i ++){
                    String []neighborDist = rowD[i].split("#");
                    twoHopMap.get(currentId)[i-1] = new TwoHop(Integer.parseInt(neighborDist[0]), Double.parseDouble(neighborDist[1]), Integer.parseInt(neighborDist[2]) ) ;
                }
            }

            inputDist.close();
        }catch(Exception ex){
            System.out.print(ex.getMessage()+"\n");
        }

        return twoHopMap;
    }

}
