package ru.stqa.pft.soap;

import com.lavasoft.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

    @Test
    public void testMyIP() {
        String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("188.32.48.144");
        assertEquals(ipLocation, "<GeoIP><Country>RU</Country><State>48</State></GeoIP>");
    }

    @Test
    public void testBadIP() {
        String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("188.32.48.xxx");
        assertEquals(ipLocation, "<GeoIP><Country>RU</Country><State>48</State></GeoIP>");
    }
}
