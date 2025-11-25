package Custom;

public class Interval1d implements Comparable<Interval1d>{

    private int low;
    private int high;

    public Interval1d(int low, int high) {
        this.low = low;
        this.high = high;
    }

    public int getLow() {
        return low;
    }

    public int getHigh() {
        return high;
    }
    public int min(){
        return low;
    }
    public int max(){
        return high;
    }
    public boolean intersects(Interval1d that){
        if(this.high < that.low ) return false;
        if (that.high <this.low ) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Interval{" +
                "low=" + low +
                ", high=" + high +
                '}';
    }

    @Override
    public int compareTo(Interval1d o) {
        if (low < o.low) return -1;
        if (low > o.low) return 1;
        return 0;
    }
}
