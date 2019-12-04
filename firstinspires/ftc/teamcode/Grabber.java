package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import java.util.Set;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


public class Grabber {

    Servo claw;
    DcMotor wrist;
    DcMotor slides;
    LinearOpMode opMode;

    public Grabber (Servo claw, DcMotor wrist, DcMotor slides, LinearOpMode opMode)
    {
        this.claw = claw;
        this.wrist = wrist;
        this.slides = slides;
        this.opMode = opMode;
    }

    public void Initialize ()
    {
        wrist.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SetWrist(0);
        wrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        wrist.setPower(0.8);

        slides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SetSlides(0);
        slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slides.setPower(0.8);
    }
    
    public void CloseClaw() {
        claw.setPosition(0.9);
    }
    
    public void OpenClaw() {
        claw.setPosition(0);
    }
    
    public void SetWrist(int value) {
        wrist.setTargetPosition(value);
        wrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        wrist.setPower(0.8);
    }
    public void SetPower(double value) {
        wrist.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        wrist.setPower(value);
    }
    
    public void LowerWrist() {
        SetWrist(-250);
    }
    
    public void ParkWrist() {
        SetWrist(10);
    }
    
    public void RaiseWrist() {
        SetWrist(350);
    }
    
    public void SetSlides(int value) {
        slides.setTargetPosition(value);
    }
    
    public void LowerSlides() {
        SetSlides(0);
    }
    
    public void GoToSlideGrabPosition() {
        SetSlides(300);
    }
    
    public void PickUpStone() {
        LowerWrist();
        opMode.sleep(5000);
        CloseClaw();
        opMode.sleep(1400);
        RaiseWrist();
        opMode.sleep(1300);
    }
    
    public void PlaceStone() {
        LowerWrist();
        opMode.sleep(1300);
        OpenClaw();
        opMode.sleep(1400);
    }
    

}