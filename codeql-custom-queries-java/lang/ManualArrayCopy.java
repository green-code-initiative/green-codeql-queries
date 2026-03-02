public class ManualArrayCopy {

    public void testManualCopies() {
        int[] src = {1, 2, 3, 4, 5};
        int[] dest = new int[5];

        for (int i = 0; i < src.length; i++) {
            dest[i] = src[i]; 
        }

        int j = 0;
        while (j < src.length) {
            dest[j] = src[j];
            j++;
        }

        int index = 0;
        for (int value : src) {
            dest[index++] = value;
        }

        if (dest.length > 0) {
            for (int k = 0; k < src.length; k++) {
                dest[k] = src[k];
            }
        }
    }

    public void testValidCases() {
        int[] src = {1, 2, 3, 4, 5};
        int[] dest = new int[5];

        System.arraycopy(src, 0, dest, 0, src.length);

        for (int i = 0; i < src.length; i++) {
            src[i] = src[i] * 2;
        }

        for (int i = 0; i < dest.length; i++) {
            dest[i] = 0;
        }
    }
}
