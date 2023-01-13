
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Tests {

    private final String basePriceId = "BasePrice";
    private final String basePriceEditId = "base-edit-icon";
    private final String basePriceValueInput = "base-value-input";
    private final String basePriceCheckButton = "base-check-icon";

    private final String totalValueField = "//*[@id=\"app\"]/div/div/div/span";

    private String addNewFieldLabelId = "ghost-label-input";
    private String addNewFieldValueId = "ghost-value-input";
    private String addNewFieldCheckId = "ghost-check-icon";

    private String internalSurcharge = "/html/body/div/div/div/ul/div[4]";
    private String storageSurcharge = "//*[@id=\"app\"]/div/div/ul/div[6]/div[2]";
    private String alloySurcharge = "//*[@id=\"app\"]/div/div/ul/div[2]/div[2]";
    private String scrapSurcharge = "//*[@id=\"app\"]/div/div/ul/div[3]/div[2]";
    private WebDriver driver;
    private Actions action;

    //to check that selenium and chrome driver works
//    public static void main(String[] args) {
//        System.setProperty("webdriver.chrome.driver","C:\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");
//        WebDriver driver = new ChromeDriver();
//        System.out.println("\n---------------------------\nExample 1\n---------------------------");
//        //Open browser to needed page
//        driver.navigate().to("http://www.google.com/");
//        driver.manage().window().maximize();
//        //Cookies prompt handling
//        driver.findElement(By.id("W0wltc")).click();
//        //Xpath for search field "/html/body/div[1]/div[3]/form/div[1]/div[1]/div[1]/div/div[2]/input"
//        driver.findElement(By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[1]/div/div[2]/input")).sendKeys("TSET company");
//        //Xpath for Google Search button before google suggestions:
//        //"/html/body/div[1]/div[3]/form/div[1]/div[1]/div[4]/center/input[1]"
//        //Xpath for Google Search button after google suggestions:
//        //"/html/body/div[1]/div[3]/form/div[1]/div[1]/div[2]/div[2]/div[5]/center/input[1]"
//        driver.findElement(By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[4]/center/input[1]")).click();
//        //check if the results page is displayed
//        String text = driver.getTitle();
//        assertEquals("TSET company - Google Search",text);
//        //closing driver
//        driver.quit();
//    }

    @Test
    public void changeBasePrice(){
        System.out.println("\n###\nTEST: Change Base Price\n###");
        System.out.println("1 - edit the Base field to 5");
        WebElement basePriceElement = driver.findElement(By.id(basePriceId));
        action.moveToElement(basePriceElement).moveToElement(driver.findElement(By.id(basePriceEditId))).click().build().perform();
        basePriceElement.findElement(By.id(basePriceValueInput)).clear();
        basePriceElement.findElement(By.id(basePriceValueInput)).sendKeys("5");
        basePriceElement.findElement(By.id(basePriceCheckButton)).click();
        System.out.println("2 - Check that the total matches the change");
        String actualTotal = driver.findElement(By.xpath(totalValueField)).getText();
        assertEquals("5.00",actualTotal);
    }

    @Test
    public void addAllPriceComponents(){
        System.out.println("\n###\nall price components from Testdata\n###");
        System.out.println("1 - Add all test data fields");
        addAddAllFields();
        assertEquals("2.15",
                driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/ul/div[2]/div[3]/div")).getText());
        assertEquals("3.14",
                driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/ul/div[3]/div[3]/div")).getText());
        assertEquals("0.77",
                driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/ul/div[4]/div[3]/div")).getText());
        assertEquals("1.0",
                driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/ul/div[5]/div[3]/div")).getText());
        assertEquals("0.3",
                driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/ul/div[6]/div[3]/div")).getText());
        System.out.println("2 - Check that the total matches the change");
        String actualTotal = driver.findElement(By.xpath(totalValueField)).getText();
        assertEquals("8.36",actualTotal);
    }

    @Test
    public void removePriceComponent(){
        System.out.println("\n###\nRemove price component: Internal surcharge\n\n###");
        //Add all fields
        addAddAllFields();
        //Start test
        System.out.println("1 - Remove Internal Surcharge");
        WebElement element = driver.findElement(By.xpath(internalSurcharge));
        //I couldn't find the right id to make it work
        action.moveToElement(element).moveToElement(driver.findElement(By.xpath("/html/body/div/div/div/ul/div[4]/div[4]/span[1]"))).click().build().perform();
        System.out.println("2 - Check total matches expectations");
        assertEquals("7.59",driver.findElement(By.xpath(totalValueField)).getText());
    }

    @Test
    public void editStorageSurcharge(){
        System.out.println("\n###\nRemove price component: Storage surcharge\n\n###");
        //Add all fields
        addAddAllFields();
        //Start test
        System.out.println("1 - Edit Storage surcharge");
        WebElement element = driver.findElement(By.xpath(storageSurcharge));
        action.moveToElement(element).moveToElement(element.findElement(By.xpath("/html/body/div/div/div/ul/div[6]/div[1]/span"))).click().build().perform();
        String labelXpath = "/html/body/div/div/div/ul/div[6]/div[2]/input";
        driver.findElement(By.xpath(labelXpath)).click();
        driver.findElement(By.xpath(labelXpath)).clear();
        driver.findElement(By.xpath(labelXpath)).sendKeys("T");
        assertEquals("This label is too short!", driver.findElement(By.xpath("/html/body/div/div/div/ul/div[6]/div[2]/p")).getText());

        driver.findElement(By.xpath("/html/body/div/div/div/ul/div[6]/div[4]/span[2]")).click();
        System.out.println("2 - Check field matches expectations");
        assertEquals("Storage surcharge",driver.findElement(By.xpath("/html/body/div/div/div/ul/div[6]/div[2]/span")).getText());
    }

    @Test
    public void editScrapSurcharge(){
        System.out.println("\n###\nRemove price component: Scrap surcharge\n\n###");
        //Add all fields
        addAddAllFields();
        //Start test
        System.out.println("1 - Edit Scrap surcharge");
        WebElement element = driver.findElement(By.xpath(scrapSurcharge));
        //Click edit button
        action.moveToElement(element).moveToElement(element.findElement(By.xpath("/html/body/div/div/div/ul/div[3]/div[1]/span"))).click().build().perform();
        String labelXpath = "/html/body/div/div/div/ul/div[3]/div[3]/input";
        driver.findElement(By.xpath(labelXpath)).click();
        driver.findElement(By.xpath(labelXpath)).clear();
        driver.findElement(By.xpath(labelXpath)).sendKeys(" -2.15");
        assertEquals("Cannot be negative!", driver.findElement(By.xpath("/html/body/div/div/div/ul/div[3]/div[3]/p")).getText());

        driver.findElement(By.xpath("/html/body/div/div/div/ul/div[3]/div[4]/span[2]")).click();
        System.out.println("2 - Check field matches expectations");
        assertEquals("3.14",driver.findElement(By.xpath("/html/body/div/div/div/ul/div[3]/div[3]/div")).getText());
    }

    @Test
    public void editAlloySurcharge() {
        System.out.println("\n###\nRemove price component: Alloy surcharge\n\n###");
        //Add all fields
        addAddAllFields();
        //Start test
        System.out.println("1 - Edit Alloy surcharge");
        WebElement element = driver.findElement(By.xpath(alloySurcharge));
        action.moveToElement(element).moveToElement(element.findElement(By.xpath("/html/body/div/div/div/ul/div[2]/div[1]/span"))).click().build().perform();
        String labelXpath = "/html/body/div/div/div/ul/div[2]/div[3]/input";
        driver.findElement(By.xpath(labelXpath)).click();
        driver.findElement(By.xpath(labelXpath)).clear();
        driver.findElement(By.xpath(labelXpath)).sendKeys("1.79");
        //click check icon
        driver.findElement(By.xpath("/html/body/div/div/div/ul/div[2]/div[4]/span[2]")).click();
        System.out.println("2 - Check total matches expectations");
        assertEquals("8.00", driver.findElement(By.xpath(totalValueField)).getText());
    }

    @BeforeEach
    public void initialize(){
        System.out.println("\n---------------------------\nInitialize driver\n---------------------------");
        System.setProperty("webdriver.chrome.driver","C:\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");
        driver=new ChromeDriver();
        driver.navigate().to("http://localhost:3000/");
        action = new Actions(driver);
    }

    @AfterEach
    public void cleanup(){
        System.out.println("\n---------------------------\nClose Driver\n---------------------------");
        driver.quit();
    }

    private void addAddAllFields(){
        //Field 1
        driver.findElement(By.id(addNewFieldLabelId)).click();
        driver.findElement(By.id(addNewFieldLabelId)).clear();
        driver.findElement(By.id(addNewFieldLabelId)).sendKeys("Alloy surcharge");
        driver.findElement(By.id(addNewFieldLabelId)).click();
        driver.findElement(By.id(addNewFieldValueId)).clear();
        driver.findElement(By.id(addNewFieldValueId)).sendKeys("2.15");
        driver.findElement(By.id(addNewFieldCheckId)).click();
        //Field 2
        driver.findElement(By.id(addNewFieldLabelId)).click();
        driver.findElement(By.id(addNewFieldLabelId)).clear();
        driver.findElement(By.id(addNewFieldLabelId)).sendKeys("Scrap surcharge");
        driver.findElement(By.id(addNewFieldValueId)).clear();
        driver.findElement(By.id(addNewFieldValueId)).sendKeys("3.14");
        driver.findElement(By.id(addNewFieldCheckId)).click();
        //Field 3
        driver.findElement(By.id(addNewFieldLabelId)).click();
        driver.findElement(By.id(addNewFieldLabelId)).clear();
        driver.findElement(By.id(addNewFieldLabelId)).sendKeys("Internal surcharge");
        driver.findElement(By.id(addNewFieldValueId)).clear();
        driver.findElement(By.id(addNewFieldValueId)).sendKeys("0.7658");
        driver.findElement(By.id(addNewFieldCheckId)).click();
        //Field 4
        driver.findElement(By.id(addNewFieldLabelId)).click();
        driver.findElement(By.id(addNewFieldLabelId)).clear();
        driver.findElement(By.id(addNewFieldLabelId)).sendKeys("External surcharge");
        driver.findElement(By.id(addNewFieldValueId)).clear();
        driver.findElement(By.id(addNewFieldValueId)).sendKeys("1");
        driver.findElement(By.id(addNewFieldCheckId)).click();
        //Field 5
        driver.findElement(By.id(addNewFieldLabelId)).click();
        driver.findElement(By.id(addNewFieldLabelId)).clear();
        driver.findElement(By.id(addNewFieldLabelId)).sendKeys("Storage surcharge");
        driver.findElement(By.id(addNewFieldValueId)).clear();
        driver.findElement(By.id(addNewFieldValueId)).sendKeys("0.3");
        driver.findElement(By.id(addNewFieldCheckId)).click();
    }
}