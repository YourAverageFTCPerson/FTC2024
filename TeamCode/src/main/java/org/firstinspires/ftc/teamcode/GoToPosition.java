package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.MathUtils;
import org.firstinspires.ftc.teamcode.util.hardware.MecanumHardware;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

public class GoToPosition {
    protected GoToPosition() {
        throw new UnsupportedOperationException();
    }

    public static void driveDistance(DcMotor frontLeftMotor, DcMotor frontRightMotor, DcMotor backLeftMotor, DcMotor backRightMotor, double angle, double cm) {
    }

    public static void goTo(AprilTagDetection detection, double x, double y, MecanumHardware hardware) {
        double[] pos = MathUtils.calculateRobotPosition(detection);
//        hardware.radianTurnWithIMU(); // TODO
    }
}
