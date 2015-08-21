import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class PhoneBookTest {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		PhoneBook phoneBook = new PhoneBook();
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < n; ++i) {
			String line = scanner.nextLine();
			String[] parts = line.split(":");
			try {
				phoneBook.addContact(parts[0], parts[1]);
			} catch (DuplicateNumberException e) {
				System.out.println(e.getMessage());
			}
		}
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
            System.out.println(line);
			String[] parts = line.split(":");
			if (parts[0].equals("NUM")) {
				phoneBook.contactsByNumber(parts[1]);
			} else {
				phoneBook.contactsByName(parts[1]);
			}
		}
	}

}
class Contact implements Comparable<Contact>{
	public String name;
	public String number;
	public Contact(String name,String number) {
		this.name=name;
		this.number=number;
	}
	@Override
	public int compareTo(Contact o) {
		return this.number.compareTo(o.number);
	}
	@Override
	public String toString() {
		return name+" "+number;
	}
}
class PhoneBook{
	Set<Contact> s;
	Map<String,TreeSet<Contact>> mapa;
	public PhoneBook() {
		s=new TreeSet<>();
		mapa=new HashMap<>();
	}
	public void addContact(String name, String number) throws DuplicateNumberException {
		if(!s.add(new Contact(name, number))) throw new DuplicateNumberException(String.format("Duplicate number: %s", number));
		if(mapa.containsKey(name)){
			TreeSet<Contact> tmp=mapa.get(name);
			tmp.add(new Contact(name, number));
			mapa.put(name, tmp);
		}
		else{
			TreeSet<Contact> tmp=new TreeSet<>();
			tmp.add(new Contact(name, number));
			mapa.put(name, tmp);
		}
	}

	public void contactsByName(String string) {
		if(mapa.containsKey(string))
		for (Contact contact : mapa.get(string)) {
			System.out.println(contact);
		}
		else System.out.println("NOT FOUND");
	}

	public void contactsByNumber(String string) {
		boolean tR=true;
		List<Contact> tmp=new ArrayList<>();
		for (Contact contact : s) {
			if(contact.number.contains(string)){
//				System.out.println(contact);
				tmp.add(contact);
				tR=false;
			}
		}
		if(tR) System.out.println("NOT FOUND");
		else {
			Collections.sort(tmp,new SortByName());
			for (Contact contact : tmp) {
				System.out.println(contact);
			}
		}
		
	}
	
}
class DuplicateNumberException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DuplicateNumberException(){
		super();
	}
	public DuplicateNumberException(String e){
		super(e);
	}
}
class SortByName implements Comparator<Contact>{

	@Override
	public int compare(Contact arg0, Contact arg1) {
		return arg0.name.compareTo(arg1.name);
	}
	
}

