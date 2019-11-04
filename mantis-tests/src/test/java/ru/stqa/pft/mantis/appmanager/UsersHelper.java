package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class UsersHelper extends HelperBase {

    public UsersHelper(ApplicationManager app) {
        super(app);
    }

    // Логинимся под админом.
    public void loginAsAdmin(String username, String password) {
        wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
        type(By.xpath("//input[@name='username']"), username);
        click(By.xpath("//input[contains(@value,'Войти')]"));
        type(By.xpath("//input[@name='password']"), password);
        click(By.xpath("//input[contains(@value,'Войти')]"));
    }

    // Переход в "управление -> управление пользователями".
    public void goToUserSettings() {
        click(By.xpath("//span[contains(.,'Управление')]"));
        click(By.linkText("Управление пользователями"));
    }

    // Кликаем в пользователя, содержащего user в имени.
    public void selectFirstUser() {
        click(By.xpath("//*[@id='main-container']//*[contains(text(), 'user')]"));
    }

    // Кликаем в "Сбросить пароль".
    public void resetUserPassword() {
        click(By.xpath("//input[contains(@value,'Сбросить пароль')]"));
    }

    // Получаем имя пользователя.
    public String getUserName() {
        String name = wd.findElement(By.xpath("//input[@name='username']")).getAttribute("value");
        return name;
    }

    // Получаем почту пользователя.
    public String getUserEmail() {
        String email = wd.findElement(By.xpath("//input[@name='email']")).getAttribute("value");
        return email;
    }
}