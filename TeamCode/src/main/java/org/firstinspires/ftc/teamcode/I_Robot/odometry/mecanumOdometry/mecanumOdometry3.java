package org.firstinspires.ftc.teamcode.I_Robot.odometry.mecanumOdometry;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.I_Robot.odometry.Odometry;
import org.firstinspires.ftc.teamcode.I_Robot.odometry.position.Position;

public class mecanumOdometry3 implements Odometry {
    // deadwheels
    private DcMotorEx n1; // right x
    private DcMotorEx n2; // left x
    private DcMotorEx n3; // single y

    // calibrations
    public final double L = 10.00; // distance between the horizontal deadwheels in cm
    public final double B = 10.00; // distance between the midpoint of L and the vertical deadwheel
    public final double R = 3.0; // wheel radius in cm
    public final double N = 2000; // deadwheel ticks per revolution, GoBuilda
    public final double c = 2.0 * Math.PI * R / N;
    public Position START_POS = new Position(0.0,0.0,0.0);
    public Position currentPosition = new Position(START_POS);

    // vars
    int oldRightPosition = 0;
    int oldLeftPosition = 0;
    int oldAuxPosition = 0;
    int lastRightPosition = 0;
    int lastLeftPosition = 0;
    int lastAuxPosition = 0;

    @Override
    public void init(HardwareMap hwMap, String x1, String x2, String y) { // reuse motor ports?
        n1 = hwMap.get(DcMotorEx.class, x1);
        n2 = hwMap.get(DcMotorEx.class, x2);
        n3 = hwMap.get(DcMotorEx.class, y);
    }

    public void update(){
        // the current deadwheel readings are now the last deadwheel readings
        lastRightPosition = oldRightPosition;
        lastLeftPosition = oldLeftPosition;
        lastAuxPosition = oldAuxPosition;
        // the current deadwheel readings are now the last deadwheel readings
        oldRightPosition = -n1.getCurrentPosition();
        oldLeftPosition = -n2.getCurrentPosition();
        oldAuxPosition = n3.getCurrentPosition();
        // get the change in readings
        double dn1 = oldRightPosition - lastRightPosition;
        double dn2 = oldLeftPosition - lastLeftPosition;
        double dn3 = oldAuxPosition - lastAuxPosition;
        // get the change in position (robot coords)
        double dTheta = c * (dn2 - dn1) / L;
        double dx = c * (dn1 + dn2) / 2.0;
        double dy = c * (dn3 - (dn2 - dn1) * B / L);
        // change the position (robot coords)
        double theta = currentPosition.h + (dTheta/2.0);
        currentPosition.x += dx * Math.cos(theta) - dy * Math.sin(theta);
        currentPosition.y += dx * Math.sin(theta) + dy * Math.cos(theta);
        currentPosition.h = dTheta;
    }

    @Override
    public void driveToPosition(double x, double y, double r) {

    }
}
