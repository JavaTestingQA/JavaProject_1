package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;
import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import org.testng.SkipException;

import java.io.File;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

public class TestBase {

    protected static final ApplicationManager app =
            new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
        app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.bak");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.ftp().restore("config_inc.php.bak", "config_inc.php");
        app.stop();
    }

    // Проверяем статус бага в трекере (возвращаем false если статус "решена", возвращаем true если статус какой-либо другой)
    boolean isIssueOpen(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = app.soap().getMantisConnect();
        IssueData issue = mc.mc_issue_get("administrator", "root", BigInteger.valueOf(issueId));
        String issueStatus = issue.getStatus().getName();
        if (issueStatus.equals("resolved")) {
            return false;
        } else return true;
    }

    // Пропускаем тест, если имеется не решенная бага.
    public void skipIfNotFixed(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }
}