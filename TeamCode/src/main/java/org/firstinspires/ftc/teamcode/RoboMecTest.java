package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.I_Robot.Robot;

@TeleOp(name = "RoboMecTest", group = "TeleOp")
public class RoboMecTest extends OpMode {
    private Robot robby;

    private DcMotorEx intakeMotor;
    //private DcMotorEx shooter;
    private DcMotorEx intake_midway_pusher;
    private Servo intake_dropper;

    //private boolean shooterOn = false;
    private boolean lastAState = false;
    private boolean lastDpadUp = false;
    private boolean lastDpadDown = false;
    private boolean lastDpadRight = false;

    private double driveSpeedScale = 1.0; // Starts at 100% full speed

    @Override
    public void init() {
        robby = new Robot("Robby", Robot.DrivetrainType.MECANUM, hardwareMap);
        robby.init();
        robby.config("FORWARD", "REVERSE", "FORWARD", "REVERSE");
        robby.drivetrain.setMotorPower(1);

        intakeMotor = hardwareMap.get(DcMotorEx.class, "intake1");
        //shooter = hardwareMap.get(DcMotorEx.class, "shooter");

        try {
            intake_midway_pusher = hardwareMap.get(DcMotorEx.class, "intake midway pusher");
        } catch (Exception e) {
            intake_midway_pusher = null;
        }

        try {
            intake_dropper = hardwareMap.get(Servo.class, "intake_dropper");
        } catch (Exception e) {
            intake_dropper = null;
        }

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        // Drive Speed Scale
        boolean currentDpadUp = gamepad1.dpad_up;
        boolean currentDpadDown = gamepad1.dpad_down;

        if (currentDpadDown && !lastDpadDown) {
            driveSpeedScale = Math.max(0.2, driveSpeedScale - 0.1); // Decrease speed (min 20%)
        }
        if (currentDpadUp && !lastDpadUp) {
            driveSpeedScale = Math.min(1.0, driveSpeedScale + 0.1); // Increase back up (max 100%)
        }
        lastDpadUp = currentDpadUp;
        lastDpadDown = currentDpadDown;

        // Drivetrain
        double drive = -gamepad1.left_stick_y * driveSpeedScale;
        double turn = (gamepad1.right_trigger - gamepad1.left_trigger) * driveSpeedScale;
        double strafe = gamepad1.right_stick_x * driveSpeedScale;
        robby.drivetrain.drive(drive, turn, strafe);
        // Intake + Intake Pusher
        if (gamepad1.left_bumper) {
            intakeMotor.setPower(1.0);
            if (intake_midway_pusher != null) intake_midway_pusher.setPower(1.0);
        } else if (gamepad1.right_bumper) {
            intakeMotor.setPower(-1.0);
            if (intake_midway_pusher != null) intake_midway_pusher.setPower(-1.0);
        } else {
            intakeMotor.setPower(0.0);
            if (intake_midway_pusher != null) intake_midway_pusher.setPower(0.0);
        }

        // Intake Dropper Servo
        boolean currentDpadRight = gamepad1.dpad_right;
        if (currentDpadRight && !lastDpadRight) {
            if (intake_dropper != null) {
                intake_dropper.setPosition(0.0); // (servos take 0.0 to 1.0)
            }
            gamepad1.rumble(500); // Rumble for 500ms on press (Tehe)
        }
        lastDpadRight = currentDpadRight;

        // Shooter
        /*boolean currentAState = gamepad1.a;
        if (currentAState && !lastAState) {
            shooterOn = !shooterOn;
        }
        lastAState = currentAState;

        if (shooterOn) {
            //shooter.setPower(-0.8);
        } else {
            shooter.setPower(0.0);
        }
*/
        // Telemetry
        telemetry.addData("Drive Speed Scale", "%d%%", Math.round(driveSpeedScale * 100));
        //telemetry.addData("Shooter Status", shooterOn ? "ON (-80%)" : "OFF");
        if (intake_dropper != null) {
            telemetry.addData("Intake Dropper Pos", intake_dropper.getPosition());
        }
    }
}
