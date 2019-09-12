package ru.stqa.pft.sandbox;

public class MyFirstJavaProgram {

    public static void main(String[] Args) {
        hello("world");
        hello("user");
        hello("Alexander");

        Square s = new Square(5);
        System.out.println("Площадь квадрата со стороной " + s.l + " = " + s.area());

        Rectangle r = new Rectangle(4, 6);
        System.out.println("Площадь прямоугольника со сторонами " + r.a + " и " + r.b + " = " + r.area());
    }

    public static void hello(String sombody) {
        System.out.println("Hello, " + sombody + "!");
    }

}