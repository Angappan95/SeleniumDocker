package iot_test;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainOne {
    public static void main(String[] args) {

        // Generate Testng xml dynamically
        XmlSuite suite = new XmlSuite();
        suite.setName("Iot_Test_Suite");

        // Create a Test suite for Chrome and add it to Suite
        XmlTest chromeTest = new XmlTest(suite);
        chromeTest.setName("Test_Chrome");
        chromeTest.setParameters(new HashMap<String, String>() {{
            put("browser", "chrome");
        }});
        List<XmlClass> chromeClasses = new ArrayList<>();
        //Add a Test Class
        chromeClasses.add(new XmlClass("iot_test.TestIOT"));
        chromeTest.setXmlClasses(chromeClasses);

        // Create a Test suite for Firefox and add it to suite
        XmlTest firefoxTest = new XmlTest(suite);
        firefoxTest.setName("Test_Firefox");
        firefoxTest.setParameters(new HashMap<String, String>() {{
            put("browser", "firefox");
        }});
        List<XmlClass> firefoxClasses = new ArrayList<>();
        //Add a Test Class
        firefoxClasses.add(new XmlClass("iot_test.TestIOT"));
        firefoxTest.setXmlClasses(firefoxClasses);

        // Use the generated Testng xml to run the suite
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.run();

    }
}
