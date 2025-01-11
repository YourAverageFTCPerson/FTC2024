package org.firstinspires.ftc.teamcode.util.hardware;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class RampActions {
    private static final int TORQUENADO_CPR = 1440;

    /**
     * TODO
     */
    private static final int LIFTED = TORQUENADO_CPR / 2;

    private final DcMotor ramp;

    public final Action lower, lift;

    public RampActions(OpMode opMode) {
        this.ramp = opMode.hardwareMap.get(DcMotor.class, "ramp");
        this.ramp.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.ramp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.lower = t -> {
            this.ramp.setTargetPosition(0);
            this.ramp.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while (this.ramp.isBusy()) {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return false;
        };
        this.lift = t -> {
            this.ramp.setTargetPosition(LIFTED);
            this.ramp.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while (this.ramp.isBusy()) {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return false;
        };
    }
}
