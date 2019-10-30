package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class ContactAddToGroupTest extends TestBase {
    private ContactData expContact;
    private GroupData expGroup;

    @BeforeMethod
    // Проверка предусловий: создание контакта и/или группы если нет существующих или если нет "свободного" контакта/группы.
    public void ensurePreconditions() {
        Contacts contacts = app.db().contacts();
        if (contacts.size() == 0 || contacts.stream().noneMatch(contact -> contact.getGroups().size() < 1)) {
            app.goTo().contactPage();
            app.contact().create(new ContactData().withFirstname("Александр").withLastname("Невский").withAddress("г. Москва, улица Рябиновая, дом 7, корпус 5.").withHomeTelephone("8-978-485-74-11").withEmail("alexqamail@qa.ru"), true);
        }

        Groups groups = app.db().groups();
        if (groups.size() == 0 || groups.stream().noneMatch(group -> group.getContacts().size() < 1)) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName(String.format("UniqueName (%s)", Math.random())).withHeader("Header").withFooter("Footer"));
        }
    }

    @Test // Выбор первого попавшегося контакта/группы и добавление контакта в группу.
    public void ContactAddToGroup() {
        Contacts contacts = app.db().contacts();
        for (ContactData contact : contacts) {
            if (contact.getGroups().size() < 1) {
                expContact = contact;
            }
        }

        Groups groups = app.db().groups();
        for (GroupData group : groups) {
            if (group.getContacts().size() < 1) {
                expGroup = group;
            }
        }

        app.goTo().contactPage();
        Groups before = expContact.getGroups();
        app.contact().addToGroup(expContact, expGroup);
        System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
        System.out.println(">>>>> Контакт " + expContact + " добавлен в группу " + expGroup + " <<<<<");
        System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");

        ContactData contactFromDb = app.db().getContactInfoById(expContact.getId());
        assertThat(contactFromDb.getGroups(), hasItem(expGroup));
        assertThat(contactFromDb.getGroups(), equalTo(before.withAdded(expGroup)));
    }
}