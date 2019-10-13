package ru.stqa.pft.addressbook.tests;

import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase {

    @Test
    public void testContactPhones() {
        app.goTo().contactPage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getHomeTelephone(), equalTo(cleaned(contactInfoFromEditForm.getHomeTelephone())));
        assertThat(contact.getMobileTelephone(), equalTo(cleaned(contactInfoFromEditForm.getMobileTelephone())));
        assertThat(contact.getWorkTelephone(), equalTo(cleaned(contactInfoFromEditForm.getWorkTelephone())));
    }

    public String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}
