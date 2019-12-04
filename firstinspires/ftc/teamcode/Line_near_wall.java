package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.enums.Alliance;

@Autonomous(name= "Line_near_wall")

public class Line_near_wall extends LinearOpMode{
    private Autonomous_Common common;
    private MecanumEncoderDrive drive;

    
    @Override
    public void runOpMode() {
        initialize();

        waitForStart();
        
        drive.moveInches(Direction.FORWARD,5,1);
        sleep(2000);
        
    }
    
    public void initialize() {
        common = new Autonomous_Common(Alliance.BLUE, this);
        
        common.Initialize();
        
        
        drive = common.drive;
    }
}