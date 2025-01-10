package org.firstinspires.ftc.teamcode;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.teamcode.util.EffectivelyFinal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

/**
 * Sample as in the game sample, not the "Concept..." naming convention.
 */
public class SampleProcessor implements VisionProcessor {
    @EffectivelyFinal
    private Rect LEFT_RECTANGLE, MIDDLE_RECTANGLE, RIGHT_RECTANGLE;

    private Mat hsvMat = new Mat();

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        LEFT_RECTANGLE = new Rect(new Point(0d, 0d/*0.25 * height*/), new Point(width / 3d, height));

        MIDDLE_RECTANGLE = new Rect(new Point(width / 3d, 0d), new Point(2d * width / 3d, height));

        RIGHT_RECTANGLE = new Rect(new Point(2d * width, 0d), new Point(width, height));
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        Imgproc.cvtColor(frame, this.hsvMat, Imgproc.COLOR_RGB2HSV);
        // 0-20, 34-360
        // 50%, 50%

        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
}
