/** 
 * Vector.java
 * (c) Copyright 04-2016 Robert Külpmann
 *  4-Dimensionale Vektorklasse 
 *  
 *  Dependencies:
 *  	Opertation.java
 *  
 *  - x, y, z, w, Betrag 
 *  - Addition, Subtraktion, Kreuzprodukt, Betrag
 *  
 *  @author Robert Külpmann
 *  @version 1.0
 *  
 */

package obj;

public class Vector4 <T extends Number> implements Comparable<Vector4<T>>
{
	private T x;
	private T y;
	private T z;
	private T w;
	
	private T magnitude;
	
	public T getX()			{return x;}
	public T getY()			{return y;}
	public T getZ()			{return z;}
	public T getW()			{return w;}
	public T getMagnitude()	{return magnitude;}
	
	public void setX(T x) 					{this.x = x;}
	public void setY(T y) 					{this.y = y;}
	public void setZ(T z)	 				{this.z = z;}
	public void setW(T w) 					{this.w = w;}
	public void setMagnitude(T magnitude)	{this.magnitude = magnitude;}
	
	public Vector4(){}
	public Vector4(Vector4<T> vector)
	{
		this.x = vector.getX();
		this.y = vector.getY();
		this.z = vector.getZ();
		this.w = vector.getW();
		this.calcMagnitude();
	}	
	public Vector4(T value)
	{
		this.setX(Operation.set(value));		
		this.setY(Operation.set(value));		
		this.setZ(Operation.set(value));		
		this.setW(Operation.set(value));	
		this.calcMagnitude();
	}
	public Vector4(T x, T y, T z, T w)
	{
		this.setX(x);
		this.setY(y);
		this.setZ(z);
		this.setW(w);
		this.calcMagnitude();
	}
	
	public void set(T x, T y, T z, T w)
	{
		this.setX(x);
		this.setY(y);
		this.setZ(z);
		this.setW(w);
		this.calcMagnitude();		
	}
	
	public void Vector2(T x, T y)
	{
		setX(x);
		setY(y);
		if (x.getClass() == Integer.class)  
		{
			setZ((T) Integer.valueOf(0));
			setW((T) Integer.valueOf(0));
		}
		if (x.getClass() == Long.class)  
		{
			setZ((T) Long.valueOf(0L));
			setW((T) Long.valueOf(0L));
		}
		if (x.getClass() == Float.class)  
		{
			setZ((T) Float.valueOf(0.f));
			setW((T) Float.valueOf(0.f));
		}
		if (x.getClass() == Double.class)  
		{
			setZ((T) Double.valueOf(0.0));
			setW((T) Double.valueOf(0.0));
		}
		setMagnitude(Operation.squareRoot(Operation.add2(Operation.square(x), Operation.square(y))));
	}

	public void Vector3(T x, T y)
	{
		setX(x);
		setY(y);
		setZ(y);
		if (x.getClass() == Integer.class)  
		{
			setW((T) Integer.valueOf(0));
		}
		if (x.getClass() == Long.class)  
		{
			setW((T) Long.valueOf(0L));
		}
		if (x.getClass() == Float.class)  
		{
			setW((T) Float.valueOf(0.f));
		}
		if (x.getClass() == Double.class)  
		{
			setW((T) Double.valueOf(0.0));
		}
		setMagnitude(Operation.squareRoot(Operation.add2(Operation.square(x), Operation.square(y))));
	}

	public Vector4<T> sub3(Vector4<T> sub)
	{
		this.x = Operation.sub2(this.getX(), sub.getX());
		this.y = Operation.sub2(this.getY(), sub.getY());
		this.z = Operation.sub2(this.getZ(), sub.getZ());
		this.w = this.getW();
		return this;
	}

	public Vector4<T> sub(Vector4<T> sub)
	{
		this.x = Operation.sub2(this.getX(), sub.getX());
		this.y = Operation.sub2(this.getY(), sub.getY());
		this.z = Operation.sub2(this.getZ(), sub.getZ());
		this.w = Operation.sub2(this.getW(), sub.getW());
		return this;
	}

	public Vector4<T> add(Vector4<T> add)
	{
		if (this.getX() == null || this.getY() == null || this.getZ() == null || this.getW() == null)
		{
			this.set((T)Float.valueOf(0.f),(T)Float.valueOf(0.f),(T)Float.valueOf(0.f),(T)Float.valueOf(0.f));
		}
		
		this.x = Operation.add2(this.getX(), add.getX());
		this.y = Operation.add2(this.getY(), add.getY());
		this.z = Operation.add2(this.getZ(), add.getZ());
		this.w = Operation.add2(this.getW(), add.getW());
		return this;
	}
	
	public Vector4<T> cross(Vector4<T> cross)
	{
		T x, y, z;
		x = Operation.sub2(	Operation.multiply2(this.getY(), cross.getZ()), 
							Operation.multiply2(this.getZ(), cross.getY()));
		y = Operation.sub2(	Operation.multiply2(this.getZ(), cross.getX()), 
							Operation.multiply2(this.getX(), cross.getZ()));
		z = Operation.sub2(	Operation.multiply2(this.getX(), cross.getY()), 
							Operation.multiply2(this.getY(), cross.getX()));
		this.setX(x);
		this.setY(y);
		this.setZ(z);
		return this;
	}	

	public Vector4<T> normalize()
	{
		this.x = Operation.divide2(this.getX(), this.magnitude); 
		this.y = Operation.divide2(this.getY(), this.magnitude);
		this.z = Operation.divide2(this.getZ(), this.magnitude);
		this.w = Operation.divide2(this.getW(), this.magnitude);
		return this;
	}
	
	private void calcMagnitude()
	{
		this.setMagnitude(Operation.powerOf(	
											Operation.add4(	
															Operation.square(x),
															Operation.square(y),
															Operation.square(z),
															Operation.square(w)
															),
				 							(T) Float.valueOf(1.f/4.f)));		
	}
	
	@Override
	public int compareTo(Vector4<T> aVector4)
	{
		int comparisonX = (double)this.getX() > (double)aVector4.getX() ? 1 : 
							(double)this.getX() < (double)aVector4.getX() ? -1 : 0; 
		if (comparisonX != 0) return comparisonX;
		else 
		{
			int comparisonY = (double)this.getY() > (double)aVector4.getY() ? 1 : 
								(double)this.getY() < (double)aVector4.getY() ? -1 : 0; 
			if (comparisonY != 0) return comparisonY;
			else
				return (double)this.getZ() > (double)aVector4.getZ() ? 1 : 
						(double)this.getZ() < (double)aVector4.getZ() ? -1 : 0; 
		}

	}
}
