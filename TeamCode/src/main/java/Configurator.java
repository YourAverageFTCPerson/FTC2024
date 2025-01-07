import android.content.Context;

import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import org.firstinspires.ftc.ftccommon.external.OnCreate;

public class Configurator {
    @OnCreate
    public static void onCreate(Context context) {
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(context));
        }
    }
}
