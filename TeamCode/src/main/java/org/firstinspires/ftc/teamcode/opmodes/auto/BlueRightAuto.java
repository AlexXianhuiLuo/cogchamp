package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "BLUE RIGHT", group = "BLUE")
@Disabled //Remaking this with roadrunner
public class BlueRightAuto extends Auto
{
    String path;

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        path = startingPosition;
        telemetry.addData("PATH >>", path);
        telemetry.update();
//        sleep(10000);
        if(path == "LEFT")
        {
            drivetrain.forwards(true, 200, 5000);
            drivetrain.strafe(true, 1500, 5000);
            drivetrain.forwards(true, 700, 5000);
            raiseLvl1();
            drivetrain.forwards(false, 250, 5000);
            drivetrain.turn(false, 800, 5000);
            drivetrain.setSpeed(0.5);
            drivetrain.forwards(true, 2500, 3000);
            drivetrain.strafe(false, 750, 2500);
            drivetrain.setSpeed(0.1);
            drivetrain.forwards(false, 100, 1000);
            sleep(100);
            drivetrain.strafe(false, 100, 1000);
            carouselMotor.setPower(.35);
            sleep(3000);
            carouselMotor.setPower(0);
            drivetrain.setSpeed(1);
            drivetrain.forwards(true, 750, 5000);
            sleep(500);
            drivetrain.strafe(true, 1000, 5000);
        } else if(path == "CENTER")
        {
            drivetrain.forwards(true, 200, 5000);
            drivetrain.strafe(true, 1500, 5000);
            drivetrain.forwards(true, 700, 5000);
            raiseLvl2();
            drivetrain.forwards(false, 250, 5000);
            drivetrain.turn(false, 850, 5000);
            drivetrain.setSpeed(0.5);
            drivetrain.forwards(true, 2500, 3000);
            drivetrain.strafe(false, 750, 2500);
            drivetrain.forwards(false, 100, 1000);
            drivetrain.setSpeed(0.1);
            sleep(100);
            drivetrain.strafe(false, 100, 1000);
            carouselMotor.setPower(.35);
            sleep(3000);
            carouselMotor.setPower(0);
            drivetrain.setSpeed(1);
            drivetrain.forwards(true, 750, 5000);
            sleep(500);
            drivetrain.strafe(true, 1000, 5000);
        }
        else
        {
            drivetrain.forwards(true, 200, 5000);
            drivetrain.strafe(true, 1500, 5000);
            drivetrain.forwards(true, 575, 5000);
            raiseLvl3();
            drivetrain.forwards(false, 250, 5000);
            drivetrain.turn(false, 850, 5000);
            drivetrain.setSpeed(0.5);
            drivetrain.forwards(true, 2500, 3000);
            drivetrain.strafe(false, 750, 2500);
            drivetrain.setSpeed(0.1);
            sleep(100);
            drivetrain.strafe(false, 100, 1000);
            carouselMotor.setPower(.35);
            sleep(3000);
            carouselMotor.setPower(0);
            drivetrain.setSpeed(1);
            drivetrain.forwards(true, 750, 5000);
            sleep(500);
            drivetrain.strafe(true, 1000, 5000);
        }
    }
}
