package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.db().groups().size() == 0) {
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test
    public void testGroupDeletion() throws Exception {
        Groups before = app.db().groups();
        GroupData deletedGroup = before.iterator().next();
        app.group().delete(deletedGroup);
        assertThat(app.group().count(), equalTo(before.size() - 1));
        Groups after = app.db().groups();
        assertThat(after, equalTo(before.without(deletedGroup)));
        verifyGroupListInUi(); // Для включения данной проверки (сравнение списков групп загруженных из интерфейса и БД), добавить в конфигурацию запуска опцию -DverifyUI=true
    }
}