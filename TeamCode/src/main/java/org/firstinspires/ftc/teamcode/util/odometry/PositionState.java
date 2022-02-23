package org.firstinspires.ftc.teamcode.util.odometry;

public class PositionState
{
    /**
     * ^ Y
     * |
     * |
     * |
     * |             X
     * 0 - - - - - - >
     */

    public double xPos, yPos, rotation;

    public PositionState(double currentX, double currentY, double currentRotation)
    {
        xPos = currentX;
        yPos = currentY;
        rotation = currentRotation;
    }

    public static double toRadians(double degrees)
    {
        return (degrees * Math.PI)/180;
    }
}
