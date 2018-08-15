package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvParser2 {

    private Path path;
    private int    count;
    private List<HashMap<String, String>> matrixMem = new ArrayList<>();

    private String[] headers ;
    private String regex;

    public CsvParser2(String aPath) {
        this(aPath, ",");
    }

    public CsvParser2(String aPath, String aRegex) {
        this.regex = aRegex;

        File file = new File(aPath);
        if (!file.exists())
            return;
        path = Paths.get(aPath);


        try {
             headers = Files.lines(path).limit(1).collect(Collectors.toList()).get(0).split(regex);
              Stream<String> matrixStream;

             matrixStream = Files.lines(path).skip(1);

            matrixStream.limit(1000).forEach(e -> {
                                                            HashMap<String, String> rowMap = new HashMap<String, String>();
                                                            String elements[] = e.split(regex);
                                                            for (int i = 0; i < elements.length; i++) {
                                                                rowMap.put(headers[i].toString(), elements[i] + "");
                                                            }
                                                            matrixMem.add(rowMap);
                                                            System.out.println(e);
                                                        });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<HashMap<String, String>> getMatrixMem() {
        return matrixMem;
    }

    public boolean isEmpty () {
        return (this.getCount() <= 0);
     //   getCount csv[ i]
    }

    public long getCount()   {

        try {
            return  Files.lines(path).skip(1).count();
        } catch (IOException e) {
            return 0;
        }
    }

    public HashMap<String, String> getRow(int rowNum)    {
        //return  matrixMem.get(rowNum);
            HashMap<String, String> rowMap = new HashMap<String, String>();

        try {
            Files.lines(path).skip(rowNum+1).forEach(e -> {

                                                            String elements[] = e.split(regex);
                                                            for (int i = 0; i < elements.length; i++) {
                                                                rowMap.put(headers[i].toString(), elements[i] + "");
                                                            }

                });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rowMap;

    }

    public List<HashMap<String, String>> where(Predicate<HashMap<String, String>> p) {

        Stream<HashMap<String, String>> lists = null;
        HashMap<String, String> rowMap = new HashMap<String, String>();
        try {
            lists = Files.lines(path).skip(1)
                                     .map(e ->  e.split(regex) )
                                     .map(a -> {
                                                 for (int i = 0; i < a.length; i++)
                                                     rowMap.put(headers[i].toString(), a[i] + "");
                                                 return rowMap;
                                             })
                                    .filter(  el -> p.test(el) );

        } catch (IOException e) {
            e.printStackTrace();
            return lists.collect(Collectors.toList());
        }
        return lists.collect(Collectors.toList());
    }

    public List<HashMap<String, String>> whereEquals(String col, String value) {
        if (value == null)
            return null;
        List<HashMap<String, String>> lists = matrixMem.stream().filter(e->value.equals(e.get(col))).collect(Collectors.toList());
        return lists;
    }

    public List<HashMap<String, String>> whereEquals(String col, double value) {

        Stream<HashMap<String, String>> lists = matrixMem.stream().filter(e -> Double.compare(value, Double.valueOf(e.get(col)).doubleValue()) == 0);
        return lists.collect(Collectors.toList());
    }

    public List<HashMap<String, String>> whereEquals(String col, int value) {

        Stream<HashMap<String, String>> lists = matrixMem.stream().filter(e -> Integer.compare(value, Integer.valueOf(e.get(col)).intValue()) == 0);
        return lists.collect(Collectors.toList());
    }

    public List<HashMap<String, String>> whereGreaterThan(String col, int value) {

        List<HashMap<String, String>> lists = matrixMem.stream().filter(e->value < Integer.valueOf(e.get(col))).collect(Collectors.toList());
        return lists;
    }

    public List<HashMap<String, String>> whereGreaterThan(String col, double value) {

        List<HashMap<String, String>> lists = matrixMem.stream().filter(e->value < Double.valueOf(e.get(col))).collect(Collectors.toList());
        return lists;
    }

    public List<HashMap<String, String>> whereLessThan(String col, int value) {

        List<HashMap<String, String>> lists = matrixMem.stream().filter(e->value > Integer.valueOf(e.get(col))).collect(Collectors.toList());
        return lists;
    }

    public List<HashMap<String, String>> whereLessThan(String col, double value) {

        List<HashMap<String, String>> lists = matrixMem.stream().filter(e->value > Double.valueOf(e.get(col))).collect(Collectors.toList());
        return lists;
    }

}
