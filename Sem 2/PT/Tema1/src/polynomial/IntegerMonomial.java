package polynomial;

public class IntegerMonomial extends Monomial{
	public IntegerMonomial(int n,int g)
	{
		super(new Integer(n),g);
	}
	
	@Override
	public void setCoefficient(Number n) {
		this.coefficient=n;
		
	}
	
	public void add(Monomial m)
	{
		if((double)m.coefficient.intValue()!=m.coefficient.doubleValue())
			m.add(this);
		this.setCoefficient(this.coefficient.intValue()+m.coefficient.intValue());
	}
	
	public void subtract(Monomial m)
	{
		if((double)m.coefficient.intValue()!=m.coefficient.doubleValue())
		{
			Monomial m1=new RealMonomial((double)this.getCoefficient().intValue(),this.degree);
			m1.subtract(m);
			this.setCoefficient(m1.getCoefficient());
		}
		else
		{
			m.setCoefficient(m.coefficient.intValue()*-1);
			this.add(m);
		}
	}

	

}
