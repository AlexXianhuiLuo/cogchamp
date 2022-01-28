package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "LIFT TEST", group = "TESTCHAMP")
public class OuttakeTest extends LinearOpMode
{
    DcMotor outtakeMotor;

    @Override
    public void runOpMode() throws InterruptedException
    {
        initialize();

        waitForStart();

        while(opModeIsActive())
        {
            telemetry.addData("OUTTAKE LIFT ENCODER POS >>",outtakeMotor.getCurrentPosition());
            telemetry.addData("OUTTAKE LOWERED >>", outtakeMotor.getCurrentPosition() <= 0);
            if(!outtakeMotor.isBusy())
            {
                outtakeMotor.setPower(0);
                outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                if(gamepad1.right_trigger != 0 && outtakeMotor.getCurrentPosition() <= 2000)
                {
                    outtakeMotor.setPower(1);
                }
                else if(gamepad1.left_trigger != 0 && outtakeMotor.getCurrentPosition() >= 0)
                {
                    outtakeMotor.setPower(-1);
                }
                if(gamepad1.a)
                {
                    outtakeMotor.setTargetPosition(1000);
                    outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    outtakeMotor.setPower(1);
                } else if(gamepad1.b)
                {
                    outtakeMotor.setTargetPosition(0);
                    outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    outtakeMotor.setPower(1);
                }
            } else
            {
                telemetry.addLine("LIFT IS BUSY");
            }
            telemetry.update();
        }
    }



    private void initialize()
    {
        /*<----- OUTTAKE ----->*/
        outtakeMotor = hardwareMap.get(DcMotor.class,"outtake");
        outtakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        resetEncoders();
    }

    private void resetEncoders()
    {
        outtakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
