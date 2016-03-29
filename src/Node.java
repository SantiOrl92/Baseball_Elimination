
public class Node extends FlowEdge
	{
		public Node next;
		
		public Node(FlowEdge e)
		{
			this(e,null);
		}
		
		public Node(FlowEdge e,Node n) {
			super(e);
			next=n;
		}
	}