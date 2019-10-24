package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDataTests extends TestBase {

    @BeforeMethod
    // Проверяем наличие существующего элемента, если его нет - то предварительно создаем его с необходимыми параметрами.
    public void ensurePreconditions() {
        app.goTo().contactPage();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData()
                    .withFirstname("Александр")
                    .withLastname("Дрозд")
                    .withAddress("г. Москва, улица Рябиновая, дом 7, корпус 5.")
                    .withEmail("alexqamail@qa.ru")
                    .withEmail2("alexqamail2@qa.ru")
                    .withEmail3("alexqamail3@qa.ru")
                    .withHomeTelephone("89784857411")
                    .withMobileTelephone("89784857412")
                    .withWorkTelephone("89784857413"), true);
        }
    }

    @Test
    // Проверяем совпадение данных у контакта на его странице просмотра и странице редактирования.
    public void testContactData() {
        app.goTo().contactPage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
        assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactDataTests::cleanedEmails)
                .collect(Collectors.joining("\n"));
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomeTelephone(), contact.getMobileTelephone(), contact.getWorkTelephone())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactDataTests::cleanedPhones)
                .collect(Collectors.joining("\n"));
    }

    // Очистка адресов почты от пробелов в начале и конце.
    public static String cleanedEmails(String email) {
        return email.replaceAll("^\\s", "").replaceAll("\\s$", "");
    }

    // Очистка телефонов от пробелов, символов круглых скобок и дефиса.
    public static String cleanedPhones(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

}