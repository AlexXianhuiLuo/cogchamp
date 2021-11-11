package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Linear Slide Tests", group = "15118")
public class LinearSlideTests extends LinearOpMode
{
    DcMotor linearSlide;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        waitForStart();

        int savedPos = 0;

        while(opModeIsActive())
        {
            telemetry.addData("Linear Slide Encoder Position", linearSlide.getCurrentPosition());
            telemetry.update();
            if(gamepad1.right_trigger != 0 || gamepad1.left_trigger != 0)
            {
                if (gamepad1.right_trigger != 0) {
                    linearSlide.setPower(-gamepad1.right_trigger * .5);
                }
                if (gamepad1.left_trigger != 0) {
                    linearSlide.setPower(gamepad1.left_trigger * .5);
                }
            }
            if(gamepad1.a)
            {
                savedPos = linearSlide.getCurrentPosition();
            }
            if(gamepad1.b)
            {
                linearSlide.setTargetPosition(savedPos);
                while(!(linearSlide.getCurrentPosition() >= linearSlide.getTargetPosition() + 10) || !(linearSlide.getCurrentPosition() <= linearSlide.getCurrentPosition() - 10))
                {
                    if(linearSlide.getCurrentPosition() > linearSlide.getTargetPosition())
                    {
                        linearSlide.setPower(-.1);
                    } else if(linearSlide.getCurrentPosition() < linearSlide.getTargetPosition())
                    {
                        linearSlide.setPower(.1);
                    } else
                    {
                        break;
                    }
                }
            }
        }
    }

    public void initialize()
    {
        linearSlide = hardwareMap.get(DcMotor.class, "outtake");
        linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
