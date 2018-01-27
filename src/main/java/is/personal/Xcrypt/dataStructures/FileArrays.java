package main.java.is.personal.Xcrypt.dataStructures;

import java.util.ArrayList;

public class FileArrays {
	
	public ArrayList<String> img; //Absolute path to files
	public ArrayList<String> dir; //All sub directories in folder
	public ArrayList<String> alreadyEncr; //Files in folder that are already encrypted
	public ArrayList<String> oldFiles; //Old files being handeld
	public ArrayList<String> newFiles; //Newly xcrypted files
	
	public FileArrays(){
		this.img = new ArrayList<String>(); //Absolute path to files
		this.dir = new ArrayList<String>(); //All sub directories in folder
		this.alreadyEncr = new ArrayList<String>(); //Files in folder that are already encrypted
		this.oldFiles = new ArrayList<String>(); //Old files being handeld
		this.newFiles = new ArrayList<String>(); //Newly xcrypted files
	}


}
