package iot_test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import testUtils.Commons;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class TestIOT extends Commons {

	@Test // Verify the Iotium text and the logo
	public void validateLogo() throws IOException, InterruptedException {
		// Get Logo urls from website
		List<WebElement> logos = driver.findElements(By.xpath("//span[@class='logo']/descendant::img"));

		// Capture image from the website using BufferedImage || Src Images
		BufferedImage baseLogo = getImageFromUrl(logos.get(0).getAttribute("src"));
		BufferedImage alternateLogo = getImageFromUrl(logos.get(1).getAttribute("src"));

		//Load image from drive to compare || Target Images
		BufferedImage targetBaseLogo = getImageFromFile("./logos/iotiumlogo.png");
		BufferedImage targetAlternateLogo = getImageFromFile("./logos/Iotium-Logo-White.png");

		// Assert if source and Target images are exactly matching
		Assert.assertEquals(compareImage(baseLogo, targetBaseLogo), 100,"Base Logo mismatch");
		Assert.assertEquals(compareImage(alternateLogo, targetAlternateLogo), 100, "Alternate Logo mismatch");
	}

	@Test // Verify ‘Solutions’ as hyperlink is available and on click it lists ‘OT-Net’
	public void validateSolutions(){
		// Verify Solution element is available
		List<WebElement> ele_Solution = listElementsFromXpath("//span[text()='Solutions']");
		Assert.assertEquals(ele_Solution.size(),1, "Element not found");

		// Verify OT-Net element is available
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(ele_Solution.get(0)));
		ele_Solution.get(0).click();
		Assert.assertEquals(listElementsFromXpath("//span[text()='OT-Net']").size(),1,"Element not found");
	}

	@Test // Verify cookies in the footer, and on clicking ‘Learn more’, the pop-up contains the string ‘Cookie and Privacy Settings’
	public void validateCookieFooter(){
		// Verify Cookie element is available
		Assert.assertEquals(listElementsFromXpath("//div[@class='avia-cookie-consent avia-cookiemessage-bottom']").size(),
				1, "Cookie Footer section not found");

		// Verify "Cookie and Privacy Settings" header is available in the pop up
		Assert.assertEquals(listElementsFromXpath("//h3[text()='Cookie and Privacy Settings']").size(),1,
				"Cookie and Privacy Settings header is present");

	}
}