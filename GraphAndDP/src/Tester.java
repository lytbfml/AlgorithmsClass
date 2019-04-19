import java.awt.Color;
import java.util.Random;

public class Tester {
	public static void main(String[] args) {
		Picture currPic;
		/*
		for(int i=1; i<=20; i++) {
			ImageProcessor ip=new ImageProcessor("images/"+i+".jpg");
			System.out.println("Image: "+i);
			currPic=ip.reduceWidth(.05*i);
			currPic.save("modified_images/"+i+"_modified.jpg");
		}
		*/
		Random Rand=new Random();
//		currPic=new Picture(2000,2000);
//		for(int i=200; i<currPic.width(); i++) {
//			for(int j=0; j<currPic.height(); j++) {
//				Color c=new Color(Rand.nextInt(256),Rand.nextInt(256),Rand.nextInt(256));
//				currPic.set(i, j, c);
//
//			}
//		}
//		currPic.save("images/21.jpg");
//		ImageProcessor ip=new ImageProcessor("images/21.jpg");
//		ip.reduceWidth(.90).save("modified_images/21_modified.jpg");
		
		
		currPic=new Picture(100,100);
		for(int i=0; i<currPic.width(); i++) {
			for(int j=0; j<currPic.height(); j++) {
				if(i >= 90)
				{
					currPic.set(i,j, new Color(255,255,255));
				}
				else
				{
					Color c=new Color(Rand.nextInt(256),Rand.nextInt(256),Rand.nextInt(256));
					currPic.set(i, j, c);
				}
			}
		}
		currPic.save("images/22.jpg");
		ImageProcessor ip=new ImageProcessor("images/22.jpg");
		ip.reduceWidth(.90).save("modified_images/22_modified.jpg");
		
	}
}