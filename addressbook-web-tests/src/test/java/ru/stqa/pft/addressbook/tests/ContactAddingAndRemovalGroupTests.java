package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class ContactAddingAndRemovalGroupTests extends TestBase {
    private ContactData expContact;
    private GroupData expGroup;

    @BeforeClass //Проверка предусловий - создание необходимых контакта/группы.
    private void ensurePreconditions () {
        Contacts contacts = app.db().contacts();
        Groups groups = app.db().groups();

        for (ContactData contact : contacts) {
            for (GroupData group : groups) {
                if (groups.stream()
                        .filter(g -> g.getName().equals(group.getName()))
                        .collect(Collectors.toList()).size() > 1) {
                    continue;
                }
                if (!contact.getGroups().contains(group)) {
                    expContact = contact;
                    expGroup = group;
                    return;
                }
            }
        }
        expContact = new ContactData()
                .withFirstname("Александр")
                .withLastname("Невский")
                .withAddress("г. Москва, улица Рябиновая, дом 7, корпус 5.")
                .withHomeTelephone("8-978-485-74-11")
                .withEmail("alexqamail@qa.ru");

        expGroup = new GroupData()
                .withName(String.format("UniqueName (%s)", Math.random()))
                .withHeader("Header")
                .withFooter("Footer");

        app.goTo().contactPage();
        app.contact().create(expContact, true);
        app.goTo().groupPage();
        app.group().create(expGroup);

        contacts = app.db().contacts();
        groups = app.db().groups();

        expContact.withId(contacts.stream().mapToInt((c) -> c.getId()).max().getAsInt());
        expGroup.withId(groups.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    }

    @Test // Добавление контакта в группу.
    public void ContactAddToGroup() {
        app.goTo().contactPage();
        Groups before = expContact.getGroups();
        app.contact().addToGroup(expContact, expGroup);

        ContactData contactFromDb = app.db().getContactInfoById(expContact.getId());
        assertThat(contactFromDb.getGroups(), hasItem(expGroup));
        assertThat(contactFromDb.getGroups(), equalTo(before.withAdded(expGroup)));

    }

    @Test // Удаление контакта из группы.
    public void ContactRemoveFromGroup() {
        app.goTo().contactPage();
        Groups before = expContact.getGroups();
        app.contact().removeFromGroup(expContact, expGroup);

        ContactData contactFromDb = app.db().getContactInfoById(expContact.getId());
        assertThat(contactFromDb.getGroups(), not(hasItem(expGroup)));
        assertThat(contactFromDb.getGroups(), equalTo(before.without(expGroup)));
    }
}