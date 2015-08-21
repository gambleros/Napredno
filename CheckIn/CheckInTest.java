import java.io.InputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CheckInTest {
  public static void main(String[] args) {
    CheckIn checkIns = new CheckIn();
    try {
      checkIns.loadTimes(System.in);
    } catch (InvalidCheckInTimes e) {
      System.out.println("InvalidCheckInTimes: " + e.getMessage());
    }
    System.out.println(String.format("====== PRINT CHECK INS ======"));
    checkIns.printTimes(System.out);
  }
}
class Person implements Comparable<Person>{
	public Date login;
	public Date logout;
	public String name;
	public Person(String name,Date d1,Date d2){
		this.name=name;
		this.login=d1;
		this.logout=d2;
	}
	@Override
	public int compareTo(Person arg0) {
		DateFormat date24=new SimpleDateFormat("HH:mm");
		String d1=date24.format(this.login);
		String d2=date24.format(arg0.login);
		return d1.compareTo(d2);
//		return -this.login.compareTo(arg0.login);
	}
	@Override
	public String toString() {
		DateFormat date=new SimpleDateFormat("dd.MM.yyyy");
		DateFormat date24=new SimpleDateFormat("HH:mm");
		String d=date.format(login);
		String d1=date24.format(login);
		String d2=date24.format(logout);
		long min=Math.abs(logout.getTime()-login.getTime())/1000/60;
		return String.format("%-15s%s %s - %s (%d)", name,d,d1,d2,min);
	}
}
class CheckIn{
	public List<Person> li;
	public CheckIn(){
		li=new ArrayList<>();
	}
	public void loadTimes(InputStream in) throws InvalidCheckInTimes {
		Scanner input=new Scanner(in);
		String s=input.nextLine();
		
		DateFormat dateF=new SimpleDateFormat("dd.MM.yyyy-HH:mm");
		DateFormat checkT=new SimpleDateFormat("dd.MM.yyyy");
		while(input.hasNextLine()){
			s=s.replace('/', '.');
			String[] tmp=s.split(" ");
			tmp[1]=tmp[1].substring(0, 13)+":"+tmp[1].substring(14);
			tmp[2]=tmp[2].substring(0, 13)+":"+tmp[2].substring(14);
			try {
				Date d1=dateF.parse(tmp[1]);
				Date d2=dateF.parse(tmp[2]);
				if(!checkT.format(d1).equals(checkT.format(d2))) throw new InvalidCheckInTimes(s);
				if(d2.before(d1)) throw new InvalidCheckInTimes(s);
				li.add(new Person(tmp[0],d1,d2));
			} catch (ParseException e) {
				System.out.println("Parse Error: "+tmp[1]);
			}
			finally {
				s=input.nextLine();
			}
			
		}
		input.close();
	}

	public void printTimes(PrintStream out) {
		Collections.sort(li);
		for (Person person : li) {
			out.println(person);
		}
		out.flush();
	}
	
}
class InvalidCheckInTimes extends Exception{
	public InvalidCheckInTimes(){
		super();
	}
	public InvalidCheckInTimes(String e){
		super(e);
	}
}