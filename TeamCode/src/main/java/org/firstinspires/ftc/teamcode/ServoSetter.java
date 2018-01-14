package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Alex Bulanov on 12/11/2017.
 */
@TeleOp(name="Servo Setter")
public class ServoSetter extends LinearOpMode {

    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor centerDrive;
    DcMotor inLeft;
    DcMotor inRight;
    Servo leftLift;
    Servo rightLift;
    Servo leftSensor;
    Servo rightSensor;
    Servo leftWing;
    Servo rightWing;
    ColorSensor columnColorLeft;
    ColorSensor columnColorRight;
    ColorSensor frontLeft;
    ColorSensor frontRight;
    ColorSensor juulColor;
    BNO055IMU imu;
    int columnCount = 0;

    //Constants
    double RAMP_LEFT_UP = 0.04;
    double RAMP_LEFT_DOWN = 0.72;
    double RAMP_RIGHT_UP = 0.88;
    double RAMP_RIGHT_DOWN = 0.18;
    double LEFT_WING = 0.5;
    double RIGHT_WING = 0.5;
    double LEFT_SENSOR = 0.5;
    double RIGHT_SENSOR = 0.5;

    public void runOpMode() throws InterruptedException {

        //Drive
        leftDrive = hardwareMap.dcMotor.get("left");
        rightDrive = hardwareMap.dcMotor.get("right");
        centerDrive = hardwareMap.dcMotor.get("center");

        //Color Sensors
        columnColorRight = hardwareMap.colorSensor.get("ccr");
        columnColorLeft = hardwareMap.colorSensor.get("ccl");
        frontLeft = hardwareMap.colorSensor.get("fl");
        frontRight = hardwareMap.colorSensor.get("fr");

        //Intake
        inLeft = hardwareMap.dcMotor.get("lspin");
        inRight = hardwareMap.dcMotor.get("rspin");

        //Lift
        leftLift = hardwareMap.servo.get("llift");
        rightLift = hardwareMap.servo.get("rlift");

        //Sensor Flippers
        leftSensor = hardwareMap.servo.get("lsensor");
        rightSensor = hardwareMap.servo.get("rsensor");

        //Wings
        leftWing = hardwareMap.servo.get("lwing");
        rightWing = hardwareMap.servo.get("rwing");

        //Config
        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        centerDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        inRight.setDirection(DcMotorSimple.Direction.REVERSE);

        leftWing.setPosition(LEFT_WING);
        rightWing.setPosition(RIGHT_WING);

        waitForStart();
        while (opModeIsActive()) {

            if (gamepad1.dpad_up && gamepad1.y) {
                LEFT_WING += 0.03;
                leftWing.setPosition(LEFT_WING);
            }

            if (gamepad1.dpad_up && gamepad1.a) {
                LEFT_WING -= 0.03;
                leftWing.setPosition(LEFT_WING);
            }

            if (gamepad1.dpad_down && gamepad1.y) {
                RIGHT_WING += 0.03;
                rightWing.setPosition(RIGHT_WING);
            }

            if (gamepad1.dpad_down && gamepad1.a) {
                RIGHT_WING -= 0.03;
                rightWing.setPosition(RIGHT_WING);
            }

            if (gamepad1.dpad_left && gamepad1.y) {
                LEFT_SENSOR += 0.03;
                leftSensor.setPosition(LEFT_SENSOR);
            }

            if (gamepad1.dpad_left && gamepad1.a) {
                LEFT_SENSOR -= 0.03;
                leftSensor.setPosition(LEFT_SENSOR);
            }

            if (gamepad1.dpad_right && gamepad1.y) {
                RIGHT_SENSOR += 0.03;
                rightSensor.setPosition(RIGHT_SENSOR);
            }

            if (gamepad1.dpad_right && gamepad1.a) {
                RIGHT_SENSOR -= 0.03;
                rightSensor.setPosition(RIGHT_SENSOR);
            }

            //Reset Values
            if (gamepad1.x) {
                LEFT_WING = 0.5;
                RIGHT_WING = 0.5;
                LEFT_SENSOR = 0.5;
                RIGHT_SENSOR = 0.5;
            }

            //Reset positions
            if (gamepad1.b) {
                leftSensor.setPosition(LEFT_SENSOR);
                rightSensor.setPosition(RIGHT_SENSOR);
                leftWing.setPosition(LEFT_WING);
                rightWing.setPosition(RIGHT_WING);
            }

            telemetry.addData("Left Sensor: ", LEFT_SENSOR);
            telemetry.addData("Right Sensor: ", RIGHT_SENSOR);
            telemetry.addData("Left Wing: ", LEFT_WING);
            telemetry.addData("Right Wing: ", RIGHT_WING);
            telemetry.update();

        }
    }

}
