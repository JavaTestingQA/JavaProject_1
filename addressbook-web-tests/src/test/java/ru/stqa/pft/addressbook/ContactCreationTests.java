package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase{

    @Test
    public void testContactCreation() throws Exception {
        initContactCreation();
        fillContactForm(new ContactData("Александр", "Дрозд", "г. Москва, улица Рябиновая, дом 7, корпус 5.", "8-978-485-74-11", "alexqamail@qa.ru"));
        submitContactCreation();
        returnToHomePage();
        logout();
    }
}
