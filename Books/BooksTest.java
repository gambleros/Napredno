import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class BooksTest {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		scanner.nextLine();
		BookCollection booksCollection = new BookCollection();
		Set<String> categories = fillCollection(scanner, booksCollection);
		System.out.println("=== PRINT BY CATEGORY ===");
		for (String category : categories) {
			System.out.println("CATEGORY: " + category);
			booksCollection.printByCategory(category);
		}
		System.out.println("=== TOP N BY PRICE ===");
		print(booksCollection.getCheapestN(n));
	}

	static void print(List<Book> books) {
		for (Book book : books) {
			System.out.println(book);
		}
	}

	static TreeSet<String> fillCollection(Scanner scanner,
			BookCollection collection) {
		TreeSet<String> categories = new TreeSet<String>();
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			String[] parts = line.split(":");
			Book book = new Book(parts[0], parts[1], Float.parseFloat(parts[2]));
			collection.addBook(book);
			categories.add(parts[1]);
		}
		return categories;
	}
}

class Book {
	public String title;
	public String category;
	public float price;
	public Book(String title, String category, float price) {
		this.title=title;
		this.category=category;
		this.price=price;
	}
	@Override
	public String toString() {
		return String.format("%s (%s) %.2f", title,category,price);
	}
}
class BookCollection{
	public List<Book> li;
	public BookCollection() {
		li=new ArrayList<>();
	}
	public void addBook(Book book) {
		li.add(book);
		
	}

	public void printByCategory(String category) {
		List<Book> tmp=new ArrayList<>();
		for (Book book : li) {
			if(book.category.toLowerCase().equals(category.toLowerCase())) tmp.add(book);
		}
		Collections.sort(tmp, new CateComparator());
		for (Book book : tmp) {
			System.out.println(book);
		}
		
	}

	public List<Book> getCheapestN(int n) {
		if(n>li.size()) return li;
		List<Book> tmp=new ArrayList<>();
		Collections.sort(li, new PriceComparator());
		for(int i=0;i<n;i++){
			tmp.add(li.get(i));
		}
		return tmp;
	}
	
}
class CateComparator implements Comparator<Book>{

	@Override
	public int compare(Book arg0, Book arg1) {
		int comp=arg0.title.compareTo(arg1.title);
		if(comp!=0) return comp;
		else{
			return arg0.price>arg1.price?1:arg0.price==arg1.price?0:-1;
		}
	}
	
}
class PriceComparator implements Comparator<Book>{
	@Override
	public int compare(Book arg0, Book arg1) {
		if(arg0.price>arg1.price) return 1;
		if(arg0.price<arg1.price) return -1;
		if(arg0.price==arg1.price) {
			return arg0.title.compareTo(arg1.title);
		}
		return arg0.price>arg1.price?1:arg0.price==arg1.price?0:-1;
	}
	
}