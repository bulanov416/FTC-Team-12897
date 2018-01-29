package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Robot {

    public DcMotor frontLeftDrive;
    public DcMotor backLeftDrive;
    public DcMotor frontRightDrive;
    public DcMotor backRightDrive;
    public DcMotor inLeft;
    public DcMotor inRight;
    public DcMotor relic;
    public Servo leftLift;
    public Servo rightLift;
    public Servo leftWing;
    public Servo rightWing;
    public Servo frontRelic;
    public Servo backRelic;
    public ColorSensor columnColorLeft;
    public ColorSensor columnColorRight;
    public DistanceSensor columnDistanceLeft;
    public DistanceSensor columnDistanceRight;
    public ColorSensor jewelColorLeft;
    public ColorSensor jewelColorRight;
    public NavxMicroNavigationSensor navxMicro;
    public ElapsedTime timer = new ElapsedTime();
    public char vuMarkData;

    //Constants
    public static double RAMP_LEFT_UP = 0.04; // was 0.04
    public static double RAMP_LEFT_DOWN = 0.722; // was 0.722
    public static double RAMP_RIGHT_UP = 0.96; // was 0.88
    public static double RAMP_RIGHT_DOWN = 0.278; // was 0.18
    public static double LEFT_WING_UP = 0.64; //was 0.6
    public static double LEFT_WING_DOWN = 0.1; //was 0.3
    public static double RIGHT_WING_UP = 0.7; //was 0.9
    public static double RIGHT_WING_DOWN = 0.04; // was 0.58
    public static double RELIC_FRONT_DOWN = 0.1;
    public static double RELIC_FRONT_UP = 0.9;
    public static double RELIC_BACK_DOWN = 0.1;
    public static double RELIC_BACK_UP = 0.9;

    public HardwareMap map;

    public Robot (HardwareMap hardwareMap) {
        this.map = hardwareMap;

        //Drive
        frontLeftDrive = hardwareMap.dcMotor.get("fl");
        frontRightDrive = hardwareMap.dcMotor.get("fr");
        backLeftDrive = hardwareMap.dcMotor.get("bl");
        backRightDrive = hardwareMap.dcMotor.get("br");

        //Color Sensors
        columnColorRight = hardwareMap.colorSensor.get("ccr");
        columnColorLeft = hardwareMap.colorSensor.get("ccl");

        //Distance Sensors
        columnDistanceRight = hardwareMap.get(DistanceSensor.class, "ccr");
        columnDistanceLeft = hardwareMap.get(DistanceSensor.class, "ccl");

        //Intake
        inLeft = hardwareMap.dcMotor.get("lspin");
        inRight = hardwareMap.dcMotor.get("rspin");

        //Lift
        leftLift = hardwareMap.servo.get("llift");
        rightLift = hardwareMap.servo.get("rlift");

        //Wings
        leftWing = hardwareMap.servo.get("lwing");
        rightWing = hardwareMap.servo.get("rwing");

        //Relic
        relic = hardwareMap.dcMotor.get("relic");
        frontRelic = hardwareMap.servo.get("frelic");
        backRelic = hardwareMap.servo.get("brelic");

        //jewel Sensors
        jewelColorLeft = hardwareMap.colorSensor.get("jcl");
        jewelColorRight = hardwareMap.colorSensor.get("jcr");

        //IMU

    }

    public void init() {
        inRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightWing.setPosition(RIGHT_WING_UP);
        leftWing.setPosition(LEFT_WING_UP);
        leftLift.setPosition(RAMP_LEFT_DOWN);
        rightLift.setPosition(RAMP_RIGHT_DOWN);
    }

    public void forward(double power){
        frontLeftDrive.setPower(-power);
        frontRightDrive.setPower(power);
        backLeftDrive.setPower(-power);
        backRightDrive.setPower(power);
    }

    public void backward(double power) {
        frontLeftDrive.setPower(power);
        frontRightDrive.setPower(-power);
        backLeftDrive.setPower(power);
        backRightDrive.setPower(-power);
    }

    public void leftOnly (double power) {
        frontLeftDrive.setPower(power);
        backLeftDrive.setPower(power);
    }

    public void rightOnly (double power) {
        frontRightDrive.setPower(power);
        backRightDrive.setPower(power);
    }

    public void forwardLeft(double power) {
        frontLeftDrive.setPower(power);
        backRightDrive.setPower(power);
    }

    public void forwardRight(double power) {
        frontRightDrive.setPower(-power);
        backLeftDrive.setPower(-power);
    }

    public void rotateLeft(double power){
        frontLeftDrive.setPower(power);
        frontRightDrive.setPower(power);
        backLeftDrive.setPower(power);
        backRightDrive.setPower(power);
    }

    public void rotateRight(double power){
        frontLeftDrive.setPower(-power);
        frontRightDrive.setPower(-power);
        backLeftDrive.setPower(-power);
        backRightDrive.setPower(-power);
    }

    public void strafeLeft(double power){
        frontLeftDrive.setPower(-power);
        frontRightDrive.setPower(-power);
        backLeftDrive.setPower(power);
        backRightDrive.setPower(power);
    }

    public void strafeRight(double power){
        frontLeftDrive.setPower(power);
        frontRightDrive.setPower(power);
        backLeftDrive.setPower(-power);
        backRightDrive.setPower(-power);
    }

    public void stopDrive() {
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backLeftDrive.setPower(0);
        backRightDrive.setPower(0);
    }

    public void intakeGlyph(double power) {
        inLeft.setPower(-power);
        inRight.setPower(-power);
    }

    public void ejectGlyph(double power) {
        inLeft.setPower(power);
        inRight.setPower(power);
    }

    public void between() throws InterruptedException {
        stopDrive();
        Thread.sleep(500);
    }

    public double getDR() {
        double distanceRight = columnDistanceRight.getDistance(DistanceUnit.CM);
        return distanceRight;
    }

    public double getDL() {
        double distanceLeft = columnDistanceLeft.getDistance(DistanceUnit.CM);
        return distanceLeft;
    }
}