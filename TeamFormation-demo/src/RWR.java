
import java.io.*;
import java.util.*;

import cern.colt.matrix.impl.*;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.matrix.DoubleMatrix2D;

/**
 * Created by karga on 12/2/2017.
 */
public class RWR extends TeamGenerationMethod {
	
	//public DoubleMatrix2D finalScoreVector; 

    public Map<String, Set<Integer>> buildEligibleSkillExpertMap(int minCardinality, int maxCardinality, String termCountFile, String termIndexFile){

        Map<String, Set<Integer>> skillExpertMap = new HashMap<String,Set<Integer>>();

        try {
            Set<String> EligibleSkills = new HashSet<String>();
            BufferedReader br =
                    new BufferedReader(new FileReader(termCountFile));
            String line;
            while ((line = br.readLine()) != null) {

                String ss[] = line.split("\t");
                String skill = ss[0];
                Integer Experts = Integer.parseInt(ss[1]);

                if (Experts >= minCardinality && Experts <= maxCardinality) {
                    EligibleSkills.add(skill);
                }
            }
            br.close();

            br = new BufferedReader(new FileReader(termIndexFile));

            while ((line = br.readLine()) != null) {
                String ss[] = line.split("\t");
                String Skill = ss[0];
                if (EligibleSkills.contains(Skill)) {
                    Set<Integer> expertsSet = new HashSet<Integer>();
                    for (int i = 1; i < ss.length; i++) {
                        int value = Integer.parseInt(ss[i]);
                        expertsSet.add(value);
                    }

                    skillExpertMap.put(Skill, expertsSet);
                }
            }
            br.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return skillExpertMap;
    }

    public Set<String> createRandomProject(int numOfRequiredSkills, Set<String> EligibleSkillsSet){

        Random randomGen = new Random();

        List<String> skillList = new ArrayList<String>(EligibleSkillsSet);

        Set<String> project = new HashSet<String>();
        while (project.size() < numOfRequiredSkills) {
            int skillId = randomGen.nextInt(skillList.size());
            String skill = skillList.get(skillId);
            if (!project.contains(skill)) {
                project.add(skill);
            }
        }

        return project;
    }
    
    public Map<Integer, Set<String>>  createSampleProjects(){
    	    	
    	Map<Integer, Set<String>> projects = new HashMap<Integer, Set<String>>();
    	
        // project 0 
    	Set<String> x = new HashSet<String>();
    	x.add("expressive");
    	x.add("reformulation");
    	projects.put(0, x);

    	// project 1
    	x = new HashSet<String>();
    	x.add("bioinformatics");
    	x.add("tensor");
    	projects.put(1, x);
    	
    	// project 2   
    	x = new HashSet<String>();
    	x.add("enhancing");
    	x.add("snapshot");
      	projects.put(2, x);
        
      	// project 3 
      	x = new HashSet<String>();
    	x.add("expressive");
    	x.add("embedding");
      	projects.put(3, x);

    	
        // project 4 
    	x = new HashSet<String>();
    	x.add("reformulation");
    	x.add("autonomous");      
      	projects.put(4, x);
      	
      	// project 5, shows off pruned BFS vs improved pruned BFS
      	x = new HashSet<String>();
      	x.add("bioinformatics");
      	x.add("autonomous");
      	x.add("embedding");
      	projects.put(5, x);

        return projects;
    }

    public DoubleMatrix2D buildMatrixM(double beta, String graphFile){

        DoubleMatrix2D M = null;
        Map<Integer,Set<Integer>> graphMap = new HashMap<Integer,Set<Integer>>();

        String sCurrentLine;
        try{
        	
            BufferedReader br = new BufferedReader(new FileReader(graphFile));
            while ((sCurrentLine = br.readLine()) != null) {

                String[] ss = sCurrentLine.split("\t");

                int nodeId = Integer.parseInt(ss[0]);

                Set<Integer> neighbors = new HashSet<Integer>();

                for(int i=1; i<ss.length; i++){
                	
                    //we assume the graph is unweighted and therefore ignore the weight
                    String[] tt = ss[i].split("#");
                    neighbors.add(Integer.parseInt(tt[0]));
                }
                graphMap.put(nodeId, neighbors);
            }

            int nodesNum = graphMap.size();
            M = new SparseDoubleMatrix2D(nodesNum,nodesNum);

            for(int nodeId : graphMap.keySet()){
            	
                for(int neighbourId : graphMap.get(nodeId)){
                	
                    double rowSum = graphMap.get(nodeId).size();
                    double value = (1/rowSum);
                    value = value*beta;
                    //A.set(neighbours, node, Math.round(value*10000.00)/10000.000);
                    M.set(neighbourId, nodeId, value);

                }
            }
            br.close();
        }
        catch(Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }

        return M;
    }

    public DoubleMatrix2D RandomRandomWalkRestart( List<Integer> restartList, DoubleMatrix2D M, double beta, int RWR_RunNum){
        //Creating 0.2(NxN) matrix on the basis of values in restList
        DoubleMatrix2D RWR_M = M.copy();
        int nodeNum = RWR_M.rows();

        for(int i=0;i<restartList.size();i++) {

            int rowId = restartList.get(i);

            for(int colId=0; colId<nodeNum; colId++) {
                double valueOfn = 1.0/restartList.size();
                valueOfn = valueOfn*(1-beta);
                double AValue = M.get(rowId, colId);
                double resultValue = valueOfn + AValue;
                RWR_M.set(rowId, colId, resultValue);
            }
        }

        DoubleMatrix2D scoreVector = new SparseDoubleMatrix2D(nodeNum,1);
        double valueV1 = 1.0/nodeNum;
        for(int i=0; i<nodeNum; i++){
            scoreVector.set(i,0,valueV1);
        }

        Algebra algebra = new Algebra();

        for(int i=0;i<RWR_RunNum; i ++) {
            DoubleMatrix2D tempV = algebra.mult(RWR_M, scoreVector);
            scoreVector = tempV;
        }
        return scoreVector;
    }

    //--------------------------------------------------------------------------------------------------------
    public Map<String, Integer> buildRWRTeam(
            Set<String> project, Map<String, Set<Integer>> skillExpertMap, DoubleMatrix2D M, double beta, int RWR_RunNum){
    	//what is M used for?
        //Find the skill with minimum cardinality in the project (skillMinCard)
        String skillMinCard = null;
        for (String skill : project){
            if (skillMinCard == null ){
                skillMinCard = skill;
            }
            if(skillExpertMap.get(skill).size() < skillExpertMap.get(skillMinCard).size()){
                skillMinCard=skill;
            }
        }

        double maxScore = Double.MIN_VALUE;
        Map<String, Integer> bestTeam = null;

        // start team with experts who have skillMinCard as a skill
        for(int micCardExpertId : skillExpertMap.get(skillMinCard)) {

            Map<String, Integer> team = new HashMap<String, Integer>();
            double score = 0;
            
            // initialize team with an expert with minimum skill   
            team.put(skillMinCard, micCardExpertId);

            // go and find the other skills in the project
            for(String skill : project) {
                if(!skill.equals(skillMinCard)) {
                	
                	// restartlist is updated to reflect updated team  
                    List<Integer> restartList = new ArrayList<Integer>();
                    for(String coveredSkill : team.keySet()) {
                        restartList.add(team.get(coveredSkill));  // get the members that cover  skills
                    }

                    //scoreVector stores the importance of each node (all nodes for the team mmebers)
                    DoubleMatrix2D scoreVector = RandomRandomWalkRestart(restartList, M, beta, RWR_RunNum);

                    finalScoreVector = scoreVector;
                    
                    double max_expert_score = -1;
                    int selected_expert = -1;
                    
                    // check all expert have the skill and check their score 
                    for(int expertId : skillExpertMap.get(skill)  ) {
                        double expert_score = scoreVector.get(expertId, 0);  // get the score of expert have the "skill"
                        if(expert_score > max_expert_score) {
                        	
                            max_expert_score = expert_score;
                            selected_expert = expertId;
                        }
                    }
                    
                    team.put(skill, selected_expert);
                    score += max_expert_score;
                }
            }

            if(score > maxScore) {
                maxScore = score;
                bestTeam = team;
            }
        }
        return bestTeam;
    }

   //------------------------------------------------------------------------------------------------------------
    public Map<String, Integer> buildRWRTeam(
            Set<String> project, Map<String, Set<Integer>> skillExpertMap, DoubleMatrix2D M, double beta, int RWR_RunNum, Set<Integer> usedIds){
    	//what is M used for?
        //Find the skill with minimum cardinality in the project (skillMinCard)
        String skillMinCard = null;
        for (String skill : project){
            if (skillMinCard == null ){
                skillMinCard = skill;
            }
            if(skillExpertMap.get(skill).size() < skillExpertMap.get(skillMinCard).size()){
                skillMinCard=skill;
            }
        }

        
        double maxScore = Double.MIN_VALUE;
        Map<String, Integer> bestTeam = null;

        // start team with experts who have skillMinCard as a skill
        for(int micCardExpertId : skillExpertMap.get(skillMinCard)) {

            Map<String, Integer> team = new HashMap<String, Integer>();
            double score = 0;
            
            // initialize team with an expert with minimum skill   
            team.put(skillMinCard, micCardExpertId);

            // go and find the other skills in the project
            for(String skill : project) {
                if(!skill.equals(skillMinCard)) {
                	
                	// restartlist is updated to reflect updated team  
                    List<Integer> restartList = new ArrayList<Integer>();
                    for(String coveredSkill : team.keySet()) {
                        restartList.add(team.get(coveredSkill));  // get the members that cover  skills
                    }

                    //scoreVector stores the importance of each node (all nodes for the team mmebers)
                    DoubleMatrix2D scoreVector = RandomRandomWalkRestart(restartList, M, beta, RWR_RunNum);

                    finalScoreVector = scoreVector;
                    
                    double max_expert_score = -1;
                    int selected_expert = -1;
                    
                    // check all expert have the skill and check their score 
                    for(int expertId : skillExpertMap.get(skill)  ) {
                        double expert_score = scoreVector.get(expertId, 0);  // get the score of expert have the "skill"
                        if((expert_score > max_expert_score) && (usedIds.contains(expertId) == false)) {
                        	
                            max_expert_score = expert_score;
                            selected_expert = expertId;
                        }
                    } 
                    team.put(skill, selected_expert);
                    score += max_expert_score;
                }
            }

            if(score > maxScore) {
                maxScore = score;
                bestTeam = team;
            }
        }
        return bestTeam;
    }


    public Map<String, Integer> buildFASTRWRTeam(
            Set<String> project, Map<String, Set<Integer>> skillExpertMap, DoubleMatrix2D M, double beta, int RWR_RunNum){
        //Find the skill with minimum cardinality
        String skillMinCard = null;
        for (String skill : project){
            if (skillMinCard == null ){
                skillMinCard = skill;
            }
            if(skillExpertMap.get(skill).size() < skillExpertMap.get(skillMinCard).size()){
                skillMinCard=skill;
            }
        }

        double maxScore = Double.MIN_VALUE;
        Map<String, Integer> bestTeam = null;

        for(int micCardExpertId : skillExpertMap.get(skillMinCard)) {

            Map<String, Integer> team = new HashMap<String, Integer>();
            double score = 0;

            team.put(skillMinCard, micCardExpertId);

            ArrayList<Integer> restartList = new ArrayList<Integer>();
            restartList.add(micCardExpertId);

            //scoreVector stores the importance of each node
            DoubleMatrix2D scoreVector = RandomRandomWalkRestart(restartList, M, beta, RWR_RunNum);

            finalScoreVector = scoreVector;
            
            for(String skill : project) {
                if(!skill.equals(skillMinCard)) {

                    double max_expert_score = -1;
                    int selected_expert = -1;

                    for(int expertId : skillExpertMap.get(skill)  ) {
                        double expert_score = scoreVector.get(expertId, 0);
                        if(expert_score > max_expert_score) {
                            max_expert_score = expert_score;
                            selected_expert = expertId;
                        }
                    }

                    team.put(skill, selected_expert);
                    score += max_expert_score;
                }
            }

            if(score > maxScore) {
                maxScore = score;
                bestTeam = team;
            }
        }
        return bestTeam;
    }

    public Map<String, Integer> buildFASTRWRTeam(
            Set<String> project, Map<String, Set<Integer>> skillExpertMap, DoubleMatrix2D M, double beta, int RWR_RunNum, Set<Integer> usedIds){
        //Find the skill with minimum cardinality
        String skillMinCard = null;
        for (String skill : project){
            if (skillMinCard == null ){
                skillMinCard = skill;
            }
            if(skillExpertMap.get(skill).size() < skillExpertMap.get(skillMinCard).size()){
                skillMinCard=skill;
            }
        }

        double maxScore = Double.MIN_VALUE;
        Map<String, Integer> bestTeam = null;

        for(int micCardExpertId : skillExpertMap.get(skillMinCard)) {

            Map<String, Integer> team = new HashMap<String, Integer>();
            double score = 0;

            team.put(skillMinCard, micCardExpertId);

            ArrayList<Integer> restartList = new ArrayList<Integer>();
            restartList.add(micCardExpertId);

            //scoreVector stores the importance of each node
            DoubleMatrix2D scoreVector = RandomRandomWalkRestart(restartList, M, beta, RWR_RunNum);

            finalScoreVector = scoreVector;
            
            for(String skill : project) {
                if(!skill.equals(skillMinCard)) {

                    double max_expert_score = -1;
                    int selected_expert = -1;

                    for(int expertId : skillExpertMap.get(skill)  ) {
                        double expert_score = scoreVector.get(expertId, 0);
                        if((expert_score > max_expert_score) && (usedIds.contains(expertId) == false)) {
                            max_expert_score = expert_score;
                            selected_expert = expertId;
                        }
                    }

                    team.put(skill, selected_expert);
                    score += max_expert_score;
                }
            }

            if(score > maxScore) {
                maxScore = score;
                bestTeam = team;
            }
        }
        return bestTeam;
    }
}
