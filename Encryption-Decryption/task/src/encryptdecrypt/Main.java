package encryptdecrypt;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder output = new StringBuilder();
        Scanner scanner = new Scanner(System.in);

        String message = scanner.nextLine();
        int shift = scanner.nextInt();

        for (int i = 0; i < message.length(); i++) {
            int letter = ALPHABET.indexOf(message.charAt(i));
            output.append(Character.isSpaceChar(message.charAt(i)) ? " " : letter + shift > 25 ?
                    ALPHABET.charAt((shift  + letter) % 26) : ALPHABET.charAt(letter + shift));
        }
        System.out.println(output);
    }
}