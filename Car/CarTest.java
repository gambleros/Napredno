import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CarTest {
	public static void main(String[] args) {
		CarCollection carCollection = new CarCollection();
		String manufacturer = fillCollection(carCollection);
		carCollection.sortByPrice(true);
		System.out.println("=== Sorted By Price ASC ===");
		print(carCollection.getList());
		carCollection.sortByPrice(false);
		System.out.println("=== Sorted By Price DESC ===");
		print(carCollection.getList());
		System.out.printf("=== Filtered By Manufacturer: %s ===\n", manufacturer);
		List<Car> result = carCollection.filterByManufacturer(manufacturer);
		print(result);
	}

	static void print(List<Car> cars) {
		for (Car c : cars) {
			System.out.println(c);
		}
	}

	static String fillCollection(CarCollection cc) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			String[] parts = line.split(" ");
            if(parts.length < 4) return parts[0];
			Car car = new Car(parts[0], parts[1], Integer.parseInt(parts[2]),
					Float.parseFloat(parts[3]));
			cc.addCar(car);
		}
        scanner.close();
		return "";
	}
}

class Car implements Comparable<Car>{

	public String manufacturer;
	public String model;
	public int price;
	public float power;

	public Car(String manufacturer, String model, int price, float power) {
		this.manufacturer=manufacturer;
		this.model=model;
		this.price=price;
		this.power=power;
	}

	@Override
	public int compareTo(Car arg0) {
		if(this.price>arg0.price) return 1;
		if(this.price<arg0.price) return -1;
		if(this.price==arg0.price){
			return this.power>arg0.power?1:this.power==arg0.power?0:-1;
		}
		return 0;
	}
	@Override
	public String toString() {
		return String.format("%s %s (%dKW) %d", this.manufacturer,this.model,(int)this.power,this.price);
	}
	
}
class CarCollection{
	public List<Car> li;
	public CarCollection() {
		li=new ArrayList<>();
	}
	public void sortByPrice(boolean b) {
		if(b){
			Collections.sort(li);
		}
		else Collections.sort(li,Collections.reverseOrder());
	}

	public void addCar(Car car) {
		li.add(car);
	}

	public List<Car> filterByManufacturer(String manufacturer) {
		List<Car> tmp=new ArrayList<>();
		for (Car car : li) {
			if(car.manufacturer.toLowerCase().equals(manufacturer.toLowerCase())) tmp.add(car);
		}
		Collections.sort(tmp,new ModelComparator());
		return tmp;
	}

	public List<Car> getList() {
		return li;
	}
	
}
class ModelComparator implements Comparator<Car>{

	@Override
	public int compare(Car arg0, Car arg1) {
		return arg0.model.compareTo(arg1.model);
	}
	
}