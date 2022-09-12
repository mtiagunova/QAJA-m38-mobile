package lib.ui.pages;

import lib.ui.MainPageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class StartPage extends MainPageObject {

    final static String INIT_SEARCH = "id:org.wikipedia:id/search_container";
    final static String HISTORY_BUTTON = "xpath://android.widget.FrameLayout[@content-desc='History']/android.widget.ImageView";
    final static String FAVORITES_BUTTON = "xpath://android.widget.FrameLayout[@content-desc='My lists']/android.widget.ImageView";

    public StartPage(RemoteWebDriver driver) {
        super(driver);
    }

    // Нажать по поисковой строке для перехода на страницу поиска
    public void initSearch() {
        WebElement searchInit = this.waitForElementPresent(
                INIT_SEARCH,
                "Cannot find Search Wikipedia init search input");
        searchInit.click();
    }

    // Открыть историю просмотра
    public void openHistory() {
        WebElement historyButton = this.waitForElementPresent(
                HISTORY_BUTTON,
                "Cannot find history button"
        );
        historyButton.click();
    }

    // Открыть закладки
    public void openFavorites() {
        WebElement favoritesButton = this.waitForElementPresent(
                FAVORITES_BUTTON,
                "Cannot find 'favorites' button"
        );
        favoritesButton.click();
    }
}
