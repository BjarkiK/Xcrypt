package tests.java.is.personal.Xcrypt;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

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
		bruteForceSeed();
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
							"kjhgyfds879a9d&()=mdnsua'''Þ,mh887667H", "154=)(ui8908UHNMJhGCFVbnjmkljbhvdgc'Þ+Þ'Æ;LM;Þþæ.,lmk,.",
							"when the", "execution", "of PL on D", "by M", "terminates,", "and", "unde", "ned,", "when",
							"it", "does", "not", "terminate.", "þþþþþþþþþþþþþþþþþþþþþþþþþþþþþþþþþ", "!!!!!!!!!!!!!!!!!!!!!"};
		for(int i = 0; i < 5000; i++){
			for(int y = 0; y < words.length; y++){
//				System.out.println(i + ":" + y);
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
		return name.equals(newName);
	}
	private static boolean chechImgAfterXcryption(String originalPath, long seed) throws FileNotFoundException, IOException{
		
		//Encrypt original image and Decrypt it again.
		File f = null;
	    BufferedImage  originalImg = FileHandeling.readImage(f, originalPath);
	    BufferedImage testImg = FileHandeling.readImage(f, originalPath);

		long startTime = new Date().getTime();
		testImg = En.encryptImage(testImg, seed);
		long endTime = new Date().getTime();
		System.out.println("Encryption time: " + (endTime - startTime) + " ms"); 
		startTime = new Date().getTime();
		testImg = De.decryptImage(testImg, seed);
		endTime = new Date().getTime();
		System.out.println("Decryption time: " + (endTime - startTime) + " ms"); 
		
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
	
	
	
	/*
	 * 1.000.000 lookups takes 1,019s so 2147483647 lookups would 
	 * take approximately 21882s or approximately 6 hours. 
	 * That is way too fast!
	 * How someone could use this to quickly find username and password
	 * 1. Find any target file they want to decrtypt by brute force
	 * 2. Copy target file name
	 * 3. Create very small image file and encrypt with any username and password. 
	 * 4. Rename the newly decrypted small image with the name of target file.
	 * 5. Input username and password so they create increasing seed
	 * *The one trying to brute force the seed probably would have to know how the program is implemented.
	 * 
	 * What to do about this.
	 * Not use the same seed for name and file xcryption
	 * How?
	 * 1. Use only password/username as seed for name (Scramble somehow)
	 * 	- By doing this, even though the file name is decrypted successfully
	 * 	  the seed for image decryption is (most likely) far from being correct.
	 */
	private static void bruteForceSeed(){
		long startTime = new Date().getTime();
		String stringToFind = "TheInput";
		String encryptedString = En.encryptName(stringToFind, random());
		for(long seed = 0; seed < 2147483647; seed++) {
			if(seed == 1000000) {
				System.out.println("BOOM");
				long endTime = new Date().getTime();
				System.out.println("It took " + (endTime - startTime) + " ms to " + seed);
			}
			if(De.decryptName(encryptedString, seed).equals(stringToFind)) {
				long endTime = new Date().getTime();
				System.out.println("It took " + (endTime - startTime) + " ms to find the seed: " + seed);
				return;
			}
		}
		long endTime = new Date().getTime();
		System.out.println("It took " + (endTime - startTime) + " ms");
		
	}
	
	private static int random() {
		int low = 1;
		int high = 2147483647;
		Random rand = new Random(55);
		return rand.nextInt(high-low) + low;
	}

}
