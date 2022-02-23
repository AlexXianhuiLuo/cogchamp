package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmodes.auto.Auto;

@Autonomous(name = "INTAKE SENSOR TEST")
public class IntakeSensorTest extends Auto
{
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        while(!isStopRequested())
        {
            intakeMotor.setPower(1);
            telemetry.addData("RED   >>", colorSensor.red());
            telemetry.addData("BLUE  >>", colorSensor.blue());
            telemetry.addData("GREEN >>", colorSensor.green());
            telemetry.update();
        }
    }
}
