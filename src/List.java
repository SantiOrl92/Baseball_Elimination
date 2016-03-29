
public class List {
	
	public Node head,tail;
	
	public List() 
	{
		this(null);
	}
	
	public List(Node e)
	{
		head=tail=e;
	}
	
	public void add(Node e)
	{
		if(head==null)
			head=tail=e;
		else
			tail=tail.next=e;
	}

}
