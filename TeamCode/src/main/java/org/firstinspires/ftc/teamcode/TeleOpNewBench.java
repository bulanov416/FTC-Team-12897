package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by Alex Bulanov on 1/30/2018.
 */
@TeleOp(name="TeleOpTestBench")
public class TeleOpNewBench extends LinearOpMode {

    DcMotor leftBack;
    DcMotor rightBack;
    DcMotor leftFront;
    DcMotor rightFront;


    public void runOpMode() throws InterruptedException {

        leftBack = hardwareMap.dcMotor.get("bl");
        rightBack = hardwareMap.dcMotor.get("br");
        leftFront = hardwareMap.dcMotor.get("fl");
        rightFront = hardwareMap.dcMotor.get("fr");

        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {

            float gamepad1LeftY = gamepad1.left_stick_y;
            float gamepad1RightY = gamepad1.right_stick_y;

            leftBack.setPower(gamepad1LeftY);
            rightBack.setPower(gamepad1RightY);
            leftFront.setPower(gamepad1LeftY);
            rightFront.setPower(gamepad1RightY);

        }
    }

}
