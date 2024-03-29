package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.robot.Robot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import java.util.Vector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import org.pattonvillerobotics.robotclasses.CustomizedRobotParameters;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.Polar2D;
import org.pattonvillerobotics.commoncode.enums.Alliance;

@TeleOp
public class MainTeleOp_OBJ extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    /*
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor leftRear = null;
    private DcMotor rightRear = null;
    */
    private MecanumEncoderDrive drive;
    private Autonomous_Common common;
    private Buttons buttons1;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
        Polar2D polarCoords;
        /*
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive_motor");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive_motor");
        leftRear  = hardwareMap.get(DcMotor.class, "left_rear_motor");
        rightRear = hardwareMap.get(DcMotor.class, "right_rear_motor");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        leftRear.setDirection(DcMotor.Direction.FORWARD);
        rightRear.setDirection(DcMotor.Direction.REVERSE);
        */
        // Wait for the game to start (driver presses PLAY)
        initialize();
        waitForStart();
        //common.grabber.SetWrist(100);
        common.grabber.OpenClaw();
        runtime.reset();

        while (opModeIsActive()) {
            polarCoords = drive.toPolar(gamepad1.left_stick_x, -gamepad1.left_stick_y);
            telemetry.addData("Polarz", "Polars: (%.2f),(%.2f)", polarCoords.distance, polarCoords.angle).setRetained(false);
            drive.moveFreely(polarCoords.angle,polarCoords.distance,gamepad1.right_stick_x);
            
            if (buttons1.LeftBumperJustPressed())
            {
                telemetry.addLine("Left Bumper Just Pressed");
                // do something
                //common.grabber.PickUpStone();
                common.grabber.CloseClaw();
                
            }
            if (buttons1.RightBumperJustPressed())
            {
                telemetry.addLine("Right Bumper Just Pressed");
                // do something
                //common.grabber.PickUpStone();
                common.grabber.OpenClaw();
                
            }
            if (gamepad1.dpad_up){
                telemetry.addLine("DPAD UP Just Pressed");
                common.grabber.SetPower(0.8);
            }
            if(gamepad1.dpad_down) {
                telemetry.addLine("Just Released");
                common.grabber.SetPower(-0.5);
                sleep(1000);
                common.grabber.SetPower(0);
            }
            // common.slides.setPower(gamepad1.right_trigger-gamepad1.left_trigger);
            telemetry.update();
        }
    }
    
    public void initialize() {
        buttons1 = new Buttons(gamepad1);
        common = new Autonomous_Common(Alliance.BLUE, this);
        common.Initialize();
        
        drive = common.drive;
    }
}
