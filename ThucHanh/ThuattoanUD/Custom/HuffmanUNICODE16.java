package Custom; /******************************************************************************
 *  Compilation:  javac Huffman.java
 *  Execution:    java Huffman - < input.txt   (compress)
 *  Execution:    java Huffman + < input.txt   (expand)
 *  Dependencies: BinaryIn.java Custom.BinaryOut.java
 *  Data files:   https://algs4.cs.princeton.edu/55compression/abra.txt
 *                https://algs4.cs.princeton.edu/55compression/tinytinyTale.txt
 *                https://algs4.cs.princeton.edu/55compression/medTale.txt
 *                https://algs4.cs.princeton.edu/55compression/tale.txt
 *
 *  Compress or expand a binary input stream using the Huffman algorithm.
 *
 *  % java Huffman - < abra.txt | java BinaryDump 60
 *  010100000100101000100010010000110100001101010100101010000100
 *  000000000000000000000000000110001111100101101000111110010100
 *  120 bits
 *
 *  % java Huffman - < abra.txt | java Huffman +
 *  ABRACADABRA!
 *
 ******************************************************************************/



/**
 *  The {@code Huffman} class provides static methods for compressing
 *  and expanding a binary input using Huffman codes over the 8-bit extended
 *  ASCII alphabet.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/55compression">Section 5.5</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */

import edu.princeton.cs.algs4.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class HuffmanUNICODE16 {

    // alphabet size of extended ASCII
    private static String fileCompress="Custom/fileCompress.txt";
    private static String fileInput="Custom/HuffmanDoc.txt";
    private static String fileExpand="Custom/fileExpand.txt";

    // Do not instantiate.
    private HuffmanUNICODE16() { }

    // Huffman trie node
    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch    = ch;
            this.freq  = freq;
            this.left  = left;
            this.right = right;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    /**
     * Reads a sequence of 8-bit bytes from standard input; compresses them
     * using Huffman codes with an 8-bit alphabet; and writes the results
     * to standard output.
     */
    public static void compress() throws FileNotFoundException {
        System.setIn(new FileInputStream(new File(fileInput)));
        BinaryOut BinaryOut = new BinaryOut(fileCompress);
        // read the input
        String s = StdIn.readAll();
        char[] input = s.toCharArray();

        // tabulate frequency counts
        HashMap<Character,Integer> freq = new HashMap<Character,Integer>();
//        int[] freq = new int[R];
        for (int i = 0; i < input.length; i++){
//            freq[input[i]]++;
            if(freq.containsKey(input[i])){
                freq.put(input[i],freq.get(input[i])+1);
            }
            else
                freq.put(input[i],1);
        }

        // build Huffman trie
        Node root = buildTrie(freq);

        // build code table
//        String[] st = new String[R];//mảng code với index là ký tự tại mỗi node
        HashMap<Character, String> st = new HashMap<Character,String>();

        buildCode(st, root, "");
        tablecode(freq,st);

        // print trie for decoder
        writeTrie(root ,BinaryOut);

        // print number of bytes in original uncompressed message
        BinaryOut.write(input.length);

        // use Huffman code to encode input
        for (int i = 0; i < input.length; i++) {
            String code = st.get(input[i]);
            for (int j = 0; j < code.length(); j++) {
                if (code.charAt(j) == '0') {
                    BinaryOut.write(false);
                }

                else if (code.charAt(j) == '1') {
                    BinaryOut.write(true);
                }
                else throw new IllegalStateException("Illegal state");
            }
        }

        // close output stream
        BinaryOut.close();
    }
    private static void tablecode(HashMap<Character, Integer> freq, HashMap<Character, String> st) {
        System.out.println("=========================================");
        System.out.println("   BẢNG TẦN SUẤT VÀ MÃ HUFFMAN            ");
        System.out.println("=========================================");
        System.out.printf("%-10s %-10s %-15s\n", "Ký Tự", "Tần Suất", "Mã Huffman");
        System.out.println("-----------------------------------------");

        // Dùng một Priority Queue để in các ký tự theo tần suất giảm dần
        MinPQ<Node> pq = new MinPQ<>();
        for (char c : freq.keySet()) {
            pq.insert(new Node(c, -freq.get(c), null, null));
        }
        while (!pq.isEmpty()) {
            Node node = pq.delMin();
            char character = node.ch;
            int frequency = -node.freq; // Chuyển lại thành số dương
            String code = st.get(character);
            // Xử lý các ký tự đặc biệt như xuống dòng, tab...
            String charDisplay;
            if (character == '\n') charDisplay = "\\n";
            else if (character == '\r') charDisplay = "\\r";
            else if (character == '\t') charDisplay = "\\t";
            else if (character == ' ') charDisplay = "' '";
            else charDisplay = "'" + character + "'";

            System.out.printf("%-10s %-10d %-15s\n", charDisplay, frequency, code);
        }
    }
    // build the Huffman trie given frequencies
    private static Node buildTrie(HashMap<Character, Integer> freq) {

        // initialize priority queue with singleton trees
        MinPQ<Node> pq = new MinPQ<Node>();
        for (char c : freq.keySet()) {
//            if (freq[c] > 0)
//                pq.insert(new Node(c, freq[c], null, null));
            pq.insert(new Node(c, freq.get(c), null, null));
        }

        // merge two smallest trees
        while (pq.size() > 1) {
            Node left  = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        return pq.delMin();
    }


    // write bitstring-encoded trie to standard output
    private static void writeTrie(Node x,BinaryOut BinaryOut) {
        if (x.isLeaf()) {
            BinaryOut.write(true);
            BinaryOut.write(x.ch, 16);
            return;
        }
        BinaryOut.write(false);
        writeTrie(x.left,BinaryOut);
        writeTrie(x.right,BinaryOut);
    }

    // make a lookup table from symbols and their encodings
    private static void buildCode(HashMap<Character,String> st, Node x, String s) {
        if (!x.isLeaf()) {
            buildCode(st, x.left,  s + '0');
            buildCode(st, x.right, s + '1');
        }
        else {
//            st[x.ch] = s; /// st[i] là các bit
            st.put(x.ch,s);
        }
    }

    /**
     * Reads a sequence of bits that represents a Huffman-compressed message from
     * standard input; expands them; and writes the results to standard output.
     */
    public static void expand() throws FileNotFoundException {
        System.setIn(new FileInputStream(new File(fileCompress)));

        BinaryOut BinaryOut = new BinaryOut(fileExpand);

        // read in Huffman trie from input stream
        Node root = readTrie();

        // number of bytes to write
        int length = BinaryStdIn.readInt();

        // decode using the Huffman trie
        for (int i = 0; i < length; i++) {
            Node x = root;
            while (!x.isLeaf()) {
                boolean bit = BinaryStdIn.readBoolean();
                if (bit) x = x.right;
                else     x = x.left;
            }
            BinaryOut.write(x.ch);
        }
        BinaryOut.close();
    }


    private static Node readTrie() {
        boolean isLeaf = BinaryStdIn.readBoolean();
        if (isLeaf) {
            return new Node(BinaryStdIn.readChar(16), -1, null, null);
        }
        else {
            return new Node('\0', -1, readTrie(), readTrie());
        }
    }

    /**
     * Sample client that calls {@code compress()} if the command-line
     * argument is "-" an {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        try {
            StdOut.println("nhập token: ");
            String token = scanner.nextLine();
            if (token.equals("-")) compress();
            else if (token.equals("+")) expand();
            else throw new IllegalArgumentException("Illegal command line argument");
        } catch (Exception e) {
            StdOut.println(e);
        } finally {
            scanner.close();
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
