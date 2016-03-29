import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

public class BaseballElimination 
{
	public static final int totMatch=100;
	public static final int randomMin=50;
	public static final int randomMax=80;
	
	public static void main(String[]args)
	{
		String []squadra;
		int []vitt;
		int []rest;
		int[][] scontro;
		BufferedReader buffer;
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
				squadra[k]=line;
				k++;
			}
			
			/*for(int i=0;i<squadra.length;i++)
				System.out.println("Squadra "+i+" : "+squadra[i]);*/
			Random rand=new Random();
			for(int i=0;i<vitt.length;i++)
			{
				vitt[i]=rand.nextInt(randomMax-randomMin)+randomMin;
				rest[i]=totMatch-vitt[i];
				System.out.println(vitt[i]+" - "+rest[i]);
				
			}
		}
		catch(Exception e)
		{
			
		}
	}

}