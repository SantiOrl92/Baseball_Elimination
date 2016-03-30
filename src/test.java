
public class test {

	public static void main(String[] args) 
	{
		FlowNetwork rete=new FlowNetwork(4);
		int s=0;
		rete.addEdge(new FlowEdge(s,1,1,0));
		rete.addEdge(new FlowEdge(s,2,6,0));
		rete.addEdge(new FlowEdge(s,3,1,0));
		System.out.println(rete.adj[0].head);
		System.out.println(rete.adj[0].head.next);
		System.out.println(rete.adj[0].head.next.next);
	}

}
