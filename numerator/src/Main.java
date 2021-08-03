import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String text = "zzzzz ${id} xxxxx ${id2} ccccc #{VK_1} vvvvv ${id1+1}" +
                "zzzzz ${id1+22222} zzzzz ${id3-1} xxxx ${id4=5}";




    }

    private static String numerate(String text){

    }

    static ArrayList<Number> numerators = new ArrayList<>();
    private static void getNumerators(String text){
        Pattern pattern = Pattern.compile("[$]{1}\\{\\w+[+-=]?\\w+\\}");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()){
            Number numerator = new Number(matcher.group());
            numerators.add(numerator);
        }
    }



    private static void executeNumerators(){
        for(Number number : numerators){
            number.execute();
        }
    }

    private static void printNumerators(){
        System.out.println("-------------------");
        for(Number number : numerators){
            System.out.println(number + ": " + number.getValue());
        }
    }
}
