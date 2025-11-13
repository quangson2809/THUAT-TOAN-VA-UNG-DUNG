package Custom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  The {@code MSD} class cung cấp phương thức sắp xếp các chuỗi ascii có thể không bằng nhau
 *  , sắp xếp kiê gom nhóm theo left to right

     * Rearranges the array of extended ASCII strings in ascending order.
     *
     */

public class MSDForStudentNameASCII {
    private static final int R             = 256;   // ASCII alphabet size
    private static final int CUTOFF        =  15;   //số phần tử trong nhóm <=15 thì sang insertion sort
    private static  int count = 0;
    // do not instantiate
    private MSDForStudentNameASCII() { }

    public static void sort(Student[] a) {
        int n = a.length;
        Student[] aux = new Student[n];
        sort(a, 0, n-1, 0, aux);
    }

    // return dth character of s, -1 if d = length of string
    private static int charAt(String s, int d) {
        assert d >= 0 && d <= s.length();
        if (d == s.length()) return -1;
        return s.charAt(d);
    }

    //get fullname
    private static String getStringOfStudent(Student a){
        String fullname = (a.ten() + a.hodem()).replace(" ","").toLowerCase();
        count ++;
        return fullname;
    }

    // sort from a[lo] to a[hi], starting at the dth character
    private static void sort(Student[] a, int lo, int hi, int d, Student[] aux) {

        // cutoff to insertion sort for small subarrays
        if (hi <= lo + CUTOFF) {
            insertion(a, lo, hi, d);
            return;
        }

        // compute frequency counts
        int[] count = new int[R+2];
        for (int i = lo; i <= hi; i++) {
            // create string
            String name = getStringOfStudent(a[i]);

            int c = charAt(name, d);
            count[c+2]++;
        }

        // transform counts to indicies
        for (int r = 0; r < R+1; r++)
            count[r+1] += count[r];

        // distribute
        for (int i = lo; i <= hi; i++) {
            // create string
            String name = getStringOfStudent(a[i]);

            int c = charAt(name, d);
            aux[count[c+1]++] = a[i];
        }

        // copy back
        for (int i = lo; i <= hi; i++) 
            a[i] = aux[i - lo];


        // recursively sort for each character (excludes sentinel -1)
        for (int r = 0; r < R; r++)
            sort(a, lo + count[r], lo + count[r+1] - 1, d+1, aux);
    }


    // insertion sort a[lo..hi], starting at dth character
    private static void insertion(Student[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(getStringOfStudent(a[j]), getStringOfStudent(a[j - 1]), d); j--)
                exch(a, j, j - 1);
        }
    }


    // exchange a[i] and a[j]
    private static void exch(Student[] a, int i, int j) {
        Student temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // is v less than w, starting at character d
    private static boolean less(String v, String w, int d) {
        // assert v.substring(0, d).equals(w.substring(0, d));
        for (int i = d; i < Math.min(v.length(), w.length()); i++) {
            if (v.charAt(i) < w.charAt(i)) return true;
            if (v.charAt(i) > w.charAt(i)) return false;
        }
        return v.length() < w.length();
    }

    // test
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(new File("Custom/studentASCII.csv")));

        List<Student> list = new ArrayList<Student>();
        while (StdIn.hasNextLine()) {
            list.add(new Student(StdIn.readLine()));
        }

        Student [] a = list.toArray(new Student[list.size()]);

        sort(a);
        for (int i = 0; i < a.length; i++)
            StdOut.println(a[i]);
        StdOut.println("call getstringstudent :" + count);
    }
}

