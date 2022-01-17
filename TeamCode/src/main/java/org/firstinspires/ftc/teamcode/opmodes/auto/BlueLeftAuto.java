package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Autonomous(name = "BLUE LEFT", group = "AUTO")
public class BlueLeftAuto extends Auto
{
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

//        if(startingPosition == "LEFT")
//        {
//            raiseLvl1();
//        } else if(startingPosition == "CENTER")
//        {
//            raiseLvl2();
//        } else if(startingPosition == "RIGHT")
//        {
//            raiseLvl3();
//        }

        raiseLvl1();
        sleep(3000);
        raiseLvl2();
        sleep(3000);
        raiseLvl3();
        sleep(3000);
        while(opModeIsActive())
        {
            sleep(100);
        }
    }
}
