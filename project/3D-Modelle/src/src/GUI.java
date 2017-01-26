package src;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FilenameFilter;
import java.awt.Container;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.util.Objects;

// TODO: Auto-generated Javadoc
/**
 * The Class GUI.
 */
public class GUI extends javax.swing.JFrame {

	// members
	private javax.swing.JButton addButton;
	private javax.swing.JLabel angleIntervalLabel;
	private javax.swing.JTextField angleIntervalTF;
	private javax.swing.JLabel angleLabel;
	private javax.swing.JTextField angleTF;
	private javax.swing.JLabel edgeLabel;
	private javax.swing.JTextField edgeTF;
	private javax.swing.JButton editButton;
	private javax.swing.JList inputList;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JLabel lenIntervalLabel;
	private javax.swing.JTextField lenIntervalTF;
	private javax.swing.JLabel lenLabel;
	private javax.swing.JTextField lenTF;
	private javax.swing.JButton readDirButton;
	private javax.swing.JButton readFileButton;
	private javax.swing.JButton removeButton;
	private javax.swing.JButton searchButton;
	private javax.swing.JLabel toEdgeLabel;
	private javax.swing.JTextField toEdgeTF;
	private javax.swing.DefaultListModel listModel = new javax.swing.DefaultListModel();
	private List<Searchobject> searchList = new ArrayList<Searchobject>();
	private javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
	private javax.swing.filechooser.FileNameExtensionFilter objFilter = new javax.swing.filechooser.FileNameExtensionFilter(
			"OBJ-Dateien", "obj");
	private Double lenInterval = 0.0;
	private Double angleInterval = 0.0;
	private boolean editClicked = false;
	private int index = -1;
	private javax.swing.JLabel outputLabel;
	private javax.swing.JLabel widthLabel;
	private javax.swing.JLabel heightLabel;
	private javax.swing.JTextField widthTF;
	private javax.swing.JTextField heightTF;

	/**
	 * Constructor
	 */
	public GUI() {

		initComponents();
		Insets insets = getInsets();
		setSize(830 + insets.left + insets.right,
                400 + insets.top + insets.bottom);
		setVisible(true);
		setResizable(false);


	}

	private void initComponents() {

		edgeLabel = new javax.swing.JLabel();
		lenLabel = new javax.swing.JLabel();
		lenTF = new javax.swing.JTextField();
		angleLabel = new javax.swing.JLabel();
		angleTF = new javax.swing.JTextField();
		toEdgeLabel = new javax.swing.JLabel();
		jScrollPane2 = new javax.swing.JScrollPane();
		inputList = new javax.swing.JList();
		searchButton = new javax.swing.JButton();
		addButton = new javax.swing.JButton();
		editButton = new javax.swing.JButton();
		edgeTF = new javax.swing.JTextField();
		toEdgeTF = new javax.swing.JTextField();
		removeButton = new javax.swing.JButton();
		readFileButton = new javax.swing.JButton();
		readDirButton = new javax.swing.JButton();
		lenIntervalLabel = new javax.swing.JLabel();
		lenIntervalTF = new javax.swing.JTextField();
		angleIntervalLabel = new javax.swing.JLabel();
		angleIntervalTF = new javax.swing.JTextField();
		outputLabel = new javax.swing.JLabel();
		widthLabel = new javax.swing.JLabel();
		heightLabel = new javax.swing.JLabel();
		widthTF = new javax.swing.JTextField();
		heightTF = new javax.swing.JTextField();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Input-GUI");

		edgeLabel.setText("Kante:");

		lenLabel.setText("Laenge:");

		lenTF.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				lenTFActionPerformed(evt);
			}
		});

		angleLabel.setText("Winkel:");

		angleTF.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				angleTFActionPerformed(evt);
			}
		});

		toEdgeLabel.setText("zur Kante:");

		inputList.setModel(listModel);// {

		inputList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jScrollPane2.setViewportView(inputList);

		searchButton.setText("Suchen");
		searchButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				searchButtonActionPerformed(evt);
			}
		});

		addButton.setText("hinzufuegen");
		addButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addButtonActionPerformed(evt);
			}
		});

		editButton.setText("Kante bearbeiten");
		editButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editButtonActionPerformed(evt);
			}
		});

		edgeTF.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				edgeTFActionPerformed(evt);
			}
		});
		
		edgeTF.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				edgeTFFocusLost(evt);
			}
		});

		toEdgeTF.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				toEdgeTFActionPerformed(evt);
			}
		});

		removeButton.setText("Kante loeschen");
		removeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeButtonActionPerformed(evt);
			}
		});

		readFileButton.setText("Datei einlesen");
		readFileButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				readFileButtonActionPerformed(evt);
			}
		});

		readDirButton.setText("Ordner einlesen");
		readDirButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				readDirButtonActionPerformed(evt);
			}
		});

		lenIntervalLabel.setText("Laengenintervall:");

		lenIntervalTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		lenIntervalTF.setText("0");
		lenIntervalTF.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				lenIntervalTFFocusLost(evt);
			}
		});
		lenIntervalTF.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				lenIntervalTFActionPerformed(evt);
			}
		});

		angleIntervalLabel.setText("Winkelintervall:");

		angleIntervalTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		angleIntervalTF.setText("0");
		angleIntervalTF.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				angleIntervalTFFocusLost(evt);
			}
		});
		angleIntervalTF.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				angleIntervalTFActionPerformed(evt);
			}
		});
			
			outputLabel.setText("Ausgabefenster:");
			outputLabel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			widthLabel.setText("Breite");
			widthLabel.setVerticalAlignment(javax.swing.JTextField.BOTTOM);
			heightLabel.setText("Hoehe");
			heightLabel.setVerticalAlignment(javax.swing.JTextField.BOTTOM);
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int width = (int)screenSize.getWidth()-100;
			int height = (int)screenSize.getHeight()-100;
			widthTF.setText(java.util.Objects.toString(width));
			widthTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			heightTF.setText(java.util.Objects.toString(height));
			heightTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		
		
		//pack();
		
		//Layout
		

		
		Container pane =  getContentPane();
		Dimension size;
		pane.setLayout(null);
			    
	    pane.add(angleIntervalLabel);
	    size = angleIntervalLabel.getPreferredSize();
	    angleIntervalLabel.setBounds(230, 60, size.width, size.height);

	    pane.add(angleIntervalTF);
	    size = angleIntervalTF.getPreferredSize();
	    angleIntervalTF.setBounds(330, 58, 50, size.height);
	    
	    pane.add(lenIntervalLabel);
	    size = lenIntervalLabel.getPreferredSize();
	    lenIntervalLabel.setBounds(65, 60, size.width, size.height);

	    pane.add( lenIntervalTF);
	    size =  lenIntervalTF.getPreferredSize();
	    lenIntervalTF.setBounds(165, 58, 50, size.height);
	    
	    pane.add(edgeLabel);
	    size = edgeLabel.getPreferredSize();
	    edgeLabel.setBounds(60, 360, size.width, size.height);
	    
	    pane.add(edgeTF);
	    size = edgeTF.getPreferredSize();
	    edgeTF.setBounds(110, 357, 37, 21);
	    
	    pane.add(lenLabel);
	    size = lenLabel.getPreferredSize();
	    lenLabel.setBounds(180, 360, size.width, size.height);

	    pane.add(lenTF);
	    size = lenTF.getPreferredSize();
	    lenTF.setBounds(240, 357 ,37 , 21);
	    
	    pane.add(angleLabel);
	    size = angleLabel.getPreferredSize();
	    angleLabel.setBounds(315, 360, size.width, size.height);
	    
	    pane.add(angleTF);
	    size = angleTF.getPreferredSize();
	    angleTF.setBounds(370,357 ,37 , 21);
	    
	    pane.add(toEdgeLabel);
	    size = toEdgeLabel.getPreferredSize();
	    toEdgeLabel.setBounds(440, 360, size.width, size.height);

	    pane.add(toEdgeTF);
	    size = toEdgeTF.getPreferredSize();
	    toEdgeTF.setBounds(507,357 ,37 , 21);
	    
	    pane.add(inputList);
	    size = inputList.getPreferredSize();
	    inputList.setBounds(67, 87, 310,215);

	    pane.add( jScrollPane2);
	    size =  jScrollPane2.getPreferredSize();
	     jScrollPane2.setBounds(65, 85, 315,220);

		pane.add(addButton);
	    size = addButton.getPreferredSize();
	    addButton.setBounds(580, 352, size.width, size.height);

	    pane.add(editButton);
	    size = editButton.getPreferredSize();
	    editButton.setBounds(400, 85, size.width, size.height);
	    
	    pane.add( removeButton);
	     removeButton.setBounds(400, 125, size.width, size.height);


	    pane.add(readDirButton);
	    size = readDirButton.getPreferredSize();
	    readDirButton.setBounds(610,125 , size.width, size.height);

	    pane.add(readFileButton);
	    readFileButton.setBounds(610, 85, size.width, size.height);



	    pane.add(searchButton);
	    size = searchButton.getPreferredSize();
	    searchButton.setBounds(400, 280, size.width, size.height);
	    
	    pane.add(outputLabel);
	    size = outputLabel.getPreferredSize();
	    outputLabel.setBounds(500, 280,  size.width, size.height);
	    
	    pane.add(widthLabel);
	    size = widthLabel.getPreferredSize();
	    widthLabel.setBounds(600, 260,  size.width, size.height);
	    
	    pane.add(heightLabel);
	    size = heightLabel.getPreferredSize();
	    heightLabel.setBounds(670, 260,  size.width, size.height);
	    
	    pane.add(widthTF);
	    size = widthTF.getPreferredSize();
	    widthTF.setBounds(600, 280,  size.width+20, size.height);
	    
	    pane.add(heightTF);
	    size = heightTF.getPreferredSize();
	    heightTF.setBounds(670, 280,  size.width+20, size.height);
	    
	    
	    
	    
	    



		pack();
	}

	/**
	 * Angle TF action performed.
	 *
	 * @param evt
	 *            the evt
	 */
	private void angleTFActionPerformed(java.awt.event.ActionEvent evt) {
		addSearchobject(searchList.size());
	}

	/**
	 * Search button action performed.
	 *
	 * @param evt
	 *            the evt
	 */
	private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
		searchSearchobject();
		//remove later, test!
		int numberObjects = (int)(Math.random()*10+40); 
		System.out.println(numberObjects);

		double lenInt = (int)(Math.random()*9+1)+(int)(Math.random()*90)*0.01;
		double angInt = (int)(Math.random()*9+1)+(int)(Math.random()*90)*0.01;
		List<Foundobject> foundList = new ArrayList<Foundobject>();
		String[] pics={"D:\\Informatik\\TortoiseOrdner\\3D-Modelle\\trunk\\resources\\cube_100x100x100.png","D:\\Informatik\\TortoiseOrdner\\3D-Modelle\\trunk\\resources\\cube_hole_100x100x100.png",""};
		
		for(int i=0;i<numberObjects;i++) {
			Foundobject f = new Foundobject((Integer)(int)(Math.random()*1000),(double)(int)(Math.random()*lenInt*100)/100,(double)(int)(Math.random()*angInt*100)/100,pics[(int)(Math.random()*pics.length)],null);
			foundList.add(f);
		}
		
		outputGUI outg3 = new outputGUI(foundList,lenInt,angInt,Integer.valueOf(widthTF.getText()),Integer.valueOf(heightTF.getText()));
		outg3.setVisible(true);
		
	}

	private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {
		editSearchobject();
	}

	/**
	 * Read file button action performed.
	 *
	 * @param evt
	 *            the evt
	 */
	private void readFileButtonActionPerformed(java.awt.event.ActionEvent evt) {
		readFile();
	}

	/**
	 * Adds the button action performed.
	 *
	 * @param evt
	 *            the evt
	 */
	private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
		if (editClicked) replaceSearchobject(index);
		else addSearchobject(searchList.size());
	}

	/**
	 * Removes the button action performed.
	 *
	 * @param evt
	 *            the evt
	 */
	private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {
		removeSearchobject();
	}

	/**
	 * Edge TF action performed.
	 *
	 * @param evt
	 *            the evt
	 */
	private void edgeTFActionPerformed(java.awt.event.ActionEvent evt) {
		addSearchobject(searchList.size());
	}
	
	private void edgeTFFocusLost(java.awt.event.FocusEvent evt) {
		if(edgeExists(edgeTF)) presetLenTF();
		else lenTF.setText("");		
	}


	/**
	 * Len TF action performed.
	 *
	 * @param evt
	 *            the evt
	 */
	private void lenTFActionPerformed(java.awt.event.ActionEvent evt) {
		addSearchobject(searchList.size());
	}

	/**
	 * To edge TF action performed.
	 *
	 * @param evt
	 *            the evt
	 */
	private void toEdgeTFActionPerformed(java.awt.event.ActionEvent evt) {
		addSearchobject(searchList.size());
	}

	/**
	 * Len interval TF action performed.
	 *
	 * @param evt
	 *            the evt
	 */
	private void lenIntervalTFActionPerformed(java.awt.event.ActionEvent evt) {
		addInterval(lenIntervalTF);
	}

	/**
	 * Angle interval TF action performed.
	 *
	 * @param evt
	 *            the evt
	 */
	private void angleIntervalTFActionPerformed(java.awt.event.ActionEvent evt) {
		addInterval(angleIntervalTF);
	}

	/**
	 * Len interval TF focus lost.
	 *
	 * @param evt
	 *            the evt
	 */
	private void lenIntervalTFFocusLost(java.awt.event.FocusEvent evt) {
		addInterval(lenIntervalTF);
	}

	/**
	 * Angle interval TF focus lost.
	 *
	 * @param evt
	 *            the evt
	 */
	private void angleIntervalTFFocusLost(java.awt.event.FocusEvent evt) {
		addInterval(angleIntervalTF);
	}

	/**
	 * Read dir button action performed.
	 *
	 * @param evt
	 *            the evt
	 */
	private void readDirButtonActionPerformed(java.awt.event.ActionEvent evt) {
		readDir();
	}

	// logic methods

	/**
	 * Check if input values are contradicting previous inputs. (Different
	 * length for the same edge etc.)
	 *
	 * @param id1
	 *            the id 1
	 * @param length
	 *            the length
	 * @param angle
	 *            the angle
	 * @param id2
	 *            the id 2
	 * @param searchList
	 *            the search list
	 * @return true, if successful
	 */
	private boolean valuesMatch(Integer id1, Double length, Double angle, Integer id2, List<Searchobject> searchList) {

		if (id1.equals(id2)) {
			javax.swing.JOptionPane.showMessageDialog(this, "Eine Kante kann keinen Winkel zu sich selbst haben!");
			return false;
		}
		
		for (Searchobject obj : searchList) {
			if (!editClicked && obj.getId1().equals(id1) && !Objects.equals(obj.getLength(),length)) {				 
					javax.swing.JOptionPane.showMessageDialog(this, "Diese Kante hat schon eine andere Laenge!");
					return false;		
			}

			else if (!editClicked && obj.getId1().equals(id1) && Objects.equals(obj.getId2(),id2) && !Objects.equals(obj.getAngle(),angle)
					|| editClicked && obj.getId1().equals(id1) && Objects.equals(obj.getId2(),id2) && !Objects.equals(obj.getAngle(),angle)
					&& searchList.indexOf(obj) != inputList.getSelectedIndex()) {
				javax.swing.JOptionPane.showMessageDialog(this, "Diese 2 Kanten haben schon einen anderen Winkel!");
				return false;
			}

		}
		
		return true;
	}

	/**
	 * Checks if a value is integer.
	 *
	 * @param string
	 *            the string
	 * @return true, if is integer
	 */
	private boolean isInteger(String string) {
		try {
			Integer.valueOf(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Checks if a value is double.
	 *
	 * @param string
	 *            the string
	 * @return true, if is double
	 */
	private boolean isDouble(String string) {
		try {
			Double.valueOf(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	private boolean edgeExists(javax.swing.JTextField tf) {
		if(isInteger(tf.getText())) {
			 Integer id = Integer.valueOf(tf.getText());
			 for (Searchobject obj : searchList) 
					if (obj.getId1().equals(id))
						return true;			 
		}
		return false;
	}
	
	private void presetLenTF() {
			 Integer id = Integer.valueOf(edgeTF.getText());
			 for (Searchobject obj : searchList) 
					if (obj.getId1().equals(id)) 
						lenTF.setText(java.util.Objects.toString(obj.getLength()));

						
		
	}

	/**
	 * Adds the interval.
	 *
	 * @param tf
	 *            the TextField
	 */
	private void addInterval(javax.swing.JTextField tf) {
		if (!isDouble(tf.getText()) || Double.valueOf(tf.getText()) < 0) {
			javax.swing.JOptionPane.showMessageDialog(this, "Das Intervall muss eine nicht-negative Zahl sein.");
			tf.setText("0");
			return;
		} else {
			lenInterval = Double.valueOf(lenIntervalTF.getText());
			angleInterval = Double.valueOf(angleIntervalTF.getText());

		}

	}

	/**
	 * Adds the searchobject.
	 */
	private void addSearchobject(int index) {
		Integer id1;
		if (!isInteger(edgeTF.getText()) || Integer.valueOf(edgeTF.getText()) < 0) {
			javax.swing.JOptionPane.showMessageDialog(this,
					"Erste Kanten-ID muss eine nicht-negative ganze Zahl sein.");

			
			edgeTF.setText("");
			return;
		} else
			id1 = Integer.valueOf(edgeTF.getText());

		Double length;
		if (lenTF.getText().isEmpty() || lenTF.getText().equals("null"))
			length = null;
		else if (!isDouble(lenTF.getText()) || Double.valueOf(lenTF.getText()) < 0) {
			javax.swing.JOptionPane.showMessageDialog(this, "Kantenlaenge muss eine nicht-negative Zahl sein.");
			lenTF.setText("");
			return;
		} else
			length = Double.valueOf(lenTF.getText());

		Double angle;
		if (angleTF.getText().isEmpty())
			angle = null;
		else if (!isDouble(angleTF.getText()) || Double.valueOf(angleTF.getText()) < 0) {
			javax.swing.JOptionPane.showMessageDialog(this, "Winkel muss eine nicht-negative Zahl sein.");
			angleTF.setText("");
			return;
		} else
			angle = Double.valueOf(angleTF.getText());

		Integer id2;
		if (toEdgeTF.getText().isEmpty())
			id2 = null;
		else if (!isInteger(toEdgeTF.getText()) || Integer.valueOf(toEdgeTF.getText()) < 0) {
			javax.swing.JOptionPane.showMessageDialog(this,
					"Zweite Kanten-ID muss eine nicht-negative ganze Zahl sein.");
			toEdgeTF.setText("");
			return;
		} else
			id2 = Integer.valueOf(toEdgeTF.getText());
 
		if ( valuesMatch(id1, length, angle, id2, searchList)) {
			Searchobject searchObj = new Searchobject(id1, length, angle, id2);
			
			if(searchList.size() <= index) searchList.add(index,searchObj);
			else searchList.set(index,searchObj);
			
			String newLine = "Kante " + id1 + ":      " + "Laenge: " + length + ",      Winkel: " + angle
					+ "   zur Kante " + id2;
			
			if(listModel.size() <= index)listModel.add(index,newLine);
			else listModel.setElementAt(newLine,index);
			//rework?
			if(!edgeExists(toEdgeTF)) {
				searchObj = new Searchobject(id2, null, null, null);
				searchList.add(index+1,searchObj);
				newLine = "Kante " + id2 + ":      " + "Laenge: " + null + ",      Winkel: " + null
						+ "   zur Kante " + null;
				listModel.add(index+1,newLine);

			}
		

			edgeTF.setText("");
			lenTF.setText("");
			angleTF.setText("");
			toEdgeTF.setText("");
			
			if(editClicked) replaceLengths(id1,length);
			editClicked = false;
			
		
		}

		if (editClicked == false)
			addButton.setText("hinzufuegen");

	}

	/**
	 * Edits the searchobject.
	 */
	private void editSearchobject() {
		index = inputList.getSelectedIndex();
		if (index == -1)
			return;

		editClicked = true;
		addButton.setText("Speichern");
		edgeTF.setText(java.util.Objects.toString(searchList.get(index).getId1(), "GG"));
		System.out.println(edgeTF.getText());

		lenTF.setText(java.util.Objects.toString(searchList.get(index).getLength(), ""));
		angleTF.setText(java.util.Objects.toString(searchList.get(index).getAngle(), ""));
		toEdgeTF.setText(java.util.Objects.toString(searchList.get(index).getId2(), ""));

		//searchList.remove(inputList.getSelectedIndex());
		//listModel.removeElementAt(inputList.getSelectedIndex());

	}
	
	private void replaceSearchobject(int index) {
		addSearchobject(index);
		
		
		
	}
	
	private void replaceLengths(Integer id, Double length) {
		
		for (Searchobject obj : searchList) 
			if ( obj.getId1().equals(id) && !Objects.equals(obj.getLength(),length)) {	
				obj.setLength(length);
				String newLine = "Kante " + id + ":      " + "Laenge: " + length + ",      Winkel: " + obj.getAngle()
						+ "   zur Kante " + obj.getId2();
				
				 listModel.setElementAt(newLine,searchList.indexOf(obj));
			
			}
		
		
		
	}

	/**
	 * Removes the searchobject.
	 */
	private void removeSearchobject() {
		if (inputList.getSelectedIndex() != -1) {
			searchList.remove(inputList.getSelectedIndex());
			listModel.removeElementAt(inputList.getSelectedIndex());

		}

	}

	/**
	 * Search searchobject.
	 */
	private void searchSearchobject() {
		for (Searchobject obj : searchList) {
			System.out.println(obj.getId1() + "  " + obj.getLength() + "  " + obj.getAngle() + "  " + obj.getId2());
		}
		System.out.println(lenInterval + " " + angleInterval);
		//outputGUI outg = new outputGUI();
		//outg.setVisible(true);
	}

	/**
	 * Read file.
	 */
	private void readFile() {
		chooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
		chooser.setFileFilter(objFilter);
		chooser.setMultiSelectionEnabled(true);

		int returnState = chooser.showDialog(null, "Open file");
		if (returnState == javax.swing.JFileChooser.APPROVE_OPTION) {
			// java.nio.file.Files.Path path =
			// chooser.getSelectedFile().getPath();
			System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
			File[] fl = chooser.getSelectedFiles();
			matchTxtPng(fl);

			// System.out.println("getSelectedFiles() : " +
			// chooser.getSelectedFiles());
			 //BuildLogic aBuildLogic = BuildLogic.getBuildLogic();
			 //aBuildLogic.createOBJ(chooser.getSelectedFile().getPath());
			 //aBuildLogic.createFigure();

			// 
		}
	}

	/**
	 * Read directory
	 */
	private void readDir() {
		chooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
		chooser.setFileFilter(objFilter);
		int returnState = chooser.showDialog(null, "Open directory");
		if (returnState == javax.swing.JFileChooser.APPROVE_OPTION) {
			File dir = chooser.getSelectedFile();
			File[] fl = dir.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String filename) {
					return filename.endsWith(".obj");
				}
			});

			matchTxtPng(fl);

			
		}
	}

	/**
	 * Looks for matching .txt and .png files in the same directory
	 *
	 * @param fl
	 *            the filelist
	 */
	private void matchTxtPng(File[] fl) {
		//rem
		String[] urls = new String[3];
		List<String[]> urlList = new ArrayList<String[]>();

		for (File file : fl) {
			String nameWithObj = java.util.Objects.toString(file);
			String nameOnly = nameWithObj.substring(0, nameWithObj.length() - 4);

			String nameWithTxt = nameOnly + ".txt";
			File txt = new File(nameWithTxt);
			if (!txt.exists())
				nameWithTxt = "";

			String nameWithPng = nameOnly + ".png";
			File png = new File(nameWithPng);
			if (!png.exists())
				nameWithPng = "";

			System.out.println(nameWithObj);
			System.out.println(nameWithTxt);
			System.out.println(nameWithPng);
			//rem
			urls[0] = nameWithObj;
			urls[0] = nameWithTxt;
			urls[0] = nameWithPng;
			
			urlList.add(urls);
		}
		//remove later
		//outputGUI outg2 = new outputGUI(imageList);
		//outg2.setVisible(true);

	}
}
