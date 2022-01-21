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
        if(path != "RIGHT")
        {
            strafe(true, 1250, 1000);
            forwards(true, 800, 1000);
            if(path == "LEFT")
            {
                raiseLvl1();
            } else if(path == "CENTER")
            {
                raiseLvl2();
            }
            forwards(false, 800, 5000);
            turn(false, 750, 5000);
            strafe(true, 1500, 5000);
            forwards(false, 2000, 5000);
            lowerIntake();
            int[] encoderValues = intake();
            liftIntake();
            goToPosition(-encoderValues[0], -encoderValues[1], -encoderValues[2], -encoderValues[3], 1000);
            forwards(true, 2000, 5000);
            turn(false, 625, 5000);
            forwards(true, 500, 5000);
            if(path == "LEFT")
            {
                raiseLvl1();
            } else if(path == "CENTER")
            {
                raiseLvl2();
            }
        }
        else
        {
            strafe(true, 1250, 1000);
            forwards(true, 600, 1000);
            raiseLvl3();
            forwards(false, 600, 1000);
            strafe(true, 1500, 1000);
        }
        while(opModeIsActive())
        {
            sleep(100);
        }
    }
}
