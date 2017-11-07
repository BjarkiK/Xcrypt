package main.java.is.personal.Xcrypt.logic;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import main.java.is.personal.Xcrypt.dataStructures.Pixel;



public class Encryption {
	

	public Encryption() {
	}
	
	public BufferedImage encryptImage(BufferedImage img, long seed) throws FileNotFoundException, IOException{
	    Random rand = new Random(seed);
		for(int i = 0; i < img.getWidth(); i++){
	    	 for(int y = 0; y < img.getHeight(); y++){
	    		 
	    		 Pixel p = new Pixel(img.getRGB(i, y));
	    	
	    		 p.r = (p.r +  random(rand, 0, 256))%256;
	    		 p.g = (p.g + random(rand, 0, 256))%256;
	    		 p.b = (p.b + random(rand, 0, 256))%256;

	    		 img.setRGB(i, y, (p.a<<24) | (p.r<<16) | (p.g<<8) | p.b);
	    	 }
	    }
		 return img;
	}
	
	
	
	public String encryptName(String name, long seed){
		Random rand = new Random(seed);
		
		//Cut of file name
		int indexOfFile = getImgIndex(name);
		String path = name.substring(0, indexOfFile);
		name = name.substring(indexOfFile, name.length());//.toLowerCase();
		
		System.out.println(path + " AND " + name);
		
		String newName = "";
		int random = 0;
		int castInt = 0;
		int tmpCastInt = 0;
		int tmp2CastInt = 0;
		char castBackChar = ' ';
		for(int i = 0; i < name.length(); i++){

			random = random(rand, 32, 245);
			castInt = name.charAt(i);
			tmpCastInt = castInt;
			
			if(castInt == 255){castInt = 253;}// Til að breyta ÿ (255) í ý (253)
			
			
			
			System.out.print("Character: " + name.charAt(i) + " castInt: " + (int) name.charAt(i) + " Random: " + random);

			
			castInt = (castInt + random)%245;
			if(castInt < 32){
				castInt = castInt + 32;
			}
			
			tmp2CastInt = castInt;
			if(castInt == 92){castInt = 255;} //Breyta bannstöfum í ÿ (255)
			else if(castInt == 47){castInt = 255;}
			else if(castInt == 58){castInt = 255;}
			else if(castInt == 42){castInt = 255;}
			else if(castInt == 63){castInt = 255;}
			else if(castInt == 34){castInt = 255;}
			else if(castInt == 62){castInt = 255;}
			else if(castInt == 60){castInt = 255;}
			else if(castInt == 124){castInt = 255;}
			else if(castInt == 127){castInt = 255;}
			else if(castInt == 160){castInt = 255;}
			

			castBackChar = (char) castInt;
			System.out.print(" CastInt: " + castInt + " castBackChar: " + castBackChar);
			newName = newName + castBackChar;
			
			if(castInt != tmp2CastInt){ //Ef final castið endar í 255
				tmpCastInt = tmpCastInt + 6;
				castBackChar = (char) tmpCastInt; //Færa bannstaf 6 sæti aftur í ascii töfluni
				System.out.print(" SHIFT -> CastInt: " + tmpCastInt + " castBackChar: " + castBackChar);
				newName = newName + castBackChar;
			}
			System.out.println("");
		}
		return path + newName;
	}
	
	public String XencryptName(String name, long seed){
		Random rand = new Random(seed);
		
		//Cut of file name
		int indexOfFile = getImgIndex(name);
		String path = name.substring(0, indexOfFile);
		name = name.substring(indexOfFile, name.length());//.toLowerCase();
		
		System.out.println(path + " AND " + name);
		
		String newName = "";
		int random = 0;
		int castInt = 0;
		char castBackChar = ' ';
		for(int i = 0; i < name.length(); i++){

			random = random(rand, 32, 245);
			castInt = name.charAt(i);
			
			if(castInt == 246){castInt = 214;} // Til að breyta öftustu ASCII
			else if(castInt == 247){castInt = 47;}
			else if(castInt == 248){castInt = 216;}
			else if(castInt == 249){castInt = 217;}
			else if(castInt == 250){castInt = 218;}
			else if(castInt == 251){castInt = 219;}
			else if(castInt == 252){castInt = 220;}
			else if(castInt == 253){castInt = 221;}
			else if(castInt == 254){castInt = 222;}
			else if(castInt == 255){castInt = 221;}
			else if(castInt == 127){castInt = 124;}
			
			System.out.print("Character: " + name.charAt(i) + " castInt: " + (int) name.charAt(i) + " Random: " + random);
//			castInt = (castInt + random)%245 + 32;
//			if(castInt > 245){
//				castInt = castInt - 32;
//			}
			
			castInt = (castInt + random)%245;
			if(castInt < 32){
				castInt = castInt + 32;
			}
			
			if(castInt == 92){castInt = 246;} //Breyta bannstöfum í öftustu ASCII
			else if(castInt == 47){castInt = 247;}
			else if(castInt == 58){castInt = 248;}
			else if(castInt == 42){castInt = 248;}
			else if(castInt == 63){castInt = 250;}
			else if(castInt == 34){castInt = 251;}
			else if(castInt == 62){castInt = 252;}
			else if(castInt == 60){castInt = 253;}
			else if(castInt == 124){castInt = 254;}
			else if(castInt == 160){castInt = 255;}
			

			castBackChar = (char) castInt;
			System.out.println(" CastInt: " + castInt + " castBackChar: " + castBackChar);
			newName = newName + castBackChar;
		}
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
