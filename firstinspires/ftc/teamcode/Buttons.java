package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;


public class Buttons {

    Gamepad gamepad;
    boolean previousLeftBumper = false;
    boolean previousRightBumper = false;
    boolean previousDpadLeft = false;
    boolean previousDpadRight = false;
    boolean previousDpadUp = false;
    boolean previousDpadDown = false;

    public Buttons (Gamepad gamepad)
    {
        this.gamepad = gamepad;
    }

    public boolean LeftBumperJustPressed ()
    {
        boolean currentValue = gamepad.left_bumper;
        boolean pressed = false;
        
        if (currentValue != previousLeftBumper)
        {
            pressed = currentValue;
            previousLeftBumper = currentValue;
        }
    
        return pressed;
    }
    
    public boolean RightBumperJustPressed ()
    {
        boolean currentValue = gamepad.right_bumper;
        boolean pressed = false;
        
        if (currentValue != previousRightBumper)
        {
            pressed = currentValue;
            previousRightBumper = currentValue;
        }
    
        return pressed;
    }
    
    public boolean DpadUpJustPressed ()
    {
        boolean currentValue = gamepad.dpad_up;
        boolean pressed = false;
        
        if (currentValue != previousDpadUp)
        {
            pressed = currentValue;
            previousDpadUp = currentValue;
        }
    
        return pressed;
    }
    
    
}