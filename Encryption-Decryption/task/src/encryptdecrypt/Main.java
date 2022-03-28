package encryptdecrypt;

public class Main {
    public static void main(String[] args) {
        EncryptorFactory factory = new EncryptorFactory();
        Encryptor encryptor = factory.encryptorBuilder(args);

        encryptor.run();
    }
}