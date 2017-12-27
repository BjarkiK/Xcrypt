package main.java.is.personal.Xcrypt.logic;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import main.java.is.personal.Xcrypt.dataStructures.Pixel;

public class Encryption {
	
	/*
	 * WARNING! Any alpha value will be set to 0;
	 */
	public BufferedImage encryptImage(BufferedImage img, long seed) throws FileNotFoundException, IOException{
	    Random rand = new Random(seed);	    
	    
		for(int i = 0; i < img.getWidth(); i++){
	    	 for(int y = 0; y < img.getHeight(); y++){
	    		 
	    		 Pixel p = new Pixel(img.getRGB(i, y));
	    	
	    		 p.r = (p.r +  random(rand, 0, 256))%256;
	    		 p.g = (p.g + random(rand, 0, 256))%256;
	    		 p.b = (p.b + random(rand, 0, 256))%256;
	    		 p.a = 255;

	    		 img.setRGB(i, y, (p.a<<24) | (p.r<<16) | (p.g<<8) | p.b);
	    	 }
	    }
		 return watermarkDecriptedFile(img);
	}
	
	
	public boolean isAlreadyEncrypted(BufferedImage img){
		return checkIfAlreadyEncrypted(img);
	}
	private boolean checkIfAlreadyEncrypted(BufferedImage img){
		int height = img.getHeight();
		int width = img.getWidth();
		Pixel p1 = new Pixel(img.getRGB(0, 0));
		Pixel p2 = new Pixel(img.getRGB(width-1, 0));
		Pixel p3 = new Pixel(img.getRGB(0, height-1));
		Pixel p4 = new Pixel(img.getRGB(width-1, height-1));
		Pixel p5 = new Pixel(img.getRGB((width-1)/2, (height-1)/2));
		if(p1.a == 45 || p2.a == 68 || p3.a == 21 || p4.a == 137 || p5.a == 211){
			return true;
		}
		return false;
	}
	
	private BufferedImage watermarkDecriptedFile(BufferedImage img){
		int height = img.getHeight();
		int width = img.getWidth();
		Pixel p1 = new Pixel(img.getRGB(0, 0));
		Pixel p2 = new Pixel(img.getRGB(width-1, 0));
		Pixel p3 = new Pixel(img.getRGB(0, height-1));
		Pixel p4 = new Pixel(img.getRGB(width-1, height-1));
		Pixel p5 = new Pixel(img.getRGB((width-1)/2, (height-1)/2));
		
		p1.a = 45;
		p2.a = 68;
		p3.a = 21;
		p4.a = 137;
		p5.a = 211;

		img.setRGB(0, 0, (p1.a<<24) | (p1.r<<16) | (p1.g<<8) | p1.b);
		img.setRGB(width-1, 0, (p2.a<<24) | (p2.r<<16) | (p2.g<<8) | p2.b);
		img.setRGB(0, height-1, (p3.a<<24) | (p3.r<<16) | (p3.g<<8) | p3.b);
		img.setRGB(width-1, height-1, (p4.a<<24) | (p4.r<<16) | (p4.g<<8) | p4.b);
		img.setRGB((width-1)/2, (height-1)/2, (p5.a<<24) | (p5.r<<16) | (p5.g<<8) | p5.b);
		
		return img;
	}
	
	
	
	public String encryptName(String name, long seed){
		Random rand = new Random(seed); //New random with seed made from username and password
		
		//Cut of file name
		int indexOfFile = getImgIndex(name);
		String path = name.substring(0, indexOfFile);
		name = name.substring(indexOfFile, name.length()).toLowerCase();
		
//		System.out.println(path + " AND " + name);
		
		String newName = "";
		int random = 0;
		int castInt = 0;
		int tmpCastInt = 0;
		char castBackChar = ' ';
		for(int i = 0; i < name.length(); i++){

			random = random(rand, 32, 254);
			castInt = name.charAt(i);
			
			if(castInt == 255){castInt = 253;}// Til a� breyta � (255) � � (253)
			else if(castInt == 180){castInt = 39;} //Breyta �(180) � '(39)
			else if(castInt == 96){castInt = 39;} //Breyta `(96) � '(39)
			
			
			
			System.out.print("Character: " + name.charAt(i) + " castInt: " + (int) name.charAt(i) + " Random: " + random);

			
			castInt = (castInt + random)%254;
//			boolean mod = false;
//			boolean pluss = false;
//			castInt = castInt + random;
//			if(castInt >= 254){
//				castInt = castInt - 254;
//				mod = true;
//			}
			
			if(castInt < 32){
				castInt = castInt + 32;
//				pluss = true;
			}
			
			tmpCastInt = castInt;
			if(castInt == 34){castInt = 255;}//Breyta bannst�fum � � (255)
			else if(castInt == 42){castInt = 255;}
			else if(castInt == 47){castInt = 255;}
			else if(castInt == 58){castInt = 255;}
			else if(castInt == 60){castInt = 255;}
			else if(castInt == 62){castInt = 255;}
			else if(castInt == 63){castInt = 255;}
			else if(castInt == 92){castInt = 255;} 
			else if(castInt == 124){castInt = 255;}
			else if(castInt == 127){castInt = 255;}
			else if(castInt == 160){castInt = 255;}
			else if(castInt == 180){castInt = 255;}
			
			
//			if(mod){
//				System.out.print(" -> MOD");
//			}
//			if(pluss){
//				System.out.print(" -> PLUSS");
//			}
			
			/*
			 * Ef castInt != tmp2CastInt �� er castInt = 255 e�a �
			 * Ef svo er �� er tmp2CastInt (upprunalegi castInt + random) + 6 og � eftir honum kemur �� �.
			 * �egar �a� er afk��a� og �a� kemur � �� er hann hundsa�ur og dregi� 6 fr� stafnum � undan og haldi� �fram venjulega.
			 */
			if(castInt != tmpCastInt){ //Ef final castInt endar � 255
				tmpCastInt = tmpCastInt + 6;
				castBackChar = (char) tmpCastInt; //F�ra bannstaf 6 s�ti fram � ascii t�fluni
//				System.out.print(" SHIFT -> CastInt: " + tmpCastInt + " castBackChar: " + castBackChar);
				newName = newName + castBackChar;
				newName = newName + (char) 255;
			}
			else{
				castBackChar = (char) castInt;
				newName = newName + castBackChar;
			}
			System.out.print(" CastInt: " + castInt + " castBackChar: " + castBackChar);
			System.out.println("");
		}
		System.out.println("NewName: " + newName);
		System.out.println("Path + newName: " + path + newName);
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
