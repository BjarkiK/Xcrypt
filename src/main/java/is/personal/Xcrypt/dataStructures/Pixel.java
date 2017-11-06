package main.java.is.personal.Xcrypt.dataStructures;

public class Pixel {
    public int a;
    public int r;
    public int g;
    public int b;

	public Pixel(int pixel) {
		 a = (pixel >> 24) & 0xff;
		 r = (pixel >> 16) & 0xff;
		 g = (pixel >> 8) & 0xff;
		 b = (pixel) & 0xff;
	}

}
