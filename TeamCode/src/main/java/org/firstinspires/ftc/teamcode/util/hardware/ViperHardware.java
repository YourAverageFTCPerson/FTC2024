package org.firstinspires.ftc.teamcode.util.hardware;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Objects;

public class ViperHardware {
    private static final String TAG = ViperHardware.class.getSimpleName();

    public static final float YELLOW_JACKET_312_PPR = 537.7f;

    public static final double REVOLUTIONS_FOR_FULL_EXTENSION = 8.1;

    public static final int FULL_EXTENSION = (int) Math.floor(REVOLUTIONS_FOR_FULL_EXTENSION * YELLOW_JACKET_312_PPR);

    private final OpMode opMode;

    private DcMotor viper;

    private boolean notInitialized = true;

    public ViperHardware(OpMode opMode) {
        this.opMode = Objects.requireNonNull(opMode);
    }

    public void initialize() {
        Log.d(TAG, "Entering initialize()");

        if (!notInitialized) {
            throw new IllegalStateException("Already initialized");
        }

        this.viper = opMode.hardwareMap.get(DcMotor.class, "viperMotor");
        this.viper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.viper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        this.notInitialized = false;

        Log.d(TAG, "Exiting initialize()");
    }

    public void extend() {
        extend(null);
    }

    public void extend(Runnable onFinished) {
        Log.d(TAG, "Entering extend()");

        if (this.notInitialized) {
            throw new IllegalStateException();
        }

        if (this.viper.isBusy()) {
            Log.d(TAG, "extend(): already busy--exiting");
            Log.d(TAG, "Exiting extend()");
            return;
        }

        this.viper.setTargetPosition(FULL_EXTENSION);
        this.viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        new Thread(() -> {
            if (onFinished == null) {
                return;
            }
            while (this.viper.isBusy()) {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    throw new AssertionError(e);
                }
            }
            onFinished.run();
        }).start();

        Log.d(TAG, "Exiting extend()");
    }

    public void retract() {
        retract(null);
    }

    public void retract(Runnable onFinished) {
        Log.d(TAG, "Entering retract()");

        if (this.notInitialized) {
            throw new IllegalStateException();
        }

        if (this.viper.isBusy()) {
            Log.d(TAG, "retract(): already busy--exiting");
            Log.d(TAG, "Exiting retract()");
            return;
        }

        this.viper.setTargetPosition(0);
        this.viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        new Thread(() -> {
            if (onFinished == null) {
                return;
            }
            while (this.viper.isBusy()) {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    throw new AssertionError(e);
                }
            }
            onFinished.run();
        }).start();

        Log.d(TAG, "Exiting retract()");
    }
}
