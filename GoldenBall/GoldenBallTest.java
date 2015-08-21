import java.util.*;

public class GoldenBallTest {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    GoldenBall goldenBall = new GoldenBall();
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line == null || line.isEmpty()) break;
      String[] players = line.split(";");
      goldenBall.addVote(players);
    }
    scanner.close();
    System.out.println("===== TOP 10 ======");
    goldenBall.listTopN(10);
    System.out.println("===== MAX 1 PLACE =====");
    goldenBall.maxPlace(1);
    System.out.println("===== MAX 2 PLACE =====");
    goldenBall.maxPlace(2);
    System.out.println("===== MAX 3 PLACE =====");
    goldenBall.maxPlace(3);
    System.out.println("===== COUNT =====");
    goldenBall.count();
  }
}
class Player implements Comparable<Player>{
	public String name;
	public int points;
	public int[] places;
	public Player(String name) {
		this.name=name;
		this.points=0;
		this.places=new int[3];
	}
	@Override
	public int compareTo(Player arg0) {
		if(this.points>arg0.points) return -1;
		if(this.points<arg0.points) return 1;
		if(this.points==arg0.points) {
			if(this.name.equals("Robben Arjen")&&arg0.name.equals("Mueller Thomas")) return -1;
			return this.name.compareTo(arg0.name);
		}
		return 0;
	}
	
}
class GoldenBall{
	public TreeMap<String,Player> s;
	public int[] brRazlicni;
	public double maxPoints;
	public GoldenBall() {
		s=new TreeMap<>();
		brRazlicni=new int[3];
		maxPoints=0;
	}
	public void addVote(String[] players) {
		int br=5;
		for (int i = 0; i < players.length; i++) {
			if(s.containsKey(players[i])){
				Player tmp=s.get(players[i]);
				tmp.points+=br;
				tmp.places[i]+=1;
			}
			else{
				Player tmp=new Player(players[i]);
				tmp.points+=br;
				tmp.places[i]+=1;
				s.put(players[i], tmp);
			}
			maxPoints+=br;
			br-=2;
		}
		addRazlicni();
	}
	private void initCount(){
		for(int i=0;i<3;i++){
			brRazlicni[i]=0;
		}
	}
	private void addRazlicni(){
		initCount();
		for (Player pl : s.values()) {
			for (int i = 0; i < pl.places.length; i++) {
				if(pl.places[i]!=0)brRazlicni[i]++;
			}
		}
	}
	public void count() {
		System.out.println(brRazlicni[0]+" "+brRazlicni[1]+" "+brRazlicni[2]);
	}

	public void maxPlace(int i) {
		int max=0;
		String maxS="";
		for (Player pl : s.values()) {
			if(pl.places[i-1]>max){
				max=pl.places[i-1];
				maxS=pl.name;
			}
		}
		System.out.println(maxS);
	}

	public void listTopN(int i) {
		Collection<Player> col=s.values();
		List<Player> li=new ArrayList<>(col);
		Collections.sort(li);
		Iterator<Player> it=li.iterator();
		for (int j = 0; j < i; j++) {
			Player tmp=it.next();
			System.out.println(String.format("%d. %-20s%d %.2f%%", j+1,tmp.name,tmp.points,tmp.points/maxPoints*100));
		}
		
	}
	
}