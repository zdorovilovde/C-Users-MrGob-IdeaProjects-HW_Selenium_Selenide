package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NegativeApplicationTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldBeFailedIncorrectNameTestInput() { // неликвидное имя
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Zdorovilov Dmitriy");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79507243850");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid")).isDisplayed());
    }

    @Test
    public void shouldBeFailedIncorrectPhoneTestInput() { //некоректный номер телефона
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Здоровилов Дмитрий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("89507243850");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid")).isDisplayed());

    }

    @Test
    public void shouldBeFailedEmptyAgreementTestInput() { // не подтверждённое соглажение
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Здоровилов Дмитрий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79507243850");
        driver.findElement(By.cssSelector("button.button")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).isDisplayed());
    }

    @Test
    public void shouldBeFailedEmptyNameTestInput() { // пустая строка с именем
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79507243850");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).isDisplayed());
    }

    @Test
    public void shouldBeFailedEmptyPhoneTestInput() { //пустное поле номера телефона
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Здоровилов Дмитрий");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).isDisplayed());
    }
}