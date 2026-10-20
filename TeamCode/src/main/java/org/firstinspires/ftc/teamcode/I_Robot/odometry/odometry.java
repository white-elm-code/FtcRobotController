package org.firstinspires.ftc.teamcode.I_Robot.odometry;

import com.qualcomm.robotcore.hardware.HardwareMap;

public interface Odometry {
    void init(HardwareMap hwMap, String x1, String x2, String y1);
    void driveToPosition(double x, double y, double r);
}
