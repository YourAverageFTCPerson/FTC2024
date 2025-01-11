package org.firstinspires.ftc.teamcode.util.hardware;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class ViperActions {
    private final ViperHardware hardware;

    private boolean notInitialized = true;

    public final Action ascend;

    public final Action descend;

    public ViperActions(OpMode opMode) {
        this.hardware = new ViperHardware(opMode);
        this.ascend = t -> {
            if (this.notInitialized) {
                this.hardware.initialize();
                this.notInitialized = false;
            }
            this.hardware.extend();
            return false;
        };
        this.descend = t -> {
            if (this.notInitialized) {
                this.hardware.initialize();
                this.notInitialized = false;
            }
            this.hardware.retract();
            return false;
        };
    }
}