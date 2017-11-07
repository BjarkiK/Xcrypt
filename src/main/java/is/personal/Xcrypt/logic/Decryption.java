package main.java.is.personal.Xcrypt.logic;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import main.java.is.personal.Xcrypt.dataStructures.Pixel;

public class Decryption {
	

	public Decryption() {
	}
	
	
	public BufferedImage decryptImage(BufferedImage img, long seed) throws FileNotFoundException, IOException{
	    Random rand = new Random(seed);
	    int rand1 = 0;
	    int rand2 = 0;
	    int rand3 = 0;
		for(int i = 0; i < img.getWidth(); i++){
	    	 for(int y = 0; y < img.getHeight(); y++){
	    		 
	    		  
	    		 Pixel p = new Pixel(img.getRGB(i, y));
	    		 
	    		 rand1 = random(rand, 0, 256);
	    		 rand2 = random(rand, 0, 256);
	    		 rand3 = random(rand, 0, 256);
	    		 p.r = (p.r - rand1);
	    		 p.g = (p.g - rand2);
	    		 p.b = (p.b - rand3);
	    		 
	    		 if(p.r < 0){
	    			 p.r = 256 + p.r;
	    		 }
	    		 if(p.g < 0){
	    			 p.g = 256 + p.g;
	    		 }
	    		 if(p.b < 0){
	    			 p.b = 256 + p.b;
	    		 }   	

	    		 img.setRGB(i, y, (p.a<<24) | (p.r<<16) | (p.g<<8) | p.b);
	    	 }
	    }
		 return img;
	}
	

	public String decryptName(String name, long seed){
		Random rand = new Random(seed);
		
		int indexOfFile = getImgIndex(name);
		String path = name.substring(0, indexOfFile);
		name = name.substring(indexOfFile, name.length());
		
//		System.out.println(path + " AND " + name);
		
		String newName = "";
		
		int castInt = 0;
		int tmpCastInt = 0;
		int random = 0;
		char castBackChar = ' ';
		for(int i = 0; i < name.length(); i++){

			random = random(rand, 32, 254);
			castInt = name.charAt(i);
			tmpCastInt = castInt;
			
//			System.out.print("Character: " + name.charAt(i) + " castInt: " + (int) name.charAt(i) + " Random: " + random);
		
//			boolean mod = false;
//			boolean pluss = false;
			
			if(castInt - random < 32){ //Ef modað var með 254 í encrypt
				castInt = castInt - random + 254 - 32;
//				mod = true;
				if(castInt < 32){
//					pluss = true;
					castInt = castInt + 32;
				}
			}
			else{
				castInt = castInt - random;
			}

			
			if(tmpCastInt == 255){
				i++;
				castInt = name.charAt(i) - 6;
			}

			castBackChar = (char) castInt;
//			System.out.print(" CastInt: " + castInt + " castBackChar: " + castBackChar);
//			if(mod){
//				System.out.print(" -> MOD");
//			}
//			
//			if(pluss){
//				System.out.print(" -> PLUSS");
//			}
			newName = newName + castBackChar;
//			System.out.println("");
		}
		
		newName = newName.toLowerCase();
		return path + newName;
	}
	
	
	
	private static int getImgIndex(String name){
		if(name.contains("//")){
			return name.lastIndexOf("//") + 2;
		}
		return 0;
	}
	
	private static int random(Random rand, int low, int high) {
		return rand.nextInt(high-low) + low;
	}

}
