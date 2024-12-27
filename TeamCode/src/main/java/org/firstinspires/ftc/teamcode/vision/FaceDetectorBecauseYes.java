package org.firstinspires.ftc.teamcode.vision;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.util.Util;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvTracker;
import org.openftc.easyopencv.OpenCvTrackerApiPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;

/**
 * From the OpenCV Java Tutorial.
 */
@TeleOp(name = "Face Detector Because Yes", group = "Concept")
public class FaceDetectorBecauseYes extends LinearOpMode {
    OpenCvWebcam webcam;
    OpenCvTrackerApiPipeline trackerApiPipeline;
    UselessColorBoxDrawingTracker tracker1, tracker2, tracker3;

    private static final String TAG = FaceDetectorBecauseYes.UselessColorBoxDrawingTracker.class.getSimpleName();

    @Override
    public void runOpMode() {
        telemetry.addData("WARNING", "This program runs in INIT");

        /*
         * Instantiate an OpenCvCamera object for the camera we'll be using.
         * In this sample, we're using a webcam. Note that you will need to
         * make sure you have added the webcam to your configuration file and
         * adjusted the name here to match what you named it in said config file.
         *
         * We pass it the view that we wish to use for camera monitor (on
         * the RC phone). If no camera monitor is desired, use the alternate
         * single-parameter constructor instead (commented out below)
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        trackerApiPipeline = new OpenCvTrackerApiPipeline();
        webcam.setPipeline(trackerApiPipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

        /*
         * Create some trackers we want to run
         */
        tracker1 = new UselessColorBoxDrawingTracker(new Scalar(255, 0, 0));
        tracker2 = new UselessColorBoxDrawingTracker(new Scalar(0, 255, 0));
        tracker3 = new UselessColorBoxDrawingTracker(new Scalar(0, 0, 255));

        /*
         * Add those trackers to the pipeline. All trackers added to the
         * trackerApiPipeline will be run upon receipt of a frame from the
         * camera. Note: the trackerApiPipeline will handle switching
         * the viewport view on tap between the output of each of the trackers
         * for you.
         */
        trackerApiPipeline.addTracker(tracker1);
        trackerApiPipeline.addTracker(tracker2);
        trackerApiPipeline.addTracker(tracker3);

        while (!isStarted()) {
            /*
             * If you later want to stop running a tracker on each frame,
             * you can remove it from the trackerApiPipeline like so:
             */
            //trackerApiPipeline.removeTracker(tracker1);

            sleep(100);
        }
    }

    private class UselessColorBoxDrawingTracker extends OpenCvTracker {
        private final Scalar color;

        private int absoluteFaceSize;

        private final CascadeClassifier faceCascade;

        UselessColorBoxDrawingTracker(Scalar color) {
            this.color = color;
            this.faceCascade = new CascadeClassifier();

            // TODO Actually read the example
            // xml was null because XmlResourceParser.getText() returns the current tag's text

            try (@SuppressLint("ResourceType") InputStream input = hardwareMap.appContext.getResources().openRawResource(R.raw.lbpcascade_frontalface)) {
                File file = new File(hardwareMap.appContext.getDir("cascade", Context.MODE_PRIVATE), "lbpcascade_frontalface.xml");

                try (FileOutputStream output = new FileOutputStream(file)) {
                    byte[] all = Util.readAll(input);
                    System.out.println(new String(all));
                    output.write(all);
                } catch (Exception e) {
                    Log.wtf(TAG, e);
                    return;
                }

                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
                file.deleteOnExit();

                this.faceCascade.load(file.getPath());
            } catch (Exception ignored) {
            }
        }

        private void detectAndDisplay(Mat frame) {
            MatOfRect faces = new MatOfRect();
            Mat grayFrame = new Mat();

            // convert the frame in gray scale
            Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
            // equalize the frame histogram to improve the result
            Imgproc.equalizeHist(grayFrame, grayFrame);

            // compute minimum face size (20% of the frame height, in our case)
            if (this.absoluteFaceSize == 0) {
                int height = grayFrame.rows();
                if (Math.round(height * 0.2f) > 0) {
                    this.absoluteFaceSize = Math.round(height * 0.2f);
                }
            }

            // detect faces
            this.faceCascade.detectMultiScale(grayFrame, faces, 1.1, 2, Objdetect.CASCADE_SCALE_IMAGE,
                    new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());

            // each rectangle in faces is a face: draw them!
            Rect[] facesArray = faces.toArray();
            for (Rect rect : facesArray) {
                Imgproc.rectangle(frame, rect.tl(), rect.br(), this.color, 3);
            }
        }

        @Override
        public Mat processFrame(Mat input) {

//            Imgproc.rectangle(
//                    input,
//                    new Point(
//                            input.cols()/4.0,
//                            input.rows()/4.0),
//                    new Point(
//                            input.cols()*(3f/4f),
//                            input.rows()*(3f/4f)),
//                    color, 4);
            try {
                if (!input.empty()) {
                    detectAndDisplay(input);
                }
            } catch (Throwable tr) {
                telemetry.addData("ERROR", tr.toString());
                Log.wtf(TAG, tr);
            }

            return input;
        }
    }
}
