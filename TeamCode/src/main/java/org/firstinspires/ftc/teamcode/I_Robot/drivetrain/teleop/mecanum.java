package org.firstinspires.ftc.teamcode.I_Robot.drivetrain.teleop;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/*
 * This is the mecanum class. It implements the Drivetrain interface and is used for a mecanum drivetrain.
 */
public class mecanum implements Drivetrain {

    public DcMotorEx front_left_drive;
    public DcMotorEx front_right_drive;
    public DcMotorEx back_left_drive;
    public DcMotorEx back_right_drive;
    private double frontLeftPower, backLeftPower, frontRightPower, backRightPower, motorPower;

    @Override
    public void init(HardwareMap hwMap, String ... motorNames){
        front_left_drive = hwMap.get(DcMotorEx.class, (motorNames.length > 0) ? motorNames[0] : "front_left_drive");
        front_right_drive = hwMap.get(DcMotorEx.class, (motorNames.length > 1) ? motorNames[1] : "front_right_drive");
        back_left_drive = hwMap.get(DcMotorEx.class, (motorNames.length > 2) ? motorNames[2] : "back_left_drive");
        back_right_drive = hwMap.get(DcMotorEx.class, (motorNames.length > 3) ? motorNames[3] : "back_right_drive");
        
        front_left_drive.setDirection(DcMotorSimple.Direction.REVERSE);
        back_left_drive.setDirection(DcMotorSimple.Direction.REVERSE);

        motorPower = 1;
    }
    @Override
    public void config(String ... motorDirections) {
        front_left_drive.setDirection( (motorDirections.length > 0 && motorDirections[0].equals("FORWARD")) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE );
        front_right_drive.setDirection( (motorDirections.length > 1 && motorDirections[1].equals("FORWARD")) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE );
        back_left_drive.setDirection( (motorDirections.length > 2 && motorDirections[2].equals("FORWARD")) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE );
        back_right_drive.setDirection( (motorDirections.length > 3 && motorDirections[3].equals("FORWARD")) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE );
    }
    @Override
    public void drive(double drive, double turn, double strafe) {
        calculateDrivePowers(drive, turn, strafe);
        normalizeDrivePowers();
        setDrivePowers();
    }
    @Override
    public void setMotorPower(double motorPower){
        if(motorPower>=1) this.motorPower = 1;
        else if(motorPower<=0) this.motorPower = 0;
        else this.motorPower = motorPower;
    }
    @Override
    public void stop() {
        drive(0.0, 0.0, 0.0);
    }
    /*
     * These are the mecanum class's private helper functions.
     * They help simplify the implementation of the Drivetrain interface by doing a small job well.
     * These helper functions also help prevent duplicate code.
     */
    private void calculateDrivePowers(double drive, double turn, double strafe){
        if(motorPower>1) motorPower = 1;
        else if(motorPower<0) motorPower = 0;
        frontLeftPower = (drive + strafe + turn) * motorPower;
        backLeftPower = (drive - strafe + turn) * motorPower;
        frontRightPower = (drive - strafe - turn) * motorPower;
        backRightPower = (drive + strafe - turn) * motorPower;
    }
    private void normalizeDrivePowers(){
        double max = Math.max(Math.abs(frontLeftPower), Math.max(Math.abs(backLeftPower), Math.max(Math.abs(frontRightPower), Math.abs(backRightPower))));
        if (max > 1.0) {
            frontLeftPower /= max;
            backLeftPower /= max;
            frontRightPower /= max;
            backRightPower /= max;
        }
    }
    private void setDrivePowers(){
        front_left_drive.setPower(frontLeftPower);
        front_right_drive.setPower(frontRightPower);
        back_left_drive.setPower(backLeftPower);
        back_right_drive.setPower(backRightPower);
    }
}
