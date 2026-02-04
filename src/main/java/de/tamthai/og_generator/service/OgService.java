package de.tamthai.og_generator.service;

import org.springframework.stereotype.Service;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.LoadState;

@Service
public class OgService {
    private final Playwright playwright = Playwright.create();
    private final Browser browser = playwright.chromium().launch();

    public byte[] captureUrl(String url) {
        try (BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1200, 630))) {
            try (Page page = context.newPage()) {
                page.navigate(url);
                // Wait for network to be idle to ensure fonts/images load
                page.waitForLoadState(LoadState.NETWORKIDLE);
                return page.screenshot();
            } catch (Exception _) {
                return new byte[0];
            }
        }
    }
}
