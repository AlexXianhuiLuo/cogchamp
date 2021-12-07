package org.firstinspires.ftc.teamcode.opmodes.auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Straight Forward Auto", group = "AUTO")
public class StraightForwardAuto extends Auto
{
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        forwards(true, 1000, 3000);
        sleep(3000);
        fl.setPower(0);
        gradualStop();
    }


}
