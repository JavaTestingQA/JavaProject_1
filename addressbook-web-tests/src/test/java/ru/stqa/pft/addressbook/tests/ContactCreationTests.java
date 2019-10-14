package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase{

    @Test
    public void testContactCreation() throws Exception {
        app.goTo().contactPage();
        Contacts before = app.contact().all();
        File photo = new File("src/test/resources/stru.png");
        ContactData contact = new ContactData()
                .withFirstname("Александр")
                .withLastname("Невский")
                .withAddress("г. Москва, улица Рябиновая, дом 7, корпус 5.")
                .withHomeTelephone("8-978-485-74-11")
                .withEmail("alexqamail@qa.ru")
                .withPhoto(photo)
                .withGroup("test1");
        app.contact().create(contact, true);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    }

    @Test
    public void testBadContactCreation() throws Exception {
        app.goTo().contactPage();
        Contacts before = app.contact().all();
        ContactData contact = new ContactData()
                .withFirstname("Александр'").withLastname("Невский").withAddress("г. Москва, улица Рябиновая, дом 7, корпус 5.").withHomeTelephone("8-978-485-74-11").withEmail("alexqamail@qa.ru").withGroup("test1");
        app.contact().create(contact, true);
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before));
    }
}
