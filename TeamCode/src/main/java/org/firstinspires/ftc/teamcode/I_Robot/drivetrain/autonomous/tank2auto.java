package org.firstinspires.ftc.teamcode.I_Robot.drivetrain.autonomous;

import static org.firstinspires.ftc.teamcode.I_Robot.drivetrain.variables.calibrations.Calibrations.TICKS_PER_360_ROTATION;
import static org.firstinspires.ftc.teamcode.I_Robot.drivetrain.variables.calibrations.Calibrations.TICKS_PER_METER_VERTICAL;
import static org.firstinspires.ftc.teamcode.I_Robot.drivetrain.variables.calibrations.Calibrations.TICKS_PER_YARD_VERTICAL;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.I_Robot.drivetrain.variables.constants.Constants.DISTANCE_UNITS;
import org.firstinspires.ftc.teamcode.I_Robot.drivetrain.variables.constants.Constants.DIRECTION;

/*
 * This is the tank2auto class. It implements the DrivetrainAuto interface and is used for a 2 wheeled tank drivetrain.
 */
public class tank2auto implements DrivetrainAuto {

    public DcMotorEx left_drive;
    public DcMotorEx right_drive;

    @Override
    public void init(HardwareMap hwMap, String ... motorNames){
        left_drive = hwMap.get(DcMotorEx.class, (motorNames.length > 0) ? motorNames[0] : "left_drive");
        right_drive = hwMap.get(DcMotorEx.class, (motorNames.length > 1) ? motorNames[1] : "right_drive");
        left_drive.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    @Override
    public void config(String ... motorDirections) {
        left_drive.setDirection( (motorDirections.length > 0 && motorDirections[0].equals("REVERSE")) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD );
        right_drive.setDirection( (motorDirections.length > 1 && motorDirections[1].equals("REVERSE")) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD );
    }
    @Override
    public void drive(double distance, DISTANCE_UNITS units, DIRECTION direction, double velocity) {
        reset();
        switch (direction){
            case FORWARD: forward(distance, units); break;
            case BACKWARD: backward(distance, units); break;
            case ROTATE_LEFT: rotateLeft(distance, units); break;
            case ROTATE_RIGHT: rotateRight(distance, units); break;
            default: break;
        }
        runToPosition();
        setVelocity(velocity);
        while(isBusy()){
            // wait for motors to finish
        }
    }
    @Override
    public void stop () {
        left_drive.setPower(0);
        right_drive.setPower(0);
    }
    /*
     * These are the tank2auto class's private helper functions.
     * They help simplify the implementation of the DrivetrainAuto interface by doing a small job well.
     * These helper functions also help prevent duplicate code.
     */
    private void forward (double distance, DISTANCE_UNITS units) {
        int ticks = getVerticalTicks(distance, units);
        left_drive.setTargetPosition(-ticks);
        right_drive.setTargetPosition(-ticks);
    }
    private void backward (double distance, DISTANCE_UNITS units) {
        int ticks = getVerticalTicks(distance, units);
        left_drive.setTargetPosition(ticks);
        right_drive.setTargetPosition(ticks);
    }
    private void rotateLeft (double degrees, DISTANCE_UNITS units) {
        int ticks = (int) ((TICKS_PER_360_ROTATION * degrees) / 360.0);
        left_drive.setTargetPosition(ticks);
        right_drive.setTargetPosition(-ticks);
    }
    private void rotateRight (double degrees, DISTANCE_UNITS units) {
        int ticks = (int) ((TICKS_PER_360_ROTATION * degrees) / 360.0);
        left_drive.setTargetPosition(-ticks);
        right_drive.setTargetPosition(ticks);
    }
    // add rounding? (+0.5)
    private int getVerticalTicks(double distance, DISTANCE_UNITS units) {
        switch (units) {
            case M: case METERS: return (int)(TICKS_PER_METER_VERTICAL * distance);
            case CM: case CENTIMETERS: return (int)((TICKS_PER_METER_VERTICAL * distance) / 100.0);
            case IN: case INCHES: return (int)((TICKS_PER_YARD_VERTICAL * distance) / 36.0);
            case YD: case YARDS: return (int)(TICKS_PER_YARD_VERTICAL * distance);
            case FT: case FEET: return (int)((TICKS_PER_YARD_VERTICAL * distance) / 3.0);
            default: return (int)(TICKS_PER_METER_VERTICAL * distance);
        }
    }
    private boolean isBusy () { // waits for all motors to finish moving
        return ( left_drive.isBusy() || right_drive.isBusy() );
    }
    private void runToPosition () {
        left_drive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right_drive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    private void setVelocity (double velocity) {
        left_drive.setVelocity(velocity);
        right_drive.setVelocity(velocity);
    }
    private void reset () {
        left_drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
