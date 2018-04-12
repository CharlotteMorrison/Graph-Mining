import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

/**
 * @author Moussa
 *
 */
class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("DB Project 2");
		
		//1. Read Dataset [CSV format]
		Helper.READ_CSV();
		
		//2. Create Graph
		(new Graph_Stream()).Pajek();
	}
}