package src;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FilenameFilter;

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

	/**
	 * Constructor
	 */
	public GUI() {

		initComponents();

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
		// String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5"
		// };
		// public int getSize() { return strings.length; }
		// public Object getElementAt(int i) { return strings[i]; }
		// });
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

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(63, 63, 63)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addComponent(edgeLabel)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(edgeTF, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(27, 27, 27)
								.addComponent(lenLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 47,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(lenTF, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
										javax.swing.GroupLayout.PREFERRED_SIZE).addGap(36, 36, 36)
								.addComponent(angleLabel)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(angleTF, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(26, 26, 26)
								.addComponent(toEdgeLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(toEdgeTF, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
										javax.swing.GroupLayout.PREFERRED_SIZE).addGap(30, 30, 30)
								.addComponent(addButton).addGap(150, 150, 150))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 323,
												javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(layout.createSequentialGroup().addComponent(lenIntervalLabel).addGap(2, 2, 2)
										.addComponent(lenIntervalTF, javax.swing.GroupLayout.PREFERRED_SIZE, 52,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18).addComponent(angleIntervalLabel)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(angleIntervalTF, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(editButton, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(removeButton, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addGap(71, 71, 71)
												.addGroup(layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(readDirButton,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(readFileButton,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
										.addComponent(searchButton))))));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(50, 50, 50)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(lenIntervalLabel).addComponent(angleIntervalLabel)
								.addComponent(angleIntervalTF, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addComponent(lenIntervalTF, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(editButton).addComponent(readFileButton))
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(removeButton))
								.addGroup(
										layout.createSequentialGroup().addGap(20, 20, 20).addComponent(readDirButton)))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(searchButton))
						.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 225,
								javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(edgeLabel)
						.addComponent(lenLabel)
						.addComponent(lenTF, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(angleLabel)
						.addComponent(angleTF, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(toEdgeLabel).addComponent(addButton)
						.addComponent(edgeTF, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(toEdgeTF, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGap(24, 24, 24)));

		layout.linkSize(javax.swing.SwingConstants.VERTICAL,
				new java.awt.Component[] { angleLabel, angleTF, edgeLabel, lenLabel, lenTF, toEdgeLabel });

		pack();
	}

	/**
	 * Angle TF action performed.
	 *
	 * @param evt
	 *            the evt
	 */
	private void angleTFActionPerformed(java.awt.event.ActionEvent evt) {
		addSearchobject();
	}

	/**
	 * Search button action performed.
	 *
	 * @param evt
	 *            the evt
	 */
	private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
		searchSearchobject();
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
		addSearchobject();
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
		addSearchobject();
	}

	/**
	 * Len TF action performed.
	 *
	 * @param evt
	 *            the evt
	 */
	private void lenTFActionPerformed(java.awt.event.ActionEvent evt) {
		addSearchobject();
	}

	/**
	 * To edge TF action performed.
	 *
	 * @param evt
	 *            the evt
	 */
	private void toEdgeTFActionPerformed(java.awt.event.ActionEvent evt) {
		addSearchobject();
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
			if (obj.getId1().equals(id1) && !obj.getLength().equals(length)) {
				javax.swing.JOptionPane.showMessageDialog(this, "Diese Kante hat schon eine andere Laenge!");
				return false;
			}

			else if (obj.getId1().equals(id1) && obj.getId2().equals(id2) && !obj.getAngle().equals(angle)) {
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
	private void addSearchobject() {
		Integer id1;
		if (!isInteger(edgeTF.getText()) || Integer.valueOf(edgeTF.getText()) < 0) {
			javax.swing.JOptionPane.showMessageDialog(this,
					"Erste Kanten-ID muss eine nicht-negative ganze Zahl sein.");
			edgeTF.setText("");
			return;
		} else
			id1 = Integer.valueOf(edgeTF.getText());

		Double length;
		if (lenTF.getText().isEmpty())
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

		if (valuesMatch(id1, length, angle, id2, searchList)) {
			Searchobject searchObj = new Searchobject(id1, length, angle, id2);
			searchList.add(searchObj);
			String newLine = "Kante " + id1 + ":      " + "Laenge: " + length + ",      Winkel: " + angle
					+ "   zur Kante " + id2;
			listModel.addElement(newLine);
		}

		edgeTF.setText("");
		lenTF.setText("");
		angleTF.setText("");
		toEdgeTF.setText("");

		if (addButton.getText().equals("Speichern"))
			addButton.setText("hinzufuegen");

	}

	/**
	 * Edits the searchobject.
	 */
	private void editSearchobject() {
		int index = inputList.getSelectedIndex();
		if (index == -1)
			return;

		addButton.setText("Speichern");
		edgeTF.setText(java.util.Objects.toString(searchList.get(index).getId1(), ""));
		lenTF.setText(java.util.Objects.toString(searchList.get(index).getLength(), ""));
		angleTF.setText(java.util.Objects.toString(searchList.get(index).getAngle(), ""));
		toEdgeTF.setText(java.util.Objects.toString(searchList.get(index).getId2(), ""));

		searchList.remove(inputList.getSelectedIndex());
		listModel.removeElementAt(inputList.getSelectedIndex());

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
		outputGUI outg = new outputGUI(searchList);
		outg.setVisible(true);
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
			// BuildLogic aBuildLogic = BuildLogic.getBuildLogic();
			// aBuildLogic.createOBJ(chooser.getSelectedFile().getPath());
			// aBuildLogic.createFigure();

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
		}

	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String args[]) {
		/*
		 * try { for (javax.swing.UIManager.LookAndFeelInfo info :
		 * javax.swing.UIManager.getInstalledLookAndFeels()) { if
		 * ("Nimbus".equals(info.getName())) {
		 * javax.swing.UIManager.setLookAndFeel(info.getClassName()); break; } }
		 * } catch (ClassNotFoundException ex) {
		 * java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util
		 * .logging.Level.SEVERE, null, ex); } catch (InstantiationException ex)
		 * {
		 * java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util
		 * .logging.Level.SEVERE, null, ex); } catch (IllegalAccessException ex)
		 * {
		 * java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util
		 * .logging.Level.SEVERE, null, ex); } catch
		 * (javax.swing.UnsupportedLookAndFeelException ex) {
		 * java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util
		 * .logging.Level.SEVERE, null, ex); }
		 */

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new GUI().setVisible(true);
			}
		});
	}

}
