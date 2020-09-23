package ru.itis.hw1.utils;

import ru.itis.hw1.models.User;

import javax.jws.soap.SOAPBinding;
import java.util.Scanner;

public class UserFromConsole {
    public static User getUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя: ");
        String name = scanner.nextLine();
        System.out.println("Введите фамилию: ");
        String surname = scanner.nextLine();
        System.out.println("Введите ваш возраст: ");
        int age = scanner.nextInt();
        System.out.println("Введите номер паспорта: ");
        long passportNumber = scanner.nextLong();
        System.out.println("Введите дату выдачи паспорта(dd/MM/yyyy): ");
        String dateOfIssue = scanner.next();
        return new User(name, surname, age, passportNumber, dateOfIssue);
    }
}
