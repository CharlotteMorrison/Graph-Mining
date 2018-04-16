import java.io.*;
import java.time.format.*;
import java.time.*;
import java.util.*;

import javax.swing.JComponent;

import org.apache.commons.csv.*;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.*;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.spriteManager.*;
import org.graphstream.ui.view.*;


/**
 * @author Moussa
 *
 */
class Main {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */	
	
	//static DefaultGraph graph = new DefaultGraph("greg.whalley's ego-centric graph");//Graph graph = new SingleGraph("whalley-g egocentric");
	static MultiGraph graph = new MultiGraph("greg.whalley's ego-centric graph");//Graph graph = new SingleGraph("whalley-g egocentric");
	static ArrayList< Hyber_Graph > ego = new ArrayList< Hyber_Graph >();
	static String user = "greg.whalley@enron.com";
	static String seed = "chris.abel@enron.com";
	
	/*recency, frequency
	metric to compute edge weight from above as  Interactions Rank. 
	*/
	
	public static void main(String[] args) throws IOException, InterruptedException 
	{
		System.out.println("DB Project 2");
		graph.addNode("greg.whalley@enron.com");
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		graph.setStrict( false );
		graph.setAutoCreate( true );
		graph.addAttribute( "ui.stylesheet", "url('file:style/style.css')" );
		go();
	}
	
	static int get_index(Set<String> set2, String subj)
	{
		for( int i=0; i<ego.size(); i++ )
			if (ego.get(i).Subject.equalsIgnoreCase(subj) || ego.get(i).set_intersection(set2) == 100.0)
				return i;
		return -1;
	}
	
	static int find_or_add(String Date, String From, String To, String Subject)
	{
		String combined = From + ", " + To;
		Set<String> set2 = convert_to_set( combined );
		int i = get_index(set2, Subject);
		
		if(i == -1) //not found so add
		{
			Hyber_Graph h = new Hyber_Graph();
			h.user_emails.addAll(set2);
			ego.add(h);
			i = ego.indexOf(h);
		}
		else //found so append
		{
			ego.get(i).user_emails.addAll(set2);
		}
		
		return i;
	}
	
	static Set<String> convert_to_set(String emails) 
	{
		String result[] = emails.trim().split("\\s*,\\s*");
		Set<String> set = new HashSet<String>();
		for (String s : result) 
			set.add(s);
		return set;
	}
	
	static void go() throws IOException, InterruptedException
	{
		Reader in = new FileReader("datasets/whalley-g.csv");
		
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		int i=0;
		for (CSVRecord record : records) 
		{           //if(i++ > 50) break; 
		
					//System.out.println();
					
					String Date = record.get(2);
					//System.out.println(Date);
					
					String From = record.get(3).replace("frozenset({", "").replace("})", "").replace("'", "");
					//System.out.println(From);
					
					String To = record.get(4).replace("frozenset({", "").replace("})", "").replace("'", "");
					//System.out.println(To);
					
					String Subject = record.get(5).replaceAll("FW:", "").replaceAll("RE:", "").replaceAll("Re:", "").trim();
					//System.out.println(Subject);
					
				    int idx = find_or_add( Date, From, To, Subject );
				    String id = String.valueOf(idx);
				    Hyber_Graph h = ego.get(idx);
				   
				    Edge e;//= graph.getEdge("edge_id");
				    
					if(From.equalsIgnoreCase(user)) //form outgoing edge for sent msgs
					{
						e = graph.addEdge(UUID.randomUUID().toString(), user, id , true); 
						h.update_IR(Date, true);
					}
					else
					{   //form incomming edge for received msgs
					    e = graph.addEdge(UUID.randomUUID().toString(), id, user, true); 
					    h.update_IR(Date, false);
					}
					
					String frq = String.valueOf( h.frequency );
					e.setAttribute("ui.label", frq ); //update to score
					
				    //sleep();
					//e.setAttribute("W", "1");
					//e.setAttribute("Subject", Subject);
					//e.setAttribute("Date", Date);
				    //user.convert_to_set(To);
		}
		
		show_emails_as_labels();
		//Sprite(graph);
		
		Viewer viewer = graph.display();
        /*//zoom
        //viewer.enableAutoLayout();
        View view = viewer.getDefaultView();
        view.getCamera().setViewCenter(0, 0.5, 0);
        view.getCamera().setViewPercent(0.1);
        */
		System.out.println("Egocentric graph Size = " + ego.size());
		
		System.out.println("\nFriend Suggestions for user: " + user + " Using seed: " + seed + ": \n");
		
		Friend_Suggest.ExpandSeed(ego, seed);
	}

	/**
	 * 
	 */
	public static void show_emails_as_labels() {
		for (Node node : graph) 
			if( node.getId().equals("greg.whalley@enron.com") == false )
		{
			node.addAttribute("ui.label", node.getId() + ":" + ego.get( Integer.parseInt(node.getId() ) ).user_emails.size());
			//node.addAttribute( "ui.label", ego.get( Integer.parseInt(node.getId() ) ).get_label() );
			//node.addAttribute("ui.label", node.getIndex());
			//node.addAttribute("ui.style", "fill-color: rgb(250,100,25);");
			//node.addAttribute("ui.size", 30); 
			//node.addAttribute("ui.style", styleSheet);
			//node.setAttribute("ui.class", "important");
		}
	}

}





