package src;
import java.math.*;
import java.util.ArrayList;
import java.util.List;

public class outGuiTest {

	public static void main(String[] args) {

		int numberObjects = (int)(Math.random()*10+40); 
		System.out.println(numberObjects);

		double lenInt = (int)(Math.random()*9+1)+(int)(Math.random()*90)*0.01;
		double angInt = (int)(Math.random()*9+1)+(int)(Math.random()*90)*0.01;
		List<Foundobject> foundList = new ArrayList<Foundobject>();
		String[] pics={"C:\\knn\\StudPro\\pngs\\cube_100x100x100.png","C:\\knn\\StudPro\\pngs\\cube_hole_100x100x100.png",""};
		
		for(int i=0;i<numberObjects;i++) {
			Foundobject f = new Foundobject((Integer)(int)(Math.random()*1000),(double)(int)(Math.random()*lenInt*100)/100,(double)(int)(Math.random()*angInt*100)/100,pics[(int)(Math.random()*pics.length)],null);
			foundList.add(f);
		}
		
		outputGUI outg3 = new outputGUI(foundList,lenInt,angInt);
		outg3.setVisible(true);
		


		

		

	}

}
