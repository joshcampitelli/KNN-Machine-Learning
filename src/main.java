import Metrics.*;
import java.util.*;

public class main {

    public static void main(String[] args) {

        MachineLearning Housing = new MachineLearning("housing");

        Housing.Learn("h1", new CartesianMetric(12, 25), new IntegerMetric(1200), new EnumMetric("new"), new IntegerMetric(500000));
        Housing.Learn("h2", new CartesianMetric(10, 50), new IntegerMetric(1000), new EnumMetric("old"), new IntegerMetric(300000));
        Housing.Learn("h3", new CartesianMetric(12, 25), new IntegerMetric(1200), new EnumMetric("new"), new IntegerMetric(400000));


        //Need to add print statement for representing the return values
        System.out.println("Predicted price of h4 is: " + Housing.Predict(1,"h4", new CartesianMetric(15, 20), new IntegerMetric(1000), new EnumMetric("new")));
        System.out.println("Predicted price of h5 is: " + Housing.Predict(2,"h5", new CartesianMetric(0,0), new IntegerMetric(1100), new EnumMetric("new")));
        System.out.println("Predicted price of h6 is: " + Housing.Predict(1,"h6", new CartesianMetric(20, 40), new IntegerMetric(900), new EnumMetric("old")));

        //Need something here to show the total error, either a getter or print at end of PredictError
        System.out.println("Prediction error of h7 is: " + Housing.PredictError(1,"h7", new CartesianMetric(15,25), new IntegerMetric(1200), new EnumMetric("new"), new IntegerMetric(500000)));
        System.out.println("Prediction error of h8 is: " + Housing.PredictError(2,"h8", new CartesianMetric(20, 40), new IntegerMetric(1000), new EnumMetric("old"), new IntegerMetric(350000)));
        System.out.println("Prediction error of h9 is: " + Housing.PredictError(3,"h9", new CartesianMetric(10, 30), new IntegerMetric(1100), new EnumMetric("new"), new IntegerMetric(450000)));

        MachineLearning Ladder = new MachineLearning("ladder");

        Ladder.Learn("L1", new IntegerMetric(10), new EnumMetric("new"), new IntegerMetric(5), new IntegerMetric(70));
        Ladder.Learn("L2", new IntegerMetric(15), new EnumMetric("old"), new IntegerMetric(7), new IntegerMetric(80));
        Ladder.Learn("L3", new IntegerMetric(20), new EnumMetric("new"), new IntegerMetric(10), new IntegerMetric(120));

        //Need to add print statement for representing the return values
        System.out.println("Predicted price of L4 is: " + Ladder.Predict(1,"L4", new IntegerMetric(12), new EnumMetric("old"), new IntegerMetric(5)));
        System.out.println("Predicted price of L5 is: " + Ladder.Predict(2,"L5", new IntegerMetric(13), new EnumMetric("new"), new IntegerMetric(5)));
        System.out.println("Predicted price of L6 is: " + Ladder.Predict(3,"L6", new IntegerMetric(18), new EnumMetric("old"), new IntegerMetric(10)));

        //Need something here to show the total error, either a getter or print at end of PredictError
        System.out.println("Prediction error of L7 is: " + Ladder.PredictError(1,"L7", new IntegerMetric(10), new EnumMetric("new"), new IntegerMetric(10), new IntegerMetric(50)));
        System.out.println("Prediction error of L8 is: " + Ladder.PredictError(2,"L8", new IntegerMetric(15), new EnumMetric("new"), new IntegerMetric(7), new IntegerMetric(100)));
        System.out.println("Prediction error of L9 is: " + Ladder.PredictError(3,"L9", new IntegerMetric(18), new EnumMetric("new"), new IntegerMetric(9), new IntegerMetric(110)));


    }

}
