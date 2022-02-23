package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "DRIVETRAIN TEST", group = "TESTCHAMP")
@Disabled
public class MovementTest extends LinearOpMode {
    DcMotor fl, fr, bl, br;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        while(opModeIsActive())
        {
            if(gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0 || gamepad1.right_stick_x != 0)
            {
                move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            }
            else if(gamepad1.dpad_up)
            {
                move(0, -.5, 0);
            }
            else if(gamepad1.dpad_down)
            {
                move(0, .5, 0);
            }
            else if(gamepad1.dpad_left)
            {
                move(-.5, 0, 0);
            }
            else if(gamepad1.dpad_right)
            {
                move(.5, 0, 0);
            } else
            {
                move(0,0,0);
            }
        }
    }
    public void move(double strafe, double forward, double turn)
    {
        forward *= -1;

        fl.setPower(forward + turn + strafe);
        fr.setPower(forward - turn - strafe);
        bl.setPower(forward + turn - strafe);
        br.setPower(forward - turn + strafe);
    }

    public void initialize()
    {
        /**<----- MOTORS ----->*/
        fl = hardwareMap.get(DcMotor.class, "fl");
        fr = hardwareMap.get(DcMotor.class, "fr");
        bl = hardwareMap.get(DcMotor.class, "bl");
        br = hardwareMap.get(DcMotor.class, "br");



        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}
