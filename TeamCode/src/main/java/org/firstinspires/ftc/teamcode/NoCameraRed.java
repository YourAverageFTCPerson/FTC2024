package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.MathUtils;
import org.firstinspires.ftc.teamcode.util.hardware.BucketActions;
import org.firstinspires.ftc.teamcode.util.hardware.GrabberActions;
import org.firstinspires.ftc.teamcode.util.hardware.RampActions;
import org.firstinspires.ftc.teamcode.util.hardware.ViperActions;

/**
 * Assumptions:
 * <ul>
 *     <li>The robot starts with back to the AprilTag (ID 12 or 15) (just for alignment; no camera shenanigans)</li>
 *     <li>Can also be blue alliance because both sides are symmetrical</li>
 *     <li>The servo on the end of the ramp is called "grabber"</li>
 *     <li>The ramp motor is called "ramp"</li>
 *     <li>The viper motor is called "viperMotor"</li>
 * </ul>
 *
 * <b>YOU HAVE TO WRITE THE CORRECT ENCODER VALUES FOR THE FOLLOWING</b>
 * @see GrabberActions
 * @see RampActions
 * @see BucketActions
 */
@Autonomous
public class NoCameraRed extends LinearOpMode {
    @Override
    public void runOpMode() {
        MecanumDrive drive = new MecanumDrive(this.hardwareMap, new Pose2d(0d, -62d, MathUtils.TAU / 2d));
        waitForStart();
        ViperActions viperActions = new ViperActions(this);
        GrabberActions grabberActions = new GrabberActions(this, "grabber");
        RampActions rampActions = new RampActions(this);
        BucketActions bucketActions = new BucketActions(this);
        Actions.runBlocking(new SequentialAction(drive.actionBuilder(drive.localizer.getPose())
                .turnTo(MathUtils.TAU / 2d - MathUtils.ANGLE_TO_GO_STRAIGHT)
                .lineToX(-52d) // Follow a straight line forward to x=-52 (yaw=ANGLE_TO_GO_STRAIGHT radians from starting orientation)
                .turnTo(MathUtils.TAU / 2d)
                .build(),
                grabberActions.open, grabberActions.close, rampActions.lift,
                drive.actionBuilder(new Pose2d(-52, -26, MathUtils.TAU / 2d))
                        .lineToY(-62d)
                        .turnTo(MathUtils.TAU / 8d) // Back facing basket
                        .build(),
                viperActions.ascend, bucketActions.tilt, bucketActions.right, viperActions.descend,
                rampActions.lower) // Could be moved
        );
    }
}
