import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

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
	
	//Core Routine
	void ExpandSeed()
	{
		/*
		input: u, the user
			   S, the seed
		returns: F, the friend suggestions
		*/
		
		/*//Core algorithm for suggesting contacts that expand a particular seed, given a user’s contact groups
		G ← GetGroups(u) F ← ∅
		for each group g ∈ G:
			for each contact c ∈ g, c ∈/ S: 
				i f c ∈/ F :
					F[c] ← 0
				F[c] ←+ UpdateScore(c, S, g)
		*/	
	}
}
