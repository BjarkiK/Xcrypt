package main.java.is.personal.Xcrypt.userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

import main.java.is.personal.Xcrypt.connection.Run;

import javax.swing.SpringLayout;
import javax.swing.ButtonGroup;
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

public class UI {

	private ArrayList<String> pathToFolder = new ArrayList<String>(); //Path to folder to encrypt again on exit
	private ArrayList<String> authentication = new ArrayList<String>(); //Password and username 
	private int Encrypt = 0;
	private int Decrypt = 1;
	private JFrame frmEncrypt;
	private JTextField textFieldPath;
	private JPasswordField fieldPassword;
	private JTextField textFieldUsername;
	private JRadioButton rdbtnEncryp;
	private JRadioButton rdbtnDecrypt;

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
		frmEncrypt = new JFrame();
		frmEncrypt.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Tron\\Pictures\\Capture.PNG"));
		frmEncrypt.setTitle("Encrypt");
		frmEncrypt.setBounds(100, 100, 420, 530);
		frmEncrypt.setMinimumSize(new Dimension(425, 450));
		frmEncrypt.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmEncrypt.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent event) {
	        	int authInt = 0;
	        	for(int i = 0; i < pathToFolder.size(); i++){
	        		frmEncrypt.hide();
	        		Run decryptOnExit = new Run(authentication.get(authInt), authentication.get(authInt+1), pathToFolder.get(i), Encrypt);
	        		decryptOnExit.run();
	        		authInt = authInt+2;
	        	}      	
	        	frmEncrypt.dispose();
	            System.exit(0);
	        }
	    });
		SpringLayout springLayout = new SpringLayout();
		frmEncrypt.getContentPane().setLayout(springLayout);
		
		
		JCheckBox chckbxEncryptOnExit = new JCheckBox("Encrypt on exit");
		chckbxEncryptOnExit.setSelected(true);
		springLayout.putConstraint(SpringLayout.NORTH, chckbxEncryptOnExit, 215, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, chckbxEncryptOnExit, 22, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(chckbxEncryptOnExit);
		chckbxEncryptOnExit.hide();

		
		// Radio buttons (En/Decript)
		JRadioButton rdbtnEncrypt = rdbtnEncrypt(springLayout, chckbxEncryptOnExit);
		JRadioButton rdbtnDecrypt = rdbtnDecrypt(springLayout, chckbxEncryptOnExit);
		grouprdbtn(rdbtnEncrypt, rdbtnDecrypt);


		
		
		JLabel pathLable = new JLabel("Enter folder path");
		springLayout.putConstraint(SpringLayout.NORTH, pathLable, 15, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, pathLable, 25, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(pathLable);
		
		textFieldPath = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textFieldPath, 35, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, textFieldPath, 60, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textFieldPath, 25, SpringLayout.WEST, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textFieldPath, 400, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(textFieldPath);
		
		
		JLabel pathErrorLable = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, pathErrorLable, 60, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, pathErrorLable, 25, SpringLayout.WEST, frmEncrypt.getContentPane());
		pathErrorLable.setForeground(Color.RED);
		frmEncrypt.getContentPane().add(pathErrorLable);
		pathErrorLable.hide();
		
		
		
		JLabel usernameLable = new JLabel("Enter username");
		springLayout.putConstraint(SpringLayout.NORTH, usernameLable, 80, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, usernameLable, 25, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(usernameLable);
		
		textFieldUsername = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textFieldUsername, 100, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, textFieldUsername, 125, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textFieldUsername, 25, SpringLayout.WEST, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textFieldUsername, 250, SpringLayout.WEST, frmEncrypt.getContentPane());
		textFieldUsername.setToolTipText("");
		frmEncrypt.getContentPane().add(textFieldUsername);
		
		JLabel usernameErrorLable = new JLabel("Username can't be empty");
		usernameErrorLable.setForeground(Color.RED);
		springLayout.putConstraint(SpringLayout.NORTH, usernameErrorLable, 125, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, usernameErrorLable, 25, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(usernameErrorLable);
		usernameErrorLable.hide();
		
		
		
		
		JLabel passwordLable = new JLabel("Enter password");
		springLayout.putConstraint(SpringLayout.NORTH, passwordLable, 145, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, passwordLable, 25, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(passwordLable);
		
		fieldPassword = new JPasswordField();
		springLayout.putConstraint(SpringLayout.NORTH, fieldPassword, 165, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, fieldPassword, 190, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, fieldPassword, 25, SpringLayout.WEST, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, fieldPassword, 250, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(fieldPassword);

		JLabel passwordErrorLable = new JLabel("Password cant be empty");
		springLayout.putConstraint(SpringLayout.NORTH, passwordErrorLable, 190, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, passwordErrorLable, 25, SpringLayout.WEST, frmEncrypt.getContentPane());
		passwordErrorLable.setForeground(Color.RED);
		frmEncrypt.getContentPane().add(passwordErrorLable);
		passwordErrorLable.hide();

		
		

		
		JProgressBar progressBar = new JProgressBar();
		springLayout.putConstraint(SpringLayout.WEST, progressBar, 35, SpringLayout.WEST, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, progressBar, -10, SpringLayout.SOUTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, progressBar, -28, SpringLayout.EAST, frmEncrypt.getContentPane());
		progressBar.setForeground(Color.GREEN);
		progressBar.setStringPainted(true);
		frmEncrypt.getContentPane().add(progressBar);
		
		
		JButton btnStart = new JButton("Start");
		springLayout.putConstraint(SpringLayout.NORTH, btnStart, 260, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnStart, -50, SpringLayout.SOUTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnStart, 50, SpringLayout.WEST, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnStart, -50, SpringLayout.EAST, frmEncrypt.getContentPane());
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				String path = textFieldPath.getText();
				String username = textFieldUsername.getText();
				String password = fieldPassword.getText();
				int action = Encrypt;
				boolean runProgram = true;

				
				if(rdbtnDecrypt.isSelected()){
					action = Decrypt;
				}

				
				if(path.equals("")){
					pathErrorLable.setText("Please enter path!");
					pathErrorLable.show();
					runProgram = false;
				}
				else if(!new File(path).exists()){
					pathErrorLable.setText("Please enter valid path!");
					pathErrorLable.show();
					runProgram = false;
				}
				else{
					pathErrorLable.hide();
				}
				
				if(username.equals("")){
					usernameErrorLable.show();
					runProgram = false;
				}
				else{
					usernameErrorLable.hide();
				}
				
				if(password.equals("")){
					passwordErrorLable.show();
					runProgram = false;
				}
				else{
					passwordErrorLable.hide();
				}
				
				if(runProgram == true){
					progressBar.setValue(0);
					Run xCryption = new Run(password, username, path, action);
					boolean status = xCryption.run();
					if(status == true){
						progressBar.setValue(100);
						if(rdbtnDecrypt.isSelected() && chckbxEncryptOnExit.isSelected()){
							pathToFolder.add(path);
							authentication.add(password);
							authentication.add(username);
						}
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		frmEncrypt.getContentPane().add(btnStart);	
		
		textFieldUsername.setText("bjarki");
		fieldPassword.setText("bjarki");
		textFieldPath.setText("D:\\photos - Copy");


		
		
		/* Multiple decription
		JCheckBox chckbxNewCheckBox = new JCheckBox("Multiple decription");
		chckbxNewCheckBox.setToolTipText("Decript even if file has already been decripted");
		springLayout.putConstraint(SpringLayout.NORTH, chckbxNewCheckBox, 29, SpringLayout.SOUTH, passwordErrorLable);
		springLayout.putConstraint(SpringLayout.WEST, chckbxNewCheckBox, 0, SpringLayout.WEST, pathLable);
		frmEncrypt.getContentPane().add(chckbxNewCheckBox);
		*/
		
		
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


	
	
	private JRadioButton rdbtnEncrypt(SpringLayout springLayout, JCheckBox chckbxEncryptOnExit){
		rdbtnEncryp = new JRadioButton("Encrypt");
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
	
	private void grouprdbtn(JRadioButton rdbtnEncrypt, JRadioButton rdbtnDecrypt){
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnEncrypt);
		group.add(rdbtnDecrypt);
	}
}
