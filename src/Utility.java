
public class Utility 
{	
	public int sum=0;
	public int howManyVertexMatch(int [][]scontro,int indexTarg)
	{
		int numVertex=0;
		for(int i=0;i<scontro.length-1;i++)
		{
			for(int j=i+1;j<scontro[i].length-1;j++)
			{
				if(j!=indexTarg && scontro[i][j]>0)
				{
					numVertex++;
				}
			}
		}
		return numVertex;
	}
	
	public String[] indexesOfMatch(int [][]scontro,int indexTarg,int nVertexMatch)
	{
		String indexes[]=new String [nVertexMatch];
		for(int i=0,k=0;i<scontro.length-1;i++)
		{
			for(int j=i+1;j<scontro[i].length-1;j++)
			{
				if(scontro[i][j]>0)
				{
					indexes[k]=""+i+"-"+j;
					System.out.println(indexes[k]);
					sum+=scontro[i][j];
					k++;
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
				j++;
			}
		}
		return u;
	}
}
