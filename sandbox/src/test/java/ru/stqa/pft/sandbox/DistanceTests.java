package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DistanceTests {

    @Test
    public void testDistanceVisible() {
        Point p1 = new Point(3, -3);
        Point p2 = new Point(-6, 2);
        Assert.assertEquals(p1.distance(p2), 10);
    }

    @Test
    public void testDistanceSimple() {
        Point p1 = new Point(3, -3);
        Point p2 = new Point(-6, 2);
        assert(p1.distance(p2) == 10);
    }
}