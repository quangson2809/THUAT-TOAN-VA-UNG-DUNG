package Custom;

import java.io.File;
import java.io.FileNotFoundException;

public interface TriesForExample {
    public void getInput(File []files) throws FileNotFoundException;
    public Iterable search(String query);
}
