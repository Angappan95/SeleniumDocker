package iot_test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import testUtils.Commons;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class TestIOT extends Commons {

	@Test
	public void validateLogo() throws IOException, InterruptedException {
		List<WebElement> logos = driver.findElements(By.xpath("//span[@class='logo']/descendant::img"));

		BufferedImage baseLogo = getImageFromUrl(logos.get(0).getAttribute("src"));
		BufferedImage alternateLogo = getImageFromUrl(logos.get(1).getAttribute("src"));

		BufferedImage targetBaseLogo = getImageFromFile("./logos/iotiumlogo.png");
		Assert.assertEquals(compareImage(baseLogo, targetBaseLogo), 100,"Base Logo mismatch");

		BufferedImage targetAlternateLogo = getImageFromFile("./logos/Iotium-Logo-White.png");
		Assert.assertEquals(compareImage(alternateLogo, targetAlternateLogo), 100, "Alternate Logo mismatch");

	}

	@Test
	public void validateSolutions(){
		List<WebElement> ele_Solution = listElementsFromXpath("//span[text()='Solutions']");
		Assert.assertEquals(ele_Solution.size(),1, "Element not found");
		ele_Solution.get(0).click();

		Assert.assertEquals(listElementsFromXpath("//span[text()='OT-Net']").size(),1,"Element not found");

	}

	@Test
	public void cookieFooter(){
		Assert.assertEquals(listElementsFromXpath("//div[@class='avia-cookie-consent avia-cookiemessage-bottom']").size(),
				1, "Cookie Footer section not found");

		Assert.assertEquals(listElementsFromXpath("//h3[text()='Cookie and Privacy Settings']").size(),1,
				"Cookie and Privacy Setting header is present");

	}
}