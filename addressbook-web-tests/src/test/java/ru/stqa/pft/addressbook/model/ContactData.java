package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;

@Entity
@Table(name = "addressbook")
@XStreamAlias("contact")
public class ContactData {
    @XStreamOmitField
    @Id
    @Column(name = "id")
    private int id = Integer.MAX_VALUE;

    @Expose
    @Column(name = "firstname")
    private String firstname;

    @Expose
    @Column(name = "lastname")
    private String lastname;

    @Expose
    @Column(name = "address")
    @Type(type = "text")
    private String address;

    @Expose
    @Column(name = "home")
    @Type(type = "text")
    private String homeTelephone;

    @Column(name = "mobile")
    @Type(type = "text")
    private String mobileTelephone;

    @Column(name = "work")
    @Type(type = "text")
    private String workTelephone;

    @Transient
    private String allPhones;

    @Expose
    @Column(name = "email")
    @Type(type = "text")
    private String email;

    @Column(name = "email2")
    @Type(type = "text")
    private String email2;

    @Column(name = "email3")
    @Type(type = "text")
    private String email3;

    @Transient
    private String allEmails;

    @Expose
    @Transient
    private String group;

    @Column(name = "photo")
    @Type(type = "text")
    private String photo;


    public int getId() {
        return id;
    }

    public ContactData withId(int id) {
        this.id = id;
        return this;
    }

    public ContactData withFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public ContactData withLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public ContactData withAddress(String address) {
        this.address = address;
        return this;
    }

    public ContactData withHomeTelephone(String homeTelephone) {
        this.homeTelephone = homeTelephone;
        return this;
    }

    public ContactData withMobileTelephone(String mobileTelephone) {
        this.mobileTelephone = mobileTelephone;
        return this;
    }

    public ContactData withWorkTelephone(String workTelephone) {
        this.workTelephone = workTelephone;
        return this;
    }

    public ContactData withAllPhones(String allPhones) {
        this.allPhones = allPhones;
        return this;
    }

    public ContactData withEmail(String email) {
        this.email = email;
        return this;
    }

    public ContactData withEmail2(String email2) {
        this.email2 = email2;
        return this;
    }

    public ContactData withEmail3(String email3) {
        this.email3 = email3;
        return this;
    }

    public ContactData withAllEmails(String allEmails) {
        this.allEmails = allEmails;
        return this;
    }

    public ContactData withGroup(String group) {
        this.group = group;
        return this;
    }

    public ContactData withPhoto(File photo) {
        this.photo = photo.getPath();
        return this;
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

    public String getEmail2() {
        return email2;
    }

    public String getEmail3() {
        return email3;
    }

    public String getAllEmails() {
        return allEmails;
    }

    public String getGroup() {
        return group;
    }

    public String getMobileTelephone() {
        return mobileTelephone;
    }

    public String getWorkTelephone() {
        return workTelephone;
    }

    public String getAllPhones() {
        return allPhones;
    }

    public File getPhoto() {
        return new File(photo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (id != that.id) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        return lastname != null ? lastname.equals(that.lastname) : that.lastname == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
