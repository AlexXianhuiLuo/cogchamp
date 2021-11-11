package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.opmodes.CogchampOpMode;

@Autonomous(name = "IMU AUTO", group = "COGCHAMP AUTO")
public class IMUAuto extends CogchampOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();



        while(opModeIsActive())
        {
            angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            if(angles.firstAngle != 0)
            {
                while(angles.firstAngle > .1) // FACING LEFT
                {
                    encoderMove(1, -1,1,-1);
                }
                while(angles.firstAngle < -.1) // FACING RIGHT
                {
                    encoderMove(-1, 1,-1,1);
                }
            }
            move(0,0,0);
            telemetry.update();
        }
    }

    public void encoderMove(int flTicks, int frTicks, int blTicks, int brTicks)
    {
        fl.setTargetPosition(flTicks);
        fr.setTargetPosition(frTicks);
        bl.setTargetPosition(blTicks);
        br.setTargetPosition(brTicks);
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        evalDirection(flTicks, fl);
        evalDirection(frTicks, fr);
        evalDirection(blTicks, bl);
        evalDirection(brTicks, br);
    }

    private void evalDirection(int ticks, DcMotor motor)
    {
        if(ticks > 0)
        {
            motor.setPower(.5);
        } else if(ticks < 0)
        {
            motor.setPower(-.5);
        } else
        {
            motor.setPower(0);
        }
    }

    public void move(double strafe, double turn, double forward)
    {
        fl.setPower(turn + forward + strafe);
        fr.setPower(turn - forward + strafe);
        bl.setPower(turn + forward - strafe);
        br.setPower(turn - forward - strafe);
    }
}
