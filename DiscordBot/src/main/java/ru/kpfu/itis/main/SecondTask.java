package ru.kpfu.itis.main;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.Adler32;

public class SecondTask {
    public static void main(String[] args) {
        String text = "Мимо белого яблока луны, " +
                "Мимо красного яблока заката " +
                "Облака из неведомой страны " +
                "К нам спешат, и опять бегут куда-то. " +
                "Облака — белогривые лошадки! " +
                "Облака — что вы мчитесь без оглядки? " +
                "Не смотрите вы, пожалуйста, с высока, " +
                "А по небу прокатите нас, облака! " +
                "Мы помчимся в заоблачную даль, " +
                "Мимо гаснущих звезд на небосклоне. " +
                "К нам неслышно опустится звезда, " +
                "И ромашкой останется в ладони! " +
                "Облака — белогривые лошадки! " +
                "Облака — что вы мчитесь без оглядки? " +
                "Не смотрите вы, пожалуйста, с высока, " +
                "А по небу прокатите нас, облака! ";
        String[] words = text.split(" ");
        List<String> list = new LinkedList<>(Arrays.asList(words));
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains("!")) list.set(i, list.get(i).substring(0, list.get(i).indexOf("!")));
            if (list.get(i).contains(",")) list.set(i, list.get(i).substring(0, list.get(i).indexOf(",")));
            if (list.get(i).contains("?")) list.set(i, list.get(i).substring(0, list.get(i).indexOf("?")));
            if (list.get(i).contains(".")) list.set(i, list.get(i).substring(0, list.get(i).indexOf(".")));
        }

        list.remove(20);
        list.remove(23);
        list.remove(61);
        list.remove(64);
        System.out.println(list.size());
        int[] countableBloomFilter = new int[18];
        Adler32 adler32 = new Adler32();
        for (int i = 0; i < list.size(); i++) {
            adler32.update(list.get(i).getBytes());
            int index = Math.abs((int)adler32.getValue()%18);
            countableBloomFilter[index]++;
        }
        for (int i = 0; i < countableBloomFilter.length; i++) {
            System.out.print(countableBloomFilter[i] + " ");
        }
    }
}
