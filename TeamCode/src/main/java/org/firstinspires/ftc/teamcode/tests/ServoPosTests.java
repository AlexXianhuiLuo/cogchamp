package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "SERVO TESTS", group = "TESTCHAMP")
public class ServoPosTests extends LinearOpMode
{
    Servo intakeLifter1, intakeLifter2;

    double targetPos = 0.5;

    @Override
    public void runOpMode() throws InterruptedException
    {
        initialize();

        waitForStart();

        while(opModeIsActive())
        {
            if(gamepad1.a)
            {
                intakeLifter1.setPosition(targetPos);
                intakeLifter2.setPosition(targetPos);
                sleep(250);
                intakeLifter1.setPosition(0);
                intakeLifter2.setPosition(0);
                sleep(250);
            }
            if(gamepad1.x)
            {
                increasePos(.05);
            }
            if(gamepad1.y)
            {
                decreasePos(.05);
            }
            if(gamepad1.b)
            {
                targetPos = intakeLifter1.getPosition();
                telemetry.addData("Saved Position >> ", targetPos);
            }
            telemetry.update();
            sleep(100);
        }
    }

    private void increasePos(double amount)
    {
        targetPos += amount;
        telemetry.addData("Target Position >> ", targetPos);
    }
    private void decreasePos(double amount)
    {
        targetPos -= amount;
        telemetry.addData("Target Position >> ", targetPos);
    }

    public void initialize()
    {
        intakeLifter1 = hardwareMap.get(Servo.class,"intake lifter right");
        intakeLifter2 = hardwareMap.get(Servo.class, "intake lifter left");
    }
}
