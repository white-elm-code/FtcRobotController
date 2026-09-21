package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.I_Robot.vision.AprilTagCam;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@Autonomous
public class RobotAprilTagCamTest extends OpMode {
    AprilTagCam aprilTagCam = new AprilTagCam();
    List<AprilTagDetection> aprilTagDetections;
    int[] detectionIds;

    @Override
    public void init(){
        aprilTagCam.init(hardwareMap, telemetry);
    }

    @Override
    public void loop(){
        aprilTagCam.update();
        aprilTagDetections = aprilTagCam.getAllDetections();
        if (aprilTagDetections != null && !aprilTagDetections.isEmpty()) {
            for (int x = 0; x < aprilTagDetections.size(); x++) {
                aprilTagCam.displayDetectionTelemetry(aprilTagDetections.get(x));
            }
        }
        telemetry.update();
    }
}
