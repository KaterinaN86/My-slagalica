package fitnesse;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DynamicDecisionTable {
    private static Map<String, Integer> COIN_EVAL = new HashMap<String, Integer>();

    static {
        COIN_EVAL.put("1cent", 1);
        COIN_EVAL.put("2cent", 2);
        COIN_EVAL.put("5cent", 5);
        COIN_EVAL.put("10cent", 10);
        COIN_EVAL.put("20cent", 20);
        COIN_EVAL.put("50cent", 50);
        COIN_EVAL.put("1eur", 100);
        COIN_EVAL.put("2eur", 200);
    }

    private Integer totalEuros = 0;

    public void reset() {
        this.totalEuros = 0;
    }

    //Setting the total value of coins.
    public void set(String coin, Integer amount) {
        if (!COIN_EVAL.containsKey(coin)) {
            throw new IllegalArgumentException("Unknown coin type" + coin);
        }
        totalEuros += amount * COIN_EVAL.get(coin);
    }

    //Getting the value of coins to total Euros.
    public String get(String requestedValue) {
        if ("Euros total".equals(requestedValue)) {
            return String.format(Locale.US, "%2f", totalEuros / 100.0);
        }
        return String.format("%d", totalEuros);
    }

    public static void main(String[] args) {
        DynamicDecisionTable dt = new DynamicDecisionTable();
        dt.set("20cent",3);
        System.out.println(dt.get("total"));
    }
}
