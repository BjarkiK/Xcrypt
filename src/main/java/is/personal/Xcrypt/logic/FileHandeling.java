package main.java.is.personal.Xcrypt.logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;


class FileArrays{
	ArrayList<String> img = new ArrayList<String>();
	ArrayList<String> dir = new ArrayList<String>();
}

public class FileHandeling {

	private static long seed;
	private static String path;
	private static int WhatToDo;
	private static int Encrypt = 0;
	private static int Decrypt = 1;

	public FileHandeling(String Ipassword, String Iusername, String Ipath, int Iaction){
		seed = calcSeed(Ipassword, Iusername);
		path = Ipath;
		WhatToDo = Iaction;
	}
	
	public FileHandeling(){

	}
	
	public boolean run(){
		try {
			return runProgram();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static boolean runProgram() throws FileNotFoundException, IOException {
		FileArrays arr = getFiles(path, new FileArrays());
		ArrayList<String> newImages = new ArrayList<String>();

		int nrOfImages = arr.img.size();
		if(nrOfImages == 0){
			System.out.println("No images found");
			return false;
		}
		
		String nextFile = "";
		String inputFile = "";
		String outputFile = "";
	    File f = null;
	    BufferedImage img = null;
	    
		try{
			System.out.println("Start ");
			for(int i = 0; i < arr.img.size(); i++){
				nextFile = arr.img.get(i);
				System.out.println("Currend file: " + nextFile);
				
				inputFile = nextFile;
				
				img = readImage(f, inputFile);
				
				if(WhatToDo == Encrypt){
					Encryption encrypt = new Encryption();
					img = encrypt.encryptImage(img, seed);
					outputFile = encrypt.encryptName(nextFile.substring(0, nextFile.lastIndexOf('.')), seed) + ".png";
				}
				else if(WhatToDo == Decrypt){
					Decryption encrypt = new Decryption();
					img = encrypt.decryptImage(img, seed);
					outputFile = encrypt.decryptName(nextFile.substring(0, nextFile.lastIndexOf('.')), seed) + ".png";
				}
				
				newImages.add(outputFile);
				System.out.println("Outputing: " + outputFile);
				System.out.println("");
	
				try{
				    writeImage(img , f, outputFile);
				}
				catch (Exception e){
					throw new FileNotFoundException();
				}
				
				Path file = Paths.get(nextFile);
				Files.setAttribute(file, "dos:hidden", true);
			}
			
			deleteImages(arr.img);
			return true;
		}
		catch(Exception e){
			return runFailure(newImages, arr.img);
		}

		
	}
	
	private static boolean runFailure(ArrayList<String> newImages, ArrayList<String> oldImages){
		deleteImages(newImages);
		unhideFiles(oldImages);
		return false;
	}

	
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
	
	private static void deleteImages(ArrayList<String> arr){
	    File deleteFile = null;
		for(int i = 0; i < arr.size(); i++){
		    deleteFile = new File(arr.get(i).toString()); // Delete file
		    deleteFile.delete();
		}
	}
	
	
	private static FileArrays getFiles(String homeDirectory, FileArrays arr){	
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
		    	  arr.dir.add(homeDirectory + "//" + listOfFiles[i].getName());
		    	  getFiles(homeDirectory + "//" + listOfFiles[i].getName(), arr);
		      }
		    }
		    return arr;
	}
	
	
	private static long calcSeed(String password, String username){
		long seed = 0;
		
		//Chech witch word has more characters
		int longerW = username.length();
		if(password.length() > longerW){
			longerW = password.length();
		}
		
		
		//Shuffle the words together p0+u0+p1+u1+p2+u2+p3+u3+p0+u4+p1+u5 t.d.
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
	
	public static void writeImage(BufferedImage img, File f, String filePath){
		try{
	        f = new File(filePath);
	        ImageIO.write(img, "png", f);
	      }catch(IOException e){
	        System.out.println(e);
	      }
	}
	
	public static BufferedImage readImage(File f, String filePath){
	    try{
	      f = new File(filePath);
	      return ImageIO.read(f);
	    }catch(IOException e){
	      System.out.println(e);
	      return null;
	    }
	}
	
	private static String getFileExtension(String fileName){
		return fileName.substring(fileName.toString().lastIndexOf('.')+1, fileName.toString().length());
	}

}
