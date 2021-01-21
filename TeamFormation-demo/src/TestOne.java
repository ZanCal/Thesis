
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cern.colt.matrix.DoubleMatrix2D;

/**
 * Created by karga on 1/21/2019.
 */

//I really don't think TestOne was supposed to be the final name for this class
public  class TestOne {

    public static double MaxCalDouble = 100000; //what?

    public List<Integer> weightedVariant(Map<Integer, TwoHop[]> twoHopMap, int sId, int tId, List<Integer> currentList, DoubleMatrix2D finalScoreVector, int prevSId,int prevTId){
    	//so what i'm gonna try and do is have this be a copy of getFullPathTwoHopArray that takes an extra argument for scores which then get
    	//converted to weights and then are accommodated for when generating a path. HERE is where i Dijkstra this bitch
    	//should I be doing it here?
    	
    	//eh, I don't think i'm going to use this at all
    	return currentList;
    }
    
    public List<Integer> getFullPathTwoHopArray(Map<Integer, TwoHop[]> twoHopMap, int sId, int tId, List<Integer> currentList, int prevSId,int prevTId){
    	//oh no it's recursive :(
    	
    	//what's S supposed to be versus T? 
    	//is S start? Starting ID?
    	//What algorithm is this supposed to implement? 
    	
        if(prevSId == sId && prevTId == tId){
            //what case is this covering?
        	return currentList;
        }

        if(currentList == null){
        	//looks like first iteration case
        	//add sID, and add tID if it's different
            currentList = new ArrayList<Integer>();
            currentList.add(sId);
            if(sId != tId)
                currentList.add(tId);
        }

        int[] connectorParentArray = pathTwoHopArrayNew(twoHopMap, sId, tId);

        int connectorNodeId = connectorParentArray[0];
        int parentIdS = connectorParentArray[1];
        int parentIdT = connectorParentArray[2];


        //build the s path
        if(connectorNodeId == sId){
            //do nothing and return.
        }else{
            if(parentIdS == sId){
                //only insert connectorNodeId after sId
                if(!currentList.contains(connectorNodeId))
                    currentList = insertOneElementAfterGivenOne(currentList, sId, connectorNodeId);

                //call between sId(==parentIdS) and connectorNodeId
                currentList = getFullPathTwoHopArray(twoHopMap, sId, connectorNodeId, currentList, sId, tId);
            }else{
                //first, insert parentIdS after sId
                if(!currentList.contains(parentIdS))
                    currentList = insertOneElementAfterGivenOne(currentList, sId, parentIdS);

                //second, insert connectorNodeId after parentIdS
                if(!currentList.contains(connectorNodeId))
                    currentList = insertOneElementAfterGivenOne(currentList, parentIdS, connectorNodeId);

                //third, call method between parentIdS and connectorNodeId
                currentList = getFullPathTwoHopArray(twoHopMap, parentIdS, connectorNodeId, currentList, sId, tId);
            }
        }

        //build the t path
        if(connectorNodeId == tId){
            //do nothing and return.
        }else{
            if(parentIdT == tId){

                //only insert connectorNodeId before tId
                if(!currentList.contains(connectorNodeId))
                    currentList = insertOneElementBeforeGivenOne(currentList, tId, connectorNodeId);

                //call between connectorNodeId and tId(==parentIdT)
                currentList = getFullPathTwoHopArray(twoHopMap,  connectorNodeId, tId, currentList, sId, tId);
            }else{
                //first, insert parentIdT before tId
                if(!currentList.contains(parentIdT))
                    currentList = insertOneElementBeforeGivenOne(currentList, tId, parentIdT);

                //second, insert connectorNodeId before parentIdT
                if(!currentList.contains(connectorNodeId))
                    currentList = insertOneElementBeforeGivenOne(currentList, parentIdT, connectorNodeId);

                //third, call method between connectorNodeId and parentIdT
                currentList = getFullPathTwoHopArray(twoHopMap, connectorNodeId, parentIdT, currentList, sId, tId);
            }
        }

        //this is the last line
        return currentList;
        //and that comment helps me how? C'mon 
    }

    public int[] pathTwoHopArrayNew(Map<Integer, TwoHop[]> twoHopMap, int sId, int tId){
    	//where and how is this function used?

        double resultDist = MaxCalDouble;
        int connectorNodeId = -1;
        int parentIdS = -1;
        int parentIdT = -1;

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

                if(currentDistTotal < resultDist){
                    resultDist = currentDistTotal;
                    connectorNodeId = nodeSId;

                    parentIdS = sList[si].parentId;
                    parentIdT = tList[ti].parentId;
                }
                si++; ti++;
            }
        }

        //the first element is nodeId, the second element is parentId
        int[] result = new int[3];
        result[0] = connectorNodeId;
        result[1] = parentIdS;
        result[2] = parentIdT;

        return result;
    }

    public List<Integer> insertOneElementBeforeGivenOne(List<Integer> inputList, int insertBeforeElement, int newElement){
    	//nice function names, now if only I knew what it was supposed to do
        int newIndex = -1;
        for(int i=0; i<inputList.size(); i ++){
            if(inputList.get(i) == insertBeforeElement){
                newIndex = i;
            }
        }

        List<Integer> outputList = new ArrayList<Integer>();
        for(int i=0; i<newIndex; i ++){
            outputList.add(inputList.get(i));
        }

        outputList.add(newElement);

        for(int i=newIndex; i<inputList.size(); i ++){
            outputList.add(inputList.get(i));
        }

        return outputList;
    }

    public List<Integer> insertOneElementAfterGivenOne(List<Integer> inputList, int insertAfterElement, int newElement){

        int newIndex = -1;
        for(int i=0; i<inputList.size(); i ++){
            if(inputList.get(i) == insertAfterElement){
                newIndex = i;
            }
        }

        List<Integer> outputList = new ArrayList<Integer>();
        for(int i=0; i<newIndex+1; i ++){
            outputList.add(inputList.get(i));
        }

        outputList.add(newElement);

        for(int i=newIndex+1; i<inputList.size(); i ++){
            outputList.add(inputList.get(i));
        }

        return outputList;
    }


}
