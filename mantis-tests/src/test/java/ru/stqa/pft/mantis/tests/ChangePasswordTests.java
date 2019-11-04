package ru.stqa.pft.mantis.tests;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.*;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testChangePassword() throws IOException, MessagingException {

        // Логинимся админом, выбираем юзера и нажимаем в его настройках на сброс пароля.
        app.usersHelper().loginAsAdmin("administrator", "root");
        app.usersHelper().goToUserSettings();
        app.usersHelper().selectFirstUser();
        String userName = app.usersHelper().getUserName();
        String userEmail = app.usersHelper().getUserEmail();
        app.usersHelper().resetUserPassword();

        // Получаем почту, переходим по ссылке, меняем на новый пароль.
        String newPassword = "secret";
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmationLink = findConfirmationLink(mailMessages, userEmail);
        app.registration().resetPassword(confirmationLink, newPassword);

        // Поверяем что можно залогиниться с новым паролем, проверяем что залогинились нужным пользователем.
        HttpSession session = app.newSession();
        session.login(userName, newPassword);
        assertTrue(session.isLoggedInAs(userName));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}