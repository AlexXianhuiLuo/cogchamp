package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.openftc.easyopencv.OpenCvCamera;

@Autonomous(name = "INTAKE LIFTER TEST", group = "TESTCHAMP")
public class IntakeLifterTest extends LinearOpMode
{
    private static final double GLOBAL_SPEED_MODIFIER = -.75;

    public ElapsedTime runtime = new ElapsedTime();

    OpenCvCamera camera;

    DcMotor fr, fl, br, bl;

    DcMotor intakeMotor, outtakeMotor, carouselMotor, intakeLifter;

    Servo outtakeGate;

    @Override
    public void runOpMode() throws InterruptedException
    {
        initialize();
        waitForStart();

        liftIntake();
        sleep(3000);
        lowerIntake();
        while(opModeIsActive())
        {
            sleep(100);
        }
    }

    private void liftIntake()
    {
        runtime.reset();
        intakeLifter.setTargetPosition(40);
        intakeLifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intakeLifter.setPower(1);
        while(intakeLifter.isBusy() && runtime.milliseconds() < 1000)
        {
            telemetry.addLine("RAISING");
            telemetry.update();
        }
        intakeLifter.setPower(0);
        intakeLifter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void lowerIntake()
    {
        runtime.reset();
        intakeLifter.setTargetPosition(0);
        intakeLifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intakeLifter.setPower(1);
        while(intakeLifter.isBusy() && runtime.milliseconds() < 500)
        {
            telemetry.addLine("LOWERING");
            telemetry.update();
        }
        intakeLifter.setPower(0);
        intakeLifter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void initialize()
    {
        /*<----- MOTORS ----->*/
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

        /*<----- INTAKE ----->*/
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");
        intakeLifter = hardwareMap.get(DcMotor.class, "intake lifter");
        intakeLifter.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeLifter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /*<----- OUTTAKE ----->*/
        outtakeMotor = hardwareMap.get(DcMotor.class,"outtake");
        outtakeGate = hardwareMap.get(Servo.class, "outtake gate");
        //outtakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        /*<----- CAROUSEL ----->*/
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
        intakeLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeLifter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
