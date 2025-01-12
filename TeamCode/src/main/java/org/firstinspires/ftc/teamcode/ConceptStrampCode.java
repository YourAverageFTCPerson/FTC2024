package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.EffectivelyFinal;

@TeleOp
public class ConceptStrampCode extends LinearOpMode {

    public void runOpMode() {
        DcMotor stramp = hardwareMap.dcMotor.get("stramp");
        boolean precisestramp = true;
        float stickvalstramp;


        waitForStart();

        while (opModeIsActive()) {
            stickvalstramp = gamepad2.right_stick_y;
            telemetry.addData("precisestramp",precisestramp);
            telemetry.addData("stramppower", stickvalstramp);
            telemetry.addData("poweryes", stramp.getPower());
            if (gamepad2.x) {
                precisestramp = !precisestramp;
            }
            if (precisestramp) {

                stramp.setPower(stickvalstramp / 4);
                telemetry.update();


            } else {
                stramp.setPower(stickvalstramp / 2);
                telemetry.update();
            }
            telemetry.update();


        }
    }
}