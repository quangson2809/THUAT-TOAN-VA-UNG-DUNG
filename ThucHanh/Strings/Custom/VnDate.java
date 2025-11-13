package Custom; /******************************************************************************
 *  Biên dịch:  javac Date.java
 *  Thực thi:    java Date
 *  Phụ thuộc: StdOut.java
 *
 *  Kiểu dữ liệu bất biến cho các ngày tháng.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;

/**
 * Lớp {@code Date} là một kiểu dữ liệu bất biến để bao gói ngày (ngày, tháng và
 * năm).
 * <p>
 * Để biết thêm thông tin chi tiết,
 * xem <a href="https://algs4.cs.princeton.edu/12oop">Mục 1.2</a> trong
 * <i>Algorithms, 4th Edition</i> của Robert Sedgewick và Kevin Wayne.
 *
 * @tác giả Robert Sedgewick
 * @tác giả Kevin Wayne
 */
public class VnDate implements Comparable<VnDate> {
    private static final int[] DAYS = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    // tháng: 1 2 3 4 5 6 7 8 9 10 11 12
    private final int day; // tháng (giữa 1 và 12)
    private final int month; // ngày (giữa 1 và DAYS[month])
    private final int year; // năm

    /**
     * Khởi tạo một ngày mới từ ngày , tháng và năm.
     * 
     * @param day   ngày (giữa 1 và 28-31, tùy thuộc vào tháng)
     * @param month tháng (giữa 1 và 12)
     * @param year  năm
     * @throws IllegalArgumentException nếu ngày này không hợp lệ
     */
    public VnDate(int day, int month, int year) {
        if (!isValid(day, month, year))
            throw new IllegalArgumentException("Invalid date");
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Khởi tạo một ngày mới được xác định bằng chuỗi dưới dạng DD/MM/YYYY.
     * 
     * @param date chuỗi biểu diễn ngày này
     * @throws IllegalArgumentException nếu ngày này không hợp lệ
     */
    public VnDate(String date) {
        String[] fields = date.split("/");// tách chuỗi ngày/tháng/năm theo dấu '/' thành 3 phần ngày, tháng và năm
        if (fields.length != 3) {
            throw new IllegalArgumentException("Invalid date "+ fields.length);
        }
        day = Integer.parseInt(fields[0]); // chuyển kiểu chuỗi được tách thành kiểu in và gán cho ngày
        month = Integer.parseInt(fields[1]); //
        year = Integer.parseInt(fields[2]); //
        if (!isValid(day, month, year))
            throw new IllegalArgumentException("Invalid date");
    }

    /**
     * Trả về tháng.
     * 
     * @return tháng (một số nguyên giữa 1 và 12)
     */
    public int month() {
        return month;
    }

    /**
     * Trả về ngày.
     * 
     * @return ngày (một số nguyên giữa 1 và 31)
     */
    public int day() {
        return day;
    }

    /**
     * Trả về năm.
     * 
     * @return năm
     */
    public int year() {
        return year;
    }

    // Ngày được cho có hợp lệ không?
    private static boolean isValid(int d, int m, int y) {
        if (m < 1 || m > 12)
            return false;
        if (d < 1 || d > DAYS[m])
            return false;
        if (m == 2 && d == 29 && !isLeapYear(y))
            return false;// nêu nhuận t2=29 ngày, nếu không là 28
        return true;
    }

    // Năm y có phải năm nhuận không?
    private static boolean isLeapYear(int y) {
        if (y % 400 == 0)
            return true;
        if (y % 100 == 0)
            return false;
        return y % 4 == 0;
    }

    /**
     * Trả về ngày kế tiếp trong lịch.
     *
     * @return một ngày đại diện cho ngày kế tiếp sau ngày này
     */
    public VnDate next() {
        if (isValid(day + 1, month, year))
            return new VnDate(day + 1, month, year);
        else if (isValid(1, month + 1, year))
            return new VnDate(1,month + 1, year);
        else
            return new VnDate(1, 1, year + 1);
    }

    /**
     * So sánh hai ngày theo thứ tự thời gian.
     *
     * @param that ngày khác
     * @return {@code true} nếu ngày này sau ngày đó; {@code false} ngược lại
     */
    public boolean isAfter(VnDate that) {
        return compareTo(that) > 0;
    }

    /**
     * So sánh hai ngày theo thứ tự thời gian.
     *
     * @param that ngày khác
     * @return {@code true} nếu ngày này trước ngày đó; {@code false} ngược lại
     */
    public boolean isBefore(VnDate that) {
        return compareTo(that) < 0;
    }

    /**
     * So sánh hai ngày theo thứ tự thời gian.
     *
     * @return giá trị {@code 0} nếu ngày tham số bằng ngày này;
     *         một số nguyên âm nếu ngày này trước ngày tham số;
     *         và một số nguyên dương nếu ngày này sau ngày tham số
     */
    //@Override
    public int compareTo(VnDate that) {
        if (this.year < that.year)
            return -1;
        if (this.year > that.year)
            return +1;
        if (this.month < that.month)
            return -1;
        if (this.month > that.month)
            return +1;
        if (this.day < that.day)
            return -1;
        if (this.day > that.day)
            return +1;
        return 0;
    }

    /**
     * Trả về biểu diễn chuỗi của ngày này.
     *
     * @return biểu diễn chuỗi dưới dạng DD/MM/YYYY
     */
    @Override
    public String toString() {
        return day + "/" + month + "/" + year;
    }

    /**
     * So sánh ngày này với ngày được chỉ định.
     *
     * @param other ngày khác
     * @return {@code true} nếu ngày này bằng {@code other}; {@code false} ngược lại
     */
    //@Override
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (other == null)
            return false;
        if (other.getClass() != this.getClass())
            return false;
        VnDate that = (VnDate) other;
        return (this.day == that.day) && (this.month == that.month) && (this.year == that.year);
    }

    /**
     * Trả về mã băm nguyên cho ngày này.
     *
     * @return mã băm nguyên cho ngày này
     */
    //@Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + day;
        hash = 31 * hash + month;
        hash = 31 * hash + year;
        return hash;
    }

    /**
     * Kiểm tra đơn vị lớp dữ liệu {@code Date}.
     *
     * @param args các tham số dòng lệnh
     */
    public static void main(String[] args) {
        VnDate today = new VnDate(25, 2, 2004);
        StdOut.println(today);
        for (int i = 0; i < 10; i++) {
            today = today.next();
            StdOut.println(today);
        }

        StdOut.println(today.isAfter(today.next()));
        StdOut.println(today.isAfter(today));
        StdOut.println(today.next().isAfter(today));

        VnDate birthday = new VnDate(16, 10, 1971);
        StdOut.println(birthday);
        for (int i = 0; i < 10; i++) {
            birthday = birthday.next();
            StdOut.println(birthday);
        }
        VnDate test = new VnDate("8/4/1955");
        StdOut.println(test);
    }

}

/******************************************************************************
 * Bản quyền 2002-2016, Robert Sedgewick và Kevin Wayne.
 *
 * Tệp tin này là một phần của algs4.jar, đi kèm với sách giáo khoa
 *
 * Algorithms, 4th edition bởi Robert Sedgewick và Kevin Wayne,
 * Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 * http://algs4.cs.princeton.edu
 *
 *
 * algs4.jar là phần mềm tự do: bạn có thể phân phối lại và/chỉnh sửa
 * nó theo điều khoản của Giấy phép GNU General Public License được công bối
 * Quỹ ban Phần mềm Tự do, phiên bản 3, hoặc (tùy chọn) phiên bản mới hơn.
 *
 * algs4.jar được phân phối với hy vọng rằng nó sẽ hữu ích,
 * nhưng KHÔNG CÓ BẢO HÀNH; thậm chí không bảo hành ngầm định về
 * TÍNH THÍCH HỢP HOẶC PHÙ HỢP CHO MỤC ĐÍCH CỤ THỂ. Xem Giấy phép GNU General
 * Public License để biết chi tiết.
 *
 * Bạn đã nhận được bản sao của GNU General Public License kèm theo algs4.jar.
 * Nếu không, xem http://www.gnu.org/licenses.
 ******************************************************************************/
