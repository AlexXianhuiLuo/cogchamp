package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "SERVO TESTS", group = "TESTCHAMP")
public class ServoPosTests extends LinearOpMode
{
    DcMotor fr, fl, br, bl;
    DcMotor intakeMotor, linearSlideMotor, carouselMotor;
    Servo intakeLifter, outtakeGate;

    @Override
    public void runOpMode() throws InterruptedException
    {
        initialize();

        waitForStart();

        double upBoundIn = 0;
        double downBoundIn = intakeLifter.getPosition();

        double upBoundOut = 0;
        double downBoundOut = outtakeGate.getPosition();

        while(opModeIsActive())
        {
            telemetry.addData("Intake Lifter Servo Position", intakeLifter.getPosition());
            telemetry.addData("Outtake Gate Servo Position", outtakeGate.getPosition());
            telemetry.update();
            if(gamepad1.left_bumper)
            {
                upBoundIn -= .1;
            }
            if(gamepad1.right_bumper)
            {
                upBoundIn += .1;
            }
            if(gamepad1.x)
            {
                upBoundOut -= .1;
            }
            if(gamepad1.y)
            {
                upBoundOut += .1;
            }
            if(gamepad1.a)
            {
                intakeLifter.setPosition(upBoundIn);
                sleep(250);
                intakeLifter.setPosition(downBoundIn);
                sleep(250);
            }
            if(gamepad1.b)
            {
                outtakeGate.setPosition(upBoundOut);
                sleep(250);
                outtakeGate.setPosition(downBoundOut);
                sleep(250);
            }
        }
    }

    public void initialize()
    {
        intakeLifter = hardwareMap.get(Servo.class,"intake lifter");
        outtakeGate = hardwareMap.get(Servo.class, "outtake gate");
    }
}
