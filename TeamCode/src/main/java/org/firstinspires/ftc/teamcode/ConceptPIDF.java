package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.util.TweakableNumbers;

@Disabled
@TeleOp
public class ConceptPIDF extends LinearOpMode {
    @Override
    public void runOpMode() {
        this.telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        DcMotorEx motor = (DcMotorEx) hardwareMap.get(DcMotor.class, "motor");

        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        PIDController controller = null;//new PIDController();

        Gamepad g = new Gamepad();
        g.circle = true;

        controller.setSetPoint(TweakableNumbers.NUMBERS.getOrDefault(g.toString(), 1000L) / 10d);

        while (!controller.atSetPoint()) {
            double output = controller.calculate(
                    motor.getCurrentPosition()  // the measured value
            );
            motor.setVelocity(output);
        }
        motor.setPower(0.0);
    }
}
