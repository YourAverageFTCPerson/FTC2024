package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Disabled
@Autonomous
public class CuriousAutonomous extends LinearOpMode {
  @Override
  public void runOpMode() throws InterruptedException {
//
//    long COUNTS_PER_REVOLUTION_OF_DRIVING_MOTORS = 525;
//    double EncoderPositionsPerInch = 116.363636364;
//    double WheelCircumferenceMM = 100 * Math.PI;
//    long ViperCountsPerRevolution;
//    double ViperEncoderPositionsPerInch;
//    DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
//    DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
//    DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
//    DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
//    DcMotor viperMotor = hardwareMap.dcMotor.get("viperMotor");
//    Servo bucketServo = hardwareMap.servo.get("bucketServo");
//    Servo rampServo = hardwareMap.servo.get("rampServo");
//    IMU imu = hardwareMap.get(IMU.class, "imu");
//
//
//    imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingPosition.UP, RevHubOrientationOnRobot.USBFacingPosition.FORWARD)));
//    imu.resetYaw();
//
//    frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//    backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//    frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//    backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//    int frontLeftTarget = (int) (-96 * EncoderPositionsPerInch);
//    int frontRightTarget = (int) (96 * EncoderPositionsPerInch);
//    int backLeftTarget = (int) (96 * EncoderPositionsPerInch);
//    int backRightTarget = (int) (-96 * EncoderPositionsPerInch);
//
//    waitForStart();
//
//    while (opModeIsActive()) {
//      double yaw = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
//
//      frontLeftMotor.setTargetPosition(frontLeftTarget);
//      frontRightMotor.setTargetPosition(frontRightTarget);
//      backLeftMotor.setTargetPosition(backLeftTarget);
//      backRightMotor.setTargetPotion(backRightTarget);
//
//      frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//      frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//      backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//      backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//      while (frontLeftMotor.isBusy() && frontRightMotor.isBusy() && backLeftMotor.isBusy() && backRightMotor.isBusy()) {
//        // wait for them to get to the position and stop moving
//      }
//
//      frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//      frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//      backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//      backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//      frontLeftTarget = (12 * EncoderPositionsPerInch);
//      frontRightTarget = (12 * EncoderPositionsPerInch);
//      backLeftTarget = (12 * EncoderPositionsPerInch);
//      backRightTarget = (12 * EncoderPositionsPerInch);
//
//      frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//      frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//      backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//      backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//      while (frontLeftMotor.isBusy() && frontRightMotor.isBusy() && backLeftMotor.isBusy() && backRightMotor.isBusy()) {
//        // wait for them to get to the position and stop moving
//      }
//
//      frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//      frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//      backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//      backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//      while (Math.abs(yaw - -45) > 1) {
//        frontRightMotor.setPower(0.5);
//        backRightMotor.setPower(0.5);
//        frontLeftMotor.setPower(-0.5);
//        backLeftMotor.setPower(-0.5);
//      }
//      // insert viper code here
//      bucketServo.setPosition(123);
//
//      bucketServo.setPosition(0);
//
//      //viper code again
//
//      while (Yaw > math.abs(0)) {
//        frontRightMotor.setPower(-0.5);
//        backRightMotor.setPower(-0.5);
//        frontLeftMotor.setPower(0.5);
//        backLeftMotor.setPower(0.5);
//      }
//      frontLeftTarget = (-12 * EncoderPositionsPerInch);
//      frontRightTarget = (-12 * EncoderPositionsPerInch);
//      backLeftTarget = -(12 * EncoderPositionsPerInch);
//      backRightTarget = (-12 * EncoderPositionsPerInch);
//
//      frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//      frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//      backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//      backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//      while (frontLeftMotor.isBusy() && frontRightMotor.isBusy() && backLeftMotor.isBusy() && backRightMotor.isBusy()) {
//        // wait for them to get to the position and stop moving
//      }
//
//      frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//      frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//      backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//      backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//      int frontLeftTarget = (121 * EncoderPositionsPerInch);
//      int frontRightTarget = (-121 * EncoderPositionsPerInch);
//      int backLeftTarget = (-121 * EncoderPositionsPerInch);
//      int backRightTarget = (121 * EncoderPositionsPerInch);
//// // Going for another sample
////       frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
////       frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
////       backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
////       backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
////       while (frontLeftMotor.isBusy() && frontRightMotor.isBusy() && backLeftMotor.isBusy() && backRightMotor.isBusy()) {
////       // wait for them to get to the position and stop moving
////       }
//
////       frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
////       frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
////       backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
////       backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
////       int frontLeftTarget = (24 * EncoderPositionsPerInch);
////       int frontRightTarget = (-24 * EncoderPositionsPerInch);
////       int backLeftTarget = (-24 * EncoderPositionsPerInch);
////       int backRightTarget = (24 * EncoderPositionsPerInch);
//
////       frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
////       frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
////       backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
////       backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
////       while (frontLeftMotor.isBusy() && frontRightMotor.isBusy() && backLeftMotor.isBusy() && backRightMotor.isBusy()) {
////       // wait for them to get to the position and stop moving
////       }
//
//
//
//    }
  }
}
