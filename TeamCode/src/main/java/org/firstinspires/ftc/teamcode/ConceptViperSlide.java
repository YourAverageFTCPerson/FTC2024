package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.EffectivelyFinal;

/**
 * TODO: find CPR first
 */
@Disabled
@TeleOp(name = "Concept Viper Slide", group = "Concept")
public class ConceptViperSlide extends LinearOpMode { // 4 stage 336 mm
    private DcMotor motor; // 2 223s, 1 312

    private static int CONVERSION = 4;

    /**
     * From goBUILDA website.
     */
    private static final float YELLOW_JACKET_312_PPR = 537.7f * CONVERSION;

    private static final float YELLOW_JACKET_223_PPR = 751.8f * CONVERSION;

    private static final double FULL_EXTENSION = 8.13333333333;

    @EffectivelyFinal
    private static boolean USING_223 = true;

    @Override
    public void runOpMode() {
        this.motor = this.hardwareMap.get(DcMotor.class, "viperMotor");
        this.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motor.setPower(0d);
        this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motor.setTargetPosition(0); // Return to original position
        this.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.waitForStart();
        while (this.opModeIsActive()) {
            if (this.gamepad1.a) {
                this.motor.setTargetPosition((int) Math.round(FULL_EXTENSION * (USING_223 ? YELLOW_JACKET_223_PPR : YELLOW_JACKET_312_PPR)));
            } else {
                this.motor.setTargetPosition(0);
            }
        }
    }
}
