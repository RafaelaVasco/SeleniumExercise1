import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.RandomEmail;

import java.io.File;
import java.io.IOException;

public class Exercise extends Base {
    @BeforeTest
    public void initialize() {
        driver = initializeDriver();
        driver.get("https://demoqa.com/automation-practice-form");
        removeAdds();
    }

    @Test
    public void test() {
        // >>> First name
        WebElement firstName_input = driver.findElement(By.id("firstName"));
        firstName_input.sendKeys("Rafaela");

        // >>> Last name
        WebElement lastName_input = driver.findElement(By.id("lastName"));
        lastName_input.sendKeys("Vasconcellos");

        // >>> Email
        WebElement email_input = driver.findElement(By.id("userEmail"));
        email_input.sendKeys(RandomEmail.randomEmail());

        // >>> Gender
        WebElement genderFemale_label = driver.findElement(By.xpath("//*[text() = 'Female']"));
        genderFemale_label.click();

        // >>> Mobile number
        WebElement mobileNumber_input = driver.findElement(By.id("userNumber"));
        mobileNumber_input.sendKeys("1234567890");

        // >>> Date of Birth
        WebElement dateOfBirth_input = driver.findElement(By.id("dateOfBirthInput"));
        dateOfBirth_input.click();
        driver.switchTo().activeElement();

        /* month */
        Select month_select = new Select(driver.findElement(By.className("react-datepicker__month-select")));
        month_select.selectByVisibleText("July");

        /* year */
        Select year_select = new Select(driver.findElement(By.className("react-datepicker__year-select")));
        year_select.selectByVisibleText("1991");

        /* day */
        WebElement day_option = driver.findElement(By.className("react-datepicker__day--002"));
        day_option.click();

        // >>> Subject
        WebElement subject_input = driver.findElement(By.id("subjectsInput"));
        subject_input.sendKeys("Mat");

        /* select Maths */
        WebElement subjectOptions_list = driver.findElement(By.className("subjects-auto-complete__menu"));
        WebDriverWait wait_subjectOptions = new WebDriverWait(driver, 5);
        wait_subjectOptions.until(ExpectedConditions.visibilityOf(subjectOptions_list));
        WebElement subjectMaths_option = driver.findElement(By.id("react-select-2-option-0"));
        subjectMaths_option.click();

        /* select Computer Science */
        subject_input.click();
        subject_input.sendKeys("Computer");
        WebElement subjectOptions_list2 = driver.findElement(By.className("subjects-auto-complete__menu"));
        wait_subjectOptions.until(ExpectedConditions.visibilityOf(subjectOptions_list2));
        WebElement subjectComputerScience_option = driver.findElement(By.id("react-select-2-option-0"));
        subjectComputerScience_option.click();

        // >>> Hobbies
        WebElement hobbies_checkbox = driver.findElement(By.xpath("//*[@id=\"hobbiesWrapper\"]/div[2]/div[2]/label"));
        hobbies_checkbox.click();

        // >>> Picture
        WebElement chooseFile_button = driver.findElement(By.id("uploadPicture"));
        File file = new File("src/test/resources/flowers.jpeg");
        chooseFile_button.sendKeys(file.getAbsolutePath());

        // >>> Current Address
        WebElement currentAddress_textarea = driver.findElement(By.id("currentAddress"));
        currentAddress_textarea.sendKeys("Street 1234");

        // >>> State
        WebElement state_select = driver.findElement(By.id("state"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", state_select);
        state_select.click();

        driver.switchTo().activeElement();
        WebElement state_option = driver.findElement(By.xpath("//*[text() = 'Uttar Pradesh']"));
        state_option.click();

        // >>> City
        WebElement city_select = driver.findElement(By.id("city"));
        city_select.click();

        driver.switchTo().activeElement();
        WebElement city_option = driver.findElement(By.xpath("//*[text() = 'Agra']"));
        city_option.click();

        // >>> Submit Button
        /* delete footer to be able to see the Submit button */
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementsByTagName('footer')[0].remove();");

        WebElement submit_button = driver.findElement(By.id("submit"));
        submit_button.click();

        driver.switchTo().activeElement();
        String expectedMessage = "Thanks for submitting the form";
        String successMessage_text = driver.findElement(By.id("example-modal-sizes-title-lg")).getText();

        System.out.println("Was it successful? >>> " + successMessage_text.equals(expectedMessage));
        Assert.assertTrue(successMessage_text.equals(expectedMessage));
    }

    private void removeAdds() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelectorAll('iframe')\n" +
                "  .forEach(iframe => iframe.remove());");
        js.executeScript("document.querySelectorAll('[id^=' + 'google_ads_iframe_' + ']')\n" +
                "  .forEach(iframe => iframe.remove());");
        js.executeScript("document.querySelectorAll('[id^=' + 'adplus-anchor' + ']')\n" +
                "  .forEach(iframe => iframe.remove());");
        js.executeScript("document.getElementById('fixedban').remove();");
    }

    @AfterTest
    public void closeDriver() {
        driver.close();
    }
}
