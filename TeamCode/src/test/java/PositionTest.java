import static org.junit.jupiter.api.Assertions.assertEquals;

import org.firstinspires.ftc.teamcode.util.MathUtils;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * <a href="https://google.github.io/styleguide/javaguide.html#s5.2.3-method-names">Google recommendation for naming of tests</a>
 */
public class PositionTest {
    @Test
    public void multiplyMatrixVector_2DInput() {
        assertEquals(List.of(23.0, 34.0), List.of(Arrays.stream(MathUtils.multiplyMatrixVector(new double[][] {{1.0,3.0}, {2.0,4.0}}, new double[] {5.0,6.0})).boxed().toArray(Double[]::new)));
    }

    @Test
    public void calculateRobotPosition_nonOrthogonalSituation() {
        // Problem
        double x = 100.0, y = 123.0, aprilTagX = 81.0, aprilTagY = 90.0;
        double aprilTagOrientation = Math.toRadians(50.0);

        double[] relativeCoordinates = MathUtils.rotate2DVector(aprilTagOrientation, x - aprilTagX, y - aprilTagY);
        // Solution
        assertEquals(List.of(x, y), List.of(Arrays.stream(MathUtils.calculateRobotPosition(relativeCoordinates[0], relativeCoordinates[1], aprilTagOrientation, aprilTagX, aprilTagY)).boxed().toArray(Double[]::new)));
    }

    @Test
    public void calculateRobotPosition_orthogonalSituation() {
        assertEquals(List.of(466.0, 356.0), List.of(Arrays.stream(MathUtils.calculateRobotPosition(-100.0, 10.0, MathUtils.TAG_13_ORIENTATION, 366.0, 366.0)).boxed().toArray(Double[]::new)));
    }

    @Test
    public void calculateRobotPosition_orthogonalSituation2() {

        System.out.println(MathUtils.calculateRobotPosition(new AprilTagDetection(12, Double.NaN, Float.NaN, null, null, null)));
    }
}
