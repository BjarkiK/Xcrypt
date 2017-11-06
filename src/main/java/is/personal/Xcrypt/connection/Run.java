package main.java.is.personal.Xcrypt.connection;

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
		HandleFiles handleFiles = new HandleFiles(password, username, path, action);
		return handleFiles.run();
	}

}
