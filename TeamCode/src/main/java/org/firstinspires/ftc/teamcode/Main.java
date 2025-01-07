package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.util.ViperHardware;
import org.firstinspires.ftc.teamcode.util.hardware.MecanumHardware;

@TeleOp(name = "MecanumProgram v2", group = "Competition")
public class Main extends LinearOpMode {
    @Override
    public void runOpMode() {
        MecanumHardware mecanum = new MecanumHardware(this);

        ViperHardware viper = new ViperHardware(this);

        waitForStart();

        Gamepad gamepad1 = new Gamepad(), previousGamepad1 = new Gamepad(), gamepad2 = new Gamepad(), previousGamepad2 = new Gamepad();

        while (opModeIsActive()) {
            gamepad1.copy(this.gamepad1);

            if (gamepad1.a && !previousGamepad1.a) {
                viper.extend();
            } else if (gamepad1.b && !previousGamepad1.b) {
                viper.retract();
            }

            mecanum.driveWithPower(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        }
    }
}
