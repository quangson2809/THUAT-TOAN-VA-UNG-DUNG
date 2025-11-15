package Custom;
/******************************************************************************
 ========nghệ an========
 đời. súp lươn nghệ an của quán có nư
 n món súp lươn nghệ an là lươn đồng t
 ng món đặc sản nghệ an ngon nổi tiếng
 mùi. súp lươn nghệ an thường được ăn
 n kèm súp lươn nghệ an – sự kết hợp r

 ========kết hợp========
 êm nếm đậm đà, kết hợp bánh mướt nóng
 n nghệ an – sự kết hợp rất đặc trưng

 ========súp lươn========
 niên lâu đời. súp lươn nghệ an của qu
 nh làm nên món súp lươn nghệ an là lươ
 m của rau mùi. súp lươn nghệ an thường
 nh mướt ăn kèm súp lươn nghệ an – sự k
 o thêm ớt. bát súp lươn ngon đúng điệu
 ĩ ngay đến món súp lươn. món ăn này ba

 ========bánh mướt========
 cắn một miếng bánh mướt chấm lươn, bạn
 an sang trọng, bánh mướt dì lương vẫn k
 n kèm bánh mì, bánh mướt hoặc bánh đa.
 hoặc mưa nhẹ. bánh mướt lan thanh là m
 ậm đà, kết hợp bánh mướt nóng hổi tạo n
 và hoài niệm. bánh mướt thúy hiếu khôn
 từng miếng bánh mướt trắng ngần, mề
 sốt mặn ngọt, bánh mướt tại quán lan t
 g như tên gọi, bánh mướt việt hiệp luôn
 ệt của quán là bánh mướt ăn kèm súp lươ
 ừa tráng xong. bánh mướt ở đây dày hơn
 . khi chan lên bánh mướt, tất cả tạo nê
 ốn thưởng thức bánh mướt. bánh ở đây kh

 ========ngon========
 ặc sản nghệ an ngon nổi tiếng, nhi
 . bát súp lươn ngon đúng điệu xứ n
 a thì lại thấy ngon, thấy ghiền và
 hưởng thức món ngon.
 lươn cũng rất ngon. không gian qu

 ========–========
 iữa đất – trời – lúa – nước miề
 t – trời – lúa – nước miền trun
 p lươn nghệ an – sự kết hợp rất
 áo lòng ăn kèm – thơm mùi tiêu,
 quyện giữa đất – trời – lúa – n
 ng nàn của mắm – tạo nên một tổ
 g non, gan heo – tất cả tạo nên
 ******************************************************************************/


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SuffixArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *  The {@code KWIK} class provides a {@link SuffixArray} client for computing
 *  all occurrences of a keyword in a given string, with surrounding context.
 *  This is known as <em>keyword-in-context search</em>.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/63suffix">Section 6.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class KWIKVNtext {

    // Do not instantiate.
    private KWIKVNtext() { }

    /**
     * Reads a string from a file specified as the first
     * command-line argument; read an integer k specified as the
     * second command line argument; then repeatedly processes
     * use queries, printing all occurrences of the given query
     * string in the text string with k characters of surrounding
     * context on either side.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(new File("Custom/banhmuot.txt")));

        // read in text
        String text = StdIn.readAll().replaceAll("\\s+", " ").toLowerCase() ;
        int n = text.length();
        // build suffix array
        SuffixArray sa = new SuffixArray(text);
        ///
        In in = new In("Custom/banhmuotquery.txt");
        int context = Integer.parseInt("15");/// left và right lấy max 15 ký tự 

        // find all occurrences of queries and give context
        while (in.hasNextLine()) {
            String query = in.readLine().toLowerCase();
            StdOut.println("========" + query+ "========");
            for (int i = sa.rank(query); i < n; i++) /// bắt đầu rank của query giả sử nằm trong trong suffix[]
            {
                int from1 = sa.index(i);
                int to1   = Math.min(n, from1 + query.length());
                if (!query.equals(text.substring(from1, to1))) break;///thoát lặp nếu thays một suffix có các tiền tố không trùng với query
                int from2 = Math.max(0, sa.index(i) - context);
                int to2   = Math.min(n, sa.index(i) + context + query.length());
                StdOut.println(text.substring(from2, to2));
            }
            StdOut.println();
        }
    } 
} 

/******************************************************************************
 *  Copyright 2002-2016, Robert Sedgewick and Kevin Wayne.
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
