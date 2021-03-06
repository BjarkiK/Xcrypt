package main.java.is.personal.Xcrypt.logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import main.java.is.personal.Xcrypt.dataStructures.DecryptReturn;
import main.java.is.personal.Xcrypt.dataStructures.FileArrays;


public class FileHandeling {

	private static long seed;
	private static String path;
	private static int WhatToDo, Encrypt = 0, Decrypt = 1;
	private static String activeTab; //Xcrypt whole folder or one file

	/*
	 * Constructor
	 * Seed is the long number that is used to generate the random numbers
	 * path is the path to the folder going to be xcrypted
	 * WhatToDo is whether to decrypt or encrypt
	 * activeTab is whether to xcrypt whole folder(s) or single file
	 */
	public FileHandeling(long Iseed, String Ipath, int Iaction, String IactiveTab){
		seed = Iseed;
		path = Ipath;
		WhatToDo = Iaction;
		activeTab = IactiveTab;
	}

	/*
	 * Simply a function to start the xcryption
	 */
	public char run(JProgressBar progressBar, JLabel lblProsesslabel, boolean inclSubfolders){
		try {
			return runProgram(progressBar, lblProsesslabel, inclSubfolders);
		} catch (IOException e) {
			e.printStackTrace();
			return 'f';
		}
	}
	
	/*
	 * Just a way to big function that does way to much!
	 */
	private static char runProgram(JProgressBar progressBar, JLabel lblProsesslabel, boolean inclSubfolders) throws IOException {
		FileArrays arr = getFiles(path, new FileArrays(), inclSubfolders);
		int nrOfImages = arr.img.size();
		
		if(nrOfImages == 0){
			System.out.println("No images found");
			return 'f';
		}
		
		String nextFile = "";
		String outputFile = "";
	    File f = null;
	    BufferedImage img = null;
	    
		try{
			System.out.println("Start ");
			int countSuccess = 0;
			for(int i = 0; i < arr.img.size(); i++){
				nextFile = arr.img.get(i);
				img = readImage(f, nextFile);
				System.out.println("Currend file: " + nextFile);
				
				if(WhatToDo == Encrypt){
					Encryption encrypt = new Encryption();
				    //If image already decrypted add path to array
				    if(encrypt.isAlreadyEncrypted(img) == true){
				    	System.out.println("Already encrypted");
				    	arr.alreadyEncr.add(nextFile);
				    	updateJFrame(i, nrOfImages, progressBar, lblProsesslabel);
				    	continue;
				    }
				    DecryptReturn DR = encrypt.encryptImage(img, seed);
				    if(DR.decrpted == false) {
				    	System.out.println("Decryption not successfull for: " + nextFile);
				    	continue;
				    }
					img = DR.image;
					outputFile = encrypt.encryptName(nextFile.substring(0, nextFile.lastIndexOf('.')), seed) + ".png";
					countSuccess++;
				}
				else if(WhatToDo == Decrypt){
					Decryption decrypt = new Decryption();
					if(decrypt.isEncrypted(img) == false){
						System.out.println("File is not encrypted so it can not be decrypted");
						updateJFrame(i, nrOfImages, progressBar, lblProsesslabel);
						continue;
					}
					img = decrypt.decryptImage(img, seed);
					outputFile = decrypt.decryptName(nextFile.substring(0, nextFile.lastIndexOf('.')), seed)  + ".png";
					countSuccess++;
				}
				arr.newFiles.add(outputFile);
				
				System.out.println("Outputing: " + outputFile + "\n");
	
				writeImage(img , f, outputFile);
				hideFile(nextFile);
				arr.oldFiles.add(nextFile);
				
				updateJFrame(i, nrOfImages, progressBar, lblProsesslabel);
			}
			
			// If any file in folder was already encrypted
			if(!arr.alreadyEncr.isEmpty()){
				System.out.println("One or more files were already decripted. Skip ing these files.");				
			}
			
			deleteImages(arr.oldFiles);
			lblProsesslabel.setText(nrOfImages + "/" + nrOfImages);
			
//			xcryptDirectories(arr.dir, null, new Decryption(), seed);
			
			if(countSuccess > 0){
				return 't';
			}
			return 'f';
		}
		catch(Exception e){
			System.out.println("FileNotFoundException cought");
			return runFailure(arr.newFiles, arr.img);
		}

		
	}
	
	private static void xcryptDirectories(ArrayList<String> dir, Encryption en, Decryption di, long seed) {
		for(int i = 0; i < dir.size(); i++) {
			File f = new File(dir.get(i));
			f.renameTo(new File(xcrypDirChooser(dir.get(i), en, di, seed)));
		}
	}
	
	private static String xcrypDirChooser(String path, Encryption en, Decryption de, long seed) {
		if(en != null) {
			return en.encryptName(path, seed);
		}
		return de.decryptName(path, seed);
	}
	
	/*
	 * Puts the hide property of a file to hidden
	 */
	private static void hideFile(String file) throws IOException {
		Path filePath = Paths.get(file);
		Files.setAttribute(filePath, "dos:hidden", true);
	}

	/*
	 * When canceling Xcryption newly created files are deleted and all hidden files are unhidden
	 */
	private static char runFailure(ArrayList<String> newImages, ArrayList<String> oldImages){
		deleteImages(newImages);
		unhideFiles(oldImages);
		return 'e';
	}
	
	/*
	 * Updates the progressBar and progressLabel on the main jFrame
	 */
	private static void updateJFrame(int i, int nrOfImages, JProgressBar progressBar, JLabel lblProsesslabel){
		double percentage = ((double)i+1)/(double)nrOfImages*100;
		
		//Check to make sure JFrame can't be closed before process is completely done
		if((int)percentage == 100){
			return;
		}
		progressBar.setValue((int)percentage);
		lblProsesslabel.setText((i+1) + "/" + nrOfImages);
	}

	
	/*
	 * When canceling Xcryption all files that have been hidden will be unhidden again
	 */
	private static void unhideFiles(ArrayList<String> arr){
		try {
			for(int i = 0; i < arr.size(); i++){
				Path file = Paths.get(arr.get(i).toString());
				Files.setAttribute(file, "dos:hidden", false);
			}
		} catch (IOException e) {
			System.out.println("Unable to unhide files");
			e.printStackTrace();
		}
	}
	
	
	/*
	 * Deletes all images in array arr
	 */
	private static void deleteImages(ArrayList<String> arr){
	    File deleteFile = null;
		for(int i = 0; i < arr.size(); i++){
		    deleteFile = new File(arr.get(i).toString()); // Delete file
		    if(deleteFile.exists()){
			    deleteFile.delete();
		    }
		}
	}
	
	
	/*
	 * Takes in path to folder and adds all .jpg, .jpeg and .png t array. 
	 * Returns that array
	 */
	private static FileArrays getFiles(String homeDirectory, FileArrays arr, boolean inclSubfolders){	
		System.out.println("Active tab: " + activeTab);
		if(activeTab.equals("File")){
			int fileIndex = homeDirectory.lastIndexOf("\\");
			homeDirectory = homeDirectory.substring(0, fileIndex) + "//" + homeDirectory.substring(fileIndex+1, homeDirectory.length());
			arr.img.add(homeDirectory);
			System.out.println("One file: " + arr.img.get(0));
			return arr;
		}
		String type = "";
		File folder = new File(homeDirectory);
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	  type = getFileExtension(listOfFiles[i].toString());
		    	  if(type.equals("png") || type.equals("PNG") || type.equals("jpg") || type.equals("JPG") ||type.equals("jpeg") || type.equals("JPEG")){
			    	  arr.img.add(homeDirectory + "//" + listOfFiles[i].getName());
		    	  }
		      } else if (listOfFiles[i].isDirectory()) {
		    	  if(inclSubfolders == true){
			    	  arr.dir.add(homeDirectory + "//" + listOfFiles[i].getName()); //adding subfolder to array
			    	  getFiles(homeDirectory + "//" + listOfFiles[i].getName(), arr, inclSubfolders);
		    	  }
		      }
		    }
		    return arr;
	}
	

	
	/*
	 * Write image to disk
	 */
	public static void writeImage(BufferedImage img, File f, String filePath) throws IOException{
	        f = new File(filePath);
	        ImageIO.write(img, "png", f);
	}
	
	/*
	 * Read file f as image and return that image
	 */
	public static BufferedImage readImage(File f, String filePath){
	    try{
	      f = new File(filePath);
	      return ImageIO.read(f);
	    }catch(IOException e){
	      System.out.println(e);
	      return null;
	    }
	}
	
	/*
	 * Returns filextension, .png, .jpg ....
	 */
	private static String getFileExtension(String fileName){
		return fileName.substring(fileName.toString().lastIndexOf('.')+1, fileName.toString().length());
	}

}
