package com.bituan.country_currency_and_exchange_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

@Service
public class ImageGenerationService {

    @Autowired
    public ImageGenerationService() {}

    public void generateImage (String string) {
        int width = 400;
        int height = 200;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();

        graphics2D.setBackground(Color.WHITE);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(string, 100, 100);

        graphics2D.dispose();

        try {
            ImageIO.write(image, "PNG", new File("generated_image.png"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
