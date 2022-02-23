package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "RED LEFT", group = "RED")
public class RedLeftAuto extends Auto
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
            drivetrain.strafe(false, 1100, 5000);
            drivetrain.forwards(true, 650, 5000);
            raiseLvl1();
            drivetrain.forwards(false, 250, 5000);
            drivetrain.turn(true, 850, 5000);
            drivetrain.setSpeed(0.5);
            drivetrain.forwards(true, 2250, 5000);
            drivetrain.turn(true, 900, 5000);
            drivetrain.strafe(false, 250, 2500);
            drivetrain.forwards(true, 150, 1000);
            carouselMotor.setPower(-.35);
            sleep(3000);
            drivetrain.setSpeed(0.1);
            sleep(100);
            drivetrain.forwards(true, 75, 1000);
            carouselMotor.setPower(-.35);
            sleep(3000);
            carouselMotor.setPower(0);
            drivetrain.setSpeed(1);
            drivetrain.strafe(false, 250, 1000);
            drivetrain.forwards(false, 900, 5000);
        } else if(path == "CENTER")
        {
            drivetrain.forwards(true, 200, 5000);
            drivetrain.strafe(false, 1100, 5000);
            drivetrain.forwards(true, 650, 5000);
            raiseLvl2();
            drivetrain.forwards(false, 250, 5000);
            drivetrain.turn(true, 850, 5000);
            drivetrain.setSpeed(0.5);
            drivetrain.forwards(true, 2250, 5000);
            drivetrain.turn(true, 900, 5000);
            drivetrain.strafe(false, 250, 2500);
            drivetrain.forwards(true, 150, 1000);
            drivetrain.setSpeed(0.1);
            sleep(100);
            carouselMotor.setPower(-.35);
            sleep(3000);
            drivetrain.forwards(true, 75, 1000);
            carouselMotor.setPower(-.35);
            sleep(3000);
            carouselMotor.setPower(0);
            drivetrain.setSpeed(1);
            drivetrain.strafe(false, 250, 1000);
            drivetrain.forwards(false, 900, 5000);
        }
        else
        {
            drivetrain.forwards(true, 200, 5000);
            drivetrain.strafe(false, 1000, 5000);
            drivetrain.forwards(true, 575, 5000);
            raiseLvl3();
            drivetrain.forwards(false, 250, 5000);
            drivetrain.turn(true, 850, 5000);
            drivetrain.setSpeed(0.5);
            drivetrain.forwards(true, 2250, 5000);
            drivetrain.turn(true, 900, 5000);
            drivetrain.strafe(false, 250, 2500);
            drivetrain.setSpeed(0.1);
            drivetrain.forwards(true, 125, 2500);
            sleep(100);
            carouselMotor.setPower(-.35);
            sleep(3000);
            drivetrain.forwards(true, 100, 1000);
            carouselMotor.setPower(-.35);
            sleep(3000);
            carouselMotor.setPower(0);
            drivetrain.setSpeed(1);
            drivetrain.strafe(false, 250, 1000);
            drivetrain.forwards(false, 900, 5000);
        }
    }
}
