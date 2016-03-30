
public class ProblemGenerator 
{
	public int getMin(int []vitt)
	{
		int min=vitt[0];
		int index=0;
		for(int i=1;i<vitt.length;i++)
		{
			if(vitt[i]<min)
			{
				min=vitt[i];
				index=i;
			}
		}
		return index;
	}
	
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
						System.out.println(""+i+"-"+j);
						k++;
					}
				}
			}
		}
		return indexes;
	}
}
