package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.enums.Direction;

public class Autonomous_Common {
    double x = 0.0;
    public void FindSkyStone(VuforiaNavigation vuforia, LinearOpMode opMode, MecanumEncoderDrive drive) {
        double y = 0.0;
        ElapsedTime delay_timer = new ElapsedTime();
    Telemetry.Item vuforiaX = opMode.telemetry.addData("Robot Y", "6").setRetained(true);
        
        while (opMode.opModeIsActive() && vuforia.getRobotY() == 0.0 && delay_timer.seconds()< 4) {
            vuforia.getVisibleTrackableLocation();
            
            y = vuforia.getRobotY();
            x = vuforia.getRobotX();
            //  vuforiaX.setValue(vuforia.getRobotX());
             
            opMode.telemetry.update();
            
        }
        drive.moveInches(Direction.RIGHT, y + 7, 0.8);
        
        
        
    }
    public void PickUpSkyStone(Servo claw, LinearOpMode opMode, MecanumEncoderDrive drive) {
      claw.setPosition(1);  
      drive.moveInches(Direction.FORWARD, x, 0.8);
        
        
        
    }
}