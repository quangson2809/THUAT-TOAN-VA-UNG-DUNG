package Custom;

import java.util.HashMap;

public class VNAlphabet {
    public static final  VNAlphabet UNICODE16 = new VNAlphabet(65536);

    private char[] alphabet;
    private int[] inverse;
    private int R;
    private HashMap map;

    public VNAlphabet(int R) {
        alphabet = new char[R];
        inverse = new int[R];
        for(int i=0; i<R; i++) {
            alphabet[i] = (char)i;
            inverse[i] = i;
        }
        this.R = R;
        map = new HashMap<>();
    }

    public int [] toIntArray(String string){
        char [] source= string.toCharArray();
        int [] target = new int[source.length];
        for(int i=0; i<source.length; i++) {
            target[i] = inverse[source[i]];
        }
        return target;
    }

    public char tochar(int index) throws Exception {
        if(index < 0 || index >= R) {
            throw new Exception("index out range");
        }
        return alphabet[index];
    }
    public void display(int [] indices) throws Exception {
        for(int i=0; i<indices.length; i++){
            StringBuilder chars = new StringBuilder();
            chars.append(tochar(indices[i]));
            System.out.println(chars.toString());
            map.put(chars.toString(), indices[i]);
        }
    }


    public static void main(String[] args) {
        VNAlphabet alphabet = new VNAlphabet(65536);
        int  [] indices = VNAlphabet.UNICODE16.toIntArray("không có gì khó");
        try {
            alphabet.display(indices);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
