public class FlowNetwork 
{
	private int V;
	private int E;
	public List [] adj;
	
	public FlowNetwork(int V) 
	{
		if(V<=0)
			throw new IllegalArgumentException("Numero di Vertici deve essere positivo!");
		else
		{
			this.V=V;
			this.E=0;
			adj=new List[V];
			for(int i=0;i<V;i++)
				adj[i]=new List();
		}
	}
	
	public void addEdge(Node e)
	{
		int v=e.from;
		int w=e.to;
		validateVertex(v);
		validateVertex(w);
		adj[v].add(e);
		E++;
	}
	
	public int getV()
	{
		return V;
	}
	
	public int getE()
	{
		return E;
	}
	
	private void validateVertex(int v) 
	{
		if (v < 0 || v >= V)
			throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
	}
}
