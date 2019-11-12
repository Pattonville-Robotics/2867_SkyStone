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

@Autonomous
public class BlueAutonomous extends LinearOpMode {

    private Autonomous_Common common;
    private MecanumEncoderDrive drive;
    private VuforiaNavigation vuforia;
    
    @Override
    public void runOpMode() {
        double y = 0.0;
        initialize();
        vuforia.activateTracking();
        waitForStart();
        
        
//        claw.setPosition(1);
//        sleep(2000);
//        claw.setPosition(0);
//        sleep(2000);
        
        
        
        drive.moveInches(Direction.FORWARD,14,0.8);

        common.GoToSkyStone(vuforia, this, drive);

        drive.moveInches(Direction.FORWARD, 24, 0.8);
        drive.rotateDegrees(Direction.COUNTERCLOCKWISE, 90, 0.8);
        drive.moveInches(Direction.FORWARD, 56, 0.8);
        //drive.moveInches(Direction.BACKWARD,12,0.4);
        // drive.rotateDegrees(Direction.CLOCKWISE,90,0.8);
        
    }

    public void initialize() {
        common = new Autonomous_Common(Alliance.BLUE, this);
        
        common.Initialize();
        
        vuforia = common.vuforia;
        drive = common.drive;
    }
}
