package ru.stqa.pft.addressbook.model;

public class ContactData {
    private final String firstname;
    private final String lastname;
    private final String address;
    private final String homeTelephone;
    private final String email;
    private String group;

    public ContactData(String firstname, String lastname, String address, String homeTelephone, String email, String group) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.homeTelephone = homeTelephone;
        this.email = email;
        this.group = group;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAddress() {
        return address;
    }

    public String getHomeTelephone() {
        return homeTelephone;
    }

    public String getEmail() {
        return email;
    }

    public String getGroup() {
        return group;
    }
}
