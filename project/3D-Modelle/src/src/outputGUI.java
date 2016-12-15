
package src;
import  java.util.List;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


/**
 *
 * @author Ekaterina Kuzminykh
 */
public class outputGUI extends javax.swing.JFrame {
	
	//members
    private javax.swing.JLabel imgLabel;
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

    //construstrors
    public outputGUI() {
        initComponents();
    }
    
    public outputGUI(List<Searchobject> foundList) {
        initComponents();
        this.foundList = foundList;
        tm.setRowCount(foundList.size());
       
        for (int i = 0; i<foundList.size();i++) {
            tm.setValueAt(foundList.get(i).getId1(),i,0);
            tm.setValueAt(foundList.get(i).getLength(),i,1);
            tm.setValueAt(foundList.get(i).getAngle(),i,2);           
        }
        
        drawImage();
        
    }

    //methods
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        imgLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable.setAutoCreateRowSorter(true);
        jTable.setModel(tm);
        jTable.setRowSorter(jTable.getRowSorter());
        jTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158)
                .addComponent(imgLabel)
                .addContainerGap(483, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                .addGap(124, 124, 124))
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(imgLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }
    
    public void drawImage() {
        
        try {
            java.net.URL imgpath = new java.net.URL("file:///C:/Studpro/"+"0"+".png");            
            java.awt.Image img = ImageIO.read(imgpath);
            java.awt.Image resizedImg = img.getScaledInstance(400,400, Image.SCALE_DEFAULT);
            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(resizedImg);
            imgLabel.setIcon(icon);
        } catch (MalformedURLException ex) {
            imgLabel.setText("file:///C:/Studpro/"+jTable.getSelectedRows()+".png");
            
        } catch (IOException ex) {
            imgLabel.setText("file:///C:/Studpro/"+jTable.getSelectedRows()+".png");
        }
        
        
        
        
        
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
