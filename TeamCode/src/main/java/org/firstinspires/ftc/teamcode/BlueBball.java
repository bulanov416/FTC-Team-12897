package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Alex Bulanov on 12/5/2017.
 */
@Autonomous(name="BlueBball")
@Disabled
public class BlueBball extends LinearOpMode {

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
            robot.vuMarkData = 'L';
        }
        sleep(1000);
        CameraDevice.getInstance().setFlashTorchMode(false);
        robot.leftSensor.setPosition(robot.LEFT_SENSOR_UP);
        sleep(500);
        robot.leftWing.setPosition(robot.LEFT_WING_DOWN);
        sleep(1000);

        if (robot.juulColorRight.blue() < robot.juulColorRight.red()) {
            telemetry.addLine("Red Juul Detected");
            telemetry.update();
            robot.rotateLeft(0.24);
            sleep(280);
            robot.stopDrive();
            sleep(500);
            robot.leftWing.setPosition(robot.LEFT_WING_UP);
            sleep(300);
            robot.rotateRight(0.24);
            sleep(280);
            robot.stopDrive();
        }

        robot.drive(0.5);
        sleep(5000);
        robot.between();
        robot.leftWing.setPosition(robot.LEFT_WING_UP);
        telemetry.update();
        robot.drive(-0.25);
        sleep(200);
        robot.between();

        robot.rightSensor.setPosition(0);
        robot.between();
        robot.columnCount = 0;
        //Drive until we pass two red columns
        if (robot.vuMarkData == 'L') {
            telemetry.addLine("Going to the left column");
            telemetry.update();
            while ((robot.columnColorRight.blue() < 20) && (robot.columnColorLeft.blue() < 20) && opModeIsActive()) {
                robot.strafeRight(0.4);
            }
        } else if (robot.vuMarkData == 'C') {
            telemetry.addLine("Going to the center column");
            telemetry.update();
            while (robot.columnCount < 2 && opModeIsActive()) {
                telemetry.addLine("Starting to scan columns");
                telemetry.update();
                robot.strafeRight(0.4);
                if (robot.columnColorRight.red() > 23 && opModeIsActive()) {
                    telemetry.addData("Columns Counted: ", robot.columnCount);
                    telemetry.update();
                    robot.columnCount++;
                    sleep(600);
                }
            }
            robot.between();
            while ((robot.columnColorRight.red() < 34) && (robot.columnColorLeft.red() < 34) && opModeIsActive()) {
                robot.strafeLeft(0.4);
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
                robot.strafeRight(0.4);
                if (robot.columnColorRight.red() > 23 && opModeIsActive()) {
                    telemetry.addData("Columns Counted: ", robot.columnCount);
                    telemetry.update();
                    robot.columnCount++;
                    sleep(600);
                }
            }
            robot.between();
            while ((robot.columnColorRight.red() < 34) && (robot.columnColorLeft.red() < 34) && opModeIsActive()) {
                robot.strafeLeft(0.4);
            }
        } else {
            telemetry.addLine("No vuMark detected");
            telemetry.update();
            while ((robot.columnColorRight.red() < 34) && (robot.columnColorLeft.red() < 34) && opModeIsActive()) {
                robot.strafeRight(0.4);
            }
        }
        robot.between();
        sleep(500);
        //Insert glyph into the column
        robot.ejectGlyph(0.5);
        sleep(700);
    }
}

