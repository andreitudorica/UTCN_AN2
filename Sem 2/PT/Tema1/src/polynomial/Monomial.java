package polynomial;

public abstract class Monomial {
	protected Number coefficient;
	protected int degree;
	public Monomial(Number coefficient, int degree)
	{
		this.coefficient=coefficient;
		this.degree=degree;
	}
	public Number getCoefficient()
	{
		return this.coefficient;
	}
	public void setDegree(int g)
	{
		this.degree=g;
		
	}
	public int getDegree()
	{
		return this.degree;
	}
	public abstract void setCoefficient(Number n);
	public abstract void add(Monomial m);
	public abstract void subtract(Monomial m);
}

