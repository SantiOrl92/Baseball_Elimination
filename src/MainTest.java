import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;


public class MainTest extends JFrame
{
	public static void main(String[]args)
	{
		JFileChooser chooser=new JFileChooser();
		chooser.showOpenDialog(null);
		File file=chooser.getSelectedFile();
		JTextArea log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
		JFrame frameLog=new JFrame();
		frameLog.setSize(800,600);
		frameLog.add(logScrollPane);
		frameLog.setLocation(0, 0);;
		frameLog.setResizable(true);
		frameLog.setVisible(true);
		if(file!=null)
		{
			try
			{
				log.append("==================TABLE BASEBALL DIVISION=================="+"\n");
				log.append("TEAM\t WINS\t LEFT\t\t LEFT MATCH(i Vs j)"+"\n");
				//System.out.println("==================TABLE BASEBALL DIVISION==================");
				//System.out.println("TEAM\t WINS\t LEFT\t\t LEFT MATCH(i Vs j)");
				
				BaseballElimination baseball=new BaseballElimination();
				baseball.generateTable(file.getPath());
				log.append(baseball.printTable()+"\n");
				FlowNetwork network=baseball.generateFlowNetwork();
				
				log.append("===================PROBLEM OF FLOWNETWOK==================="+"\n");
				log.append(network.toString()+"\n");
				FordFulkerson maxFlow=new FordFulkerson(network,0,network.getV()-1);
				FlowEdge e=baseball.checkEliminationCondition( network);
				if(e==null)
				{			
					log.append("Team "+baseball.teams[baseball.teamsNum-1]+" is not mathematically eliminated"+"\n");
					log.append("because we can satisfy all the winning conditions between other teams"+"\n");
				}
				else
				{
					log.append("Team "+baseball.teams[baseball.teamsNum-1]+" is mathematically eliminated"+"\n");
					log.append("because the winning condition between, the teams in the match "+e.toString()+" is not satisfy"+"\n");
				}
				
				log.append("===================FINAL FLOWNETWOK==================="+"\n");
				log.append(network.toString()+"\n");
				
				//BorderLayout layout=new BorderLayout();
				
				GraphDraw frame = new GraphDraw("Test Window");
		        
				frame.setSize(800,800);
				baseball.draw(frame);
			    frame.setLocationRelativeTo(null);
				frame.setResizable(false);
				frame.setVisible(true);
	
			}
			catch(Exception e)
			{
				log.append(e.toString());
			}
        }
	}
}
