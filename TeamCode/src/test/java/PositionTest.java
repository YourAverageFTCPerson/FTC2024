import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class PositionTest {
    private static double[] multiplyMatrixVector(double[][] matrix, double[] vector) {
        double firstColumnX = matrix[0][0] * vector[0];
        double firstColumnY = matrix[1][0] * vector[0];
        double secondColumnX = matrix[0][1] * vector[1];
        double secondColumnY = matrix[1][1] * vector[1];
        return new double[] {firstColumnX + secondColumnX, secondColumnY + firstColumnY};
    }
    public double[] rotate2DVector(double angle, double[] vector) {
        angle = -angle;
        double[][] rotationMatrix = {
                {Math.cos(angle), Math.sin(angle)},
                {-Math.sin(angle), Math.cos(angle)}
        };
        return multiplyMatrixVector(rotationMatrix, vector);
    }

    @Test
    public void multiplyMatrixVectorTest() {
        assertEquals(List.of(23.0, 34.0), List.of(Arrays.stream(multiplyMatrixVector(new double[][] {{1.0,3.0}, {2.0,4.0}}, new double[] {5.0,6.0})).boxed().toArray(Double[]::new)));
    }

    @Test
    public void test() {
        double[] problem = extracted();
        double yaw = problem[0], range = problem[1];
        // Solution
        double x = range * Math.cos(yaw), y = range * Math.sin(yaw);
        double[] predictedCoordinates = rotate2DVector(problem[2], new double[] {x,y});
        System.out.println(Arrays.toString(predictedCoordinates));
    }

    private static double[] extracted() {
        double x = 100, y = 123, aprilTagX = 81, aprilTagY = 90;
        double aprilTagOrientation = 50 * Math.PI/180;
        double a = aprilTagX - x, c = aprilTagY - y, b = Math.sqrt(a*a+c*c);
        return new double[] {Math.acos((a*a+b*b-c*c)/(2*a*b)), b, aprilTagOrientation};
    }
}
