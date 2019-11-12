package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.enums.Alliance;
import org.pattonvillerobotics.commoncode.enums.Direction;

public class Autonomous_Common {

    final double SlowSpeed = 0.4;
    final double NormalSpeed = 0.8;

    Alliance alliance;

    public Autonomous_Common(Alliance alliance) {
        this.alliance = alliance;
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
                drive.moveInches(direction, 8, NormalSpeed);
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

    public void PickUpSkyStone(Servo claw, DcMotor wrist, LinearOpMode opMode, MecanumEncoderDrive drive) {
 
        claw.setPosition(1);  
        RaiseWrist(wrist);
    }

    public void RaiseWrist(DcMotor wrist) {
        wrist.setTargetPosition(200);
        wrist.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        wrist.setPower(NormalSpeed);
   }
    
}