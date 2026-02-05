package de.tamthai.og_generator.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.xhtmlrenderer.simple.Graphics2DRenderer;
import org.xhtmlrenderer.swing.Java2DRenderer;
import org.xml.sax.SAXException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OgService {
    // private final Playwright playwright = Playwright.create();
    // private final Browser browser = playwright.chromium().launch();

    // public byte[] captureUrl(String url, int width, int height) {
    // try (BrowserContext context = browser.newContext(new
    // Browser.NewContextOptions()
    // .setViewportSize(width, height)
    // .setDeviceScaleFactor(2))) {
    // try (Page page = context.newPage()) {
    // page.navigate(url);
    // // Wait for network to be idle to ensure fonts/images load
    // page.waitForLoadState(LoadState.NETWORKIDLE);
    // return page.screenshot();
    // } catch (Exception _) {
    // return new byte[0];
    // }
    // }
    // }

    public byte[] fromTemplate(String title, String description, int width, int height) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // 1. Draw Background
        g2d.setColor(new Color(255, 241, 227));
        g2d.fillRect(0, 0, width, height);

        // 2. Draw Text
        g2d.setColor(new Color(57, 57, 57));
        g2d.setFont(new Font("Arial", Font.BOLD, 60));
        g2d.drawString(title, 100, 300);

        g2d.setColor(new Color(57, 57, 57));
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.drawString(description, 100, 400);

        g2d.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }

    public byte[] fromUrl(String url, int width, int height) {
        if (url == null) {
            url = "http://www.w3.org/";
        }
        // render
        try {
            BufferedImage buff = null;
            buff = Graphics2DRenderer.renderToImage(url, width, height);
            java.io.ByteArrayOutputStream os = new java.io.ByteArrayOutputStream();
            javax.imageio.ImageIO.write(buff, "png", os);
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public byte[] fromHtml(String title, String description) throws SAXException, IOException {
        long startTime = System.currentTimeMillis();
        String xhtml = """
                <html xmlns="http://www.w3.org/1999/xhtml">
                <head>
                    <style>
                        body {
                            background-color: rgb(255, 241, 227);
                            color: rgb(57, 57, 57);
                            margin: 0;
                            font-family: sans-serif;
                            width: 1200px;
                            height: 630px;
                        }
                        .content {
                            height: 630px;
                            margin: 0;
                            padding:50px;
                            text-align: center;
                            flex: content;
                            flex-direction: row;
                            justify-content: center;
                            background-color: rgb(255, 241, 227);
                        }
                        h1 {
                            font-size: 64px;
                            margin: 0;
                            background-color: rgb(255, 241, 227);
                            z-index: 2;
                            position: relative;
                            padding: 20px;
                            margin-top: 150px;
                        }
                        h2 {
                            font-size: 40px;
                            margin-top: 50px;
                            margin-left: 0px;
                            opacity: 0.9;
                        }
                    </style>
                </head>
                <body>
                    <div class="content">
                        <div style="position: relative; width: fit-content">
                            <div
                                style="background-color: rgb(57,57,57); z-index: 1; padding-top: 20px; position: absolute; width: 100px; height: 100px; top: -20px;left: -20px;">
                            </div>
                            <h1>%s</h1>
                            <div
                                style="background-color: rgb(57,57,57); position: absolute; width: 100px; height: 100px; bottom: -20px;right: -20px;">
                            </div>
                        </div>
                        <h2>%s</h2>
                    </div>
                </body>
                </html>
                """
                .formatted(title, description);
        BufferedImage image = Java2DRenderer.htmlAsImage(xhtml, 1200);
        // Convert to PNG bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        long duration = System.currentTimeMillis() - startTime;
        log.info("Generated title: {}, description: {}, ms: {}", title, description, duration);
        return baos.toByteArray();
    }

}
