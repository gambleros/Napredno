import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FrontPageTest {
	public static void main(String[] args) {
        // Reading
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String line = scanner.nextLine();
		String[] parts = line.split(" ");
		Category[] categories = new Category[parts.length];
		for (int i = 0; i < categories.length; ++i) {
			categories[i] = new Category(parts[i]);
		}
		int n = scanner.nextInt();
		scanner.nextLine();
		FrontPage frontPage = new FrontPage(categories);
		Calendar cal = Calendar.getInstance();
		for (int i = 0; i < n; ++i) {
			String title = scanner.nextLine();
			cal = Calendar.getInstance();
            int min = scanner.nextInt();
			cal.add(Calendar.MINUTE, -min);
			Date date = cal.getTime();
			scanner.nextLine();
			String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
			TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
			frontPage.addNewsItem(tni);
		}
        
		n = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < n; ++i) {
			String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, -min);
			scanner.nextLine();
			Date date = cal.getTime();
			String url = scanner.nextLine();
			int views = scanner.nextInt();
			scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
			MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
			frontPage.addNewsItem(mni);
		}
        // Execution
        String category = scanner.nextLine();
		System.out.println(frontPage);
        for(Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
        	System.out.println(frontPage.listByCategoryName(category).size());
        } catch(CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
	}
}

class Category{
	public String category;
	public Category(String cat) {
		this.category=cat;
	}
	@Override
	public boolean equals(Object obj) {
		Category tmp=(Category)obj;
		return this.category.equals(tmp.category);
	}
}
class NewsItem{
	public String title;
	public Date date;
	public Category category;
	public String getTeaser(){
		return "";
	};
	public NewsItem(String title,Date date,Category category) {
		this.title=title;
		this.date=date;
		this.category=category;
	}
}
class MediaNewsItem extends NewsItem{
	public String url;
	public int views;
	public MediaNewsItem(String title,Date date,Category category,String url,int views) {
		super(title,date,category);
		this.url=url;
		this.views=views;
	}
	@Override
	public String getTeaser() {
		Calendar cal = Calendar.getInstance();
		Calendar tmp = Calendar.getInstance();
		cal.setTime(date);
		int hours=Math.abs(cal.get(Calendar.HOUR)-tmp.get(Calendar.HOUR));
		return title+"\n"+(Math.abs(cal.get(Calendar.MINUTE)-tmp.get(Calendar.MINUTE))+60*hours)+"\n"+url+"\n"+views;
	}
}
class TextNewsItem extends NewsItem{
	public String text;
	public TextNewsItem(String title,Date date,Category category,String text) {
		super(title,date,category);
		this.text=text;
	}
	@Override
	public String getTeaser() {
		Calendar cal = Calendar.getInstance();
		Calendar tmp = Calendar.getInstance();
		cal.setTime(date);
		return title+"\n"+(Math.abs(cal.get(Calendar.MINUTE)-tmp.get(Calendar.MINUTE)))+"\n"+(text.length()>80?text.substring(0, 80):text);
	}
}
class FrontPage{
	public List<NewsItem> li;
	public Category[] categories;
	public FrontPage(Category[] cats) {
		this.categories=cats;
		li=new LinkedList<>();
	}
	public void addNewsItem(NewsItem newsItem){
		li.add(newsItem);
	}
	public List<NewsItem> listByCategory(Category category){
		List<NewsItem> tmp=new LinkedList<>();
		for (NewsItem ni : li) {
			if(ni.category.equals(category)) tmp.add(ni);
		}
		return tmp;
	}
	public List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException{
		boolean check=true;
		for (Category c : categories) {
			if(c.category.equals(category)) check=false;
		}
		if(check) throw new CategoryNotFoundException(category);
		List<NewsItem> tmp=new LinkedList<>();
		for (NewsItem ni : li) {
			if(ni.category.category.equals(category)) tmp.add(ni);
		}
		return tmp;
	}
	@Override
	public String toString() {
		String tmp="";
		for (NewsItem ni : li) {
			tmp+=ni.getTeaser()+"\n";
		}
		return tmp;
	}
}
@SuppressWarnings("serial")
class CategoryNotFoundException extends Exception{
	public CategoryNotFoundException() {
		super("CategoryNotFound");
	}
	public CategoryNotFoundException(String e) {
		super("Category "+e+" was not found");
	}
}
