package testUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Commons {

	public static WebDriver driver;
	private String baseUrl;

	@BeforeMethod
	@Parameters("browser")
	public void setUp(String browser ) throws Exception {

		if(browser.toLowerCase() == "chrome") {
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("version","");
			capabilities.setPlatform(Platform.LINUX);
			driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"),capabilities);
		}
		else{
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("version","");
			capabilities.setPlatform(Platform.LINUX);
			driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"),capabilities);
		}

		baseUrl = "https://www.iotium.io/";
		driver.manage().window().maximize();
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}


	public int compareImage(BufferedImage src, BufferedImage target) throws IOException {
		int percentage = 0;

		DataBuffer bufferInput = src.getData().getDataBuffer();
		int sizefileInput = bufferInput.getSize();

		DataBuffer datafileOutPut = target.getData().getDataBuffer();
		int sizefileOutPut = datafileOutPut.getSize();

		int count = 0;
		if (sizefileInput == sizefileOutPut) {
			for (int i = 0; i < sizefileInput; i++) {
				if (bufferInput.getElem(i) == datafileOutPut.getElem(i)) {
					count = count + 1;
				}
			}
			percentage = (count * 100) / sizefileInput;
		}
		else
			System.out.println("Size of the image are different");
		System.out.println("Image match in %:" + percentage);
		return percentage;
	}


	public BufferedImage getImageFromUrl(String urlString) throws IOException {
		URL url = new URL(urlString);
		return ImageIO.read(url);
	}
	
	public BufferedImage getImageFromFile(String path) throws IOException {
		return ImageIO.read(new File(path));
	}

	public List<WebElement> listElementsFromXpath(String xpath){
		return driver.findElements(By.xpath(xpath));
	}
}