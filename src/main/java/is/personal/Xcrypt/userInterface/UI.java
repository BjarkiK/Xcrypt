package main.java.is.personal.Xcrypt.userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorListener;

import java.awt.CardLayout;
import java.awt.GridLayout;
import javax.swing.SpringLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Event;
import java.awt.Toolkit;
import javax.swing.JPasswordField;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class UI {


	private JFrame frmEncrypt;
	private JTextField textFieldPath;
	private JPasswordField fieldPassword;
	private JTextField textFieldUsername;
	private JRadioButton rdbtnEncryp;
	private JRadioButton rdbtnDecrypt_1;
	private boolean runProgram = true;
	
	private boolean tri = false;

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
	 */
	public UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("deprecation")
	private void initialize() {
		
		
		frmEncrypt = new JFrame();
		frmEncrypt.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Tron\\Pictures\\Capture.PNG"));
		frmEncrypt.setTitle("Encrypt");
		frmEncrypt.setBounds(100, 100, 484, 570);
		frmEncrypt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmEncrypt.getContentPane().setLayout(springLayout);
		
		
		JRadioButton rdbtnEncrypt = rdbtnEncrypt(springLayout);
		JRadioButton rdbtnDecrypt = rdbtnDecrypt(springLayout);
		grouprdbtn(rdbtnEncrypt, rdbtnDecrypt);
		
		JLabel pathLable = new JLabel("Enter folder path");
		springLayout.putConstraint(SpringLayout.NORTH, pathLable, 35, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, pathLable, 23, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(pathLable);
		
		textFieldPath = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textFieldPath, 53, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textFieldPath, 23, SpringLayout.WEST, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textFieldPath, -23, SpringLayout.EAST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(textFieldPath);
		textFieldPath.setColumns(10);
		
		
		JLabel pathErrorLable = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, pathErrorLable, 2, SpringLayout.SOUTH, textFieldPath);
		springLayout.putConstraint(SpringLayout.WEST, pathErrorLable, 23, SpringLayout.WEST, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, pathErrorLable, 402, SpringLayout.WEST, pathLable);
		pathErrorLable.setForeground(Color.RED);
		frmEncrypt.getContentPane().add(pathErrorLable);
		pathErrorLable.hide();
		
		
		
		fieldPassword = new JPasswordField();
		springLayout.putConstraint(SpringLayout.WEST, fieldPassword, 21, SpringLayout.WEST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(fieldPassword);

		
		JLabel usernameLable = new JLabel("Enter username");
		springLayout.putConstraint(SpringLayout.SOUTH, pathErrorLable, -11, SpringLayout.NORTH, usernameLable);
		springLayout.putConstraint(SpringLayout.NORTH, usernameLable, 100, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, usernameLable, 0, SpringLayout.WEST, pathLable);
		frmEncrypt.getContentPane().add(usernameLable);
		
		textFieldUsername = new JTextField();
		springLayout.putConstraint(SpringLayout.EAST, fieldPassword, -22, SpringLayout.EAST, textFieldUsername);
		springLayout.putConstraint(SpringLayout.WEST, textFieldUsername, 23, SpringLayout.WEST, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textFieldUsername, -252, SpringLayout.EAST, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, textFieldUsername, 6, SpringLayout.SOUTH, usernameLable);
		textFieldUsername.setToolTipText("");
		frmEncrypt.getContentPane().add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		JProgressBar progressBar = new JProgressBar();
		springLayout.putConstraint(SpringLayout.WEST, progressBar, -410, SpringLayout.EAST, textFieldPath);
		springLayout.putConstraint(SpringLayout.SOUTH, progressBar, -10, SpringLayout.SOUTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, progressBar, -28, SpringLayout.EAST, frmEncrypt.getContentPane());
		progressBar.setForeground(Color.GREEN);
		progressBar.setStringPainted(true);
		frmEncrypt.getContentPane().add(progressBar);
		
		JLabel passwordErrorLable = new JLabel("Password cant be empty");
		springLayout.putConstraint(SpringLayout.SOUTH, fieldPassword, -2, SpringLayout.NORTH, passwordErrorLable);
		springLayout.putConstraint(SpringLayout.NORTH, passwordErrorLable, 4, SpringLayout.NORTH, rdbtnDecrypt_1);
		springLayout.putConstraint(SpringLayout.WEST, passwordErrorLable, 0, SpringLayout.WEST, pathLable);
		passwordErrorLable.setForeground(Color.RED);
		frmEncrypt.getContentPane().add(passwordErrorLable);
		passwordErrorLable.hide();
		
		JLabel passwordLable = new JLabel("Enter password");
		springLayout.putConstraint(SpringLayout.WEST, passwordLable, 23, SpringLayout.WEST, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, passwordLable, -6, SpringLayout.NORTH, fieldPassword);
		frmEncrypt.getContentPane().add(passwordLable);
		
		JLabel usernameErrorLable = new JLabel("Username can't be empty");
		usernameErrorLable.setForeground(Color.RED);
		springLayout.putConstraint(SpringLayout.NORTH, usernameErrorLable, 6, SpringLayout.SOUTH, textFieldUsername);
		springLayout.putConstraint(SpringLayout.WEST, usernameErrorLable, 0, SpringLayout.WEST, pathLable);
		frmEncrypt.getContentPane().add(usernameErrorLable);
		usernameErrorLable.hide();
		
		
		JButton btnStart = new JButton("Start");
		springLayout.putConstraint(SpringLayout.NORTH, btnStart, 131, SpringLayout.SOUTH, rdbtnDecrypt);
		springLayout.putConstraint(SpringLayout.WEST, btnStart, 40, SpringLayout.WEST, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnStart, -68, SpringLayout.SOUTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnStart, -55, SpringLayout.EAST, frmEncrypt.getContentPane());
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				String path = textFieldPath.getText();
				String username = textFieldUsername.getText();
				String password = fieldPassword.getText();
				int Encrypt = 0;
				int Decrypt = 1;
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
					//RunXcryption xCryption = new RunXcryption(password, username, path, action);
					boolean status = true;//xCryption.run();
					if(status == true){
						progressBar.setValue(100);
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		frmEncrypt.getContentPane().add(btnStart);	
		
		textFieldUsername.setText("bjarki");
		fieldPassword.setText("bjarki");
		textFieldPath.setText("D:\\photos - Copy");
	}
	


	
	
	private JRadioButton rdbtnEncrypt(SpringLayout springLayout){
		rdbtnEncryp = new JRadioButton("Encrypt");
		rdbtnEncryp.setSelected(true);
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnEncryp, 175, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, rdbtnEncryp, -78, SpringLayout.EAST, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, rdbtnEncryp, 0, SpringLayout.EAST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(rdbtnEncryp);
		return rdbtnEncryp;
	}
	
	private JRadioButton rdbtnDecrypt(SpringLayout springLayout){
		rdbtnDecrypt_1 = new JRadioButton("Decrypt");
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnDecrypt_1, 204, SpringLayout.NORTH, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, rdbtnDecrypt_1, 390, SpringLayout.WEST, frmEncrypt.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, rdbtnDecrypt_1, 0, SpringLayout.EAST, frmEncrypt.getContentPane());
		frmEncrypt.getContentPane().add(rdbtnDecrypt_1);
		return rdbtnDecrypt_1;
	}
	
	private void grouprdbtn(JRadioButton rdbtnEncrypt, JRadioButton rdbtnDecrypt){
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnEncrypt);
		group.add(rdbtnDecrypt);
	}
	
}
