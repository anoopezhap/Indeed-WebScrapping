package indeed.PageObjects;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.print.DocFlavor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public class pages {
    public WebDriver driver;

    public  pages(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }


    @FindBy(id = "text-input-what")
    WebElement jobTitle;

    @FindBy(id ="text-input-where")
    WebElement jobLocation;

    @FindBy(xpath = "//button[@type=\"submit\"]")
    WebElement search;

    @FindBy(id ="filter-dateposted" )
    WebElement filterByDataPosted;

    @FindBy(xpath = "//a[text()='Last 3 days']")
    WebElement selectDateFilter;

    @FindBy(xpath ="//div[contains(@class,'metadataContainer')]//parent::td[@class=\"resultContent\"]//span[contains(@id,'jobTitle')]" )
    List<WebElement> jobTitles;

    @FindBy(xpath ="//div[contains(@class,'metadataContainer')]//preceding-sibling::div[contains(@class,'companyInfo')]//span[@class=\"companyName\"]" )
    List<WebElement> companyNames;

    @FindBy(xpath ="//div[contains(@class,'metadataContainer')]//preceding-sibling::div[contains(@class,'companyInfo')]//div[@class=\"companyLocation\"]" )
    List<WebElement> locations;

    @FindBy(xpath = "//div[contains(@class,'metadataContainer')]")
    List<WebElement> metaDatas;

    @FindBy(xpath = "//div[contains(@class,'metadataContainer')]//preceding::div[@class=\"job_seen_beacon\"]//span[@class=\"date\"]")
    List<WebElement> postedDates;

    @FindBy(xpath = "//div[contains(@class,'metadataContainer')]//parent::td[@class=\"resultContent\"]//a")
    List<WebElement> jobLinks;

    @FindBy(xpath ="//a[@data-testid=\"pagination-page-next\"]")
    WebElement nextPage;
    @FindBy(xpath = "//nav[@role=\"navigation\"]//div")
    List<WebElement> noOfPages;


    public void enterDetails(String title, String location)
    {
        jobTitle.sendKeys(title);
        jobLocation.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
        jobLocation.sendKeys(location);
        search.click();
    }

    public int filterByDatePosted(String option)
    {
        filterByDataPosted.click();
        filterByDataPosted.findElement(By.xpath("//a[text()="+option+"]")).click();
        return jobTitles.size();
    }

    public void closePopup() throws InterruptedException
    {
        Actions action = new Actions(driver);
        Thread.sleep(3000);
        action.click().build().perform();

    }

    public String[][] getData(int size)
    {
        int count =0;
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        do {
            for (int i = 0; i < jobTitles.size() - 1; i++) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", jobLinks.get(i));
                ArrayList<String> tempData = new ArrayList<>();
                tempData.add(jobTitles.get(i).getText());
                tempData.add(companyNames.get(i).getText());
                tempData.add(locations.get(i).getText());
                tempData.add( metaDatas.get(i).getText());
                tempData.add(postedDates.get(i).getText());
                tempData.add(jobLinks.get(i).getAttribute("href"));
                data.add(tempData);
            }
            if (noOfPages.size() >= 5) {
                nextPage.click();
            }
            count++;
        }while (noOfPages.size()>0 && count<4 && nextPage.isDisplayed());

        String newData[][] = new String[data.size()][6];
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(0).size(); j++) {
                newData[i][j] = data.get(i).get(j);
            }
        }
        return newData;
    }

    public String[][] cleanData(String[][] data, int noOfRecords)
    {
        String newData[][] = new String[noOfRecords][6];
        newData = Arrays.stream(data).map(String[]::clone).toArray(String[][]::new);


        for(int i=0;i<data.length-1;i++)
        {
            String temp = data[i][4];
            String data2 = temp.replace("\"","");
            String input = data2.replace("\n"," ");
            String split[] = input.split(" ");
            LinkedHashSet<String> set = new LinkedHashSet<>();
            for(String s : split)
            {
                set.add(s);
            }
            String a = set.toString();
            String b = a.replace("[","").replace("]","").replace(",","");
            newData[i][4] = b;

//            String tempLink = data[i][5];
//            String newLink = UrlShortener.urlShortener(tempLink);
//            newData[i][5] = newLink;
        }
        return newData;

    }

    public void writeDataToExcel(String data[][]) throws IOException
    {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Scrapped Data");
        String header[] = {"JOB TITLE","COMPANY","LOCATION" ,"KEY POINTS","POSTED DATE","LINK"};
        XSSFCellStyle style1 =workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Courier New");
        font.setBold(true);
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style1.setFont(font);
        XSSFRow row = sheet.createRow(0);
        for(int i=0;i<header.length;i++)
        {
            XSSFCell cell = row.createCell(i);
            cell.setCellStyle(style1);
            cell.setCellValue(header[i]);
        }

        int rowCount = 1;
        int colCount=0;
        for(String[] s : data)
        {
            XSSFRow rows = sheet.createRow(rowCount++);
            for(String c : s)
            {
                XSSFCell cells = rows.createCell(colCount++);
                cells.setCellValue(c);
            }
            colCount=0;
        }
        String filePath = System.getProperty("user.dir") + ".\\data.xlsx";
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
    }
}
