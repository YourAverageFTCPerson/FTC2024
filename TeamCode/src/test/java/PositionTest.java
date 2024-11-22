import static org.junit.jupiter.api.Assertions.assertEquals;

import org.firstinspires.ftc.teamcode.util.MathUtils;
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

    public static double[] rotate2DVector(double angle, double[] vector) {
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
        // Problem
        double x = 100.0, y = 123.0, aprilTagX = 81.0, aprilTagY = 90.0;
        double aprilTagOrientation = Math.toRadians(50.0);

        double[] relativeCoordinates = MathUtils.rotate2DVector(aprilTagOrientation, aprilTagX - x, aprilTagY - y);
        // Solution
        assertEquals(List.of(x, y), List.of(Arrays.stream(MathUtils.calculateRobotPosition(relativeCoordinates[0], relativeCoordinates[1], aprilTagOrientation, aprilTagX, aprilTagY)).boxed().toArray(Double[]::new)));
    }
}
