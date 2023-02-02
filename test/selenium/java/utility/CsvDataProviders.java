package utility;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

//Utility class used for reading data from CSV files.
public class CsvDataProviders {

    public static List<Map<String, String>> mapList;

    /**
     * Method used as data provider.
     *
     * @param method Method class object used to access declaring class and the name of the method that calls csv data provider.
     * @return Iterator object, used to loop over the list of Map objects.
     */
    @DataProvider(name = "csvReader")
    public static Iterator<Object[]> csvReader(Method method) {
        //List of objects used for storing Map objects for each key/value pair read from csv file.
        List<Object[]> list = new ArrayList<>();
        mapList=new ArrayList<>();
        //Path to csv file. Name of the file corresponds to name of test method that uses this data provider and name of package corresponds to declaring class name.
        String pathname = System.getProperty("user.dir") + File.separator + "test" + File.separator + "selenium" + File.separator + "resources" + File.separator
                + "dataProviders" + File.separator + method.getDeclaringClass().getSimpleName() + File.separator
                + method.getName() + ".csv";
        //File object used for reading data from file.
        File file = new File(pathname);
        try {
            //Initializing reader object.
            CSVReader reader = new CSVReader(new FileReader(file));
            //Reading first line of csv file (header).
            String[] keys = reader.readNext();
            if (keys != null) {
                //Array of strings that stores each comma separated value.
                String[] dataParts;
                //Using reader object data is read from csv file line by line. Each read line is stored in dataParts array. When end of file has been reached readNext method results in null object.
                while ((dataParts = reader.readNext()) != null) {
                    //Map object is created for each read line of comma separated values.
                    Map<String, String> testData = new HashMap<>();
                    //Looping over the array of header strings (first line of csv file).
                    for (int i = 0; i < keys.length; i++) {
                        //Storing key/value pari in testData Map object. Key always matches one of header strings, and is paired with corresponding read value from file. Example: [no,1], [username, testUser], [password, 123456] and so on...
                        testData.put(keys[i], dataParts[i]);
                    }
                    //Create Object array for each map object and add it to main list.
                    list.add(new Object[]{testData});
                    mapList.add(testData);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File " + pathname + " was not found.\n" + Arrays.toString(e.getStackTrace()));
        } catch (IOException e) {
            throw new RuntimeException("Could not read " + pathname + " file.\n" + Arrays.toString(e.getStackTrace()));
        } catch (CsvValidationException e) {
            throw new RuntimeException("CSV validation failed for " + pathname + " file.\n" + Arrays.toString(e.getStackTrace()));
        }
        //An Iterator object is returned, to enable easier access to list elements.
        return list.iterator();
    }

}