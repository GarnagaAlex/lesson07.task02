package com.garnagaaa.lesson07.task02.app1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Класс генератор текста
 */
public class Generator {

    private int countAbzac = 0;

    /**
     * Метод генерации текстовых файлов
     *
     * @param path Путь к файлу
     * @param n Кол-во файлов
     * @param size Кол-во абзацев
     * @param words Массив слов вставляемых на основе вероятности
     * @param probability Вероятность появления слова из переданного массива
     */
    public void getFiles(String path, int n, int size, String[] words, int probability) {
        for (int i = 0; i < n; i++) {
            try (FileWriter writer = new FileWriter(path + "text" + (i + 1) + ".txt", true)) {
                String text = createFinalText(size, probability, words);
                writer.write(text);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            countAbzac = 0;
        }
    }

    /**
     * Метод генерации слова
     *
     * @return Слово
     */
    private String createWord() {
        char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        int MAXLengthName = 15;
        int MINLengthName = 1;

        int length = randomArrGenerator(MAXLengthName, MINLengthName, 1)[0];
        int[] name = randomArrGenerator(ALPHABET.length - 1, 0, length);
        StringBuilder sb = new StringBuilder();
        for (int j : name) {
            sb.append(ALPHABET[j]);
        }
        return sb.toString();
    }

    /**
     * Генератор массива случайных чисел (RAG)
     *
     * @param max   Максимальное число
     * @param min   Минимальное число
     * @param count Кол-во чисел в массива
     * @return int[] Массив случайных чисел
     */
    private int[] randomArrGenerator(int max, int min, int count) {
        Random rd = new Random();
        int[] temp = new int[count];
        for (int i = 0; i < count; i++) {
            temp[i] = rd.nextInt(max - min) + min;
        }
        return temp;
    }

    /**
     * Метод генерации предложения
     *
     * @param probability Вероятность появления слова из переданного массива
     * @param words Массив слов вставляемых на основе вероятности
     * @return Предложение
     */
    private String createPredlog(int probability, String[] words) {
        int MAXLengthName = 15;
        int MINLengthName = 1;
        int length = randomArrGenerator(MAXLengthName, MINLengthName, 1)[0];
        String predlog = "";
        String[] razdelitel = new String[]{" ", ", "};
        for (int i = 0; i < length; i++) {
            int id = randomArrGenerator(1, 0, 1)[0];
            predlog += createWord() + razdelitel[id];
        }

        String[] punkt = new String[]{".", "!", "?"};
        int id = randomArrGenerator(punkt.length - 1, 0, 1)[0];

        if (veroyatnost(probability)) {
            predlog = replaceOldWordToNew(predlog, words);
        }

        if ((predlog.charAt(predlog.length() - 2)) == ',') {
            predlog = predlog.substring(0, predlog.length() - 1);
        }
        return predlog.substring(0, 1).toUpperCase() + predlog.substring(1, predlog.length() - 1) + punkt[id];
    }

    /**
     * Метод генерции абзаца
     *
     * @param probability Вероятность появления слова из переданного массива
     * @param words Массив слов вставляемых на основе вероятности
     * @return Абзац
     */
    private String createAbzac(int probability, String[] words) {
        int MAX = 20;
        int length = randomArrGenerator(MAX, 1, 1)[0];
        String Abzac = "";
        for (int i = 0; i < length; i++) {
            Abzac += createPredlog(probability, words) + " ";
            countAbzac += 1;
        }
        return Abzac + "\r\n";
    }

    /**
     * Метод герерации текста документа
     *
     * @param size Размер докумена в вбзацах
     * @param probability Вероятность появления слова из переданного массива
     * @param words Массив слов вставляемых на основе вероятности
     * @return Текст документа
     */
    private String createFinalText(int size, int probability, String[] words) {
        String text = "";
        while (countAbzac < size + 1) {
            text += createAbzac(probability, words);
        }
        return text;
    }

    /**
     * Метод возвращаюий признак попадания в вероятность
     *
     * @param probability Вероятность появления слова из переданного массива
     */
    private Boolean veroyatnost(int probability) {
        return Math.random() <= 1.0 / probability;
    }

    /**
     * Метод выборки случаного слова из массива
     *
     * @param words Массив слов вставляемых на основе вероятности
     * @return Случайное слово из массива
     */
    private String getRandomWord(String[] words) {
        int id = randomArrGenerator(words.length, 0, 1)[0];
        return words[id];
    }

    /**
     * @param predloj Предложение в котором нужно выполнить замену слова
     * @param words Массив слов вставляемых на основе вероятности
     * @return Предложение
     */
    private String replaceOldWordToNew(String predloj, String[] words) {
        String[] wordArr = predloj.split(" ");
        int id = randomArrGenerator(wordArr.length, 0, 1)[0];
        wordArr[id] = getRandomWord(words);
        StringBuilder builder = new StringBuilder();
        for (String s : wordArr) {
            builder.append(s).append(" ");
        }
        return builder.toString();
    }

}
