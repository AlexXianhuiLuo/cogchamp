package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "RED RIGHT", group = "AUTO")
public class RedRightAuto extends Auto
{
    String path;
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        path = startingPosition;
        if(path == "LEFT")
        {
            drivetrain.strafe(true, 1200, 5000);
            drivetrain.forwards(true, 700, 5000);
            raiseLvl2();
            drivetrain.forwards(false, 250, 5000);
            drivetrain.turn(true, 800, 5000);
            drivetrain.forwards(false, 2500, 5000);
            drivetrain.forwards(false, 300, 5000);
            drivetrain.strafe(false, 250, 3000);
        } else if(path == "CENTER")
        {
            drivetrain.strafe(true, 1200, 5000);
            drivetrain.forwards(true, 750, 5000);
            raiseLvl2();
            drivetrain.forwards(false, 250, 5000);
            drivetrain.turn(true, 800, 5000);
            drivetrain.forwards(false, 2500, 5000);
            drivetrain.forwards(false, 300, 5000);
            drivetrain.strafe(false, 250, 3000);
        }
        else
        {
            drivetrain.strafe(true, 1200, 5000);
            drivetrain.forwards(true, 675, 5000);
            raiseLvl3();
            drivetrain.forwards(false, 250, 5000);
            drivetrain.turn(true, 800, 5000);
            drivetrain.forwards(false, 2500, 5000);
            drivetrain.forwards(false, 300, 5000);
            drivetrain.strafe(false, 250, 3000);
        }
    }
}
