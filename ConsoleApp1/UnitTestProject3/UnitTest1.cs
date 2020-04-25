using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.Support.UI;
using OpenQA.Selenium;
using NUnit;
using NUnit.Framework;
using Excel = Microsoft.Office.Interop.Excel;
using Microsoft.CSharp;




namespace FirstTask
{
    [TestFixture]
    public class UnitTest1
    {
       
        public IWebDriver driver;
        public WebDriverWait wait;
        public IWebElement dropdown;
       
        [SetUp]
        public void initial()
        {
             
            driver = new ChromeDriver();
            driver.Manage().Window.Maximize();
            wait = new WebDriverWait(driver, TimeSpan.FromSeconds(50));
        }

        [Test]
        public void SignUpToApp()
        {

            List<string> userRegData = new List<string> { };
            var ExcelFilePath = @"C:\Users\shaimaa.sadek\source\repos\ConsoleApp1\UnitTestProject3\FullRegData.xlsx";

            //specifying which workbook , worksheet and used rows

            Excel.Application xlApp = new Excel.Application();
            Excel.Workbook xlWorkbook = xlApp.Workbooks.Open(ExcelFilePath);
            Excel._Worksheet xlWorksheet = xlWorkbook.Sheets[1];
            Excel.Range xlRange = xlWorksheet.UsedRange;


            int rowCount = xlRange.Rows.Count;
            int colCount = xlRange.Columns.Count;
            for (int i = 2; i <= rowCount; i++)
            {
                for (int j = 1; j <= colCount; j++)
                {
                    userRegData.Add(xlRange.Cells[i, j].Value2.ToString());
                }
                //defining the excel row data
                //Calling the parameters from the POM
              
                string firstName = userRegData[0];
                string LastName = userRegData[1];
                string Mobile = userRegData[2];
                string Email = userRegData[3];
                string Password = userRegData[4]; 
                string ConfirmingPassword = userRegData[5];
                
                //fetch the data from the excel to the fields
                driver.Navigate().GoToUrl("http://www.phptravels.net/");
                wait = new WebDriverWait(driver, TimeSpan.FromSeconds(10));
                //click on my account drop down list  select signup
                driver.FindElement(By.Name("firstname")).SendKeys(firstName);
                driver.FindElement(By.Name("lastname")).SendKeys(LastName);
                driver.FindElement(By.Name("phone")).SendKeys(Mobile);
                driver.FindElement(By.Name("email")).SendKeys(Email);
                driver.FindElement(By.Name("password")).SendKeys(Password);
                driver.FindElement(By.Name("confirmpassword")).SendKeys(ConfirmingPassword);


                driver.FindElement(By.ClassName("signupbtn btn_full btn btn-success btn-block btn-lg")).Click();
                wait = new WebDriverWait(driver, TimeSpan.FromSeconds(10));

                //assert on the Successfuly signup image if its displayed then user is redirected to his profile.
                var UserProfileImage = driver.FindElement(By.ClassName("img-responsive go-right img-thumbnail"));
               
                NUnit.Framework.Assert.That(UserProfileImage, !Is.Null);
            }
        }
        


        
        [Test]
        public void NonMatchingPasswords()
        {

            List<string> userRegData = new List<string> { };
            var ExcelFilePath = @"C:\Users\shaimaa.sadek\source\repos\ConsoleApp1\UnitTestProject3\testusersdata.xlsx";

            //specifying which workbook , worksheet and used rows

            Excel.Application xlApp = new Excel.Application();
            Excel.Workbook xlWorkbook = xlApp.Workbooks.Open(ExcelFilePath);
            Excel._Worksheet xlWorksheet = xlWorkbook.Sheets[1];
            Excel.Range xlRange = xlWorksheet.UsedRange;


            int rowCount = xlRange.Rows.Count;
            int colCount = xlRange.Columns.Count;
            //Reading Row number 3
            for (int i = 3; i <= rowCount; i++)
            {
                //moving across each column
                for (int j = 1; j <= colCount; j++)
                {
                    userRegData.Add(xlRange.Cells[i, j].Value2.ToString());

                }
            //defining the excel row data
            string firstName = userRegData[0];
            string LastName = userRegData[1];
            string Mobile = userRegData[2];
            string Email = userRegData[3];
            string Password = userRegData[4];
            string WrongPassword = userRegData[6];

            //fetch the data from the excel to the fields
            driver.Navigate().GoToUrl("http://www.phptravels.net/");
            wait = new WebDriverWait(driver, TimeSpan.FromSeconds(10));
            //click on my account drop down list  select signup
            driver.FindElement(By.Name("firstname")).SendKeys(firstName);
            driver.FindElement(By.Name("lastname")).SendKeys(LastName);
            driver.FindElement(By.Name("phone")).SendKeys(Mobile);
            driver.FindElement(By.Name("email")).SendKeys(Email);
            driver.FindElement(By.Name("password")).SendKeys(Password);
            driver.FindElement(By.Name("confirmpassword")).SendKeys(WrongPassword);

                var Errormsg = driver.FindElement(By.ClassName("alert alert-danger"));
                
                NUnit.Framework.Assert.That(Errormsg.Text, Does.Contain("Password not matching with confirm password."));
                




            }

          
        }

        [Test]
        public void TestwrongemailFormat()
        {

        }
    }
}
