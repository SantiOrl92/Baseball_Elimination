import java.util.LinkedList;

public class Queue<E> {
	private LinkedList<E> list=new LinkedList<E>();
	
	public void enqueue(E item)
	{
		list.add(item);
	}
	
	public E dequeue()
	{
		return list.poll();
	}
	
	public boolean isEmpty()
	{
		return list.isEmpty();
	}
	
	public void addItems(Queue<? extends E> q)
	{
		while(!q.isEmpty())
		{
			list.addLast(q.dequeue());
		}
	}

}
