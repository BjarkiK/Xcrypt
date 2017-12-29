package main.java.is.personal.Xcrypt.userInterface;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

import main.java.is.personal.Xcrypt.connection.Run;

import javax.swing.SpringLayout;
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
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;


public class UI{

	private static final long serialVersionUID = 1L;
	private ArrayList<String> pathToFolder = new ArrayList<String>(); //Path to folder to encrypt again on exit		//Minor security hole! Fix when there is time
	private ArrayList<String> authentication = new ArrayList<String>(); //Password and username 					//Security hole! Fix when there is time
	private int Encrypt = 0;
	private int Decrypt = 1;
	private JFrame frmEncrypt;
	private JTextField textFieldPath, textFieldUsername;
	private JPasswordField fieldPassword;
	private JRadioButton rdbtnEncryp, rdbtnDecrypt;
	private static JButton btnStart;
	private JCheckBox chckbxEncryptOnExit;
	private JLabel BGImage, lblProsesslabel, secretLabel;
	private JProgressBar progressBar;
	private DynamicProgressBarExecute exProgressBar;
	
	
	class DynamicProgressBarExecute extends SwingWorker<Object, Object>{
		private String password, username, path;
		private int action;
		
		DynamicProgressBarExecute(String pw, String un, String path, int action){
			this.password = pw;
			this.username = un;
			this.path = path;
			this.action = action;
		}

		@Override
		protected Object doInBackground() throws Exception {
			btnStart.setEnabled(false);
			Run xCryption = new Run(password, username, path, action);
			boolean status = xCryption.run(progressBar, lblProsesslabel);
			if(status == true){
				if(rdbtnDecrypt.isSelected() && chckbxEncryptOnExit.isSelected()){
					pathToFolder.add(path);
					authentication.add(password);
					authentication.add(username);
				}
			}
			sleep(1000);
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
		
		// Render main window
		frmEncrypt = new JFrame();;
		frmEncrypt.setFont(new Font("Cambria", Font.PLAIN, 14));
		frmEncrypt.setResizable(false);
		frmEncrypt.setIconImage(Toolkit.getDefaultToolkit().getImage("img\\design\\Icon.png"));
		frmEncrypt.setTitle("Xcrypt");
		frmEncrypt.setBounds(100, 100, 420, 530);
		frmEncrypt.setMinimumSize(new Dimension(425, 450));
		frmEncrypt.setMaximumSize(new Dimension(420, 530));
		frmEncrypt.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmEncrypt.addWindowListener(new WindowAdapter() {
			
			//When x is pressed this function will run. If decrypt on exit was set and un and pw is correct then folder will be encrypted.
	        @Override
	        public void windowClosing(WindowEvent event) {
        		frmEncrypt.enable(false);
	        	int progressBarStatus = progressBar.getValue();
	        	if(progressBarStatus > 0 && progressBarStatus < 100){
	        		System.out.println("Xcryption in prosess. Can't close");
	        		ProcessRuningOnExit warningWindow = new ProcessRuningOnExit(secretLabel);
	        		warningWindow.execute();
	        		return;
	        	}
	        	decryptOnExit();
	        }
	    });
		SpringLayout springLayout = new SpringLayout();
		frmEncrypt.getContentPane().setLayout(springLayout);
		
		
		chckbxEncryptOnExit = new JCheckBox("Encrypt on exit");
		chckbxEncryptOnExit.setFont(new Font("Cambria", Font.PLAIN, 14));
		disableBackground(chckbxEncryptOnExit);
		chckbxEncryptOnExit.setSelected(true);
		springLayout.putConstraint(SpringLayout.NORTH, chckbxEncryptOnExit, 215, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, chckbxEncryptOnExit, 22, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(chckbxEncryptOnExit);
		chckbxEncryptOnExit.hide();

		
		// Radio buttons (En/Decript)
		JRadioButton rdbtnEncrypt = rdbtnEncrypt(springLayout, chckbxEncryptOnExit);
		JRadioButton rdbtnDecrypt = rdbtnDecrypt(springLayout, chckbxEncryptOnExit);
		grouprdbtn(rdbtnEncrypt, rdbtnDecrypt);


		
		
		
		JLabel pathLabel = new JLabel("Enter folder path");
		pathLabel.setFont(new Font("Cambria", Font.BOLD, 14));
		springLayout.putConstraint(SpringLayout.NORTH, pathLabel, 15, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, pathLabel, 25, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(pathLabel);
		
		textFieldPath = new JTextField();
		textFieldPath.setFont(new Font("Cambria", Font.PLAIN, 14));
		textFieldPath.setOpaque(false);
		textFieldPath.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		springLayout.putConstraint(SpringLayout.NORTH, textFieldPath, 35, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, textFieldPath, 60, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textFieldPath, 25, SpringLayout.WEST, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textFieldPath, 400, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(textFieldPath);
		
		
		JLabel pathErrorLabel = new JLabel("");
		pathErrorLabel.setFont(new Font("Cambria", Font.PLAIN, 14));
		springLayout.putConstraint(SpringLayout.NORTH, pathErrorLabel, 60, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, pathErrorLabel, 25, SpringLayout.WEST, frmEncrypt.getContentPane());
		pathErrorLabel.setForeground(Color.ORANGE);
		frmEncrypt.getContentPane().add(pathErrorLabel);
		pathErrorLabel.hide();
		
		
		
		JLabel usernameLabel = new JLabel("Enter username");
		usernameLabel.setFont(new Font("Cambria", Font.BOLD, 14));
		springLayout.putConstraint(SpringLayout.NORTH, usernameLabel, 80, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, usernameLabel, 25, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(usernameLabel);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setFont(new Font("Cambria", Font.PLAIN, 14));
		textFieldUsername.setOpaque(false);
		textFieldUsername.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		springLayout.putConstraint(SpringLayout.NORTH, textFieldUsername, 100, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, textFieldUsername, 125, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textFieldUsername, 25, SpringLayout.WEST, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textFieldUsername, 250, SpringLayout.WEST, frmEncrypt.getContentPane());
		textFieldUsername.setToolTipText("");
		frmEncrypt.getContentPane().add(textFieldUsername);
		
		JLabel usernameErrorLabel = new JLabel("Username can't be empty");
		usernameErrorLabel.setFont(new Font("Cambria", Font.PLAIN, 14));
		usernameErrorLabel.setForeground(Color.ORANGE);
		springLayout.putConstraint(SpringLayout.NORTH, usernameErrorLabel, 125, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, usernameErrorLabel, 25, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(usernameErrorLabel);
		usernameErrorLabel.hide();
		
		
		
		
		JLabel passwordLabel = new JLabel("Enter password");
		passwordLabel.setFont(new Font("Cambria", Font.BOLD, 14));
		springLayout.putConstraint(SpringLayout.NORTH, passwordLabel, 145, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, passwordLabel, 25, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(passwordLabel);
		
		fieldPassword = new JPasswordField();
		fieldPassword.setOpaque(false);
		fieldPassword.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		springLayout.putConstraint(SpringLayout.NORTH, fieldPassword, 165, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, fieldPassword, 190, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, fieldPassword, 25, SpringLayout.WEST, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, fieldPassword, 250, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(fieldPassword);

		JLabel passwordErrorLabel = new JLabel("Password can't be empty");
		passwordErrorLabel.setFont(new Font("Cambria", Font.PLAIN, 14));
		springLayout.putConstraint(SpringLayout.NORTH, passwordErrorLabel, 190, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, passwordErrorLabel, 25, SpringLayout.WEST, frmEncrypt.getContentPane());
		passwordErrorLabel.setForeground(Color.ORANGE);
		frmEncrypt.getContentPane().add(passwordErrorLabel);
		passwordErrorLabel.hide();

		
		

		progressBar = new JProgressBar();
		progressBar.setFont(new Font("Cambria", Font.PLAIN, 12));
		springLayout.putConstraint(SpringLayout.WEST, progressBar, 35, SpringLayout.WEST, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, progressBar, -10, SpringLayout.SOUTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, progressBar, -28, SpringLayout.EAST, frmEncrypt.getContentPane());
		progressBar.setForeground(Color.GREEN);
		progressBar.setStringPainted(true);
		frmEncrypt.getContentPane().add(progressBar);
		
		
		lblProsesslabel = new JLabel("");
		lblProsesslabel.setFont(new Font("Cambria", Font.PLAIN, 11));
		springLayout.putConstraint(SpringLayout.NORTH, lblProsesslabel, 456, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblProsesslabel, 35, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(lblProsesslabel);
		
		
		
		btnStart = new JButton("Start");
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
				setMainBackgroundImage("bgImageExtended.png");
				if(mouseReleasedInsideButton(e) == false){
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
					pathErrorLabel.show();
					runProgram = false;
				}
				else if(!new File(path).exists()){
					pathErrorLabel.setText("Please enter valid path!");
					pathErrorLabel.show();
					runProgram = false;
				}
				else{
					pathErrorLabel.hide();
				}
				
				if(username.equals("")){
					usernameErrorLabel.show();
					runProgram = false;
				}
				else{
					usernameErrorLabel.hide();
				}
				
				if(password.equals("")){
					passwordErrorLabel.show();
					runProgram = false;
				}
				else{
					passwordErrorLabel.hide();
				}
				
				if(runProgram == true){
					exProgressBar = new DynamicProgressBarExecute(password, username, path, action);
					exProgressBar.execute();

				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {		
				btnStart.setForeground(new Color(63, 132, 216));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnStart.setForeground(Color.BLACK);
				setMainBackgroundImage("bgImageExtended.png");
			}
			@Override
			public void mousePressed(MouseEvent e) {
				setMainBackgroundImage("bgImageExtendedBtnClicked.png");
			}
		});
		frmEncrypt.getContentPane().add(btnStart);	
		
		textFieldUsername.setText("bjarki");
		fieldPassword.setText("bjarki");
		textFieldPath.setText("D:\\photos - Copy");
		
		
		secretLabel = new JLabel("");
		secretLabel.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				frmEncrypt.show();
				System.out.println("---------------------------------------------------PropertyChange");
				if(secretLabel.getText().equals("Exit")){
					secretLabel.setText("");
					while(progressBar.getValue() != 100){
						sleep(250);
					}
					decryptOnExit();
				}
				else if(secretLabel.getText().equals("Wait")){
					frmEncrypt.enable(true);
					secretLabel.setText("");
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, secretLabel, 0, SpringLayout.NORTH, chckbxEncryptOnExit);
		springLayout.putConstraint(SpringLayout.WEST, secretLabel, 0, SpringLayout.EAST, chckbxEncryptOnExit);
		secretLabel.hide();
		frmEncrypt.getContentPane().add(secretLabel);
		
		BGImage = new JLabel("");
		setMainBackgroundImage("bgImageExtended.png");
		springLayout.putConstraint(SpringLayout.NORTH, BGImage, 0, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, BGImage, 0, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(BGImage);


		
		/* Select path window
		JButton btnFile = new JButton("File");
		btnFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					String path = textFieldPath.getText();
					if(path.equals("")){
					}
					else if(!new File(path).exists()){
					}
					selectPath();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnFile, -1, SpringLayout.NORTH, textFieldPath);
		springLayout.putConstraint(SpringLayout.EAST, btnFile, 0, SpringLayout.EAST, progressBar);
		frmEncrypt.getContentPane().add(btnFile);
		
		*/
	
	}
	
	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	private void selectPath(File path) throws IOException {
		
		// Render main window
		frmEncrypt = new JFrame();
		frmEncrypt.setTitle("Encrypt");
		frmEncrypt.setBounds(100, 100, 484, 570);
		frmEncrypt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setCurrentDirectory(path);
		int returnVal = fileChooser.showOpenDialog(frmEncrypt);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
		   System.out.println("You chose to open this file: " + fileChooser.getSelectedFile().getName());
		   fileChooser.hide();
		}
		frmEncrypt.getContentPane().add(fileChooser);
	}

	private void sleep(int milliseconds){
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Decrypts folders in array pathToFolder if any
	 */
	@SuppressWarnings("deprecation")
	private void decryptOnExit(){
    	int authInt = 0;
    	for(int i = 0; i < pathToFolder.size(); i++){
    		frmEncrypt.hide();
    		Run decryptOnExit = new Run(authentication.get(authInt), authentication.get(authInt+1), pathToFolder.get(i), Encrypt);
    		decryptOnExit.run(progressBar, lblProsesslabel);
    		authInt = authInt+2;
    	}      	
    	frmEncrypt.dispose();
        System.exit(0);
	}
	
	private JRadioButton rdbtnEncrypt(SpringLayout springLayout, JCheckBox chckbxEncryptOnExit){
		rdbtnEncryp = new JRadioButton("Encrypt");
		rdbtnEncryp.setFont(new Font("Cambria", Font.PLAIN, 14));
		disableBackground(rdbtnEncryp);
		rdbtnEncryp.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseReleased(MouseEvent e) {
				chckbxEncryptOnExit.hide();
			}
		});
		rdbtnEncryp.setSelected(true);
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnEncryp, 120, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, rdbtnEncryp, 330, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(rdbtnEncryp);
		return rdbtnEncryp;
	}
	
	private JRadioButton rdbtnDecrypt(SpringLayout springLayout, JCheckBox chckbxEncryptOnExit){
		rdbtnDecrypt = new JRadioButton("Decrypt");
		rdbtnDecrypt.setFont(new Font("Cambria", Font.PLAIN, 14));
		disableBackground(rdbtnDecrypt);
		rdbtnDecrypt.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseReleased(MouseEvent e) {
				chckbxEncryptOnExit.show();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnDecrypt, 140, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, rdbtnDecrypt, 330, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(rdbtnDecrypt);
		return rdbtnDecrypt;
	}
	
	private boolean mouseReleasedInsideButton(MouseEvent e){
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
	
	private void setMainBackgroundImage(String imageName){
		ImageIcon backgroundImage = new ImageIcon("img\\design\\" + imageName);
		BGImage.setIcon(backgroundImage);
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
