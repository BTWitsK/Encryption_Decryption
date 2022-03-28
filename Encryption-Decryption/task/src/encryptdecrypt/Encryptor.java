package encryptdecrypt;
import java.io.*;
import java.util.*;

abstract class Encryptor {
    enum Mode {
        ENC,
        DEC
    }

    StringBuilder output = new StringBuilder();
    HashMap<String, String> argumentMap = new HashMap<>();
    Scanner scanner;
    String message;
    Mode mode;
    int shift;


    Encryptor(String[] args) {
        for (int i = 0; i < args.length; i += 2) {
            argumentMap.put(args[i], args[i + 1]);
        }
        message = parseMessage();
        shift = Integer.parseInt(argumentMap.getOrDefault("-key", "0"));
        mode = Mode.valueOf(argumentMap.getOrDefault("-mode", "enc").toUpperCase());
    }

    public void printMessage(String output) {
        if (argumentMap.containsKey("-out")) {
            try (PrintWriter writer = new PrintWriter(argumentMap.get("-out"))) {
                writer.print(output);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println(output);
        }
    }

    public String parseMessage() {
        if (argumentMap.containsKey("-data")) {
            return argumentMap.get("-data");
        } else {
            try {
                scanner = new Scanner(new FileReader(argumentMap.get("-in")));
                return scanner.nextLine();
            } catch (IOException e) {
                System.out.println("Error: No input file found");
            }
        }
        return "";
    }
    
    abstract void encryptMessage();
    
    abstract void decryptMessage();
}

class Shift extends Encryptor {
    final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public Shift(String[] args) {
        super(args);
    }

    public char encryptLetter(char letter) {
        if (Character.isSpaceChar(letter)) {
            return ' ';
        } else {
            int letterIndex = ALPHABET.indexOf(letter);

            char shiftedLetter = letterIndex + shift > 25 ? ALPHABET.charAt((shift + letterIndex) % 26)
                    : ALPHABET.charAt(letterIndex + shift);

            return Character.isLowerCase(letter) ? shiftedLetter : Character.toUpperCase(shiftedLetter);
        }
    }

    public char decryptLetter(char letter) {
        if (Character.isSpaceChar(letter)) {
            return ' ';
        } else {
            int letterIndex = ALPHABET.indexOf(letter);

            char shiftedLetter = letterIndex - shift > 25 ? ALPHABET.charAt((shift - letterIndex) % 26)
                    : ALPHABET.charAt(letterIndex - shift);

            return Character.isLowerCase(letter) ? shiftedLetter : Character.toUpperCase(shiftedLetter);
        }
    }

    @Override
    public void encryptMessage() {
        for (int i = 0; i < message.length(); i++) {
            output.append(encryptLetter(message.charAt(i)));
        }
        printMessage(output.toString());
    }

    public void decryptMessage() {
        for (int i = 0; i < message.length(); i++) {
            output.append(decryptLetter(message.charAt(i)));
        }
        printMessage(output.toString());
    }
}

class Unicode extends Encryptor {
    public Unicode(String[] args) {
        super(args);
    }

    @Override
    public void encryptMessage() {
        for (int i = 0; i < message.length(); i++) {
            output.append((char)(message.charAt(i) + shift));
        }
        printMessage(output.toString());
    }

    @Override
    public void decryptMessage() {
        for (int i = 0; i < message.length(); i++) {
            output.append((char) (message.charAt(i) - shift));
        }
        printMessage(output.toString());
    }
}