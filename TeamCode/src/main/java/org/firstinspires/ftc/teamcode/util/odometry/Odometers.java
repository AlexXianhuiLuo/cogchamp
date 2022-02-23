package org.firstinspires.ftc.teamcode.util.odometry;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Odometers
{
    DcMotor left, right, strafe;

    public Odometers(DcMotor leftOdometer, DcMotor rightOdometer, DcMotor strafeOdometer)
    {
        left = leftOdometer;
        right = rightOdometer;
        strafe = strafeOdometer;
    }

    public int[] getValues()
    {
        return new int[]{left.getCurrentPosition(), right.getCurrentPosition(), strafe.getCurrentPosition()};
    }

    public String getAction(Odometers previous)
    {
        String action = "";
        int[] pastValues = previous.getValues();
        int leftChange, rightChange, strafeChange;
        leftChange = this.getValues()[0] - pastValues[0];
        rightChange = this.getValues()[1] - pastValues[1];
        strafeChange = this.getValues()[2] - pastValues[2];

        if((leftChange > 0 && rightChange < 0) || (leftChange < 0 && rightChange > 0))
        {
            action += 'T';
        }
        if(leftChange > 0 && rightChange > 0)
        {

        }

        return action;
    }
}
