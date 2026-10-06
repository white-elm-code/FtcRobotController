package org.firstinspires.ftc.teamcode.I_Robot.drivetrain.variables.calibrations;

public class Calibrations {

    public static int TICKS_PER_METER_HORIZONTAL = 1000;
    public static int TICKS_PER_YARD_HORIZONTAL = 1000;
    public static int TICKS_PER_METER_VERTICAL = 1000;
    public static int TICKS_PER_YARD_VERTICAL = 1000;
    public static int TICKS_PER_360_ROTATION = 1000;

    public static void calibrate(int tpmh, int tpyh, int tpmv, int tpyv, int tprot){
        TICKS_PER_METER_HORIZONTAL = tpmh;
        TICKS_PER_YARD_HORIZONTAL = tpyh;
        TICKS_PER_METER_VERTICAL = tpmv;
        TICKS_PER_YARD_VERTICAL = tpyv;
        TICKS_PER_360_ROTATION = tprot;
    }
}
