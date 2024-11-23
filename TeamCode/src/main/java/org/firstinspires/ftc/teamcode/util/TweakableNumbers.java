package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * This is a way to tweak values without recompiling and wasting time. It consists of a
 * {@code HashMap} which holds {@code toString()}s of {@code gamepad1} with the substring starting
 * from index 0 and ending at index {@link TweakableNumbers#LENGTH_OF_OMISSION} (exclusive) removed.
 * {@snippet :
 * import org.firstinspires.ftc.team24388.util.TweakableNumbers;
 * ...
 * // Get the value of the state with only dpad_up of gamepad1 down.
 * Long temp; // We use temp for a null check.
 * long someRandomValue = (temp = TweakableNumbers.NUMBERS.get("dpad_up ")) == null ? 0L : temp;
 * doSomething(someRandomValue);
 * ...
 * }
 */
@TeleOp(name = "Tweakable Numbers", group = "Utilities")
public class TweakableNumbers extends LinearOpMode {
    public static final HashMap<String, Long> NUMBERS = new HashMap<>();

    /**
     * The length that is stripped from the beginning of {@code toString()}s of {@code gamepad1}
     * for keys of {@link TweakableNumbers#NUMBERS}. This assumes that the length of ID is always 4
     * digits. (Which it probably is.) The reason why the beginning is always stripped off by this
     * length is because it contains unpredictable and redundant values there (i.e. joystick
     * position, gamepad ID and user number).
     */
    private static final int LENGTH_OF_OMISSION = "ID: 1000 user:  1 lx:  0.00 ly:  0.00 rx:  0.00 ry:  0.00 lt: 0.00 rt: 0.00 ".length();

    private static final String NUMBER_FIELD_NAME;

    static {
        String temp;
        try {
            Field field = TweakableNumbers.class.getField("NUMBERS");
            // No need for Class.getTypeName() because TweakableNumbers.class is never an array.
            temp = field.getDeclaringClass().getName() + '.' + field.getName();
        } catch (NoSuchFieldException e) {
            // If we can't find the field, whatever. It really doesn't matter.
            // Note that this should not happen unless I renamed NUMBERS.
            temp = TweakableNumbers.class.getName() + ".NUMBERS";
        }
        NUMBER_FIELD_NAME = temp;

        // Maybe set some numbers here in advance? (Default values)
    }

    @Override
    public void runOpMode() {
        // Over-engineered displaying of numbers.
        telemetry.addData(NUMBER_FIELD_NAME, NUMBERS);
        telemetry.addLine("Please start the program to set values.");
        telemetry.update();

        waitForStart();

        String key = null;
        StringBuilder builder = new StringBuilder();

        Gamepad previousGamepad1 = new Gamepad();
        Gamepad currentGamepad1 = new Gamepad();

        boolean nowSettingValue = false;

        for (; ; ) {
            telemetry.addData("builder", "\"%s\"", builder);
            telemetry.addData("nowSettingValue", nowSettingValue);
            telemetry.addLine("dpad_up to exit");
            telemetry.update();

            previousGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);

            if (currentGamepad1.dpad_up && !previousGamepad1.dpad_up) {
                break;
            }

            if (currentGamepad1.left_bumper && !previousGamepad1.left_bumper) {
                // We use base ten because 0.1 is an infinite string in binary. (Binary was previously used)
                if (nowSettingValue) {
                    if (currentGamepad1.dpad_left) {
                        builder.append(0);
                    } else if (currentGamepad1.dpad_right) {
                        builder.append(1);
                    } else if (currentGamepad1.dpad_down) {
                        builder.append(2);
                    } else if (currentGamepad1.x) {
                        builder.append(3);
                    } else if (currentGamepad1.y) {
                        builder.append(4);
                    } else if (currentGamepad1.a) {
                        builder.append(5);
                    } else if (currentGamepad1.b) {
                        builder.append(6);
                    } else if (currentGamepad1.left_stick_button) {
                        builder.append(7);
                    } else if (currentGamepad1.right_stick_button) {
                        builder.append(8);
                    } else if (currentGamepad1.right_bumper) {
                        builder.append(9);
                    }
                } else {
                    // The first part of the first omission is omitted because "ID" changes when
                    // gamepad1 is taken out and plugged back in. "user" is always 1 because we
                    // read from gamepad1. The numbers after that are subject to change and cannot
                    // be the same every time. "left_bumper " is removed because we use it to get
                    // the input and is therefore always true.
                    key = currentGamepad1.toString().substring(LENGTH_OF_OMISSION).replace("left_bumper ", "");
                    nowSettingValue = true;
                }
            }
        }
        if (key != null) {
            NUMBERS.put(key, Long.parseLong(builder.toString()));
        }

        telemetry.addData(NUMBER_FIELD_NAME, NUMBERS);
        telemetry.update();

        while (opModeIsActive()) { // Satisfy IntelliJ with non-empty loop.
            sleep(50L); // Don't destroy the CPU
        }
    }
}