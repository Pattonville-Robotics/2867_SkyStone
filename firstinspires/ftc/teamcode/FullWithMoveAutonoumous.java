package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaParameters;
import org.pattonvillerobotics.commoncode.enums.Alliance;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.pattonvillerobotics.robotclasses.CustomizedRobotParameters;

@Autonomous(name="WithFoundationMove")

public class FullWithMoveAutonoumous extends LinearOpMode {

    private Autonomous_Common common;
    private MecanumEncoderDrive drive;
    private VuforiaNavigation vuforia;
    
    @Override
    public void runOpMode() {
        initialize();
        vuforia.activateTracking();
        waitForStart();
        
        double y = 0.0;
        
        drive.moveInches(Direction.FORWARD,10,1);
        common.GetSkyStoneLocation(this.vuforia, this);
        sleep(2000);
        
        y = this.vuforia.getRobotY();
        
        drive.moveInches(Direction.RIGHT, y, 1);
        
        this.common.grabber.OpenClaw();
        
        drive.moveInches(Direction.FORWARD, 15, 1);
        
        sleep(2000);
        
        this.common.grabber.CloseClaw();
        
        this.common.grabber.SetPower(0.8);
        
        drive.moveInches(Direction.BACKWARD, 10, 1);
        
        this.common.grabber.SetPower(-3);
        
        drive.moveInches(Direction.LEFT, (22+y), 1);
        
        drive.rotateDegrees(Direction.COUNTERCLOCKWISE, 90, 0.8);
        
        // Cutoff Point
        
        drive.moveInches(Direction.FORWARD, 22, 1);
        
        drive.moveInches(Direction.LEFT, 10,1);
        
        this.common.grabber.SetPower(0.8);
         
        drive.moveInches(Direction.FORWARD, 25, 1);
        
        this.common.grabber.SetPower(-0.3);
        
        this.common.grabber.OpenClaw();
        
        drive.moveInches(Direction.BACKWARD, 25, 1);
        
        drive.moveInches(Direction.RIGHT, 10, 1);
        
        drive.moveInches(Direction.BACKWARD, 8, 1);
        
        //PARK

    }

    public void initialize() {
        common = new Autonomous_Common(Alliance.BLUE, this);
        
        common.Initialize();
        
        vuforia = common.vuforia;
        drive = common.drive;
    }
}