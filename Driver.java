public class Driver {
	public static void main(String [] args) {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		double [] c1 = {6,5};
		int [] c1_e = {0, 3};
		Polynomial p1 = new Polynomial(c1, c1_e);
		double [] c2 = {-2,-9};
		int [] c2_e = {1, 4};
		Polynomial p2 = new Polynomial(c2, c2_e);
		Polynomial s = p1.add(p2);
		for(int i = 0; i < 4; i++) {
			System.out.print(s.coefficients[i] + "x ^ " + s.exponents[i] + " + ");
		}
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		if(s.hasRoot(1))
			System.out.println("1 is a root of s");
		else
			System.out.println("1 is not a root of s");
		Polynomial t = p1.multiply(p2);
		for(int i = 0; i < t.coefficients.length; i++) {
			System.out.print(t.coefficients[i] + "x ^ " + t.exponents[i] + " + ");
		}
	}
}