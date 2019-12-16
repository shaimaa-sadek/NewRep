package Tests;

//import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseClass {
	
	//Initialize an appium driver

	 AppiumDriver<MobileElement> driver;
	 
	@BeforeTest
	public void setup()
	{
		try {
			
	
	   //File app = new File("D:\\", "Swvl Bus Booking App_v5.7.1_apkpure.com.apk");
	
		DesiredCapabilities caps = new DesiredCapabilities();
		//caps.setCapability("platformName", "Android");
		caps.setCapability(CapabilityType.PLATFORM_NAME, "ANDROID");
		caps.setCapability(CapabilityType.VERSION,"7.0");
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
		//caps.setCapability("app", app.getAbsolutePath());
		
		caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
		
		//defining the package of swvl
		caps.setCapability("app-package", "io.swvl.customer");
		 //set the Launcher activity name of the app
		 caps.setCapability("app-activity", "io.swvl.customer.features.LaunchScreenActivity");
		 

		
		URL url =new URL ("http://127.0.0.1:4723/wd/hub");
		 driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		
		driver = new AppiumDriver<MobileElement>(url,caps);
		driver = new AndroidDriver<MobileElement>(url,caps);
		//driver = new IOSDriver<MobileElement>(url,caps);
		
		
		
		}catch(Exception exp)
		{
			System.out.println("Cause is:"+exp.getCause());
			System.out.println("Message is:"+exp.getMessage());
		}
	}
	@Test
	public void SignIN()
	{
		string MN= driver.findElement(By.id("mobilenumber"));
		driver.findElement(By.id("mobilenumber")).sendKeys("01281637951");
		//Sign In 
		//Insert Where To 
		//Insert From 
		//Assert on the Retrieved ride having the same location"text , by map longitude/latitude regions"
		
		//
		System.out.println("inside swvl");
		
	}
	@AfterTest
	public void Teardown()
	{
		
	}
	
}
