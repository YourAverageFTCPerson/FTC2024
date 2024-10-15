package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Concept Tank Drive")
public class ConceptTankDrive extends LinearOpMode {
    @Override
    public void runOpMode() {
        DcMotor frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        DcMotor frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        DcMotor backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        DcMotor backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE); // Change later
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        double leftPower, rightPower;
        while (opModeIsActive()) {
            frontLeftMotor.setPower(leftPower = gamepad1.left_stick_y + gamepad1.left_stick_x);
            frontRightMotor.setPower(rightPower = gamepad1.left_stick_y - gamepad1.left_stick_x);
            backLeftMotor.setPower(leftPower);
            backRightMotor.setPower(rightPower);
        }
    }
}
