package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Movement Test")
public class MovementTest extends LinearOpMode {
    DcMotor fr, fl, br, bl;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        while(opModeIsActive())
        {
            if(gamepad1.left_stick_x != 0 || gamepad1.right_stick_x != 0 || gamepad1.left_stick_y != 0)
            {
                move(-gamepad1.left_stick_x, -gamepad1.right_stick_x, -gamepad1.left_stick_y);
            }
            if(gamepad1.left_stick_x == 0 || gamepad1.left_stick_y == 0 || gamepad1.right_stick_x == 0)
            {
                move(0, 0, 0);
            }
        }
    }

    public void move(double strafe, double forward, double turn)
    {
        //Following is just there if your robot is moving backwards, if it is then just uncomment it
        turn *= -1;
        forward *= 1;
        strafe *= 1;
        fl.setPower(forward + turn + strafe);
        fr.setPower(forward - turn + strafe);
        bl.setPower(forward - turn - strafe);
        br.setPower(forward + turn - strafe);
    }

    public void initialize()
    {
        /**<----- MOTORS ----->*/
        fr = hardwareMap.get(DcMotor.class, "fr");
        fl = hardwareMap.get(DcMotor.class, "fl");
        br = hardwareMap.get(DcMotor.class, "br");
        bl = hardwareMap.get(DcMotor.class, "bl");
    }
}
