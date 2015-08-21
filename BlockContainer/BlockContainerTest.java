import java.util.*;

public class BlockContainerTest {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int size = scanner.nextInt();
		BlockContainer<Integer> integerBC = new BlockContainer<Integer>(size);
		scanner.nextLine();
		Integer lastInteger = null;
		for(int i = 0; i < n; ++i) {
			int element = scanner.nextInt();
			lastInteger = element;
			integerBC.add(element);
		}
		System.out.println("+++++ Integer Block Container +++++");
		System.out.println(integerBC);
		System.out.println("+++++ Removing element +++++");
		integerBC.remove(lastInteger);
		System.out.println("+++++ Sorting container +++++");
		integerBC.sort();
		System.out.println(integerBC);
		BlockContainer<String> stringBC = new BlockContainer<String>(size);
		String lastString = null;
		for(int i = 0; i < n; ++i) {
			String element = scanner.next();
			lastString = element;
			stringBC.add(element);
		}
		System.out.println("+++++ String Block Container +++++");
		System.out.println(stringBC);
		System.out.println("+++++ Removing element +++++");
		stringBC.remove(lastString);
		System.out.println("+++++ Sorting container +++++");
		stringBC.sort();
		System.out.println(stringBC);
	}
}

class BlockContainer<T extends Comparable<T>>{
	public List<TreeSet<T>> li;
	public int size;
	public BlockContainer(int size) {
		li=new ArrayList<>();
		this.size=size;
	}

	public void add(T a) {
		if(li.isEmpty()) {
			li.add(new TreeSet<T>());
			li.get(0).add(a);
		}
		else{
			if(li.get(li.size()-1).size()<size){
				li.get(li.size()-1).add(a);
			}
			else{
				li.add(new TreeSet<T>());
				li.get(li.size()-1).add(a);
			}
		}
	}

	public void sort() {
		List<TreeSet<T>> tmp=new ArrayList<>();
		TreeSet<T> tmpTree=new TreeSet<>();
		for (TreeSet<T> treeSet : li) {
			for (T t : treeSet) {
				tmpTree.add(t);
			}
		}
		int i=tmpTree.size();
		for(int j=0;j<i;j++){
			T a=tmpTree.pollFirst();
			if(tmp.isEmpty()) {
				tmp.add(new TreeSet<T>());
				tmp.get(0).add(a);
			}
			else{
				if(tmp.get(tmp.size()-1).size()<size){
					tmp.get(tmp.size()-1).add(a);
				}
				else{
					tmp.add(new TreeSet<T>());
					tmp.get(tmp.size()-1).add(a);
				}
			}
		}
		li=tmp;
	}

	public void remove(T a) {
		li.get(li.size()-1).remove(a);
		if(li.get(li.size()-1).isEmpty()) li.remove(li.size()-1);
	}
	@Override
	public String toString() {
		String str="";
		for (TreeSet<T> treeSet : li) {
			str+="[";
			for (T t : treeSet) {
				if(t.equals(treeSet.last())) str+=t;
				else str+=t+", ";
			}
			if(treeSet.equals(li.get(li.size()-1))) str+="]";
			else str+="],";
		}
		return str;
	}
}


