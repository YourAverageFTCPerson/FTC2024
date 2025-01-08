package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.hardware.ViperHardware;

@Autonomous
public class MainAuto extends LinearOpMode {
    public class Viper {
        private final ViperHardware hardware = new ViperHardware(MainAuto.this);

        private boolean notInitialized = true;

        public final Action ascend = t -> {
            if (notInitialized) {
                hardware.initialize();
                notInitialized = false;
            }
            hardware.extend();
            return false;
        };

        public final Action descend = t -> {
            if (notInitialized) {
                hardware.initialize();
                notInitialized = false;
            }
            hardware.retract();
            return false;
        };
    }

    @Override
    public void runOpMode() {
        this.telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        MecanumDrive drive = new MecanumDrive(this.hardwareMap, new Pose2d(0d, 0d, 0d));

        TrajectoryActionBuilder trajectories = drive.actionBuilder(drive.localizer.getPose()).lineToX(50d);

        waitForStart();

        Viper viper = new Viper();

        Actions.runBlocking(new SequentialAction(trajectories.build(), viper.ascend, viper.descend));
    }
}
