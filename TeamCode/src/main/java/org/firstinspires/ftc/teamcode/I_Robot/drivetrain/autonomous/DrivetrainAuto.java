package org.firstinspires.ftc.teamcode.I_Robot.drivetrain.autonomous;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.I_Robot.drivetrain.variables.constants.Constants.DISTANCE_UNITS;
import org.firstinspires.ftc.teamcode.I_Robot.drivetrain.variables.constants.Constants.DIRECTION;

/*
 * This is the DrivetrainAuto interface.
 * It defines functions that must be implemented by Autonomous drivetrains.
 * The idea behind interfaces is that the user doesn't have to know the code behind the function,
 *      they just have to understand what the functions do and how to use them properly.
 */
public interface DrivetrainAuto {
    void init(HardwareMap hwMap, String ... motorNames);
    void config(String ... motorDirections);
    void drive(double distance, DISTANCE_UNITS units, DIRECTION direction, double velocity);
    void stop();
}
