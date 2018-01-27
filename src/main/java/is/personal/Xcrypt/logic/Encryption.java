package main.java.is.personal.Xcrypt.logic;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import main.java.is.personal.Xcrypt.dataStructures.DecryptReturn;
import main.java.is.personal.Xcrypt.dataStructures.Pixel;

public class Encryption {
	
	
	/*
	 * WARNING! Any alpha value will be set to 255;
	 * If watermarking is unsuccessful image encryption is aborted!
	 * 
	 */
	public DecryptReturn encryptImage(BufferedImage img, long seed) throws FileNotFoundException, IOException{
	    img = convertImagetoARGB(img);
	    
	    Random rand = new Random(seed);	    
		for(int x = 0; x <  img.getWidth(); x++){
	    	 for(int y = 0; y < img.getHeight(); y++){
	    		 
	    		 Pixel p = new Pixel(img.getRGB(x, y));
	    	
	    		 p.r = (p.r +  random(rand, 0, 256))%256;
	    		 p.g = (p.g + random(rand, 0, 256))%256;
	    		 p.b = (p.b + random(rand, 0, 256))%256;
	    		 p.a = 255;

	    		 img.setRGB(x, y, (p.a<<24) | (p.r<<16) | (p.g<<8) | p.b);
	    	 }
	    }
		return watermarkDecriptedFile(img);
	}
	
	
	//Public function for checkIfAlreadyEncrypted(BufferedImage)
	public boolean isAlreadyEncrypted(BufferedImage img){
		return alreadyWatermarked(img);
	}

	/*
	 * Checks every corner pixel and center pixel if correct alpha 
	 * value is set. If the correct alpha value was set in all cases this image 
	 * is most likely already encrypted
	 */
	private boolean alreadyWatermarked(BufferedImage img){
		int height = img.getHeight();
		int width = img.getWidth();

		Pixel p1 = new Pixel(img.getRGB(0, 0));
		Pixel p2 = new Pixel(img.getRGB(width-1, 0));
		Pixel p3 = new Pixel(img.getRGB(0, height-1));
		Pixel p4 = new Pixel(img.getRGB(width-1, height-1));
		Pixel p5 = new Pixel(img.getRGB((width-1)/2, (height-1)/2));
//		System.out.println("p1: " + p1.a + " p2: " + p2.a +  " p3: " + p3.a +  " p4: " + p4.a +  " p5: " + p5.a);
		if(p1.a == 45 || p2.a == 68 || p3.a == 21 || p4.a == 137 || p5.a == 211){
			return true;
		}
		return false;
	}
	
	
	/*
	 * Set all corner pixels and the center pixel to set alpha value. 
	 * When set it is possible to check if image is already encrypted
	 */
	private DecryptReturn watermarkDecriptedFile(BufferedImage img){
		int height = img.getHeight();
		int width = img.getWidth();
	    BufferedImage tmpImg = img;//convertImageFromRGBtoARGB(img);
	    
		Pixel p1 = new Pixel(tmpImg.getRGB(0, 0));
		Pixel p2 = new Pixel(tmpImg.getRGB(width-1, 0));
		Pixel p3 = new Pixel(tmpImg.getRGB(0, height-1));
		Pixel p4 = new Pixel(tmpImg.getRGB(width-1, height-1));
		Pixel p5 = new Pixel(tmpImg.getRGB((width-1)/2, (height-1)/2));
		
		p1.a = 45;
		p2.a = 68;
		p3.a = 21;
		p4.a = 137;
		p5.a = 211;
		
		tmpImg.setRGB(0, 0, (p1.a<<24) | (p1.r<<16) | (p1.g<<8) | p1.b);
		tmpImg.setRGB(width-1, 0, (p2.a<<24) | (p2.r<<16) | (p2.g<<8) | p2.b);
		tmpImg.setRGB(0, height-1, (p3.a<<24) | (p3.r<<16) | (p3.g<<8) | p3.b);
		tmpImg.setRGB(width-1, height-1, (p4.a<<24) | (p4.r<<16) | (p4.g<<8) | p4.b);
		tmpImg.setRGB((width-1)/2, (height-1)/2, (p5.a<<24) | (p5.r<<16) | (p5.g<<8) | p5.b);
		
//		System.out.println("p1: " + p1.a + " p2: " + p2.a +  " p3: " + p3.a +  " p4: " + p4.a +  " p5: " + p5.a);
		
		if(alreadyWatermarked(tmpImg) == false) {
			System.out.println("WARNING! ImageWatermarking not successfull");
			return new DecryptReturn(img, false);
		}
		
		return new DecryptReturn(tmpImg, true);
	}

	
	public String encryptName(String name, long seed){
		Random rand = new Random(seed); //New random with seed made from username and password
		
		//Cut of file name
		int indexOfFile = getImgIndex(name);
		String path = name.substring(0, indexOfFile);
		name = name.substring(indexOfFile, name.length()).toLowerCase();
		
		String newName = "";
		int random = 0;
		int castInt = 0;
		for(int i = 0; i < name.length(); i++){

			random = random(rand, 32, 254);
			castInt = name.charAt(i);
			
			if(castInt == 255){castInt = 253;}		//Change ÿ (255) to ý (253)
			
			castInt = castInt + random ;
			if(castInt > 254) {
				castInt = castInt - 254;
			}

			
			if(castInt < 32){
				castInt = castInt + 32;
				newName = newName + (char) 255;
				newName = newName + (char) 255;
			}
			
//			System.out.println("Excryption -> Character: " + name.charAt(i) + " castInt: " + (int) name.charAt(i) + " Random: " + random + " castChar: " + (char)castInt + " castInt: " + castInt);
			
			newName = newName + handleIllegalChar(castInt);

		}
		return path + newName;
	}
	
	/*
	 * If BufferedImgage is not TYPE_INT_ARGB then convert it to that type
	 * Returns BufferedImage of type = TYPE_INT_ARGB
	 */
	private static BufferedImage convertImagetoARGB(BufferedImage img) {
		final int TYPE_INT_ARGB = 2;
		BufferedImage newImage = img;
	    if(img.getType() != TYPE_INT_ARGB){
			newImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
	    	newImage.getGraphics().drawImage(img, 0, 0, null);
	    }

    	return newImage;
	}

	
	/*
	 * If character is illegal (Can not be in filename [Windows])
	 * then push it 6 up on the ascii table and add ÿ (255) in front
	 * of it to mark it for decryption.
	 */
	private String handleIllegalChar(int charInt) {
		String rtnString = "";
		char character = (char) charInt;
		if(checkIfCharIsLegal(character) == false) {
			char panChar = (char) ((int) character + 6);
			rtnString = (char)255 + "" + panChar;
		}
		else {
			rtnString = character + "";
		}
		return rtnString;
	}
	
	/*
	 * Checks if character is allowed in file name (Windows)
	 * If not then return false, otherwise true
	 */
	private boolean checkIfCharIsLegal(char character) {
		int[] illegalChars = {34, 42, 47, 58, 60, 62, 63, 92, 124, 127, 160, 180};
		for(int i = 0; i < illegalChars.length; i++) {
			if(character == illegalChars[i]) {
				return false;
			}
		}
		return true;
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
