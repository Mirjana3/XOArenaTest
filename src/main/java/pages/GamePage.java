package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GamePage {

    WebDriver driver;
    WebDriverWait wait;

    // lokatori
    By startButton = By.id("startBtn");
    By winnerText = By.id("winner");

    public GamePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void open(String url) {
        driver.get(url);
        wait.until(d -> ((JavascriptExecutor) d)
                .executeScript("return document.readyState")
                .equals("complete"));
        wait.until(ExpectedConditions.titleContains("Arena"));
    }

    public void clickStart() {
        WebElement startBtn = wait.until(ExpectedConditions.elementToBeClickable(startButton));
        startBtn.click();
    }

    public void clickCell(int index) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int attempts = 0;
        int maxAttempts = 25; // 25 * 200ms = 5 sekundi maksimalno čekanje

        while (attempts < maxAttempts) {
            // Dohvati ćelije iz DOM-a svaki put, da Selenium vidi aktualni tekst
            java.util.List<WebElement> cells = driver.findElements(By.cssSelector(".board__cell"));

            if (index < 0 || index >= cells.size()) {
                throw new RuntimeException("Ćelija index " + index + " nije dostupna!");
            }

            WebElement cell = cells.get(index);

            if (cell.getText().isEmpty()) {
                // Klik pomoću JS
                js.executeScript(
                        "arguments[0].dispatchEvent(new MouseEvent('click', {bubbles: true}))",
                        cell
                );
                return;
            } else {
                try {
                    Thread.sleep(200); // čekaj 200ms i probaj ponovno
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                attempts++;
            }
        }

        throw new RuntimeException("Ćelija index " + index + " nije mogla biti kliknuta nakon čekanja!");
    }




    public String getWinnerText() {
        return (String) ((JavascriptExecutor) driver).executeScript(
                "let winner = document.getElementById('winner');" +
                        "return winner ? winner.innerText : '';"
        );
    }
}