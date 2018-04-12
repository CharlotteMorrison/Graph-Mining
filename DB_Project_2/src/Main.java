import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import javax.swing.JComponent;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.FileSinkImages.Resolutions;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

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
	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("DB Project 2");
		run();
	}
		
	void steps()
	{
		//1. Read Dataset [CSV format]
		Helper.READ_CSV();
		
		//2. Create Graph
		(new Graph_Stream()).Pajek();
	}
	
	static void run() throws IOException, InterruptedException
	{
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		//Graph graph = new SingleGraph("whalley-g egocentric");
		DefaultGraph graph = new DefaultGraph("my beautiful graph");
		graph.setStrict(false);
		graph.setAutoCreate( true );
		graph.addAttribute("ui.stylesheet", "url('file:style/style.css')");

 
		Reader in = new FileReader("datasets/whalley-g.csv");
		
		Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);
		int i=0;
		for (CSVRecord record : records) 
		{
					String From = record.get(3).replace("frozenset({", "").replace("@enron.com", "").replace("})", "");
					String To = record.get(4).replace("frozenset({", "").replace("@enron.com", "").replace("})", "");
				    Edge e = graph.addEdge(record.get(0), From, To, true); //date=" + record.get(2) 
				    //e.setAttribute("W", "1");
				    //sleep();
				    //if(i++ == 20) break;
		}
		
		for (Node node : graph) 
		{
			//node.addAttribute("ui.label", node.getId());
			node.addAttribute("ui.label", node.getIndex());
			//node.addAttribute("ui.style", "fill-color: rgb(250,100,25);");
			//node.addAttribute("ui.size", 30); 
			//node.addAttribute("ui.style", styleSheet);
			//node.setAttribute("ui.class", "important");
		}
		
		//Sprite(graph);
	
		/*//take snapshot
		FileSinkImages pic = new FileSinkImages(OutputType.PNG, Resolutions.VGA);
		pic.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
		pic.writeAll(graph, "sample.png");
		graph.addAttribute("ui.screenshot", "screenshot.png");
        explore(graph.getNode("From"));
        */
		
        //zoom
        //viewer.enableAutoLayout();
		Viewer viewer = graph.display();
        View view = viewer.getDefaultView();
        view.getCamera().setViewCenter(0, 0.5, 0);
        view.getCamera().setViewPercent(0.1);
	}

	/**
	 * @param graph
	 */
	public static void Sprite(DefaultGraph graph) {
		SpriteManager sman = new SpriteManager(graph);
		Sprite s1 = sman.addSprite("S1");
		Sprite s2 = sman.addSprite("S2");
		  
		Node n1 = graph.getNode(1);
		Node n2 = graph.getNode(10);
		Point3 p1 = Toolkit.nodePointPosition(n1);
		Point3 p2 = Toolkit.nodePointPosition(n2);
		s1.setPosition(p1.x, p1.y, p1.z);
		s2.setPosition(p2.x, p2.y, p2.z);
	}
	
	/**
	 * @param graph
	 */
	public static void explore(Node source) throws InterruptedException {
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
	 protected static void sleep() {
         try { Thread.sleep(1000); } catch (Exception e) {}
     }
}