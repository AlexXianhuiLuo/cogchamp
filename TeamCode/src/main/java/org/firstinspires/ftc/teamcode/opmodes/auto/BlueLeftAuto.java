package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "BLUE LEFT", group = "AUTO")
public class BlueLeftAuto extends Auto
{
    String path;
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        path = startingPosition;
        if(path == "LEFT")
        {
            drivetrain.forwards(true, 200, 5000);
            drivetrain.strafe(false, 1100, 5000);
            drivetrain.forwards(true, 750, 5000);
            raiseLvl1();
            drivetrain.forwards(false, 250, 5000);
            drivetrain.turn(false, 800, 5000);
            drivetrain.forwards(false, 2500, 5000);
            drivetrain.forwards(false, 300, 5000);
            drivetrain.strafe(true, 250, 3000);
        } else if(path == "CENTER")
        {
            drivetrain.forwards(true, 200, 5000);
            drivetrain.strafe(false, 1100, 5000);
            drivetrain.forwards(true, 700, 5000);
            raiseLvl2();
            drivetrain.forwards(false, 200, 5000);
            drivetrain.turn(false, 800, 5000);
            drivetrain.forwards(false, 2500, 5000);
            drivetrain.forwards(false, 300, 5000);
            drivetrain.strafe(true, 250, 3000);
        }
        else
        {
            drivetrain.strafe(false, 1100, 1000);
            drivetrain.forwards(true, 650, 1000);
            raiseLvl3();
            drivetrain.forwards(false, 200, 1000);
            drivetrain.turn(false, 800, 5000);
            drivetrain.forwards(false, 2500, 5000);
            drivetrain.forwards(false, 300, 5000);
            drivetrain.strafe(true, 250, 3000);
        }
    }
}
