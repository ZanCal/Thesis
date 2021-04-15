//package ca.uwindsor.cs.mkargar.graphsearch.WWW;

import java.util.*;
import java.io.*;

/**
 * Created by karga on 12/2/2017.
 */
public class ShortestPath extends TeamGenerationMethod {

    public static double MaxCalDouble = 100000;

    public Map<String, Integer> buildShortestPathTeam(
            Set<String> project, Map<String, Set<Integer>> skillExpertMap, Map<Integer, TwoHop[]> twoHopMap){

        Map<String, Integer> bestTeam = new HashMap<String, Integer>();
        double leastWeight = Double.MAX_VALUE;

        String minSkill = null;
        for (String skill : project){
            if (minSkill == null ){
                minSkill = skill;
            }
            if(skillExpertMap.get(skill).size() < skillExpertMap.get(minSkill).size()){
                minSkill=skill;
            }
        }
        Set<Integer> potentialRootIdList = skillExpertMap.get(minSkill);

        for (int rootId : potentialRootIdList) {

            Map<String, Integer> team = new HashMap<String, Integer>();
            double weight = 0;
            team.put(minSkill, rootId);

            for(String skill : project) {

                if(!minSkill.equals(skill)) {
                    double shortestDistValue = Double.MAX_EXPONENT;
                    int bestExpert = -1;

                    for (int expertId : skillExpertMap.get(skill)) {

                        double dist = queryTwoHopArray(twoHopMap, expertId, rootId);

                        if (dist < MaxCalDouble) {
                            if (dist < shortestDistValue) {
                                shortestDistValue = dist;
                                bestExpert = expertId;
                            }
                        }
                    }

                    if (bestExpert != -1) {
                        team.put(skill, bestExpert);
                        weight += shortestDistValue;
                    }
                }
            }

            if(team.size() == project.size()){
                if(weight < leastWeight) {
                    leastWeight = weight;
                    bestTeam = team;
                }
            }
        }

        return bestTeam;
    }

    public Map<String, Integer> buildShortestPathTeam(
            Set<String> project, Map<String, Set<Integer>> skillExpertMap, Map<Integer, TwoHop[]> twoHopMap, Set<Integer> usedIds){

        Map<String, Integer> bestTeam = new HashMap<String, Integer>();
        double leastWeight = Double.MAX_VALUE;

        String minSkill = null;
        for (String skill : project){
            if (minSkill == null ){
                minSkill = skill;
            }
            if(skillExpertMap.get(skill).size() < skillExpertMap.get(minSkill).size()){
                minSkill=skill;
            }
        }
        Set<Integer> potentialRootIdList = skillExpertMap.get(minSkill);

        for (int rootId : potentialRootIdList) {

            Map<String, Integer> team = new HashMap<String, Integer>();
            double weight = 0;
            team.put(minSkill, rootId);

            for(String skill : project) {

                if(!minSkill.equals(skill)) {
                    double shortestDistValue = Double.MAX_EXPONENT;
                    int bestExpert = -1;

                    for (int expertId : skillExpertMap.get(skill)) {

                        double dist = queryTwoHopArray(twoHopMap, expertId, rootId);

                        if (dist < MaxCalDouble) {
                            if ((dist < shortestDistValue) && (usedIds.contains(expertId) == false)) {
                                shortestDistValue = dist;
                                bestExpert = expertId;
                            }
                        }
                    }

                    if (bestExpert != -1) {
                        team.put(skill, bestExpert);
                        weight += shortestDistValue;
                    }
                }
            }

            if(team.size() == project.size()){
                if(weight < leastWeight) {
                    leastWeight = weight;
                    bestTeam = team;
                }
            }
        }

        return bestTeam;
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
