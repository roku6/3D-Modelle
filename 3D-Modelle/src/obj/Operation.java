/** 
 * Operation.java
 * (c) Copyright 04-2016 Robert Külpmann
 *  
 *  Mathematische Operationen, für die Generalisierten Number Klassen
 *  - Addition
 *  - Subtraktion
 *  - Multiplitkation
 *  - Division
 *  - Quadrat
 *  - Potenz
 *  - QuadratWurzel
 *  
 *  @author Robert Külpmann
 *  @version 1.0
 *  
 */

package obj;

public abstract class Operation
{

	public static <T extends Number> T set(T value1)
	{
		Class<? extends Number> cls = value1.getClass();
		if (cls == Integer.class)
		{
			return (T) Integer.valueOf(value1.intValue());		
		}
		if (cls == Long.class)
		{
			return (T) Long.valueOf(value1.longValue());		
		}
		if (cls == Float.class)
		{
			return (T) Float.valueOf(value1.floatValue());		
		}
		if (cls == Double.class)
		{
			return (T) Double.valueOf(value1.doubleValue());		
		}
		return value1;
	}

	public static <T extends Number, L extends Number> T multiply2(T value1, L value2)
	{
		Class<? extends Number> cls = value1.getClass();
		Class<? extends Number> cls2 = value2.getClass();
		if (cls == Integer.class && cls2 == Integer.class)
		{
			return (T) Integer.valueOf(value1.intValue() * value2.intValue());		
		}
		else if (cls == Long.class && cls2 == Integer.class)
		{
			return (T) Long.valueOf(value1.longValue() * value2.longValue());		
		}
		else if (cls == Float.class && cls2 == Float.class)
		{
			return (T) Float.valueOf(value1.floatValue() * value2.floatValue());		
		}
		else if (cls == Double.class && cls2 == Double.class)
		{
			return (T) Double.valueOf(value1.doubleValue() * value2.doubleValue());		
		}
		return value1;
	}
	
	public static <T extends Number, L extends Number> T divide2(T value1, L value2)
	{
		Class<? extends Number> cls = value1.getClass();
		Class<? extends Number> cls2 = value2.getClass();
		if (cls == Integer.class && cls2 == Integer.class)
		{
			if (value2.intValue() == 0)	{return (T) Integer.valueOf(0);}
			return (T) Integer.valueOf(value1.intValue() / value2.intValue());		
		}
		if (cls == Long.class && cls2 == Integer.class)
		{
			if (value2.longValue() == 0)	{return (T) Long.valueOf(0);}
			return (T) Long.valueOf(value1.longValue() / value2.longValue());		
		}
		if (cls == Float.class && cls2 == Float.class)
		{
			if (value2.floatValue() == 0)	{return (T) Float.valueOf(0);}
			return (T) Float.valueOf(value1.floatValue() / value2.floatValue());		
		}
		if (cls == Double.class && cls2 == Double.class)
		{
			if (value2.doubleValue() == 0)	{return (T) Double.valueOf(0);}
			return (T) Double.valueOf(value1.doubleValue() / value2.doubleValue());		
		}
		return value1;
	}

	public static <T extends Number> T add2(T value1, T value2)
	{
		Class<? extends Number> cls = value1.getClass();
		Class<? extends Number> cls2 = value2.getClass();
		if (cls == Integer.class && cls2 == Integer.class)
		{
			return (T) Integer.valueOf(value1.intValue() + value2.intValue());		
		}
		if (cls == Long.class && cls2 == Long.class)
		{
			return (T) Long.valueOf(value1.longValue() + value2.longValue());		
		}
		if (cls == Float.class && cls2 == Float.class)
		{
			return (T) Float.valueOf(value1.floatValue() + value2.floatValue());		
		}
		if (cls == Double.class && cls2 == Double.class)
		{
			return (T) Double.valueOf(value1.doubleValue() + value2.doubleValue());		
		}
		return value1;
	}

	public static <T extends Number> T sub2(T value1, T value2)
	{
		Class<? extends Number> cls = value1.getClass();
		Class<? extends Number> cls2 = value2.getClass();
		if (cls == Integer.class && cls2 == Integer.class)
		{
			return (T) Integer.valueOf(value1.intValue() - value2.intValue());		
		}
		else if (cls == Long.class && cls2 == Long.class)
		{
			return (T) Long.valueOf(value1.longValue() - value2.longValue());		
		}
		else if (cls == Float.class && cls2 == Float.class)
		{
			return (T) Float.valueOf(value1.floatValue() - value2.floatValue());		
		}
		else if (cls == Double.class && cls2 == Double.class)
		{
			return (T) Double.valueOf(value1.doubleValue() - value2.doubleValue());		
		}
		return value1;
	}
	
	public static <T extends Number, L extends Number> T powerOf(T value1, L value2)
	{
		Class<? extends Number> cls = value1.getClass();
		Class<? extends Number> cls2 = value2.getClass();
		if (cls == Integer.class && cls2 == Integer.class)
		{
			Double value3 = Math.pow(value1.doubleValue(), value2.doubleValue());
			return (T) Integer.valueOf(value3.intValue());		
		}
		if (cls == Long.class && cls2 == Integer.class)
		{
			Double value3 = Math.pow(value1.doubleValue(), value2.doubleValue());
			return (T) Long.valueOf(value3.longValue());		
		}
		if (cls == Float.class && cls2 == Float.class)
		{
			Double value3 = Math.pow(value1.doubleValue(), value2.doubleValue());
			return (T) Float.valueOf(value3.floatValue() / value2.floatValue());		
		}
		if (cls == Double.class && cls2 == Double.class)
		{
			return (T) Double.valueOf(Math.pow(value1.doubleValue(),  value2.doubleValue()));		
		}
		return value1;
	}
	
	public static <T extends Number, L extends Number> L squareRoot(T value, L length)
	{
		Double temp;
		temp = Math.sqrt(Double.valueOf(value.doubleValue()));
		if (length.getClass() == Integer.class)
		{
			return (L) Integer.valueOf(temp.intValue());					
		}
		else if (length.getClass() == Long.class)
		{
			return (L) Long.valueOf(temp.longValue());					
		}
		else if (length.getClass() == Float.class)
		{
			return (L) Float.valueOf(temp.floatValue());					
		}
		else if (length.getClass() == Double.class)
		{
			return (L) Double.valueOf(temp.doubleValue());					
		}
		return length;
	}

	public static <T extends Number> T squareRoot(T value)
	{
		Double temp;
		temp = Math.sqrt(Double.valueOf(value.doubleValue()));
		if (value.getClass() == Integer.class)
		{
			return (T) Integer.valueOf(temp.intValue());					
		}
		else if (value.getClass() == Long.class)
		{
			return (T) Long.valueOf(temp.longValue());					
		}
		else if (value.getClass() == Float.class)
		{
			return (T) Float.valueOf(temp.floatValue());					
		}
		else if (value.getClass() == Double.class)
		{
			return (T) Double.valueOf(temp.doubleValue());					
		}
		return value;
	}

	public static <T extends Number> T add(T value[])
	{
		T actual = value[0];
		for (int i=1; i<value.length; i++)
		{
			actual = Operation.add2(actual, value[i]);
		}
		return actual;
	}

	public static <T extends Number> T add3(T value1, T value2, T value3)
	{
		value1 = Operation.add2(value1, value2);
		value1 = Operation.add2(value1, value3);
		return value1;		
	}
	
	public static <T extends Number> T add4(T value1, T value2, T value3, T value4)
	{
		value1 = Operation.add3(value1, value2, value3);
		value1 = Operation.add2(value1, value4);
		return value1;		
	}
	
	public static <T extends Number> T square(T number)
	{
		return Operation.multiply2(number, number);
	}	
	
	public static <T extends Number> T multiply3(T value1, T value2, T value3)
	{
		Operation.multiply2(value1, value2);
		Operation.multiply2(value1, value3);		
		return value1;
	}	
}