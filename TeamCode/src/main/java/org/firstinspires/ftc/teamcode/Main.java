package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.EffectivelyFinal;

@TeleOp(name = "MecanumProgram v2", group = "Competition")
public class Main extends LinearOpMode {
    private DcMotor motor; // 2 223s, 1 312

//    private static final int CONVERSION = 1;

    /**
     * From goBUILDA website.
     */
    public static final float YELLOW_JACKET_312_PPR = 537.7f;

    public static final float YELLOW_JACKET_223_PPR = 751.8f;

    public static final double REVOLUTIONS_FOR_FULL_EXTENSION = 8.1;


    @EffectivelyFinal
    public static Boolean USING_223 = true;

    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;
    private Servo bucket;

    @Override
    public void runOpMode() {
        this.frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        this.backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        this.frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        this.backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        this.motor = this.hardwareMap.get(DcMotor.class, "viperMotor");
        this.bucket = hardwareMap.get(Servo.class, "bucket");
        motor.setDirection(DcMotor.Direction.REVERSE);
        final int fullExtension = (int) Math.round(REVOLUTIONS_FOR_FULL_EXTENSION * (USING_223 ? YELLOW_JACKET_223_PPR : YELLOW_JACKET_312_PPR)) - 500;
        this.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motor.setPower(0.0);
        this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motor.setTargetPosition(0); // Return to original position
        this.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        this.motor.setPower(1);
        this.waitForStart();
        int targetPosition = 0;
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        int upordown = 2;
        float stickval;
        boolean precise = false;
        while (this.opModeIsActive()) {
            stickval = gamepad2.left_stick_y;
            telemetry.addData("Current Position", motor.getCurrentPosition());
            telemetry.addData("Target Position", motor.getTargetPosition());
            telemetry.addData("Full Extension", fullExtension);
            telemetry.addData("UporDown", upordown);
            telemetry.addData("precise", precise);
            telemetry.addData("motor", motor.getPower());
            telemetry.addData("stickval", stickval);
            telemetry.update();
            if (gamepad2.right_bumper) {
                bucket.setPosition(1.0);
                if (gamepad2.left_bumper) {
                    bucket.setPosition(0);
                }
                if (gamepad2.b) {
                    precise = !precise;
                }
                if (precise) {
                    motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    if (-stickval > 0) {
                        upordown = 0;
                        this.motor.setTargetPosition(targetPosition = fullExtension);
                        if (motor.getCurrentPosition() - 20 > targetPosition && upordown == 0) {
                            this.motor.setPower(0.0);
                        } else {
                            motor.setPower(-gamepad2.left_stick_y / 2.0);
                        }
                    } else if (-stickval < 0) {
                        upordown = 1;
                        if (motor.getCurrentPosition() - 50 <= 0 && upordown == 1) {
                            this.motor.setPower(0.0);
                        } else {
                            motor.setPower(-gamepad2.left_stick_y / 2.0);
                        }


                    }
                } else {
                    motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    if (this.gamepad2.y) {
                        upordown = 0;
                        this.motor.setTargetPosition(targetPosition = fullExtension);

                        while (upordown == 0) {
                            telemetry.update();

                            if (motor.getCurrentPosition() - 20 > targetPosition && upordown == 0) {
                                this.motor.setPower(0.0);
                                upordown = 2;
                            } else {
                                this.motor.setPower(1);
                            }
                        }

                    } else if (gamepad2.a) {
                        upordown = 1;
                        targetPosition = 0;


                        while (upordown == 1) {
                            telemetry.update();
                            if (motor.getCurrentPosition() - 75 <= targetPosition && upordown == 1) {
                                this.motor.setPower(0.0);
                                upordown = 2;
                            } else {
                                this.motor.setPower(-1);
                            }


                        }

                    }


                }

                double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
                double x = gamepad1.left_stick_x * -1.1; // Counteract imperfect strafing
                double rx = gamepad1.right_stick_x;

                // Denominator is the largest motor power (absolute value) or 1
                // This ensures all the powers maintain the same ratio,
                // but only if at least one is out of the range [-1, 1]
                double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
                double frontLeftPower = (y + x + rx) / denominator;
                double backLeftPower = (y - x + rx) / denominator;
                double frontRightPower = (y - x - rx) / denominator;
                double backRightPower = (y + x - rx) / denominator;

                frontLeftMotor.setPower(frontLeftPower);
                backLeftMotor.setPower(backLeftPower);
                frontRightMotor.setPower(frontRightPower);
                backRightMotor.setPower(backRightPower);
            }
        }
    }
}
