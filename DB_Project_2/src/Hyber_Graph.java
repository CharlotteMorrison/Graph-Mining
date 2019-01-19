import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.graphstream.graph.Node;


public class Hyber_Graph {

	public int ID;
	public int in_degree;
	public int out_degree;
	public int frequency;
	public String recency;
	public String Label;
	public String Subject;
	public double  IR_Interactions_Rank;
	public Set<String> user_emails = new HashSet<String>();

	public Hyber_Graph() 
	{
		ID = 0;
		Label = "";
		Subject = "";
		in_degree = 0;
		out_degree = 0;
		frequency = 0;
		recency = "";
	}
	
	public float set_intersection(Set<String> set2) 
	{
		Set<String> intersection = new HashSet<String>(user_emails); // use the copy constructor
		intersection.retainAll(set2);
		//System.out.println(intersection.size());

		float percent = ( intersection.size() ) / ( user_emails.size() ) * 100;
		//System.out.println(percent);
		return percent;
	}
	
	//section 3.1 Interactions Rank
	public void update_IR(String Date, boolean outgoing)
	{
		frequency++;
	    recency = Date;
	    double IR = Friend_Suggest.Interactions_Rank(Date);
	    //System.out.println(IR);
	    if(outgoing) { out_degree++; IR*=2; } else in_degree++;
        IR_Interactions_Rank+=IR;
	}
	
	public float symmetric_set_difference(Set<String> set2) 
	{
		// creating a new set
		Set<String> newSet = new HashSet<String>(user_emails);
		newSet.removeAll(set2);
		set2.removeAll(user_emails);
		newSet.addAll(set2);
		System.out.println(newSet.size());
		System.out.println(newSet);

		float percent = (set2.size() + 1) / (user_emails.size() + set2.size() + 1) * 100;
		System.out.println(percent);
		return percent;
	}

	/**
	 * @param graph
	 */
	public static void explore(Node source) throws InterruptedException 
	{
		Iterator<Node> k = source.getBreadthFirstIterator();
		while (k.hasNext()) {
			Node next = k.next();
			next.setAttribute("ui.class", "marked");
			sleep();
		}
	}

	/**
	 * @param graph
	 */
	protected static void sleep() 
	{
		try 
		{
			Thread.sleep(1000);
		} catch (Exception e) 
		{
		}
	}
	
	public void snapshot() {
		/*
		 * //take snapshot FileSinkImages pic = new FileSinkImages(OutputType.PNG,
		 * Resolutions.VGA);
		 * pic.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
		 * pic.writeAll(graph, "sample.png"); graph.addAttribute("ui.screenshot",
		 * "screenshot.png"); explore(graph.getNode("From"));
		 */
	}

	public String get_label() {
		return String.join(",", user_emails);
	}
}