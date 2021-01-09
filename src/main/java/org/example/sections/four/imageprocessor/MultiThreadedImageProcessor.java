package org.example.sections.four.imageprocessor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MultiThreadedImageProcessor {

    public static final String SOURCE_FILE = "./resources/many-flowers.jpg";
    public static final String DESTINATION_FILE = "./many-flowers.jpg";

    public static void main(String[] args) throws IOException {
        int seventeen = 0x11; // 0001 0001
        int bitmask = 0xFF; // 1111 1111
        int result = seventeen & bitmask;

        //System.out.println(result); // 17
        //System.out.println(result >> 4); // 0001 0001 (17) => (shift right 4) 0000 0001 (1)

        // read original image into bufferedimage object
        BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));

        // original dimensions of image are 3036 x 4048
        System.out.println(originalImage.getWidth());
        System.out.println(originalImage.getHeight());

        // store output image
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        long startTime = System.currentTimeMillis();

        //recolorSingleThreaded(originalImage, resultImage);
        recolorMultiThreaded(originalImage, resultImage, 8);

        long endTime = System.currentTimeMillis();

        System.out.println(String.format("%d milliseconds", endTime - startTime));

        File outputFile = new File(DESTINATION_FILE);
        ImageIO.write(resultImage,"jpg", outputFile);
    }

    public static void recolorMultiThreaded(BufferedImage originalImage, BufferedImage resultImage, int numberOfThreads)  {
        List<Thread> threads = new ArrayList<>();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight() / numberOfThreads;

        // loop through # of threads
        IntStream.range(0,numberOfThreads).forEach( i -> {
            //int threadMultiplier = i;

            // add thread, override run()
            Thread thread = new Thread(() -> {
                int topCorner = height * i;
                recolorImage(originalImage, resultImage,0,topCorner,width,height);
            });

            threads.add(thread);
        });

        threads.forEach(Thread::start);
        threads.forEach(i -> {
            try {
                i.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }


    public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage) {
        recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
    }

    public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int leftCorner,
                                    int topCorner, int width, int height) {

        for(int x = leftCorner; x < leftCorner +width && x < originalImage.getWidth(); x++) {
            for(int y = topCorner; y< topCorner + height && y < originalImage.getHeight(); y++) {
                recolorPixel(originalImage, resultImage, x, y);
            }
        }
    }

    /**
     *
     * @param originalImage
     * @param resultImage
     * @param x
     * @param y
     */
    public static void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
        int rgb = originalImage.getRGB(x, y);

        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        int newRed, newGreen, newBlue;

        // make purple
        if(isShadeOfGray(red, green, blue)) {
            // increase red value
            newRed = Math.min(255, red + 10);
            newGreen = Math.max(0, green -80);
            newBlue = Math.max(0, blue -20);
        }
        else {
            // if pixel != gray, leave alone
            newRed = red;
            newGreen = green;
            newBlue = blue;
        }

        int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
        setRGB(resultImage, x, y, newRGB);
    }

    public static void setRGB(BufferedImage image, int x, int y, int rgb) {
        image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
    }


    /**
     *
     * @param red value from 0 to 255
     * @param green value from 0 to 255
     * @param blue value from 0 to 255
     * @return checks if pixel is shade of gray
     */
    public static boolean isShadeOfGray(int red, int green, int blue) {
        // arbitrary distance is 30
        return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(green - blue) < 30;
    }

    /**
     *
     * @param red value from 0 to 255
     * @param green value from 0 to 255
     * @param blue value from 0 to 255
     * @return build compound pixel rgb value
     */
    public static int createRGBFromColors(int red, int green, int blue) {
        int rgb = 0;

        rgb |= blue;
        rgb |= green << 8; // shift green left 8 bits, then add
        rgb |= red << 16; // shift red left 16 bits, then add

        // set alpha value to 255 (opaque)
        return (rgb |= 0xFF000000);
    }
    /**
     *
     * @param rgb the four byte rgb pixel value from image
     * @return the bitwise AND of rgb pixel and bitmask
     */
    public static int getRed(int rgb) {
        return (rgb & 0x00FF0000) >> 16;
    }

    /**
     *
     * @param rgb the four byte rgb pixel value from image
     * @return the bitwise AND of rgb pixel and bitmask
     */
    public static int getGreen(int rgb) {
        // shift bits one byte to the right
        return (rgb & 0x0000FF00) >> 8;
    }

    /**
     *
     * @param rgb the four byte rgb pixel value from image
     * @return the bitwise AND of rgb pixel and bitmask
     */
    public static int getBlue(int rgb) {
        return (rgb & 0x000000FF);

    }

}
