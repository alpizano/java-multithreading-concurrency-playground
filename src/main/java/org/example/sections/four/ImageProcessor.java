package org.example.sections.four;

public class ImageProcessor {


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
    public static void main(String[] args) {
        int seventeen = 0x11; // 0001 0001
        int bitmask = 0xFF; // 1111 1111
        int result = seventeen & bitmask;

        System.out.println(result); // 17
        System.out.println(result >> 4); // 0001 0001 (17) => (shift right 4) 0000 0001 (1)

    }
}
