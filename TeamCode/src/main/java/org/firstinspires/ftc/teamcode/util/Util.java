package org.firstinspires.ftc.teamcode.util;

import java.io.IOException;
import java.io.InputStream;

public class Util {
    protected Util() {
        throw new UnsupportedOperationException();
    }

    public static byte[] readAll(InputStream in) {
        try {
            int length = in.available();
            byte[] b = new byte[length];
            //noinspection ResultOfMethodCallIgnored
            in.read(b);
            return b;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
