import java.util.List;
import java.util.Vector;

public class IrisData {
    public String irisName;
    public List<Double> attributes;

    public IrisData(String irisName, List<Double> attributes) {
        this.attributes = attributes;
        this.irisName = irisName;
    }

    public IrisData(List<Double> attributes) {
        this.attributes = attributes;

    }

    @Override
    public String toString() {
        return "IrisData{" +
                "type='" + irisName + '\'' +
                '}';
    }
}
