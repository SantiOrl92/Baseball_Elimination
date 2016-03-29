
public class FlowEdge 
{
	public int from;
	public int to;
	public int capacity;
	public int flow;
	
	public FlowEdge(int v,int w,int c,int fl)
	{
		if(validateVertex(v,w))
		{
			if(validateFlow(c,fl))
			{
				from=v;
				to=w;
				capacity=c;
				flow=fl;
			}
		}
	}
	
	public FlowEdge(FlowEdge e) 
	{
		if(validateVertex(e.from,e.to))
			if(validateFlow(e.capacity,e.flow))
			{
				from=e.from;
				to=e.to;
				capacity=e.capacity;
				flow=e.flow;
			}
	}
	
	public int other(int v)
	{
		if(v==from)	return to;
		if(v==to)	return from;
		else throw new IllegalArgumentException("Indice errato!");
	}
	
	public int residualCapacityTo(int v)
	{
		//if(v==from) return flow;
		if(v==to)	return capacity - flow;
		else throw new IllegalArgumentException("Indice errato!");
	}
	
	public void addResidualFlowTo(int v,int delta)
	{
		//if(v==from) flow-=delta;
		if(v==to)	flow+=delta;
		else throw new IllegalArgumentException("Indice errato!");
		if(!(flow<=capacity))	throw new IllegalArgumentException("Flusso supera la capacità");

	}
	
	public String toString()
	{
		return "Edge from "+from+" to "+to+" with flow "+flow+"/"+capacity;
	}
	private boolean validateVertex(int v, int w)
	{
		if(v<0)	throw new IndexOutOfBoundsException("indice di vertice negativo!");
		if(w<0)	throw new IndexOutOfBoundsException("indice di vertice negativo!");
		return true;
	}
	
	private boolean validateFlow(int cap,int flo)
	{
		if(!(cap>=0))  throw new IllegalArgumentException("la capacità deve essere non negativa!");
		if(!(flo<=cap)) throw new IllegalArgumentException("il flusso supera la capacità dell'arco!");
		if(!(flow>=0)) throw new IllegalArgumentException("il flusso deve essere non negativo!");
		return true;
	}

}
