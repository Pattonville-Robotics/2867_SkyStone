package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaParameters;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumNoRunToPositionDrive;
import org.pattonvillerobotics.commoncode.enums.Alliance;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.robotclasses.CustomizedRobotParameters;

public class Autonomous_Common {

    final double SlowSpeed = 0.4;
    final double NormalSpeed = 0.8;

    Alliance alliance;
    public LinearOpMode opMode;
    public MecanumEncoderDrive drive;
    public Servo claw;
    public DcMotor slides;
    public DcMotor wrist;
    public Grabber grabber;
    public VuforiaNavigation vuforia;

    public Autonomous_Common(Alliance alliance, LinearOpMode opMode) {
        this.alliance = alliance;
        this.opMode = opMode;
    }

    // this version is for teleop
    public Autonomous_Common(LinearOpMode opMode) {
        // alliance color doesn't matter for teleop, so just pick one        
        this(Alliance.BLUE, opMode);
    }

    public void Initialize () {
        drive = new MecanumNoRunToPositionDrive(opMode.hardwareMap, opMode, CustomizedRobotParameters.getRobotParameters(opMode));
        claw = opMode.hardwareMap.servo.get("claw");
        wrist = opMode.hardwareMap.dcMotor.get("wrist");
        slides = opMode.hardwareMap.dcMotor.get("slides");
        grabber = new Grabber (claw, wrist, slides, opMode);
        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
        vuforia = new VuforiaNavigation(new VuforiaParameters.Builder()
                .cameraMonitorViewId(/*R.id.cameraMonitorViewId*/cameraMonitorViewId)
                .licenseKey("AclLpHb/////AAAAGa41kVT84EtWtYJZW0bIHf9DHg5EHVYWCqExQMx6bbuBtjFeYdvzZLExJiXnT31qDi3WI3QQnOXH8pLZ4cmb39d1w0Oi7aCwy35ODjMvG5qX+e2+3v0l3r1hPpM8P7KPTkRPIl+CGYEBvoNkVbGGjalCW7N9eFDV/T5CN/RQvZjonX/uBPKkEd8ciqK8vWgfy9aPEipAoyr997DDagnMQJ0ajpwKn/SAfaVPA4osBZ5euFf07/3IUnpLEMdMKfoIH6QYLVgwbPuVtUiJWM6flzWaAw5IIhy0XXWwI0nGXrzVjPwZlN3El4Su73ADK36qqOax/pNxD4oYBrlpfYiaFaX0Q+BNro09weXQEoz/Mfgm")
                .build());
                opMode.idle();

        grabber.Initialize();
    }

    public void GoToFirstStone(LinearOpMode opMode, MecanumEncoderDrive drive) {

        Direction direction;
        
        if (alliance == Alliance.RED) {
            direction = Direction.LEFT;
        }
        else
        {
            direction = Direction.RIGHT;
        }

        // line up with the stone
        drive.moveInches(direction, 7, NormalSpeed);

        drive.moveInches(Direction.FORWARD, 14, NormalSpeed);
    }

    public void GoToSkyStone(VuforiaNavigation vuforia, LinearOpMode opMode, MecanumEncoderDrive drive) {

        double x = 14.0;  // if not found, move forward 14 inches
        double y = 0.0;
        int loops = 0;
        Direction direction;
        
        if (alliance == Alliance.RED) {
            direction = Direction.LEFT;
        }
        else
        {
            direction = Direction.RIGHT;
        }

        // try up to 3 times
        while (loops < 3) {
            if (GetSkyStoneLocation(vuforia, opMode)) {
                //found it
                x = vuforia.getRobotX();
                y = vuforia.getRobotY();
                loops = 3;
            }
            else
            {
                // move towards next stone (8 inches wide)
                drive.moveInches(Direction.FORWARD, 8, NormalSpeed);
                ++loops;
            }
        }        

        // line up with the skystone
        drive.moveInches(Direction.RIGHT, y + 7, NormalSpeed);
        drive.moveInches(Direction.FORWARD, x, NormalSpeed);
    }

    public boolean GetSkyStoneLocation(VuforiaNavigation vuforia, LinearOpMode opMode) {
        // returns true if it can see the SkyStone. Position is 
        // at vuforia.GetRobotX(), vuforia.GetRobotY()
        
        boolean found = false;
        ElapsedTime delay_timer = new ElapsedTime();
        
        Telemetry.Item vuforiaY = opMode.telemetry.addData("Robot Y", "6").setRetained(true);
        
        while (opMode.opModeIsActive() && vuforia.getRobotY() == 0.0 && delay_timer.seconds()< 2) {
            if (vuforia.getVisibleTrackableLocation() != null) {
                vuforiaY.setValue(vuforia.getRobotY());
             
                opMode.telemetry.update();
                
                found = true;
            }
        }
        
        return found;
    }

}