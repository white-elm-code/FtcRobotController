package org.firstinspires.ftc.teamcode.I_Robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.I_Robot.drivetrain.autonomous.DrivetrainAuto;
import org.firstinspires.ftc.teamcode.I_Robot.drivetrain.autonomous.mecanumAuto;
import org.firstinspires.ftc.teamcode.I_Robot.drivetrain.autonomous.tank2auto;
import org.firstinspires.ftc.teamcode.I_Robot.drivetrain.teleop.Drivetrain;
import org.firstinspires.ftc.teamcode.I_Robot.drivetrain.teleop.mecanum;
import org.firstinspires.ftc.teamcode.I_Robot.drivetrain.teleop.tank2;
import org.firstinspires.ftc.teamcode.I_Robot.drivetrain.teleop.tank4;
import org.firstinspires.ftc.teamcode.I_Robot.drivetrain.autonomous.tank4auto;
import org.firstinspires.ftc.teamcode.I_Robot.drivetrain.variables.constants.Constants;

/*
 * The Robot class provides a simple, reusable drivetrain.
 * This helps prevent duplicate code across our different TeleOp and Autonomous programs.
 *
 * @white-elm-code
 */
public class Robot {
    /*
     * These are a Robot's local variables.
     */
    public String name;
    public DrivetrainType drivetrainType;
    public Drivetrain drivetrain;
    public DrivetrainAuto drivetrainAuto;
    public HardwareMap hwMap;

    public enum DrivetrainType { TANK2, TANK4, MECANUM };

    /*
     * This is the Robot constructor.
     *
     * When you initialize a Robot, you provide it with its name, OpMode type, Drivetrain type, and a reference to the current hardware map.
     * String name - the name of the Robot as a String (ex: {@code "Robby"})
     * DrivetrainType drivetrainType - the type of drivetrain the Robot uses (ex: {@code Robot.DrivetrainType.TANK4})
     * HardwareMap hwMap - the current HardwareMap in the Robot's current OpMode
     * Example: Robot robby = new Robot("Robby", Robot.DrivetrainType.TANK4, hardwareMap);
     */
    public Robot(String name, DrivetrainType drivetrainType, HardwareMap hwMap){
        this.name = name;
        this.drivetrainType = drivetrainType;
        this.hwMap = hwMap;
        switch (drivetrainType){
            case TANK2: this.drivetrain = new tank2(); break;
            case TANK4: this.drivetrain = new tank4(); break;
            case MECANUM: this.drivetrain = new mecanum(); break;
        }
        switch (drivetrainType){
            case TANK2: this.drivetrainAuto = new tank2auto(); break;
            case TANK4: this.drivetrainAuto = new tank4auto(); break;
            case MECANUM: this.drivetrainAuto = new mecanumAuto(); break;
        }
    }
    /*
     * The Robot's init function initializes the Robot's Drivetrains.
     *
     * String ... motorNames - this is a variable array of Strings so the drivetrain can initialize the names of the motors with the HardwareMap based on the Robot's configuration on the Driver Station.
     *      The default motor names for a TANK2 drivetrain are "left_drive" and "right_drive".
     *      The default motor names for a TANK4 or MECANUM drivetrain are "front_left_drive", "front_right_drive", "back_left_drive", and "back_right_drive".
     */
    public void init(String ... motorNames){
        this.drivetrain.init(this.hwMap, motorNames);
        this.drivetrainAuto.init(this.hwMap, motorNames);
    }
    /*
     * The Robot's config function configures the direction of rotation for the wheels of the Robot's Drivetrains.
     *
     * String ... motorDirections - this is a variable array of Strings so the drivetrain can set the motors in the right direction.
     *      The order of motors for a TANK2 drivetrain are "left_drive" and "right_drive".
     *      The order of motors for a TANK4 or MECANUM drivetrain are "front_left_drive", "front_right_drive", "back_left_drive", and "back_right_drive".
     */
    public void config(String ... motorDirections){
        this.drivetrain.config(motorDirections);
        this.drivetrainAuto.config(motorDirections);
    }
    /*
     * The Robot's drive function is what allows the Robot to move in a TeleOp program.
     * You must first initialize the drivetrain with the init function.
     * You might have to configure the direction each drivetrain motor spins using the config function.
     * For a TANK2 or TANK4 drivetrain, the drive parameter sets the power of the left wheel(s), the turn parameter sets the power of the right wheel(s), and the strafe value is 0.0.
     * For a MECANUM drivetrain, the power for each wheel is calculated using the drive, turn, and strafe powers. See I_Robot.drivetrain.teleop.mecanum for more information.
     */
    public void drive (double drive, double turn, double strafe) {
        this.drivetrain.drive(drive, turn, strafe);
    }
    /*
     * The Robot's driveAuto function is what allows the Robot to move in a Autonomous program. It can also be used for automated movements in a TeleOp program.
     * You must first initialize the drivetrain with the init function.
     * You might have to configure the direction each drivetrain motor spins using the config function.
     * The function takes a distance, units, direction, and velocity to move the robot [distance] [units] in the [direction] direction with a velocity of [velocity] ticks per second.
     */
    public void driveAuto (double distance, Constants.DISTANCE_UNITS units, Constants.DIRECTION direction, int velocity) {
        this.drivetrainAuto.drive(distance, units, direction, velocity);
    }
}
