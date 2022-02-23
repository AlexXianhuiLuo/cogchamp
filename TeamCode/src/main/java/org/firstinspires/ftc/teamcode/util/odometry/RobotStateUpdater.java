package org.firstinspires.ftc.teamcode.util.odometry;

import com.qualcomm.hardware.bosch.BNO055IMU;

public class RobotStateUpdater
{
    public PositionState state;

    BNO055IMU imu;

    Odometers odometers;

    public RobotStateUpdater(BNO055IMU imu, Odometers odometers)
    {
        this.imu = imu;
        this.odometers = odometers;
    }

    public void update()
    {

    }

    public PositionState getState()
    {
        return state;
    }
}
