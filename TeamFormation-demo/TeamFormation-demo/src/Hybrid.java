//package ca.uwindsor.cs.mkargar.graphsearch.WWW;

import cern.colt.matrix.DoubleMatrix2D;

import java.util.*;

/**
 * Created by karga on 12/2/2017.
 */
public class Hybrid {

    public Map<String, Integer> buildHybridTeam(
            Set<String> project, Map<String, Set<Integer>> skillExpertMap,
            Map<Integer, TwoHop[]> twoHopMap, DoubleMatrix2D M,
            double beta, int RWR_RunNum, double lambda)
    {
        ShortestPath sp = new ShortestPath();
        RWR rwr = new RWR();

        //Get the skill with minimum cardinality
        String skillMinCard = null;
        for (String skill : project)
        {
            if (skillMinCard == null )
            {
                skillMinCard = skill;
            }
            if(skillExpertMap.get(skill).size() < skillExpertMap.get(skillMinCard).size())
            {
                skillMinCard=skill;
            }
        }

        double minHybridWeight = Double.MAX_VALUE;
        Map<String, Integer> bestTeam = null;

        for(int micCardExpertId : skillExpertMap.get(skillMinCard)) {

            Map<String, Integer> team = new HashMap<String, Integer>();
            double weight = 0;

            team.put(skillMinCard, micCardExpertId);

            for(String skill : project) {
                if(!skill.equals(skillMinCard)) {

                    ArrayList<Integer> restartList = new ArrayList<Integer>();
                    for(String coveredSkill : team.keySet()) {
                        restartList.add(team.get(coveredSkill));
                    }

                    //scoreVector stores the importance of each node
                    DoubleMatrix2D scoreVector = rwr.RandomRandomWalkRestart(restartList, M, beta, RWR_RunNum);

                    double min_expert_weight = Double.MAX_VALUE;
                    int selected_expert = -1;

                    for(int expertId : skillExpertMap.get(skill)  ) {
                        double expert_score_rwr = scoreVector.get(expertId, 0);
                        //RWR is a maximization problem since it maximizes the sum of the probabilities
                        //Shortest path is a minimization problem
                        //by subtracting one from the RWR score, we turn it into minimization
                        expert_score_rwr = 1 - expert_score_rwr; //maximization to minimization

                        //TODO: fix normalization
                        double expert_score_rwr_norm = expert_score_rwr*5;

                        double dist = sp.queryTwoHopArray(twoHopMap, expertId, micCardExpertId);

                        double expert_weight = (lambda*expert_score_rwr_norm) + ((1-lambda)*dist);

                        if(expert_weight < min_expert_weight) {
                            min_expert_weight = expert_weight;
                            selected_expert = expertId;
                        }
                    }

                    team.put(skill, selected_expert);
                    weight += min_expert_weight;

                }
            }

            if(weight < minHybridWeight) {
                minHybridWeight = weight;
                bestTeam = team;
            }

        }

        return bestTeam;
    }

}
