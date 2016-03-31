import java.io.BufferedReader;
import java.io.FileReader;

public class BaseballElimination 
{
	public String [] teams;
	public int teamsNum;
	public int []wins;
	public int []left;
	public int[][] match;
	
	private Utility generator=new Utility();
	
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
		int matchVertex=generator.howManyVertexMatch(match,inquireTeam);
		int teamVertex=teamsNum-1;
		int V=matchVertex+teamVertex+2;
		
		FlowNetwork network=new FlowNetwork(matchVertex+teamVertex+2);
		
		int s=0;
		int t=V-1;
		
		String []indexes=generator.indexesOfMatch(match,inquireTeam,matchVertex);
		
		int gameindex=1;
		for(int k=0;k<indexes.length;k++,gameindex++)
		{
			int i=Integer.parseInt(""+indexes[k].charAt(0));
			int j=Integer.parseInt(""+indexes[k].charAt(2));
			
			FlowEdge e=new FlowEdge(s,gameindex,match[i][j],0);
			network.addEdge(e);
			e=new FlowEdge(gameindex,((i+matchVertex)%matchVertex)+matchVertex+1,Integer.MAX_VALUE,0);
			network.addEdge(e);
			e=new FlowEdge(gameindex,((j+matchVertex)%matchVertex)+matchVertex+1,Integer.MAX_VALUE,0);
			network.addEdge(e);
		}
		
		for(int i=0;i<teamsNum-1;i++)
		{
			FlowEdge e=new FlowEdge(i+matchVertex+1,t,u[i],0);
			network.addEdge(e);
		}
		return network;
	}
	
	public boolean checkElimination()
	{
		return true;
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
}