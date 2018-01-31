package main.java.is.personal.Xcrypt.userInterface;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

import main.java.is.personal.Xcrypt.connection.Run;

import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.ChangeEvent;


public class UI{

	///Global variables only to make things easy to start with. Clean up!
	private static final long serialVersionUID = 1L;
	private ArrayList<String> pathToFolder = new ArrayList<String>(); //Path to folder to encrypt again on exit		//Minor security hole! Fix when there is time
	private ArrayList<String> authentication = new ArrayList<String>(); //Password and username 					//Security hole! Fix when there is time
	private int Encrypt = 0;
	private int Decrypt = 1;
	private JFrame frmEncrypt;
	private SpringLayout springLayout;
	private JButton btnFileTab;
	private JLabel secretLabel, startBtnImage;
	private JProgressBar progressBar;
	private DynamicProgressBarExecute exProgressBar;
	
	
	class DynamicProgressBarExecute extends SwingWorker<Object, Object>{
		private String password, username, path, activeTab;
		private int action;
		private JButton btnStart;
		private JCheckBox chckbxInclSubfolders, chckbxEncryptOnExit;
		private JRadioButton rdbtnDecrypt;
		private JProgressBar progressBar;
		private JLabel lblProsesslabel;
		
		DynamicProgressBarExecute(String pw, String un, String path, int action, String activeTab, JButton btnStart, JCheckBox chckbxInclSubfolders, JCheckBox chckbxEncryptOnExit, JRadioButton rdbtnDecrypt, JProgressBar progressBar, JLabel lblProsesslabel){
			this.password = pw;
			this.username = un;
			this.path = path;
			this.action = action;
			this.activeTab = activeTab;
			this.btnStart = btnStart;
			this.chckbxInclSubfolders = chckbxInclSubfolders;
			this.chckbxEncryptOnExit = chckbxEncryptOnExit;
			this.rdbtnDecrypt = rdbtnDecrypt;
			this.progressBar = progressBar;
			this.lblProsesslabel = lblProsesslabel;
		}

		@Override
		protected Object doInBackground() throws Exception {
			btnStart.setEnabled(false);
			Run xCryption = new Run(password, username, path, action, activeTab);
			System.out.println("--------------------------------------------------------------------------------------------Xcoding");				
			boolean inclSubfolders = chckbxInclSubfolders.isSelected();
			char status = xCryption.run(progressBar, lblProsesslabel, inclSubfolders);
			if(status == 't'){
				if(rdbtnDecrypt.isSelected() && chckbxEncryptOnExit.isSelected()){
					System.out.println("--------------------------------------------------------------------------------------------ADDING");
					pathToFolder.add(path);
					authentication.add(password);
					authentication.add(username);
				}
			}
			if( status == 'f' || status == 't'){
				progressBar.setValue(100);
			}
			else if(status == 'e'){
				progressBar.setForeground(Color.RED);
			}
			btnStart.setEnabled(true);
			return null;
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					window.frmEncrypt.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public UI() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	private void initialize() throws IOException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double screenWidth = screenSize.getWidth();
		double screenHeight = screenSize.getHeight();
		double frameXPos = screenWidth/2 - 420/2;
		double frameYPos = screenHeight/2 - 530/2;
		
		// Render main window
		frmEncrypt = new JFrame();;
		frmEncrypt.setFont(new Font("Cambria", Font.PLAIN, 14));
		frmEncrypt.setResizable(false);
		frmEncrypt.setIconImage(Toolkit.getDefaultToolkit().getImage("img\\design\\Icon.png"));
		frmEncrypt.setTitle("Xcrypt");
		frmEncrypt.setBounds((int)frameXPos, (int)frameYPos, 420, 530);
		frmEncrypt.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmEncrypt.addWindowListener(new WindowAdapter() {
			
			//When x is pressed this function will run. If decrypt on exit was set and un and pw is correct then folder will be encrypted.
	        @Override
	        public void windowClosing(WindowEvent event) {
        		frmEncrypt.enable(false);
	        	int progressBarStatus = progressBar.getValue();
	        	if(progressBarStatus > 0 && progressBarStatus < 100 && progressBar.getForeground() == Color.GREEN){
	        		System.out.println("Xcryption in prosess. Can't close");
	        		ProcessRuningOnExit warningWindow = new ProcessRuningOnExit(secretLabel);
	        		warningWindow.execute();
	        		frmEncrypt.enable(true);
	        		return;
	        	}
	        	decryptOnExit(null, secretLabel, secretLabel);
	        }
	    });
		springLayout = new SpringLayout();
		frmEncrypt.getContentPane().setLayout(springLayout);
		
		
		JTextField textFieldPath = createTextField(35, 60, 25, 365);	
		JTextField textFieldUsername = createTextField(100, 125, 25, 250);
		
		JLabel pathLabel = createLabel("Enter folder path", 15, 25);
		pathLabel.setFont(new Font("Cambria", Font.BOLD, 14));

		JLabel pathErrorLabel = createLabel("", 60, 25);
		pathErrorLabel.setFont(new Font("Cambria", Font.PLAIN, 14));
		pathErrorLabel.setForeground(Color.ORANGE);
		pathErrorLabel.setVisible(false);
	
		JLabel usernameErrorLabel = createLabel("Username can't be empty", 125, 25);
		usernameErrorLabel.setFont(new Font("Cambria", Font.PLAIN, 14));
		usernameErrorLabel.setForeground(Color.ORANGE);
		usernameErrorLabel.setVisible(false);
		
		JLabel passwordLabel = createLabel("Enter password", 145, 25);
		passwordLabel.setFont(new Font("Cambria", Font.BOLD, 14));
		
		JLabel passwordErrorLabel = createLabel("Password can't be empty", 190, 25);
		passwordErrorLabel.setFont(new Font("Cambria", Font.PLAIN, 14));
		passwordErrorLabel.setForeground(Color.ORANGE);
		passwordErrorLabel.setVisible(false);;

		createLabel("Folder", 18, 151);
		
		createLabel("File", 18, 205);
		
		JLabel tabsLabel = createLabel("Folder", 0, 0);
		setLabelImage(tabsLabel, "FolderTabSelected.png");
		
		JPasswordField fieldPassword = createPasswordField(165, 190, 25, 250);	
		
		JCheckBox chckbxInclSubfolders = new JCheckBox("Include subfolders");
		chckbxInclSubfolders.setFont(new Font("Cambria", Font.PLAIN, 14));
		disableBackground(chckbxInclSubfolders);
		springLayout.putConstraint(SpringLayout.NORTH, chckbxInclSubfolders, 205, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, chckbxInclSubfolders, 22, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(chckbxInclSubfolders);
		
		JCheckBox chckbxEncryptOnExit = new JCheckBox("Encrypt folder on exit");
		chckbxEncryptOnExit.setFont(new Font("Cambria", Font.PLAIN, 14));
		disableBackground(chckbxEncryptOnExit);
		chckbxEncryptOnExit.setSelected(true);
		springLayout.putConstraint(SpringLayout.NORTH, chckbxEncryptOnExit, 225, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, chckbxEncryptOnExit, 22, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(chckbxEncryptOnExit);
		chckbxEncryptOnExit.setVisible(false);

		
		
		// Radio buttons (En/Decript)
		JRadioButton rdbtnEncrypt = createRadioButton("Encrypt", 120, 330);
		rdbtnEncrypt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				chckbxEncryptOnExit.setVisible(false);
			}
		});
		rdbtnEncrypt.setSelected(true);
		
		JRadioButton rdbtnDecrypt = createRadioButton("Decrypt", 140, 330);
		rdbtnDecrypt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(tabsLabel.getText().equals("Folder")){
					chckbxEncryptOnExit.setVisible(true);	
				}
			}
		});
		
		grouprdbtn(rdbtnEncrypt, rdbtnDecrypt);


		
		JButton btnFolderTab = createButton("", 12, 35, 140, 199);
		btnFolderTab.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(tabsLabel.getText().equals("File")){
					if(rdbtnDecrypt.isSelected()){
						chckbxEncryptOnExit.setVisible(true);
					}
					chckbxInclSubfolders.setVisible(true);
					setLabelImage(tabsLabel, "FolderTabSelected.png");
					tabsLabel.setText("Folder");
					springLayout.putConstraint(SpringLayout.NORTH, btnFolderTab, 12, SpringLayout.NORTH, frmEncrypt.getContentPane());
					springLayout.putConstraint(SpringLayout.NORTH, btnFileTab, 14, SpringLayout.NORTH, frmEncrypt.getContentPane());
					springLayout.putConstraint(SpringLayout.EAST, btnFolderTab, 199 , SpringLayout.WEST, frmEncrypt.getContentPane());
					springLayout.putConstraint(SpringLayout.WEST, btnFileTab, 199, SpringLayout.WEST, frmEncrypt.getContentPane());
				}
			}
		});
		
		btnFileTab = createButton("", 14, 35, 199, 246);
		btnFileTab.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(tabsLabel.getText().equals("Folder")){
					setLabelImage(tabsLabel, "FileTabSelected.png");
					tabsLabel.setText("File");
					springLayout.putConstraint(SpringLayout.NORTH, btnFileTab, 12, SpringLayout.NORTH, frmEncrypt.getContentPane());
					springLayout.putConstraint(SpringLayout.NORTH, btnFolderTab, 14, SpringLayout.NORTH, frmEncrypt.getContentPane());
					springLayout.putConstraint(SpringLayout.EAST, btnFolderTab, 187 , SpringLayout.WEST, frmEncrypt.getContentPane());
					springLayout.putConstraint(SpringLayout.WEST, btnFileTab, 187, SpringLayout.WEST, frmEncrypt.getContentPane());
					chckbxEncryptOnExit.setVisible(false);
					chckbxInclSubfolders.setVisible(false);
				}
			}
		});
		
		
		
		secretLabel = createLabel("", 0, 0);
		secretLabel.setVisible(false);
		secretLabel.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				System.out.println("---------------------------------------------------PropertyChange");
				frmEncrypt.setVisible(true);
				if(secretLabel.getText().equals("Wait")){
					secretLabel.setText("");
				}
				else if(secretLabel.getText().equals("Wait")){
					frmEncrypt.enable(false);
				}
			}
		});
		
		

		progressBar = new JProgressBar();
		progressBar.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				System.out.println("---------------------------------------------------state change" + progressBar.getValue());
				if(secretLabel.getText().equals("Exit") && progressBar.getValue() == 100){
					System.out.println("Inside");
					secretLabel.setText("");
					decryptOnExit(chckbxEncryptOnExit, tabsLabel, tabsLabel);
				}
			}
		});
		progressBar.setFont(new Font("Cambria", Font.PLAIN, 12));
		springLayout.putConstraint(SpringLayout.WEST, progressBar, 35, SpringLayout.WEST, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, progressBar, -10, SpringLayout.SOUTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, progressBar, -28, SpringLayout.EAST, frmEncrypt.getContentPane());
		progressBar.setForeground(Color.GREEN);
		progressBar.setStringPainted(true);
		frmEncrypt.getContentPane().add(progressBar);
		
		
		JLabel lblProsesslabel = createLabel("", 456, 35);
		lblProsesslabel.setFont(new Font("Cambria", Font.PLAIN, 11));
		
		
		
		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Cambria", Font.PLAIN, 59));
		disableBackground(btnStart);
		springLayout.putConstraint(SpringLayout.NORTH, btnStart, 260, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnStart, -50, SpringLayout.SOUTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnStart, 50, SpringLayout.WEST, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnStart, -50, SpringLayout.EAST, frmEncrypt.getContentPane());
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!btnStart.isEnabled()){
					return;
				}
				progressBar.setValue(0);
				progressBar.setForeground(Color.GREEN);
				if(mouseReleasedInsideButton(e, btnStart) == false){
					return;
				}

				String path = textFieldPath.getText();
				String username = textFieldUsername.getText();
				String password = fieldPassword.getText();
				int action = Encrypt;
				boolean runProgram = true;

				
				if(rdbtnDecrypt.isSelected()){
					action = Decrypt;
				}

				if(path.equals("")){
					pathErrorLabel.setText("Please enter path!");
					pathErrorLabel.setVisible(true);
					runProgram = false;
				}
				else if(!new File(path).exists()){
					pathErrorLabel.setText("Please enter valid path!");
					pathErrorLabel.setVisible(true);
					runProgram = false;
				}
				else{
					pathErrorLabel.setVisible(false);
				}
				
				if(username.equals("")){
					usernameErrorLabel.setVisible(true);
					runProgram = false;
				}
				else{
					usernameErrorLabel.setVisible(false);
				}
				
				if(password.equals("")){
					passwordErrorLabel.setVisible(true);
					runProgram = false;
				}
				else{
					passwordErrorLabel.setVisible(false);
				}
				
				if(runProgram == true){
					exProgressBar = new DynamicProgressBarExecute(password, username, path, action, tabsLabel.getText(), btnStart, chckbxEncryptOnExit, chckbxEncryptOnExit, rdbtnDecrypt, progressBar, lblProsesslabel);
					exProgressBar.execute();
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {		
				setLabelImage(startBtnImage, "btnHover.png");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setLabelImage(startBtnImage, "Empty.png");
			}
		});
		frmEncrypt.getContentPane().add(btnStart);	
		
		textFieldUsername.setText("bjarki");
		fieldPassword.setText("bjarki");
		textFieldPath.setText("E:\\Xcrypt\\photos - Copy");
		
		JLabel usernameLabel = createLabel("Enter username", 80, 25);new JLabel("Enter username");
		usernameLabel.setFont(new Font("Cambria", Font.BOLD, 14));
		
		JLabel selectFolderImage = createLabel("", 0, 0);

		startBtnImage = createLabel("", 0, 0);
		
		JLabel BGImage = createLabel("", 0, 0);
		setLabelImage(BGImage, "bgImageMain.png");
		
		JLabel btnSelectPathButton = createLabel("", 36, 59, 379, 404);
		btnSelectPathButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setLabelImage(selectFolderImage, "Empty.png");
				selectPath(tabsLabel, textFieldUsername);
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {		
				setLabelImage(selectFolderImage, "FolderIconHover.png");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setLabelImage(selectFolderImage, "Empty.png");
			}
		});

	
	}
	
	
	private JLabel createLabel(String text, int north, int west) {
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Cambria", Font.PLAIN, 13));
		setSmallSpringlayout(label, north, west);
		setSmallSpringlayout(label, north, west);
		frmEncrypt.getContentPane().add(label);
		
		return label;
	}
	
	private JLabel createLabel(String text, int north, int south, int west, int east) {
		JLabel label = createLabel(text, north, west);
		setBigSpringlayout(label, north, south, west, east);
		
		return label;
	}

	
	private JPasswordField createPasswordField(int north, int south, int west, int east) {
		JPasswordField passwordField = new JPasswordField();
		passwordField.setOpaque(false);
		passwordField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		setBigSpringlayout(passwordField, north, south, west, east);
		frmEncrypt.getContentPane().add(passwordField);
		
		return passwordField;
	}
	
	private JTextField createTextField(int north, int south, int west, int east) {
		JTextField textField = new JTextField();
		textField.setFont(new Font("Cambria", Font.PLAIN, 14));
		textField.setOpaque(false);
		textField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		setBigSpringlayout(textField, north, south, west, east);
		frmEncrypt.getContentPane().add(textField);
		
		return textField;
	}
	
	private JButton createButton(String text, int north, int south, int west, int east) {
		JButton button = new JButton(text);
		disableBackground(button);
		setBigSpringlayout(button, north, south, west, east);
		frmEncrypt.getContentPane().add(button);
		
		return button;
	}
	
	private JRadioButton createRadioButton(String text, int north, int west){
		JRadioButton radioButton = new JRadioButton(text);
		radioButton.setFont(new Font("Cambria", Font.PLAIN, 14));
		disableBackground(radioButton);
		setSmallSpringlayout(radioButton, north, west);
		frmEncrypt.getContentPane().add(radioButton);
		return radioButton;
	}
	
	private void setBigSpringlayout(Component comp, int north, int south, int west, int east) {
		setSmallSpringlayout(comp, north, west);
		springLayout.putConstraint(SpringLayout.SOUTH, comp, south, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, comp, east, SpringLayout.WEST, frmEncrypt.getContentPane());
	}
	
	private void setSmallSpringlayout(Component comp, int north, int west) {
		springLayout.putConstraint(SpringLayout.NORTH, comp, north, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, comp, west, SpringLayout.WEST, frmEncrypt.getContentPane());
	}

	
	/*
	 * Decrypts folders in array pathToFolder if any
	 */
	private void decryptOnExit(JCheckBox chckbxInclSubfolders, JLabel tabsLabel, JLabel lblProsesslabel){
    	int authInt = 0;
    	System.out.println("decrypting on exit " + pathToFolder.size());
    	for(int i = 0; i < pathToFolder.size(); i++){
    		boolean inclSubfolders = chckbxInclSubfolders.isSelected();
    		frmEncrypt.setVisible(false);
    		Run decryptOnExit = new Run(authentication.get(authInt), authentication.get(authInt+1), pathToFolder.get(i), Encrypt, tabsLabel.getText());
    		decryptOnExit.run(progressBar, lblProsesslabel, inclSubfolders);
    		authInt = authInt+2;
    	}      	
    	frmEncrypt.dispose();
        System.exit(0);
	}
	
	/*
	 * Select path to folder with JFileChooser
	 */
	private void selectPath(JLabel tabLabel, JTextField textFieldPath){
		File setPath = new File(textFieldPath.getText());
		if(!setPath.isDirectory()){
			setPath = new File(textFieldPath.getText().substring(0, textFieldPath.getText().lastIndexOf("\\")));
		}
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setCurrentDirectory(setPath);
		fileChooser.setDialogTitle("Select path");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(tabLabel.getText().equals("File")){
			FileFilter imageFilter = new FileNameExtensionFilter("Image files", "jpg", "png");
			fileChooser.addChoosableFileFilter(imageFilter);
			fileChooser.setDialogTitle("Select file");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		}
		int returnVal = fileChooser.showOpenDialog(frmEncrypt);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println(fileChooser.getSelectedFile());
			String selectedPath = fileChooser.getSelectedFile().toString();
		  	textFieldPath.setText(selectedPath);
		  	fileChooser.setVisible(false);
		}
	}
	
	private boolean mouseReleasedInsideButton(MouseEvent e, JButton btnStart){
		double btnHeight = btnStart.getSize().getHeight();
		double btnWidth = btnStart.getSize().getWidth();
		
		if (e.getX() >= 0 && e.getX() <= btnWidth && e.getY() >= 0 && e.getY() <= btnHeight ){
			return true;
		}
		return false;
	}
	
	private void disableBackground(AbstractButton element){
		element.setOpaque(false);
		element.setContentAreaFilled(false);
		element.setBorderPainted(false);
		element.setFocusPainted(false);
	}
	
	private void setLabelImage(JLabel label, String imageName){
		ImageIcon backgroundImage = new ImageIcon("img\\design\\" + imageName);
		label.setIcon(backgroundImage);
	}
	
	private void grouprdbtn(JRadioButton rdbtnEncrypt, JRadioButton rdbtnDecrypt){
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnEncrypt);
		group.add(rdbtnDecrypt);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
