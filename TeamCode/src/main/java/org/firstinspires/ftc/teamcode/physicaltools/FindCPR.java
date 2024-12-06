package org.firstinspires.ftc.teamcode.physicaltools;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Find CPR", group = "Physical Tools")
public class FindCPR extends LinearOpMode {
    public static String motorName = "arm";

    @Override
    public void runOpMode() {
        DcMotor motor = this.hardwareMap.get(DcMotor.class, motorName);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.waitForStart();

        while (this.opModeIsActive()) {
            Log.i("FindCPR", "Position: " + motor.getCurrentPosition());
            this.telemetry.addData("Position", motor.getCurrentPosition());
            this.telemetry.update();
        }
    }
}
