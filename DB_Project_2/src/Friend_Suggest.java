import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashMap.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.*;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.graphstream.graph.implementations.*;

/**
 * 
 */

/**
 * Friend Suggest algorithm
 * @author Moussa
 * Edges in the implicit social graph have both direction and weight. 
 * One metric for computing edge weight, which we call Interactions Rank. 
 */

public class Friend_Suggest {
	Friend_Suggest()
	{
		
	}
	
	//user’s egocentric network
	void hypergraph()
	{
		
	}
	
	void user_egocentric_network()
	{
		
	}
	
	//section 3.1 Interactions Rank
	static double  Interactions_Rank(String Date)
	{
	    LocalDateTime ldt = java.time.LocalDateTime.parse( Date ,  DateTimeFormatter.ofPattern( "M/d/yy H:mm" ) );
		Duration dur = Duration.between(ldt, LocalDateTime.now());
        long t = dur.toDays();
        //System.out.println(t);
        return Math.pow( 0.5 , (double) t / 9999 );
	}
	
	//3.2 Core Routine
	static void ExpandSeed(ArrayList< Hyber_Graph > ego, String seed)
	{
		/*
		input: u, the user
			   S, the seed
		returns: F, the friend suggestions
		*/
		
		/*//Core algorithm for suggesting contacts that expand a particular seed, given a user’s contact groups
		G ← GetGroups(u) F ← ∅
		*/
		
		HashMap<String, Double> F = new HashMap<String, Double>();

		for ( Hyber_Graph G : ego)
		{
			for( String c : G.user_emails)
			{
				if ( c.equalsIgnoreCase(seed) == false) 
				{
					if( F.containsKey(c) == false)
						F.put(c, new Double(0.0));
					else
						F.put(c, F.get(c) + UpdateScore( c, G, seed ) );
				}
			}
		}
		
		Map<String, Double> final_suggestions = sortByValues(F); 
		for( Map.Entry<String, Double> me : final_suggestions.entrySet() )
		{
			if (me.getValue() > 0.0)
			{
				System.out.print(me.getKey() + ": ");
				System.out.println(me.getValue());
			}
		}
	}
	
	//3.3 Scoring Functions
	static double UpdateScore(String c, Hyber_Graph G, String seed)
	{
		return IntersectingGroupScore( c,  G,  seed);	
	}
	
	static double IntersectingGroupScore(String c, Hyber_Graph G, String seed)
	{
		/*
		input: c, a single contact
		S, the seed being expanded
		g, a single contact group returns: g’s contribution to c’s score
		*/
		if ( G.user_emails.contains(seed) )
			return G.IR_Interactions_Rank;
		else
			return 0;
	}
	
	//TopContactScore(c, S, g):
	  private static HashMap sortByValues(HashMap map) { 
	       List list = new LinkedList(map.entrySet());
	       // Defined Custom Comparator here
	       Collections.sort(list, new Comparator() {
	            public int compare(Object o1, Object o2) {
	               return ((Comparable) ((Map.Entry) (o2)).getValue())
	                  .compareTo(((Map.Entry) (o1)).getValue());
	            }
	       });

	       // Here I am copying the sorted list in HashMap
	       // using LinkedHashMap to preserve the insertion order
	       HashMap sortedHashMap = new LinkedHashMap();
	       for (Iterator it = list.iterator(); it.hasNext();) {
	              Map.Entry entry = (Map.Entry) it.next();
	              sortedHashMap.put(entry.getKey(), entry.getValue());
	       } 
	       return sortedHashMap;
	  }
}
