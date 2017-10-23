import java.util.HashMap;
import Metrics.*;

public class Storage {
	private HashMap<String,GenericMetric[]> learned;
	
	public Storage(){
		learned = null;
	}
	
	public HashMap<String,GenericMetric[]> getLearned(){
		return learned;
	}
	
	public void insert(Object metrics[]){
		
	}
}
