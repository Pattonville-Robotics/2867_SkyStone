package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaParameters;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.TensorFlowNavigation;

@TeleOp(name = "TensorFlowTest")
public class TensorFlowTest extends LinearOpMode {

    private VuforiaNavigation vuforia;
    private TensorFlowNavigation tfod;

    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        vuforia = new VuforiaNavigation(new VuforiaParameters.Builder()
                .cameraMonitorViewId(/*R.id.cameraMonitorViewId*/cameraMonitorViewId)
                .licenseKey("AclLpHb/////AAAAGa41kVT84EtWtYJZW0bIHf9DHg5EHVYWCqExQMx6bbuBtjFeYdvzZLExJiXnT31qDi3WI3QQnOXH8pLZ4cmb39d1w0Oi7aCwy35ODjMvG5qX+e2+3v0l3r1hPpM8P7KPTkRPIl+CGYEBvoNkVbGGjalCW7N9eFDV/T5CN/RQvZjonX/uBPKkEd8ciqK8vWgfy9aPEipAoyr997DDagnMQJ0ajpwKn/SAfaVPA4osBZ5euFf07/3IUnpLEMdMKfoIH6QYLVgwbPuVtUiJWM6flzWaAw5IIhy0XXWwI0nGXrzVjPwZlN3El4Su73ADK36qqOax/pNxD4oYBrlpfYiaFaX0Q+BNro09weXQEoz/Mfgm")
                .build());
                
        tfod = new TensorFlowNavigation(this, vuforia.getLocalizer());
                
        Telemetry.Item skystone = telemetry.addData("Skystones", "0").setRetained(true);
        Telemetry.Item stone = telemetry.addData("Stones", "0").setRetained(true);
        Telemetry.Item skystonePos = telemetry.addData("Skystone Pos", "").setRetained(true);
        Telemetry.Item stonePos = telemetry.addData("Stone Pos", "").setRetained(true);

        vuforia.activateTracking();
        tfod.activateTracking();
        waitForStart();

        while (opModeIsActive()) {

            List<Recognition> skyStones = tfod.getSkyStones();
            skystone.setValue(skyStones.size());
            stone.setValue(tfod.getStones().size());

            List<Integer> skyStonePosList = tfod.getSkyStonePositions();
            if (skyStonePosList.size() >= 1) {
                skystonePos.setValue(skyStonePosList.get(0).toString() + ", " + skyStones.get(0).getConfidence());
            }
            else {
                skystonePos.setValue("");
            }

            List<Integer> stonePosList = tfod.getStonePositions();
            if (stonePosList.size() >= 1) {
               stonePos.setValue(stonePosList.get(0));
            }
            else {
                stonePos.setValue("");
            }

            telemetry.update();
            idle();
        }
    }

}
