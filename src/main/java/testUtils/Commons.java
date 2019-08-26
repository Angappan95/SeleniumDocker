package testUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
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

	@BeforeTest
	@Parameters("browser")
	public void setUp(String browser) throws Exception {
		if(browser.toLowerCase().contentEquals("chrome")) {
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("version","");
			capabilities.setPlatform(Platform.LINUX);
			ChromeOptions options = new ChromeOptions();
//			options.setHeadless(true);
			options.addArguments("window-size=1920,1080");
			options.merge(capabilities);
			driver = new RemoteWebDriver(new URL("http://35.209.196.86:4444/wd/hub"),options);
		}
		else if (browser.toLowerCase().contentEquals("firefox")){
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setBrowserName("firefox");
			capabilities.setPlatform(Platform.LINUX);
			FirefoxOptions options = new FirefoxOptions();
//			options.setHeadless(true);
//			options.addArguments("window-size=1920,1080");
			options.merge(capabilities);
			driver = new RemoteWebDriver(new URL("http://35.209.196.86:4444/wd/hub"),options);
		}
		else{
			System.out.println("Invalid Browser selection.");
		}

		baseUrl = "https://www.iotium.io/";
		driver.manage().window().maximize();
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@AfterTest
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