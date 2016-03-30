import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

public class BaseballElimination 
{
	
	public static void main(String[]args)
	{
		String []squadra;
		int []vitt;
		int []rest;
		int[][] scontro;
		BufferedReader buffer;
		ProblemGenerator generator=new ProblemGenerator();
		try
		{
			buffer=new BufferedReader(new FileReader("teams.txt"));
			
			String line=buffer.readLine();
			System.out.println(line);
			int numSquadre=Integer.parseInt(line);
			squadra=new String [numSquadre];
			vitt=new int[numSquadre];
			rest=new int[numSquadre];
			scontro=new int [numSquadre][numSquadre];
			int k=0;
			while((line=buffer.readLine()) != null)
			{
				splitAndPut(line,"_",squadra,vitt,rest,scontro,k);
				k++;
			}
			buffer.close();
			
			int indexTarget=numSquadre-1;
			int wMax=vitt[indexTarget]+rest[indexTarget];
			System.out.println(wMax);
			int []u=generator.vittorieNS(vitt, indexTarget, wMax);
			int matchVertex=generator.howManyVertexMatch(scontro,indexTarget);
			int teamVertex=numSquadre-1;
			int V=matchVertex+teamVertex+2;
			//System.out.println("number of vertex match: "+V);
			FlowNetwork rete=new FlowNetwork(matchVertex+teamVertex+2);
			
			int s=0;
			int t=V-1;
			
			String []indexes=generator.indexesOfMatch(scontro,indexTarget,matchVertex);
			
			int gameindex=1;
			for(k=0;k<indexes.length;k++,gameindex++)
			{
				int i=Integer.parseInt(""+indexes[k].charAt(0));
				int j=Integer.parseInt(""+indexes[k].charAt(2));
				FlowEdge e=new FlowEdge(s,gameindex,scontro[i][j],0);
				rete.addEdge(e);
				e=new FlowEdge(gameindex,((i+matchVertex)%matchVertex)+matchVertex+1,Integer.MAX_VALUE,0);
				rete.addEdge(e);
				e=new FlowEdge(gameindex,((j+matchVertex)%matchVertex)+matchVertex+1,Integer.MAX_VALUE,0);
				rete.addEdge(e);
			}
			
			for(k=0;k<numSquadre-1;k++)
			{
				FlowEdge e=new FlowEdge(k+4,t,u[k],0);
				rete.addEdge(e);
			}
			
			for(k=0;k<rete.adj.length;k++)
			{
				Node e=rete.adj[k].head;
				for(;e!=null;e=e.next)
				{
					System.out.println(e.toString());
				}
			}
		}
		catch(Exception e)
		{
			
		}
	}
	
	public static void splitAndPut(String str, String regex,String []squadra,
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
			//System.out.println(i);
			scontro[k][i]=Integer.parseInt(aux[j]);
			System.out.print(scontro[k][i]+"\t");
		}
		System.out.println();
	}
}