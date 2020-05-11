package project.services;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import project.models.Product;
import project.models.Site;
import project.models.Url;
import project.repositories.ProductRepository;

import java.io.IOException;

@Component
public class ParseServiceImpl implements ParseService {
    @Autowired
    private SiteServiceImpl siteService;
    @Autowired
    private ProductService productService;

    @SneakyThrows
//    @Scheduled(fixedDelay = 3600000)
    @Override
    public void parse() {
        productService.truncateTable();
        System.setProperty("webdriver.chrome.driver", "D:\\Driver\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        for (Site site : siteService.findAll()
        ) {
            for (Url url : site.getSaleUrls()
            ) {
                Document doc = null;
                if (site.getHasJS()) {
                    driver.get(url.getUrl());
//                    JavascriptExecutor js = (JavascriptExecutor) driver;
//                    js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
                    doc = Jsoup.parse(driver.getPageSource());
                } else {
                    try {
                        doc = Jsoup.connect(url.getUrl())
                                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                                .get();
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                }
                Elements goods = doc.select(site.getProductsTag());
                for (Element e : goods.select(site.getProductItemTag())
                ) {
                    String newPrice = e.select(site.getNewPriceTag()).text().split(" (?=\\D)")[0].replaceAll(" ", "");
                    String oldPrice = e.select(site.getOldPriceTag()).text().split(" (?=\\D)")[0].replaceAll(" ", "");
                    productService.save(Product.builder()
                            .site(site)
                            .productName(e.select(site.getProductName()).text())
                            .productUrl(site.getSiteUrl() + e.select(site.getProductLinkTag()).attr(site.getProductLink()))
                            .imgUrl(e.select(site.getProductImgTag()).attr(site.getProductImg()).equals("") ? null : e.select(site.getProductImgTag()).attr(site.getProductImg()))
                            .newPrice(newPrice)
                            .oldPrice(oldPrice)
                            .who(url.getWho())
                            .build());
                }
            }
        }
        driver.close();
    }
}
