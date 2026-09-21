package org.firstinspires.ftc.teamcode.I_Robot.drivetrain.teleop;

import com.qualcomm.robotcore.hardware.HardwareMap;

/*
 * This is the Drivetrain interface.
 * It defines functions that must be implemented by TeleOp drivetrains.
 * The idea behind interfaces is that the user doesn't have to know the code behind the function,
 *      they just have to understand what the functions do and how to use them properly.
 */
public interface Drivetrain {
    void init(HardwareMap hwMap, String ... motorNames);
    void config(String ... motorDirections);
    void drive(double drive, double turn, double strafe);
    void setMotorPower(double motorPower);
    void stop();
}
