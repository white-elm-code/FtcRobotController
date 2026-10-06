package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;
import org.firstinspires.ftc.teamcode.I_Robot.Robot;

@TeleOp
public class RoboMecTest extends OpMode {
    Robot robby;

    private DcMotorEx intakeMotor;
    //private Servo flicker;
    private DcMotorEx shooter;
    private Servo intake_dropper;
    //private Servo flicker;
    private boolean shooterOn = false;
    private boolean lastAState = false;

    // /Users/user_name/Library/Android/sdk/platform-tools/adb connect 192.168.43.1:5555
    @Override
    public void init(){
        robby = new Robot("Robby", Robot.DrivetrainType.MECANUM, hardwareMap);
        robby.init();
        //robby.init("front_left_drive", "front_right_drive", "back_left_drive", "back_right_drive");
        robby.config("FORWARD", "REVERSE", "FORWARD", "REVERSE");
        robby.drivetrain.setMotorPower(1);

        intakeMotor = hardwareMap.get(DcMotorEx.class, "intake1");
        //flicker = hardwareMap.get(Servo.class, "flicker");
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
    }

    @Override
    public void loop(){
        double keep_going = 1;
        double Power_Adjustment = 0;
        while (keep_going > 0) {
            if (gamepad1.dpad_down) {
                if (Power_Adjustment > -1) Power_Adjustment = Power_Adjustment - 0.1;
            }
            if (gamepad1.dpad_up) {
                if (Power_Adjustment < 0) Power_Adjustment = Power_Adjustment + 0.1;
            }
            if (gamepad1.left_bumper) intakeMotor.setPower(1.0);
            else if (gamepad1.right_bumper) intakeMotor.setPower(-1.0);
            else intakeMotor.setPower(0.0);
            robby.drivetrain.drive(-gamepad1.left_stick_y - Power_Adjustment, gamepad1.right_trigger - gamepad1.left_trigger, gamepad1.right_stick_x);
            if (gamepad1.dpad_right) {
                intake_dropper.setPosition(-0.25);
                keep_going = keep_going - 0.25;
                gamepad1.rumble(5000);
            }
            //Shooter Toggle, A to toggle
            boolean currentAState = gamepad1.a;
            if (currentAState && !lastAState) { // Rising edge detection: triggers only once per press
                shooterOn = !shooterOn;
            }
            lastAState = currentAState;

            if (shooterOn) {
                shooter.setPower(-0.8);
            } else {
                shooter.setPower(0.0);
            }

            // Telemetry feedback for driver station
            telemetry.addData("Shooter Status", shooterOn ? "ON (-80%)" : "OFF");

            //if(gamepad1.b) flicker.setPosition(0.01);
        }
    }
}
