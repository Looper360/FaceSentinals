package gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
 
import javax.imageio.ImageIO;
 
/**
 * This program demonstrates how to resize an image.
 *
 * @author www.codejava.net
 *
 */
public class ImageResizer {
 
    /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional)
     * @param inputImagePath Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param scaledWidth absolute width in pixels
     * @param scaledHeight absolute height in pixels
     * @throws IOException
     */
    public static void resize (String inputImagePath,
            String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
 
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());
 
        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
 
        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);
 
        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }
 
 
    /**
     * Test resizing images
     */
    public static void image_resizer (String inputImagePath) {
        //String inputImagePath = "D:\\FaceDetection\\23621654_518526281827613_3660635719155022033_n.jpg";
        //String outputImagePath1 = "D:\\FaceDetection\\resize.jpg";
 
        try {
            // resize to a fixed width (not proportional)
            int scaledWidth = 480;
            int scaledHeight = 480;
            //ImageResizer.resize(inputImagePath, outputImagePath1, scaledWidth, scaledHeight);
            ImageResizer.resize(inputImagePath, inputImagePath, scaledWidth, scaledHeight);
 
        } catch (IOException ex) {
            System.out.println("Error resizing the image.");
            ex.printStackTrace();
        }
    }
 
}
