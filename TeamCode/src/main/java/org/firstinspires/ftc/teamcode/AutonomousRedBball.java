package org.firstinspires.ftc.teamcode;

import android.graphics.Camera;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.sun.tools.javac.code.Symbol;
import com.vuforia.CameraDevice;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import static java.lang.Thread.sleep;

/**
 * Created by Alex Bulanov on 12/5/2017.
 */

public class AutonomousRedBball extends LinearOpMode {

    public Robot robot;

    public static final String TAG = "Vuforia VuMark Sample";
    VuforiaLocalizer vuforia;

    public void runOpMode() throws InterruptedException {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "ASjaom3/////AAAAGVJ2W0zahEYxrq20N+eyldpgD2tJvXupPF1olTLbfRpR9gyG57/8kjOBt9AFnYyLPRTYPY/+yHB2tSnK3cmivLv384s7lcjA6GzeLXp3YaSyaYhILd1IJxhH6Lvp9z7i1RD4AfA01gS0fe5JX9wm/BmPZsf19fo2TmdBLepHwfGTKw2EZweW/hP5L8QjaFXEZn1kA8u0eOceu6HChXBOvHCQDVYsi54M92VcG7I8sihw8Xlb5NxIj8ek5NPVd13cKzUYbbtWsQfYV4pJ5dbRXw3f3p+i6Zmg3D4mVV8QpVhfMuzEQwO5O34aa5EX4oppAKcaA/569gFcveowLb+NR6CXxmdwm56c1KSY+Pln1gPP";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
        relicTrackables.activate();

        this.robot = new Robot(hardwareMap);

        waitForStart();

        robot.init();
        robot.leftSensor.setPosition(robot.LEFT_SENSOR_DOWN);
        robot.rightSensor.setPosition(robot.RIGHT_SENSOR_DOWN);
        sleep(500);
        CameraDevice.getInstance().setFlashTorchMode(true);
        sleep(1000);
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        telemetry.addData("VuMark", "%s visible", vuMark);
        telemetry.update();
        sleep(1000);

        if (vuMark == (vuMark.LEFT)) {
            telemetry.addLine("Left vuMark detected");
            telemetry.update();
            robot.vuMarkData = 'L';
        } else if (vuMark == (vuMark.CENTER)) {
            telemetry.addLine("Center vuMark detected");
            telemetry.update();
            robot.vuMarkData = 'C';
        } else if (vuMark == (vuMark.RIGHT)) {
            telemetry.addLine("Right vuMark detected");
            telemetry.update();
            robot.vuMarkData = 'R';
        } else {
            telemetry.addLine("No vuMark detected");
            telemetry.update();
            robot.vuMarkData = 'O';
        }
        sleep(1000);
        CameraDevice.getInstance().setFlashTorchMode(false);
        robot.rightSensor.setPosition(robot.RIGHT_SENSOR_UP);
        sleep(500);
        robot.rightWing.setPosition(robot.RIGHT_WING_DOWN);
        sleep(1000);

        if (robot.juulColorRight.blue() > robot.juulColorRight.red()) {
            telemetry.addLine("Blue Juul Detected");
            telemetry.update();
            robot.rotateRight(0.24);
            sleep(280);
            robot.stopDrive();
            sleep(500);
            robot.rightWing.setPosition(robot.RIGHT_WING_UP);
            sleep(300);
            robot.rotateLeft(0.24);
            sleep(280);
            robot.stopDrive();
        }

        robot.drive(0.43);
        sleep(1300);
        robot.between();
        telemetry.addLine("Off ramp");
        telemetry.update();
        //Drive to red line
        while ((robot.frontLeft.red() < 30) && opModeIsActive()) {
            robot.drive(0.3);
        }
        robot.drive(0.24);
        sleep(300);
        robot.between();
        robot.rightWing.setPosition(robot.RIGHT_WING_UP);
        //Drive off the red line

        telemetry.addLine("Off first red line");
        telemetry.update();
        //Drive to second red line
        while ((robot.frontLeft.red() < 20) && opModeIsActive()) {
            robot.drive(0.28);
        }
        robot.between();
        //Back up off the second red line
        while ((robot.frontLeft.red() > 30) && opModeIsActive()) {
            robot.drive(-0.25);
        }
        robot.between();
        //Drive onto it
        while ((robot.frontLeft.red() < 30) && opModeIsActive()) {
            robot.drive(0.25);
        }
        robot.between();
        //Drive past
        /*while ((frontLeft.red() > 50) && opModeIsActive()) {
            drive(0.2);
        }
        */
       /* if (robot.frontLeft.red() < 23) {
            while (robot.frontLeft.red() < 23) {
                robot.drive(0.3);
            }
            robot.between();
            while (robot.frontLeft.red() > 30) {
                robot.drive(-0.25);
            }
            robot.between();
            while (robot.frontLeft.red() < 23) {
                robot.drive(0.25);
            }
            robot.between();
            restOfAutonomous();
            stop();
        }
*/
        robot.leftSensor.setPosition(0);
        robot.between();
        robot.columnCount = 0;
        //Drive until we pass two red columns
        if (robot.vuMarkData == 'R') {
            telemetry.addLine("Going to the right column");
            telemetry.update();
            while ((robot.columnColorRight.red() < 34) && (robot.columnColorLeft.red() < 34) && opModeIsActive()) {
                robot.strafeLeft(0.4);
            }
        } else if (robot.vuMarkData == 'C') {
            telemetry.addLine("Going to the center column");
            telemetry.update();
            while (robot.columnCount < 2 && opModeIsActive()) {
                telemetry.addLine("Starting to scan columns");
                telemetry.update();
                robot.strafeLeft(0.4);
                if (robot.columnColorRight.red() > 23 && opModeIsActive()) {
                    telemetry.addData("Columns Counted: ", robot.columnCount);
                    telemetry.update();
                    robot.columnCount++;
                    sleep(600);
                }
            }
            robot.between();
            while ((robot.columnColorRight.red() < 34) && (robot.columnColorLeft.red() < 34) && opModeIsActive()) {
                robot.strafeRight(0.4);
            }

        } else if (robot.vuMarkData == 'L') {
            telemetry.addLine("Going to the left column");
            telemetry.update();
            //robot.leftDrive.setPower(0.28);
            //robot.rightDrive.setPower(-0.28);
            //sleep(108);
            //robot.between();
            while (robot.columnCount < 3 && opModeIsActive()) {
                telemetry.addLine("Starting to scan columns");
                telemetry.update();
                robot.strafeLeft(0.4);
                if (robot.columnColorRight.red() > 23 && opModeIsActive()) {
                    telemetry.addData("Columns Counted: ", robot.columnCount);
                    telemetry.update();
                    robot.columnCount++;
                    sleep(600);
                }
            }
            robot.between();
            while ((robot.columnColorRight.red() < 34) && (robot.columnColorLeft.red() < 34) && opModeIsActive()) {
                robot.strafeRight(0.4);
            }
        } else {
            telemetry.addLine("No vuMark detected");
            telemetry.update();
            while ((robot.columnColorRight.red() < 34) && (robot.columnColorLeft.red() < 34) && opModeIsActive()) {
                robot.strafeLeft(0.4);
            }
        }
        robot.between();
        sleep(500);
        //Insert glyph into the column
        robot.ejectGlyph(0.5);
        sleep(700);
    }
    /*public void restOfAutonomous() throws InterruptedException {
        if (robot.vuMarkData == 'R') {
            telemetry.addLine("Going to the right column");
            telemetry.update();
            while ((robot.columnColorRight.red() < 23) && (robot.columnColorLeft.red() < 23) && opModeIsActive()) {
                robot.strafeLeft(0.4);
            }
        } else if (robot.vuMarkData == 'C') {
            telemetry.addLine("Going to the center column");
            telemetry.update();
            while (robot.columnCount < 2 && opModeIsActive()) {
                telemetry.addLine("Starting to scan columns");
                telemetry.update();
                robot.strafeLeft(0.4);
                if (robot.columnColorRight.red() > 23 && opModeIsActive()) {
                    telemetry.addData("Columns Counted: ", robot.columnCount);
                    telemetry.update();
                    robot.columnCount++;
                    Thread.sleep(600);
                }
            }
            robot.between();
            while ((robot.columnColorRight.red() < 23) && (robot.columnColorLeft.red() < 23) && opModeIsActive()) {
                robot.strafeRight(0.35);
            }

        } else if (robot.vuMarkData == 'L') {
            telemetry.addLine("Going to the left column");
            telemetry.update();
            //robot.leftDrive.setPower(0.28);
            //robot.rightDrive.setPower(-0.28);
            //sleep(108);
            //robot.between();
            while (robot.columnCount < 3 && opModeIsActive()) {
                telemetry.addLine("Starting to scan columns");
                telemetry.update();
                robot.strafeLeft(0.4);
                if (robot.columnColorRight.red() > 23 && opModeIsActive()) {
                    telemetry.addData("Columns Counted: ", robot.columnCount);
                    telemetry.update();
                    robot.columnCount++;
                    Thread.sleep(600);
                }
            }
            robot.between();
            while ((robot.columnColorRight.red() < 23) && (robot.columnColorLeft.red() < 23) && opModeIsActive()) {
                robot.strafeRight(0.35);
            }
        } else {
            telemetry.addLine("No vuMark detected");
            telemetry.update();
            while ((robot.columnColorRight.red() < 23) && (robot.columnColorLeft.red() < 23) && opModeIsActive()) {
                robot.strafeLeft(0.35);
            }
        }
        robot.between();
        Thread.sleep(500);
        //Insert glyph into the column
        robot.ejectGlyph(0.5);
        sleep(700);
    */
}

