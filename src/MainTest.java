
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
		System.out.println("=================FLOWNETWOK OF THE PROBLEM=================");
		System.out.println(network.toString());
	}

}
