package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase{

  @Test
  public void testContactDeletion() throws Exception {
    app.selectContact();
    app.deleteSelectedContact();
    app.acceptContactDeleting();
    app.goToHomePage();
    app.logout();
  }
}