import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class AmazonTestCase {
    public static WebDriver driver;
    public static WebDriverWait wait;


    @BeforeClass
    public void Setup() {
        driver = new ChromeDriver();
        driver.get("https://www.amazon.com.tr");
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        if (driver.findElements(By.xpath("//div[@class='nav-bb-right']/a[1]")).size() != 0) {
            WebElement myAccount = driver.findElement(By.xpath("//div[@class='nav-bb-right']/a[1]"));
            myAccount.click();
        }
    }

    @Test
    public void SuccessfulVirgosolTest() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-link-accountList-nav-line-1")));

        WebElement siteDirectory = driver.findElement(By.id("nav-link-accountList-nav-line-1"));
        String text = siteDirectory.getText();
        Assert.assertEquals(text, "Merhaba, Giriş yapın");

        WebElement acceptCookies = driver.findElement(By.id("sp-cc-accept"));
        acceptCookies.click();

        WebElement loginButton = driver.findElement(By.xpath("//div[@id='nav-tools']/a[1]"));
        loginButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='a-row a-spacing-base']/input[1]")));
        WebElement enteredEmail = driver.findElement(By.xpath("//div[@class='a-row a-spacing-base']/input[1]"));
        enteredEmail.click();
        enteredEmail.sendKeys("email@gmail.com");

        WebElement continueButton = driver.findElement(By.id("continue"));
        continueButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='a-section a-spacing-large']/input")));
        WebElement enteredPassword = driver.findElement(By.xpath("//div[@class='a-section a-spacing-large']/input"));
        enteredPassword.click();
        enteredPassword.sendKeys("password");

        WebElement signInButton = driver.findElement(By.id("signInSubmit"));
        signInButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='nav-tools']/a/span[@class='nav-line-2 ']")));
        WebElement accountAndList = driver.findElement(By.xpath("//div[@id='nav-tools']/a/span[@class='nav-line-2 ']"));
        String text1 = accountAndList.getText();
        Assert.assertEquals(text1, "Hesap ve Listeler");

        Actions action = new Actions(driver);
        action.moveToElement(accountAndList).perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='nav-text' and text()='Liste Oluşturun']")));
        WebElement createList1 = driver.findElement(By.xpath("//span[@class='nav-text' and text()='Liste Oluşturun']"));
        createList1.click();

        if (driver.findElements(By.id("createList-announce")).size() != 0) {  //element ekranda var mı diye bakmak için find elenemts kullanarak size ını aldık
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//input[@aria-labelledby='createList-announce']")));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='createList-announce']")));
            WebElement createList2 = driver.findElement(By.xpath("//input[@aria-labelledby='createList-announce']"));
            createList2.click();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='a-section a-spacing-base']/span/input")));
        WebElement listText = driver.findElement(By.xpath("//div[@class='a-section a-spacing-base']/span/input"));
        listText.clear();
        listText.sendKeys("Virgosol Liste");

        WebElement createListButton = driver.findElement(By.xpath("//span[@id='wl-redesigned-create-list']"));
        createListButton.click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".a-popover-modal")));  //invisible element ekranda olmayana dek bekler
        WebElement allCategories = driver.findElement(By.xpath("//div[@class='nav-search-scope nav-sprite']/select"));
        Select categories = new Select(allCategories);  //kategori seçimi yapıldı
        categories.selectByVisibleText("Bilgisayarlar");

        WebElement computerElement = driver.findElement(By.xpath("//div[@class='nav-search-scope nav-sprite']/select/option[@value='search-alias=computers']"));
        String compText = computerElement.getText();
        Assert.assertEquals(compText, "Bilgisayarlar");

        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.click();
        searchBox.sendKeys("msi");

        WebElement searchButton = driver.findElement(By.id("nav-search-submit-button"));
        searchButton.click();

        String msiText = driver.findElement(By.xpath("//div[@class='a-section a-spacing-small a-spacing-top-small']/span[3]")).getText();
        Assert.assertEquals(msiText, "\"msi\"");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='s-pagination-item s-pagination-button' and text()='2']")));
        WebElement click2Page = driver.findElement(By.xpath("//a[@class='s-pagination-item s-pagination-button' and text()='2']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", click2Page);
        click2Page.click();

        wait.until(ExpectedConditions.textToBe(By.xpath("//div[@class='a-section a-spacing-small a-spacing-top-small']/span[1]"), "5.000 üzeri sonuç arasından 25-48 arası gösteriliyor. Aranan ürün:"));
        String msiText2 = driver.findElement(By.xpath("//div[@class='a-section a-spacing-small a-spacing-top-small']/span[1]")).getText();
        Assert.assertEquals(msiText2, "5.000 üzeri sonuç arasından 25-48 arası gösteriliyor. Aranan ürün:");

        WebElement secondProduct = driver.findElement(By.xpath("//div[contains(@class,'s-main')]/div[@data-index='2']"));
        secondProduct.click();

        WebElement addList = driver.findElement(By.xpath("//span[@class='a-button-inner']/input[@id='add-to-wishlist-button-submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addList);
        addList.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[contains(@class,'huc-atwl-header-main')])[1]")));
        if (driver.findElement(By.xpath("(//span[contains(@class,'huc-atwl-header-main')])[1]")).getText().equals("1 ürün şuraya eklendi:")) { // eğer ürün eklendiyse
            String successAddList = driver.findElement(By.xpath("(//span[contains(@class,'huc-atwl-header-main')])[1]")).getText();
            Assert.assertEquals(successAddList, "1 ürün şuraya eklendi:");
        } else { //eklenmediyse
            String text3 = driver.findElement(By.xpath("(//span[contains(@class,'huc-atwl-header-main')])[1]")).getText();
            Assert.assertEquals(text3, "Bu ürün zaten şurada mevcut:");
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='a-button-inner']/a[@class='a-button-text' and text()='Listenizi Görüntüleyin']")));
        WebElement seeList = driver.findElement(By.xpath("//span[@class='a-button-inner']/a[@class='a-button-text' and text()='Listenizi Görüntüleyin']"));
        seeList.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-list-name")));
        String listName = driver.findElement(By.id("profile-list-name")).getText();
        Assert.assertEquals(listName, "Virgosol Liste");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@id,'delete-button')]")));
        WebElement deleteButton = driver.findElement(By.xpath("//span[contains(@id,'delete-button')]"));
        deleteButton.click();

        wait.until(ExpectedConditions.textToBe(By.xpath("//div[@class='a-alert-content' and text()='Silindi']"), "Silindi"));
        String deleteText = driver.findElement(By.xpath("//div[@class='a-alert-content' and text()='Silindi']")).getText();
        Assert.assertEquals(deleteText, "Silindi");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='nav-tools']/a/span[@class='nav-line-2 ']")));
        WebElement accountAndList2 = driver.findElement(By.xpath("//div[@id='nav-tools']/a/span[@class='nav-line-2 ']"));

        Actions action1 = new Actions(driver);
        action1.moveToElement(accountAndList2).perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='nav-text' and text()='Çıkış Yap']")));
        WebElement signOut = driver.findElement(By.xpath("//span[@class='nav-text' and text()='Çıkış Yap']"));
        signOut.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("createAccountSubmit")));
        String signInText = driver.findElement(By.id("createAccountSubmit")).getText();
        Assert.assertEquals(signInText, "Amazon hesabınızı oluşturun");

    }

    @AfterClass
    public void CloseDriver() {
        driver.close();
    }
}
