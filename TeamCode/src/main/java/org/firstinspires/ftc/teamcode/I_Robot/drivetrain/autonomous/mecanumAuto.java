package org.firstinspires.ftc.teamcode.I_Robot.drivetrain.autonomous;

import static org.firstinspires.ftc.teamcode.I_Robot.drivetrain.variables.calibrations.Calibrations.TICKS_PER_360_ROTATION;
import static org.firstinspires.ftc.teamcode.I_Robot.drivetrain.variables.calibrations.Calibrations.TICKS_PER_METER_HORIZONTAL;
import static org.firstinspires.ftc.teamcode.I_Robot.drivetrain.variables.calibrations.Calibrations.TICKS_PER_METER_VERTICAL;
import static org.firstinspires.ftc.teamcode.I_Robot.drivetrain.variables.calibrations.Calibrations.TICKS_PER_YARD_HORIZONTAL;
import static org.firstinspires.ftc.teamcode.I_Robot.drivetrain.variables.calibrations.Calibrations.TICKS_PER_YARD_VERTICAL;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.I_Robot.drivetrain.variables.constants.Constants.DISTANCE_UNITS;
import org.firstinspires.ftc.teamcode.I_Robot.drivetrain.variables.constants.Constants.DIRECTION;

/*
 * This is the mecanumAuto class. It implements the DrivetrainAuto interface and is used for a mecanum drivetrain.
 */
public class mecanumAuto implements DrivetrainAuto {

    public DcMotorEx front_left_drive;
    public DcMotorEx front_right_drive;
    public DcMotorEx back_left_drive;
    public DcMotorEx back_right_drive;

    @Override
    public void init(HardwareMap hwMap, String ... motorNames){
        front_left_drive = hwMap.get(DcMotorEx.class, (motorNames.length > 0) ? motorNames[0] : "front_left_drive");
        front_right_drive = hwMap.get(DcMotorEx.class, (motorNames.length > 1) ? motorNames[1] : "front_right_drive");
        back_left_drive = hwMap.get(DcMotorEx.class, (motorNames.length > 2) ? motorNames[2] : "back_left_drive");
        back_right_drive = hwMap.get(DcMotorEx.class, (motorNames.length > 3) ? motorNames[3] : "back_right_drive");
        front_left_drive.setDirection(DcMotorSimple.Direction.REVERSE);
        back_left_drive.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    @Override
    public void config(String ... motorDirections) {
        front_left_drive.setDirection( (motorDirections.length > 0 && motorDirections[0].equals("REVERSE")) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD );
        front_right_drive.setDirection( (motorDirections.length > 1 && motorDirections[1].equals("REVERSE")) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD );
        back_left_drive.setDirection( (motorDirections.length > 2 && motorDirections[2].equals("REVERSE")) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD );
        back_right_drive.setDirection( (motorDirections.length > 3 && motorDirections[3].equals("REVERSE")) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD );
    }
    @Override
    public void drive(double distance, DISTANCE_UNITS units, DIRECTION direction, double velocity) {
        reset();
        switch (direction){
            case FORWARD: forward(distance, units); break;
            case BACKWARD: backward(distance, units); break;
            case ROTATE_LEFT: rotateLeft(distance, units); break;
            case ROTATE_RIGHT: rotateRight(distance, units); break;
            case STRAFE_LEFT: strafeLeft(distance, units); break;
            case STRAFE_RIGHT: strafeRight(distance, units); break;
        }
        runToPosition();
        setVelocity(velocity);
        while(isBusy()){
            // wait for motors to finish
        }
    }
    @Override
    public void stop () {
        front_left_drive.setPower(0);
        back_left_drive.setPower(0);
        front_right_drive.setPower(0);
        back_right_drive.setPower(0);
    }
    /*
     * These are the mecanumAuto class's private helper functions.
     * They help simplify the implementation of the DrivetrainAuto interface by doing a small job well.
     * These helper functions also help prevent duplicate code.
     */
    private void forward (double distance, DISTANCE_UNITS units) {
        int ticks = getVerticalTicks(distance, units);
        front_left_drive.setTargetPosition(-ticks);
        back_left_drive.setTargetPosition(-ticks);
        front_right_drive.setTargetPosition(-ticks);
        back_right_drive.setTargetPosition(-ticks);
    }
    private void backward (double distance, DISTANCE_UNITS units) {
        int ticks = getVerticalTicks(distance, units);
        front_left_drive.setTargetPosition(ticks);
        back_left_drive.setTargetPosition(ticks);
        front_right_drive.setTargetPosition(ticks);
        back_right_drive.setTargetPosition(ticks);
    }
    private void strafeRight (double distance, DISTANCE_UNITS units) {
        int ticks = getHorizontalTicks(distance, units);
        front_left_drive.setTargetPosition(-ticks);
        back_left_drive.setTargetPosition(ticks);
        front_right_drive.setTargetPosition(ticks);
        back_right_drive.setTargetPosition(-ticks);
    }
    private void strafeLeft (double distance, DISTANCE_UNITS units) {
        int ticks = getHorizontalTicks(distance, units);
        front_left_drive.setTargetPosition(ticks);
        back_left_drive.setTargetPosition(-ticks);
        front_right_drive.setTargetPosition(-ticks);
        back_right_drive.setTargetPosition(ticks);
    }
    private void rotateLeft (double degrees, DISTANCE_UNITS units) {
        int ticks = (int) ((TICKS_PER_360_ROTATION * degrees) / 360.0);
        front_left_drive.setTargetPosition(ticks);
        back_left_drive.setTargetPosition(ticks);
        front_right_drive.setTargetPosition(-ticks);
        back_right_drive.setTargetPosition(-ticks);
    }
    private void rotateRight (double degrees, DISTANCE_UNITS units) {
        int ticks = (int) ((TICKS_PER_360_ROTATION * degrees) / 360.0);
        front_left_drive.setTargetPosition(-ticks);
        back_left_drive.setTargetPosition(-ticks);
        front_right_drive.setTargetPosition(ticks);
        back_right_drive.setTargetPosition(ticks);
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
    private int getHorizontalTicks(double distance, DISTANCE_UNITS units) {
        switch (units) {
            case M: case METERS: return (int)(TICKS_PER_METER_HORIZONTAL * distance);
            case CM: case CENTIMETERS: return (int)((TICKS_PER_METER_HORIZONTAL * distance) / 100.0);
            case IN: case INCHES: return (int)((TICKS_PER_YARD_HORIZONTAL * distance) / 36.0);
            case YD: case YARDS: return (int)(TICKS_PER_YARD_HORIZONTAL * distance); 
            case FT: case FEET: return (int)((TICKS_PER_YARD_HORIZONTAL * distance) / 3.0);
            default: return (int)(TICKS_PER_METER_HORIZONTAL * distance);
        }
    }
    private boolean isBusy () {
        return (front_left_drive.isBusy() || back_left_drive.isBusy() 
                || front_right_drive.isBusy() || back_right_drive.isBusy());
    }
    private void runToPosition () {
        front_left_drive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        back_left_drive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        front_right_drive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        back_right_drive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    private void setVelocity (double velocity) {
        front_left_drive.setVelocity(velocity);
        back_left_drive.setVelocity(velocity);
        front_right_drive.setVelocity(velocity);
        back_right_drive.setVelocity(velocity);
    }
    private void reset () {
        front_left_drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        front_right_drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_left_drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_right_drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
