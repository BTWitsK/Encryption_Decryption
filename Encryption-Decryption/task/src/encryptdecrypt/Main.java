package encryptdecrypt;
import java.util.*;

public class Main {
    static StringBuilder output = new StringBuilder();
    static HashMap<String, String> argMap = new HashMap<>();

    public static void parseArgs(String[] arg) {
       for (int i = 0; i < arg.length; i += 2) {
           argMap.put(arg[i], arg[i + 1]);
       }
    }

    public static void encryptMessage(String message, int shift) {
        for (int i = 0; i < message.length(); i++) {
            output.append((char)(message.charAt(i) + shift));
        }
        System.out.println(output);
    }

    public static void decryptMessage(String message, int shift) {
        for (int i = 0; i < message.length(); i++) {
            output.append((char) (message.charAt(i) - shift));
        }
        System.out.println(output);
    }

    public static void main(String[] args) {
        parseArgs(args);

        switch (argMap.getOrDefault("-mode", "enc")) {
            case "enc" -> encryptMessage(argMap.getOrDefault("-data", ""),
                    Integer.parseInt(argMap.getOrDefault("-key", "0")));
            case "dec" -> decryptMessage(argMap.getOrDefault("-data", ""),
                    Integer.parseInt(argMap.getOrDefault("-key", "0")));
        }
    }
}