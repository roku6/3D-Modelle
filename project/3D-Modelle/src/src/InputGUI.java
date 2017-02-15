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

import javax.swing.AbstractButton;
import javax.swing.event.ChangeEvent;

/**
 * The Class GUI.
 * 
 * @author Ekaterina Kuzminykh
 */
public class InputGUI extends javax.swing.JFrame {

	// members
	private Modelle modelle;
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
	private javax.swing.JButton removeAllButton;
	private javax.swing.JButton removeObjFromDB;
	private javax.swing.JButton searchButton;
	private javax.swing.JLabel toEdgeLabel;
	private javax.swing.JTextField toEdgeTF;
	private javax.swing.DefaultListModel listModel;
	private List<Searchobject> searchList;
	private javax.swing.JFileChooser chooser;
	private javax.swing.filechooser.FileNameExtensionFilter objFilter;
	private Double lenInterval;
	private Double angleInterval;
	private boolean editClicked;
	private javax.swing.JLabel outputLabel;
	private javax.swing.JLabel widthLabel;
	private javax.swing.JLabel heightLabel;
	private javax.swing.JTextField widthTF;
	private javax.swing.JTextField heightTF;
	private javax.swing.JLabel objInDBLabel;
	List<String[]> namesList;
	private int edgeEditIndex;
	int width;
	int height;

	/**
	 * Constructor
	 */
	public InputGUI() {

		this.modelle = Modelle.getInstance();
		initComponents();
		Insets insets = getInsets();
		setSize(830 + insets.left + insets.right, 400 + insets.top + insets.bottom);
		setVisible(true);
		setResizable(false);
	}

	/**
	 * Initializes all components. Sets the text on the labels and in the text
	 * fields with presets (lenInterval and angleInterval are set to 0.0 by
	 * default, the width and height of the outputGUI to screensizes minus 100).
	 * Adds actionListeners to the buttons. Sets the settings and model for the
	 * visible list of input edges. Finally, sets the position and size of all
	 * elements. No layout manager was used to keep it consistent with the
	 * outputGUI, where custom positioning of the images makes the use of a
	 * layout manager too complicated.
	 * 
	 */
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
		removeAllButton = new javax.swing.JButton();
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
		namesList = new ArrayList<String[]>();
		removeObjFromDB = new javax.swing.JButton();
		listModel = new javax.swing.DefaultListModel();
		searchList = new ArrayList<Searchobject>();
		chooser = new javax.swing.JFileChooser();
		objFilter = new javax.swing.filechooser.FileNameExtensionFilter("OBJ-Dateien", "obj");
		lenInterval = 0.0;
		angleInterval = 0.0;
		editClicked = false;
		edgeEditIndex = -1;

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Kanten- und Winkeleingabe");

		edgeLabel.setText("Kante:");

		lenLabel.setText("Länge:");

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

		inputList.setModel(listModel);

		inputList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jScrollPane2.setViewportView(inputList);

		searchButton.setText("Suchen");
		searchButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				searchButtonActionPerformed(evt);
			}
		});

		addButton.setText("Hinzufügen");
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

		removeButton.setText("Kante löschen");
		removeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeButtonActionPerformed(evt);
			}
		});
		
		removeAllButton.setText("Alle löschen");
		removeAllButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeAllButtonActionPerformed(evt);
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

		removeObjFromDB.setText("Objekt löschen");
		removeObjFromDB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeObjFromDBActionPerformed(evt);
			}
		});

		lenIntervalLabel.setText("Längenintervall:");

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
		heightLabel.setText("Höhe");
		heightLabel.setVerticalAlignment(javax.swing.JTextField.BOTTOM);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) screenSize.getWidth() - 100;
		height = (int) screenSize.getHeight() - 100;
		widthTF.setText(java.util.Objects.toString(width));
		widthTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		heightTF.setText(java.util.Objects.toString(height));
		heightTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		
		objInDBLabel = new javax.swing.JLabel();
		objInDBLabel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		objInDBLabel.setVerticalAlignment(javax.swing.JTextField.CENTER);
		int obj=0;
		try {
			obj= modelle.getDBController().getUsedIds().size();
			if (obj == 1) {
				objInDBLabel.setText("1 Objekt in der Datenbank.");
			} else {
				objInDBLabel.setText(obj + " Objekte in der Datenbank.");
			}
		}
		catch(NullPointerException ex) {
			objInDBLabel.setText("keine Datenbankverbindung!");
		
		}
		

		// Layout

		Container pane = getContentPane();
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

		pane.add(lenIntervalTF);
		size = lenIntervalTF.getPreferredSize();
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
		lenTF.setBounds(240, 357, 37, 21);

		pane.add(angleLabel);
		size = angleLabel.getPreferredSize();
		angleLabel.setBounds(315, 360, size.width, size.height);

		pane.add(angleTF);
		size = angleTF.getPreferredSize();
		angleTF.setBounds(370, 357, 37, 21);

		pane.add(toEdgeLabel);
		size = toEdgeLabel.getPreferredSize();
		toEdgeLabel.setBounds(440, 360, size.width, size.height);

		pane.add(toEdgeTF);
		size = toEdgeTF.getPreferredSize();
		toEdgeTF.setBounds(507, 357, 37, 21);

		pane.add(inputList);
		size = inputList.getPreferredSize();
		inputList.setBounds(67, 87, 310, 215);

		pane.add(jScrollPane2);
		size = jScrollPane2.getPreferredSize();
		jScrollPane2.setBounds(65, 85, 315, 220);

		pane.add(addButton);
		size = addButton.getPreferredSize();
		addButton.setBounds(580, 352, size.width, size.height);

		pane.add(editButton);
		size = editButton.getPreferredSize();
		editButton.setBounds(400, 85, size.width, size.height);

		pane.add(removeButton);
		removeButton.setBounds(400, 125, size.width, size.height);
		
		pane.add(removeAllButton);
		removeAllButton.setBounds(400, 165, size.width, size.height);

		pane.add(readDirButton);
		
		size = readDirButton.getPreferredSize();
		readDirButton.setBounds(610, 125, size.width, size.height);

		pane.add(readFileButton);
		readFileButton.setBounds(610, 85, size.width, size.height);

		pane.add(removeObjFromDB);
		removeObjFromDB.setBounds(610, 165, size.width, size.height);
		
		pane.add(objInDBLabel);
		size = objInDBLabel.getPreferredSize();
		objInDBLabel.setBounds(610,50,size.width, size.height);
		

		pane.add(searchButton);
		size = searchButton.getPreferredSize();
		searchButton.setBounds(400, 280, size.width, size.height);

		pane.add(outputLabel);
		size = outputLabel.getPreferredSize();
		outputLabel.setBounds(500, 280, size.width, size.height);

		pane.add(widthLabel);
		size = widthLabel.getPreferredSize();
		widthLabel.setBounds(600, 260, size.width, size.height);

		pane.add(heightLabel);
		size = heightLabel.getPreferredSize();
		heightLabel.setBounds(670, 260, size.width, size.height);

		pane.add(widthTF);
		size = widthTF.getPreferredSize();
		widthTF.setBounds(600, 280, size.width + 20, size.height);

		pane.add(heightTF);
		size = heightTF.getPreferredSize();
		heightTF.setBounds(670, 280, size.width + 20, size.height);

		pack();
	}

	//action performed methods

	/**
	 * Angle TF action performed. The ActionPerformed methods only calls the
	 * corresponding logic method(s), so GUI elements can be replaced or removed
	 * later.
	 * Calls {@link #manageInput()} . 
	 */
	private void angleTFActionPerformed(java.awt.event.ActionEvent evt) {
		manageInput();
	}

	/**
	 * Search button action performed. The ActionPerformed methods only calls
	 * the corresponding logic method(s), so GUI elements can be replaced or
	 * removed later.
	 * Calls {@link #searchSearchobject()} . 
	 */
	private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
		searchSearchobject();

		// remove later, test!
		/*
		  int numberObjects = (int)(Math.random()*10+50);

		  System.out.println(numberObjects);
		  
		  double lenInt =
		  (int)(Math.random()*9+1)+(int)(Math.random()*90)*0.01; double angInt
		  = (int)(Math.random()*9+1)+(int)(Math.random()*90)*0.01;
		  List<Foundobject> foundList = new ArrayList<Foundobject>(); String[]
		 pics={"C:\\knfn\\StudPro\\pngs\\cube_100x100x100.png"};//,"D:\\Informatik\\TortoiseOrdner\\3D-Modelle\\trunk\\resources\\cube_hole_100x100x100.png" ,""};
		  
		 for(int i=0;i<numberObjects;i++) { Foundobject f = new
		  Foundobject((Integer)(int)(Math.random()*1000),(double)(int)(Math.
		  random()*lenInt*100)/100,(double)(int)(Math.random()*angInt*100)/100,
		  pics[(int)(Math.random()*pics.length)],null); foundList.add(f); }
		  
		  OutputGUI outg3 = new
		  OutputGUI(foundList,lenInt,angInt,Integer.valueOf(widthTF.getText()),
		  Integer.valueOf(heightTF.getText())); outg3.setVisible(true);
		  
		  outg3 = null;
		  
		  return;*/
		  
		 
	}

	/**
	 * Edit button action performed. The ActionPerformed methods only calls the
	 * corresponding logic method(s), so GUI elements can be replaced or removed
	 * later.
	 * Calls {@link #prepareForEdit()} . 
	 */
	private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {		
		prepareForEdit();
	}

	/**
	 * Read file button action performed. The ActionPerformed methods only calls
	 * the corresponding logic method(s), so GUI elements can be replaced or
	 * removed later.
	 * Calls {@link #readFile()} . 
	 */
	private void readFileButtonActionPerformed(java.awt.event.ActionEvent evt) {
		readFile();
	}

	/**
	 * Add button action performed. The ActionPerformed methods only calls the
	 * corresponding logic method(s), so GUI elements can be replaced or removed
	 * later.
	 * Calls {@link #manageInput()} . 
	 */
	private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
		manageInput();
	}

	/**
	 * Remove button action performed. The ActionPerformed methods only calls
	 * the corresponding logic method(s), so GUI elements can be replaced or
	 * removed later.
	 * Calls {@link #removeSearchobject()} . 
	 */
	private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {
		removeSearchobject();
	}
	
	/**
	 * Remove all button action performed. The ActionPerformed methods only calls
	 * the corresponding logic method(s), so GUI elements can be replaced or
	 * removed later.
	 * Calls {@link #removeAllSearchobjects()} . 
	 */
	private void removeAllButtonActionPerformed(java.awt.event.ActionEvent evt) {
		removeAllSearchobjects();
	}

	/**
	 * Edge TF action performed. The ActionPerformed methods only calls the
	 * corresponding logic method(s), so GUI elements can be replaced or removed
	 * later.
	 * Calls {@link #manageInput()} . 
	 */
	private void edgeTFActionPerformed(java.awt.event.ActionEvent evt) {
		manageInput();
	}

	/**
	 * Edge TF focus lost. The ActionPerformed methods only calls the
	 * corresponding logic method(s), so GUI elements can be replaced or removed
	 * later.
	 * Calls {@link #presetLenTF()} . 
	 */
	private void edgeTFFocusLost(java.awt.event.FocusEvent evt) {
		presetLenTF();
	}

	/**
	 * Len TF action performed. The ActionPerformed methods only calls the
	 * corresponding logic method(s), so GUI elements can be replaced or removed
	 * later.
	 * Calls {@link #manageInput()} . 
	 */
	private void lenTFActionPerformed(java.awt.event.ActionEvent evt) {
		manageInput();
	}

	/**
	 * To edge TF action performed. The ActionPerformed methods only calls the
	 * corresponding logic method(s), so GUI elements can be replaced or removed
	 * later.
	 * Calls {@link #manageInput()} . 
	 */
	private void toEdgeTFActionPerformed(java.awt.event.ActionEvent evt) {
		manageInput();
	}

	/**
	 * Len interval TF action performed. The ActionPerformed methods only calls
	 * the corresponding logic method(s), so GUI elements can be replaced or
	 * removed later.
	 * Calls {@link #addInterval()} . 
	 */
	private void lenIntervalTFActionPerformed(java.awt.event.ActionEvent evt) {
		addInterval(lenIntervalTF);
	}

	/**
	 * Angle interval TF action performed. The ActionPerformed methods only
	 * calls the corresponding logic method(s), so GUI elements can be replaced
	 * or removed later.
	 * Calls {@link #addInterval()} . 
	 */
	private void angleIntervalTFActionPerformed(java.awt.event.ActionEvent evt) {
		addInterval(angleIntervalTF);
	}

	/**
	 * Len interval TF focus lost. The ActionPerformed methods only calls the
	 * corresponding logic method(s), so GUI elements can be replaced or removed
	 * later.
	 * Calls {@link #addInterval()} . 
	 */
	private void lenIntervalTFFocusLost(java.awt.event.FocusEvent evt) {
		addInterval(lenIntervalTF);
	}

	/**
	 * Angle interval TF focus lost. The ActionPerformed methods only calls the
	 * corresponding logic method(s), so GUI elements can be replaced or removed
	 * later.
	 * Calls {@link #addInterval()} . 
	 */
	private void angleIntervalTFFocusLost(java.awt.event.FocusEvent evt) {
		addInterval(angleIntervalTF);
	}

	/**
	 * Read dir button action performed. The ActionPerformed methods only calls
	 * the corresponding logic method(s), so GUI elements can be replaced or
	 * removed later.
	 * Calls {@link #readDir()} . 
	 */
	private void readDirButtonActionPerformed(java.awt.event.ActionEvent evt) {
		readDir();
	}

	/**
	 * Remove object from DB button action performed. The ActionPerformed
	 * methods only calls the corresponding logic method(s), so GUI elements can
	 * be replaced or removed later.
	 * Calls {@link #removeObjFromDB()} .
	 * 
	 */
	private void removeObjFromDBActionPerformed(java.awt.event.ActionEvent evt) {
		removeObjFromDB();
	}

	// logic methods

	/**
	 * Checks if input values are contradicting previous inputs. (Different
	 * length for the same edge etc.) Displays error messages if so.
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
	 * @return true, if there are no contradictions
	 */
	private boolean valuesMatch(Integer id1, Double length, Double angle, Integer id2, List<Searchobject> searchList) {

		if (id1.equals(id2)) {
			javax.swing.JOptionPane.showMessageDialog(this, "Eine Kante kann keinen Winkel zu sich selbst haben!");
			return false;
		}

		for (Searchobject obj : searchList) {
			if (!editClicked && obj.getId1().equals(id1) && !Objects.equals(obj.getLength(), length)) {
				javax.swing.JOptionPane.showMessageDialog(this, "Diese Kante hat schon eine andere Länge!");
				return false;
			}

			else if (!editClicked && obj.getId1().equals(id1) && Objects.equals(obj.getId2(), id2)
					&& !Objects.equals(obj.getAngle(), angle)
					|| editClicked && obj.getId1().equals(id1) && Objects.equals(obj.getId2(), id2)
							&& !Objects.equals(obj.getAngle(), angle)
							&& searchList.indexOf(obj) != inputList.getSelectedIndex()) {
				javax.swing.JOptionPane.showMessageDialog(this, "Diese 2 Kanten haben schon einen anderen Winkel!");
				return false;
			}
		}

		return true;
	}

	/**
	 * Checks if a string value is integer.
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
	 * Checks if a string value is double.
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

	/**
	 * checks if edge with the user input ID already exists
	 * 
	 * @param tf
	 * @return
	 */
	private boolean edgeExists(javax.swing.JTextField tf) {
		if (isInteger(tf.getText())) {
			Integer id = Integer.valueOf(tf.getText());
			for (Searchobject obj : searchList)
				if (obj.getId1().equals(id))
					return true;
		}
		return false;
	}

	/**
	 * When the user types in an ID into the first text field, this method
	 * checks if an edge with this ID already exists. If so, the length text
	 * field is set to it's length. Else it remains empty.
	 */
	private void presetLenTF() {
		if (edgeExists(edgeTF)) {
			Integer id = Integer.valueOf(edgeTF.getText());
			for (Searchobject obj : searchList)
				if (obj.getId1().equals(id))
					lenTF.setText(java.util.Objects.toString(obj.getLength()));
		} else
			lenTF.setText("");
	}

	/**
	 * Adds the interval from the user input to the corresponding variables.
	 * Also checks if user input is correct.
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
	 *
	 * Checks if the user input consists of acceptable values. Different from
	 * the valuesMatch method, checks only for valid types (int/double) and
	 * positive numbers in the input. If all values are valid, returns true.
	 * 
	 * @param index
	 *            the index at which the object is added
	 */
	
	private boolean validInput() {
		

		if (!isInteger(edgeTF.getText()) || Integer.valueOf(edgeTF.getText()) <= 0) {
			javax.swing.JOptionPane.showMessageDialog(this,
					"Erste Kanten-ID muss eine positive ganze Zahl sein.");
			edgeTF.setText("");
			return false;
		} 

	
		if (lenTF.getText().isEmpty() || lenTF.getText().equals("null")) {}
			
			
		else if (!isDouble(lenTF.getText()) || Double.valueOf(lenTF.getText()) < 0) {
			javax.swing.JOptionPane.showMessageDialog(this, "Kantenlänge muss eine nicht-negative Zahl sein.");
			lenTF.setText("");
			return false;
		} 

	
		if (angleTF.getText().isEmpty()) {}
		
		else if (!isDouble(angleTF.getText()) || Double.valueOf(angleTF.getText()) < 0) {
			javax.swing.JOptionPane.showMessageDialog(this, "Winkel muss eine nicht-negative Zahl sein.");
			angleTF.setText("");
			return false;
		}

		
		if (toEdgeTF.getText().isEmpty()) {}
	
		else if (!isInteger(toEdgeTF.getText()) || Integer.valueOf(toEdgeTF.getText()) <= 0) {
			javax.swing.JOptionPane.showMessageDialog(this,
					"Zweite Kanten-ID muss eine positive ganze Zahl sein.");
			toEdgeTF.setText("");
			return false;
		} 
		return true;
		

		}
		
	/**
	 * Calls all the functions that are needed to manage the user input.
	 * First, calls {@link #validInput()} .
	 * If textfields were left empty, sets the corresponding values to null.
	 * Then calls {@link #valuesMatch()} .
	 * If it succeeded, calls {@link #createSearchobject()} .
	 * Checks whether the user clicked the edit button and calls
	 * {@link #addSearchobject()} or {@link #replaceSearchobject()}.
	 */
	private void manageInput() {
		if(!validInput())
			return;
		Integer id1 = Integer.valueOf(edgeTF.getText());
		
		Double length;
		if (lenTF.getText().isEmpty() || lenTF.getText().equals("null"))
			length = null;
		else
			length = Double.valueOf(lenTF.getText());
		
		Double angle;
		if (angleTF.getText().isEmpty()|| angleTF.getText().equals("null"))
			angle = null;
		else
			angle = Double.valueOf(angleTF.getText());
		
		Integer id2;
		if (toEdgeTF.getText().isEmpty()|| toEdgeTF.getText().equals("null"))
			id2 = null;
		else		
			id2 =Integer.valueOf(toEdgeTF.getText());
		
		if(!valuesMatch(id1,length,angle,id2,searchList))
			return;
		Searchobject searchObj = createSearchobject(id1,length,angle,id2);
		
		if(editClicked==true) 
			replaceSearchobject(edgeEditIndex,searchObj);
		else
			addSearchobject(searchObj);
		
	}
	
	/**
	 * Creates a new searchobject from the input valies.
	 * @param id1
	 * @param length
	 * @param angle
	 * @param id2
	 * @return
	 */
	private Searchobject createSearchobject(Integer id1, Double length, Double angle, Integer id2) {
		Searchobject searchObj = new Searchobject(id1, length, angle, id2);
		return searchObj;
		
	}


	/**
	 * Adds the given searchobject to the end of the searchlist.
	 * Resets the textfields to empty.
	 * Calls {@link #displaySearchlist()}
	 * @param searchObj
	 */
	private void addSearchobject(Searchobject searchObj) {

		searchList.add(searchObj);

		edgeTF.setText("");
		lenTF.setText("");
		angleTF.setText("");
		toEdgeTF.setText("");

		displaySearchlist();
	}
	
	/**
	 * Copies the values from the logical internal searchlist to the visible list representation of the GUI.
	 * First, clears the GUI-listmodel, then adds a new line for every searchobject on the searchlist.
	 * Should be called after every change to the searchlist.
	 */
	private void displaySearchlist() {
		listModel.clear();
		for (Searchobject obj : searchList) {
			String newLine = "Kante " + obj.getId1() + ":      " + "Länge: " + obj.getLength() + ",      Winkel: " + obj.getAngle()
					+ "   zur Kante " + obj.getId2();
			listModel.addElement(newLine);
			
		}
		
	}

	/**
	 *If no line is selected in the inputList, does
	 * nothing. Else sets the edgeEditIndex and the editClicked flag for the
	 * replaceObject method and sets the data from the selected line to the text
	 * fields.
	 * Disables the remove and remove all buttons.
	 * 
	 */
	private void prepareForEdit() {
		edgeEditIndex = inputList.getSelectedIndex();
		if (edgeEditIndex == -1)
			return;

		editClicked = true;
		removeButton.setEnabled(false);
		removeAllButton.setEnabled(false);
		addButton.setText("Speichern");
		edgeTF.setText(java.util.Objects.toString(searchList.get(edgeEditIndex).getId1(), ""));
		lenTF.setText(java.util.Objects.toString(searchList.get(edgeEditIndex).getLength(), ""));
		angleTF.setText(java.util.Objects.toString(searchList.get(edgeEditIndex).getAngle(), ""));
		toEdgeTF.setText(java.util.Objects.toString(searchList.get(edgeEditIndex).getId2(), ""));
	}

	/**
	 * Replaces the searchobject at the given index of the searchlist with the given searchobject.
	 * Calls {@link #replaceLengths()}.
	 * Sets the editClicked flag to false, enables the remove buttons, sets the textfields to empty
	 * and calls {@link #displaySearchlist()}
	 * @param index
	 * @param searchObj
	 */
	private void replaceSearchobject(int index, Searchobject searchObj) {

		searchList.set(index, searchObj);
		replaceLengths(searchObj.getId1(), searchObj.getLength());
		editClicked = false;
		removeButton.setEnabled(true);
		removeAllButton.setEnabled(true);
		edgeTF.setText("");
		lenTF.setText("");
		angleTF.setText("");
		toEdgeTF.setText("");
		addButton.setText("hinzufügen");
		
		displaySearchlist();
	}

	/**
	 * If the length of a searchobject was edited, goes through the list and
	 * edits the length in all appearances of this searchobject.
	 * 
	 * @param id
	 * @param length
	 */
	private void replaceLengths(Integer id, Double length) {
		for (Searchobject obj : searchList)
			if (obj.getId1().equals(id) && !Objects.equals(obj.getLength(), length)) 
				obj.setLength(length);
		displaySearchlist();
	}

	/**
	 * Removes the searchobject from the  the internal searchList.
	 * Calls {@link #displaySearchlist()}
	 */
	private void removeSearchobject() {	
		if (inputList.getSelectedIndex() != -1) 
			searchList.remove(inputList.getSelectedIndex());
		displaySearchlist();
	}
	
	/**
	 * Asks for confirmation, then clears rhe searchlist.
	 * Calls {@link #displaySearchlist()}
	 */
	private void removeAllSearchobjects() {
		int reply = javax.swing.JOptionPane.showConfirmDialog(this, "Alle Kanten aus der Liste löschen?","",
				javax.swing.JOptionPane.YES_NO_OPTION);
		if (reply == javax.swing.JOptionPane.YES_OPTION)
	    {
			searchList.clear();
			displaySearchlist();
	    }

	}
	

	/**
	 * Calls the search function of the main class if at least one searchobject
	 * exists. Displays an error message otherwise.
	 */
	private void searchSearchobject() {
		if (searchList.size() > 0) {
			if(isInteger(widthTF.getText()))
					width = Integer.valueOf(widthTF.getText());
			if(isInteger(heightTF.getText()))
				height = Integer.valueOf(heightTF.getText());
			modelle.search(searchList, angleInterval, lenInterval, width, height);
		}
		else
			javax.swing.JOptionPane.showMessageDialog(this, "Zum Suchen muss mindestens eine Kante angegeben werden.");
	}

	/**
	 * Read file. Opens the file chooser for files.
	 * Adds chosen file to the filelist.
	 * Calls {@link #matchTxtPng()}
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
			
		
			}
		
	}

	/**
	 * Read directory 
	 * Opens the file chooser for directories 
	 * Adds chosen files to the filelist
	 * Calls {@link #matchTxtPng()}
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
	 * Looks for matching .txt and .png files in the same directory to use as
	 * description and picture. Saves empty string if it finds none. Calls the
	 * buildObjects method in the main class with the list of all filenames as
	 * parameter.
	 * Calls {@link #renewObjCount()}
	 *
	 * @param fl
	 *            the filelist
	 */
	private void matchTxtPng(File[] fl) {

		namesList.clear();
		for (File file : fl) {
			String nameWithObj = java.util.Objects.toString(file);
			nameWithObj = nameWithObj.replace("\\", "\\\\");
			String nameOnly = nameWithObj.substring(0, nameWithObj.length() - 4);

			String nameWithTxt = nameOnly + ".txt";
			File txt = new File(nameWithTxt);
			if (!txt.exists())
				nameWithTxt = "";

			String nameWithPng = nameOnly + ".png";
			File png = new File(nameWithPng);
			if (!png.exists())
				nameWithPng = "";

			String[] names = new String[3];
			names[0] = nameWithObj;
			names[1] = nameWithTxt;
			names[2] = nameWithPng;
			namesList.add(names);
		}
		
		modelle.buildObjects(namesList);
		renewObjCount();
		
	
		
	}

	/**
	 * Displays an input window for the ID of the object to be removed. Calls
	 * the deleteObjFromDB method in the main class.
	 * Calls {@link #renewObjCount()}
	 */
	private void removeObjFromDB() {
		String id = javax.swing.JOptionPane.showInputDialog("Objekt-ID eingeben:");
		modelle.deleteObjFromDB(id);
		renewObjCount();
	}
	
	
	/**
	 * Renews the label showing how many objects are in the database.
	 */
	private void renewObjCount() {
		int obj=0;
		try {
			obj= modelle.getDBController().getUsedIds().size();
			if (obj == 1) {
				objInDBLabel.setSize(objInDBLabel.getPreferredSize());
				objInDBLabel.setText("1 Objekt in der Datenbank.");
			} else {
				objInDBLabel.setSize(objInDBLabel.getPreferredSize());
				objInDBLabel.setText(obj + " Objekte in der Datenbank.");
			}
		}
		catch(NullPointerException ex) {
			objInDBLabel.setSize(objInDBLabel.getPreferredSize());
			objInDBLabel.setText("keine Datenbankverbindung!");
		}
	}

	/**
	 * Close DBController on exit
	 */
	@Override
	public void dispose() {
		modelle.getDBController().shutdownDB();
		super.dispose();
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String args[]) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new InputGUI().setVisible(true);
			}
		});
	}
}
