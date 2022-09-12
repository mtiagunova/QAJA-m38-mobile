package lib.ui.pages;

import lib.ui.MainPageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SearchPage extends MainPageObject {

    final static String SEARCH_INPUT = "id:org.wikipedia:id/search_src_text";
    final static String RESULT_ELEMENT_BY_TEXT_TPL = "xpath://*[./*[contains(@text,'{TEXT}')]]";
    final static String SEARCH_EMPTY_TEXT = "id:org.wikipedia:id/search_empty_text";

    public SearchPage(RemoteWebDriver driver) {
        super(driver);
    }

    // Поиск по тексту
    public void findByText(String text) {
        WebElement searchInput = this.waitForElementPresent(
                SEARCH_INPUT,
                "Cannot find Search... input"
        );
        searchInput.sendKeys(text);
    }

    // Выбор элемента по тексту
    public void selectByText(String text) {
        WebElement expectedResult = this.waitForElementPresent(
                getResultElementByText(text),
                "Cannot find result: '" + text + "'"
        );

        expectedResult.click();
    }

    private static String getResultElementByText(String text) {
        return RESULT_ELEMENT_BY_TEXT_TPL.replace("{TEXT}", text);
    }

    // Возвращает текст сообщения 'No results found', если не найдены результаты по поисковому запросу
    public String getEmptySearchMsg() {
        WebElement emptyMsg = this.waitForElementPresent(
                SEARCH_EMPTY_TEXT,
                "Cannot find element 'No results found'");
        return emptyMsg.getText();
    }
}
