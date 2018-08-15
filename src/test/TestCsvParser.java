package test;

import main.CsvParser2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;


/*
name,age
martin,27
martin,30
gigi  ,41
adam  ,1000

* */
public class TestCsvParser {
    private CsvParser2 csv2 = null;

    @Before
    public void _setup() {
         csv2 = new CsvParser2("/Users/samuelc/Documents/projects/java/pdf2json/CsvParser/src/main/namedColumn.csv");//isEmpty getCount  csv[i]




    }

    @After
    public void _tearDown() {
        csv2 = null;
    }

    @Test
    public void isEquals()  {
        List<HashMap<String, String>> matches2 = csv2.whereEquals("name", "martin");//"result", "OK");
        assertEquals(1, matches2.size());
        assertEquals("martin", matches2.get(0).get("name"));


    }


    @Test
    public void isGreater()  {
        List<HashMap<String, String>> matches2 = csv2.whereGreaterThan("age", 20);//"status", 200);
        assertEquals(4, matches2.size());


    }

    @Test
    public void isLess()  {
        List<HashMap<String, String>> matches2 = csv2.whereLessThan("age", 0);//"status", 500);
        assertEquals(0, matches2.size());


    }

    @Test
    public void isMoreDouble()  {
        List<HashMap<String, String>> matches2 = csv2.whereGreaterThan("age", 27.0);//"status", 200);
        assertEquals(3, matches2.size());
    }


    @Test
    public void isLessDouble()  {
        List<HashMap<String, String>> matches2 = csv2.whereLessThan("age", 27.5);//"status", 500);
        assertEquals(1, matches2.size());

    }

    @Test
    public void isWhere()  {
        List<HashMap<String, String>> matches = null;
        matches = csv2.where(r -> r.get("name").equals("martin"));//"result", "OK");
        assertEquals(1, matches.size());
        matches = csv2.where(r -> Integer.valueOf(r.get("age")).intValue() == 27);//"result", "OK");
        assertEquals(1, matches.size());
        matches = csv2.where(r -> Double.valueOf(r.get("age")).doubleValue() == 27.0);//"result", "OK");
        assertEquals(1, matches.size());
        matches = csv2.where(r -> Double.compare(Double.valueOf(r.get("age")).doubleValue(), 0) > 0);//"status", 500);
        assertEquals(4, matches.size());

    }

    @Test
    public void count(){
        assertEquals(4, csv2.getCount());
    }
}
