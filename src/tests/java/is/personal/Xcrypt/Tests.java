package tests.java.is.personal.Xcrypt;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import main.java.is.personal.Xcrypt.dataStructures.Pixel;
import main.java.is.personal.Xcrypt.logic.Decryption;
import main.java.is.personal.Xcrypt.logic.Encryption;
import main.java.is.personal.Xcrypt.logic.FileHandeling;

public class Tests {
	
	private static Encryption En = new Encryption();
	private static Decryption De = new Decryption();
	

	public static void main(String[] args) throws FileNotFoundException, IOException{
		System.out.println("Starting tests");
		if(runTests() == true){
			System.out.println("All tests passed!");
		}
		else{
			System.out.println("Testing failed");
		}
		
	}
	
	public static boolean runTests() throws FileNotFoundException, IOException{
		if(testName() == false){
			System.err.println("testName failed");
			return false;
		}
		if(testLQimage() == false){
			System.err.println("testLQimage failed");
			return false;
		}
		if(testHQimage() == false){
			System.err.println("testHQimage failed");
			return false;
		}
		return true;
	}
	
	
	private static boolean testName() throws FileNotFoundException, IOException{
		System.out.println("Testing filenames");
		String[] words = {"MikKi Mú'UÚS", "ÖÐÞ'ÆæÆdÐP", "Stofa", "Mamma Mía", "Litla Herbergi", "Ýta", "DsdasDSDSD$C=)(&%", "Ö=Ö)(YGHCVGGVBNDKSD)87654534%&(D)JHNBC", 
							"kjhgyfds879a9d&()=mdnsua'''Þ,mh887667H", "154=)(ui8908UHNMJhGCFVbnjmkljbhvdgc'Þ+Þ'Æ;LM;Þþæ.,lmk,."};
		for(int i = 0; i < 1000; i++){
			for(int y = 0; y < words.length; y++){
				if(!chechNameAfterXcryption(words[y], i)){
					return false;
				}
			}

		}
		return true;
	}
	
	private static boolean testLQimage() throws FileNotFoundException, IOException{
		System.out.println("Testig LQ image");
		return chechImgAfterXcryption("img\\test\\HQ\\HQ1.png", 323123532);
	}
	
	private static boolean testHQimage() throws FileNotFoundException, IOException{
		System.out.println("Testing HQ image");
		return chechImgAfterXcryption("img\\test\\HQ\\HQ1.png", 323123532);
	}
	
	private static boolean chechNameAfterXcryption(String name, long seed){
		String newName = En.encryptName(name, seed);
		newName = De.decryptName(newName, seed);
		name = name.toLowerCase();
//		System.out.println(name + " : " + newName);
		return name.equals(newName);
	}
	private static boolean chechImgAfterXcryption(String originalPath, long seed) throws FileNotFoundException, IOException{
		
		//Encrypt original image and Decrypt it again.
		File f = null;
	    BufferedImage  originalImg = FileHandeling.readImage(f, originalPath);
	    BufferedImage testImg = FileHandeling.readImage(f, originalPath);

		testImg = En.encryptImage(testImg, seed);
		testImg = De.decryptImage(testImg, seed);
		
		return compareTwoImages(originalImg, testImg);
	}
	
	private static boolean compareTwoImages(BufferedImage original, BufferedImage test){
		if(original.getHeight() != test.getHeight() || original.getWidth() != test.getWidth()){
			return false;
		}
		
		for(int x = 0; x < test.getWidth(); x++){
			for(int y = 0; y < test.getHeight(); y++){
				Pixel oP = new Pixel(original.getRGB(x, y));
				Pixel tP = new Pixel(test.getRGB(x, y));
				
				if(oP.a != tP.a || oP.r != tP.r || oP.g != tP.g || oP.b != tP.b){
					System.out.println("Pixel not the same");
					return false;
				}
			}
		}
		return true;
	}

}
