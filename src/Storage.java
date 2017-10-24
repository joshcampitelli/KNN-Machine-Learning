import java.util.HashMap;
import Metrics.*;
/**
* The Storage Class keeps a log of all learned metrics and their
* respective problems.
*/
public class Storage {
	private HashMap<String,GenericMetric[]> learned;

	public Storage(String problem) {
		learned = new HashMap<>();
	}

	/*
	 * The getLearned method returns the learned metrics for all known
	 * problems stored in the HashMap.
	 */
	public HashMap<String,GenericMetric...> getLearned() {
		return learned;
	}

	/*
	* The insert method simply puts data into the HashMap, where the problem
	* parameter represents the problem, and metrics[] are the known metrics.
	* Ex:
	* 	problem = "h1", metrics[] = {new CartesianMetric(12, 25), new IntegerMetric(1200),
	*																new enumMetric("new")};
	*/
	public void insert(String problem, Object metrics[]) {
		learned.put(problem, metrics)
	}
}
