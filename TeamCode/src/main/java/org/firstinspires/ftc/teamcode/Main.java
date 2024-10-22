package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.EffectivelyFinal;

/**
 * Program entry point for competition.
 *
 * TODO TensorFlow for Auto Specimens
 */
@TeleOp(name = "Main", group = "Official")
public class Main extends LinearOpMode {
    @EffectivelyFinal
    public static Boolean IS_BLUE_ALLIANCE;

    @Override
    public void runOpMode() {
        if (IS_BLUE_ALLIANCE) {
            BlueAllianceCode.run(this);
        } else {
            RedAllianceCode.run(this);
        }
    }
}
