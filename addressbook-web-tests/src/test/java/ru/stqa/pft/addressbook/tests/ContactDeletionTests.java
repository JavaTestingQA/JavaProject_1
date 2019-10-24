package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().contactPage();
        if (app.db().contacts().size() == 0) {
            app.contact().create(new ContactData().withFirstname("Александр").withLastname("Дрозд").withAddress("г. Москва, улица Рябиновая, дом 7, корпус 5.").withHomeTelephone("8-978-485-74-11").withEmail("alexqamail@qa.ru"), true);
        }
    }

    @Test
    public void testContactDeletion() throws Exception {
        Contacts before = app.db().contacts();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        assertThat(app.contact().count(), equalTo(before.size() - 1));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(deletedContact)));
        verifyContactListInUI(); // Для включения данной проверки (сравнение списков контактов загруженных из интерфейса и БД), добавить в конфигурацию запуска опцию -DverifyUI=true
    }

}