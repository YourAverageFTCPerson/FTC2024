package org.firstinspires.ftc.teamcode.physicaltools;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class DetermineFileSystem extends LinearOpMode {
    private static final String TAG = DetermineFileSystem.class.getSimpleName();

    @Override
    public void runOpMode() {
        Log.i(TAG, System.getProperties().toString());

        telemetry.addLine(System.getProperties().toString());

        while (!isStopRequested()) {
        }
    }
}
