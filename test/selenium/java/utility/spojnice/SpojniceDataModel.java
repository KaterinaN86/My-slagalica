package utility.spojnice;

import java.util.List;

public class SpojniceDataModel {
    String headline;
    List<String> column1;
    List<String> column2;

    public SpojniceDataModel() {
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public List<String> getColumn1() {
        return column1;
    }

    public void setColumn1(List<String> column1) {
        this.column1 = column1;
    }

    public List<String> getColumn2() {
        return column2;
    }

    public void setColumn2(List<String> column2) {
        this.column2 = column2;
    }

    @Override
    public String toString() {
        return "Heading: "+this.headline+"\n" + "Column 1: " + this.column1 + "\n" + "Column 2: " + this.column2 + "\n";
    }
}
