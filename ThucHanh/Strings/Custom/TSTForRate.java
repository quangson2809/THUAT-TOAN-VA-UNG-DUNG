package Custom;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.TST;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TSTForRate implements TriesForExample {
    TST<Double> dssv ;

    public TSTForRate(){
        this.dssv = new TST<>();
    }
    @Override
    public void getInput(File[] files) throws FileNotFoundException {
        if (files.length == 0) {
            throw new FileNotFoundException();
        }

        Scanner scanner = new Scanner(files[0]);
        if (!scanner.hasNextLine()) {
            throw new FileNotFoundException("file is empty");
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Student student = new Student(line);
            String Key = (student.hodem().trim() +" " + student.ten().trim());
            Key = Key.replaceAll("\\s+"," ");
            dssv.put(Key, student.diemTb());
        }                                                                                                                                                                  
        scanner.close();
//
    } public class StudentRate{
        private String ten;
        private Double diem;

        public StudentRate(String ten, Double diem){
            this.ten = ten;
            this.diem = diem;
        }

        @Override
        public String toString() {
            return this.ten + "  " + this.diem;
        }

    }

    @Override
    public Iterable<StudentRate> search(String query) {
        query = query.trim().replaceAll("\\s+"," ");
        Queue<StudentRate > results= new Queue<>();
        for(String key : this.dssv.keysWithPrefix(query)){
            StudentRate s = new StudentRate(key,this.dssv.get(key));
            results.enqueue(s);
        }
        return results;
    }
    

    public void displayAll() {
        System.out.println("size : " + this.dssv.size());
        for (String key : this.dssv.keys()) {
            System.out.println(key + " : " + this.dssv.get(key));
        }
    }

    public static void main(String[] args) {
        List<File> list = new ArrayList<>();
        list.add(new File("Custom/studentUnicode16.csv"));
        File [] files = list.toArray(new File[list.size()]);
        TSTForRate trie = new TSTForRate();
        try {
            trie.getInput(files);
            trie.displayAll();
            for(StudentRate student : trie.search("Đặng    Quang ")){
                System.out.println("-----" + student );
            }
            for(StudentRate student : trie.search("   Nguyễn   ")){
                System.out.println("-----" + student );
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
