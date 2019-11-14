package org.pattonvillerobotics.commoncode.robotclasses.drive;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.pattonvillerobotics.commoncode.robotclasses.drive.Polar2D;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.pattonvillerobotics.commoncode.enums.Direction;

import java.util.Locale;

/**
 * Created by greg on 10/2/2017.
 */

public class MecanumNoRunToPositionDrive extends MecanumEncoderDrive {

    boolean leftDone = false;
    boolean rightDone = false;

    public MecanumNoRunToPositionDrive(HardwareMap hardwareMap, LinearOpMode linearOpMode, RobotParameters robotParameters) {
        super(hardwareMap, linearOpMode, robotParameters);
    }

    /**
     * drives a specific number of inches in a given direction
     *
     * @param direction the direction (forward or backward) to drive in
     * @param inches    the number of inches to drive
     * @param power     the power with which to drive
     */
    @Override
    public void moveInches(Direction direction, double inches, double power) {
        //Move Specified Inches Using Motor Encoders

        int targetPositionLeft;
        int targetPositionRight;
        int targetPositionLeftRear;
        int targetPositionRightRear;

        Log.e(TAG, "Getting motor modes");
        storeMotorModes();

        resetMotorEncoders();

        int deltaPosition = (int) Math.round(inchesToTicks(inches));

        switch (direction) {
            case FORWARD: {
                targetPositionLeft = deltaPosition;
                targetPositionRight = deltaPosition;
                targetPositionLeftRear = deltaPosition;
                targetPositionRightRear = deltaPosition;
                break;
            }
            case BACKWARD: {
                targetPositionLeft = -deltaPosition;
                targetPositionRight = -deltaPosition;
                targetPositionLeftRear = -deltaPosition;
                targetPositionRightRear = -deltaPosition;
                break;
            }
            case LEFT: {
                targetPositionLeft = -deltaPosition;
                targetPositionRight = deltaPosition;
                targetPositionLeftRear = deltaPosition;
                targetPositionRightRear = -deltaPosition;
                break;
            }
            case RIGHT: {
                targetPositionLeft = deltaPosition;
                targetPositionRight = -deltaPosition;
                targetPositionLeftRear = -deltaPosition;
                targetPositionRightRear = deltaPosition;
                break;
            }
            default:
                throw new IllegalArgumentException("Direction must be Direction.FORWARDS, Direction.BACKWARDS, Direction.LEFT, or Direction.RIGHT!");
        }

        Log.e(TAG, "Setting motor power high");
        move(Direction.FORWARD, power); // To keep power in [0.0, 1.0]. Encoders control direction

        Log.e(TAG, "Setting target position");
        setMotorTargets(targetPositionLeft, targetPositionRight, targetPositionLeftRear, targetPositionRightRear);

        Log.e(TAG, "Setting motor modes");
        setMotorsMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftDone = false;
        rightDone = false;

        telemetry("Moving " + inches + " inches at power " + power);
        telemetry("LFMotorT: " + targetPositionLeft);
        telemetry("RFMotorT: " + targetPositionRight);
        telemetry("LRMotorT: " + targetPositionLeftRear);
        telemetry("RRMotorT: " + targetPositionRightRear);
        telemetry("EncoderDelta: " + deltaPosition);
        Telemetry.Item distance = telemetry("DistanceL: N/A DistanceR: N/A");
        Telemetry.Item distanceRear = telemetry("DistanceLR: N/A DistanceRR: N/A");

        while (isMovingToPosition()
                || !motorsReachedTarget(targetPositionLeft, targetPositionRight, targetPositionLeftRear, targetPositionRightRear)
                && linearOpMode.opModeIsActive()) {
            distance.setValue(String.format(Locale.getDefault(), "DistanceL: %d DistanceR: %d", leftDriveMotor.getCurrentPosition(), rightDriveMotor.getCurrentPosition()));
            distanceRear.setValue(String.format(Locale.getDefault(), "DistanceLR: %d DistanceRR: %d", leftRearMotor.getCurrentPosition(), rightRearMotor.getCurrentPosition()));
            linearOpMode.telemetry.update();
        }
        Log.e(TAG, "Setting motor power low");
        stop();

        Log.e(TAG, "Restoring motor mode");
        restoreMotorModes();

        sleep(100);
    }

    @Override
    public void rotateDegrees(Direction direction, double degrees, double speed) {
        //Move specified degrees using motor encoders
        //TODO: use the IMU on the REV module for more accurate turns
        int targetPositionLeft;
        int targetPositionRight;
        int targetPositionLeftRear;
        int targetPositionRightRear;

        Log.e(TAG, "Getting motor modes");
        storeMotorModes();

        resetMotorEncoders();

        double inches = degreesToInches(degrees);
        int deltaPosition = (int) Math.round(inchesToTicks(inches));

        switch (direction) {
            case COUNTERCLOCKWISE: {
                targetPositionLeft = deltaPosition;
                targetPositionRight = -deltaPosition;
                targetPositionLeftRear = deltaPosition;
                targetPositionRightRear = -deltaPosition;
                break;
            }
            case CLOCKWISE: {
                targetPositionLeft = -deltaPosition;
                targetPositionRight = deltaPosition;
                targetPositionLeftRear = -deltaPosition;
                targetPositionRightRear = deltaPosition;
                break;
            }
            default:
                throw new IllegalArgumentException("Direction must be Direction.CLOCKWISE or Direction.COUNTERCLOCKWISE!");
        }

        setMotorTargets(targetPositionLeft, targetPositionRight);

        Log.e(TAG, "Setting motor modes");
        setMotorsMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Telemetry.Item[] items = new Telemetry.Item[]{
                telemetry("Rotating " + degrees + " degrees at speed " + speed).setRetained(true),
                telemetry("LFMotorT: " + targetPositionLeft).setRetained(true),
                telemetry("RFMotorT: " + targetPositionRight).setRetained(true),
                telemetry("LRMotorT: " + targetPositionLeftRear).setRetained(true),
                telemetry("RRMotorT: " + targetPositionRightRear).setRetained(true),
                telemetry("EncoderDelta: " + deltaPosition).setRetained(true),
                telemetry("DistanceLF: DistanceRF:").setRetained(true),
                telemetry("DistanceLR: DistanceRR:").setRetained(true)
        };
        Telemetry.Item distance = items[6];
        Telemetry.Item distanceRear = items[7];

        move(Direction.FORWARD, speed); // To keep speed in [0.0, 1.0]. Encoders control direction
        while (isMovingToPosition()
                || !motorsReachedTarget(targetPositionLeft, targetPositionRight, targetPositionLeftRear, targetPositionRightRear)
                && linearOpMode.opModeIsActive()) {
            distance.setValue("DistanceLF: " + leftDriveMotor.getCurrentPosition() + " DistanceRF: " + rightDriveMotor.getCurrentPosition());
            distanceRear.setValue("DistanceLR: " + leftRearMotor.getCurrentPosition() + " DistanceRR: " + rightRearMotor.getCurrentPosition());
            linearOpMode.telemetry.update();
        }
        stop();

        Log.e(TAG, "Restoring motor mode");
        restoreMotorModes();

        for (Telemetry.Item i : items)
            i.setRetained(false);

        sleep(100);
    }

    protected boolean motorsReachedTarget(int targetPositionLeft, int targetPositionRight, int targetPositionLeftRear, int targetPositionRightRear) {
        return reachedTarget(leftDriveMotor.getCurrentPosition(), targetPositionLeft, rightDriveMotor.getCurrentPosition(), targetPositionRight) &&
                reachedTarget(leftRearMotor.getCurrentPosition(), targetPositionLeftRear, rightRearMotor.getCurrentPosition(), targetPositionRightRear);
    }

    protected boolean reachedTarget(int currentPositionLeft, int targetPositionLeft, int currentPositionRight, int targetPositionRight) {
        if ((!leftDone) && Math.abs(currentPositionLeft - targetPositionLeft) < TARGET_REACHED_THRESHOLD)
        {
           leftDone = true;
        }
        if ((!rightDone) && Math.abs(currentPositionRight - targetPositionRight) < TARGET_REACHED_THRESHOLD)
        {
           rightDone = true;
        }

        return leftDone || rightDone;
    }

    public Telemetry.Item telemetry(String message) {
        return this.linearOpMode.telemetry.addData("EncoderDrive", message);
    }
}
