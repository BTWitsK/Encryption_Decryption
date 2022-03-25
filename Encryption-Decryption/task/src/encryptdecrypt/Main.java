package encryptdecrypt;
import java.util.*;

public class Main {
    final static String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    static Scanner scanner = new Scanner(System.in);
    static StringBuilder output = new StringBuilder();

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
        String operation = scanner.nextLine();
        String message = scanner.nextLine();
        int shift = scanner.nextInt();

        switch (operation) {
            case "enc" -> encryptMessage(message, shift);
            case "dec" -> decryptMessage(message, shift);
        }
    }
}