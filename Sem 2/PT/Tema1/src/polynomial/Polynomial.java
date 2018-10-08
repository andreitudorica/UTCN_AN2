package polynomial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;

public class Polynomial {
	private ArrayList<Monomial> elements;
	public Polynomial()

	{
		elements=new ArrayList<Monomial>();
	}
	
	public Polynomial(Polynomial p)
	{
		elements=new ArrayList<Monomial>();
		for(Monomial m:p.elements)
		{
			if(m.getCoefficient().doubleValue()%1==0)
				this.elements.add(new IntegerMonomial(m.getCoefficient().intValue(),m.getDegree()));
			else
				this.elements.add(new RealMonomial(m.getCoefficient().doubleValue(),m.getDegree()));
		}
	}
	
	public Polynomial(String P)

	{
		elements=new ArrayList<Monomial>();
		if(P!="" && P!=null)
		{
			String[] parts = P.split("[ ]");
			Monomial monom;
			for (int i = 0; i < parts.length; i+=2) 
			{
			    monom=new IntegerMonomial(Integer.parseInt(parts[i]), Integer.parseInt(parts[i+1]));
			    this.elements.add(monom);
			}
		}
	}

	public Polynomial(ArrayList<Monomial> e)
	{
		this.elements =e;
	}
	
	public void addElement(Monomial m)
	{
		this.elements.add(m);
	}
	
	public Polynomial addition(Polynomial p)
	{
		for(Monomial m:p.elements)
		{
			int ok=0;
			for(Monomial m1:this.elements)
				if(m.getDegree()==m1.getDegree())
				{
					ok=1;
					m1.add(m);
					break;
				}
			if(ok==0)
				this.elements.add(m);
		}
		return this;
	}
	
	public Polynomial subtraction(Polynomial p)
	{
		for(Monomial m:p.elements)
		{
			int ok=0;
			Monomial m2;
			m2=m;
			for(Monomial m1:this.elements)
				if(m.getDegree()==m1.getDegree())
				{
					ok=1;
					m1.subtract(m);
					m2=m1;
					break;
				}
			if(ok==0)
				this.elements.add(m);
			else 
				if(m2.getCoefficient().doubleValue()==0)
					this.elements.remove(m2);
		}
		return this;
	}
	
	public Polynomial multiply(Polynomial p)
	{
		Polynomial prod=new Polynomial();
		for(Monomial m:this.elements)
		{
			for(Monomial m1:p.elements)
			{
				Monomial res=new IntegerMonomial(m.getCoefficient().intValue()*m1.getCoefficient().intValue(),m.getDegree()+m1.getDegree());
				Polynomial p1=new Polynomial();
				p1.addElement(res);
				prod.addition(p1);
			}
		}
		return prod;
	}
	
	public Polynomial divide(Polynomial p) 
	{
		this.elements.sort((m1, m2)-> (new Integer(m2.getDegree()).compareTo(new Integer(m1.getDegree()))));
		p.elements.sort((m1, m2)-> (new Integer(m2.getDegree()).compareTo(new Integer(m1.getDegree()))));
		Polynomial quotient=new Polynomial();

		while (this.elements.size() > 0 && this.elements.get(0).getDegree() >= p.elements.get(0).getDegree())
		{
			double c1 = this.elements.get(0).getCoefficient().doubleValue();
			double c2 = p.elements.get(0).getCoefficient().doubleValue();
			Polynomial aux = new Polynomial();
			for(Monomial m:p.elements)
				aux.addElement(new RealMonomial(m.getCoefficient().doubleValue()*((double)c1 / c2),m.getDegree())); 
	
			RealMonomial t=new RealMonomial((double)c1/c2,this.elements.get(0).getDegree() - p.elements.get(0).getDegree()); 
			quotient.addElement(t);
			for(Monomial m:aux.elements)
				m.setDegree(t.getDegree()+m.getDegree());

			this.subtraction(aux);
		}
		return quotient;
	}
	
	public Polynomial differentiate()
	{
		for (Iterator<Monomial> iterator = this.elements.iterator(); iterator.hasNext();) 
		{
			Monomial m = iterator.next();
			m.setCoefficient(m.getCoefficient().intValue()*m.getDegree());
			m.setDegree(m.getDegree()-1);
			if(m.getDegree()<0)
				iterator.remove();
		}
		return this;
	}
	
	public Polynomial integrate()
	{
		Polynomial res=new Polynomial();
		for (Iterator<Monomial> iterator = this.elements.iterator(); iterator.hasNext();) 
		{
			Monomial m = iterator.next();
			if(m.getCoefficient().doubleValue()/m.getDegree()%1==0)
				res.addElement(new IntegerMonomial(new Integer(m.getCoefficient().intValue()/(m.getDegree()+1)),m.getDegree()+1));
			else
				res.addElement(new RealMonomial(new Double(m.getCoefficient().doubleValue()/(m.getDegree()+1)),m.getDegree()+1));
		}
		return res;
	}
	
	public String PolynomialToString()
	{
		this.elements.sort((m1, m2)-> (new Integer(m2.getDegree()).compareTo(new Integer(m1.getDegree()))));
		StringBuilder ResPolynomial=new StringBuilder();
		for(Monomial m : this.elements)
		{
			if(!m.getCoefficient().equals(0))
			{
				if(m.getCoefficient().doubleValue()%1!=0)
				{
					if(m.getCoefficient().doubleValue()>0) 
						ResPolynomial.append("+");
					if(m.getCoefficient().doubleValue()!=0 && m.getDegree()>1)
						ResPolynomial.append(m.getCoefficient().toString() + "x^" + m.getDegree());
					else
						if(m.getCoefficient().doubleValue()!=0 && m.getDegree()==1)
						ResPolynomial.append(m.getCoefficient().toString() + "x");
					else
						if(m.getCoefficient().doubleValue()!=0 && m.getDegree()==0)
						ResPolynomial.append(m.getCoefficient().toString());
				}
				if(m.getCoefficient().doubleValue()%1==0)
				{
					if(m.getCoefficient().intValue()>0) 
						ResPolynomial.append(" + ");
					if(m.getCoefficient().intValue()!=0 && m.getDegree()>1)
						ResPolynomial.append(m.getCoefficient().toString() + "x^" + m.getDegree());
					else
						if(m.getCoefficient().intValue()!=0 && m.getDegree()==1)
						ResPolynomial.append(m.getCoefficient().toString() + "x");
					else
						if(m.getCoefficient().intValue()!=0 && m.getDegree()==0)
						ResPolynomial.append(m.getCoefficient().toString());
				}
			}
				
		}
		System.out.println(ResPolynomial.toString());
		if(ResPolynomial.length()==0)
			return "0";
		return ResPolynomial.toString();
	}
}
