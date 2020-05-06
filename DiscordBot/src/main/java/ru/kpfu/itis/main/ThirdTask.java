package ru.kpfu.itis.main;

import au.com.bytecode.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ThirdTask {
    public static void main(String[] args) throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader("transactions.csv"));
        List<String[]> allRows = csvReader.readAll();
        Map<String, Integer> productFrequent = new HashMap<>();
        Map<Long, List<String>> baskets = new HashMap<>();
        for(String[] row : allRows) {
            String product = row[0].substring(0, row[0].indexOf(";"));
            Long basket = Long.parseLong(row[0].substring(row[0].indexOf(";") + 1));
                if (baskets.containsKey(basket)) {
                    List<String> list = baskets.get(basket);
                    list.add(row[0].substring(0, row[0].indexOf(";")));
                    baskets.put(basket, list);
                } else {
                    baskets.put(basket, new ArrayList<>());
                }
            if (productFrequent.containsKey(product)) {
                productFrequent.put(product, productFrequent.get(product) + 1);
            } else {
                productFrequent.put(product, 0);
            }
            System.out.println(Arrays.toString(row));
        }
        Map<Long, List<Doubletone>> doubletoneMap = new HashMap<>();
        Set<Long> basketsKey = baskets.keySet();
        Long[] basketId = (Long[]) basketsKey.toArray();
        Collection<List<String>> basketValues = baskets.values();
        for (int i = 0; i < basketValues.size(); i++) {

        }
    }

    private class Doubletone{
        private String firstDoubletone;
        private String secondDoubletone;
    }
}


