
public class FordFulkerson 
{
	private boolean[] marked;	//true if s->v € residual network
	private FlowEdge[] edgeTo;	//edgeTo[v], is the edge 
								//to reach v from s, in the path
	private int flow;			//flow in the network
	
	public FordFulkerson(FlowNetwork network, int s,int t)
	{
		validate(s,t,network.getV());
		int value=0; //flow global var
		while(hasAugmentingPath(network,s,t))
		{
			//search the min value in the path
			
			//increment flow for each edge in the 
			//augmenting path
			
		}
	}
	
	public boolean hasAugmentingPath(FlowNetwork network, int s,int t)
	{
		edgeTo=new FlowEdge[network.getV()];
		marked=new boolean[network.getV()];
		
		Queue<Integer> queue=new Queue<Integer>();
		queue.enqueue(s);
		marked[s]=true;
		while(!queue.isEmpty() && !marked[t])
		{
			int v=queue.dequeue();
			
			for(Node e=network.adj[v].head; e!=null; e=e.next )
			{
				int w=e.other(v);
				
				if(e.residualCapacityTo(w)>0)
				{
					if(!marked[w])
					{
						marked[w]=true;
						edgeTo[w]=e;
						queue.enqueue(w);
					}
				}
			}
		}
			
		return  marked[t];
	}
	
	public boolean validate(int s, int t,int V)
	{
		if(V>0 && (s>=0 && s<=V-1) && (t>=0 && t<=V-1))
			return true;
		throw new IllegalArgumentException("Illegal number of vertex, s and t must be between 0 and "+(V-1));
	}

}
