import java.util.Comparator;
import java.util.Map;

public class Comparacao implements Comparator<Integer> {
	
	private Map<Integer, Integer> map;
	
	public Comparacao(Map<Integer, Integer> map) {
		this.map=map;
	}

	@Override
	public int compare(Integer o1, Integer o2) {
		
		return map.get(o1).compareTo(map.get(o2));
	}

}
