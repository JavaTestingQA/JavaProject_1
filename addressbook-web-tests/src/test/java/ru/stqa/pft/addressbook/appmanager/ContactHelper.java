package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void returnToHomePage() {
        click(By.linkText("home page"));
    }

    public void submitContactCreation() {
        click(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomeTelephone());
        type(By.name("email"), contactData.getEmail());

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void deleteSelectedContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void acceptContactDeleting() {
        alertAccepting();
    }

    public void selectContact(int index) {
        wd.findElements(By.xpath("//input[@name='selected[]']")).get(index).click();
    }

    public void initContactModification(int index) {
        wd.findElements(By.xpath("//img[@title='Edit']")).get(index).click();
    }

    public void submitContactModification() {
        click(By.xpath("//input[@name='update'][2]"));
    }

    public void createContact(ContactData contact, boolean creation) {
        initContactCreation();
        fillContactForm(contact, creation);
        submitContactCreation();
        returnToHomePage();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.xpath("//img[@alt='Edit'][1]"));
    }

    public int GetContactCount() {
        return wd.findElements(By.xpath("//input[@name='selected[]']")).size();
    }

    public List<ContactData> getContactList() {

        List<ContactData> contacts = new ArrayList<ContactData>();
        List <WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            List<WebElement> cells = element.findElements(By.tagName("td"));
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            String firstName = cells.get(2).getText();
            String lastName = cells.get(1).getText();
            ContactData contact = new ContactData(id, firstName, lastName, null, null, null, null);
            contacts.add(contact);
        }
        return contacts;
    }
}
