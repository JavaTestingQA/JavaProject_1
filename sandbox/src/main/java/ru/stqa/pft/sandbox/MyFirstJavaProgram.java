package ru.stqa.pft.sandbox;

public class MyFirstJavaProgram {

    public static void main(String[] Args) {
        hello("world");
        hello("user");
        hello("Alexander");

        Square s = new Square();
        s.l = 5;
        System.out.println("������� �������� �� �������� " + s.l + " = " + area(s));

        Rectangle r = new Rectangle();
        r.a = 4;
        r.b = 6;
        System.out.println("������� �������������� �� ��������� " + r.a + " � " + r.b + " = " + area(r));
    }

    public static void hello(String sombody) {
        System.out.println("Hello, " + sombody + "!");
    }

    public static double area(Square s) {
        return s.l * s.l;
    }

    public static double area(Rectangle r) {
        return r.a * r.b;
    }
}