package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmodes.auto.Auto;

@Autonomous(name = "OUTTAKE LEVEL TEST", group = "TESTCHAMP")
public class LiftLevelTest extends Auto
{
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
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
