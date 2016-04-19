import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

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
	private String []indexes;
	private FlowNetwork network;
	private int simulate[];
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
		
		indexes=generator.indexesOfMatch(match,inquireTeam,matchVertex);
		
		setLabel(V);
		//aggiungere i nodi partita
		int gameindex=1;
		for(int k=0;k<indexes.length;k++,gameindex++)
		{
			int i=Integer.parseInt(""+indexes[k].charAt(0));
			int j=Integer.parseInt(""+indexes[k].charAt(2));
			
			FlowEdge e=new FlowEdge(s,gameindex,match[i][j],0);
			network.addEdge(e);
			e=new FlowEdge(gameindex,((i+matchVertex)%matchVertex)+matchVertex+1,generator.sum,0);
			network.addEdge(e);
			e=new FlowEdge(gameindex,((j+matchVertex)%matchVertex)+matchVertex+1,generator.sum,0);
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
			//System.out.println(labelNode[i]);
		}
		
		int inquireTeam=teamsNum-1;
		
		for(int i=0,k=1;i<matchVertex;i++,k++)
		{
			int v=Integer.parseInt(""+indexes[i].charAt(0));
			int w=Integer.parseInt(""+indexes[i].charAt(2));
			System.out.println(labelNode[((w+matchVertex)%matchVertex)+matchVertex-1]);
			labelNode[k]=labelNode[((v+matchVertex)%matchVertex)+matchVertex+1]+""
									+ ", "+""
									+labelNode[((w+matchVertex)%matchVertex)+matchVertex+1];
		}
	}
	
	public void draw(GraphDraw frame)
	{
		int y=frame.getHeight();
		int x=frame.getWidth();
		int offset=100;
		frame.addNode(labelNode[0],50,y/2);
		
		drawNode(matchVertex,offset,1,x/3,frame);
		
		int diff=(V-1)-(matchVertex+1);
		drawNode(diff,offset,matchVertex+1,x-(x/3),frame);
		
		frame.addNode(labelNode[V-1], x-50,y/2);
		
		for(int i=0;i<network.adj.length;i++)
		{
			for(Node e=network.adj[i].head;e!=null;e=e.next)
			{
				frame.addEdge(i, e.other(i), ""+e.flow+"/ "+e.capacity);
			}
		}
	}
	
	public void drawNode(int numNode,int offset,int index,int x,GraphDraw frame)
	{
		int y=frame.getHeight();
		
		if((numNode%2)==0)
		{
			for(int i=0, k=index;i<numNode;i++,k++)
			{
				if(i<numNode/2)
					frame.addNode(labelNode[k], x, ((y/2)-((i+1)*offset)));
				else
					frame.addNode(labelNode[k], x, ((y/2)+((i-1)*offset)));
			}
		}
		else
		{
			for(int i=0, k=index;i<numNode;i++,k++)
			{
				if(i<numNode/2)
				{
					frame.addNode(labelNode[k], x, ((y/2)-((i+1)*offset)));
				}
				else
				{
					if(i==numNode/2)
						frame.addNode(labelNode[k], x, (y/2));
					else
						frame.addNode(labelNode[k], x, ((y/2)+((i-1)*offset)));
				}
			}
		}
	}
	
	public void simulateHalfMatch()
	{
		int last=teams.length-1;
		int first=0;
		simulate=new int [2];
		simulate[0]=simulate[1]=left[last]/2;
		int k=0,i=0;
		while(k<simulate[1])
		{
			if(match[last][i]>0)
			{
				wins[last]++;
				left[i]--;
				left[last]--;
				match[last][i]--;
				match[i][last]--;
				if(i==first)
					simulate[0]--;
				k++;
			}
			i=(i+1)%last;
		}
		Random rand=new Random();
		k=0;
		i=1;
		while(k<simulate[0])
		{
			if(match[first][i]>0)
			{
				int esito=rand.nextInt(2);
				if(esito==0)
				{
					wins[i]++;
				}
				else
				{
					wins[first]++;
				}
				left[first]--;
				left[i]--;
				match[first][i]--;
				match[i][first]--;
				k++;
			}
			i=(i+1)%last;
		}
	}
	
	public void printTable()
	{
		for(int i=0;i<teamsNum;i++)
		{
			System.out.print(teams[i]+"\t");
			System.out.print(wins[i]+"\t");
			System.out.print(left[i]+"\t");
			for(int j=0;j<teamsNum;j++)
			{
				System.out.print(match[i][j]+"\t");
			}
			System.out.println();
		}
	}
	
	public boolean validate()
	{
		int wmax=wins[teamsNum-1]+left[teamsNum-1];
		for(int i=0;i<teamsNum-1;i++)
		{
			if(wins[i]+left[i]>wmax)
				return false;
		}
		return true;
	}
}