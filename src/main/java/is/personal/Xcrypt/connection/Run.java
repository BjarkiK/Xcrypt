package main.java.is.personal.Xcrypt.connection;


import javax.swing.JLabel;
import javax.swing.JProgressBar;

import main.java.is.personal.Xcrypt.logic.FileHandeling;

public class Run {

	private long seed;
	private String path;
	private int action;
	private String activeTab; //Xcrypt whole folder of one file

	public Run(String Ipassword, String Iusername, String Ipath, int Iaction, String IactiveTab) {
		seed = calcSeed(Ipassword, Iusername);
		path = Ipath;
		action = Iaction;
		activeTab = IactiveTab;
	}
	
	
	public char run(JProgressBar progressBar, JLabel lblProsesslabel, boolean inclSubfolders) {
		FileHandeling handleFiles = new FileHandeling(seed, path, action, activeTab);
		return handleFiles.run(progressBar, lblProsesslabel, inclSubfolders);
	}
	
	private static long calcSeed(String password, String username){
		String seedString = "";
		
		//Chech witch word has more characters
		int longerW = username.length();
		if(password.length() > longerW){
			longerW = password.length();
		}
		
		
		//Shuffle the Strings together p0+u0+p1+u1+p2+u2+p3+u3+p0+u4+p1+u5
		int pIndex = 0;
		int uIndex = 0;
		for(int i = 0; i < longerW; i++){
			if(pIndex == password.length()){
				pIndex = 0;
			}
			else if(uIndex == username.length()){
				uIndex = 0;
			}
			
			seedString = seedString + "" + (int) password.charAt(pIndex++);
			seedString = seedString + "" + (int) username.charAt(uIndex++);
			if(Long.parseLong(seedString) > 2147483392) {
				seedString = "" + Long.parseLong(seedString)%2147483647;
			}
	    }
		long seed = Long.parseLong(seedString);
		System.out.println("SEED: " + seed);
		return seed;
	}

}
