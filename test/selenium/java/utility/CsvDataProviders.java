package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

import com.opencsv.exceptions.CsvValidationException;
import org.testng.annotations.DataProvider;

import com.opencsv.CSVReader;

public class CsvDataProviders {

    @DataProvider(name = "csvReader")
    public static Iterator<Object[]> csvReader(Method method) {
        List<Object[]> list = new ArrayList<>();
        String pathname = System.getProperty("user.dir")+ File.separator + "test" + File.separator + "selenium"+ File.separator + "resources" + File.separator
                + "dataProviders" + File.separator + method.getDeclaringClass().getSimpleName() + File.separator
                + method.getName() + ".csv";
        File file = new File(pathname);
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            String[] keys = reader.readNext();
            if (keys != null) {
                String[] dataParts;
                while ((dataParts = reader.readNext()) != null) {
                    Map<String, String> testData = new HashMap<>();
                    for (int i = 0; i < keys.length; i++) {
                        testData.put(keys[i], dataParts[i]);
                    }
                    list.add(new Object[] { testData });
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

        return list.iterator();
    }

}