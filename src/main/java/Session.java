import java.util.Random;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class Session {

    private static final Random RANDOM   = new Random();
    private static final char[] ALPHABET = "0123465798/*-+abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final int LENGTH = 64;

    public static String generate() {
        StringBuffer buffer = new StringBuffer(64);
        for (int i = 0; i < LENGTH; i++) {
            buffer.append(ALPHABET[RANDOM.nextInt(ALPHABET.length)]);
        }

        return buffer.toString();
    }


    public static void main(String[] args) {

        System.out.println(Session.generate());
        System.out.println(Session.generate());
        System.out.println(Session.generate());
        System.out.println(Session.generate());
        System.out.println(Session.generate());
        System.out.println(Session.generate());

    }

}
