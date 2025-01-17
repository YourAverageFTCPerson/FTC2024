package org.firstinspires.ftc.teamcode.util.hardware;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.util.MathUtils;
import org.firstinspires.ftc.teamcode.util.TweakableNumbers;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * NOTE: All lengths are in centimeters.
 *
 * @see org.firstinspires.ftc.robotcontroller.external.samples.RobotHardware
 */
public class MecanumHardware {
    public static final long DRIVING_MOTOR_COUNTS_PER_REVOLUTION = 525L;
    public static final double WHEEL_RADIUS = 5.0, WHEEL_CIRCUMFERENCE = MathUtils.TAU * WHEEL_RADIUS;
    public static final int COUNTS_PER_CM_OF_DRIVING_MOTORS = (int) (DRIVING_MOTOR_COUNTS_PER_REVOLUTION / WHEEL_CIRCUMFERENCE);

    private static final String CLASS_NAME = MecanumHardware.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    private final OpMode opMode;

    public double degreeError;
    public double radianError;

    private IMU imu;
    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;

    public static final double DEFAULT_DEGREE_ERROR = 1.0;

    public static double degreeErrorByDefault = DEFAULT_DEGREE_ERROR;

    public static final double DEFAULT_RADIAN_ERROR = Math.toRadians(DEFAULT_DEGREE_ERROR);

    public static double radianErrorByDefault = DEFAULT_RADIAN_ERROR;

    private boolean notInitialized = true;

    private void checkInitialized() {
        if (notInitialized) {
            throw new IllegalStateException("Not initialized");
        }
    }

    public MecanumHardware(OpMode opMode) {
        Long temp;
        this.degreeError = ((temp = TweakableNumbers.NUMBERS.get("a ")) == null) ? degreeErrorByDefault : temp / 1000.0;
        this.radianError = ((temp = TweakableNumbers.NUMBERS.get("b ")) == null) ? radianErrorByDefault : temp / 1000.0;
        this.opMode = opMode;
    }

    public void initialize() {
        if (!notInitialized) {
            throw new IllegalStateException("Already initialized");
        }

        LOGGER.logp(Level.INFO, CLASS_NAME, "initialize", "Initializing hardware");

        LOGGER.info("Initializing the imu variable");
        this.imu = this.opMode.hardwareMap.get(IMU.class, "imu");

        this.imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD))); // TODO read this and trust
        LOGGER.info("imu initialized");

        LOGGER.info("Initializing motor variables");

        this.frontLeftMotor = this.opMode.hardwareMap.get(DcMotor.class, "frontLeftMotor");
        this.frontRightMotor = this.opMode.hardwareMap.get(DcMotor.class, "frontRightMotor");
        this.backLeftMotor = this.opMode.hardwareMap.get(DcMotor.class, "backLeftMotor");
        this.backRightMotor = this.opMode.hardwareMap.get(DcMotor.class, "backRightMotor");

        this.frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        this.backLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        this.frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.brakeRobot();

        this.resetDrivingEncoders();

        this.enableDrivingMotors();

        LOGGER.info("Motor variables initialized");

        LOGGER.logp(Level.INFO, CLASS_NAME, "initialize", "Initialized hardware");

        this.notInitialized = false;
    }

    public void setPowers(double frontLeftPower, double backLeftPower, double frontRightPower, double backRightPower) {
        LOGGER.entering(CLASS_NAME, "setPowers", new Object[] { frontLeftPower, backLeftPower, frontRightPower, backRightPower });

        this.checkInitialized();

        this.frontLeftMotor.setPower(frontLeftPower);
        this.backLeftMotor.setPower(backLeftPower);
        this.frontRightMotor.setPower(frontRightPower);
        this.backRightMotor.setPower(backRightPower);

        LOGGER.exiting(CLASS_NAME, "setPowers");
    }

    public void driveWithPower(double y, double x, double rx) { // https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html#deriving-mecanum-control-equations
        LOGGER.entering(CLASS_NAME, "driveWithPower", new Object[] { y, x, rx });

        this.checkInitialized();

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        this.setPowers(frontLeftPower, backLeftPower, frontRightPower, backRightPower);

        LOGGER.exiting(CLASS_NAME, "driveWithPower");
    }

    public void turnUntilStopped(double power) {
        LOGGER.entering(CLASS_NAME, "turnUntilStopped", power);

        this.checkInitialized();

        this.setPowers(power, power, -power, -power);

        LOGGER.exiting(CLASS_NAME, "turnUntilStopped");
    }

    public void brakeRobot() {
        this.checkInitialized();
        this.setPowers(0.0, 0.0, 0.0, 0.0);
    }

    public void degreeTurnWithIMU(double amount) {
        LOGGER.entering(CLASS_NAME, "degreeTurnWithIMU", amount);

        this.checkInitialized();

        LOGGER.logp(Level.FINE, CLASS_NAME, "degreeTurnWithIMU", "degreeError: %f", this.degreeError);

        this.imu.resetYaw();
        this.turnUntilStopped(1.0);
        while (true) {
            if (Math.abs(this.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - amount) <= this.degreeError) {
                break;
            }
        }
        this.brakeRobot();

        LOGGER.exiting(CLASS_NAME, "degreeTurnWithIMU");
    }

    public void radianTurnWithIMU(double amount) {
        LOGGER.entering(CLASS_NAME, "radianTurnWithIMU", amount);

        this.checkInitialized();

        LOGGER.logp(Level.FINE, CLASS_NAME, "degreeTurnWithIMU", "radianError: %f", this.radianError);

        this.imu.resetYaw();
        this.turnUntilStopped(1.0);
        while (true) {
            if (Math.abs(this.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS) - amount) <= this.radianError) {
                break;
            }
        }
        this.brakeRobot();

        LOGGER.exiting(CLASS_NAME, "radianTurnWithIMU");
    }

    public void resetDrivingEncoders() {
        LOGGER.entering(CLASS_NAME, "resetDrivingEncoders");

        this.checkInitialized();

        this.frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        LOGGER.exiting(CLASS_NAME, "resetDrivingEncoders");
    }

    public void enableDrivingMotors() {
        LOGGER.entering(CLASS_NAME, "reEnableDrivingMotors");

        this.checkInitialized();

        this.frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        LOGGER.exiting(CLASS_NAME, "reEnableDrivingMotors");
    }

    public void makeDrivingMotorsRunToPosition() {
        LOGGER.entering(CLASS_NAME, "makeDrivingMotorsRunToPosition");

        this.checkInitialized();

        this.frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        LOGGER.exiting(CLASS_NAME, "makeDrivingMotorsRunToPosition");
    }

    public void moveWheels(int frontLeftDistance, int backLeftDistance, int frontRightDistance, int backRightDistance) {
        LOGGER.entering(CLASS_NAME, "moveWheels", new Object[] { frontLeftDistance, backLeftDistance, frontRightDistance, backRightDistance });

        this.checkInitialized();

        this.resetDrivingEncoders();

        this.frontLeftMotor.setTargetPosition(frontLeftDistance * COUNTS_PER_CM_OF_DRIVING_MOTORS);
        this.backLeftMotor.setTargetPosition(backLeftDistance * COUNTS_PER_CM_OF_DRIVING_MOTORS);
        this.frontRightMotor.setTargetPosition(frontRightDistance * COUNTS_PER_CM_OF_DRIVING_MOTORS);
        this.backRightMotor.setTargetPosition(backRightDistance * COUNTS_PER_CM_OF_DRIVING_MOTORS);

        this.makeDrivingMotorsRunToPosition();

        LOGGER.exiting(CLASS_NAME, "moveWheels");
    }

    public void moveWheels(double frontLeftDistance, double backLeftDistance, double frontRightDistance, double backRightDistance) {
        LOGGER.entering(CLASS_NAME, "moveWheels", new Object[] { frontLeftDistance, backLeftDistance, frontRightDistance, backRightDistance });

        this.checkInitialized();

        this.moveWheels((int) Math.round(frontLeftDistance), (int) Math.round(backLeftDistance), (int) Math.round(frontRightDistance), (int) Math.round(backRightDistance));

        LOGGER.exiting(CLASS_NAME, "moveWheels");
    }

    public void driveWithEncoders(double y, double x, double rx) {
        LOGGER.entering(CLASS_NAME, "driveWithEncoders", new Object[] { y, x, rx });

        this.checkInitialized();

        double frontLeftDistance = y + x + rx;
        double backLeftDistance = y - x + rx;
        double frontRightDistance = y - x - rx;
        double backRightDistance = y + x - rx;

        this.moveWheels(frontLeftDistance, backLeftDistance, frontRightDistance, backRightDistance);

        LOGGER.exiting(CLASS_NAME, "driveWithEncoders");
    }
}