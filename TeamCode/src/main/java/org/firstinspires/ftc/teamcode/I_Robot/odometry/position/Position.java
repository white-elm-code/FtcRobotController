package org.firstinspires.ftc.teamcode.I_Robot.odometry.position;

public class Position {
    public double x;
    public double y;
    public double h;
    public Position(Position position){
        this.x = position.x;
        this.y = position.y;
        this.h = position.h;
    }
    public Position(double x, double y, double h){
        this.x = x;
        this.y = y;
        this.h = h;
    }
}
