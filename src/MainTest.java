import javax.swing.JFrame;

public class MainTest extends JFrame
{
	public static void main(String[]args)
	{
		BaseballElimination baseball=new BaseballElimination();
		String fileName="Sample_3.txt";
		System.out.println("==================TABLE BASEBALL DIVISION==================");
		System.out.println("TEAM\t WINS\t LEFT\t\t LEFT MATCH(i Vs j)");

		baseball.generateTable(fileName);
		boolean flag=true;
		while(flag)
		{
			FlowNetwork network=baseball.generateFlowNetwork();
			System.out.println();
			System.out.println("===================PROBLEM OF FLOWNETWOK===================");
			System.out.println(network.toString());
			FordFulkerson maxFlow=new FordFulkerson(network,0,network.getV()-1);
			FlowEdge e=baseball.checkEliminationCondition( network);
			if(e==null)
			{			
				System.out.println("Team "+baseball.teams[baseball.teamsNum-1]+" is not mathematically eliminated");
				System.out.println("because we can satisfy all the winning conditions between other teams");
				flag=true;
			}
			else
			{
				System.out.println("Team "+baseball.teams[baseball.teamsNum-1]+" is mathematically eliminated");
				System.out.println("because the winning condition between, the teams in the match "+e.toString()+" is not satisfy");
				flag=false;
			}
			System.out.println();	
			System.out.println("===================FINAL FLOWNETWOK===================");
			System.out.println(network.toString());
			GraphDraw frame = new GraphDraw("Test Window");
			frame.setSize(800,800);
			
			baseball.draw(frame);
		    frame.setLocationRelativeTo(null);
			frame.setResizable(false);
			frame.setVisible(true);
			
			baseball.simulateMatch();
			
		}
	}
}
