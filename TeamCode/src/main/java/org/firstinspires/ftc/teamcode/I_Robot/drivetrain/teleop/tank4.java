package org.firstinspires.ftc.teamcode.I_Robot.drivetrain.teleop;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/*
 * This is the tank4 class. It implements the Drivetrain interface and is used for a tank4 drivetrain.
 */
public class tank4 implements Drivetrain {

    public DcMotorEx front_left_drive;
    public DcMotorEx front_right_drive;
    public DcMotorEx back_left_drive;
    public DcMotorEx back_right_drive;
    private double leftDrivePower, rightDrivePower, motorPower;

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
        front_left_drive.setDirection( (motorDirections.length > 0 && motorDirections[0].equals("REVERSE")) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD );
        front_right_drive.setDirection( (motorDirections.length > 1 && motorDirections[1].equals("REVERSE")) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD );
        back_left_drive.setDirection( (motorDirections.length > 2 && motorDirections[2].equals("REVERSE")) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD );
        back_right_drive.setDirection( (motorDirections.length > 3 && motorDirections[3].equals("REVERSE")) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD );
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
     * These are the tank4 class's private helper functions.
     * They help simplify the implementation of the Drivetrain interface by doing a small job well.
     * These helper functions also help prevent duplicate code.
     */
    private void setDrivePowers(double leftDrivePower, double rightDrivePower){
        front_left_drive.setPower(leftDrivePower * motorPower);
        back_left_drive.setPower(leftDrivePower * motorPower);
        front_right_drive.setPower(rightDrivePower * motorPower);
        back_right_drive.setPower(rightDrivePower * motorPower);
    }
}
