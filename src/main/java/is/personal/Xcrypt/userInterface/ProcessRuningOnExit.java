package main.java.is.personal.Xcrypt.userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Window.Type;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProcessRuningOnExit extends SwingWorker<Object, Object>{

	private JFrame frmExit;
	private JLabel secretLabel;
	

	@Override
	protected Object doInBackground() throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProcessRuningOnExit window = new ProcessRuningOnExit(secretLabel);
					window.frmExit.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return null;
	}

	/**
	 * Create the application.
	 */
	public ProcessRuningOnExit(JLabel secretLabel) {
		this.secretLabel = secretLabel;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmExit = new JFrame();
		frmExit.setTitle("Can't exit just yet");
		frmExit.getContentPane().setLayout(null);
		frmExit.setResizable(false);
		frmExit.setType(Type.POPUP);
		frmExit.setBounds(100, 100, 420, 229);
		frmExit.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmExit.setIconImage(Toolkit.getDefaultToolkit().getImage("img\\design\\Icon.png"));
		
		JLabel lblXcryptionIsStill = new JLabel("Xcryption is still running!");
		lblXcryptionIsStill.setHorizontalAlignment(SwingConstants.CENTER);
		lblXcryptionIsStill.setBounds(10, 11, 394, 39);
		lblXcryptionIsStill.setFont(new Font("Cambria", Font.BOLD, 29));
		frmExit.getContentPane().add(lblXcryptionIsStill);
		
		JLabel lblPleaseWaitUntill = new JLabel("Please wait untill all files have been xcrypted");
		lblPleaseWaitUntill.setFont(new Font("Cambria", Font.PLAIN, 18));
		lblPleaseWaitUntill.setVerticalAlignment(SwingConstants.TOP);
		lblPleaseWaitUntill.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseWaitUntill.setBounds(20, 61, 384, 29);
		frmExit.getContentPane().add(lblPleaseWaitUntill);
		
		JLabel lblOrSomeFiles = new JLabel("or some files may become currupted.");
		lblOrSomeFiles.setVerticalAlignment(SwingConstants.TOP);
		lblOrSomeFiles.setHorizontalAlignment(SwingConstants.CENTER);
		lblOrSomeFiles.setFont(new Font("Cambria", Font.PLAIN, 18));
		lblOrSomeFiles.setBounds(20, 83, 384, 29);
		frmExit.getContentPane().add(lblOrSomeFiles);
		
		JButton btnWait = new JButton("OK I'll wait");
		btnWait.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frmExit.dispose();
				secretLabel.setText("Wait");
			}
		});
		btnWait.setBounds(51, 123, 135, 48);
		btnWait.setFocusPainted(false);
		frmExit.getContentPane().add(btnWait);
		
		JButton btnExitWhenDone = new JButton("Exit when done");
		btnExitWhenDone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frmExit.dispose();
				secretLabel.setText("Exit");
			}
		});
		btnExitWhenDone.setBounds(234, 123, 135, 48);
		btnExitWhenDone.setFocusPainted(false);
		frmExit.getContentPane().add(btnExitWhenDone);
		
		

	}
}
