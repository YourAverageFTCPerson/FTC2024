package org.firstinspires.ftc.teamcode.util.hardware;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class GrabberActions {
    public Servo servo;

    public GrabberActions(OpMode opMode, String motorName) {
        this.servo = opMode.hardwareMap.get(Servo.class, motorName);
    }

    public GrabberActions(Servo servo) {
        this.servo = servo;
    }

    public final Action open = t -> {
        this.servo.setPosition(0.0);
        return false;
    };

    public final Action close = t -> {
        // TODO
        this.servo.setPosition(0.5);
        return false;
    };
}
