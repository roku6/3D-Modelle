package obj;

import java.util.ArrayList;

public class Face 
{
	int id;
	ArrayList<Integer> pointList;
	
	Face()
	{
		
	}
	
	Face(int p1, int p2, int p3, int p4)
	{
		pointList.add(p1);
		pointList.add(p2);
		pointList.add(p3);
		pointList.add(p4);
	}
}
