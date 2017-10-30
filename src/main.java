import Metrics.*;

public class main {

    /**
	 * Run two examples of machine learning, Housing and Ladder. Prints results of predicts and error.
	 * 
	 * @author Ethan Morrill 
	 */
    public static void main(String[] args) {

        System.out.println("Housing machine Learning Example: \n");
        MachineLearning Housing = new MachineLearning("housing");
        

        // Teach housing problem 3 given examples of houses
        Housing.Learn("h1", new CartesianMetric(12, 25), new IntegerMetric(1200), new EnumMetric("new"), new IntegerMetric(500000));
        Housing.Learn("h2", new CartesianMetric(10, 50), new IntegerMetric(1000), new EnumMetric("old"), new IntegerMetric(300000));
        Housing.Learn("h3", new CartesianMetric(30, 100), new IntegerMetric(800), new EnumMetric("new"), new IntegerMetric(400000));


        // Predict 3 examples of houses, results are printed
        System.out.println("Predicted price of h4 is: " + Housing.Predict(1,"h4", new CartesianMetric(15, 20), new IntegerMetric(1000), new EnumMetric("new")));
        System.out.println("Predicted price of h5 is: " + Housing.Predict(2,"h5", new CartesianMetric(15,20), new IntegerMetric(1000), new EnumMetric("new")));
        System.out.println("Predicted price of h6 is: " + Housing.Predict(1,"h6", new CartesianMetric(20, 40), new IntegerMetric(900), new EnumMetric("old")));

        // Predict and calculate the error or 3 houses, error of predictions are printed
        System.out.println("Prediction error of h7 is: " + Housing.PredictError(1,"h7", new CartesianMetric(15,25), new IntegerMetric(1200), new EnumMetric("new"), new IntegerMetric(500000)));
        System.out.println("Prediction error of h8 is: " + Housing.PredictError(2,"h8", new CartesianMetric(20, 40), new IntegerMetric(1000), new EnumMetric("old"), new IntegerMetric(350000)));
        System.out.println("Prediction error of h9 is: " + Housing.PredictError(3,"h9", new CartesianMetric(10, 30), new IntegerMetric(1100), new EnumMetric("new"), new IntegerMetric(450000)));

        // Print total error of the problem.
        System.out.println("The total prediction error of the Housing problem is: "+ Housing.getTotalError() + "\n");

        System.out.println("Housing machine Learning Example: \n");
        MachineLearning Ladder = new MachineLearning("ladder");

        // Teach Ladder problem 3 given examples of ladders
        Ladder.Learn("L1", new IntegerMetric(10), new EnumMetric("new"), new IntegerMetric(5), new IntegerMetric(70));
        Ladder.Learn("L2", new IntegerMetric(15), new EnumMetric("old"), new IntegerMetric(7), new IntegerMetric(80));
        Ladder.Learn("L3", new IntegerMetric(20), new EnumMetric("new"), new IntegerMetric(10), new IntegerMetric(120));

        // Predict 3 examples of ladders, results are printed
        System.out.println("Predicted price of L4 is: " + Ladder.Predict(1,"L4", new IntegerMetric(12), new EnumMetric("old"), new IntegerMetric(5)));
        System.out.println("Predicted price of L5 is: " + Ladder.Predict(2,"L5", new IntegerMetric(12), new EnumMetric("old"), new IntegerMetric(5)));
        System.out.println("Predicted price of L6 is: " + Ladder.Predict(3,"L6", new IntegerMetric(12), new EnumMetric("old"), new IntegerMetric(5)));

        // Predict and calculate the error or 3 houses, error of predictions are printed
        System.out.println("Prediction error of L7 is: " + Ladder.PredictError(1,"L7", new IntegerMetric(10), new EnumMetric("new"), new IntegerMetric(10), new IntegerMetric(50)));
        System.out.println("Prediction error of L8 is: " + Ladder.PredictError(2,"L8", new IntegerMetric(15), new EnumMetric("new"), new IntegerMetric(7), new IntegerMetric(100)));
        System.out.println("Prediction error of L9 is: " + Ladder.PredictError(3,"L9", new IntegerMetric(18), new EnumMetric("new"), new IntegerMetric(9), new IntegerMetric(110)));

        // Print total error of the problem.
        System.out.println("The total prediction error of the Ladder problem is: "+ Ladder.getTotalError());

    }

}
