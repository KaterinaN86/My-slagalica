package utility.spojnice;

import utility.CsvDataProviders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpojniceData {

    public static List<SpojniceDataModel> readData() {
        List<SpojniceDataModel> dataList = new ArrayList<>();
        CsvDataProviders.csvReader(SpojniceData.class.getMethods()[0]);
        List<Map<String, String>> mapList = CsvDataProviders.mapList;
        for (Map<String, String> element : mapList) {
            SpojniceDataModel dataObject = new SpojniceDataModel();
            dataObject.setHeadline(element.get("Headline"));
            List<String> tempList = List.of(element.get("Column1").split(";"));
            dataObject.setColumn1(tempList);
            tempList = List.of(element.get("Column2").split(";"));
            dataObject.setColumn2(tempList);
            dataList.add(dataObject);
        }
        return dataList;
    }
}
