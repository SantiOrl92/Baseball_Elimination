
public class MainTest 
{
	public static void main(String[]args)
	{
		BaseballElimination baseball=new BaseballElimination();
		String fileName="teams.txt";
		System.out.println("==================TABLE BASEBALL DIVISION==================");
		baseball.generateTable(fileName);
		FlowNetwork network=baseball.generateFlowNetwork();
		System.out.println();
		System.out.println("===================PROBLEM OF FLOWNETWOK===================");
		System.out.println(network.toString());
		FordFulkerson maxFlow=new FordFulkerson(network,0,network.getV()-1);
		System.out.println(baseball.checkElimination(maxFlow.getFlow(), network));
		
	}

}
