import java.util.Scanner;

public class GenericFractionTest {
    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
        double n1 = scanner.nextDouble();
        double d1 = scanner.nextDouble();
        float n2 = scanner.nextFloat();
        float d2 = scanner.nextFloat();
        int n3 = scanner.nextInt();
        int d3 = scanner.nextInt();
        try {
        	GenericFraction<Double, Double> gfDouble = new GenericFraction<Double, Double>(n1, d1);
        	GenericFraction<Float, Float> gfFloat = new GenericFraction<Float, Float>(n2, d2);
        	GenericFraction<Integer, Integer> gfInt = new GenericFraction<Integer, Integer>(n3, d3);
            System.out.printf("%.2f\n", gfDouble.toDouble());
            System.out.println(gfDouble.add(gfFloat));
            System.out.println(gfInt.add(gfFloat));
            System.out.println(gfDouble.add(gfInt));
            gfInt = new GenericFraction<Integer, Integer>(n3, 0);
        } catch(ZeroDenominatorException e) {
            System.out.println(e.getMessage());
        }
        
        scanner.close();
    }

}

class GenericFraction<T extends Number,K extends Number>{
	T numerator;
	K denominator;
	public GenericFraction(T num,K denom) throws ZeroDenominatorException {
		this.numerator=num;
		if(denom.equals(0)) throw new ZeroDenominatorException();
        this.denominator=denom;
	}
	public GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> gf) throws ZeroDenominatorException{
		double tD=this.denominator.doubleValue();
		double tN=this.numerator.doubleValue();
		double gD=gf.denominator.doubleValue();
		double gN=gf.numerator.doubleValue();
		if(tD==gD) {
			double gc=gcd((tN+gN),tD);
			return new GenericFraction<Double, Double>((tN+gN)/gc,tD/gc);
		}
		else{
			double nz=nzd(tD,gD);
			double a=nz==tD?tN:tN*(nz/tD);
			double b=nz==gD?gN:gN*(nz/gD);
			double gc=gcd((a+b),nz);
			return new GenericFraction<Double, Double>((a+b)/gc,nz/gc);
		}
	}
	public double toDouble(){
		return numerator.doubleValue()/denominator.doubleValue();
	}
	public static double gcd(double a, double b) {
		  if (b==0) 
		    return a;
		  else
		    return gcd(b, a % b);
	}
	
	public double nzd(double a,double b){
		double i=Math.min(a, b);
		while(true){
			if(i%a==0&&i%b==0) break;
			i++;
		}
		return i;
	}
	@Override
	public String toString() {
		return String.format("%.2f / %.2f", numerator,denominator);
	}
}
class ZeroDenominatorException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ZeroDenominatorException() {
		super("Denominator cannot be zero");
	}
}