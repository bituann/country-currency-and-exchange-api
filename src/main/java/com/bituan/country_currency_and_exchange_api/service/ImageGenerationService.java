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

    public void generateImage (String string, String name) {
        int width = 800;
        int height = 400;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();

        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, width, height);

//        Font font = new Font("Arial", Font.BOLD, 30);
//        graphics2D.setFont(font);

//        graphics2D.setColor(Color.BLACK);
//        graphics2D.drawString(string, 400, 200);

        graphics2D.dispose();

        try {
            ImageIO.write(image, "PNG", new File("%s.png".formatted(name)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
