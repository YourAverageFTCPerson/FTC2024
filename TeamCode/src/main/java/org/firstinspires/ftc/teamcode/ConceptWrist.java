package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.EffectivelyFinal;

@TeleOp
public class ConceptWrist extends LinearOpMode {
    private CRServo wrist;
    public void runOpMode() {
        wrist = hardwareMap.crservo.get("wrist");



        boolean stopstart = true;
        waitForStart();
        while (opModeIsActive()){
            if (gamepad2.dpad_up){
                if (stopstart){
                    wrist.setPower(1);
                    stopstart = false;
                }else{
                    wrist.setPower(0);
                    stopstart = true;
                }
            }
            if (gamepad2.dpad_down){
                if (stopstart){
                    wrist.setPower(-1);
                    stopstart = false;
                }else {
                    wrist.setPower(0);
                    stopstart = true;
                }
            }

        }
    }
}