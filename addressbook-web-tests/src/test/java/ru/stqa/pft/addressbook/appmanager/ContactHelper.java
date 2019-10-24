package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

import java.security.AllPermission;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void returnToHomePage() {
        click(By.linkText("home page"));
    }

    public void goToHomePageThruHeader() {
        click(By.linkText("home"));
    }

    public void submitContactCreation() {
        click(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomeTelephone());
        type(By.name("mobile"), contactData.getMobileTelephone());
        type(By.name("work"), contactData.getWorkTelephone());
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());
        attach(By.name("photo"), contactData.getPhoto());

        if (creation) {
            if (contactData.getGroups().size() > 0) {
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
                }
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

    public void selectContactById(int id) {
        wd.findElement(By.xpath("//input[@value='" + id + "']")).click();
    }

    public void initContactModificationById(int id) {
       wd.findElement(By.xpath("//a[@href='edit.php?id=" + id + "']")).click();
    }

    public void submitContactModification() {
        click(By.xpath("//input[@name='update'][2]"));
    }

    public void create(ContactData contact, boolean creation) {
        initContactCreation();
        fillContactForm(contact, creation);
        submitContactCreation();
        contactCache = null;
        returnToHomePage();
    }

    public void modify(ContactData contact) {
        initContactModificationById(contact.getId()); // Т.к для модификации выбор чекбоксом не нужен, выбор записи происходит здесь.
        fillContactForm(contact, false);
        submitContactModification();
        contactCache = null;
        returnToHomePage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContact();
        acceptContactDeleting();
        contactCache = null;
        goToHomePageThruHeader();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.xpath("//img[@alt='Edit'][1]"));
    }

    public int count() {
        return wd.findElements(By.xpath("//input[@name='selected[]']")).size();
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }

        contactCache = new Contacts();
        List <WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            List<WebElement> cells = element.findElements(By.tagName("td"));
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            String firstName = cells.get(2).getText();
            String lastName = cells.get(1).getText();
            String address = cells.get(3).getText();
            String allemails = cells.get(4).getText();
            String allphones = cells.get(5).getText();
            contactCache.add(new ContactData()
                    .withId(id)
                    .withFirstname(firstName)
                    .withLastname(lastName)
                    .withAddress(address)
                    .withAllEmails(allemails)
                    .withAllPhones(allphones));
        }
        return new Contacts(contactCache);
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");

        wd.navigate().back();
        return new ContactData()
                .withId(contact.getId())
                .withFirstname(firstname)
                .withLastname(lastname)
                .withAddress(address)
                .withHomeTelephone(home)
                .withMobileTelephone(mobile)
                .withWorkTelephone(work)
                .withEmail(email)
                .withEmail2(email2)
                .withEmail3(email3);
    }
}