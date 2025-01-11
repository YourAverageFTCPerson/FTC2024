package org.firstinspires.ftc.teamcode.util.hardware;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class BucketActions {
    private final Servo bucket;

    public final Action tilt;

    /**
     * The opposite of tilt
     */
    public final Action right;

    public BucketActions(OpMode opMode) {
        this.bucket = opMode.hardwareMap.get(Servo.class, "bucket");
        this.tilt = t -> {
            bucket.setPosition(1d);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return false;
        };
        this.right = t -> {
            bucket.setPosition(0d);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return false;
        };
    }
}
