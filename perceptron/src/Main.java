import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    static List<IrisData> traningData;
    static List<IrisData> testData;
    static String desiredType = "Iris-setosa"; //check for that type
    static {
        try {
            traningData = FileLoader.loadDataAlt("data/iris_training.txt"); //here you can upload your training data
            testData = FileLoader.loadDataAlt("data/iris_test.txt"); //and here test data
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static List<Double> trainedVector = train(traningData);




    public static void main(String[] args) {

        System.out.println("Trained vector of weights: " + trainedVector);
        test(testData);

        String input = "";

        while(!input.equals("exit")) {
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();


            String[] data = input.trim().split("\\s+");
            List<Double> irisData = new ArrayList<>();
            for (int i = 0; i < data.length; i++) {
                irisData.add(Double.parseDouble(data[i]));
            }

            IrisData iris = new IrisData(irisData);
            testData = new ArrayList<>();
            testData.add(iris);

            testSingle(testData);
        }

    }









    private static List<Double> train(List<IrisData> traningData){


        double theta = 0.0; //theta threshold

        //loading weights
        List<Double> weights = new ArrayList<>();
        for(int i = 0; i<traningData.get(0).attributes.size(); i++){
            weights.add(1.0);
        }
        weights.add(theta);


        double d; //right decision
        double y; //actual decision
        final double learnConst = 0.3; //learning constant


        //training
        boolean isTrained = false;
        while (!isTrained) {
            isTrained = true;

            for(IrisData iris : traningData) {
                List<Double> inputs = new ArrayList<>(iris.attributes);
                inputs.add(-1.0);

                if(desiredType.equals(iris.irisName))
                    d = 1;
                else
                    d = 0;

                double result = scalarProduct(inputs, weights);

                if(result >= theta)
                    y = 1.0;

                else
                    y = 0.0;

                if(d != y) {
                    isTrained = false;
                    while(d != y) {
                        weights = deltaRule(weights, d, y, learnConst, inputs);

                        result = scalarProduct(inputs, weights);

                        if(result >= theta)
                            y = 1.0;

                        else {
                            y = 0.0;
                        }
                    }
                }
            }

        }
        return weights;
    }



    //scalar product: sum of every [i] element of a vector * [i] element of another vector
    private static double scalarProduct(List<Double> inputs, List<Double> weights) {
        double result = 0.0;

        for (int i = 0; i < inputs.size(); i++) {
            result += (inputs.get(i) * weights.get(i));
        }

        return result;
    }


    //delta rule: computing new weights using the formula
    private static List<Double> deltaRule(List<Double> weights, double d, double y, double learnConst, List<Double> inputs) {
        List<Double> results = new ArrayList<>();
        double diff = d - y;
        inputs = getNewInputs(diff, learnConst, inputs);
        results = getNewWeights(weights, inputs);
        return results;
    }

    //support method to delta rule
    private static List<Double> getNewInputs(double dy, double learnConst, List<Double> inputs) {
        List<Double> newInputs = new ArrayList<>();
        for (int i = 0; i < inputs.size(); i++) {
            newInputs.add(dy * learnConst * inputs.get(i));
        }
        return newInputs;
    }

    //support method to delta rule
    private  static List<Double> getNewWeights(List<Double> weights, List<Double> inputs) {
        List<Double> newWeights = new ArrayList<>();
        for (int i = 0; i < weights.size(); i++) {
            newWeights.add(weights.get(i) + inputs.get(i));
        }
        return newWeights;
    }




    //checking how accurate is perceptron by comparing decisions on a test data
    private static void test(List<IrisData> testData) {
        double theta = 0.0;

        double countCorrect = 0;

        double d;

        for(IrisData iris : testData) {
            List<Double> inputs = new ArrayList<>(iris.attributes);
            inputs.add(theta);

            d = iris.irisName.equals(desiredType) ? 1 : 0;

            double result = scalarProduct(inputs, trainedVector);

            if(d == 1) {
                if (result >= theta) {
                    countCorrect++;
                }
            } else
            if (result < theta)
                countCorrect++;
        }

        System.out.println("Correct answers: " + countCorrect + ", " + (countCorrect/testData.size()) * 100 + "%");
    }





    //testing a single vector of attributes
    private static void testSingle(List<IrisData> testData) {
        double theta = 0.0;

        boolean isSetosa = false;

        for(IrisData iris : testData) {
            List<Double> inputs = new ArrayList<>(iris.attributes);
            inputs.add(theta);

            double result = scalarProduct(inputs, trainedVector);

            if (result >= theta) {
                isSetosa = true;
            }

        }

        if(isSetosa)
            System.out.println("Iris-setosa");
        else
            System.out.println("Not setosa");
    }

}
