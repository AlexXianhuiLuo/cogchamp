package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.Drivetrain;

@TeleOp(name = "DRIVETRAIN TEST", group = "TESTCHAMP")
public class MovementTest extends LinearOpMode {
    Drivetrain drivetrain;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        while(opModeIsActive())
        {
            if(gamepad1.left_bumper)
            {
                drivetrain.setSpeed(.5);
            } else
            {
                drivetrain.setSpeed(1);
            }
            if(gamepad1.left_stick_x != 0 || gamepad1.right_stick_x != 0 || gamepad1.left_stick_y != 0)
            {
                drivetrain.move(-gamepad1.left_stick_x, -gamepad1.right_stick_x, -gamepad1.left_stick_y);
            } else if(gamepad1.dpad_up)
            {
                drivetrain.move(0, 0, .5);
            }
            else if(gamepad1.dpad_left)
            {
                drivetrain.move(.5, 0, 0);
            }
            else if(gamepad1.dpad_down)
            {
                drivetrain.move(0, 0, -.5);
            }
            else if(gamepad1.dpad_right)
            {
                drivetrain.move(-.5,0,0);
            }
            else
            {
                drivetrain.move(0, 0, 0);
            }
        }
    }

    public void initialize()
    {
        /**<----- MOTORS ----->*/
        drivetrain = new Drivetrain(hardwareMap);
        drivetrain.initialize();
        drivetrain.setMode("TELEOP");
    }
}
