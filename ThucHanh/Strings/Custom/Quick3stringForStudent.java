package Custom;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 *  The {@code Quick3string} class cung câps cacs phương thuwcs tĩnh để sắp xếp mảng các chuỗi có độ dài ngẫu nhiên
 * dựa trên thuật toans sắp xép quicksort dùng phần tử trục (pivot) để chia các thành các mảng con
 * -> đệ quy chia để trị
 *  <p>
 *
 */
public class Quick3stringForStudent {
    private static final int CUTOFF =  15;   // điều kiện mảng con để chuyển sang insertsort
    private static int count =0;
    // do not instantiate
    private Quick3stringForStudent() { }

    /**
     * Rearranges the array of strings in ascending order.
     *
     * @param a the array to be sorted
     */

    private static String getStringOfStudent(Student a){
        String fullname = (a.ten() + a.hodem()).replace(" ","").toLowerCase();
        count ++;
        return fullname;
    }

    public static void sort(Student[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length-1, 0);
        assert isSorted(a);
    }

    // return the dth character of s, -1 if d = length of s
    private static int charAt(String s, int d) {
        assert d >= 0 && d <= s.length();
        if (d == s.length()) return -1;
        return s.charAt(d);
    }


    // 3-way string quicksort a[lo..hi] starting at dth character
    private static void sort(Student[] a, int lo, int hi, int d) {

        // cutoff to insertion sort for small subarrays
        if (hi <= lo + CUTOFF) {
            insertion(a, lo, hi, d);
            return;
        }

        int lt = lo, gt = hi;
        int v = charAt(getStringOfStudent(a[lo]), d);
        int i = lo + 1;
        while (i <= gt) {
            int t = charAt(getStringOfStudent(a[i]), d);
            if      (t < v) exch(a, lt++, i++);
            else if (t > v) exch(a, i, gt--);
            else              i++;
        }

        // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi].
        sort(a, lo, lt-1, d);
        if (v >= 0) sort(a, lt, gt, d+1);
        sort(a, gt+1, hi, d);
    }

    // sort from a[lo] to a[hi], starting at the dth character
    private static void insertion(Student[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(getStringOfStudent(a[j]), getStringOfStudent(a[j-1]), d); j--)
                exch(a, j, j-1);
    }

    // exchange a[i] and a[j]
    private static void exch(Student[] a, int i, int j) {
        Student temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // is v less than w, starting at character d
    // DEPRECATED BECAUSE OF SLOW SUBSTRING EXTRACTION IN JAVA 7
    // private static boolean less(String v, String w, int d) {
    //    assert v.substring(0, d).equals(w.substring(0, d));
    //    return v.substring(d).compareTo(w.substring(d)) < 0;
    // }

    // is v less than w, starting at character d
    private static boolean less(String v, String w, int d) {
        assert v.substring(0, d).equals(w.substring(0, d));
        for (int i = d; i < Math.min(v.length(), w.length()); i++) {
            if (v.charAt(i) < w.charAt(i)) return true;
            if (v.charAt(i) > w.charAt(i)) return false;
        }
        return v.length() < w.length();
    }

    // is the array sorted
    private static boolean isSorted(Student[] a) {
        for (int i = 1; i < a.length; i++)
            if (a[i].compareTo(a[i-1]) < 0) return false;
        return true;
    }


    /**
     * Reads in a sequence of fixed-length strings from standard input;
     * 3-way radix quicksorts them;
     * and prints them to standard output in ascending order.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        try {
            System.setIn(new FileInputStream(new File("Custom/studentASCII.csv")));
            // read in the strings from standard input
            List<Student> list = new ArrayList<>();
            while (StdIn.hasNextLine()){
                list.add(new Student(StdIn.readLine()));
            }
            int n = list.size();
            Student[] a = list.toArray(new Student[n]);
            // sort the Students
            sort(a);

            // print the results
            for (int i = 0; i < n; i++)
                StdOut.println(a[i]);
            StdOut.println("call getstringforstudent : " + count);
        }catch (Exception ex){
            StdOut.println(ex.getMessage());
        }
    }
}

/******************************************************************************
 *  Copyright 2002-2025, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/