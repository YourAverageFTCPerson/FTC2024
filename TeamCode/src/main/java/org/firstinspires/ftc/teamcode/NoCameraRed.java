package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.MathUtils;
import org.firstinspires.ftc.teamcode.util.hardware.GrabberActions;
import org.firstinspires.ftc.teamcode.util.hardware.ViperActions;

@Disabled
@Autonomous
public class NoCameraRed extends LinearOpMode {
    /**
     * Straight to alliance neutral samples
     */
    private static final double ANGLE_TO_GO_STRAIGHT = Math.PI - 0.605544663605; // arcsin(36/63.2455532034)

    @Override
    public void runOpMode() {
        MecanumDrive drive = new MecanumDrive(this.hardwareMap, new Pose2d(0d, -62d, 0d));
        waitForStart();
        ViperActions viperActions = new ViperActions(this);
        GrabberActions grabberActions = new GrabberActions(this, "grabber");
        Actions.runBlocking(new SequentialAction(drive.actionBuilder(drive.localizer.getPose()).turnTo(ANGLE_TO_GO_STRAIGHT)
                .lineToX(-52d)
                .turnTo(0d).build(),
                grabberActions.open,
                grabberActions.close,
                drive.actionBuilder(drive.localizer.getPose())
                .lineToY(-62d).turnTo(-MathUtils.TAU / 8d)
                .build(),
                viperActions.ascend,
                viperActions.descend)
        );
    }
}
