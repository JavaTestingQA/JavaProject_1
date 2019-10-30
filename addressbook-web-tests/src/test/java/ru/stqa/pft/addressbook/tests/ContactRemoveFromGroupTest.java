package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class ContactRemoveFromGroupTest extends TestBase {
    private ContactData expContact;
    private GroupData expGroup;

    @BeforeMethod
    // Проверка предусловий: если нет существующего контакта/группы, или если нет группы с добавленным контактом - создаем их.
    public void ensurePreconditions() {
        // Если нет существующего контакта - создаем его.
        if (app.db().contacts().size() == 0) {
            app.goTo().contactPage();
            app.contact().create(new ContactData().withFirstname("Александр").withLastname("Невский").withAddress("г. Москва, улица Рябиновая, дом 7, корпус 5.").withHomeTelephone("8-978-485-74-11").withEmail("alexqamail@qa.ru"), true);
        }

        // Если нет существующей группы - создаем её.
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName(String.format("UniqueName (%s)", Math.random())).withHeader("Header").withFooter("Footer"));
        }

        // Если нет группы с добавленным контактом - находим "пустые" контакт и группу и добавляем найденный контакт в найденную группу.
        Groups groups = app.db().groups();
        if (groups.stream().noneMatch(group -> group.getContacts().size() > 0)) {

            Contacts contactsAll = app.db().contacts();
            for (ContactData contact : contactsAll) {
                if (contact.getGroups().size() < 1) {
                    expContact = contact;
                }
            }

            Groups groupsAll = app.db().groups();
            for (GroupData group : groupsAll) {
                if (group.getContacts().size() < 1) {
                    expGroup = group;
                }
            }
            app.goTo().contactPage();
            app.contact().addToGroup(expContact, expGroup);
            // Если группа с добавленным контактом есть - находим ее и получаем пару группа/контакт.
        } else {
            for (GroupData groupAll : groups) {
                if (groupAll.getContacts().size() > 0) {
                    expGroup = groupAll;
                }

                Contacts contacts = app.db().contacts();
                for (ContactData contact : contacts) {
                    if (contact.getGroups().contains(groupAll)) {
                        expContact = contact;
                    }
                }
            }
        }
    }


    @Test // Удаление контакта из группы.
    public void ContactRemoveFromGroup() {
        app.goTo().contactPage();
        Groups before = expContact.getGroups();
        app.contact().removeFromGroup(expContact, expGroup);
        System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
        System.out.println(">>>>> Контакт " + expContact + " удален из группы " + expGroup + " <<<<<");
        System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");

        ContactData contactFromDb = app.db().getContactInfoById(expContact.getId());
        assertThat(contactFromDb.getGroups(), not(hasItem(expGroup)));
        assertThat(contactFromDb.getGroups(), equalTo(before.without(expGroup)));
    }
}