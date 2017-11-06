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
		
		int indexOfFile = getPath(name);
		String path = name.substring(0, indexOfFile+2);
		name = name.substring(indexOfFile+2, name.length());
		
		System.out.println(path + " AND " + name);
		
		String newName = "";
		
		int castInt = 0;
		int random = 0;
		char castBackChar = ' ';
		for(int i = 0; i < name.length(); i++){

			random = random(rand, 32, 245);
			castInt = name.charAt(i);
			if(castInt == 246){castInt = 92;}
			else if(castInt == 247){castInt = 47;}
			else if(castInt == 248){castInt = 58;}
			else if(castInt == 249){castInt = 42;}
			else if(castInt == 250){castInt = 63;}
			else if(castInt == 251){castInt = 34;}
			else if(castInt == 252){castInt = 62;}
			else if(castInt == 253){castInt = 60;}
			else if(castInt == 254){castInt = 124;}
			else if(castInt == 255){castInt = 160;}
			
			System.out.print("Character: " + name.charAt(i) + " castInt: " + (int) name.charAt(i) + " Random: " + random);
			
			if(castInt - random < 32){ //Ef modað var með 245 í encrypt
				castInt = castInt - random + 245 - 32;
				if(castInt < 32){
					castInt = castInt + 32;
				}
			}
			else{
				castInt = castInt - random;
			}

			castBackChar = (char) castInt;
			System.out.println(" CastInt: " + castInt + " castBackChar: " + castBackChar);
			newName = newName + castBackChar;
		}
		
		return path + newName;
	}
	
	
	
	private static int getPath(String name){
		return name.lastIndexOf("//");

	}
	
	private static int random(Random rand, int low, int high) {
		return rand.nextInt(high-low) + low;
	}

}
