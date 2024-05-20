package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();

        IData data = new Data();
        Analyzer analyzer = new Analyzer(data);
        if (analyzer.analyze(str)) {
            System.out.println("Success");
        } else {
            System.out.println("Wrong grammar");
        }
    }
}