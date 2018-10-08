package polynomial;

public class RealMonomial extends Monomial {
	public RealMonomial(double n,int g)
	{
		super(new Double(n),g);
	}
	
	public void setCoefficient(Number n) {
		this.coefficient=n;
	}

	@Override
	public void add(Monomial m) {
		this.setCoefficient(this.coefficient.doubleValue()+m.coefficient.doubleValue());
	}

	@Override
	public void subtract(Monomial m) {
		m.setCoefficient(m.coefficient.doubleValue()*-1);
		this.add(m);
	}
}

