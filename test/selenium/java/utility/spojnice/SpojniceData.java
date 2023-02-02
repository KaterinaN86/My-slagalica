package utility.spojnice;


import com.google.common.collect.Lists;
import utility.CsvDataProviders;

import java.util.*;
import java.util.stream.Collectors;

public class SpojniceData {

    List<SpojniceDataModel> dataList;

    public SpojniceData() {
        this.dataList = new ArrayList<>();
    }
    public void readData() {
     CsvDataProviders.csvReader(SpojniceData.class.getMethods()[0]);
       List<Map<String,String>> mapList = CsvDataProviders.mapList;
        for (Map<String, String> element : mapList) {
            SpojniceDataModel dataObject = new SpojniceDataModel();
            dataObject.setHeadline(element.get("Headline"));
            List<String> tempList = List.of(element.get("Column1").split(";"));
            dataObject.setColumn1(tempList);
            tempList=List.of(element.get("Column2").split(";"));
            dataObject.setColumn2(tempList);
            dataList.add(dataObject);
        }
        for (SpojniceDataModel el:dataList){
            System.out.println(el);
        }
    }
}
