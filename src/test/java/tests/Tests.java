package tests;

import lib.CoreTestCase;
import lib.ui.pages.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Tests extends CoreTestCase {

    StartPage startPage;
    SearchPage searchPage;
    HistoryPage historyPage;
    ArticlePage articlePage;
    FavoritesPage favoritesPage;

    @Before
    public void initPages() {
        this.startPage = new StartPage(driver);
        this.searchPage = new SearchPage(driver);
        this.historyPage = new HistoryPage(driver);
        this.articlePage = new ArticlePage(driver);
        this.favoritesPage = new FavoritesPage(driver);
    }

    // 1. Позитивный сценарий: найти статью с названием 'Java'
    @Test
    public void testFindJava() {
        startPage.initSearch();
        searchPage.findByText("Java");
        searchPage.selectByText("Island of Indonesia");
        System.out.println(articlePage.getArticleTitle());
        assertEquals("Java", articlePage.getArticleTitle());
    }

    // 2. Негативный сценарий: статья с названием 'wfewfewfwegweg' не найдена
    @Test
    public void testFindNonExistentText() {
        startPage.initSearch();
        searchPage.findByText("wfewfewfwegweg");
        assertEquals("No results found", searchPage.getEmptySearchMsg());
    }

    // 3. Позитивный сценарий: после посещения страницы 'Java' она находится в истории
    @Test
    public void testFindJavaAndCheckHistory() {
        startPage.initSearch();
        searchPage.findByText("Java");
        searchPage.selectByText("Island of Indonesia");

        driver.navigate().back();
        startPage.openHistory();
        String actualText = historyPage.getHistoryElementText();
        assertEquals("Java", actualText);
    }

    // 4. Позитивный сценарий: удалить историю просмотра статей
    @Test
    public void testDeleteHistoryContent() {
        startPage.initSearch();
        searchPage.findByText("Java");
        searchPage.selectByText("Island of Indonesia");

        driver.navigate().back();
        startPage.openHistory();
        historyPage.pushDeleteHistoryButton();
        String actualText = historyPage.getEmptyHistoryTitle();
        assertEquals("No recently viewed articles", actualText);
    }

    // 5. Позитивный сценарий: найти статью 'Island of Indonesia'
    // и добавить в список для чтения с предложенным названием 'My reading list'
    @Test
    public void testFindArticleAndAddToFavorite() {
        startPage.initSearch();
        searchPage.findByText("Java");
        searchPage.selectByText("Island of Indonesia");
        articlePage.addToFavorites();

        driver.navigate().back();
        driver.navigate().back();

        startPage.openFavorites();
        favoritesPage.openItem("My reading list");
        String actualArticleTitle = favoritesPage.getArticleTitle();
        assertEquals("Java", actualArticleTitle);
    }

    // 6. Позитивный сценарий: найти статью 'Island of Indonesia', добавить в закладки
    // в список для чтения с именем 'Places'
    @Test
    public void testCreateNewReadingListName() {
        startPage.initSearch();
        searchPage.findByText("Java");
        searchPage.selectByText("Island of Indonesia");
        articlePage.addToFavorites("Places");

        driver.navigate().back();
        driver.navigate().back();

        startPage.openFavorites();
        String actualArticleTitle = favoritesPage.getListTitle();
        assertEquals("Places", actualArticleTitle);
    }

    // 7. Позитивный сценарий: удалить (последнюю) статью из списка для чтения, список становится пуст
    @Test
    public void testRemoveArticleFromFavorites() {
        startPage.initSearch();
        searchPage.findByText("Java");
        searchPage.selectByText("Island of Indonesia");
        articlePage.addToFavorites("Places");

        driver.navigate().back();
        driver.navigate().back();

        startPage.openFavorites();
        favoritesPage.openItem("Places");
        favoritesPage.removeArticleFromList("Java");

        String actualText = favoritesPage.getEmptyListText();
        assertEquals("You have no articles added to this list.", actualText);
    }

    // 8. Позитивный сценарий: переименовать список для чтения
    @Test
    public void testChangeListTitle() {
        startPage.initSearch();
        searchPage.findByText("Java");
        searchPage.selectByText("Island of Indonesia");
        articlePage.addToFavorites();

        driver.navigate().back();
        driver.navigate().back();

        startPage.openFavorites();
        favoritesPage.openItem("My reading list");
        String actualTitle = favoritesPage.changeListTitle("Favourite places");
        assertEquals("Favourite places", actualTitle);
    }

    // 9. Негативный сценарий: переименовать список для чтения - сделать без названия
    @Test
    public void testChangeListTitleToEmpty() {
        startPage.initSearch();
        searchPage.findByText("Java");
        searchPage.selectByText("Island of Indonesia");
        articlePage.addToFavorites();

        driver.navigate().back();
        driver.navigate().back();

        startPage.openFavorites();
        favoritesPage.openItem("My reading list");
        favoritesPage.changeListTitle("");
        assertEquals("Please enter a title.", favoritesPage.getEmptyListTitleErrorMsg());
        System.out.println(favoritesPage.getEmptyListTitleErrorMsg());
    }

    // 10. Позитивный сценарий: изменить язык статьи на русский
    // Название статьи изменилось с 'Java' на 'Ява'
    @Test
    public void testChangeArticleLanguage() {
        startPage.initSearch();
        searchPage.findByText("Java");
        searchPage.selectByText("Island of Indonesia");

        String actualTitle = articlePage.changeLanguage();
        assertEquals("Ява", actualTitle);
    }

    // 11. Позитивный сценарий: Удалить список для чтения
    @Test
    public void testRemoveReadingList() {
        startPage.initSearch();
        searchPage.findByText("Java");
        searchPage.selectByText("Island of Indonesia");
        articlePage.addToFavorites();

        driver.navigate().back();
        driver.navigate().back();

        startPage.openFavorites();
        favoritesPage.removeListFromFavorites();
        String actualText = favoritesPage.getEmptyFavoritesText();
        assertEquals("No reading lists yet", actualText);
    }

    // 12. Позитивный сценарий: удалить список для чтения и сразу отменить действие с помощью кнопки UNDO на snackbar
    @Test
    public void testRemoveReadingListAndUndo() {
        startPage.initSearch();
        searchPage.findByText("Java");
        searchPage.selectByText("Island of Indonesia");
        articlePage.addToFavorites();

        driver.navigate().back();
        driver.navigate().back();

        startPage.openFavorites();
        String actualTitle = favoritesPage.removeListFromFavoritesAndUndo();
        assertEquals("My reading list", actualTitle);
    }
}