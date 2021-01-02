package org.example.sections.four;

public class ImageProcessor {
    /**
     *
     * @param rgb the four byte rgb pixel value from image
     * @return the bitwise AND of rgb pixel and bitmask
     */
    public static int getRed(int rgb) {
        return (rgb & 0x00FF0000);
    }

    /**
     *
     * @param rgb the four byte rgb pixel value from image
     * @return the bitwise AND of rgb pixel and bitmask
     */
    public static int getGreen(int rgb) {
        return (rgb & 0x0000FF00);
    }

    /**
     *
     * @param rgb the four byte rgb pixel value from image
     * @return the bitwise AND of rgb pixel and bitmask
     */
    public static int getBlue(int rgb) {
        return (rgb & 0x000000FF);

    }
    public static void main(String[] args) {

    }
}
