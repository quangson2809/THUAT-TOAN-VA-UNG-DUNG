import java.util.Arrays;
import java.util.Comparator;

/**
 * Write a description of class Student here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Student implements Comparable<Student> {
    // instance variables - replace the example below with your own
    private   String maSv;
    private   String hodem;
    private   String ten;
    private   VnDate ngaysinh;
    private   String que;
    private   double diemTb;

    /**
     * Constructor for objects of class Student
     */
    public Student(String maSv, String hodem, String ten, VnDate ngaysinh,String que, double diemTb)
    {
       this.maSv=maSv;
       this.hodem=hodem;
       this.ten=ten;
       this.ngaysinh= ngaysinh;
       this.que=que;
       this.diemTb=diemTb;

    }
    
    /** khởi một student với tham số đầu vào là một String*/
    public Student(String student) {
       String a[]= student.split(",");
       maSv    =a[0];
       hodem   =a[1];
       ten     =a[2];
       ngaysinh=new VnDate(a[3]);
       que     =a[4];
       diemTb  =Double.parseDouble(a[5]);
    }
    
    public String maSv(){
        return maSv;
    }
    
    public String hodem(){
        return hodem;
    }
    
    public String ten(){
        return ten;
    }
    
    public VnDate ngaysinh(){
        return ngaysinh;
    }
    
    public String que(){
        return que;
    }
    
     public double diemTb(){
         return diemTb;
    }

    /** tra ve chuoi bieu dien thong tin sinh vien*/
    @Override
    public String toString(){
        
     return String.format("%-10s | %10s %10s | %10s | %10s | %8.2f |  ",maSv,hodem,ten,ngaysinh,que,diemTb);
        
    }
    
    /** 
     * so sanh hai sinh vien theo maSV
     */
    public static class maSvOrder implements Comparator<Student>{
        public int compare(Student v, Student w) {
            return v.maSv.compareTo(w.maSv);        }
    }   

    /** 
     * so sanh hai sinh vien theo hodem
     */
    public static class hodemOrder implements Comparator<Student>{
        public int compare(Student v, Student w) {
            return v.hodem.compareTo(w.hodem);        }
    }

    /** 
     * so sanh hai sinh vien theo ten
     */
    public static class tenOrder implements Comparator<Student>{
        public int compare(Student v, Student w) {
            if(v.ten != w.ten)
                return v.ten.compareTo(w.ten);
            return  v.hodem.compareTo(w.hodem);
        }
    }

    /** 
     * so sanh hai sinh vien theo ngaysinh
     */
    public static class ngaysinhOrder implements Comparator<Student>{
        public int compare(Student v, Student w) {
            return v.ngaysinh.compareTo(w.ngaysinh);        }
    }
    
    /** 
     * so sanh hai sinh vien theo que
     */
    public static class queOrder implements Comparator<Student>{
        public int compare(Student v, Student w) {
            return v.que.compareTo(w.que);        }
    }

    public int compareTo(Student that) {
        return Double.compare(this.diemTb, that.diemTb);
    }

    /**
    * So sánh sinh viên này với một đối tượng khác.
    *
    * @param other sinh viên khác
    * @return true nếu sinh viên này bằng {@code other}; false nếu ngược lại
    */
   @Override
   public boolean equals(Object other) {
       if (other == this)
           return true;
       if (other == null)
           return false;
       if (other.getClass() != this.getClass())
           return false;
       Student that = (Student) other;
       return (this.maSv.equals(that.maSv)) && (this.diemTb == that.diemTb) 
               && (this.hodem.equals(that.hodem))
               && (this.ten.equals(that.ten))
               && (this.ngaysinh.equals(that.ngaysinh))
               && (this.que.equals( that.que)) ;
   }
   
   
   
   /**
    * Trả về mã băm của sinh viên này.
    *
    * @return mã băm của sinh viên này
    */
   public int hashCode() {
       int hash = 1;
       hash = 31 * hash + maSv.hashCode();
       hash = 31 * hash + hodem.hashCode();
       hash = 31 * hash + ten.hashCode();
       hash = 31 * hash + ngaysinh.hashCode();
       hash = 31 * hash + que.hashCode();
       hash = 31 * hash + ((Double) diemTb).hashCode();
       return hash;
       // return Objects.hash(maSv,hodem, ten, ngaysinh, diemTb);
   }
   /** Tính điểm trung bình */

  public static void main(String[] args) {
    Student a[] = new Student[4];
    a[0] =new Student("231230885,dangquang,son,28/09/2005,nghean,29.0");
    a[1] =new Student("123456786,hoangduc,trong,26/3/2005,thainuyen,30.43");
    a[2] = new Student("122121212,buiminh,thuong,14/6/2005,hoabinh,33.34");
        a[3] = new Student("121435546,dovan,an,12/2/2004,thaibinh,28.90");
    
    StdOut.println("chưa săp  xếp:");
    Arrays.sort(a);
    for(Student c: a) {
        StdOut.println(c);
    }
    
    StdOut.println("theo maSV :");
    Arrays.sort(a, new maSvOrder());
    for(Student c: a) {
        StdOut.println(c);
    }
    
    StdOut.println("theo ten :");
    Arrays.sort(a, new tenOrder());
    for(Student c: a) {
        StdOut.println(c);
    }
    
}
    
    
}
