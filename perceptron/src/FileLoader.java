import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {

    public static List<IrisData> loadDataAlt(String filename) throws IOException {
        List<IrisData> data = new ArrayList<>();


        BufferedReader br = new BufferedReader(new java.io.FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            List<Double> attributesVector = new ArrayList<>();
            for (int i = 0; i < parts.length - 1; i++) {
                attributesVector.add(Double.parseDouble(parts[i].replaceAll(",",".")));
            }
            data.add(new IrisData(parts[parts.length-1], attributesVector));
        }
        br.close();
        return data;
    }
}
