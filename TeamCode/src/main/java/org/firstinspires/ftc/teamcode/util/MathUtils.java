package org.firstinspires.ftc.teamcode.util;

import android.util.Log;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.Arrays;

/**
 * @author YourAverageFTCPerson
 */
public class MathUtils {
    /**
     * Straight to alliance neutral samples from AprilTag 12 or 15.
     */
    public static final double ANGLE_TO_GO_STRAIGHT = 0.605544663605; // arcsin(36/63.2455532034)

    protected MathUtils() { // Allow subclasses for extension (even though this is a utility class)
        throw new UnsupportedOperationException();
    }

    public static double[] multiplyMatrixVector(final double[][] matrix, final double[] vector) {
        if (vector.length != 2) {
            throw new IllegalArgumentException();
        }
        double firstColumnX = matrix[0][0] * vector[0];
        double firstColumnY = matrix[1][0] * vector[0];
        double secondColumnX = matrix[0][1] * vector[1];
        double secondColumnY = matrix[1][1] * vector[1];
        return new double[] { firstColumnX + secondColumnX, secondColumnY + firstColumnY };
    }

    public static double[] multiplyMatrixVector(final double[][] matrix, final double x, final double y) {
        double firstColumnX = matrix[0][0] * x;
        double firstColumnY = matrix[1][0] * x;
        double secondColumnX = matrix[0][1] * y;
        double secondColumnY = matrix[1][1] * y;
        return new double[] { firstColumnX + secondColumnX, secondColumnY + firstColumnY };
    }

    /**
     * @param angle in positive=counterclockwise radians.
     * @return a matrix that, when multiplied by a vector, rotates it by {@code angle}.
     */
    public static double[][] createRotationMatrix(double angle) {
        angle = -angle; // The matrix that I found on Wikipedia is for positive=clockwise
        return new double[][] {
                { Math.cos(angle), Math.sin(angle) },
                { -Math.sin(angle), Math.cos(angle) }
        };
    }

    /**
     * @param angle in RADIANS.
     * @param vector the vector coordinates as an array of length 2.
     * @return the rotated 2D vector.
     * @throws IllegalArgumentException if {@code vector.length != 2}
     */
    public static double[] rotate2DVector(double angle, final double[] vector) {
        if (vector.length != 2) {
            throw new IllegalArgumentException();
        }
        return multiplyMatrixVector(createRotationMatrix(angle), vector);
    }

    /**
     * @param angle in RADIANS.
     * @param x the x component of the vector.
     * @param y the y component of the vector.
     * @return the rotated 2D vector.
     */
    public static double[] rotate2DVector(double angle, final double x, final double y) {
        return multiplyMatrixVector(createRotationMatrix(angle), x, y);
    }

    /**
     * The circumference of a circle of radius 1 or a full turn in radians.
     */
    public static final double TAU = 2.0 * Math.PI;

    /**
     * <a href="https://ftc-docs.firstinspires.org/en/latest/_images/45-ITD-tag-numbers.png">From this picture.</a>
     */
    public static final double TAG_11_ORIENTATION = 0.0,
                               TAG_12_ORIENTATION = -TAU / 4.0,
                               TAG_13_ORIENTATION = TAU / 2.0,
                               TAG_14_ORIENTATION = TAU / 2.0,
                               TAG_15_ORIENTATION = TAU / 4.0,
                               TAG_16_ORIENTATION = 0.0;

    public static double getFieldAprilTagOrientation(int id) {
        switch (id) {
            case 11:
                return TAG_11_ORIENTATION;
            case 12:
                return TAG_12_ORIENTATION;
            case 13:
                return TAG_13_ORIENTATION;
            case 14:
                return TAG_14_ORIENTATION;
            case 15:
                return TAG_15_ORIENTATION;
            case 16:
                return TAG_16_ORIENTATION;
            default:
                throw new IllegalArgumentException("Tag: '" + id + "' isn't on the field. Only standard field tags are supported.");
        }
    }

    public static double[] calculateRobotPosition(double relativeX, double relativeY,
                                                  double aprilTagOrientation, double aprilTagX, double aprilTagY) {
        double[] predictedCoordinates = rotate2DVector(aprilTagOrientation, relativeX, relativeY);

        return new double[] { aprilTagX + predictedCoordinates[0], aprilTagY + predictedCoordinates[1] };
    }

    public static double[] calculateRobotPosition(AprilTagDetection detection) {
        Log.d("MathUtils", Arrays.toString(new Object[] {detection.ftcPose.x, detection.ftcPose.y,
                MathUtils.getFieldAprilTagOrientation(detection.id),
                detection.metadata.fieldPosition.get(0), detection.metadata.fieldPosition.get(1)}));
        return calculateRobotPosition(detection.ftcPose.x, detection.ftcPose.y,
                MathUtils.getFieldAprilTagOrientation(detection.id),
                detection.metadata.fieldPosition.get(0), detection.metadata.fieldPosition.get(1));
    }

    public static double inchToCm(double inches) {
        return inches * 2.54;
    }

    private static final double INVERSE_CONVERSION = 1 / 2.54;

    public static double cmToInch(double cm) {
        return cm * INVERSE_CONVERSION;
    }
}
