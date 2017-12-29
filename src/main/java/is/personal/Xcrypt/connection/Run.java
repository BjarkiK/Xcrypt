package main.java.is.personal.Xcrypt.connection;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import main.java.is.personal.Xcrypt.logic.FileHandeling;

public class Run {

	private static long seed;
	private static String path;
	private static int action;

	public Run(String Ipassword, String Iusername, String Ipath, int Iaction) {
		seed = calcSeed(Ipassword, Iusername);
		path = Ipath;
		action = Iaction;
	}
	
	
	public boolean run(JProgressBar progressBar, JLabel lblProsesslabel){
		FileHandeling handleFiles = new FileHandeling(seed, path, action);
		return handleFiles.run(progressBar, lblProsesslabel);
	}
	
	private static long calcSeed(String password, String username){
		long seed = 0;
		
		//Chech witch word has more characters
		int longerW = username.length();
		if(password.length() > longerW){
			longerW = password.length();
		}
		
		
		//Shuffle the words together p0+u0+p1+u1+p2+u2+p3+u3+p0+u4+p1+u5
		int pIndex = 0;
		int uIndex = 0;
		for(int i = 0; i < longerW; i++){
			if(pIndex == password.length()){
				pIndex = 0;
			}
			else if(uIndex == username.length()){
				uIndex = 0;
			}
	    	seed += password.charAt(pIndex++);
	    	seed += username.charAt(uIndex++);
	    }
		return seed;
	}

}
