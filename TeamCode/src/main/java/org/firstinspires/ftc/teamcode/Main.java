package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.util.hardware.BucketActions;
import org.firstinspires.ftc.teamcode.util.hardware.GrabberActions;
import org.firstinspires.ftc.teamcode.util.hardware.RampActions;
import org.firstinspires.ftc.teamcode.util.hardware.ViperHardware;
import org.firstinspires.ftc.teamcode.util.hardware.MecanumHardware;

@TeleOp(name = "MecanumProgram v2", group = "Competition")
public class Main extends LinearOpMode {
    @Override
    public void runOpMode() {
        MecanumHardware mecanum = new MecanumHardware(this);

        mecanum.initialize();

        ViperHardware viper = new ViperHardware(this);

        GrabberActions grabberActions = new GrabberActions(this, "grabber");
        BucketActions bucketActions = new BucketActions(this);
        RampActions rampActions = new RampActions(this);

        viper.initialize();

        waitForStart();

        Gamepad gamepad1 = new Gamepad(), previousGamepad1 = new Gamepad(), gamepad2 = new Gamepad(), previousGamepad2 = new Gamepad();

        while (opModeIsActive()) {
            gamepad1.copy(this.gamepad1);

            if (gamepad1.a && !previousGamepad1.a) {
                viper.extend();
            } else if (gamepad1.b && !previousGamepad1.b) {
                viper.retract();
            }

            if (gamepad1.x && !previousGamepad1.x) {
                grabberActions.open.run(null);
            } else if (gamepad1.y && !previousGamepad1.y) {
                grabberActions.close.run(null);
            }

            if (gamepad1.dpad_left && !previousGamepad1.dpad_left) {
                rampActions.lift.run(null);
            } else if (gamepad1.dpad_right && !previousGamepad1.dpad_right) {
                rampActions.lower.run(null);
            }

            if (gamepad1.dpad_up && !previousGamepad1.dpad_up) {
                bucketActions.tilt.run(null);
            } else if (gamepad1.dpad_down && !previousGamepad1.dpad_down) {
                bucketActions.right.run(null);
            }

            mecanum.driveWithPower(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        }
    }
}
