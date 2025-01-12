package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.util.EffectivelyFinal;
import org.firstinspires.ftc.teamcode.util.TweakableNumbers;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Basic Mecanum Drive")


public class MecanumTeleOp extends LinearOpMode {
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

    @Override
    public void runOpMode() throws InterruptedException {
        this.motor = this.hardwareMap.get(DcMotor.class, "viperMotor");
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
        // Declare our motors
        // Make sure your IDs match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        //under is the stramp code variables and motors
        DcMotor stramp = hardwareMap.dcMotor.get("stramp");
        boolean precisestramp = true;
        float stickvalstramp;
        CRServo wrist;
        Servo gate;
        boolean stopstart = true;
        //under is the wrist variables/servos

        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE); // USE "mecanum" config
        wrist = hardwareMap.crservo.get("wrist");
        gate = hardwareMap.servo.get("gate");
        waitForStart();

        while (opModeIsActive()) {
            stickval = gamepad2.left_stick_y;
            telemetry.addData("Current Position", motor.getCurrentPosition());
            telemetry.addData("Target Position", motor.getTargetPosition());
            telemetry.addData("Full Extension", fullExtension);
            telemetry.addData("UporDown", upordown);
            telemetry.addData("precise", precise);
            telemetry.addData("motor", motor.getPower());
            telemetry.addData("stickval", stickval);
            telemetry.update();
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

            stickvalstramp = gamepad2.left_stick_y;
            //next three lines are stramp telemetry
            telemetry.addData("precisestramp", precisestramp);
            telemetry.addData("stramppower", stickvalstramp);
            telemetry.addData("poweryes", stramp.getPower());

            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
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

            telemetry.addData("frontLeft", frontLeftPower);
            telemetry.addData("frontRight", frontRightPower);
            telemetry.addData("backLeft", backLeftPower);
            telemetry.addData("backRight", backRightPower);

            telemetry.update();
            //UNDER THIS IS THE STRAMP CODe
            if (gamepad2.x) {
                precisestramp = !precisestramp;
            }
            if (precisestramp) {

                stramp.setPower(stickvalstramp / 2);
                telemetry.update();


            } else {
                stramp.setPower(stickvalstramp / 1.5);
                telemetry.update();
            }
            telemetry.update();
            //under this is the concept WRIST main code
//            if (gamepad2.right_bumper){
//                wrist.setPower(1);
//            }
//            if (gamepad2.left_bumper){
//                wrist.setPower(0);
//            }
            if (gamepad2.right_bumper) {
                if (stopstart) {
                    wrist.setPower(1);
                    stopstart = false;
                } else {
                    wrist.setPower(0);
                    stopstart = true;
                }
            }
            if (gamepad2.left_bumper) {
                if (stopstart) {
                    wrist.setPower(-1);
                    stopstart = false;
                } else {
                    wrist.setPower(0);
                    stopstart = true;
                }
            }
            telemetry.update();

            if (gamepad2.dpad_right) {
                gate.setPosition(0.4);
            }
            if (gamepad2.dpad_left) {
                gate.setPosition(0.2);
            }


        }
    }
}

