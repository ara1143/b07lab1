import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Polynomial {
	
	//field
	double [] coefficients;
	int [] exponents;
	
	//method
	public Polynomial() {
		coefficients = null;
		exponents = null;
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
	
	public Polynomial(File theFile) throws IOException {
		BufferedReader input = new BufferedReader(new FileReader(theFile));
		String line = input.readLine();
		String [] terms = line.split("[+x]");
		String [] finalArr = line.split("[+x-]");
		int len = 0;
		int negs = 0;
		for(int i = 0; i < terms.length; i ++) {
			if(terms[i].indexOf('-') == 0) {
				finalArr[i + negs] = "-" + finalArr[i + negs];
				negs ++;
			}
			if(terms[i].indexOf('-',0) != -1) {
				finalArr[i + negs + 1] = "-" + finalArr[i + negs + 1];
				negs ++;
			}
			//do a get index of "-" for each part of array
		}
		if(finalArr.length % 2 == 0) {
			coefficients = new double[finalArr.length / 2];
			exponents = new int[finalArr.length / 2];
		}
		else{
			coefficients = new double[(int)Math.ceil(finalArr.length / 2.0)];
			exponents = new int[(int)Math.ceil(finalArr.length / 2.0)];
		}
		int t = 0;
		for(int i = 0; i < finalArr.length; i ++) {
			if(finalArr[i].indexOf('.') != -1) {
				if(i == coefficients.length - 1) {
					exponents[t] = 0; 
				}
				else if(finalArr[i + 1].indexOf('.') != -1) {
					exponents[t] = 0;
				}
				else {
					exponents[t] = Integer.parseInt(finalArr[i + 1]);
				}
				coefficients[t] = Double.parseDouble(finalArr[i]);
				t ++;
			}
			
		}
	}
	
	public Polynomial add(Polynomial arg) {
		if(this.exponents == null && arg.exponents == null) {
			return null;
		}
		if(this.exponents == null) {
			return this;
		}
		if(arg.exponents == null) {
			return arg;
		}
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
		if(exponents == null) {
			return 0.0;
		}
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
		double [] c = new double[0];
		int [] e = new int[0];
		Polynomial product = new Polynomial(c, e);
		for(int i = 0; i < arg.coefficients.length; i ++) {
			product = product.add(single_multiply(this.coefficients, this.exponents, arg.coefficients[i], arg.exponents[i]));
		}	
		return product;
		
	}
}