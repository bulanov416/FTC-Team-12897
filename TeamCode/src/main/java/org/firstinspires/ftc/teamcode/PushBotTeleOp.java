package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="TeleopPush")
public class PushBotTeleOp extends LinearOpMode {
    Servo righthand;
    Servo lefthand;
    DcMotor left;
    DcMotor right;

    @Override
    public void runOpMode() throws InterruptedException {
        righthand = hardwareMap.servo.get("righthand");
        lefthand = hardwareMap.servo.get("lefthand");
        left = hardwareMap.dcMotor.get("leftwheel");
        right = hardwareMap.dcMotor.get("rightwheel");
        right.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_right == true) {
                righthand.setPosition(0.9);
                lefthand.setPosition(0.1);
            } else if (gamepad1.dpad_right == false) {
                righthand.setPosition(1);
                lefthand.setPosition(0);
            }
            left.setPower(gamepad1.left_stick_y);
            right.setPower(gamepad1.right_stick_y);


        }
    }
}

