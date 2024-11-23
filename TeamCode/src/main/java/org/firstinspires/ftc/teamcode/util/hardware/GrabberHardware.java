package org.firstinspires.ftc.teamcode.util.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class GrabberHardware {
    public Servo servo;

    public GrabberHardware(OpMode opMode, String motorName) {
        this.servo = opMode.hardwareMap.get(Servo.class, motorName);
    }

    public GrabberHardware(Servo servo) {
        this.servo = servo;
    }

    public void open() {
        this.servo.setPosition(0.0);
    }

    public void close() {
        // TODO
        this.servo.setPosition(0.5);
    }
}
