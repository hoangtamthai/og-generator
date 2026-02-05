package de.tamthai.og_generator.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import de.tamthai.og_generator.service.OgService;

@RestController
public class OgController {
    OgService ogService;

    @Autowired
    public OgController(OgService ogService) {
        this.ogService = ogService;
    }

    @GetMapping(value = "/api/og", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generateFromTemplate(
            @RequestParam(required = false) Optional<String> url, @RequestParam(required = false) String title,
            @RequestParam(required = false) String description) throws SAXException, IOException {
        int width = 1200;
        int height = 630;

        if (url.isPresent()) {
            return ogService.fromUrl(url.get(), width, height);
        }

        return ogService.fromHtml(title, description);
    }

    @GetMapping("/api/og/thread")
    public String test() {
        return Thread.currentThread().toString();
    }
}
