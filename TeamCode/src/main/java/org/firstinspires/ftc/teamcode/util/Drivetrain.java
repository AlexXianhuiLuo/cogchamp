package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Drivetrain
{
    public enum Mode
    {
        NONE, AUTO, TELE;
    }

    PIDCoefficients coeffs;

    PIDFController controller;

    public double DRIVETRAIN_SPEED_MODIFIER = 1;

    HardwareMap hw;

    DcMotor fl, fr, bl, br;

    Mode mode;

    ElapsedTime runtime = new ElapsedTime();

    boolean isBusy = false;

    public Drivetrain (HardwareMap hardwareMap)
    {
        hw = hardwareMap;
        mode = Mode.NONE;

        initialize();
    }

    /**<----- TELEOP ----->*/
    public void move(double strafe, double turn, double forward)
    {
        turn *= -DRIVETRAIN_SPEED_MODIFIER;
        forward *= DRIVETRAIN_SPEED_MODIFIER;
        strafe *= -DRIVETRAIN_SPEED_MODIFIER;
        fl.setPower(forward + turn + strafe);
        fr.setPower(forward - turn - strafe);
        bl.setPower(forward + turn - strafe);
        br.setPower(forward - turn + strafe);
    }
    /**<----- TELEOP ----->*/

    /**<----- HELPER ----->*/
    public void initialize()
    {
        /**<----- MOTORS ----->*/
        fr = hw.get(DcMotor.class, "fr");
        fl = hw.get(DcMotor.class, "fl");
        br = hw.get(DcMotor.class, "br");
        bl = hw.get(DcMotor.class, "bl");

        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);

        setEncoderMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setEncoderMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setMode(String newMode)
    {
        if(newMode.equals("AUTO"))
        {
            mode = Mode.AUTO;
            setEncoderMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else if(newMode.equals("TELEOP"))
        {
            mode = Mode.TELE;
            setEncoderMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        } else
        {
            mode = Mode.NONE;
        }
    }

    public String getMode()
    {
        if(mode.equals(Mode.AUTO))
        {
            return "AUTO";
        } else if(mode.equals(Mode.TELE))
        {
            return "TELEOP";
        } else
        {
            return "NONE";
        }
    }

    public void setSpeed(double newSpeed)
    {
        DRIVETRAIN_SPEED_MODIFIER = newSpeed;
    }

    public void setEncoderMode(DcMotor.RunMode encoderMode)
    {
        fr.setMode(encoderMode);
        fl.setMode(encoderMode);
        br.setMode(encoderMode);
        bl.setMode(encoderMode);
    }

    public void setMotorPower(double newPower)
    {
        fl.setPower(newPower);
        fr.setPower(newPower);
        bl.setPower(newPower);
        br.setPower(newPower);
    }

    public int[] getEncoderTicks()
    {
        return new int[]
                {fr.getCurrentPosition(), fl.getCurrentPosition(), br.getCurrentPosition(), bl.getCurrentPosition()};
    }
    /**<----- HELPER ----->*/

    /**<----- AUTO ----->*/
    public void forwards(boolean forward, int amount, int time_ms)
    {
        if(mode.equals(Mode.AUTO))
        {
            if (!forward) {
                fr.setTargetPosition(amount);
                fl.setTargetPosition(amount);
                br.setTargetPosition(amount);
                bl.setTargetPosition(amount);
            } else {
                fr.setTargetPosition(-amount);
                fl.setTargetPosition(-amount);
                br.setTargetPosition(-amount);
                bl.setTargetPosition(-amount);
            }
            go(time_ms);
        }
    }

    public void strafe(boolean left, int amount, int time_ms)
    {
        if(mode.equals(Mode.AUTO))
        {
            if (!left) {
                fr.setTargetPosition(amount);
                fl.setTargetPosition(-amount);
                br.setTargetPosition(-amount);
                bl.setTargetPosition(amount);
            } else {
                fr.setTargetPosition(-amount);
                fl.setTargetPosition(amount);
                br.setTargetPosition(amount);
                bl.setTargetPosition(-amount);
            }
            go(time_ms);
        }
    }

    public void turn(boolean left, int amount, int time_ms)
    {
        if(mode.equals(Mode.AUTO))
        {
            if (!left) {
                fr.setTargetPosition(-amount);
                fl.setTargetPosition(amount);
                br.setTargetPosition(-amount);
                bl.setTargetPosition(amount);
            } else {
                fr.setTargetPosition(amount);
                fl.setTargetPosition(-amount);
                br.setTargetPosition(amount);
                bl.setTargetPosition(-amount);
            }
            go(time_ms);
        }
    }

    public void goToPosition(int frPos, int flPos, int brPos, int blPos, int time_ms)
    {
        if(mode.equals(Mode.AUTO))
        {
            fr.setTargetPosition(frPos);
            fl.setTargetPosition(flPos);
            br.setTargetPosition(brPos);
            bl.setTargetPosition(blPos);
            go(time_ms);
        }
    }

    public boolean isBusy() {
        return isBusy;
    }

    private void go(int time_ms)
    {
        if(mode.equals(Mode.AUTO))
        {
            isBusy = true;
            fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            setMotorPower(DRIVETRAIN_SPEED_MODIFIER);

            runtime.reset();
            while (runtime.milliseconds() < time_ms) {
                int busyCount = 0;
                if (!fr.isBusy()) {
                    busyCount++;
                }
                if (!fl.isBusy()) {
                    busyCount++;
                }
                if (!br.isBusy()) {
                    busyCount++;
                }
                if (!bl.isBusy()) {
                    busyCount++;
                }
                if (busyCount >= 3) {
                    break;
                }
            }
            setMotorPower(0);

            fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            isBusy = false;
        }
    }
    /**<----- AUTO ----->*/
}
