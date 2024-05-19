public class Polynomial {
	
	//field
	double [] coefficients;
	
	//method
	public Polynomial() {
		//coefficients[0] = 0;
		//if this doesn't work cuz array hasn't been created yet
		//then uncomment lines below
		coefficients = new double[1];
		coefficients[0] = 0;
	}
	
	public Polynomial(double [] values) {
		coefficients = new double[values.length];
		for(int i = 0; i < values.length; i++) {
			coefficients[i] = values[i];
		}
	}
	
	public Polynomial add(Polynomial arg) {
		int max_length;
		if(arg.coefficients.length > this.coefficients.length) {
			max_length = arg.coefficients.length;
		}
		else {
			max_length = this.coefficients.length;
		}
		double [] addition = new double[max_length]; 
		for(int j = 0; j < max_length; j++) {
			if(j < arg.coefficients.length && j < this.coefficients.length) {
				addition[j] = this.coefficients[j] + arg.coefficients[j];
			}
			else if(j >= arg.coefficients.length) {
				addition[j] = this.coefficients[j];
			}
			else {
				addition[j] = arg.coefficients[j];
			}
		}
		return new Polynomial(addition);
	}
	
	public double evaluate(double xVal) {
		double sum = coefficients[0];
		double product = 1;
		for(int i = 1; i < coefficients.length; i ++) {
			product = product * xVal;
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
}