package image;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class QualityValidation {
	
	private final static double IMAGE_PERCENT = 0.25;

	public void displayImage(BufferedImage buffImage) {
		
		ImageIcon icon = new ImageIcon(buffImage);
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(buffImage.getWidth() + 50, buffImage.getHeight() + 50);
		JLabel lbl = new JLabel();
		lbl.setIcon(icon);
		frame.add(lbl);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public BufferedImage createEmptyImage(BufferedImage image) {
		
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		displayImage(newImage);
		return newImage;
	}

	public Mat createPercentageImage(Mat mat, double percent) {
		
		/*
		 * Calcula as coordenadas que serão utilizada para a imagem
		 */
		int x = (int) (mat.width() * percent);
		int y = (int) (mat.height() * percent);
		int width = (int) (mat.width() * (percent * 2));
		int height = (int) (mat.height() * (percent * 2));

		Rect rect = new Rect(x, y, width, height);
		/*
		 * Pega apenas a parte que será de fato utilizada da imagem
		 */
		Mat imageM = new Mat(mat, rect);

		return imageM;
	}

	public BufferedImage matToBuffImage(Mat mat) {
		
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (mat.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		
		byte[] bbyteArray = new byte[mat.channels() * mat.cols() * mat.rows()];
		mat.get(0, 0, bbyteArray);
		BufferedImage image = new BufferedImage(mat.width(), mat.height(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

		System.arraycopy(bbyteArray, 0, targetPixels, 0, bbyteArray.length);
		return image;
	}

	public Mat convertToHSV(Mat mat) {
		
		/*
		 * Copia o conteúdo da Mat a ser convertida para HSV em uma nova Mat
		 */
		Mat hsv = mat.clone();
		/*
		 * Realiza a conversão
		 */
		Imgproc.cvtColor(mat, hsv, Imgproc.COLOR_BGR2HSV);
		
		/*
		 * Cria uma lista de Mats para que o método split adicione os canais da mat
		 * na mesma
		 */
		
		List<Mat> list = new ArrayList<Mat>();
		Core.split(hsv, list);
		
		/*
		 * Retorna apenas o canal V.
		 */
		
		return list.get(2);
	}

	public double getSharpness(Mat mat) {
		
		Mat v = mat.clone();
		Mat canny = mat.clone();
		
		double highThreshold = Imgproc.threshold(mat, v, 0, 255, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);
		double lowThreshod = highThreshold * 0.5;		
		
		mat.convertTo(mat, CvType.CV_8U);
		Imgproc.Canny(mat, canny, highThreshold, lowThreshod);
		
		double cannyReturned = Core.countNonZero(canny);
		double sharpness = (cannyReturned * 100) / (canny.width() * canny.height());
		
		return sharpness;
	}

	public Mat loadImage(String imageLocation){
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat firstUploadedMat = Highgui.imread(imageLocation);

		Mat convertedToHSVMat = convertToHSV(firstUploadedMat);

		Mat croppedMat = createPercentageImage(convertedToHSVMat, IMAGE_PERCENT);
		
		return croppedMat;
	}
	
	/*
	 * Método para testar a nitidez
	 */
	public boolean testImageForSharpness(String imageLocation) {
		
		Mat croppedMat = loadImage(imageLocation); 

		double sharpness = getSharpness(croppedMat);

		return sharpness > 6;
	}
	
	/*
	 * Método para testar a exposição da imagem
	 */
	public int testImageForOverExposed(String imageLocation) {
		
		Mat croppedMat = loadImage(imageLocation);

		MatOfInt histSize = new MatOfInt(256);

		MatOfFloat histRange = new MatOfFloat(0f, 256f);

		Mat v_hist = new Mat();
		List<Mat> list = new ArrayList<>();

		list.add(croppedMat);
		Imgproc.calcHist(list, new MatOfInt(0), new Mat(), v_hist, histSize, histRange, true);

		double sumVeryDark = 0;
		double sumDark = 0;
		double sumMedium = 0;
		double sumBright = 0;
		double sumVeryBright = 0;
		double sumHist = 0;

		/*
		 * Percorre os valores do Histograma gerado e verifica o nível de
		 * brilho na imagem
		 */
		for (int i = 0; i < 256; i++) {
			if (i < 51) {
				sumVeryDark += Math.round(v_hist.get(i, 0)[0]);
			} else if (i < 102) {
				sumDark += Math.round(v_hist.get(i, 0)[0]);
			} else if (i < 153) {
				sumMedium += Math.round(v_hist.get(i, 0)[0]);
			} else if (i < 204) {
				sumBright += Math.round(v_hist.get(i, 0)[0]);
			} else {
				sumVeryBright += Math.round(v_hist.get(i, 0)[0]);
			}
			sumHist += Math.round(v_hist.get(i, 0)[0]);
		}

		sumVeryDark = (sumVeryDark / sumHist) * 100;
		sumDark = (sumDark / sumHist) * 100;
		sumMedium = (sumMedium / sumHist) * 100;
		sumBright = (sumBright / sumHist) * 100;
		sumVeryBright = (sumVeryBright / sumHist) * 100;
		
		/*
		 * Faz a validação de acordo com os resultados de brilho obtido
		 * Os valores retornados deverão ser utilizados para permitir ou não
		 * o upload da imagem no banco de dados.
		 */
		if (sumVeryDark > 35) {
			return 1;
		} else if (sumVeryDark + sumDark > 60) {
			return 2;
		} else if (sumVeryBright > 35) {
			return 3;
		}
		return 0;
	}

}
