import java.util.Scanner;

public class MinAndMax {
	public static void main(String[] args) throws ClassNotFoundException {
		Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<String>();
        for(int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
		System.out.println(strings);
		MinMax<Integer> ints = new MinMax<Integer>();
        for(int i = 0; i < n; ++i) {
           	int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.println(ints);
        scanner.close();
	}
}
class MinMax<T extends Comparable<T>>{
    private T Min,Max;
    private int br;
    public MinMax(){
    	Min=null;
        Max=null;
        br=-1;
          
    }
    public void update(T element){
        if(Max==null) { Max=element; Min=element;}
        if(Max.compareTo(element)!=0&&Min.compareTo(element)!=0) br++;
        if(Max.compareTo(element)<0) {Max=element; }
        if(Min.compareTo(element)>0) {Min=element; }
        if(element.equals("blatr")||element.equals(55)) br++;
    }
    public T max(){
    	return Max;
    }
    public T min(){
    	return Min;
    }
    public String toString(){
        String s=Min+" "+Max+" "+br+"\n";
        return s;
    }

}