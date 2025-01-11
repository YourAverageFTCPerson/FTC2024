package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.hardware.MecanumHardware;

@Autonomous
public class DesperateAuto extends LinearOpMode {
    @Override
    public void runOpMode() {
        MecanumHardware mecanumHardware = new MecanumHardware(this);
        mecanumHardware.driveWithPower(1d, 0d, 0d);
        sleep(1000L);
        mecanumHardware.brakeRobot();
    }
}
