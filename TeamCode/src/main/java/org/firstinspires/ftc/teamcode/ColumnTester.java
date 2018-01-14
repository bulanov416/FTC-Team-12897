package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static java.lang.Double.NaN;

/**
 * Created by Alex Bulanov on 12/5/2017.
 */
@Autonomous(name="ColumnTester")
public class ColumnTester extends LinearOpMode {

    public Robot robot;

    public static final String TAG = "Vuforia VuMark Sample";
    VuforiaLocalizer vuforia;

    public void runOpMode() throws InterruptedException {

        this.robot = new Robot(hardwareMap);

        //Starts Robot
        waitForStart();
        robot.init();
        sleep(500);

        robot.strafeRight(0.5);
        while (!(robot.getDR() < 15 && robot.getDR() > 5 && robot.getDL() < 15 && robot.getDL() > 5)) {
            telemetry.addLine("Looking for Right");
            telemetry.update();
        }
        sleep(500);
        while (!(robot.getDR() < 15 && robot.getDR() > 5 && robot.getDL() < 15 && robot.getDL() > 5)) {
            telemetry.addLine("Looking for Center");
            telemetry.update();
        }
        sleep(500);
        while (!(robot.getDR() < 15 && robot.getDR() > 5 && robot.getDL() < 15 && robot.getDL() > 5)) {
            telemetry.addLine("Looking for Left");
            telemetry.update();
        }
        /*
        robot.stopDrive();
        robot.drive(0.25);
        sleep(500);
        robot.stopDrive();
        robot.leftLift.setPosition(robot.RAMP_LEFT_UP);
        robot.rightLift.setPosition(robot.RAMP_RIGHT_UP);
        sleep(1000);
        robot.drive(0.25);
        sleep(500);
        robot.stopDrive();
        */
    }
}