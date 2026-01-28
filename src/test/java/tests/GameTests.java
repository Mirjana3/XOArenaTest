package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.GamePage;

public class GameTests {

    WebDriver driver;
    GamePage gamePage;

    @BeforeClass
    public void setup() {
        System.setProperty(
                "webdriver.edge.driver",
                "C:\\Users\\Korisnik\\Downloads\\edgedriver_win64\\msedgedriver.exe"
        );

        driver = new EdgeDriver();
        driver.manage().window().maximize();
        gamePage = new GamePage(driver);
    }

    @Test(priority = 1)
    public void testOpenGamePage() {
        gamePage.open("https://mirjana3.github.io/X-O-Arena/");
        Assert.assertTrue(driver.getTitle().contains("Arena"));
    }

    @Test(priority = 2)
    public void testStartGameButton() {
        gamePage.open("https://mirjana3.github.io/X-O-Arena/");
        gamePage.clickStart();
    }

    @Test(priority = 3)
    public void testMakeMove() {
        gamePage.open("https://mirjana3.github.io/X-O-Arena/");
        gamePage.clickStart();
        gamePage.clickCell(0);
    }

    @Test(priority = 4)
    public void testPlayScenario() throws InterruptedException {
        gamePage.open("https://mirjana3.github.io/X-O-Arena/");
        gamePage.clickStart();

        gamePage.clickCell(0);
        Thread.sleep(600);
        gamePage.clickCell(1);
        Thread.sleep(600);
        gamePage.clickCell(6);
        Thread.sleep(600);
        gamePage.clickCell(7);
        Thread.sleep(600);
        gamePage.clickCell(8);
        Thread.sleep(600);
    }

    @Test(priority = 5)
    public void testMultipleMoves() {
        gamePage.open("https://mirjana3.github.io/X-O-Arena/");
        gamePage.clickStart();

        for (int i = 0; i < 5; i++) {
            gamePage.clickCell(i);
        }
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
