package ru.stqa.pft.sandbox;

public class Distance {

    public static void main(String[] Args) {

        Point p1 = new Point(3, -3);
        Point p2 = new Point(-6, 2);

        System.out.println("Расстояние между двумя точками " + "P1 (x = " + p1.x + ", y = " + p1.y + ")" + " и " + "P2" + " (x = " + p2.x + ", y = " + p2.y + ")" + " = " + p1.distance(p2));
    }

}