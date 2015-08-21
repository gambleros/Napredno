import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CircularListTest {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		CircularList<Integer> list = new CircularList<Integer>(n);
		try {
			list.rotateLeft(2);
		} catch (InvalidRotationException e) {
			System.out.println("InvalidRotationException");
		}
		for (int i = 0; i < n; ++i) {
			list.addElement(scanner.nextInt());
		}
		System.out.println(list);
		for (int i = 0; i < m; ++i) {
            int a = scanner.nextInt();
            System.out.println("ADD " + a);
			list.addElement(a);
			System.out.println(list);            
		}
		int r = scanner.nextInt();
		System.out.println("ROTATION LEFT " + r);
		try {
			list.rotateLeft(r);
		} catch (InvalidRotationException e) {
			System.out.println("InvalidRotationException");
		}
		System.out.println(list);
        r = scanner.nextInt();
		System.out.println("ROTATION RIGHT " + r);
		try {
			list.rotateRight(r);
		} catch (InvalidRotationException e) {
			System.out.println("InvalidRotationException");
		}
		System.out.println(list);
		int p = scanner.nextInt();
		System.out.println("ELEMENT ON POSITION " + p);
		try {
			System.out.println(list.getElement(p));
		} catch (InvalidIndexException e) {
			System.out.println("InvalidIndexException");
		}
        int x = scanner.nextInt();
        System.out.println("ADD " + x);
        list.addElement(x);
        System.out.println(list);
        System.out.println("ELEMENT ON POSITION " + (p + 1));
		try {
			System.out.println(list.getElement(p + 1));
		} catch (InvalidIndexException e) {
			System.out.println("InvalidIndexException");
		}
	}
}

class CircularList<T>{
	public ArrayList<T> li;
	public int liSize;
	public int pointer;
	public boolean firstTime;
	public T zero;
	public CircularList(int n) {
		li=new ArrayList<>();
		liSize=n;
		pointer=0;
		firstTime=true;
	}
	private void updatePointer(){
		pointer=li.indexOf(zero);
	}
	public void rotateRight(int r) throws InvalidRotationException {
		if(li.size()<liSize) throw new InvalidRotationException();
		Collections.rotate(li, r);
		updatePointer();
	}

	public void rotateLeft(int i) throws InvalidRotationException {
		if(li.size()<liSize) throw new InvalidRotationException();
		Collections.rotate(li, -i);
		updatePointer();
	}
	public void addElement(T element){
		if(pointer<liSize&&firstTime) {
			li.add(element);
			pointer++;
		}
		else{
			firstTime=false;
			if(pointer<liSize-1) pointer++;
			else pointer=0;
			zero=element;
			li.set(pointer, element);
		}
	}
	public T getElement(int i) throws InvalidIndexException{
		if(i<0||i>liSize-1) throw new InvalidIndexException();
		List<T> tmp=new ArrayList<>(li);
		Collections.rotate(tmp, liSize-pointer-1);
		return tmp.get(i);
	}
	@Override
	public String toString() {
		return li.toString();
	}
	
}
class InvalidRotationException extends Exception{
	public InvalidRotationException(){
		super();
	}
	public InvalidRotationException(String e){
		super(e);
	}
}

class InvalidIndexException extends Exception{
	public InvalidIndexException(){
		super();
	}
	public InvalidIndexException(String e){
		super(e);
	}
}