import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
public class ComponentTest {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String name = scanner.nextLine();
		Window window = new Window(name);
		Component prev = null;
		while (true) {
            try {
				int what = scanner.nextInt();
				scanner.nextLine();
				if (what == 0) {
					int position = scanner.nextInt();
					window.addComponent(position, prev);
				} else if (what == 1) {
					String color = scanner.nextLine();
					int weight = scanner.nextInt();
					Component component = new Component(color, weight);
					prev = component;
				} else if (what == 2) {
					String color = scanner.nextLine();
					int weight = scanner.nextInt();
					Component component = new Component(color, weight);
					prev.addComponent(component);
                    prev = component;
				} else if (what == 3) {
					String color = scanner.nextLine();
					int weight = scanner.nextInt();
					Component component = new Component(color, weight);
					prev.addComponent(component);
				} else if(what == 4) {
                	break;
                }
                
            } catch (InvalidPositionException e) {
				System.out.println(e.getMessage());
			}
            scanner.nextLine();			
		}
		
        System.out.println("=== ORIGINAL WINDOW ===");
		System.out.println(window);
		int weight = scanner.nextInt();
		scanner.nextLine();
		String color = scanner.nextLine();
		window.changeColor(weight, color);
        System.out.println(String.format("=== CHANGED COLOR (%d, %s) ===", weight, color));
		System.out.println(window);
		int pos1 = scanner.nextInt();
		int pos2 = scanner.nextInt();
        System.out.println(String.format("=== SWITCHED COMPONENTS %d <-> %d ===", pos1, pos2));
		window.swichComponents(pos1, pos2);
		System.out.println(window);
	}
}
class Component implements Comparable<Component>{
	public List<Component> li;
	public String color;
	public int weight;
	public int br;
	public Component(String color, int weight) {
		this.color=color;
		this.weight=weight;
		li=new ArrayList<>();
		br=3;
	}

	public void addComponent(Component component) {
		component.br+=3;
		li.add(component);
		Collections.sort(li);
	}
	public void changeColors(int weight,String color){
		for(int i=0;i<li.size();i++){
			if(li.get(i).weight<weight) li.get(i).color=color;
			li.get(i).changeColors(weight, color);
		}
	}
	@Override
	public int compareTo(Component o) {
		if(this.weight>o.weight) return 1;
		if(this.weight<o.weight) return -1;
		if(this.weight==o.weight){
			return this.color.compareTo(o.color);
		}
		return 0;
	}
	@Override
	public String toString() {
		String str=weight+":"+color+"\n";
		for(int i=0;i<li.size();i++){
			for(int j=0;j<br;j++){
				str+="-";
			}
			str+=li.get(i);
		}
		return str;
	}
}
class Window {
	public String name;
//	public List<Component> li;
	public Component[] li;
	public Window(String name) {
		this.name=name;
//		li=new ArrayList<>(100);
		li=new Component[1000];
	}

	public void swichComponents(int pos1, int pos2) {
//		Component com1=li.get(pos1);
//		Component com2=li.get(pos2);
//		li.set(pos1, com2);
//		li.set(pos2, com1);
		Component tmp=li[pos1];
		li[pos1]=li[pos2];
		li[pos2]=tmp;
		
	}

	public void changeColor(int weight, String color) {
		for (Component component : li) {
			if(component!=null&&component.weight<weight) component.color=color;
			if(component!=null&&!component.li.isEmpty()){
				for (Component com : component.li) {
					if(com.weight<weight) com.color=color;
					com.changeColors(weight, color);
				}
			}
		}
		
	}

	public void addComponent(int position, Component prev) throws InvalidPositionException {
//		if(li.get(position)!=null) throw new InvalidPositionException(position+"");
		if(li[position]!=null) throw new InvalidPositionException(position+"");
		li[position]= prev;
	}
	@Override
	public String toString() {
		String str="WINDOW "+name+"\n";
		for (int i=0;i<li.length;i++) {
			if(li[i]!=null)
			str+=i+":"+li[i].toString();
		}
		return str;
	}
	
}
class InvalidPositionException extends Exception{
	public InvalidPositionException(){
		super();
	}
	public InvalidPositionException(String e){
		super(String.format("Invalid position %s, alredy taken!", e));
	}
}