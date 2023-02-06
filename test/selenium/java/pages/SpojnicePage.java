package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import utility.spojnice.SpojniceData;
import utility.spojnice.SpojniceDataModel;

import java.util.ArrayList;
import java.util.List;

public class SpojnicePage extends TestBase {
    //Locator object for game headline text.
    By headlineLoc = new By.ById("headline");
    //Locator object for table element body.
    By tableBodyLoc = new By.ById("tbody");
    //Locator object for submit button.
    By submitBtnLoc = new By.ById("submitBtn");
    //Locator object used for td elements in table.
    By tableCellsLocator = new By.ByXPath("//*[@id='tbody']//child::button");
    //List of objects that contain data for game. One objects matches data for current game.
    List<SpojniceDataModel> dataList;
    //Lists of String values for each column of word buttons.
    List<String> column1Words;
    List<String> column2Words;
    //Object that stores data used for game.
    SpojniceDataModel gameData;
    //Lists of web elements for each column of word buttons.
    List<WebElement> column1Btns;
    List<WebElement> column2Btns;
    //Control variable used to indicate expected alert didn't appear.
    boolean noAlertOnSubmit;
    //List of String arrays made up of two elements (game data values, starting with a number, that match user picks from each column). Used for calculating game points.
    List<String[]> userPicksList = new ArrayList<>();
    //Lists of strings that contain user selected text from each column correspondingly.
    List<String> pickedFromColumn1 = new ArrayList<>();
    List<String> pickedFromColumn2 = new ArrayList<>();

    //Constructor.
    public SpojnicePage() {
        super();
        //Initializing game data object.
        this.gameData = new SpojniceDataModel();
        //Initializing list of data Objects that contains all possible data for game, read from CSV file.
        this.dataList = SpojniceData.readData();
    }

    public void verifyTitlesAfterOpen(String expectedPageTitle, String expectedContentTitle) {
        this.setConfigTitle(expectedPageTitle);
        this.verifyMethods.verifyPageTitle();
        this.verifyMethods.verifyH1Content(expectedContentTitle);
    }

    //Checks if columns with buttons are displayed. Verifies data in buttons with words. Initializes lists of Web elements and String values for each column.
    public void verifyTableContent() {
        //Initializing lists of words and button elements.
        column1Words = new ArrayList<>();
        column2Words = new ArrayList<>();
        column1Btns = new ArrayList<>();
        column2Btns = new ArrayList<>();
        //int variable used as index to determine in which column an element belongs.
        int i = 1;
        waitForVisibilityOf(tableBodyLoc);
        //Added as workaround for bug regarding buttons and headline not being displayed on game page.
        try {
            //List of all WebElements with tag name <td> that have table body element as parent.
            List<WebElement> tableCellsEl = findAll(tableCellsLocator);
            wait.until(ExpectedConditions.visibilityOfAllElements(tableCellsEl));
            Reporter.log("Verifying all buttons for picking pairs are enabled.");
            System.out.println("Verifying all buttons for picking pairs are enabled.");
            //Looping over each button element in list.
            for (WebElement el : tableCellsEl) {
                //All buttons with odd index values belong to column1.
                if (i % 2 != 0) {
                    //Adding WebElement and String object to corresponding lists.
                    column1Btns.add(el);
                    column1Words.add(el.getText());
                } else {
                    //Adding WebElement and String object with even index value to corresponding lists.
                    column2Btns.add(el);
                    column2Words.add(el.getText());
                }
                //Checking if element is enabled.
                Assert.assertTrue(el.isEnabled(), "Button not enabled!");
                i++;
            }
            Reporter.log("All buttons are enabled.");
            System.out.println("All buttons are enabled.");
            Reporter.log("Verifying data in columns.");
            System.out.println("Verifying data in columns.");
            verifyColumnData();
        } catch (Exception e) {
            System.err.println("Game error! Columns with word buttons not displayed!");
        }
    }

    public void verifyGameHeadline() {
        //Control variable.
        boolean isValid = false;
        //Added as workaround for bug regarding buttons and headline not being displayed on game page.
        try {
            waitForVisibilityOf(headlineLoc);
            String headlineText = find(headlineLoc).getText();
            //Looping over data objects to check if one of them contains generated headline text.
            for (SpojniceDataModel modelObject : dataList) {
                //If headline class variable of SpojniceDataModel object from data list is equal to the generated headline on game UI isValid control, variable is set to true.
                isValid = modelObject.getHeadline().equals(headlineText);
                if (isValid) {
                    //Object with game data is set to object with matching headline value.
                    this.gameData = modelObject;
                    //Once matching object is found it si safe to break the cycle.
                    break;
                }
            }
            Reporter.log("Verifying game headline.");
            System.out.println("Verifying game headline.");
            Assert.assertTrue(isValid, "Game headline: " + headlineText + " doesn't match expected data.");
            Reporter.log("Game headline: " + headlineText + " verified.");
            System.out.println("Game headline: " + headlineText + " verified.");
        } catch (Exception e) {
            System.err.println("Game error! Headline is not displayed!");
        }
    }

    //Checks if data displayed on game UI matches data in gameData object.
    public void verifyColumnData() {
        //Variable used as index value.
        int i = 0;
        for (String word : column1Words) {
            Assert.assertTrue(gameData.getColumn1().get(i).contains(word), "Word: " + word + " doesn't match expected data.");
            i++;
        }
        i = 0;
        for (String word : column2Words) {
            Assert.assertTrue(gameData.getColumn2().get(i).contains(word), "Word: " + word + " doesn't match expected data.");
            i++;
        }
        Reporter.log("Words in button columns match expected data.");
        System.out.println("Words in button columns match expected data.");
    }

    public void clickSubmitBtn() {
        noAlertOnSubmit = false;
        this.waitForElToBeClickable(submitBtnLoc);
        click(submitBtnLoc);
        //Added to handle exception that appears when alert is not displayed.
        try {
            this.dealWithAlert();
        } catch (Exception e) {
            noAlertOnSubmit = true;
            System.err.println("Submit button alert not displayed!");
        }
    }

    public TestBase pickGoBackMethod() {
        //Sometimes alert on submit button is not displayed. If that is the case noAlertOnSubmit will equal true.
        if (noAlertOnSubmit) {
            //Resetting control variable.
            this.noAlertOnSubmit = false;
            return this.goBack();
        } else {
            //This method is called instead of goBack method because if Submit button is clicked alert on go back button is not displayed.
            return this.goBackAfterGameFinished();
        }
    }

    //Selects random value from column with text buttons.
    public void clickRandomButtonFromColumn(List<WebElement> btnsColumn) {
        Reporter.log("Click random text buttons.");
        System.out.println("Click random text buttons.");
        //Control variable used to generate random index value.
        int maxValue = btnsColumn.size();
        //List of already generated random values. Used to avoid repeating index value.
        ArrayList<Integer> generated = new ArrayList<>();
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(btnsColumn));
            /*Every time a user selection is simulated, three random values are generated. When user selects value from first column they can click multiple times and the last selection is considered as final. For column 2 user can also click multiple times but only the first selected value is finally selected. */
            for (int i = 0; i < 3; i++) {
                //Random int from 0 to 8 (excluding 8). Each time the method is called a unique number is generated.
                int index = generateUnique(generated, maxValue);
                clickButtonWithIndex(index, btnsColumn);
            }
        } catch (Exception e) {
            System.err.println("Error in displaying buttons column!");
        }
    }

    /**
     * Method used to click on an element with specified index from one of two columns.
     *
     * @param index      int
     * @param btnsColumn List<WebElement>, list of column1 buttons or column2 buttons.
     */
    public void clickButtonWithIndex(int index, List<WebElement> btnsColumn) {
        //Initializing button element.
        WebElement el = btnsColumn.get(index);
        try {
            waitForElToBeClickable(el);
            Assert.assertTrue(el.isEnabled(), "Selected text button not clickable!");
            //Clicking button specified by index value.
            el.click();
            waitForElToBeClickable(locators.getH1TitleLoc());
            //Storing text displayed on selected element.
            String text = el.getText();
            Reporter.log("Clicked button with text: " + text);
            System.out.println("Clicked button with text: " + text);
        } catch (Exception e) {
            System.err.println("Button: " + el.getText() + " not clickable!");
        }
        waitForElToBeClickable(locators.getH1TitleLoc());
    }

    /**
     * Called every time a selection from a column is made.
     *
     * @return String (Final selected value from a column, after multiple clicks.)
     */
    public String getFinalSelection() {
        //List of all button elements marked as selected.
        List<WebElement> selectedBtns = new ArrayList<>();
        try {
            //Adding all elements that match the locator to the list.
            selectedBtns = findAll(new By.ByXPath("//button[@class='selected']"));
        } catch (Exception e) {
            System.err.println("Error displaying columns with text buttons!");
        }
        //Looping over selected elements.
        for (WebElement el : selectedBtns) {
            //Checking if selected value belongs to first column.
            if (this.column1Words.contains(el.getText())) {
                //If value has already been added to picked values loop continues to next step.
                if (this.pickedFromColumn1.contains(el.getText())) {
                    continue;
                }
                //Adding selected value to list of picked values from column1.
                this.pickedFromColumn1.add(el.getText());
                //Returning data that matches final selected value. This value matches the last selected value from column1, but starts with a number.
                return userTextLookup(this.gameData.getColumn1(), el.getText());
            } else {
                //If value has already been added to picked values loop continues to next step.
                if (this.pickedFromColumn2.contains(el.getText())) {
                    continue;
                }
                //Adding selected value to list of picked values from column2.
                this.pickedFromColumn2.add(el.getText());
                //Returning data that matches final selected value. This value matches the last selected value from column2, but starts with a number.
                return userTextLookup(this.gameData.getColumn2(), el.getText());
            }
        }
        return null;
    }

    /**
     * Used to get data needed for calculating game points.
     *
     * @param columnData List<String> (All values from a specified column that contains values matching ones displayed on UI, but starting with a number).
     * @param userText   String (Last selected UI value by user.)
     * @return String (Matching value from game data list, same as UI value but starts with a number.)
     */
    public String userTextLookup(List<String> columnData, String userText) {
        for (String data : columnData) {
            if (data.contains(userText)) {
                return data;
            }
        }
        return null;
    }

    /**
     * Used to simulate user selection of text values pair.
     */
    public void selectPair() {
        //Value from column1 is randomly selected.
        clickRandomButtonFromColumn(this.column1Btns);
        //Selected value is stored.
        String column1Data = getFinalSelection();
        //Value from column2 is randomly selected.
        clickRandomButtonFromColumn(this.column2Btns);
        //Selected value is stored.
        String column2Data = getFinalSelection();
        //Selected pair is added to list of userPicks (String pairs).
        this.userPicksList.add(new String[]{column1Data, column2Data});
    }

    /**
     * Uses list of pairs that match selected pairs to calculate game points.
     */
    public void calculateGamePoints() {
        Reporter.log("Game data according to user selections");
        System.out.println("Game data according to user selections");
        //Displaying game data that matches user selected pairs selected from UI.
        for (String[] pair : this.userPicksList) {
            //If nothing has been selected execution stops.
            if ((pair[0] == null) && (pair[1] == null)) {
                System.err.println("No values selected!");
                return;
            }
            //Trimming unwanted empty spaces.
            String column1Data = pair[0].trim();
            String column2Data = pair[1].trim();
            Reporter.log(column1Data + ", " + column2Data + ".\n");
            System.out.print(column1Data + ", " + column2Data + ".\n");
            //Extracting starting number for values corresponding to each column.
            String num1 = column1Data.substring(0, 1);
            String num2 = column2Data.substring(0, 1);
            //Adding three points to calculated game points when starting numbers match.
            if (num1.equals(num2)) {
                setCalculatedPoints(this.getCalculatedPoints() + 3);
            }
        }
    }

    /**
     * Comparing calculated points to value displayed in alert message on submit button click.
     */
    public void verifyCalculatedPoints() {
        if (this.getAlertMsg() == null) {
            System.err.println("No alert message!");
            return;
        }
        //Converting alert message to array of String values.
        String[] msgArray = this.getAlertMsg().split(" ");
        //Number of points matches last element in array.
        int computerPoints = Integer.parseInt(msgArray[msgArray.length - 1]);
        Reporter.log("Check if computer calculated points match expected.");
        System.out.println("Check if computer calculated points match expected.");
        Assert.assertEquals(computerPoints, this.getCalculatedPoints(), "Computer calculated points: " + computerPoints + " don't match expected value: " + this.getCalculatedPoints());
        Reporter.log("Computer calculated points: " + computerPoints + " matches expected value.");
        System.out.println("Computer calculated points: " + computerPoints + " matches expected value.");
    }
}
