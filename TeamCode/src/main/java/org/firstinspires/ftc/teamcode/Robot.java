package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Robot {

    //Drive
    public DcMotor frontLeftDrive;
    public DcMotor backLeftDrive;
    public DcMotor frontRightDrive;
    public DcMotor backRightDrive;

    //Intake
    public DcMotor inLeft;
    public DcMotor inRight;

    //Relic
    public DcMotor relic;
    public Servo frontRelic;
    public Servo relicWinch;

    //Lift
    public Servo leftLift;
    public Servo rightLift;

    //Jewel stuff
    public Servo wing;
    public Servo jewelServo;

    //Column sensors

    public DistanceSensor columnDistanceLeft;
    public DistanceSensor columnDistanceRight;

    public ColorSensor jewelColor;
    public DistanceSensor glyphDetect;

    public NavxMicroNavigationSensor navxMicro;
    public ElapsedTime timer = new ElapsedTime();
    public char vuMarkData;

    //Constants

    //ramp
    public static double RAMP_LEFT_UP = 0.04; // was 0.04
    public static double RAMP_LEFT_DOWN = 0.722; // was 0.722
    public static double RAMP_RIGHT_UP = 0.96; // was 0.88
    public static double RAMP_RIGHT_DOWN = 0.278; // was 0.18

    //jewel things
    public static double RIGHT_WING_UP = 0.6; //was 0.93. it worked fine, but the button head screws were getting stuck.
    public static double RIGHT_WING_DOWN = 0.05; // was 0.58

    //relic
    public static double RELIC_WINCH_MIDDLE = 0.57;
    public static double RELIC_WINCH_EXTENDED = 0.97;
    public static double RELIC_WINCH_DOWN = 0.05;
    public static double RELIC_FRONT_CLOSED = 0.05;//was  0.1
    public static double RELIC_FRONT_OPEN = 0.9;



    public HardwareMap map;

    public Robot (HardwareMap hardwareMap) {
        this.map = hardwareMap;

        //Drive
        frontLeftDrive = hardwareMap.dcMotor.get("fl");
        frontRightDrive = hardwareMap.dcMotor.get("fr");
        backLeftDrive = hardwareMap.dcMotor.get("bl");
        backRightDrive = hardwareMap.dcMotor.get("br");

        //Distance Sensors
        columnDistanceRight = hardwareMap.get(DistanceSensor.class, "ccr");
        columnDistanceLeft = hardwareMap.get(DistanceSensor.class, "ccl");

        //Intake
        inLeft = hardwareMap.dcMotor.get("lspin");
        inRight = hardwareMap.dcMotor.get("rspin");

        //Lift
        leftLift = hardwareMap.servo.get("llift");
        rightLift = hardwareMap.servo.get("rlift");

        //Jewel stuff
        wing = hardwareMap.servo.get("wing");
        jewelServo = hardwareMap.servo.get("jewelservo");
        jewelColor = hardwareMap.colorSensor.get("jc");

        //Glyph Stuff
        glyphDetect = hardwareMap.get(DistanceSensor.class, "glyph");

        //Relic
        relic = hardwareMap.dcMotor.get("relic");
        frontRelic = hardwareMap.servo.get("frelic");
        relicWinch = hardwareMap.servo.get("brelic");
    }

    public void init() {
        inRight.setDirection(DcMotor.Direction.REVERSE);
        wing.setPosition(RIGHT_WING_UP);
        leftLift.setPosition(RAMP_LEFT_DOWN);
        rightLift.setPosition(RAMP_RIGHT_DOWN);
    }

    public void forward(double power){
        frontLeftDrive.setPower(power);
        frontRightDrive.setPower(-power);
        backLeftDrive.setPower(power);
        backRightDrive.setPower(-power);

    }

    public void backward(double power) {
        frontLeftDrive.setPower(-power);
        frontRightDrive.setPower(power);
        backLeftDrive.setPower(-power);
        backRightDrive.setPower(power);
    }

    public void rotateLeft(double power){
        frontLeftDrive.setPower(-power);
        frontRightDrive.setPower(-power);
        backLeftDrive.setPower(-power);
        backRightDrive.setPower(-power);
    }

    public void rotateRight(double power){
        frontLeftDrive.setPower(power);
        frontRightDrive.setPower(power);
        backLeftDrive.setPower(power);
        backRightDrive.setPower(power);
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

    public void stopIntake() {
        inLeft.setPower(0);
        inRight.setPower(0);
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
        Thread.sleep(350);
    }

    public double getDR() {
        double distanceRight = columnDistanceRight.getDistance(DistanceUnit.CM);
        return distanceRight;
    }

    public double getDL() {
        double distanceLeft = columnDistanceLeft.getDistance(DistanceUnit.CM);
        return distanceLeft;
    }

    public double getGlyph() {
        double glyphDistance = glyphDetect.getDistance(DistanceUnit.CM);
        return glyphDistance;
    }
}