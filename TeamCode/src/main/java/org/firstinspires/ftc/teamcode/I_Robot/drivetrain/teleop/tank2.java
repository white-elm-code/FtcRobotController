package org.firstinspires.ftc.teamcode.I_Robot.drivetrain.teleop;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/*
 * This is the tank2 class. It implements the Drivetrain interface and is used for a tank2 drivetrain.
 */
public class tank2 implements Drivetrain {

    public DcMotorEx left_drive;
    public DcMotorEx right_drive;
    private double leftDrivePower, rightDrivePower, motorPower;

    @Override
    public void init(HardwareMap hwMap, String ... motorNames){
        left_drive = hwMap.get(DcMotorEx.class, (motorNames.length > 0) ? motorNames[0] : "left_drive");
        right_drive = hwMap.get(DcMotorEx.class, (motorNames.length > 1) ? motorNames[1] : "right_drive");

        right_drive.setDirection(DcMotorSimple.Direction.REVERSE);

        motorPower = 1;
    }
    @Override
    public void config(String ... motorDirections) {
        left_drive.setDirection( (motorDirections.length > 0 && motorDirections[0].equals("FORWARD")) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE );
        right_drive.setDirection( (motorDirections.length > 1 && motorDirections[1].equals("FORWARD")) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE );
    }
    @Override
    public void drive(double drive, double turn, double strafe) {
        setDrivePowers(drive, turn);
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
    private void setDrivePowers(double leftDrivePower, double rightDrivePower){
        left_drive.setPower(leftDrivePower * motorPower);
        right_drive.setPower(rightDrivePower * motorPower);
    }
}
