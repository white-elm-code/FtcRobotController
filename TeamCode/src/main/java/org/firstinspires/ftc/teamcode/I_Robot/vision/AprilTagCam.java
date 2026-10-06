package org.firstinspires.ftc.teamcode.I_Robot.vision;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.ArrayList;
import java.util.List;

public class AprilTagCam {
    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;
    private List<AprilTagDetection> allDetections = new ArrayList<>();
    private List<AprilTagSingleDetection> detectedSingleTags = new ArrayList<>();
    private List<AprilTagClusterDetection> detectedClusterTags = new ArrayList<>();
    private Telemetry telemetry;

    public void init(HardwareMap hwMap, Telemetry telemetry){
        this.telemetry = telemetry;

        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hwMap.get(WebcamName.class, "Webcam 1"));
        builder.setCameraResolution(new Size(640, 480));
        builder.addProcessor(aprilTagProcessor);

        visionPortal = builder.build();
    }

    public void update(){
        allDetections = aprilTagProcessor.getDetections();
        this.detectedSingleTags.clear();
        this.detectedClusterTags.clear();
        for(AprilTagDetection detection : allDetections){
            if(detection instanceof AprilTagSingleDetection)
                this.detectedSingleTags.add((AprilTagSingleDetection) detection);
            else if(detection instanceof AprilTagClusterDetection)
                this.detectedClusterTags.add((AprilTagClusterDetection) detection);
        }
    }

    public List<AprilTagDetection> getAllDetections(){
        return allDetections;
    }

    public List<AprilTagSingleDetection> getSingleDetections(){
        return detectedSingleTags;
    }
    public List<AprilTagClusterDetection> getClusterDetections(){
        return detectedClusterTags;
    }

    public void displayDetectionTelemetry(AprilTagDetection detection){
        if(detection == null) return;
        if(detection instanceof AprilTagSingleDetection) displaySingleDetectionTelemetry((AprilTagSingleDetection) detection);
        else if(detection instanceof AprilTagClusterDetection) displayClusterDetectionTelemetry((AprilTagClusterDetection) detection);
    }
    public void displaySingleDetectionTelemetry(AprilTagSingleDetection detection){
        if (detection.metadata != null) {
            telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
            telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
            telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
            telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
        } else {
            telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
            telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
        }
    }
    public void displayClusterDetectionTelemetry(AprilTagClusterDetection detection){
        telemetry.addLine(String.format("\n==== (Cluster) %s", detection.metadata.name));
        telemetry.addLine(String.format("Visibility: %d%% of cluster tags visible", detection.percentClusterFound));
        telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
        telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
        telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
    }

    public void stop(){
        if(visionPortal != null) visionPortal.close();
    }
}
