package org.pattonvillerobotics.commoncode.robotclasses.vuforia;

import android.graphics.Bitmap;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

public class TensorFlowNavigation {

    private LinearOpMode opMode;
    private TFObjectDetector tfod;
    private VuforiaLocalizer vuforia;

    public TensorFlowNavigation (LinearOpMode opMode, VuforiaLocalizer vuforia) {
        this.opMode = opMode;
        this.vuforia = vuforia;

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            opMode.telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
    }

    public void activateTracking() {
        if(tfod != null) tfod.activate();
    }

    public void deactivateTracking() {
        if(tfod != null) tfod.deactivate();
    }

    public List<Recognition> getSkyStones() {
        List<Recognition> skyStones = new ArrayList<Recognition>();

        if (tfod != null) {
           for (Recognition recognition : tfod.getRecognitions()) {
               if (recognition.getLabel() == "Skystone") {
                   skyStones.add(recognition);
               }
           }
        }

        return skyStones;
    }

    public List<Integer> getSkyStonePositions() {
        List<Integer> skyStonePositions = new ArrayList<Integer>();

        if (tfod != null) {
           for (Recognition recognition : tfod.getRecognitions()) {
               if (recognition.getLabel() == "Skystone") {
                   float position = recognition.getLeft();
                   skyStonePositions.add((int)position);
               }
           }
        }

        return skyStonePositions;
    }

    public List<Recognition> getStones() {
        List<Recognition> stones = new ArrayList<Recognition>();

        if (tfod != null) {
           for (Recognition recognition : tfod.getRecognitions()) {
               if (recognition.getLabel() == "Stone") {
                   stones.add(recognition);
               }
           }
        }

        return stones;
    }

    public List<Integer> getStonePositions() {
        List<Integer> stonePositions = new ArrayList<Integer>();

        if (tfod != null) {
           for (Recognition recognition : tfod.getRecognitions()) {
               if (recognition.getLabel() == "Stone") {
                   float position = recognition.getLeft();
                   stonePositions.add((int)position);
               }
           }
        }

        return stonePositions;
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        final String TFOD_MODEL_ASSET = "Skystone.tflite";
        final String LABEL_FIRST_ELEMENT = "Stone";
        final String LABEL_SECOND_ELEMENT = "Skystone";

        int tfodMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);

        // want to be sure it really is what we want
        tfodParameters.minimumConfidence = 0.7;

        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
    
}
