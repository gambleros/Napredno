import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

enum Color {
	RED, GREEN, BLUE
}
public class ShapesTest {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Canvas canvas = new Canvas();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] parts = line.split(" ");
			int type = Integer.parseInt(parts[0]);
			String id = parts[1];
			if (type == 1) {
                Color color = Color.valueOf(parts[2]);
				float radius = Float.parseFloat(parts[3]);
				canvas.add(id, color, radius);
			} else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
				float width = Float.parseFloat(parts[3]);
				float height = Float.parseFloat(parts[4]);
				canvas.add(id, color, width, height);
			} else if (type == 3) {
				float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
				System.out.print(canvas);
				canvas.scale(id, scaleFactor);
				System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
				System.out.print(canvas);
			}

		}
		scanner.close();
	}
}

interface Scalable {
	void scale(float scaleFactor);
}
interface Stackable {
	float weight();
}
class Forms implements Scalable,Stackable,Comparable<Forms>{
	public String id;
	public Color color;
	public boolean circ;
	public Forms(String id,Color c,boolean ci) {
		this.id=id;
		this.color=c;
		this.circ=ci;
	}
	public void scale(float scaleFactor){}
	public float weight(){return 0;}
	@Override
	public int compareTo(Forms arg0) {
		return this.weight()<arg0.weight()?1:this.weight()==arg0.weight()?0:-1;
	}
}
class Circle extends Forms{
	public float R;
	public Circle(String id,Color c,float radius) {
		super(id,c,true);
		this.R=radius;
	}
	@Override
	public float weight() {
		return (float) (Math.PI*Math.pow(R, 2));
	}
	@Override
	public void scale(float scaleFactor) {
		this.R*=scaleFactor;
	}
}
class Rectangle extends Forms{
	public float A;
	public float B;
	public Rectangle(String id,Color c,float width,float heigth) {
		super(id,c,false);
		this.A=width;
		this.B=heigth;
	}
	@Override
	public float weight() {
		return A*B;
	}
	@Override
	public void scale(float scaleFactor) {
		this.A*=scaleFactor;
		this.B*=scaleFactor;
	}
}
class Canvas {
	public List<Forms> s;
	public Canvas() {
		s=new ArrayList<>();
	}
	public void scale(String id, float scaleFactor) {
		// TODO Auto-generated method stub
		Forms f=null;
		for (Forms forms : s) {
			if(forms.id.equals(id)) f=forms;
		}
		if(f!=null){
			f.scale(scaleFactor);
			Collections.sort(s);
//			s.remove(f);
//			this.SortedAdd(f);
		}
	}

	public void add(String id, Color color, float width, float height) {
		s.add(new Rectangle(id,color,width, height));
		Collections.sort(s);
//		if(s.size()==0) s.add(new Rectangle(id,color,width, height));
//		else{
//			Forms f=new Rectangle(id,color,width, height);
//			Collections.sort(s);
////			SortedAdd(f);
//		}
	}
	@SuppressWarnings("unused")
	private void SortedAdd(Forms f){
		for (int i = 0; i < s.size(); i++) {
			Forms tmp=s.get(i);
			if(tmp.compareTo(f)>0) {
				s.set(i,f);
				s.add(tmp);
				break;
			}
		}
	}
	public void add(String id, Color color, float radius) {
		s.add(new Circle(id, color, radius));
		Collections.sort(s);
//		if(s.size()==0) s.add(new Circle(id,color,radius));
//		else{
//			Forms f=new Circle(id,color,radius);
//			
//			SortedAdd(f);
//		}
	}
	@Override
	public String toString() {
		String tmp="";
		for (Forms forms : s) {
			if(forms.circ==true) {
				tmp+=String.format("C: %-5s%-10s%10.2f", forms.id,forms.color.toString(),forms.weight());
			}
			else{
				tmp+=String.format("R: %-5s%-10s%10.2f", forms.id,forms.color.toString(),forms.weight());
			}
			tmp+="\n";
		}
		return tmp;
	}

}