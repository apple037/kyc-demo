package com.wanda.kyc.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class ImageUtil {

    public static BufferedImage getBufferedImage(String imgUrl) throws IOException {
        URL url = new URL(imgUrl);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        return ImageIO.read(connection.getInputStream());
    }

    public static long getSizeKB(BufferedImage img) {
        DataBuffer dataBuffer = img.getData().getDataBuffer();
        return dataBuffer.getSize() * 4L / 1024L;
    }

    public static long getSizeKB(String imgUrl) throws IOException {
        URL url = new URL(imgUrl);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        BufferedImage img = ImageIO.read(connection.getInputStream());
        DataBuffer dataBuffer = img.getData().getDataBuffer();
        return dataBuffer.getSize() * 4L / 1024L;
    }

    public static long getSizeBytes(BufferedImage img) {
        DataBuffer dataBuffer = img.getData().getDataBuffer();
        return dataBuffer.getSize() * 4L;
    }

    public static long getSizeMB(BufferedImage img) {
        DataBuffer dataBuffer = img.getData().getDataBuffer();
        return dataBuffer.getSize() * 4L / (1024L * 1024L);
    }

    public static String getPixel(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        return width + " x " + height;
    }

    public static String getPixel(String imgUrl) throws IOException {
        URL url = new URL(imgUrl);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        BufferedImage img = ImageIO.read(connection.getInputStream());
        int width = img.getWidth();
        int height = img.getHeight();
        return width + " x " + height;
    }
}
