package Custom; /******************************************************************************
 *  Compilation:  javac LZW.java
 *  Execution:    java LZW - < input.txt   (compress)
 *  Execution:    java LZW + < input.txt   (expand)
 *  Dependencies: BinaryIn.java Custom.BinaryOut.java
 *  Data files:   https://algs4.cs.princeton.edu/55compression/abraLZW.txt
 *                https://algs4.cs.princeton.edu/55compression/ababLZW.txt
 *
 *  Compress or expand binary input from standard input using LZW.
 *

 ******************************************************************************/


import edu.princeton.cs.algs4.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *  The {@code LZW} class provides static methods for compressing
 *  and expanding a binary input using LZW compression over the 8-bit extended
 *  ASCII alphabet with 12-bit codewords.
 *  <p>
 *  WARNING: Starting with Oracle Java 7u6, the substring method takes time and
 *  space linear in the length of the extracted substring (instead of constant
 *  time an space as in earlier versions). As a result, compression takes
 *  quadratic time. TODO: fix.
 *  See <a href = "http://java-performance.info/changes-to-string-java-1-7-0_06/">this article</a>
 *  for more details.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/55compression">Section 5.5</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class LZW_UNICODE {
    private static final int R = 256;        // number of input chars
    private static final int L = 4096;       // number of codewords = 2^W
    private static final int W = 12;         // codeword width
    private static  String fileInput="Custom/fileLZW_UNICODE.txt";
    private static String fileCompress="Custom/fileCompress.txt";
    private static String filExpand="Custom/fileExpand.txt";
    private static int CODE =0;
    // Do not instantiate.
    private LZW_UNICODE() { }

    /**
     * Reads a sequence of 8-bit bytes from standard input; compresses
     * them using LZW compression with 12-bit codewords; and writes the results
     * to standard output.
     */
    public static void compress() throws FileNotFoundException {
        try {
            // 1. Đọc file input dưới dạng các byte (byte[])
            // Đọc toàn bộ file thành byte array
            byte[] inputBytes = Files.readAllBytes(Paths.get(fileInput));
            BinaryOut out = new BinaryOut(fileCompress);

            // Từ điển dùng TST (hoặc HashMap)
            TST<Integer> st = new TST<Integer>();

            // 2. Khởi tạo bảng mã ASCII mở rộng (0-255)
            // Lưu ý: Ta ép kiểu char để dùng String làm key, nhưng giá trị char này chỉ <= 255
            for (int i = 0; i < R; i++)
                st.put("" + (char) i, i);

            int code = R + 1;  // R là mã EOF

            // 3. Thuật toán LZW chạy trên các byte
            String currentPrefix = "";

            for (byte b : inputBytes) {
                // Quan trọng: Chuyển byte (-128 đến 127) sang char (0 đến 255)
                char c = (char) (b & 0xFF);

                String combined = currentPrefix + c;
                if (st.contains(combined)) {
                    currentPrefix = combined;
                } else {
                    out.write(st.get(currentPrefix), W); // Ghi mã của chuỗi trước đó

                    if (code < L)    // Thêm chuỗi mới vào từ điển
                        st.put(combined, code++);

                    currentPrefix = "" + c;
                }
            }

            // Ghi chuỗi còn sót lại
            if (currentPrefix.length() > 0) {
                out.write(st.get(currentPrefix), W);
            }

            out.write(R, W); // Ghi mã kết thúc file (EOF)
            out.close();
            display(st);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void display(TST<Integer> st) {
        for (String c: st.keys()){
            StdOut.println(c+ " : " + st.get(c));
        }
    }

    /**
     * Reads a sequence of bit encoded using LZW compression with
     * 12-bit codewords from standard input; expands them; and writes
     * the results to standard output.
     */
    public static void expand() throws FileNotFoundException {
        BinaryIn in = new BinaryIn(fileCompress);
        BinaryOut out = new BinaryOut(filExpand);

        // 1. Khởi tạo từ điển ngược: Mã -> Chuỗi (String ở đây hiểu là chuỗi các byte)
        String[] st = new String[L];
        int i; // i chạy từ 0 đến 255
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;

        st[i++] = ""; // Mã R không dùng (hoặc là EOF)

        int codeword = in.readInt(W);
        if (codeword == R) return; // File rỗng

        String val = st[codeword];

        while (true) {
            // 2. Ghi chuỗi byte ra file output
            // Quan trọng: Duyệt từng 'char' trong chuỗi val và ghi ra đúng 8 bit
            for (int j = 0; j < val.length(); j++) {
                out.write(val.charAt(j), 8); // CHỈ GHI 8 BIT (1 BYTE)
            }

            int nextCodeword = in.readInt(W);
            if (nextCodeword == R) break; // Gặp EOF thì dừng

            String s = st[nextCodeword];

            // Trường hợp đặc biệt của LZW: cScSc
            if (i == nextCodeword)
                s = val + val.charAt(0);

            if (i < L)
                st[i++] = val + s.charAt(0);

            val = s;
        }

        out.close();
    }

    /**
     * Sample client that calls {@code compress()} if the command-line
     * argument is "-" an {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
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