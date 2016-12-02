import java.util.Arrays;
import java.util.Random;

public class PointSeparatorPerceptron {

    private static final double LEARNING_RATE = 0.1;
    private static final int BIAS = 1;
    private static final Random r = new Random();

    private int[] expectedOutputs;

    private int[][] inputs;

    private double[] weights;

    //Constructor for a perceptron to be trained with random points
    public PointSeparatorPerceptron() {
        //Since the inputs are randomly assigned and the outputs are locally computed we initialize the 2 vectors with null.
        this.inputs = null;
        this.expectedOutputs = null;

        //Initialize the weights as random values.
        weights = new double[3];
        for (int i = 0; i < weights.length; i++)
            weights[i] = r.nextDouble() * 2 - 1;

        //Print the initial weights.
        System.out.println("Initial random weights be: " + Arrays.toString(weights));
    }

    //Constructor for a perceptron that takes in a list of inputs and a list of corresponding outputs.
    public PointSeparatorPerceptron(int[][] inputs, int[]expectedOutputs) {
        //Store the inputs/outputs.
        this.inputs = inputs;
        this.expectedOutputs = expectedOutputs;

        //Initialize the weights as random values.
        weights = new double[inputs[0].length];
        for (int i = 0; i < weights.length; i++)
            weights[i] = r.nextDouble() * 2 - 1;


        //Print the initial weights.
        System.out.println("Initial random weights be: " + Arrays.toString(weights));
    }

    @Deprecated
    private void train(long iterations) {

        for (long iter = 0; iter <= iterations; iter++) {
            // Initializing a random point and putting it inside a vector with the BIAS input value.
            int i1 = r.nextInt(200) - 100;
            int i2 = r.nextInt(200) - 100;
            int[] inputVector = new int[]{i1, i2, BIAS};
            // Computing the error by deducting the Computed value from the Expected value.
            int error = computeTarget(i1, i2) - computeOutput(inputVector);
            // Adjusting the weights.
            adjustWeights(inputVector, error);
        }
        System.out.println(Arrays.toString(weights));
    }

    //Train method that iterates through the lists of inputs and outputs that you pass and trains
    private void train() {
        int globalError;
        int epochs = 0;
        int iterations = 0;

        do {
            epochs++;               // First epoch == First run through all the presented data
            globalError = 0;

            for (int i = 0; i < expectedOutputs.length; i++) {
                int localError = expectedOutputs[i] - computeOutput(inputs[i]);
                globalError += Math.abs(localError);
                adjustWeights(inputs[i], localError);
                iterations++;
            }

            System.out.println(globalError);
            //System.out.println("globalError = " + globalError);
        } while (globalError != 0);

        System.out.println("total epochs = " + epochs);
        System.out.println(Arrays.toString(weights));
    }

    private int computeTarget(int x, int y) {
        if (y >= 2*x + 1) return 1;
        else              return 0;
    }

    private int computeOutput(int[] input) {
        double linearCombination = 0;
        for (int i = 0; i < input.length; i++)
            linearCombination += weights[i] * input[i];

        //Step function with the threshold of 0
        return (linearCombination >= 0) ? 1 : 0;
    }

    private void adjustWeights(int[] input, int error) {

        //System.out.println("Beep!");
        for (int i = 0; i < weights.length; i++)
            weights[i] += LEARNING_RATE * error * input[i];
    }

    private void howGoodIsMyPerceptron() {
        System.out.println("Starting to classify points");
        int i = 0;
        while(i <= Integer.MAX_VALUE / 2) {
            i++;
            int x = r.nextInt(400) - 200;
            int y = r.nextInt(400) - 200;
            if (!this.testPoint(x , y))
                System.out.println("Error made at " + i + "th point.");
            //System.out.println("y = " + y + "  x = " + x);
        }
        System.out.println("I classified " + i + " points successfully.");
    }

    private boolean testPoint (int x, int y) {
        int whatPerceptronSays = computeOutput(new int[]{x, y, 1});
        //System.out.println("whatPerceptronSays = " + whatPerceptronSays);
        int whatItShouldBe = (y >= 2 * x + 1) ? 1 : 0;
        //System.out.println("whatItShouldBe = " + whatItShouldBe);

        return whatPerceptronSays == whatItShouldBe;
    }

    private void setWeights(double[] weights) {
        this.weights = weights;
    }




    public static void main(String[] args) {

        //Here I created a test set of points on a plane.
        int[][] inputs = new int[500000][3];
        int[] outputs = new int[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            // Initialize 500k points with random coordinates between -10000 and 10000.
            inputs[i][0] = r.nextInt(2000000) - 1000000;
            inputs[i][1] = r.nextInt(2000000) - 1000000;
            // Set the bias node for each data-point.
            inputs[i][2] = 1;
            // Compute whether the randomly generated point is Above ( 1 ) or Under ( 2 ) the line.
            outputs[i] = (inputs[i][1] >= 2 * inputs[i][0] + 1) ? 1 : 0;
        }

        PointSeparatorPerceptron p = new PointSeparatorPerceptron();
        p.train(100000);
        p.howGoodIsMyPerceptron();


//        PointSeparatorPerceptron p = new PointSeparatorPerceptron(inputs, outputs);
//        p.train();
//        p.howGoodIsMyPerceptron();


//        PointSeparatorPerceptron p = new PointSeparatorPerceptron(new int[][]{new int[]{-1, 0, 1}, new int[]{0, 2, 1}, new int[]{0, 0, 1}},
//                                                                    new int[]{1, 1, 0});
//        p.train();
//        p.howGoodIsMyPerceptron();

        /*PointSeparatorPerceptron assignment1 = new PointSeparatorPerceptron(new int[][]{new int[]{0,0,1}, new int[]{0,1,1}, new int[]{1,0,1}, new int[]{1,1,1}}, new int[]{0, 1, 1, 1});
        assignment1.setWeights(new double[]{0.1, 0.2, -0.3});
        assignment1.train();*/

    }
}
