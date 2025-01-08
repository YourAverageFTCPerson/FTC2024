package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

@TeleOp(group = "Illegals")
public class JShellForAndroid extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try (ServerSocket socket = new ServerSocket(90)) {
                Socket s = socket.accept();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
                    
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
