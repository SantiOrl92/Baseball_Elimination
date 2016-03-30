
public class ProblemGenerator 
{	
	public int howManyVertexMatch(int [][]scontro,int indexTarg)
	{
		int numVertex=0;
		for(int i=0;i<scontro.length;i++)
		{
			if(i!=indexTarg)
			{
				for(int j=i+1;j<scontro[i].length;j++)
				{
					if(j!=indexTarg && scontro[i][j]>0)
						numVertex++;
				}
			}
		}
		return numVertex;
	}
	
	public String[] indexesOfMatch(int [][]scontro,int indexTarg,int nVertexMatch)
	{
		String indexes[]=new String [nVertexMatch];
		int k=0;
		for(int i=0;i<scontro.length;i++)
		{
			if(i!=indexTarg)
			{
				for(int j=i+1;j<scontro[i].length;j++)
				{
					if(j!=indexTarg  && scontro[i][j]>0)
					{
						indexes[k]=""+i+"-"+j;
						//System.out.println(""+i+"-"+j);
						k++;
					}
				}
			}
		}
		return indexes;
	}
	
	public int[] vittorieNS(int []vitt,int target,int wMax)
	{
		int []u=new int[vitt.length-1];
		for(int i=0,j=0;i<vitt.length;i++)
		{
			if(i!=target)
			{
				u[j]=wMax-vitt[i];
				System.out.println(u[j]);
				j++;
			}
		}
		return u;
	}
}
