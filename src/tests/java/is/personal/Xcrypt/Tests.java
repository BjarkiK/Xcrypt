package tests.java.is.personal.Xcrypt;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import main.java.is.personal.Xcrypt.logic.Decryption;
import main.java.is.personal.Xcrypt.logic.Encryption;
import main.java.is.personal.Xcrypt.logic.FileHandeling;

public class Tests {
	public Tests(){
		
	}

	public static void main(String[] args) throws FileNotFoundException, IOException{
		if(runTests() == true){
			System.out.println("All tests passed!");
		}
		else{
			System.out.println("Testing failed");
		}
		
	}
	
	public static boolean runTests() throws FileNotFoundException, IOException{		
		if(testLQimage() == false){
			return false;
		}
		return true;
	}
	
	private static boolean testLQimage() throws FileNotFoundException, IOException{
		long seed = 254165111;
		String originalPath = "src\\tests\\images\\img\\LQ\\LQ1.png";
		String testPath = "src\\tests\\images\\test\\Test.png";

		Path src = Paths.get(originalPath);
		Path dst = Paths.get(testPath);
		copyTestImage(src, dst);	

		
		//Encrypt original image and Decrypt it again.
		File f = null;
	    BufferedImage  originalImg = FileHandeling.readImage(f, originalPath);
		Encryption En = new Encryption();
		BufferedImage testImg = En.encryptImage(originalImg, seed);
		FileHandeling.writeImage(testImg, f, testPath);
		
		testImg =  FileHandeling.readImage(f, testPath);
		Decryption De = new Decryption();
		testImg = De.decryptImage(testImg, seed);
		FileHandeling.writeImage(testImg, f, testPath);
		
		deleteFile("src\\tests\\images\\test");
		return true;
	}
	
	private boolean compareTwoImages(BufferedImage original, BufferedImage test){
		if(original.getHeight() != test.getHeight() || original.getWidth() != test.getWidth()){
			return false;
		}
		
		for(int i = 0; i < test.getHeight(); i++){
			for(int y = 0; y < test.getWidth(); y++){
				
			}
		}
		return true;
	}
	
	private static void copyTestImage(Path src, Path dst){

		deleteFile("src\\tests\\images\\test").mkdir(); //Make sure it doesn't exist and make dir.
		
		try {
			Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private static File deleteFile(String path){
		File f = new File(path); 
		if(f.exists()){
			File[] listOfFiles = f.listFiles();
			for(int i = 0; i < listOfFiles.length; i++){
				listOfFiles[i].delete();
			}
			f.delete();
		}
		return f;
	}

}
