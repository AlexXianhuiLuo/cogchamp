package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "RED LEFT", group = "AUTO")
public class RedLeftAuto extends Auto
{
    String path;
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        path = startingPosition;
        if(path != "RIGHT")
        {
            strafe(false, 1000, 1000);
            forwards(true, 800, 1000);
            if(startingPosition == "LEFT")
            {
                raiseLvl1();
            } else if(startingPosition == "CENTER")
            {
                raiseLvl2();
            }
            forwards(false, 800, 1000);
            strafe(true, 1250, 1000);
        }
        else
        {
            strafe(false, 1000, 1000);
            forwards(true, 600, 1000);
            raiseLvl3();
            forwards(false, 600, 1000);
            strafe(true, 1250, 1000);
        }
        while(opModeIsActive())
        {
            sleep(100);
        }
    }
}
