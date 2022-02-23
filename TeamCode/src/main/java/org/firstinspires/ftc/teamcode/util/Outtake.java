package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Outtake
{
    public boolean isLowered;

    double OUTTAKE_SPEED_MODIFIER = 1;

    HardwareMap hw;

    DcMotor outtake;
    Servo arm, gate;

    Double liftTime, dropTime;

    ElapsedTime runtime;

    boolean liftingLvl1, liftingLvl2, liftingLvl3;

    public Outtake(HardwareMap hardwareMap)
    {
        hw = hardwareMap;

        initialize();
    }

    public void initialize()
    {
        outtake = hw.get(DcMotor.class, "outtake");
        gate = hw.get(Servo.class, "outtake gate");
        arm = hw.get(Servo.class, "outtake arm");

        runtime = new ElapsedTime();

        liftTime = runtime.milliseconds();
        dropTime = runtime.milliseconds();
        outtake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        arm.setPosition(0.4);
        gate.setPosition(1);

        isLowered = true;
    }

    public boolean isBusy()
    {
        if(isLowered)
        {
            return false;
        }
        return (outtake.isBusy());
    }

    public void setPower(double newPower)
    {
        if(outtake.getCurrentPosition() >= 0 || outtake.getCurrentPosition() <= 2000)
        {
            outtake.setPower(newPower * OUTTAKE_SPEED_MODIFIER);
        } else
        {
            outtake.setPower(0);
        }
    }

    public void setSpeed(double newSpeed)
    {
        OUTTAKE_SPEED_MODIFIER = newSpeed;
    }

    public void moveLift(int target, double time_ms)
    {
        if(liftTime <= runtime.milliseconds())
        {
            outtake.setTargetPosition(target);
            outtake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            outtake.setPower(OUTTAKE_SPEED_MODIFIER);
            liftTime = runtime.milliseconds() + time_ms;
        }
    }

    public void resetLift(double time_ms) throws InterruptedException {
        if(arm.getPosition() != 0.40)
        {
            angleArm();
            Thread.sleep(250);
        }
        moveLift(0, time_ms);
    }

    public void raise1Auto()
    {
        moveLift(400 ,1000);
    }

    public void raise1()
    {
        moveLift(400, 1000);
        liftingLvl1 = true;
    }

    public void raise2Auto()
    {
        moveLift(1200, 1000);
    }
    public void raise2()
    {
        moveLift(600, 1000);
        liftingLvl2 = true;
    }

    public void raise3()
    {
        moveLift(700, 2000);
        liftingLvl3 = true;
    }
    public void raise3Auto()
    {
        moveLift(1900, 2000);
    }

    public void flatArm()
    {
        arm.setPosition(0.25);
    }

    public void angleArm() throws InterruptedException {
        arm.setPosition(0.40);
    }

    public void sleepDrop(int time_ms) throws InterruptedException {
        gate.setPosition(0.6);
        Thread.sleep(time_ms);
        gate.setPosition(1);
    }

    public void drop()
    {
        gate.setPosition(0.6);
        dropTime = runtime.milliseconds() + 500;
    }

    public void smallDrop()
    {
        gate.setPosition(0.8);
        dropTime = runtime.milliseconds() + 250;
    }

    public void autoDrop() throws InterruptedException {
        gate.setPosition(0.8);
        Thread.sleep(250);
        gate.setPosition(1);
    }

    public void pushDrop()
    {
        gate.setPosition(0.7);
        dropTime = runtime.milliseconds() + 250;
    }

    public int update() throws InterruptedException
    {
        isLowered = (outtake.getCurrentPosition() <= 25);
        if(!outtake.isBusy() || (runtime.milliseconds() >= liftTime))
        {
            outtake.setPower(0);
            outtake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            liftingLvl1 = false;
            liftingLvl2 = false;
            liftingLvl3 = false;
        }
        if(runtime.milliseconds() >= dropTime)
        {
            gate.setPosition(1);
        }
        if(liftingLvl1 || liftingLvl2 || liftingLvl3)
        {
            if(outtake.getCurrentPosition() >= 200)
            {
                flatArm();
            }
        }
        return outtake.getCurrentPosition();
    }
}
