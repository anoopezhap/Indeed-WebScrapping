package indeed;

import base.TestBase;
import indeed.PageObjects.pages;
import org.testng.annotations.Test;

import java.io.IOException;

public class IndeedScrapping extends TestBase {


    @Test
    public void scrapeIndeed() throws InterruptedException, IOException {
        pages pages = new pages(driver);

        String jobTitle = System.getProperty("jobTitle") !=null ? System.getProperty("jobTitle") : prop.getProperty("jobTitle");
        String jobLocation = System.getProperty("jobLocation") !=null ? System.getProperty("jobLocation") : prop.getProperty("jobLocation");

        pages.enterDetails(jobTitle,jobLocation);
        int noOfRecords = pages.filterByDatePosted(prop.getProperty("sortBy"));
        pages.closePopup();
        String[][] data = pages.getData(noOfRecords);
        String[][] cleanedData = pages.cleanData(data,noOfRecords);
        pages.writeDataToExcel(cleanedData);
    }
}
