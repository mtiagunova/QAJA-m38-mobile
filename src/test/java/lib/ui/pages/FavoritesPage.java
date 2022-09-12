package lib.ui.pages;

import lib.ui.MainPageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class FavoritesPage extends MainPageObject {

    final static String LIST_TITLE= "id:org.wikipedia:id/item_title";
    final static String ARTICLE_TITLE = "id:org.wikipedia:id/page_list_item_title";
    final static String ITEM_TITLE = "xpath://*[./*[contains(@text,'{TEXT}')]]"; // любой список или любая статья с любым названием
    final static String ARTICLE_MENU = "id:org.wikipedia:id/page_list_item_action_primary";
    final static String ARTICLE_MENU_REMOVE = "id:org.wikipedia:id/reading_list_item_remove_text";
    final static String EMPTY_LIST_OF_FAVORITES_TEXT = "id:org.wikipedia:id/reading_list_empty_text";

    final static String LIST_MENU = "xpath://android.widget.ImageView[@content-desc=\"More options\"]";
    final static String LIST_MENU_REMOVE = "xpath:/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.RelativeLayout";
    final static String EMPTY_FAVORITES_TEXT = "id:org.wikipedia:id/empty_title";

    final static String UNDO_BUTTON = "id:org.wikipedia:id/snackbar_action";

    final static String LIST_MENU_RENAME = "xpath:/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[1]/android.widget.RelativeLayout/android.widget.TextView";
    final static String LIST_RENAME_INPUT = "id:org.wikipedia:id/text_input";
    final static String RENAME_LIST_OK_BUTTON = "id:android:id/button1";
    final static String EMPTY_LIST_TITLE_ERROR_MSG = "id:org.wikipedia:id/textinput_error";

    public FavoritesPage(RemoteWebDriver driver) {
        super(driver);
    }

    private static String getTitleByText(String title) {
        return ITEM_TITLE.replace("{TEXT}", title);
    }

    // Открывает список для чтения на странице закладок
    // или статью в открытом списке для чтения
    // по его/ее названию title
    public void openItem(String title) {
        WebElement list = this.waitForElementPresent(
                getTitleByText(title),
                "Cannot find '" + title + "'"
        );
        list.click();
    }

    // Возвращает название статьи со страницы закладок (находящейся в к.-л. списке для чтения)
    public String getArticleTitle() {
        WebElement articleTitle = this.waitForElementPresent(
                ARTICLE_TITLE,
                "Cannot find 'Java' title element");
        return articleTitle.getText();
    }

    // Возвращает название списка для чтения
    public String getListTitle() {
        WebElement listTitle = this.waitForElementPresent(
                LIST_TITLE,
                "Cannot find list title element");
        return listTitle.getText();
    }

    // Удаление статьи из списка
    public void removeArticleFromList(String articleTitle) {
        WebElement articleMenu = this.waitForElementPresent(
                ARTICLE_MENU,
                "Cannot find 'Java' article menu"
        );
        articleMenu.click();
        WebElement articleMenuRemove = this.waitForElementPresent(
                ARTICLE_MENU_REMOVE,
                "Cannot find remove button"
        );
        articleMenuRemove.click();
    }

    // Возвращает сообщение о пустом списке для чтения 'You have no articles added to this list.'
    public String getEmptyListText() {
        WebElement emptyText = this.waitForElementPresent(
                EMPTY_LIST_OF_FAVORITES_TEXT,
                "Cannot find empty list text"
        );
        return emptyText.getText();
    }

    // Меняет название списка для чтения на новое
    // Возвращает новое название списка
    public String changeListTitle(String newTitle) {
        openListMenu();
        WebElement listMenuRename = this.waitForElementPresent(
                LIST_MENU_RENAME,
                "Cannot find list menu rename button"
        );
        listMenuRename.click();
        WebElement listRenameInput = this.waitForElementPresent(
                LIST_RENAME_INPUT,
                "Cannot find list rename input"
        );
        listRenameInput.clear();
        if(newTitle != "") {
            listRenameInput.sendKeys(newTitle);
            WebElement okButton = this.waitForElementPresent(
                    RENAME_LIST_OK_BUTTON,
                    "Cannot find OK button"
            );
            okButton.click();
            System.out.println(getListTitle());
            return getListTitle();
        }
        else
            return "";
    }

    // Открывает action menu списка для чтения
    public void openListMenu() {
        // Локатор зависит от того, открывают меню списка со страницы закладок или со страницы самого списка для чтения
        try {
            WebElement listMenu = this.waitForElementPresent(
                    "(" + LIST_MENU + ")[1]",
                    "Cannot find list menu"
            );
            listMenu.click();
        } catch(IllegalArgumentException e) {
            WebElement listMenu = this.waitForElementPresent(
                    LIST_MENU,
                    "Cannot find list menu"
            );
            listMenu.click();
        };

    }

    // При пустом поле input для названия списка чтения возвращает сообщение об ошибке
    // Текст сообщения: "Please enter a title."
    public String getEmptyListTitleErrorMsg() {
        WebElement errorMsg = this.waitForElementPresent(
                EMPTY_LIST_TITLE_ERROR_MSG,
                "Cannot find Please enter a title message"
        );
        return errorMsg.getText();
    }

    // Удаление списка для чтения
    public void removeListFromFavorites() {
        openListMenu();
        WebElement listMenuRemove = this.waitForElementPresent(
                LIST_MENU_REMOVE,
                "Cannot find delete list button"
        );
        listMenuRemove.click();
    }

    // Возвращается сообщение о том, что в закладках нет ни одного списка для чтения
    // Сообщение: "No reading lists yet"
    public String getEmptyFavoritesText() {
        WebElement text = this.waitForElementPresent(
                EMPTY_FAVORITES_TEXT,
                "Cannot find 'No reading lists yet' text"
        );
        return text.getText();
    }

    public String removeListFromFavoritesAndUndo() {
        removeListFromFavorites();
        WebElement undoButton = this.waitForElementPresent(
                UNDO_BUTTON,
                "Cannot find UNDO button"
        );
        undoButton.click();
        return getListTitle();
    }
}
