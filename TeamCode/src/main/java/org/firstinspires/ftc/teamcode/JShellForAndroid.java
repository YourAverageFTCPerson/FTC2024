package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.hardware.MecanumHardware;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

@TeleOp(group = "Illegals")
public class JShellForAndroid extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumHardware mecanum = new MecanumHardware(this);

        Thread thread = new Thread(() -> {
            try (ServerSocket socket = new ServerSocket(90)) {
                Socket s = socket.accept();
                String line;
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
                    while ((line = reader.readLine()) != null) {
                        String[] data = line.split(",");
                        double y = Double.parseDouble(data[0]);
                        double x = Double.parseDouble(data[1]);
                        double rx = Double.parseDouble(data[2]);
                        mecanum.driveWithPower(y, x, rx);
                        System.out.println(line);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        thread.start();

        thread.join();

        while (!isStopRequested()) {
            sleep(10L);
        }
    }
}