package com.garnagaaa.lesson07.task02.app1;

/**
 * @author Aleksei Garnaga
 */
public class Program {
    public static void main(String[] args) throws Exception {
        String[] words = {"and", "new", "old", "test"};
        Generator gen = new Generator();
        gen.getFiles(args[0], 2, 100, words, 2);
    }
}