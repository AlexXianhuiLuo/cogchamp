package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "RED RIGHT", group = "RED")
@Disabled //Remaking this with roadrunner
public class RedRightAuto extends Auto
{
    String path;
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        path = startingPosition;
        telemetry.addData("PATH >>", path);
        telemetry.update();
        if(path == "LEFT")
        {
            drivetrain.strafe(true, 1350, 5000);
            drivetrain.forwards(true, 775, 5000);
            raiseLvl1();
            drivetrain.forwards(false, 250, 5000);
            drivetrain.turn(true, 800, 5000);
            drivetrain.strafe(true, 750, 2500);
            sleep(100);
            sleep(100);
            drivetrain.setEncoderMode(DcMotor.RunMode.RUN_USING_ENCODER);
            drivetrain.move(-0.5, 0, 1);
            sleep(1250);
            drivetrain.setEncoderMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            sleep(100);
            drivetrain.strafe(false, 1250, 5000);
        } else if(path == "CENTER")
        {
            drivetrain.strafe(true, 1350, 5000);
            drivetrain.forwards(true, 775, 5000);
            raiseLvl2();
            drivetrain.forwards(false, 250, 5000);
            drivetrain.turn(true, 800, 5000);
            drivetrain.strafe(true, 750, 2500);
            sleep(100);
            drivetrain.strafe(true, 200, 2500);
            sleep(100);
            drivetrain.setEncoderMode(DcMotor.RunMode.RUN_USING_ENCODER);
            drivetrain.move(-0.5, 0, 1);
            sleep(1250);
            drivetrain.setEncoderMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            sleep(100);
            drivetrain.strafe(false, 1250, 5000);
        }
        else
        {
            drivetrain.strafe(true, 1350, 5000);
            drivetrain.forwards(true, 600, 5000);
            raiseLvl3();
            drivetrain.forwards(false, 250, 5000);
            drivetrain.turn(true, 800, 5000);
            drivetrain.strafe(true, 750, 2500);
            sleep(100);
            drivetrain.strafe(true, 200, 2500);
            sleep(100);
            drivetrain.setEncoderMode(DcMotor.RunMode.RUN_USING_ENCODER);
            drivetrain.move(-0.5, 0, 1);
            sleep(1250);
            drivetrain.setEncoderMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            sleep(100);
            drivetrain.strafe(false, 1250, 5000);
        }
    }
}
