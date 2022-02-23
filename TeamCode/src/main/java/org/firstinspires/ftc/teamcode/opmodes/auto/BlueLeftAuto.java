package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "BLUE LEFT", group = "BLUE")
@Disabled //Remaking this with roadrunner
public class BlueLeftAuto extends Auto
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
            drivetrain.forwards(true, 200, 5000);
            drivetrain.strafe(false, 1000, 5000);
            drivetrain.forwards(true, 625, 5000);
            raiseLvl1();
            drivetrain.forwards(false, 250, 5000);
            drivetrain.turn(false, 800, 5000);
            drivetrain.strafe(false, 750, 2500);
            sleep(100);
            drivetrain.setEncoderMode(DcMotor.RunMode.RUN_USING_ENCODER);
            drivetrain.move(0.5, 0, 1);
            sleep(1250);
            drivetrain.setEncoderMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            drivetrain.strafe(true, 1250, 5000);
        } else if(path == "CENTER")
        {
            drivetrain.forwards(true, 200, 5000);
            drivetrain.strafe(false, 1000, 5000);
            drivetrain.forwards(true, 625, 5000);
            raiseLvl2();
            drivetrain.forwards(false, 200, 5000);
            drivetrain.turn(false, 800, 5000);
            drivetrain.strafe(false, 750, 2500);
            sleep(100);
            drivetrain.setEncoderMode(DcMotor.RunMode.RUN_USING_ENCODER);
            drivetrain.move(0.5, 0, 1);
            sleep(1250);
            drivetrain.setEncoderMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            drivetrain.strafe(true, 1250, 5000);
        }
        else {
            drivetrain.forwards(true, 200, 5000);
            drivetrain.strafe(false, 1000, 1000);
            drivetrain.forwards(true, 600, 1000);
            raiseLvl3();
            drivetrain.forwards(false, 300, 1000);
            drivetrain.turn(false, 800, 5000);
            drivetrain.strafe(false, 750, 2500);
            sleep(100);
            drivetrain.setEncoderMode(DcMotor.RunMode.RUN_USING_ENCODER);
            drivetrain.move(0.5, 0, 1);
            sleep(1250);
            drivetrain.setEncoderMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            drivetrain.strafe(true, 1250, 5000);
        }
    }
}
