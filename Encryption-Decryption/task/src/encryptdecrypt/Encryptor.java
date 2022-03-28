package encryptdecrypt;
import java.io.*;
import java.util.*;

abstract class Encryptor {
    enum Mode {
        ENC,
        DEC
    }

    protected StringBuilder output = new StringBuilder();
    protected HashMap<String, String> argumentMap;
    protected Scanner scanner;
    protected String message;
    protected Mode mode;
    protected int shift;

    Encryptor(HashMap<String, String> map) {
        argumentMap = map;
        message = parseMessage();
        shift = Integer.parseInt(argumentMap.getOrDefault("-key", "0"));
        mode = Mode.valueOf(argumentMap.getOrDefault("-mode", "enc").toUpperCase());
    }

    protected void printMessage(String output) {
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

    protected String parseMessage() {
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
    
    abstract protected void encryptMessage();
    
    abstract protected void decryptMessage();

    abstract protected void run();
}

class Shift extends Encryptor {
    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public Shift(HashMap<String, String> args) {
        super(args);
    }

    private char encryptLetter(char letter) {
        if (Character.isSpaceChar(letter)) {
            return ' ';
        } else {
            int letterIndex = ALPHABET.indexOf(letter);

            char shiftedLetter = letterIndex + shift > 25 ? ALPHABET.charAt((shift + letterIndex) % 26)
                    : ALPHABET.charAt(letterIndex + shift);

            return Character.isLowerCase(letter) ? shiftedLetter : Character.toUpperCase(shiftedLetter);
        }
    }

    private char decryptLetter(char letter) {
        if (Character.isSpaceChar(letter)) {
            return ' ';
        } else {
            int letterIndex = ALPHABET.indexOf(letter);

            char shiftedLetter = letterIndex - shift < 0 ? ALPHABET.charAt(26 - Math.abs(letterIndex - shift))
                    : ALPHABET.charAt(letterIndex - shift);

            return Character.isLowerCase(letter) ? shiftedLetter : Character.toUpperCase(shiftedLetter);
        }
    }

    @Override
    protected void encryptMessage() {
        for (int i = 0; i < message.length(); i++) {
            output.append(encryptLetter(message.charAt(i)));
        }
        printMessage(output.toString());
    }

    protected void decryptMessage() {
        for (int i = 0; i < message.length(); i++) {
            output.append(decryptLetter(message.charAt(i)));
        }
        printMessage(output.toString());
    }

    @Override
    public void run() {
        switch (mode) {
            case ENC -> encryptMessage();
            case DEC -> decryptMessage();
        }
    }
}

class Unicode extends Encryptor {
    public Unicode(HashMap<String, String> args) {
        super(args);
    }

    @Override
    protected void encryptMessage() {
        for (int i = 0; i < message.length(); i++) {
            output.append((char)(message.charAt(i) + shift));
        }
        printMessage(output.toString());
    }

    @Override
    protected void decryptMessage() {
        for (int i = 0; i < message.length(); i++) {
            output.append((char) (message.charAt(i) - shift));
        }
        printMessage(output.toString());
    }

    @Override
    public void run() {
        switch (mode) {
            case ENC -> encryptMessage();
            case DEC -> decryptMessage();
        }
    }
}

class EncryptorFactory {
    public Encryptor encryptorBuilder(String[] args) {
        HashMap<String, String> map = new HashMap<>();

        for (int i = 0; i < args.length; i += 2) {
            map.put(args[i], args[i + 1]);
        }

        return switch(map.getOrDefault("-alg", "shift")) {
            case "shift" -> new Shift(map);
            case "unicode" -> new Unicode(map);
            default -> null;
        };
    }
}