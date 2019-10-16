package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase{

    @DataProvider
    public Iterator<Object[]> validContacts() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")));
        String line = reader.readLine();
        while (line != null) {
            String[] split = line.split(";");
            list.add(new Object[] {new ContactData().withFirstname(split[0]).withLastname(split[1]).withAddress(split[2]).withEmail(split[3]).withHomeTelephone(split[4]).withGroup(split[5])});
            line = reader.readLine();
        }
        return list.iterator();
    }

    @Test(dataProvider = "validContacts")
    public void testContactCreation(ContactData contact) throws Exception {
        app.goTo().contactPage();
        Contacts before = app.contact().all();
        // File photo = new File("src/test/resources/stru.png");
        app.contact().create(contact, true);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    }

    // Старый тест создания (до параметризации), переименован и отключен.
    @Test (enabled = false)
    public void testContactCreationOLD() throws Exception {
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
