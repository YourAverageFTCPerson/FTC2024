package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.TweakableNumbers;

@TeleOp
public class ConceptArm extends LinearOpMode {
    private boolean movingArm;

    @Override
    public void runOpMode() {
        DcMotor arm = hardwareMap.get(DcMotor.class, "arm");
//        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        CRServo wrist = hardwareMap.get(CRServo.class, "wrist");

        Long temp = TweakableNumbers.NUMBERS.getOrDefault("dpad_down ", 400L);
        if (temp == null) {
            Log.wtf("ConceptArm", "temp == null");
            return;
        }
        long wristDelay = temp;
        temp = TweakableNumbers.NUMBERS.getOrDefault("dpad_right ", 125L);
        if (temp == null) {
            Log.wtf("ConceptArm", "temp == null");
            return;
        }
        long armDelay = temp;
        temp = TweakableNumbers.NUMBERS.getOrDefault("dpad_left", 600L);
        double armPower = temp / 1000;
        waitForStart();
//
//        Gamepad previousGamepad2 = new Gamepad();
//        Gamepad gamepad2 = new Gamepad();
//
//        while (opModeIsActive()) {
//            this.gamepad2.copy(gamepad2);
//            if (gamepad2.dpad_up && !) {
//                arm.setPower(1d);
//                sleep(125);
//                arm.setPower(0d);
//            } else if (gamepad2.dpad_down) {
//                arm.setPower(-1d);
//                sleep(125);
//                arm.setPower(0d);
//            }
//            if (gamepad2.dpad_left) {
//                wrist.setPower(-1d);
//            } else if (gamepad2.dpad_right) {
//                wrist.setPower(1d);
//            } else if (gamepad2.right_bumper) {
//                wrist.setPower(0d);
//            }
//            previousGamepad2.copy(ga);
//        }
        while (opModeIsActive()) {
            telemetry.addData("Moving Arm", movingArm);
            if (!movingArm) {
                if (gamepad2.dpad_up) {
                    new Thread(() -> {
                        this.movingArm = true;
                        arm.setPower(armPower);
                        sleep(armDelay);
                        arm.setPower(0d);
                        this.movingArm = false;
                    }).start();
                } else if (gamepad2.dpad_down) {
                    new Thread(() -> {
                        this.movingArm = true;
                        arm.setPower(-armPower);
                        sleep(armDelay);
                        arm.setPower(0d);
                        this.movingArm = false;
                    }).start();
                }
            }
            if (gamepad2.dpad_right) {
                wrist.setPower(1d);
                sleep(wristDelay);
                wrist.setPower(0d);
            } else if (gamepad2.dpad_left) {
                wrist.setPower(-1d);
                sleep(wristDelay);
                wrist.setPower(0d);
            }
        }
    }
}
