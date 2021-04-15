//package ca.uwindsor.cs.mkargar.graphsearch.WWW;

/**
 * Created by karga on 12/2/2017.
 */
public class TwoHop implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Integer nodeId;
    public double dist;
    public Integer parentId;

    public TwoHop(int nodeId, double dist, int parentId){
        this.nodeId = nodeId;
        this.dist = dist;
        this.parentId = parentId;
    }

}
