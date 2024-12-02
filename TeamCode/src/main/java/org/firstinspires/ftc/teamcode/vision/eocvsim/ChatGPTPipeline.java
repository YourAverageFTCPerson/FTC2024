package org.firstinspires.ftc.teamcode.vision.eocvsim;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class ChatGPTPipeline implements VisionProcessor {
    @Override
    public void init(int width, int height, CameraCalibration calibration) {
//        Log.d(ChatGPTPipeline.class.getSimpleName(), "Hello World!");
//        Logger.getAnonymousLogger().log(Level.INFO, "Hello World!");
    }

    /**
     *
     * @param contours
     * @return {@code new Object[] {MatOfPoint, int}}
     */
    private static Object[] max(List<MatOfPoint> contours) {
        int size;
        switch (size = contours.size()) {
            case 0:
                throw new IllegalArgumentException();
            case 1:
                return new Object[] {contours.get(0), 0};
        }
        MatOfPoint max = contours.get(0);
        MatOfPoint contour;
        int goodIndex = 0;
        double area = Imgproc.contourArea(max), testArea;
        for (int i = 1; i < size; i++) {
            if ((testArea = Imgproc.contourArea(contour = contours.get(i))) > area) {
                max = contour;
                area = testArea;
                goodIndex = i;
            }
        }
        return new Object[] {max, goodIndex};
    }

    private Mat grayed = new Mat();

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        Imgproc.putText(frame, "Hello World!", new Point(0, 100), Imgproc.FONT_HERSHEY_COMPLEX, 1, new Scalar(0, 0, 0), 1, Imgproc.LINE_4, false);
        List<MatOfPoint> contours = new ArrayList<>();
//        Imgproc.cvtColor(contours, this.grayed, Imgproc.COLOR_RGB2GRAY)
        Imgproc.findContours(frame, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        Object[] thing = max(contours);
        MatOfPoint largestContour = (MatOfPoint) thing[0];
        int index = (int) thing[1];
        Imgproc.rectangle(frame, Imgproc.fitEllipseDirect(largestContour).boundingRect(), new Scalar(255, 0, 0));
        Imgproc.drawContours(frame, contours, index, new Scalar(0,255,0), 3);
        return frame;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
}