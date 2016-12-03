//Filename als File

package src;

public class Modelle
{
	public static void main(String [ ] args)
	{
		
		
		BuildLogic aBuildLogic = BuildLogic.getBuildLogic();
		
		aBuildLogic.createOBJ("../../resources/cube_half_100x100x100.obj");
		aBuildLogic.createFigure();
		//System.out.println(aBuildLogic.getaOBJ().toString());
		
		/*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
		 */
		
	}
}