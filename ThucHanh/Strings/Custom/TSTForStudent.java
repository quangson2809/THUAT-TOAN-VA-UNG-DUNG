package Custom;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.TST;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TSTForStudent implements TriesForExample {
    TST<Student> dssv ;

    public TSTForStudent(){
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
            dssv.put(Key, student);
        }
        scanner.close();
        for(int i=1; i< files.length;i++){

            String[] tokens=files[i].getName().split("\\.");System.out.println(tokens.length);
            String monhoc = tokens[0].trim().replaceAll("\\s+"," ");
            Scanner scn = new Scanner(files[i]);
            while(scn.hasNextLine()){
                String  line = scn.nextLine();
                String[] str = line.split(",");
                String keyforStudent = str[0].trim().replaceAll("\\s+"," ");
//                System.out.println(keyforStudent);
                Double diem = Double.parseDouble(str[1].trim());
                this.dssv.get(keyforStudent).bangdiem().put(monhoc,diem);
//                System.out.println("---------" + this.dssv.get(keyforStudent));
            }
            scn.close();
        }
    }

    @Override
    public Iterable<Student> search(String query) {
        query = query.trim().replaceAll("\\s+"," ");
        Queue<Student> results= new Queue<>();
        for(String key : this.dssv.keysWithPrefix(query)){
            results.enqueue(this.dssv.get(key));
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
        list.add(new File("Custom/Csdl.03.04.csv"));
        list.add(new File("Custom/Java.03.04.csv"));
        list.add(new File("Custom/Trr.03.03.csv"));
        File [] files = list.toArray(new File[list.size()]);
        TSTForStudent trie = new TSTForStudent();
        try {
            trie.getInput(files);
            trie.displayAll();
            for(Student student : trie.search("Đặng    Quang ")){
                System.out.println("-----" + student );
            }
            for(Student student : trie.search("   Nguyễn   ")){
                System.out.println("-----" + student );
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
