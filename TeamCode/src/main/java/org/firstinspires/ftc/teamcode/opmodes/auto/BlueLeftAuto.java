package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Autonomous(name = "BLUE LEFT", group = "AUTO")
public class BlueLeftAuto extends Auto
{
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        double mmsToTurn = 419.1*Math.PI;
        strafe(true, 1, 5000); //TODO: Replace amount
        forwards(true, mmToTick(1), 5000); //TODO: Replace Amount
        if(startingPosition == 'L')
        {
            raiseLvl1();
        } else if(startingPosition == 'C')
        {
            raiseLvl2();
        } else if(startingPosition == 'R')
        {
            raiseLvl3();
        }
        drop();
        lowerOuttake();
        forwards(false, mmToTick(1), 5000); //TODO: Replace amount
        turn(true, mmToTick((int) mmsToTurn/4), 5000);
        forwards(true, mmToTick(1), 5000); //TODO: Replace Amount
        while(opModeIsActive())
        {
            sleep(100);
        }
    }
}
