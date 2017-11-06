package main.java.is.personal.Xcrypt.connection;

import main.java.is.personal.Xcrypt.logic.FileHandeling;
import tests.java.is.personal.Xcrypt.Tests;

public class Run {

	private static String password;
	private static String username;
	private static String path;
	private static int action;

	public Run(String Ipassword, String Iusername, String Ipath, int Iaction) {
		password = Ipassword;
		username = Iusername;
		path = Ipath;
		action = Iaction;
	}
	
	
	public boolean run(){
		FileHandeling handleFiles = new FileHandeling(password, username, path, action);
		return handleFiles.run();
	}

}
