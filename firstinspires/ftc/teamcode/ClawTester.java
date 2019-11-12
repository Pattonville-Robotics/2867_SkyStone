package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.enums.Alliance;
import org.pattonvillerobotics.robotclasses.CustomizedRobotParameters;



@Autonomous

public class ClawTester extends LinearOpMode {

    private Autonomous_Common common;
    private MecanumEncoderDrive drive;
    private Servo claw;
    private DcMotor wrist;
    
    @Override
    public void runOpMode() {
        initialize();
        //vuforia.activateTracking();
        waitForStart();
        //common.RaiseWrist(wrist);
        
        claw.setPosition(1);
        sleep(2000);
        claw.setPosition(0);
        sleep(2000);
        
        
        
        //drive.moveInches(Direction.FORWARD,14,0.8);

        //common.GoToSkyStone(vuforia, this, drive);

        //drive.moveInches(Direction.FORWARD, 24, 0.8);
        //drive.rotateDegrees(Direction.COUNTERCLOCKWISE, 90, 0.8);
        //drive.moveInches(Direction.FORWARD, 56, 0.8);
        //drive.moveInches(Direction.BACKWARD,12,0.4);
        // drive.rotateDegrees(Direction.CLOCKWISE,90,0.8);
        
    }

    public void initialize() {
        common = new Autonomous_Common(Alliance.RED);
        drive = new MecanumEncoderDrive(hardwareMap,this,CustomizedRobotParameters.ROBOT_PARAMETERS);
        claw = hardwareMap.servo.get("claw");
        wrist = hardwareMap.dcMotor.get("wrist");
        
    }
}
