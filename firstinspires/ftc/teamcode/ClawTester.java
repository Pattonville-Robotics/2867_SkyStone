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
    private Grabber grabber;
    
    @Override
    public void runOpMode() {
        initialize();

        waitForStart();
        /*
        grabber.CloseClaw();
        sleep(2000);
        grabber.SetWrist(350);
        sleep(5000);
        grabber.SetWrist(-350);
        sleep(5000);
        */
        
        //grabber.SetWrist(10);
        
        // claw.setPosition(1);
        // sleep(2000);
        // grabber.SetWrist(1000);
        // sleep(2000);
        // claw.setPosition(0);
        // sleep(5000);
        /*
        //grabber.PickUpStone();
        grabber.LowerWrist();
        sleep(3300);
        grabber.CloseClaw();
        sleep(1400);
        grabber.RaiseWrist();
        sleep(1300);
        grabber.LowerWrist();
        sleep(2000);
        grabber.RaiseWrist();
        sleep(1300);
        grabber.LowerWrist();
        sleep(2000);
        grabber.OpenClaw();
        sleep(2000);
        // grabber.PlaceStone();
        // sleep(2000);
        */
        
        //drive.moveInches(Direction.FORWARD,14,0.8);

        //common.GoToSkyStone(vuforia, this, drive);

        //drive.moveInches(Direction.FORWARD, 24, 0.8);
        //drive.rotateDegrees(Direction.COUNTERCLOCKWISE, 90, 0.8);
        //drive.moveInches(Direction.FORWARD, 56, 0.8);
        //drive.moveInches(Direction.BACKWARD,12,0.4);
        // drive.rotateDegrees(Direction.CLOCKWISE,90,0.8);
        
    }

    public void initialize() {
        common = new Autonomous_Common(Alliance.RED, this);
        
        common.Initialize();
        
        drive = common.drive;
        claw = common.claw;
        wrist = common.wrist;
        grabber = common.grabber;
    }
}
