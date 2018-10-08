import java.util.Random;

public class Useful {
    private static String vowels = "aeiouy";
    private static String consonants = "bcdfghjklmnpqrstvwxz";
    private static Random gen = new Random();

    public Useful() {
    }

    public static final String[] generateItemInfo() {
        String[] result = new String[]{generateCode(), generateText(35), generateText(45), Integer.toString((1900 + gen.nextInt(116)) * 10000 + (gen.nextInt(12) + 1) * 100 + 1 + gen.nextInt(29))};
        return result;
    }

    private static final String generateCode() {
        return Long.toString(1000000000000L + (long)gen.nextInt(2147483647));
    }

    private static final String generateText(int len) {
        int[] spacesIndex = new int[1 + gen.nextInt(3)];
        spacesIndex[0] = 4 + gen.nextInt(len / 3);

        for(int text = 1; text < spacesIndex.length; ++text) {
            spacesIndex[text] = spacesIndex[text - 1] + gen.nextInt(len - 5);
        }

        StringBuffer var7 = new StringBuffer(len);
        char c = gen.nextInt() % 2 == 1?vowels.charAt(gen.nextInt(vowels.length())):consonants.charAt(gen.nextInt(consonants.length()));
        var7.append(Character.toUpperCase(c));

        for(int i = 1; i < len; ++i) {
            boolean capitalize = false;
            int j = 0;

            while(true) {
                if(j < spacesIndex.length) {
                    if(i == spacesIndex[j]) {
                        var7.append(' ');
                        break;
                    }

                    if(i - 1 == spacesIndex[j]) {
                        capitalize = true;
                    }

                    if(i <= j) {
                        ++j;
                        continue;
                    }
                }

                var7.append(gen.nextInt() % 2 == 1?vowels.charAt(gen.nextInt(vowels.length())):consonants.charAt(gen.nextInt(consonants.length())));
                if(capitalize) {
                    c = var7.charAt(i);
                    var7.setCharAt(i, Character.toUpperCase(c));
                }
                break;
            }
        }

        return var7.toString();
    }

    public static final String[] generateStorageAreaInfo() {
        String[] result = new String[]{Integer.toString(1000 + gen.nextInt(8999)), Integer.toString(1 + gen.nextInt(9))};
        return result;
    }
}
