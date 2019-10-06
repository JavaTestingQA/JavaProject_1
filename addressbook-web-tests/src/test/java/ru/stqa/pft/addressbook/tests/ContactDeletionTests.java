package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase{

  @Test
  public void testContactDeletion() throws Exception {
    app.getNavigationHelper().goToHomePage();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Александр", "Дрозд", "г. Москва, улица Рябиновая, дом 7, корпус 5.", "8-978-485-74-11", "alexqamail@qa.ru", "test1"), true);
    }
    int before = app.getContactHelper().GetContactCount();
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContact();
    app.getContactHelper().acceptContactDeleting();
    app.getNavigationHelper().goToHomePage();
    int after = app.getContactHelper().GetContactCount();
    Assert.assertEquals(after, before - 1);
    app.getSessionHelper().logout();
  }
}