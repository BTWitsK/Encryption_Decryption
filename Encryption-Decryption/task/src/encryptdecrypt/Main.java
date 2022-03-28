package encryptdecrypt;

public class Main {

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