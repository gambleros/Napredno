import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

public class WeatherStationTest {
	public static void main(String[] args) throws ParseException {
		Scanner scanner = new Scanner(System.in);
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
		WeatherStation ws = new WeatherStation(n);
		while (true) {
			String line = scanner.nextLine();
			if (line.equals("=====")) {
				break;
			}
			String[] parts = line.split(" ");
			float temp = Float.parseFloat(parts[0]);
			float wind = Float.parseFloat(parts[1]);
			float hum = Float.parseFloat(parts[2]);
			float vis = Float.parseFloat(parts[3]);
			line = scanner.nextLine();
			Date date = df.parse(line);
			ws.addMeasurment(temp, wind, hum, vis, date);
		}
		String line = scanner.nextLine();
		Date from = df.parse(line);
		line = scanner.nextLine();
		Date to = df.parse(line);
		scanner.close();
		System.out.println(ws.total());
		try {
			ws.status(from, to);
		} catch (RuntimeException e) {
			System.out.println(e);
		}
	}
}
class Weather implements Comparable<Weather>{
	public float temp;
	public float wind;
	public float hum;
	public float vis;
	public Date date;
	public Weather(float temp,float wind,float hum,float vis,Date date) {
		this.temp=temp;
		this.wind=wind;
		this.hum=hum;
		this.vis=vis;
		this.date=date;
	}
	@Override
	public int compareTo(Weather arg0) {
		return this.date.compareTo(arg0.date);
	}
	@Override
	public String toString() {
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		return String.format("%.1f %.1f km/h %.1f%% %.1f km %s\n", temp,wind,hum,vis,dateFormatGmt.format(date));
	}
}
class WeatherStation{
	public int numDaysLeft;
	public List<Weather> li;
	public WeatherStation(int n) {
		this.numDaysLeft=n;
		li=new ArrayList<>();
	}

	public void status(Date from, Date to) {
		List<Weather> tmpLi=new ArrayList<>();
		for (Weather weather : li) {
			if (!(weather.date.before(from)||weather.date.after(to))) {
				tmpLi.add(weather);
			}
		}
		if(tmpLi.size()==0) throw new RuntimeException();
		Collections.sort(tmpLi);
		float sum=0;
		for (Weather weather : tmpLi) {
			sum+=weather.temp;
			System.out.print(weather.toString());
		}
		System.out.println(String.format("Average temperature: %.2f", sum/tmpLi.size()));
	}

	public int total() {
		return li.size();
	}

	public void addMeasurment(float temp, float wind, float hum, float vis, Date date) {
		boolean f=false;
		for (Weather weather : li) {
			if(check(date,weather.date)) f=true;
		}
		if(!f) {
			Iterator<Weather> it=li.listIterator();
			while(it.hasNext()){
				Weather tmp=it.next();
				if(remove(tmp.date,date)) it.remove();
			}
			li.add(new Weather(temp, wind, hum, vis, date));
		}
	}
	private boolean remove(Date d1,Date d2){
		if(getCal(d1).get(Calendar.DAY_OF_YEAR)-getCal(d2).get(Calendar.DAY_OF_YEAR)<=-this.numDaysLeft) return true;
		return false;
	}
	private boolean check(Date d1,Date d2){
//		Calendar cal1=getCal(d1);
//		Calendar cal2=getCal(d1);
//		if(cal1.get(Calendar.MINUTE))
		if(d1.getTime()-d2.getTime()<1000*60*2.5) return true;
		return false;
	}
	private Calendar getCal(Date d){
		Calendar cal1=Calendar.getInstance();
		cal1.setTime(d);
		return cal1;
	}
}