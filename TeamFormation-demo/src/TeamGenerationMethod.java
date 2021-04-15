import java.util.Map;
import java.util.Set;

import cern.colt.matrix.DoubleMatrix2D;

class TeamGenerationMethod {
	public DoubleMatrix2D finalScoreVector;

	//welcome to placeholder hell
	//nothing here isn't a placeholder
	public Map<String, Integer> buildShortestPathTeam(Set<String> project, Map<String, Set<Integer>> skillExpertMap,
			Map<Integer, TwoHop[]> twoHopMap) {
		return null;
	}
	public Map<String, Integer> buildShortestPathTeam(Set<String> project, Map<String, Set<Integer>> skillExpertMap,
			Map<Integer, TwoHop[]> twoHopMap, Set<Integer> usedIds) {
		return null;
	}

	public Map<String, Integer> buildRWRTeam(Set<String> project, Map<String, Set<Integer>> skillExpertMap,
			DoubleMatrix2D m, double beta, int rWR_RunNum) {
		return null;
	}
	public Map<String, Integer> buildRWRTeam(Set<String> project, Map<String, Set<Integer>> skillExpertMap,
			DoubleMatrix2D m, double beta, int rWR_RunNum, Set<Integer> usedIds) {
		return null;
	}

	public Map<String, Integer> buildFASTRWRTeam(Set<String> project, Map<String, Set<Integer>> skillExpertMap,
			DoubleMatrix2D m, double beta, int rWR_RunNum) {
		return null;
	}
	public Map<String, Integer> buildFASTRWRTeam(Set<String> project, Map<String, Set<Integer>> skillExpertMap,
			DoubleMatrix2D m, double beta, int rWR_RunNum, Set<Integer> usedIds) {
		return null;
	}

	public Map<String, Integer> buildEMBTeam(Set<String> project, Map<String, Set<Integer>> skillExpertMap,
			GraphEmbedding gem) {
		return null;
	}
	public Map<String, Integer> buildEMBTeam(Set<String> project, Map<String, Set<Integer>> skillExpertMap,
			GraphEmbedding gem, Set<Integer> usedIds) {
		return null;
	}
	




}