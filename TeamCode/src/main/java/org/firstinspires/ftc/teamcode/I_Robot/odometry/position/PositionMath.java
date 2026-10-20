package org.firstinspires.ftc.teamcode.I_Robot.odometry.position;

public class PositionMath {
    public static double calcDistance(Position position1, Position position2){
        return Math.sqrt(Math.pow(position1.x-position2.x, 2.0) + Math.pow(position1.y-position2.y, 2.0));
    }
}
