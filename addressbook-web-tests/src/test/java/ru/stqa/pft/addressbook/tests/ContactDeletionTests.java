package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().contactPage();
        if (app.contact().list().size() == 0) {
            app.contact().create(new ContactData()
                    .withFirstname("Александр").withLastname("Дрозд").withAddress("г. Москва, улица Рябиновая, дом 7, корпус 5.").withHomeTelephone("8-978-485-74-11").withEmail("alexqamail@qa.ru").withGroup("test1"), true);
        }
    }

    @Test
    public void testContactDeletion() throws Exception {
        List<ContactData> before = app.contact().list();
        int index = before.size() - 1;
        app.contact().delete(index);
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(index);
        Assert.assertEquals(before, after);
    }

}