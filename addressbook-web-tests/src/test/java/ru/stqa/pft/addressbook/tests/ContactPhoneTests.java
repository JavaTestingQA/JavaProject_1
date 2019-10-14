package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase {

    @BeforeMethod // Проверяем наличие существующего элемента, если его нет - то предварительно создаем его с необходимыми параметрами.
    public void ensurePreconditions() {
        app.goTo().contactPage();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData()
                    .withFirstname("Александр")
                    .withLastname("Дрозд").withAddress("г. Москва, улица Рябиновая, дом 7, корпус 5.")
                    .withHomeTelephone("89784857411")
                    .withMobileTelephone("89784857412")
                    .withWorkTelephone("89784857413")
                    .withEmail("alexqamail@qa.ru")
                    .withGroup("test1"), true);
        }
    }

    @Test (enabled = false)
    public void testContactPhones() {
        app.goTo().contactPage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomeTelephone(), contact.getMobileTelephone(), contact.getWorkTelephone())
                .stream().filter((s) -> ! s.equals(""))
                .map(ContactPhoneTests::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}
