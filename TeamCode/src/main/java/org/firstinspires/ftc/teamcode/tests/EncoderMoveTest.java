package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "ENCODER MOVEMENT TEST", group = "TESTCHAMP")
public class EncoderMoveTest extends LinearOpMode {
    private static double GLOBAL_SPEED_MODIFIER = .75;

    public ElapsedTime runtime = new ElapsedTime();

    DcMotor fr, fl, br, bl;

    DcMotor intakeMotor, outtakeMotor, carouselMotor, intakeLifter;

    Servo outtakeGate;

    @Override
    public void runOpMode() throws InterruptedException
    {
        runtime.reset();
        initialize();
        waitForStart();
        forwards(true, 500, 1000);
        turn(false, 500, 1000);
        forwards(true, 500, 1000);
        turn(false, 335, 1000);
        strafe(false, 1000, 1000);
        forwards(false, 1750, 1000);
        setMotorPower(0);
    }

    private void go(int time_ms)
    {
        runtime.reset();
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setMotorPower(GLOBAL_SPEED_MODIFIER);

        while(opModeIsActive() && runtime.milliseconds() < time_ms )
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
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

        telemetry.addLine("STOPPED");
        telemetry.update();
    }

    public void forwards(boolean forward, int amount, int time_ms)
    {
        if(!forward)
        {
            fr.setTargetPosition(amount);
            fl.setTargetPosition(amount);
            br.setTargetPosition(amount);
            bl.setTargetPosition(amount);
        } else
        {
            fr.setTargetPosition(-amount);
            fl.setTargetPosition(-amount);
            br.setTargetPosition(-amount);
            bl.setTargetPosition(-amount);
        }
        sleep(100);
        go(time_ms);
    }

    public void turn(boolean left, int amount, int time_ms)
    {
        if(!left)
        {
            fr.setTargetPosition(-amount);
            fl.setTargetPosition(amount);
            br.setTargetPosition(amount);
            bl.setTargetPosition(-amount);
        } else
        {
            fr.setTargetPosition(amount);
            fl.setTargetPosition(-amount);
            br.setTargetPosition(-amount);
            bl.setTargetPosition(amount);
        }
        go(time_ms);
    }

    public void strafe(boolean left, int amount, int time_ms)
    {
        if(!left)
        {
            fr.setTargetPosition(amount);
            fl.setTargetPosition(-amount);
            br.setTargetPosition(amount);
            bl.setTargetPosition(-amount);
        } else
        {
            fr.setTargetPosition(-amount);
            fl.setTargetPosition(amount);
            br.setTargetPosition(-amount);
            bl.setTargetPosition(amount);
        }
        go(time_ms);
    }

    public void setMotorPower(double power)
    {
        fl.setPower(power);
        fr.setPower(power);
        br.setPower(power);
        bl.setPower(power);
    }

    private void initialize()
    {
        /**<----- MOTORS ----->*/
        fr = hardwareMap.get(DcMotor.class, "fr");
        fl = hardwareMap.get(DcMotor.class, "fl");
        br = hardwareMap.get(DcMotor.class, "br");
        bl = hardwareMap.get(DcMotor.class, "bl");
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        /**<----- INTAKE ----->*/
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");
        intakeLifter = hardwareMap.get(DcMotor.class, "intake lifter");

        /**<----- OUTTAKE ----->*/
        outtakeMotor = hardwareMap.get(DcMotor.class,"outtake");
        outtakeGate = hardwareMap.get(Servo.class, "outtake gate");
        //outtakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        /**<----- CAROUSEL ----->*/
        carouselMotor = hardwareMap.get(DcMotor.class,"carousel");
        carouselMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        resetEncoders();
    }

    private void resetEncoders()
    {
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void raiseLvl3()
    {
        outtakeMotor.setTargetPosition(450);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeMotor.setPower(.5);
        runtime.reset();
        while(opModeIsActive() && runtime.milliseconds() < 1000 )
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }
        outtakeMotor.setPower(0);
        drop();
        sleep(100);
        outtakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setTargetPosition(-450);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeMotor.setPower(.5);
        runtime.reset();
        while(opModeIsActive() && runtime.milliseconds() < 1000 )
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }
        outtakeMotor.setPower(0);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void raiseLvl2()
    {
        outtakeMotor.setTargetPosition(250);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeMotor.setPower(.5);
        runtime.reset();
        while(opModeIsActive() && runtime.milliseconds() < 500 )
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }
        outtakeMotor.setPower(0);
        drop();
        sleep(100);
        outtakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setTargetPosition(-250);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeMotor.setPower(.5);
        runtime.reset();
        while(opModeIsActive() && runtime.milliseconds() < 500 )
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }
        outtakeMotor.setPower(0);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void raiseLvl1()
    {
        outtakeMotor.setTargetPosition(50);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeMotor.setPower(.5);
        runtime.reset();
        while(opModeIsActive() && runtime.milliseconds() < 100 )
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }
        outtakeMotor.setPower(0);
        drop();
        sleep(100);
        outtakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setTargetPosition(-50);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeMotor.setPower(.5);
        runtime.reset();
        while(opModeIsActive() && runtime.milliseconds() < 100 )
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }
        outtakeMotor.setPower(0);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void drop()
    {
        outtakeGate.setPosition(.5);
        sleep(500);
        outtakeGate.setPosition(0);
    }

    public void lowerIntake()
    {

    }

    public void raiseIntake()
    {

    }
}
