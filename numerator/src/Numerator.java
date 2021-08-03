import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Numerator {

    private static Numerator instance;
    public static Numerator getInstance(){
        if (instance == null){
            instance = new Numerator();
        }
        return instance;
    }

    private static final Pattern pattern = Pattern.compile("[$]{1}\\{\\w+[+-=]?\\w+\\}");
    private static final HashMap<String, Integer> numbers = new HashMap<>();

    public String numerate(String text){
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()){
            String numeratorString = matcher.group();
            String name = getNumeratorName(numeratorString);
            int delta = getNumeratorDelta(numeratorString);
            Operation operation = getNumeratorOperation(numeratorString);
        }

    }

    private String getNumeratorName(String numeratorString){
        String name = "";
        int start = 2;
        int end;
        if (numeratorString.contains("+")){
            end = numeratorString.indexOf("+");
        } else if (numeratorString.contains("-")){
            end = numeratorString.indexOf("-");
        } else if (numeratorString.contains("=")){
            end = numeratorString.indexOf("=");
        } else {
            end = numeratorString.length() - 1;
        }
        name = numeratorString.substring(start, end);
        return name;
    }

    private int getNumeratorDelta(String numeratorString){
        int delta;
        int start;
        int end;

        if (numeratorString.contains("+")){
            start = numeratorString.indexOf("+");
            end = numeratorString.length() - 1;
            delta = Integer.parseInt(numeratorString.substring(start, end));

        } else if (numeratorString.contains("-")){
            start = numeratorString.indexOf("-");
            end = numeratorString.length() - 1;
            delta = Integer.parseInt(numeratorString.substring(start, end));

        } else if (numeratorString.contains("=")){
            start = numeratorString.indexOf("=");
            end = numeratorString.length() - 1;
            delta = Integer.parseInt(numeratorString.substring(start, end));

        } else {
            start = numeratorString.indexOf("+");
            end = numeratorString.length() - 1;
            delta = Integer.parseInt(numeratorString.substring(start, end));
        }
        name = numeratorString.substring(start, end);
        return name;
    }

    private Operation getNumeratorOperation(String numeratorString){

    }

    //    Matcher matcher = pattern.matcher(text);
//        while (matcher.find()){
//        Number number = new Number(matcher.group());
//        if (!numbers.containsKey(number.toString())){
//            numbers.put(number.toString(), number);
//        }
//    }


}
