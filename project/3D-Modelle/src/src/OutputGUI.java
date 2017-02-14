
package src;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLayeredPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;


/**
 *
 * @author Ekaterina Kuzminykh
 */
@SuppressWarnings("serial")
public class OutputGUI extends javax.swing.JFrame {

	// members
	private int width;
	private int height;
	Insets insets;
	private JLayeredPane pane;
	private Dimension size;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTable jTable;
	private double lenInt;
	private double angInt;
	private int imgsize;
	private int mPleft;
	private int mPtop;
	private int mPright;
	private int mPbottom;
	private List<Foundobject> foundList;
	private Image defimg;
	private Graphics g;
	private List<javax.swing.JButton> butArr;
	private JPanel mPanel;
	private javax.swing.table.DefaultTableModel tm;
	private javax.swing.JLabel numberFoundLabel;
	private int hcenter;
	private int vcenter;
	private Runtime r;
	

	// construstrors

	/**
	 * Constructor Displays a message if no objects were found and an output GUI
	 * window else. The table model for the table on the left is set here
	 * because it needs to know the foundList size before the initialization of
	 * the table.
	 * 
	 * @param foundList
	 * @param lenInt
	 * @param angInt
	 * @param width
	 * @param height
	 */
	public OutputGUI(List<Foundobject> foundList, double lenInt, double angInt, int width, int height) {
		r = Runtime.getRuntime();
		if (foundList.size() == 0) {
			javax.swing.JOptionPane.showMessageDialog(this, "Es wurden keine Objekte gefunden.",
					"keine Objekte gefunden", javax.swing.JOptionPane.ERROR_MESSAGE);
			dispose();
			setVisible(false);
		}

		else {

			pane = new JLayeredPane();

			this.lenInt = lenInt;
			this.angInt = angInt;
			this.foundList = foundList;
			this.height = height;
			this.width = width;

			setContentPane(pane);

			setSize(width /* + insets.left + insets.right */,
					height/* + insets.top + insets.bottom */);

			initComponents();
			
			tm.setRowCount(foundList.size());
			tm.setColumnCount(3);
			for (int i = 0; i < foundList.size(); i++) {
				tm.setValueAt(foundList.get(i).getId(), i, 0);
				tm.setValueAt(foundList.get(i).getLenSim(), i, 1);
				tm.setValueAt(foundList.get(i).getAngSim(), i, 2);
			}

			setVisible(true);
			setResizable(false);
			drawAll();
		}

	}

	/**
	 * Initializes the components apart from the pictures. Creates the table on
	 * the left and the background coordinate system Sets the image size and the
	 * font size in the coordinate system proportionally to window size Creates
	 * a default image to display when no image is found
	 */
	private void initComponents() {
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		
		
	
		

		insets = new Insets(20, 20, 45, 25);

		imgsize = (int) (getHeight() / 6);
		butArr = new ArrayList<javax.swing.JButton>(foundList.size());

		// default image
		defimg = new BufferedImage(imgsize, imgsize, java.awt.image.BufferedImage.TYPE_INT_ARGB);
		g = defimg.getGraphics();
		g.setColor(Color.white);
		g.fillRect(imgsize / 4, imgsize / 4, imgsize / 2, imgsize / 2);
		g.setColor(Color.black);
		g.drawString("Kein", imgsize / 3, imgsize * 1 / 2);
		g.drawString("Bild", imgsize / 3, imgsize * 1 / 2 + 15);

		g.drawLine(imgsize / 4, imgsize / 4, imgsize * 3 / 4, imgsize / 4);
		g.drawLine(imgsize / 4, imgsize * 3 / 4, imgsize * 3 / 4, imgsize * 3 / 4);

		g.drawLine(imgsize / 4, imgsize / 4, imgsize / 4, imgsize * 3 / 4);
		g.drawLine(imgsize * 3 / 4, imgsize / 4, imgsize * 3 / 4, imgsize * 3 / 4);
	

		mPanel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				int fontsize = 1 + width / 100;

				g.setFont(new Font("default", Font.BOLD, fontsize));
				// Draw Text
				int w = getWidth() / 2;
				int h = getHeight() / 2;

				// x-Axis
				g.drawLine(imgsize / 2, h, getWidth() - imgsize / 2, h);
				// y-Axis
				g.drawLine(w, imgsize / 2, w, getHeight() - imgsize / 2);

				// create hatch marks for x axis.
				for (int i = 0; i < 11; i++) {
					int y = h;
					int x = w + i * (getWidth() - imgsize) / 20;
					g.drawLine(x, y, x, y + 5);
					String mark = new BigDecimal(String.valueOf((double) i * lenInt / 10))
							.setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();

					g.setColor(Color.blue);

					g.drawString(mark, x, y - 2);

					x = w - i * (getWidth() - imgsize) / 20;
					g.drawLine(x, y, x, y + 5);
					g.drawString(mark.toString(), x, y - 2);
				}
			

				// create hatch marks for y axis.
				for (int i = 0; i < 11; i++) {
					int x = w;
					int y = h + i * (getHeight() - imgsize) / 20;
					g.drawLine(x - 5, y, x, y);
					String mark = new BigDecimal(String.valueOf((double) i * angInt / 10))
							.setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
					if (i != 0)
						g.drawString(mark, x + 2, y);

					y = h - i * (getHeight() - imgsize) / 20;
					g.drawLine(x - 5, y, x, y);
					if (i != 0)
						g.drawString(mark.toString(), x + 2, y);

				}

			}
		};
		

		tm = new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] { "ID", "LenSim", "AngleSim" }
		) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			// makes table sortable
			@Override
			public Class<?> getColumnClass(int column) {
				if (column == 0) {
					return Integer.class;
				}
				if (column == 1 || column == 2) {
					return Double.class;
				}
				return super.getColumnClass(column);
			}
		};

		jScrollPane2 = new javax.swing.JScrollPane();
		jTable = new javax.swing.JTable();

		numberFoundLabel = new javax.swing.JLabel();
		numberFoundLabel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		numberFoundLabel.setVerticalAlignment(javax.swing.JTextField.CENTER);
		if (foundList.size() == 1) {
			numberFoundLabel.setText("1 Objekt gefunden.");
		} else {
			numberFoundLabel.setText(foundList.size() + " Objekte gefunden.");
		}

		pane.setLayout(null);

		pane.add(numberFoundLabel);
		size = numberFoundLabel.getPreferredSize();
		numberFoundLabel.setBounds(insets.left + (int) (getWidth() / 15), insets.top, size.width, size.height);

		pane.add(jScrollPane2);
		jScrollPane2.setBounds(insets.left, insets.top + 25, (int) (getWidth() / 5),
				getHeight() - insets.top - insets.bottom - 25);

		pane.add(jTable);
		jTable.setBounds(insets.left + 5, insets.top + 30, (int) (getWidth() / 5) - 5,
				getHeight() - insets.top - insets.bottom - 30);

		jTable.setAutoCreateRowSorter(true);
		jTable.setModel(tm);
		jTable.setRowSorter(jTable.getRowSorter());
		jTable.getTableHeader().setReorderingAllowed(false);
		jScrollPane2.setViewportView(jTable);
		jTable.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
			@Override
			public void valueChanged(javax.swing.event.ListSelectionEvent event) {
				if (jTable.getSelectedRow() > -1) {
					for (int i = 0; i < foundList.size() && butArr.get(i) != null; i++) {
						
						butArr.get(i).setBorderPainted(false);
						butArr.get(i).getParent().setComponentZOrder(butArr.get(i), 1);
						if (foundList.get(i).getId() == jTable.getValueAt(jTable.getSelectedRow(), 0)) {
							butArr.get(i).getParent().setComponentZOrder(butArr.get(i), 0);
							butArr.get(i).setBorderPainted(true);
							
							jTable.requestFocusInWindow();
						}
					}
				}
			}
		});

		jTable.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				if(butArr!=null)
					for (int i = 0; i < butArr.size() ; i++) 
						if(butArr.get(i)!=null)
							butArr.get(i).setBorderPainted(false);
				// mPanel.getParent().setComponentZOrder(mPanel, 0);
			}
		});

		jTable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					if (jTable.getSelectedRow() > -1) {
						for (int i = 0; i < foundList.size() && butArr.get(i) != null; i++) {
							if (foundList.get(i).getId() == jTable.getValueAt(jTable.getSelectedRow(), 0)) {
								ArrayList<JButton> overlappingButtons = new ArrayList<JButton>();
								ArrayList<String> popupIcons = new ArrayList<String>();
								overlappingButtons.add(butArr.get(i));
								popupIcons.add((String) butArr.get(i).getClientProperty("popupPath"));
								jButtonActionPerformed(overlappingButtons, popupIcons);
							}
						}

					}
				}
			}
		});

		pane.add(mPanel);

		mPleft = (int) (getWidth() / 5) + 2 * insets.left;
		mPtop = insets.top;

		mPanel.setBounds(mPleft, mPtop, (int) (getWidth() * 0.8) - 2 * insets.left - insets.right,
				getHeight() - insets.top - insets.bottom);
		mPright = mPleft + (int) (getWidth() * 0.72);
		mPbottom = mPtop + (int) (getHeight() * 0.85);
		
		 hcenter = mPleft + mPanel.getWidth() / 2;
		 vcenter = mPtop + mPanel.getHeight() / 2;


		mPanel.setBackground(new Color(0, 0, 0, 0));

		mPanel.setBorder(BorderFactory.createLineBorder(Color.black));

	}
	
	
	
	
	private void drawOneButton(int i) {
		
		javax.swing.JButton jButton = new javax.swing.JButton() {
			// draws the ID of the object into the picture
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawString(java.util.Objects.toString(this.getName(), ""), getWidth() / 2, getHeight() / 2);
				
			}
		};
		
		butArr.add(jButton);
		pane.add(jButton, "jLabel" + i);
		// pane.setLayer(jButton, 0);

		int hoffset = (int) (foundList.get(i).getLenSim() / lenInt * (mPanel.getWidth() - imgsize) / 2);
		int voffset = (int) (foundList.get(i).getAngSim() / angInt * (mPanel.getHeight() - imgsize) / 2);
		// System.out.println(foundList.get(i).getId()+" "+voffset);

		// possible locations of the button in each quarter counterclockwise
		Rectangle bounds1 = new Rectangle(hcenter - imgsize / 2 + hoffset, vcenter - imgsize / 2 + voffset, imgsize+5,
				imgsize+5);
		Rectangle bounds2 = new Rectangle(hcenter - imgsize / 2 - hoffset, vcenter - imgsize / 2 + voffset, imgsize,
				imgsize);
		Rectangle bounds3 = new Rectangle(hcenter - imgsize / 2 - hoffset, vcenter - imgsize / 2 - voffset, imgsize,
				imgsize);
		Rectangle bounds4 = new Rectangle(hcenter - imgsize / 2 + hoffset, vcenter - imgsize / 2 - voffset, imgsize,
				imgsize);

		Map<Rectangle, Integer> treeMap = new HashMap<Rectangle, Integer>();
		treeMap.put(bounds1, numberOverlaps(bounds1));
		treeMap.put(bounds2, numberOverlaps(bounds2));
		treeMap.put(bounds3, numberOverlaps(bounds3));
		treeMap.put(bounds4, numberOverlaps(bounds4));

		List<java.util.Map.Entry<Rectangle, Integer>> list = new LinkedList<>(treeMap.entrySet());
		Collections.sort(list, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				return ((Comparable<Integer>) ((Map.Entry<Rectangle, Integer>) (o1)).getValue())
						.compareTo(((Map.Entry<Rectangle, Integer>) (o2)).getValue());
			}
		});

		Rectangle wantedBounds = list.get(0).getKey();
		jButton.setBounds(wantedBounds);
		

		jButton.setOpaque(false);
		jButton.setContentAreaFilled(false);
		jButton.setBorder(new javax.swing.border.LineBorder(Color.red,2));
		
	
		jButton.setBorderPainted(false);
		jButton.setFocusPainted(false);

		jButton.setName(java.util.Objects.toString((foundList.get(i).getId()), ""));
		


		java.awt.Image img;
		
		javax.swing.ImageIcon popupIcon = new javax.swing.ImageIcon(foundList.get(i).getPic());
		img = popupIcon.getImage();
		jButton.putClientProperty("popupPath", foundList.get(i).getPic());

		if(img.getWidth(null)<1) {
			img=defimg;
			jButton.putClientProperty("popupPath","defimg" );
		}
		
	
		

		// keep proportions, calculate min scaling factor
		Dimension sourceDim = new Dimension(img.getWidth(null), img.getHeight(null));

		Dimension destinationDim = new Dimension(imgsize, imgsize);
		double scaleX = 0;
		double scaleY = 0;
		scaleX = destinationDim.getWidth() / sourceDim.getWidth();
		scaleY = destinationDim.getHeight() / sourceDim.getHeight();
		double scale = Math.min(scaleX, scaleY);

		java.awt.Image resizedImg = img.getScaledInstance((int) (sourceDim.getWidth() * scale),
				(int) (sourceDim.getHeight() * scale), Image.SCALE_FAST);
		

		javax.swing.ImageIcon icon = new javax.swing.ImageIcon(resizedImg);
		//icon = defImg;
		jButton.setIcon(icon);
		
		img.flush();
		img=null;
		resizedImg.flush();
		resizedImg = null;
		
		
		
		jButton.putClientProperty("description", foundList.get(i).getDescr());
		jButton.putClientProperty("x", wantedBounds.getMinX() / getWidth());
		jButton.putClientProperty("y", wantedBounds.getMinY() / getHeight());

		jButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				Rectangle bounds = jButton.getBounds();

				ArrayList<JButton> overlappingButtons = new ArrayList<JButton>();
				ArrayList<String> popupPaths = new ArrayList<String>();
				overlappingButtons.add(jButton);
				popupPaths.add((String)jButton.getClientProperty("popupPath"));
				for (int i = 0; i < butArr.size() && butArr.get(i) != null; i++)
					if (overlap(bounds, butArr.get(i).getBounds()) && butArr.get(i) != jButton) {
						overlappingButtons.add(butArr.get(i));
						popupPaths.add((String)butArr.get(i).getClientProperty("popupPath"));
					}

				jButtonActionPerformed(overlappingButtons, popupPaths);

			}
		});

		jButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				jButton.getParent().setComponentZOrder(jButton, 0);

			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				jButton.getParent().setComponentZOrder(mPanel, 0);

			}

		});

		
		
	}

	private void drawAll() {

		// calculate borders and center of coordinate system

		int hcenter = mPleft + mPanel.getWidth() / 2;
		int vcenter = mPtop + mPanel.getHeight() / 2;

		for (int i = 0; i < foundList.size(); i++) {
			drawOneButton(i);

		}

	}

	private void jButtonActionPerformed(ArrayList<JButton> buttons, ArrayList<String> icons) {
		if (buttons.size() == 1) {
			String descr = java.util.Objects.toString(buttons.get(0).getClientProperty("description"),
					"keine Beschreibung");
			JLabel label = new JLabel();
			
			if( icons.get(0)=="defimg") {
				label.setIcon(buttons.get(0).getIcon());
				System.out.println("1");
			}
			else {
				//scale for small monitors
				javax.swing.ImageIcon labelIcon = new javax.swing.ImageIcon(icons.get(0));
				if(labelIcon.getIconWidth()>width-150 || labelIcon.getIconHeight()>height-150) {
					Image img = labelIcon.getImage();
					Dimension sourceDim = new Dimension(img.getWidth(null), img.getHeight(null));
					Dimension destinationDim = new Dimension(width-150, height-150);
					double scaleX = 0;
					double scaleY = 0;
					scaleX = destinationDim.getWidth() / sourceDim.getWidth();
					scaleY = destinationDim.getHeight() / sourceDim.getHeight();
					double scale = Math.min(scaleX, scaleY);
					java.awt.Image resImg = img.getScaledInstance((int) (sourceDim.getWidth() * scale),
						(int) (sourceDim.getHeight() * scale), Image.SCALE_FAST);
					labelIcon.setImage(resImg);
					img.flush();
					img=null;
					resImg.flush();
					resImg=null;
				}
				label.setIcon(labelIcon);
				System.out.println("2");
			}
			label.setHorizontalTextPosition(JLabel.CENTER);
			label.setVerticalTextPosition(JLabel.BOTTOM);
			label.setText("ID " + buttons.get(0).getName() + ", " + descr);
			javax.swing.JOptionPane.showMessageDialog(pane, label, buttons.get(0).getName(),
					javax.swing.JOptionPane.PLAIN_MESSAGE);
		} 
		
		else {
			System.out.println(buttons.size() );
			JPanel l = new JPanel();

			for (int i = 0; i < buttons.size(); i++) {
				String name = buttons.get(i).getName();
				String descr = java.util.Objects.toString(buttons.get(i).getClientProperty("description"),
						"keine Beschreibung");

				JLabel label = new JLabel(buttons.get(i).getIcon()) {

					private static final long serialVersionUID = 1L;

					// draws the ID of the object into the picture
					@Override
					public void paintComponent(Graphics g) {
						super.paintComponent(g);
						g.drawString(java.util.Objects.toString(name, ""), getWidth() / 2, getHeight() / 2);
					}
				};
				
				

				

				javax.swing.ImageIcon ic;
				if(buttons.get(i).getClientProperty("popupPath")=="defimg") {
					ic=(ImageIcon) buttons.get(i).getIcon();
					
				}
				
				else {
					ic = new javax.swing.ImageIcon((String)buttons.get(i).getClientProperty("popupPath"));
					//scale for small monitors
				
					if(ic.getIconWidth()>width-150 ||ic.getIconHeight()>height-150) {
						Image img = ic.getImage();
						Dimension sourceDim = new Dimension(img.getWidth(null), img.getHeight(null));
						Dimension destinationDim = new Dimension(width-150, height-150);
						double scaleX = 0;
						double scaleY = 0;
						scaleX = destinationDim.getWidth() / sourceDim.getWidth();
						scaleY = destinationDim.getHeight() / sourceDim.getHeight();
						double scale = Math.min(scaleX, scaleY);
						java.awt.Image resImg = img.getScaledInstance((int) (sourceDim.getWidth() * scale),
							(int) (sourceDim.getHeight() * scale), Image.SCALE_FAST);
						ic.setImage(resImg);
						img.flush();
						img=null;
						resImg.flush();
						resImg=null;
					}
					
			
				}
				
			
				
				label.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						JLabel label = new JLabel(ic);
						label.setHorizontalTextPosition(JLabel.CENTER);
						label.setVerticalTextPosition(JLabel.BOTTOM);
						label.setText("ID: " + name + ", " + descr);
						

						javax.swing.JOptionPane.showMessageDialog(pane, label, name,
								javax.swing.JOptionPane.PLAIN_MESSAGE);

					}
				});
				l.add(label);

			}
			Dimension d = new Dimension((int) (imgsize * Math.ceil(Math.sqrt(buttons.size()) + 1)),
					(int) (imgsize * Math.round(Math.sqrt(buttons.size()))));
			l.setPreferredSize(d);

			if (d.getWidth() * d.getHeight() > width * height * 4 / 9) {
				int w =width/2;				
				double picsHor = w/imgsize;
				System.out.println("w "+w +"imgsize "+imgsize+"picsHor"+ picsHor);
				d.setSize(w-100,imgsize*Math.ceil(buttons.size()/picsHor)+50);
				System.out.println("was: "+(int) (imgsize * Math.ceil(Math.sqrt(buttons.size()) + 1))+" * "+ (int) (imgsize * Math.round(Math.sqrt(buttons.size()))));
				System.out.println("now: "+w+" * "+d.getHeight());
				System.out.println(picsHor);
				l.setPreferredSize(d);
				System.out.println(l.getPreferredSize());
				l.setSize(d);
				l.setBorder(BorderFactory.createLineBorder(Color.red,3));
				

				javax.swing.JScrollPane scr = new javax.swing.JScrollPane(l);
				Dimension d2 = new Dimension(width/2, height / 2);
				scr.setPreferredSize(d2);
				javax.swing.JOptionPane.showMessageDialog(pane, scr, "", javax.swing.JOptionPane.PLAIN_MESSAGE);
				System.out.println("scrsize:" + scr.getSize());

			}
			
			
			else
				javax.swing.JOptionPane.showMessageDialog(pane, l, "", javax.swing.JOptionPane.PLAIN_MESSAGE);

			// javax.swing.JOptionPane diapane = new javax.swing.JOptionPane();
			// diapane.setSize((int)(imgsize*Math.ceil(Math.sqrt(buttons.size()))),(int)(imgsize*Math.ceil(Math.sqrt(buttons.size()))));
			// diapane.showMessageDialog(pane, l,"",
			// javax.swing.JOptionPane.PLAIN_MESSAGE);
			// javax.swing.JDialog dialog = diapane.createDialog(pane, l,"",
			// javax.swing.JOptionPane.PLAIN_MESSAGE);
			// dialog.show();

			

		}

	}

	// check if overlap is bigger than 1/2 of imgsize
	private boolean overlap(Rectangle a, Rectangle b) {
		Rectangle inter = a.intersection(b);

		if (inter.height > 0 && inter.width > 0 && (inter.height > imgsize / 2 || inter.width > imgsize / 2))
			return true;
		return false;

	}

	// returns the number of overlapping buttons for a rectangle
	private int numberOverlaps(Rectangle a) {
		int count = 0;
		for (int j = 0; j < butArr.size() && butArr.get(j) != null; j++) {
			Rectangle b = butArr.get(j).getBounds();
			if (overlap(a, b))
				count++;
		}
		return count;
	}
	

	private OutputGUI getgui() {
		return this;
	
	}
	
	@Override
	public void dispose() {
		if(butArr!=null) {	
		for(JButton b : butArr) {
			
			b.setIcon(null);	
			b.removeAll();
			java.awt.event.ActionListener l = b.getActionListeners()[0];
			b.removeActionListener(l);
			b.invalidate();
			b=null;			
		}
		butArr.clear();
		butArr =null;
		mPanel.removeAll();
		pane.removeAll();
		defimg  =null;
		}
		System.gc();		
		r.gc();
		System.runFinalization();
		r.runFinalization();
		System.gc();
		r.gc();
		super.dispose();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				// new outputGUI().setVisible(true);
			}
		});
	}

}
