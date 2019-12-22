package Tests;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

//import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import ReadingDataInputFromExcel.TestDrivenData;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
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
	public void BookAride(){
		
		//Reading the input data to use in the testcase from an excelsheet
		TestDrivenData  objExcelFile = new TestDrivenData ();
        //Prepare the path of excel file
       String filePath = System.getProperty("user.dir")+"C:\\Users\\shaimaa.sadek\\eclipse-workspace\\appiumtestproject";

	    //Call read file method of the class to read data

	    objExcelFile.readExcel(filePath,"SwvlData.xlsx","SwvlSheet");
	    //as i previously defined a list of strings getting all cells data into list
	    //Define string to the fetched data cells from the testdriven class
	    String Countrytext = SwvlData[2];
	    String MobileNumber = SwvlData[0];
	    String Password = SwvlData[1];
	    String DestinedLocation = SwvlData[3];
	    
	   
		//1)Sign in to the application
		driver.findElement(By.id("hint")).click();
		MobileElement el3 = (MobileElement) driver.findElement(By.id("io.swvl.customer:id/hint"));
		el3.click();
		
		MobileElement el4 = (MobileElement) driver.findElementById("io.swvl.customer:id/arrow_image_view");
		el4.click();
		
		MobileElement el5 = (MobileElement) driver.findElementById("io.swvl.customer:id/search_et");
		el5.click();
		
		el5.sendKeys(Countrytext);
		MobileElement el6 = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[2]/android.widget.TextView[1]");
		el6.click();
		MobileElement el7 = (MobileElement) driver.findElementById("io.swvl.customer:id/phone_edit_text");
		el7.click();
		el7.sendKeys(MobileNumber);
		MobileElement el8 = (MobileElement) driver.findElementById("io.swvl.customer:id/auth_constraint_layout");
		el8.click();
		MobileElement el9 = (MobileElement) driver.findElementById("io.swvl.customer:id/next_btn");
		el9.click();
		MobileElement el10 = (MobileElement) driver.findElementById("io.swvl.customer:id/next_btn");
		el10.click();
		MobileElement el12 = (MobileElement) driver.findElementById("io.swvl.customer:id/password_edit_text");
		el12.sendKeys(Password);
		MobileElement el13 = (MobileElement) driver.findElementById("io.swvl.customer:id/next_btn");
		el13.click();
		
		//assert that the field "where to" appeared to verify successful login
		MobileElement wheretoel = (MobileElement) driver.findElementById("io.swvl.customer:id/where_to");
		if(wheretoel.getText()=="Where to?") {
			//indicating no issues with login
		
		MobileElement el1 = (MobileElement) driver.findElementById("io.swvl.customer:id/where_to");
		el1.click();
		MobileElement el2 = (MobileElement) driver.findElementById("io.swvl.customer:id/dropoff_et");
		el2.click();
		el2.sendKeys(DestinedLocation); //maadi as in the excel file
		
		
		boolean waitForPresence (AndroidDriver driver, int timeLimitInSeconds, String targetResourceId){
			
	  //locate the SearchResult list and wait until it is displayed
	 	MobileElement ListOfLocationResults = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout");
		WebDriverWait wait = new WebDriverWait(driver,timeLimitInSeconds);
		wait.until(ExpectedConditions.visibilityOf(ListOfLocationResults));
		
		}
		
		ListOfLocationResults.click();
		//Verify each result contains the text searched with " Maadi "
		//Loop over each index within the view group and assert that it contains "Maadior the text fetched from the excel"->seperate testcase
		MobileElement ViewGroupListOfResults= (MobileElement) driver.findElementByClassName("android.view.ViewGroup");
		//Fetching records from the list by indeces 
		String firstrestext = ViewGroupListOfResults.findElementByResourceId("io.swvl.customer:id/title_tv").get(0);
		String secondestext = ViewGroupListOfResults.findElementByResourceId("io.swvl.customer:id/title_tv").get(1);
		
		assertTrue(firstrestext.contains(DestinedLocation));
		assertTrue(secondrestext.contains(DestinedLocation));
		
		//Click on the first result suggested
		//Confirm he drop off 
		MobileElement ConfirmDropOff = (MobileElement) driver.findElementById("io.swvl.customer:id/done_btn");
		ConfirmDropOff.click();
		
		//Wait until the list of available trips is displayed 
		boolean waitForPresence (AndroidDriver driver, int timeLimitInSeconds, String targetResourceId){
			
			  //locate the trips list and wait until it is displayed
			 	MobileElement ListOfavailabletrips = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout");
				WebDriverWait wait = new WebDriverWait(driver,timeLimitInSeconds);
				wait.until(ExpectedConditions.visibilityOf(ListOfavailable));
				
				}
		MobileElement ViewGroupListOftrips = (MobileElement) driver.findElementByClassName("android.view.ViewGroup");
		//Fetching records from the list by indeces 
		MobileElement FirstAvailableBooking= ViewGroupListOftrips.findElementByResourceId("io.swvl.customer:id/ride_card_view").get(0);
		FirstAvailableBooking.click();
		MobileElement Next =(MobileElement) driver.findElementById("io.swvl.customer:id/next_btn");

		//Taking into consideration the tc covers only one person in the ride/no promo codes.
		MobileElement BookAction =(MobileElement) driver.findElementById("io.swvl.customer:id/Book_btn");
		
		//Assert on the outputmessage stating trip is successfully booked to ensure tcs passed
		MobileElement SuccessfulBookingMessage = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.widget.TextView\r\n" + 
				"");
		
		String AsserttextActual = SuccessfulBookingMessage.getText();
		AssertEquals(AsserttextActual,"Trip Successfully Booked");
		
		
		}
		
		else
		{
			System.out.println("non successful login cannot execute the tc");
		}
		
	
		//
	}
		
	@Test
	public boolean CancelAride()
	{
		MobileElement ExpandMenu = (MobileElement) driver.findElementById("io.swvl.customer:id/menu_iv");
		ExpandMenu.click();
		
		MobileElement BookedTrips = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.FrameLayout/androidx.recyclerview.widget.RecyclerView/androidx.appcompat.widget.P[1]/android.widget.CheckedTextView");
		BookedTrips.click();
		
		//bydefault it select first 0 index
		MobileElement OpenFirstBookedTrip = (MobileElement) driver.findElementByClassName("io.swvl.customer:id/ride_card_view");
		OpenFirstBookedTrip.click();
		
		//swipe the trip details view to the upper direction
		public void HorizontalSwipeByPer(double startPercentage, double endPercentage, double anchorPercentage)
		{
	        Dimension size = driver.manage().window().getSize();
	        int anchor = (int) (size.height * anchorPercentage);
	        int startPoint = (int) (size.width * startPercentage);
	        int endPoint = (int) (size.width * endPercentage);
	 
	        new TouchAction(driver)
	                .press(point(startPoint, anchor))
	                .waitAction(waitOptions(ofMillis(1000)))
	                .moveTo(point(endPoint, anchor))
	                .release().perform();
	        
	        //verify cancel button is present to confirm swipe is done 
	        MobileElement CancelButton = (MobileElement) driver.findElementById("io.swvl.customer:id/cancel_booking_iv");
	        CancelButton.click();
	        
	        MobileElement ConfirmCancellation = (MobileElement) driver.findElementById("io.swvl.customer:id/confirm_btn");
	        ConfirmCancellation.click();
	        
	        MobileElement feedback = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.widget.TextView[1]");
	       feedback.click();
	       
	       MobileElement ElmentinResetPage = (MobileElement) driver.findElementById("io.swvl.customer:id/upcoming_btn");

	       //verify system has navigated the user to the trips page
	       
	    if(ElmentinResetPage.isDisplayed())
	    {
	    	return true;
	    }
	    else
	    {
	    	return false;
	    }
	    }
		
		
		
	}
	
	
	@AfterTest
	public void Teardown()
	{
		
	}
	
	}
	

