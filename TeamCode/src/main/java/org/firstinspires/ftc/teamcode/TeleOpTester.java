package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Alex Bulanov on 9/8/2018.
 */
@TeleOp(name="TeleOpTester")
public class TeleOpTester extends LinearOpMode {

    public DcMotor testMotor;

    public void runOpMode() {

        testMotor = hardwareMap.dcMotor.get("motor");

        waitForStart();

        while (opModeIsActive()) {

            testMotor.setPower(gamepad1.left_stick_y);

        }

    }
}
