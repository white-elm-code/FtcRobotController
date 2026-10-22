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
    private DcMotorEx shooter;

    // /Users/user_name/Library/Android/sdk/platform-tools/adb connect 192.168.43.1:5555
    @Override
    public void init(){
        robby = new Robot("Robby", Robot.DrivetrainType.MECANUM, hardwareMap);
        robby.init();
        //robby.init("front_left_drive", "front_right_drive", "back_left_drive", "back_right_drive");
        robby.config("FORWARD", "REVERSE", "FORWARD", "REVERSE");
        robby.drivetrain.setMotorPower(0.75);

        intakeMotor1 = hardwareMap.get(DcMotorEx.class, "intakeMotor1");
        intakeMotor2 = hardwareMap.get(DcMotorEx.class, "intakeMotor2");
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
    }

    @Override
    public void loop(){

        if(gamepad2.left_bumper){ intakeMotor1.setPower(1.0); intakeMotor2.setPower(0.0); }
        else if(gamepad2.right_bumper){ intakeMotor1.setPower(-1.0); intakeMotor2.setPower(-1.0); }
        else{ intakeMotor1.setPower(0.0); intakeMotor2.setPower(0.0); }
        robby.drivetrain.drive(-gamepad1.left_stick_y, gamepad1.right_trigger - gamepad1.left_trigger, gamepad1.right_stick_x);
        if(gamepad2.a){ shooter.setPower(1.0);}
        if(gamepad2.b){ shooter.setPower(0.0);}
    }
}
