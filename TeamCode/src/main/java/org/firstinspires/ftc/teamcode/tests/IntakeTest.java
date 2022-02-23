package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmodes.auto.Auto;

@Autonomous(name = "INTAKE TEST")
//@Disabled
public class IntakeTest extends Auto
{
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        intake();
    }
}
