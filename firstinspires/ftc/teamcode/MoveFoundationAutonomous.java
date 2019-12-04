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


@Autonomous(name="MoveFoundation")

public class MoveFoundationAutonomous extends LinearOpMode {

    private Autonomous_Common common;
    private MecanumEncoderDrive drive;
    //private VuforiaNavigation vuforia;
    
    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        
        drive.moveInches(Direction.BACKWARD, 28, 1);
        
        sleep(5000);
        common.GrabFoundation();
        
        sleep(500);
        
        drive.moveInches(Direction.FORWARD, 24, 1);
        
        sleep(5000);
        
        common.ReleaseFoundation();
        
        sleep(500);
        
        drive.moveInches(Direction.LEFT, 48, 1);

    }

    public void initialize() {
        common = new Autonomous_Common(Alliance.BLUE, this);
        
        common.Initialize();
        
        drive = common.drive;
    }
}