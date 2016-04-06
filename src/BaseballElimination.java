import java.io.BufferedReader;
import java.io.FileReader;

public class BaseballElimination 
{
	public String [] teams;
	public String[] labelNode;
	public int teamsNum;
	public int []wins;
	public int []left;
	public int[][] match;
	private Utility generator=new Utility();
	private int V;
	private int matchVertex;
	private FlowNetwork network;
	
	public void generateTable(String fileName)
	{
		BufferedReader buffer;
		try
		{
			buffer=new BufferedReader(new FileReader(fileName));
			
			String line=buffer.readLine();
			
			teamsNum=Integer.parseInt(line);
			
			teams=new String [teamsNum];
			wins=new int[teamsNum];
			left=new int[teamsNum];
			match=new int [teamsNum][teamsNum];
			
			int k=0;
			while((line=buffer.readLine()) != null)
			{
				splitAndPut(line,"_",teams,wins,left,match,k);
				k++;
			}
			
			buffer.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getStackTrace());
		}
	}
	
	public FlowNetwork generateFlowNetwork()
	{
		int inquireTeam=teamsNum-1;
		int wMax=wins[inquireTeam]+left[inquireTeam];
		//System.out.println(wMax);
		int []u=generator.vittorieNS(wins, inquireTeam, wMax);
		matchVertex=generator.howManyVertexMatch(match,inquireTeam);
		int teamVertex=teamsNum-1;
		//aggiungere i nodi squadra
		V=matchVertex+teamVertex+2;
		labelNode=new String[V];
		
		network=new FlowNetwork(matchVertex+teamVertex+2);
		
		int s=0;
		int t=V-1;
		
		setLabel(V);
		String []indexes=generator.indexesOfMatch(match,inquireTeam,matchVertex);
		//aggiungere i nodi partita
		int gameindex=1;
		for(int k=0;k<indexes.length;k++,gameindex++)
		{
			int i=Integer.parseInt(""+indexes[k].charAt(0));
			int j=Integer.parseInt(""+indexes[k].charAt(2));
			
			FlowEdge e=new FlowEdge(s,gameindex,match[i][j],0);
			network.addEdge(e);
			e=new FlowEdge(gameindex,((i+matchVertex)%matchVertex)+matchVertex+1,100,0);
			network.addEdge(e);
			e=new FlowEdge(gameindex,((j+matchVertex)%matchVertex)+matchVertex+1,100,0);
			network.addEdge(e);
		}
		
		for(int i=0;i<teamsNum-1;i++)
		{
			FlowEdge e=new FlowEdge(i+matchVertex+1,t,u[i],0);
			network.addEdge(e);
		}
		return network;
	}
	
	public Node checkEliminationCondition(FlowNetwork network)
	{
		Node e;
		for(e=network.adj[0].head;e!=null;e=e.next)
			if(e.flow<e.capacity)
				break;
		return e;
	}
	
	public void splitAndPut(String str, String regex,String []squadra,
			int []vitt,int []rest,int[][] scontro,int k)
	{
		String []aux=str.split(regex);
		
		squadra[k]=aux[0];
		System.out.print(squadra[k]+"\t");
		
		vitt[k]=Integer.parseInt(aux[1]);
		System.out.print(vitt[k]+"\t");
		
		rest[k]=Integer.parseInt(aux[2]);
		System.out.print(rest[k]+"\t");
		
		for(int i=0,j=3;i<scontro[k].length && j<aux.length;i++,j++)
		{
			scontro[k][i]=Integer.parseInt(aux[j]);
			System.out.print(scontro[k][i]+"\t");
		}
		System.out.println();
	}
	
	public void setLabel(int V)
	{
		labelNode[0]="S";
		labelNode[V-1]="T";
		
		
		for(int i=V-2,k=teams.length-2;k>=0;i--,k--)
		{
			labelNode[i]=""+teams[k].substring(0, 3);
			//System.out.println("Node "+i+" "+teams[k]);
		}
		
		int inquireTeam=teamsNum-1;
		int matchVertex=generator.howManyVertexMatch(match,inquireTeam);
		String []indexes=generator.indexesOfMatch(match,inquireTeam,matchVertex);
		
		for(int i=0,k=1;i<matchVertex;i++,k++)
		{
			int v=Integer.parseInt(""+indexes[i].charAt(0));
			int w=Integer.parseInt(""+indexes[i].charAt(2));
			labelNode[k]=labelNode[((v+matchVertex)%matchVertex)+matchVertex+1]+""
									+ ", "+""
									+labelNode[((w+matchVertex)%matchVertex)+matchVertex+1];
			//System.out.println("Node "+k+""
			//		+ " "+labelNode[((v+matchVertex)%matchVertex)+matchVertex+1]+""
			//				+ ", "+""
			//				+labelNode[((w+matchVertex)%matchVertex)+matchVertex+1]);
		}


	}
	
	public void draw(GraphDraw frame)
	{
		int y=frame.getHeight();
		int x=frame.getWidth();
		frame.addNode(labelNode[0],50,y/2);
		
		for(int i=0,k=1;i<matchVertex;i++,k++)
		{
			frame.addNode(labelNode[k],x/3,k*120);
			
			System.out.println( x/3+" "+k*200);
		}
		
		for(int i=0,k=matchVertex+1;k<V-1;i++,k++)
		{
			frame.addNode(labelNode[k], x-(x/3), (i+1)*150);
		}
		
		frame.addNode(labelNode[V-1], x-50,y/2);
		
		//frame.addEdge(1, 6, "ciao mbare");
		
		//String []indexes=generator.indexesOfMatch(match,teamsNum-1,matchVertex);
		for(int i=0;i<network.adj.length;i++)
		{
			for(Node e=network.adj[i].head;e!=null;e=e.next)
			{
				frame.addEdge(i, e.other(i), ""+e.flow+"/ "+e.capacity);
			}
		}
		
	}
}