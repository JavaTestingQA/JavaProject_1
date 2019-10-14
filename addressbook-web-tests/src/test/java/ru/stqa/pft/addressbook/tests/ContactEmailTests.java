package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTests extends TestBase {

    @BeforeMethod // Проверяем наличие существующего элемента, если его нет - то предварительно создаем его с необходимыми параметрами.
    public void ensurePreconditions() {
        app.goTo().contactPage();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData()
                    .withFirstname("Александр")
                    .withLastname("Дрозд").withAddress("г. Москва, улица Рябиновая, дом 7, корпус 5.")
                    .withEmail("alexqamail@qa.ru")
                    .withEmail2("alexqamail2@qa.ru")
                    .withEmail3("alexqamail3@qa.ru")
                    .withEmail("alexqamail@qa.ru")
                    .withGroup("test1"), true);
        }
    }

    @Test (enabled = false)
    public void testContactEmails() {
        app.goTo().contactPage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s) -> ! s.equals(""))
                .map(ContactEmailTests::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String email) {
        return email.replaceAll("^\\s", "").replaceAll("\\s$", "");
    }

}