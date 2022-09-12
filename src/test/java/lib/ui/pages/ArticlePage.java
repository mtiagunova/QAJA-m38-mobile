package lib.ui.pages;

import lib.ui.MainPageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ArticlePage extends MainPageObject {

    final static String ADD_TO_FAVORITES_BUTTON = "xpath://android.widget.ImageView[@content-desc=\"Add this article to a reading list\"]";
    final static String ONBOARDING_BUTTON= "id:org.wikipedia:id/onboarding_button"; // GOT IT! button
    final static String LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
    final static String OK_BUTTON = "id:android:id/button1";
    final static String ARTICLE_MENU = "xpath://android.widget.ImageView[@content-desc=\"More options\"]";
    final static String ARTICLE_MENU_CHANGE_LANGUAGE = "xpath:/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[1]/android.widget.RelativeLayout/android.widget.TextView";
    final static String LANGUAGE_SEARCH_INPUT = "id:org.wikipedia:id/langlinks_filter";    final static String LANGUAGE_RUSSIAN = "xpath:/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.LinearLayout[2]/android.widget.TextView[1]";
    final static String ARTICLE_TITLE = "id:org.wikipedia:id/view_page_title_text";

    public ArticlePage(RemoteWebDriver driver) {
        super(driver);
    }

    // Нажать на значок "Добавить в закладки"
    private void pushAddToFavoritesButton() {
        WebElement addToFavButton = this.waitForElementPresent(
                ADD_TO_FAVORITES_BUTTON,
                "Cannot find 'add to favorites' button"
        );
        addToFavButton.click();
        WebElement onBoardingButton = this.waitForElementPresent(
                ONBOARDING_BUTTON,
                "Cannot find onboarding 'GOT IT' button"
        );
        onBoardingButton.click();
    }

    private void pushOKButton() {
        WebElement OKButton = this.waitForElementPresent(
                OK_BUTTON,
                "Cannot find OK button"
        );
        OKButton.click();
    }

    // 1. Добавление статьи в список для чтения с предложенным названием 'My reading list'
    public void addToFavorites() {
        pushAddToFavoritesButton();
        pushOKButton();
    }

    // 2. Добавление статьи в список для чтения со своим названием
    public void addToFavorites(String newListTitle) {
        pushAddToFavoritesButton();

        WebElement listNameInput = this.waitForElementPresent(
                LIST_NAME_INPUT,
                "Cannot find list name input"
        );
        listNameInput.clear();
        listNameInput.sendKeys(newListTitle);

        pushOKButton();
    }

    // Изменить язык статьи на русский
    // Возвращает название статьи на русском языке
    public String changeLanguage() {
        openArticleMenu();
        WebElement articleMenuChangeLang = this.waitForElementPresent(
                ARTICLE_MENU_CHANGE_LANGUAGE,
                "Cannot find article menu change language"
        );
        articleMenuChangeLang.click();
        WebElement langSearchInput = this.waitForElementPresent(
                LANGUAGE_SEARCH_INPUT,
                "Cannot find language search input"
        );
        langSearchInput.click();
        langSearchInput.sendKeys("rus");
        WebElement langRusOption = this.waitForElementPresent(
                LANGUAGE_RUSSIAN,
                "Cannot find russian language option"
        );
        langRusOption.click();
        WebElement articleTitle = this.waitForElementPresent(
                ARTICLE_TITLE,
                "Cannot find article title"
        );
        return articleTitle.getText();
    }

    // Открывает action menu статьи
    public void openArticleMenu() {
        WebElement articleMenu = this.waitForElementPresent(
                ARTICLE_MENU,
                "Cannot find article menu"
        );
        articleMenu.click();
    }

    // Возвращает название статьи
    public String getArticleTitle() {
        WebElement articleTitle = this.waitForElementPresent(
                ARTICLE_TITLE,
                "Cannot find article title");
        return articleTitle.getText();
    }
}
