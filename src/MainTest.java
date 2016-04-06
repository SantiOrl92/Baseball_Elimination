import javax.swing.JFrame;

public class MainTest extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[]args)
	{
		BaseballElimination baseball=new BaseballElimination();
		String fileName="Sample_1.txt";
		System.out.println("==================TABLE BASEBALL DIVISION==================");
		System.out.println("TEAM\t WINS\t LEFT\t\t LEFT MATCH(i Vs j)");

		baseball.generateTable(fileName);
		FlowNetwork network=baseball.generateFlowNetwork();
		System.out.println();
		System.out.println("===================PROBLEM OF FLOWNETWOK===================");
		System.out.println(network.toString());
		FordFulkerson maxFlow=new FordFulkerson(network,0,network.getV()-1);
		//System.out.println(baseball.checkElimination(maxFlow.getFlow(), network));
		FlowEdge e=baseball.checkEliminationCondition( network);
		if(e==null)
		{			
			System.out.println("Team "+baseball.teams[baseball.teamsNum-1]+" is not mathematically eliminated");
			System.out.println("because we can satisfy all the winning conditions between other teams");
		}
		else
		{
			System.out.println("Team "+baseball.teams[baseball.teamsNum-1]+" is mathematically eliminated");
			System.out.println("because the winning condition between, the match in "+e.toString()+" is not satisfy");
		}
			
		System.out.println("===================FINAL FLOWNETWOK===================");
		System.out.println(network.toString());
		GraphDraw frame = new GraphDraw("Test Window");
		frame.setSize(800,800);
		
		baseball.draw(frame);
		frame.setVisible(true);
	}
}
