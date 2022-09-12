package lib.ui.pages;

import lib.ui.MainPageObject;
import org.opencv.ml.EM;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HistoryPage extends MainPageObject {

    final static String ELEMENT_IN_HISTORY_LIST = "id:org.wikipedia:id/page_list_item_title";
    final static String TRASH_BIN_BUTTON = "id:org.wikipedia:id/menu_clear_all_history";
    final static String YES_BUTTON = "id:android:id/button1";
    final static String EMPTY_HISTORY_TITLE = "id:org.wikipedia:id/history_empty_title";

    public HistoryPage(RemoteWebDriver driver) {
        super(driver);
    }

    // Возвращает название статьи из списка в истории
    public String getHistoryElementText() {
        WebElement elementInHistory = this.waitForElementPresent(
                ELEMENT_IN_HISTORY_LIST,
                "Cannot find Java text title element");
        return elementInHistory.getText();
    }

    // Возвращает текст 'No recently viewed articles' при пустой истории
    public String getEmptyHistoryTitle() {
        WebElement emptyHistoryTitle = this.waitForElementPresent(
                EMPTY_HISTORY_TITLE,
                "Cannot find empty history title"
        );
        return emptyHistoryTitle.getText();
    }

    // Нажать кнопку 'Корзина'
    public void pushDeleteHistoryButton() {
        WebElement trashBinButton = this.waitForElementPresent(
                TRASH_BIN_BUTTON,
                "Cannot find trash bin button"
        );
        trashBinButton.click();
        WebElement confirmButton = this.waitForElementPresent(
                YES_BUTTON,
                "Cannot find yes button"
        );
        confirmButton.click();
    }
}
