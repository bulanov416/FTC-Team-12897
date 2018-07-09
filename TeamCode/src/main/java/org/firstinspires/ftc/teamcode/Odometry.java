package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.ArrayList;


@TeleOp(name="Odometry")
public class Odometry extends LinearOpMode {

    public Robot robot;

    public Encoder xLeft;
    public Encoder xRight;
    public Encoder y;

    public static double X;
    public static double Y;
    public static double DEG;

    public static ArrayList<Encoder> allEncoders = new ArrayList<Encoder>();

    public void runOpMode() throws InterruptedException {
        this.robot = new Robot(hardwareMap);

        this.xLeft = new Encoder();
        this.xRight = new Encoder();
        this.y = new Encoder();

        waitForStart();
        robot.xLeft.resetDeviceConfigurationForOpMode();
        robot.xRight.resetDeviceConfigurationForOpMode();
        robot.y.resetDeviceConfigurationForOpMode();
        while (opModeIsActive()) {
            xLeft.newAngle = robot.xLeft.getVoltage()/3.26*360;
            xRight.newAngle = robot.xLeft.getVoltage()/3.26*360;
            y.newAngle = robot.xLeft.getVoltage()/3.26*360;

            Encoder.updatePosition(xLeft, xRight, y);

            telemetry.addLine("xLeft Angle: " + xLeft.newAngle);
            telemetry.addLine("xRight Angle: " + xRight.newAngle);
            telemetry.addLine("y Angle: " + y.newAngle);

            telemetry.addLine("X: " + X);
            telemetry.addLine("Y: " + Y);
            telemetry.addLine("DEG: " + DEG);

            telemetry.update();

        }
    }
}
