package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous 
public class AutoWithTime extends LinearOpMode {
  @Override
  public void runOpMode throws interruptedException {
    DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
    DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
    DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
    DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
    DcMotor viperMotor = hardwareMap.dcMotor.get("viperMotor");
    Servo rampServo = hardwareMap.servo.get("rampServo");
    IMU imu = hardwareMap.get(IMU.class, "imu");
    int viperMotorTarget;
    final int fullExtension = (int) Math.round(REVOLUTIONS_FOR_FULL_EXTENSION * (USING_223 ? YELLOW_JACKET_223_PPR : YELLOW_JACKET_312_PPR)) - 500;
    
    waitForStart(); 

    while (opModeIsActive) {
      // to not touch the wall, right side is facing the wall
      frontLeftMotor.setPower(-0.2); 
      backLeftMotor.setPower(0.2);
      backRightMotor.setPower(-0.2);
      frontRightMotor.setPower(0.2);
      sleep(200);
      frontLeftMotor.setPower(0); 
      backLeftMotor.setPower(0);
      backRightMotor.setPower(0);
      frontRightMotor.setPower(0);

      frontLeftMotor.setPower(-0.2); 
      backLeftMotor.setPower(-0.2);
      backRightMotor.setPower(-0.2);
      frontRightMotor.setPower(-0.2);
      sleep(3500);
      frontLeftMotor.setPower(0); 
      backLeftMotor.setPower(0);
      backRightMotor.setPower(0);
      frontRightMotor.setPower(0);

     upordown = 0;
     viperMotor.setTargetPosition(viperTargetPosition = fullExtension);
     while (upordown == 0) {
        viperMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION); 
        if (viperMotor.getCurrentPosition() - 20 > targetPosition && upordown == 0) {
          this.motor.setPower(0.0); // autiiiiiiism
        }  else {
             this.motor.setPower(1);
        }
     }

      rampServo.setPosition(123); 

     viperMotor.setTargetPosition(viperTargetPosition = 0); 
     upordown = 2; 
     while (upordown == 2) {
       viperMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION); 
       if (motor.getCurrentPosition() - 75 <= targetPosition && upordown == 1) {
         this.motor.setPower(0.0);
           upordown = 2;
       } else {
                 this.motor.setPower(-1);
       }
     }

      frontLeftMotor.setPower(0.5); 
      backLeftMotor.setPower(0.5);
      backRightMotor.setPower(0.5);
      frontRightMotor.setPower(0.5);
      sleep(3000);
      frontLeftMotor.setPower(0); 
      backLeftMotor.setPower(0);
      backRightMotor.setPower(0);
      frontRightMotor.setPower(0);

    }
  }
}
