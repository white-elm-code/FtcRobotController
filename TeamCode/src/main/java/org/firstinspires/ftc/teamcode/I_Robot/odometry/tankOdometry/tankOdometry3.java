package org.firstinspires.ftc.teamcode.I_Robot.odometry.tankOdometry;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.I_Robot.odometry.Odometry;

public class tankOdometry3 implements Odometry {
    private DcMotorEx deadwheelXr;
    private DcMotorEx deadwheelXl;
    private DcMotorEx deadwheelY;
    @Override
    public void init(HardwareMap hwMap, String x1, String x2, String y) {
        deadwheelXr = hwMap.get(DcMotorEx.class, x1);
        deadwheelXl = hwMap.get(DcMotorEx.class, x2);
        deadwheelY = hwMap.get(DcMotorEx.class, y);
    }

    @Override
    public void driveToPosition(double x, double y, double r) {

    }
}
