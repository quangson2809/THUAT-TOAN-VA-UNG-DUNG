package Custom;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.TrieST;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class TrieSTForDocumnet implements TriesForExample {
    private TrieSTByHashmap<Integer> doc ;

    public TrieSTForDocumnet(){
        this.doc = new TrieSTByHashmap<Integer>();
    }

    @Override
    public void getInput(File[] files) throws FileNotFoundException {
        for(File file: files) {
            System.setIn(new FileInputStream(file));
            if (StdIn.isEmpty()) {
                throw new FileNotFoundException("file is empty");
            }
            /// next() sẽ đọc dữ liệu từ vị trí hiện tại cho đến khi gặp khoảng trắng hoặc delimiter (mặc định là khoảng trắng, tab, xuống dòng).
            String str = StdIn.readAll();
            String[] temp = str.split("/");
            for (int i = 0; i < temp.length; i++) {
                String key = temp[i].trim().replaceAll("\\s+", " ");
                doc.put(key, i);
            }
        }
    }

    @Override
    public Iterable<String> search (String query){
        return this.doc.keysWithPrefix(query);
    }

    public void displayAll(){
        System.out.println("size: "+ this.doc.size());
        for(String key : doc.keys()){
            System.out.println(key +" " + this.doc.get(key));
        }
    }

    public static void main(String[] args) {
        TrieSTForDocumnet trie = new TrieSTForDocumnet();
        List<File> list = new ArrayList<>();
        list.add(new File("Custom/banhmuot.txt"));
        File [] files = list.toArray(new File[list.size()]);
        try {
            trie.getInput(files);
            trie.displayAll();
            String query = "đi";
            System.out.println("====query: "+ query);
            for(String key : trie.search(query)){
                System.out.println(key);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
