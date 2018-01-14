package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.microsoft.MicrosoftGamepadXbox360;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by alexbulanov on 10/10/17.
 */
@TeleOp (name="TeleOpOutreach")
public class TeleOpOutreach extends OpMode {

    DcMotor rightDrive;
    DcMotor leftDrive;
    Servo catapultHolder;
    float leftPower;
    float rightPower;
    String catapultPosition;

    public void init() {
        leftDrive = hardwareMap.dcMotor.get("left");
        rightDrive = hardwareMap.dcMotor.get("right");
        catapultHolder = hardwareMap.servo.get("ch");

    }

    public void loop() {

        leftPower = (-gamepad1.left_stick_y);
        rightPower = gamepad1.right_stick_y;

        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);

        if (gamepad1.a) {
            catapultHolder.setPosition(1);
            catapultPosition = "Catapult Launch Success";
        } else {
            catapultHolder.setPosition(0.4);
            catapultPosition = "Catapult Ready!";
        }

        telemetry.addData("Left Power: ", leftPower);
        telemetry.addData("Right Power: ", rightPower);
        telemetry.addData("Servo Position: ", catapultPosition);
        telemetry.update();
    }

    public void stop() {

    }

}
