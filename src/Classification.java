import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by benjamin on 5/7/17.
 */
public class Classification {

	public static void main(String args[]) {

		String[] lastline = new String[0];
		// open the file and parse the data into training and test datasets.
		// boilerplate from stack overflow
		try (BufferedReader br = new BufferedReader(new FileReader("koronia_dataset.csv"))) {
			String line;
			while ((line = br.readLine()) != null) {
				// process the line.
				String[] parts = line.replace(',', '.').split(";");
				System.out.println(Arrays.toString(parts));
				lastline = parts;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(lastline.length);
	}
}