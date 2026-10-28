package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.I_Robot.Robot;

@TeleOp
public class Connor_Bot extends OpMode {
    Robot robby;

    private DcMotorEx intakeMotor1;
    private DcMotorEx intakeMotor2;
    private DcMotorEx flywheel;

    // private Servo leftShooterServo;
    // private Servo rightShooterServo;

    private int drivetrainPower;
    private int flywheelVelocity;

    // /Users/user_name/Library/Android/sdk/platform-tools/adb connect 192.168.43.1:5555
    @Override
    public void init(){
        robby = new Robot("Robby", Robot.DrivetrainType.MECANUM, hardwareMap);
        robby.init();
        //robby.init("front_left_drive", "front_right_drive", "back_left_drive", "back_right_drive");
        robby.config("FORWARD", "REVERSE", "FORWARD", "REVERSE");
        robby.drivetrain.setMotorPower(1.0);

        intakeMotor1 = hardwareMap.get(DcMotorEx.class, "intake_motor_1"); // powers the front intake and the vertical intake
        intakeMotor2 = hardwareMap.get(DcMotorEx.class, "intake_motor_2"); // powers the inner flapper thingy

        // leftShooterServo = hardwareMap.get(Servo.class, "left_shooter_servo"); // powers the left shooter positioning servo
        // rightShooterServo = hardwareMap.get(Servo.class, "right_shooter_servo"); // powers the right shooter positioning servo

        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        flywheel.setDirection(DcMotorEx.Direction.REVERSE);
        flywheel.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        flywheelVelocity = 100; // tps

        telemetry.addLine("HTTPS 200 OK");
        telemetry.update();
    }

    @Override
    public void loop(){

        // gamepad1 controls the driving
        // gamepad2 controls the shooting and intake

        /*
        // adjust flywheel positioning
        if(gamepad2.a){
            leftShooterServo.setPosition(?.?); // set for pollen
            rightShooterServo.setPosition(?.?);
        }else if(gamepad2.b){
            leftShooterServo.setPosition(?.?); // set for nectar
            rightShooterServo.setPosition(?.?);
        }
        */

        // adjust flywheel velocity (gamepad2)
        if(gamepad2.dpadUpWasPressed()) flywheelVelocity += 100;
        else if(gamepad2.dpadDownWasPressed() && flywheelVelocity >= 100) flywheelVelocity -= 100;
        else if(gamepad2.dpadRightWasPressed()) flywheelVelocity += 10;
        else if(gamepad2.dpadLeftWasPressed() && flywheelVelocity >= 10) flywheelVelocity -= 10;

        // flywheel controls (gamepad2)
        if(gamepad2.left_bumper) flywheel.setVelocity(flywheelVelocity); // if gamepad2's left bumper is pressed, set the flywheel's velocity in ticks per second
        else flywheel.setVelocity(0.0); // otherwise, stop the flywheel
        // if this doesn't work
        // * change the encoder wires
        // * change the motor port
        // * change the motor
        // * change the control hub

        // intake controls (gamepad2)
        if(gamepad2.left_trigger > 0.05){ // if gamepad2's left trigger is pressed, spit out the nectar/pollen
            intakeMotor1.setPower(gamepad2.left_trigger);
            intakeMotor2.setPower(0.0); // stop the inner intake from destroying itself
        }else if(gamepad2.right_trigger > 0.05){ // if gamepad2's right trigger is pressed, eat the nectar/pollen
            intakeMotor1.setPower(-gamepad2.right_trigger);
            intakeMotor2.setPower(-gamepad2.right_trigger);
        }else{ // otherwise, stop the intake
            intakeMotor1.setPower(0.0);
            intakeMotor2.setPower(0.0);
        }

        // adjust drivetrain velocity (gamepad1)
        if(gamepad1.dpadUpWasPressed()) robby.drivetrain.setMotorPower(robby.drivetrain.getMotorPower() + 0.1);
        else if(gamepad1.dpadDownWasPressed()) robby.drivetrain.setMotorPower(robby.drivetrain.getMotorPower() - 0.1);
        else if(gamepad1.dpadRightWasPressed()) robby.drivetrain.setMotorPower(robby.drivetrain.getMotorPower() + 0.01);
        else if(gamepad1.dpadLeftWasPressed()) robby.drivetrain.setMotorPower(robby.drivetrain.getMotorPower() - 0.01);

        // drivetrain controls (gamepad1)
        robby.drivetrain.drive(-gamepad1.left_stick_y, gamepad1.right_trigger - gamepad1.left_trigger, gamepad1.right_stick_x);

        // telemetry
        telemetry.addLine("ConnorBot Data");
        telemetry.addData("Drive Power", robby.drivetrain.getMotorPower() * 100);
        telemetry.addData("FlyV Target", flywheelVelocity);
        telemetry.addData("FlyV Real", flywheel.getVelocity());
        telemetry.update();
    }
}
