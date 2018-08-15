package main;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

/*
* time,status,result
    18:05,200,OK
    18:02,302,Moved Permanently
    18:02,404,Not Found
    18:04,500,Internal Server Error
The required API, for example in C#:

CsvParser csv = new CsvParser("myfile.csv");
if (!csv.IsEmpty)
{
int count = csv.Count;
for (int i = 0; i < csv.Count; ++i) {
dynamic row = csv[i]; Console.WriteLine("{0}: {1}", row.time,
row.result);
}
foreach (CsvRow row in csv)
{
Console.WriteLine("{0}: {1}", row["time"], row["result"]);
}
}
List matches = csv.WhereEquals("result", "OK");
matches = csv.WhereGreaterThan("status", 200);
matches = csv.WhereLessThan("status", 500);

The Where* methods are not allowed to read the entire file into memory and then perform a query on top of that. They should perform
the filtering by reading the file line-by-line (or in somewhat bigger chunks) and filtering in-place.

The CsvParser indexer (in the line csv[i]) returns an object of type dynamic that represents a row. You should probably consider
something along these lines: https://reyrahadian.wordpress.com/2012/02/01/creating-a-dynamic-dictionary-with-c-4-dynamic/

Do not assume that the entire file fits into memory. For example, to implement the Count property, you can read the whole file
line-by-line (or in somewhat bigger chunks), but you should not assume that the entire file fits into memory.

the WhereEquals   method  should support the following types: int, double, string. The WhereGreaterThanN and
the WhereLessThaN methods should support the following types: int, double. There is no need to support additional types.
* */
public class Main <V>  {

    public static void main(String args[]){
        Main m = new Main();
        m.start();
    }
    public void start() {


        CsvParser2 csv2 = new CsvParser2("/Users/samuelc/Documents/projects/java/pdf2json/CsvParser/src/main/namedColumn.csv");//isEmpty getCount  csv[i]
        if (!csv2.isEmpty()) {
            long count = csv2.getCount();
            for (int i = 0; i < count; ++i) {
                HashMap<String, String> rowMap = csv2.getRow(i);
                //System.out.println("{0}: {1}", row.getTime(),   row.getResult());
            }
            csv2.getMatrixMem().forEach(e -> System.out.println( e.get("name")+":"+ e.get("age")));
        }

        List<HashMap<String, String>> matches2 = csv2.whereEquals("name", "martin");//"result", "OK");
        matches2 = csv2.whereGreaterThan("age", 20);//"status", 200);
        matches2 = csv2.whereLessThan("age", 0);//"status", 500);
        matches2 = csv2.whereGreaterThan("age", 20.0);//"status", 200);
        matches2 = csv2.whereLessThan("age", 0.5);//"status", 500);

        Predicate<HashMap<String, String>> priceEquals = r -> r.get("name").equals("martin");
        List<HashMap<String, String>> matches3 = null;
        matches3 = csv2.where(r -> r.get("name").equals("martin"));//"result", "OK");
        matches3 = csv2.where(r -> Integer.valueOf(r.get("age")).intValue() == 27);//"result", "OK");
        matches3 = csv2.where(r -> Double.valueOf(r.get("age")).doubleValue() == 27.0);//"result", "OK");
        matches2 = csv2.where(r -> Double.compare(0, Double.valueOf(r.get("age")).doubleValue()) > 0);//"status", 500);
        matches2 = csv2.where(r -> Double.compare(0, Double.valueOf(r.get("age")).doubleValue()) < 0);//"status", 500); matches2 = csv2.where(r -> Double.compare(0, Double.valueOf(r.get("age")).doubleValue()) > 0);//"status", 500);
        matches2 = csv2.where(r -> Double.compare(0, Integer.valueOf(r.get("age")).intValue()) < 0);//"status", 500); matches2 = csv2.where(r -> Double.compare(0, Double.valueOf(r.get("age")).doubleValue()) > 0);//"status", 500);
        matches2 = csv2.where(r -> Double.compare(0, Integer.valueOf(r.get("age")).intValue()) < 0);//"status", 500);


    }

 }
