package encryptdecrypt;
import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder output = new StringBuilder();
    static HashMap<String, String> argMap = new HashMap<>();
    static Scanner scanner;

    public static void parseArgs(String[] arg) {
       for (int i = 0; i < arg.length; i += 2) {
           argMap.put(arg[i], arg[i + 1]);
       }
    }

    public static void encryptMessage(String message, int shift) {
        for (int i = 0; i < message.length(); i++) {
            output.append((char)(message.charAt(i) + shift));
        }
        printMessage(output.toString());
    }

    public static void decryptMessage(String message, int shift) {
        for (int i = 0; i < message.length(); i++) {
            output.append((char) (message.charAt(i) - shift));
        }
        printMessage(output.toString());
    }

    public static void printMessage(String output) {
        if (argMap.containsKey("-out")) {
            try (PrintWriter writer = new PrintWriter(argMap.get("-out"))) {
                writer.print(output);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println(output);
        }
    }

    public static String parseMessage() {
       if (argMap.containsKey("-data")) {
           return argMap.get("-data");
       } else {
           try {
               scanner = new Scanner(new FileReader(argMap.get("-in")));
               return scanner.nextLine();
           } catch (IOException e) {
               System.out.println("Error: No input file found");
           }
       }
       return "";
    }

    public static void main(String[] args) {
        parseArgs(args);

        switch (argMap.getOrDefault("-mode", "enc")) {
            case "enc" -> encryptMessage(parseMessage(),
                    Integer.parseInt(argMap.getOrDefault("-key", "0")));
            case "dec" -> decryptMessage(parseMessage(),
                    Integer.parseInt(argMap.getOrDefault("-key", "0")));
        }
    }
}