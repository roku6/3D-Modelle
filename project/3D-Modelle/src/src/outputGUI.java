
package src;
import  java.util.List;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


/**
 *
 * @author Ekaterina Kuzminykh
 */
public class outputGUI extends javax.swing.JFrame {
	
	//members
	private Container pane =  getContentPane();
	private Dimension size;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable;


    javax.swing.table.DefaultTableModel tm = new javax.swing.table.DefaultTableModel(
    new Object [][] {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
    },
    new String [] {
        "ID", "LenSim", "AngleSim"
    }
            
    ){

   @Override
   public boolean isCellEditable(int row, int column) {
     
       return false;
   }
    };
    
    List<Searchobject> foundList = new ArrayList<Searchobject>();
    List<String>imageList = new ArrayList<String>();

    //construstrors
    public outputGUI() {
    	initComponents();
		Insets insets = getInsets();
		setSize(1600 + insets.left + insets.right,
                900 + insets.top + insets.bottom);
		setVisible(true);
		setResizable(false);		
        
    }
    
  /*  public outputGUI(List<Searchobject> foundList) {
        initComponents();
        this.foundList = foundList;
        tm.setRowCount(foundList.size());
       
        for (int i = 0; i<foundList.size();i++) {
            tm.setValueAt(foundList.get(i).getId1(),i,0);
            tm.setValueAt(foundList.get(i).getLength(),i,1);
            tm.setValueAt(foundList.get(i).getAngle(),i,2);           
        }
        
        drawImage();
        
    }*/
    
    public outputGUI(List<String> imageList) {
    	this.imageList = imageList;
    	initComponents();
		Insets insets = getInsets();
		setSize(1600 + insets.left + insets.right,
                900 + insets.top + insets.bottom);
		setVisible(true);
		setResizable(false);
		drawAll();
    }

    //methods
    private void initComponents() {


        jScrollPane2 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable.setAutoCreateRowSorter(true);
        jTable.setModel(tm);
        jTable.setRowSorter(jTable.getRowSorter());
        jTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable);
        

		pane.setLayout(null);
		
		pane.add(jScrollPane2);
	    jScrollPane2.setBounds(20, 20, 300, 500);

	    pane.add(jTable);
	    size = jTable.getPreferredSize();
	    jTable.setBounds(25, 25, 295, 495);


        pack();
    }
    
    private void drawAll() {

    	for(int i=0;i<imageList.size();i++) {
		javax.swing.JButton jButton = new javax.swing.JButton();
		pane.add(jButton, "jLabel"+i);
 
        jButton.setBounds(400+i*105, i*105, 122, 122);
        



        try {
            java.net.URL imgpath = new java.net.URL("file:///"+imageList.get(i));            
            java.awt.Image img = ImageIO.read(imgpath);
            javax.swing.ImageIcon popupIcon = new javax.swing.ImageIcon(img);
            java.awt.Image resizedImg = img.getScaledInstance(100,100, Image.SCALE_DEFAULT);
            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(resizedImg);
            jButton.setIcon(icon);
            
    		jButton.addActionListener(new java.awt.event.ActionListener() {
    			public void actionPerformed(java.awt.event.ActionEvent evt) {
    				//jButtonActionPerformed(evt);
    				javax.swing.JOptionPane.showMessageDialog(pane, new JLabel(popupIcon),"", javax.swing.JOptionPane.PLAIN_MESSAGE);
    			}
    		});

        } catch (MalformedURLException ex) {
        	jButton.setText("not found-1");
        	System.out.println("AA" + imageList.get(i));
            
        } catch (IOException ex) {
        	jButton.setText("not found-2");
        }
	}
    	
    	
    	
    }
    
	private void jButtonActionPerformed(java.awt.event.ActionEvent evt) {
		
	}
        
        
        
        
        
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
/*
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(outputGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(outputGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(outputGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(outputGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }*/

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new outputGUI().setVisible(true);
            }
        });
    }




}
