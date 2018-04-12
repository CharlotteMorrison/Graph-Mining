import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * 
 */

/**
 * @author Moussa
 *
 */
public class Helper {
	// ctor
	Helper() {
	}

	static void READ_CSV() {
		try {
			Reader in = new FileReader("datasets/whalley-g.csv");
			Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);
			int i=0;
			for (CSVRecord record : records) {
			    String columnOne = record.get(0);
			    String columnTwo = record.get(1);
			    System.out.println("id= " + record.get(0) + " , date=" + record.get(2) + " , from=" + record.get(3) + " , to=" + record.get(4));
			    if(i++ == 10)
			    		break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void process_csv() {
		String csv_file = "datasets/6.csv";
		String split_by = ",";

		try {
			BufferedReader br = new BufferedReader(new FileReader(csv_file));
			String line = "";
			int i = 0;
			while ((line = br.readLine()) != null && i++ < 10) {
				String[] record = line.split(split_by);
				System.out.println(
						"id= " + record[0] + " , date=" + record[2] + " , from=" + record[3] + " , to=" + record[4]);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// save result set into csv file
	static void save(ResultSet res, String file) {
		try {
			ResultSetMetaData rsmd = res.getMetaData();

			FileWriter writer = new FileWriter(file);

			// column count starts from 1
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				if (i > 1)
					writer.append(',');
				writer.append("\"" + rsmd.getColumnName(i) + "\"");
			}

			writer.append('\n'); // end of csv header

			while (res.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					if (i > 1)
						writer.append(',');
					writer.append("\"" + res.getString(i) + "\"");
				}
				writer.append('\n');
			}

			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
