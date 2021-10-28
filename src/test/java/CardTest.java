import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import io.github.bonigarcia.wdm.WebDriverManager;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "./driver/win/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ягами Лайт");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+72341234562");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actualMessage = driver.findElement(By.cssSelector(".paragraph.paragraph_theme_alfa-on-white")).getText().trim();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals(expectedMessage, actualMessage);
    };

    @Test
    void shouldNotName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Light -");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+72341234562");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actualMessage = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText().trim();
        String expectedMessage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldNotPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ягами Лайт");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7234123456");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actualMessage = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().trim();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expectedMessage, actualMessage);
    };

    @Test
    void shouldNotCheckbox() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ягами Лайт");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+72341234568");
        driver.findElement(By.cssSelector(".button__text")).click();
        String actualMessage = driver.findElement(By.cssSelector(".input_invalid")).getCssValue("color");
        String expectedMessage = "rgba(255, 92, 92, 1)";

        assertEquals(expectedMessage, actualMessage);
    };
}