package ru.stqa.pft.sandbox;

public class Distance {

    public static void main(String[] Args) {

        Point p1 = new Point();
        p1.x = 3;
        p1.y = -3;

        Point p2 = new Point();
        p2.x = -6;
        p2.y = 2;

        System.out.println("Расстояние между двумя точками " + "P1 (x = " + p1.x + ", y = " + p1.y + ")" + " и " + "P2" + " (x = " + p2.x + ", y = " + p2.y + ")" + " = " + distance(p1, p2));
    }

    public static double distance(Point p1, Point p2) {
        return Math.sqrt((p1.x - p1.y) * (p1.x - p1.y) + (p2.x - p2.y) * (p2.x - p2.y));
    }

}