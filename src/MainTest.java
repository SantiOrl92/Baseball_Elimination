
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
	}

}
