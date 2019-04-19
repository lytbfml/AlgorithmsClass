import java.awt.Color;
import java.util.ArrayList;

/**
 * @author Cheng Song, Yangxiao Wang
 */
public class ImageProcessor {
	
	Picture picture;
	int W, H;
	Color[][] OriginalPic;
	
	/**
	 * W to denote the width and H to denote the height of the
	 * input image. Generate a color matrix of the input image.
	 * Each color variable has a R, G, B value inside of it.
	 *
	 * @param imageFile A string holds the name the name of the image that will be manipulated
	 */
	public ImageProcessor(String imageFile) {
		picture = new Picture(imageFile);
		W = picture.width();
		H = picture.height();
		
		OriginalPic = new Color[H][W];
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				//Pixel[x; y] refers to the pixel in xth column and yth row
				OriginalPic[i][j] = picture.get(j, i);
			}
		}
	}
	
	/**
	 * Use the static method minCostVC to compute the
	 * vertical cut. Generate a image with a width equals to x times W. Therefore, the reduced time
	 * equals to W times(1-x).
	 * For each reduce time, the minCost generate a minCost ArrayList contains the minCost path of
	 * the image.
	 * For each reduce time, the color matrix has been reduced 1 pixel as the minCost path from each
	 * row of the matrix.
	 * In the end, the reduced image has been generated.
	 *
	 * @param x A double variable will determine the width of output picture equals to [x × W].
	 * @return Picture whose width is [x × W].
	 */
	public Picture reduceWidth(double x) {
		if (x >= 1) {
			System.out.println("x should be smaller than 1 to reduce");
		}
		Color[][] OriginalTemp = new Color[H][W];
		for (int row = 0; row < H; row++) {
			OriginalTemp[row] = OriginalPic[row].clone();
		}
		
		int reduceNum = (int) Math.ceil(W * (1 - x));
		System.out.println("Redu: " + reduceNum);
		Picture output = new Picture(W - reduceNum, H);
		
		for (int i = 0; i < reduceNum; i++) {
			int[][] input = Importance(OriginalTemp);
			ArrayList<Integer> reducePixels = DynamicProgramming.minCostVC(input);
			Color[][] newPic = new Color[H][W - 1];
			
			for (int j = 0; j < H; j++) {
				int delete = reducePixels.get(2 * j + 1);
				if (delete == 0) {
					System.arraycopy(OriginalTemp[j], 1, newPic[j], 0, W - 1);
				} else {
					System.arraycopy(OriginalTemp[j], 0, newPic[j], 0, delete);
					if (delete != W - 1) {
						System.arraycopy(OriginalTemp[j], delete + 1, newPic[j], delete,
						                 W - 1 - delete);
					}
				}
				//				for(int k = 0; k < W - 1; k++) {
				//					if(k < delete) {
				//						newPic[j][k] = OriginalTemp[j][k];
				//					} else if(k >= delete) {
				//						newPic[j][k] = OriginalTemp[j][k + 1];
				//					}
				//					//System.out.println("newPic["+j+"]["+k+"] "+newPic[j][k]+" orig["+j+"]["+k+"]: "+OriginalPic[j][k]);
				//				}
			}
			OriginalTemp = new Color[H][W - 1];
			
			for (int row = 0; row < newPic.length; row++) {
				//Update new original picture
				OriginalTemp[row] = newPic[row].clone();
			}
			W -= 1;
		}
		
		for (int i = 0; i < OriginalTemp.length; i++) {
			for (int j = 0; j < OriginalTemp[0].length; j++) {
				Color tmpColor = OriginalTemp[i][j];
				output.set(j, i, tmpColor);
			}
		}
		
		return output;
	}
	
	
	/**
	 * Y importance equals to the distance from the M[i-1,j] to M[i+1,j].
	 * If i = 0, then distance is from M[H-1,j] to M[i+1,j]
	 * If i = H-1, then distance is from M[i-1,j] to M[0,j]
	 *
	 * @param M Input M is the color matrix.
	 * @return return an important value matrix calculated by the input M.
	 */
	private int[][] YImportance(Color[][] M) {
		int height, width;
		height = M.length;
		width = M[0].length;
		int[][] N = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (i == 0) {
					N[i][j] = Dist(M[height - 1][j], M[i + 1][j]);
				} else if (i == (height - 1)) {
					N[i][j] = Dist(M[i - 1][j], M[0][j]);
				} else {
					N[i][j] = Dist(M[i - 1][j], M[i + 1][j]);
				}
			}
		}
		return N;
	}
	
	/**
	 * X importance equals to the distance from the M[i,j-1] to M[i,j+1].
	 * If j = 0, then distance is from M[i,W-1] to M[i,j+1]
	 * If j = W-1, then distance is from M[i,0] to M[i,j-1]
	 *
	 * @param M Input M is the color matrix.
	 * @return return an important value matrix calculated by the input M.
	 */
	private int[][] XImportance(Color[][] M) {
		int height, width;
		height = M.length;
		width = M[0].length;
		int[][] N = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (j == 0) {
					N[i][j] = Dist(M[i][width - 1], M[i][j + 1]);
				} else if (j == (width - 1)) {
					N[i][j] = Dist(M[i][0], M[i][j - 1]);
				} else {
					N[i][j] = Dist(M[i][j - 1], M[i][j + 1]);
				}
				//System.out.println("row: "+i+" col: "+j);
			}
		}
		return N;
	}
	
	/**
	 * Importance of vertex M[i,j] = X Importance of M[i,j] + Y Importance of M[i,j]
	 *
	 * @param M Input M is the color matrix.
	 * @return return an important value matrix calculated by the input M. Importance value =
	 * Ximportance + Yimportance
	 */
	private int[][] Importance(Color[][] M) {
		int[][] X = XImportance(M);
		int[][] Y = YImportance(M);
		int[][] result = new int[H][W];
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				result[i][j] = X[i][j] + Y[i][j];
			}
		}
		return result;
	}
	
	/**
	 * Calculate the distance from color p to color q. The calculation
	 * formula is Dist(p; q) = (r1 − r2)^2 + (g1 − g2)^2 + (b1 − b2)^2
	 *
	 * @param p Input p is the first color valuable
	 * @param q Input q is the first color valuable
	 * @return The result distance from color p to color q.
	 */
	private int Dist(Color p, Color q) {
		int r = p.getRed() - q.getRed();
		int g = p.getGreen() - q.getGreen();
		int b = p.getBlue() - q.getBlue();
		int result = (int) (Math.pow(r, 2) + Math.pow(g, 2) + Math.pow(b, 2));
		return result;
	}
	
	/**
	 * Only for test
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		ImageProcessor image = new ImageProcessor("./Original.jpg");
		//ImageProcessor image = new ImageProcessor("C:/Users/sc922/Desktop/COM S 311/PA3/sand(420 by 365).jpg");
		//image.picture.show();
		//ImageProcessor image = new ImageProcessor("C:/Users/sc922/Desktop/COM S 311/PA3/home.jpg");
		Picture tmp = image.reduceWidth(0.73);
		tmp.show();
	}
	
}