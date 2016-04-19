import java.util.*;
import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;

class GraphDraw extends JFrame {
    
	int width;
    int height;

    ArrayList<Node> nodes;
    ArrayList<edge> edges;

    public GraphDraw() { //Constructor
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	nodes = new ArrayList<Node>();
	edges = new ArrayList<edge>();
	width = 30;
	height = 30;
    }

    public GraphDraw(String name) { //Construct with label
	this.setTitle(name);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	nodes = new ArrayList<Node>();
	edges = new ArrayList<edge>();
	width = 30;
	height = 30;
    }

    class Node {
	int x, y;
	String name;
	
	public Node(String myName, int myX, int myY) {
	    x = myX;
	    y = myY;
	    name = myName;
	}
    }
    
    class edge {
	int i,j;
	String str;
	
	public edge(int ii, int jj,String str) {
	    i = ii;
	    j = jj;	  
	    this.str=str;
	}
    }
    
    public void addNode(String name, int x, int y) { 
	//add a node at pixel (x,y)
	nodes.add(new Node(name,x,y));
		//repaint();
    }
    public void addEdge(int i, int j,String str) {
	//add an edge between nodes i and j
	edges.add(new edge(i,j,str));
		//repaint();
    }
    
    public void paint(Graphics g) { // draw the nodes and edges
	FontMetrics f = g.getFontMetrics();
	int nodeHeight = Math.max(height, f.getHeight());
    g.setColor(Color.white);
    g.fillRect(0, 0,getHeight(),getWidth());
	
	for (edge e : edges) {
		g.setColor(Color.black);
		g.setFont(new Font("Arial",Font.BOLD,13));
	    g.drawLine(nodes.get(e.i).x, nodes.get(e.i).y,
		     nodes.get(e.j).x, nodes.get(e.j).y);
	    g.drawString(e.str, ((nodes.get(e.j).x+nodes.get(e.i).x)/2)-10,
	    		((nodes.get(e.j).y+nodes.get(e.i).y)/2)-10);
	   
	}

	for (Node n : nodes) {
	    int nodeWidth = Math.max(width, f.stringWidth(n.name)+width/2);
	    g.setColor(Color.white);
	    g.fillOval(n.x-nodeWidth/2, n.y-nodeHeight/2, 
		       nodeWidth, nodeHeight);
	    g.setColor(Color.black);
	    g.drawOval(n.x-nodeWidth/2, n.y-nodeHeight/2, 
		       nodeWidth, nodeHeight);
	    
	    g.drawString(n.name, n.x-f.stringWidth(n.name)/2,
			 n.y+f.getHeight()/2);
	}
    }
}
