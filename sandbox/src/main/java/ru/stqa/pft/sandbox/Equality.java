package ru.stqa.pft.sandbox;

public class Equality {

    public static void main(String[] arg) {
        String s1 = "firefox";
        String s2 = "firefox";

        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
    }

    // equals - когда сравниваем объекты
    // == - когда сравниваем цифры
}
