import Metrics.*;

public class main {

    public static void main(String[] args) {

        MachineLearning Housing = new MachineLearning("housing");

        Housing.Learn("h1", new CartesianMetric(12, 25), new IntegerMetric(1200), new EnumMetric("new"), new IntegerMetric(500000));
        Housing.Learn("h2", new CartesianMetric(10, 50), new IntegerMetric(1000), new EnumMetric("old"), new IntegerMetric(300000));
        Housing.Learn("h3", new CartesianMetric(12, 25), new IntegerMetric(1200), new EnumMetric("new"), new IntegerMetric(400000));

        //Need to add print statement for representing the return values
        Housing.Predict("h4", new CartesianMetric(15, 20), new IntegerMetric(1000), new EnumMetric("new"));
        Housing.Predict("h5", new CartesianMetric(0,0), new IntegerMetric(1100), new EnumMetric("new"));
        Housing.Predict("h6", new CartesianMetric(20, 40), new IntegerMetric(900), new EnumMetric("old"));

        //Need something here to show the total error, either a getter or print at end of PredictError
        Housing.PredictError("h7", new CartesianMetric(15,25), new IntegerMetric(1200), new EnumMetric("new"), new IntegerMetric(500000));
        Housing.PredictError("h8", new CartesianMetric(20, 40), new IntegerMetric(1000), new EnumMetric("old"), new IntegerMetric(350000));
        Housing.PredictError("h9", new CartesianMetric(10, 30), new IntegerMetric(1100), new EnumMetric("new"), new IntegerMetric(450000));

        MachineLearning Ladder = new MachineLearning("ladder");

        Ladder.Learn("L1", new IntegerMetric(10), new EnumMetric("new"), new IntegerMetric(5), new IntegerMetric(70));
        Ladder.Learn("L2", new IntegerMetric(15), new EnumMetric("old"), new IntegerMetric(7), new IntegerMetric(80));
        Ladder.Learn("L3", new IntegerMetric(20), new EnumMetric("new"), new IntegerMetric(10), new IntegerMetric(120));

        //Need to add print statement for representing the return values
        Ladder.Predict("L4", new IntegerMetric(12), new EnumMetric("old"), new IntegerMetric(5));
        Ladder.Predict("L5", new IntegerMetric(13), new EnumMetric("new"), new IntegerMetric(5));
        Ladder.Predict("L6", new IntegerMetric(18), new EnumMetric("old"), new IntegerMetric(10));

        //Need something here to show the total error, either a getter or print at end of PredictError
        Ladder.PredictError("L7", new IntegerMetric(10), new EnumMetric("new"), new IntegerMetric(10), new IntegerMetric(50));
        Ladder.PredictError("L8", new IntegerMetric(15), new EnumMetric("new"), new IntegerMetric(7), new IntegerMetric(100));
        Ladder.PredictError("L9", new IntegerMetric(18), new EnumMetric("new"), new IntegerMetric(9), new IntegerMetric(110));


    }

}
