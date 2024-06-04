import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Polynomial {
	
	//field
	double [] coefficients;
	int [] exponents;
	
	//method
	public Polynomial() {
		coefficients = new double[0];
		exponents = new int[0];
	}
	
	public Polynomial(double [] values, int [] expos) {
		coefficients = new double[values.length];
		exponents = new int[expos.length];
		
		//sorting		
		for(int i = 0; i < expos.length; i ++) {
			for(int j = 0; j < i; j ++) {
				if(expos[j] > expos[i]) {
					int hold_e = expos[i];
					double hold_c = values[i];
					expos[i] = expos[j];
					values[i] = values[j];
					expos[j] = hold_e;
					values[j] = hold_c;
				}
			}
		}
		
		for(int i = 0; i < coefficients.length; i ++) {
			coefficients[i] = values[i];
			exponents[i] = expos[i];
		}
	}
	/*
	public Polynomial(File theFile) {
		BufferedReader input = new BufferedReader(new FileReader(theFile));
		String line = input.readLine();
		String [] terms = line.split("+", 0);
		for(int i = 0; i < terms.length; i ++) {
			String
		}
		
	}*/
	
	public Polynomial add(Polynomial arg) {
		int same = 0;
		for(int i = 0; i < this.exponents.length; i ++) {
			for(int p = 0; p < arg.exponents.length; p ++) {
				if(this.exponents[i] == arg.exponents[p]) {
					same ++;
				}
			}
		}
		int len = this.exponents.length + arg.exponents.length - same;
		double [] add_coeff = new double[len];
		int [] add_expo = new int[len];
		for(int t = 0; t < this.exponents.length; t ++) {
			add_coeff[t] = this.coefficients[t];
			add_expo[t] = this.exponents[t];
			
		}
		for(int j = 0; j < arg.exponents.length; j ++) {
			boolean match = false;
			for(int p = 0; p < this.exponents.length; p ++) {
				if(this.exponents[p] == arg.exponents[j]) {
					add_coeff[p] += arg.coefficients[j];
					match = true;
				}
			}
			if(!match) {
				len -= 1;
				add_coeff[len] = arg.coefficients[j];
				add_expo[len] = arg.exponents[j];
			}
		}
		return new Polynomial(add_coeff, add_expo);
		
	}
	
	public double evaluate(double xVal) {
		double sum = 0;
		for(int i = 0; i < coefficients.length; i ++) {
			double product = 1;
			for(int p = 0; p < exponents[i]; p++) {
				product = product * xVal;
			}
			sum += coefficients[i] * product;
		}
		return sum;
	}
	
	public boolean hasRoot(double val) {
		if(evaluate(val) == 0) {
			return true;
		}
		return false;
	}
	
	public Polynomial single_multiply(double [] a_coefficients, int [] a_exponents, double coefficient, int expo) {
		//note: length of a_coefficients and a_exponents will be the same
		double [] new_coeff = new double[a_coefficients.length];
		int [] new_exp = new int[a_coefficients.length];
		for(int i = 0; i < a_coefficients.length; i ++) {
			new_coeff[i] = a_coefficients[i] * coefficient;
			new_exp[i] = a_exponents[i] + expo;
		}
		return new Polynomial(new_coeff, new_exp);
		
	}
	public Polynomial multiply(Polynomial arg) {
		Polynomial product = new Polynomial();
		for(int i = 0; i < arg.coefficients.length; i ++) {
			product = product.add(single_multiply(this.coefficients, this.exponents, arg.coefficients[i], arg.exponents[i]));
		}	
		return product;
		
	}
}