package de.tamthai.og_generator.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OgController {
    @GetMapping(value = "/api/og/template", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generateFromTemplate(@RequestParam String title) throws IOException {
        int width = 1200;
        int height = 630;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // 1. Draw Background
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, width, height);

        // 2. Draw Text
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 60));
        g2d.drawString(title, 100, 300);

        g2d.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }

    @GetMapping("/og/test")
    public String test() {
        return Thread.currentThread().toString();
        // Should output something containing "VirtualThread"
        // instead of "Thread[http-nio...]"
    }
}
