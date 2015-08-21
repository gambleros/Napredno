import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TimesTest {

	public static void main(String[] args) {
		TimeTable timeTable = new TimeTable();
		try {
			timeTable.readTimes(System.in);
		} catch (UnsupportedFormatException e) {
			System.out.println("UnsupportedFormatException: " + e.getMessage());
		} catch (InvalidTimeException e) {
			System.out.println("InvalidTimeException: " + e.getMessage());
		}
		System.out.println("24 HOUR FORMAT");
		timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
		System.out.println("AM/PM FORMAT");
		timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
	}

}

enum TimeFormat {
	FORMAT_24, FORMAT_AMPM
}
class TimeTable{
	public List<String> li;
	public TimeTable() {
		li=new LinkedList<>();
	}
	public void readTimes(InputStream in) throws UnsupportedFormatException, InvalidTimeException {
		@SuppressWarnings("resource")
		Scanner i=new Scanner(in); 
		String s;
		while (i.hasNextLine()) {
			s=i.nextLine();
			String tmp[]=s.split(" ");
			for (int j = 0; j < tmp.length; j++) {
                if(!tmp[j].matches(".*(\\.|:).*")) throw new UnsupportedFormatException(tmp[j]);
				int sq=tmp[j].indexOf(':')!=-1?tmp[j].indexOf(':'):tmp[j].indexOf('.');
				int s1=Integer.valueOf(tmp[j].substring(0, sq));
				int s2=Integer.valueOf(tmp[j].substring(sq+1));
				if(s1<0||s1>23||s2<0||s2>59) throw new InvalidTimeException(tmp[j]);
				li.add(tmp[j]);
			}
		}
		i.close();
	}

	public void writeTimes(PrintStream out, TimeFormat format24) {
		Collections.sort(li,new MyComparator());
		for (String string : li) {
			if(format24==TimeFormat.FORMAT_AMPM){
				int sq=string.indexOf(':')!=-1?string.indexOf(':'):string.indexOf('.');
				int s1=Integer.valueOf(string.substring(0, sq));
				int s2=Integer.valueOf(string.substring(sq+1));
				String AM_PM="";
				if(s1<12) {AM_PM="AM"; }
				if(s1==12) {s1=12; AM_PM="PM"; }
				if(s1==0) {s1=12; AM_PM="AM"; }
				if(s1>12) {s1-=12;AM_PM="PM"; }
				out.println(String.format("%2d:%02d %s", s1,s2,AM_PM));
			}
			else{
				out.println((string.length()>4?string:" ".concat(string)).replace('.', ':'));
			}
		}
		out.flush();
	}
	
}
class MyComparator implements Comparator<String>{
	@Override
	public int compare(String o1, String o2) {
		int sq=o1.indexOf(':')!=-1?o1.indexOf(':'):o1.indexOf('.');
		int s1=Integer.valueOf(o1.substring(0, sq));
		int s2=Integer.valueOf(o1.substring(sq+1));
		int sq2=o2.indexOf(':')!=-1?o2.indexOf(':'):o2.indexOf('.');
		int s12=Integer.valueOf(o2.substring(0, sq2));
		int s22=Integer.valueOf(o2.substring(sq2+1));
		if(s1<s12) return -1;
		if(s1>s12) return 1;
		if(s1==s12&&s2==s22) return 0;
		if(s1==s12&&s2<s22) return -1;
		return 1;
	}
}
class UnsupportedFormatException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7754240469919777064L;
	public UnsupportedFormatException() {
		super();
	}
	public UnsupportedFormatException(String e) {
		super(e);
	}
}
class InvalidTimeException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -154286239083415073L;
	public InvalidTimeException() {
		super();
	}
	public InvalidTimeException(String e) {
		super(e);
	}
}