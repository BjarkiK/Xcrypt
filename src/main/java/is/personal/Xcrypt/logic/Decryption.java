package main.java.is.personal.Xcrypt.logic;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import main.java.is.personal.Xcrypt.dataStructures.Pixel;

public class Decryption {
	

	public Decryption() {
	}
	
	
	/*
	 * Encrypted image is decrypted.
	 */
	public BufferedImage decryptImage(BufferedImage img, long seed) throws FileNotFoundException, IOException{
		
	    Random rand = new Random(seed);
	    int rand1 = 0;
	    int rand2 = 0;
	    int rand3 = 0;
		for(int x = 0; x < img.getWidth(); x++){
	    	 for(int y = 0; y < img.getHeight(); y++){
	    		 
	    		  
	    		 Pixel p = new Pixel(img.getRGB(x, y));
	    		 
	    		 rand1 = random(rand, 0, 256);
	    		 rand2 = random(rand, 0, 256);
	    		 rand3 = random(rand, 0, 256);
	    		 p.r = (p.r - rand1);
	    		 p.g = (p.g - rand2);
	    		 p.b = (p.b - rand3);
	    		 p.a = 255;
	    		 
	    		 if(p.r < 0){
	    			 p.r = 256 + p.r;
	    		 }
	    		 if(p.g < 0){
	    			 p.g = 256 + p.g;
	    		 }
	    		 if(p.b < 0){
	    			 p.b = 256 + p.b;
	    		 }   	

	    		 img.setRGB(x, y, (p.a<<24) | (p.r<<16) | (p.g<<8) | p.b);
	    	 }
	    }
		 return img;
	}
	
	/*
	 * public function for checkIfEncrypted(BufferedImage)
	 */
	public boolean isEncrypted(BufferedImage img){
		return checkIfEncrypted(img);
	}
	
	/*
	 * Checks if all corner pixels and center pixel has set alpha value.
	 * If so then file is most likely encrypted
	 * Returns true if encrypted, false otherwise
	 */
	private boolean checkIfEncrypted(BufferedImage img){
		int height = img.getHeight();
		int width = img.getWidth();
		Pixel p1 = new Pixel(img.getRGB(0, 0));
		Pixel p2 = new Pixel(img.getRGB(width-1, 0));
		Pixel p3 = new Pixel(img.getRGB(0, height-1));
		Pixel p4 = new Pixel(img.getRGB(width-1, height-1));
		Pixel p5 = new Pixel(img.getRGB((width-1)/2, (height-1)/2));
		if(p1.a == 45 && p2.a == 68 && p3.a == 21 && p4.a == 137 && p5.a == 211){
			return true;
		}
		return false;
	}
	

	/*
	 * Decrypt already encrypted file name
	 */
	public String decryptName(String name, long seed){
		Random rand = new Random(seed);
		int indexOfFile = getImgIndex(name);
		String path = name.substring(0, indexOfFile);
		name = name.substring(indexOfFile, name.length());
		
		String newName = "";
		int castInt = 0;
		int random = 0;
		char castBackChar = ' ';
		String returnHandler = "";
		
		for(int i = 0; i < name.length(); i++){
			
			random = random(rand, 32, 254);
						
			returnHandler = fixChar(castInt, name, i);
			castInt = returnHandler.charAt(0);
			
			i = Integer.parseInt(returnHandler.substring(1, returnHandler.length()));
			
			castInt = castInt - random;
			if(castInt < 32) {
				castInt = castInt + 254;
			}
			
//			System.out.print("Decryption -> Character: " + name.charAt(i) + " castInt: " + (int) name.charAt(i) + " Random: " + random + " castChar: " + (char)castInt + " castInt: " + castInt);
//			System.out.println("");
			castBackChar = (char) castInt;

			newName = newName + castBackChar;
		}
		
		newName = newName.toLowerCase();
		return path + newName;
	}
	
	/*
	 *Fixes nextCharInt if charInt = 255
	 *if nextCharInt = 255 then nextNextCharInt index shifts -32 - 6
	 *if nextCharInt is legal index is shift -32
	 *else nextCharInt index shift -6
	 */
	private String fixChar(int castInt, String name, int index) {
		castInt = name.charAt(index);
		if(castInt == 255) {
			index++;
			int nextCharInt = name.charAt(index);
			if(nextCharInt == 255) {
				index++;
				if(name.charAt(index) == 255) {
					index++;
					castInt = name.charAt(index) - 32 - 6;
				}
				else {
					castInt = name.charAt(index) - 32;
				}

			}
			else {
				castInt = name.charAt(index) - 6;
			}
		}
		
		return (char) castInt + "" + index;
	}

	
	/*
	 * Returns the index of the start of file name
	 */
	private static int getImgIndex(String name){
		if(name.contains("//")){
			return name.lastIndexOf("//") + 2;
		}
		return 0;
	}
	
	/*
	 * Generate random number from low to high with seed made from
	 * username and password
	 */
	private static int random(Random rand, int low, int high) {
		return rand.nextInt(high-low) + low;
	}

}
