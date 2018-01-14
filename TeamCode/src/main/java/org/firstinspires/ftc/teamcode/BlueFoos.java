// Get the FIRST Team Code Package
package org.firstinspires.ftc.teamcode;

//Import all necessary classes
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.CameraDevice;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

//Name the Autonomous "Avi's Autonomous
@Autonomous(name="BlueFoos")
public class BlueFoos extends LinearOpMode {

    // Creates a variable of type robot, titled robot.
    public Robot robot;

    //Creates Vuforia variables
    public static final String TAG = "Vuforia VuMark Sample";
    public VuforiaLocalizer vuforia;

    //Runs Op Mode
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

        //Sets the robot constructor equal to a new
        this.robot = new Robot(hardwareMap);
        robot.init();
        //Starts Robot
        waitForStart();
        sleep(500);

        //Turns on the Phone Flash Light
        CameraDevice.getInstance().setFlashTorchMode(true);
        sleep(1000);

        // Creates a new vuMark variable called vuMark
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        telemetry.addData("VuMark", "%s visible", vuMark);
        telemetry.update();
        sleep(1000);

        //Scans for a vuMark
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

        //Turns flashlight off
        CameraDevice.getInstance().setFlashTorchMode(false);

        //Set Right Wing Down
        robot.rightWing.setPosition(robot.RIGHT_WING_DOWN);
        sleep(500);

        //Scan Juul
        if (robot.juulColorRight.blue() < robot.juulColorRight.red()) {
            robot.rotateRight(0.25);
            sleep(250);
            robot.stopDrive();
            robot.rightWing.setPosition(robot.RIGHT_WING_UP);
            sleep(250);
            robot.rotateLeft(0.25);
            sleep(250);
            robot.stopDrive();
        }
        else if (robot.juulColorRight.blue() > robot.juulColorRight.red()) {
            robot.rotateLeft(0.25);
            sleep(250);
            robot.stopDrive();
            robot.rightWing.setPosition(robot.RIGHT_WING_UP);
            sleep(250);
            robot.rotateRight(0.25);
            sleep(250);
            robot.stopDrive();
        }
        sleep(500);
        // Drive forward for 1.5 Seconds
        robot.drive(0.6);
        sleep(2000);
        robot.stopDrive();

        //Drive Backwards to stone
        robot.drive(-0.4);
        while (!(robot.getDR() < 15 && robot.getDR() > 5 && robot.getDL() < 15 && robot.getDL() > 5) && opModeIsActive()) {
            telemetry.addLine("Looking for Balancing Stone");
            telemetry.update();
        }
        robot.stopDrive();

        //Drive Forward Half Second
        robot.drive(0.6);
        sleep(500);
        robot.stopDrive();

        //Turn to 270
        if (opModeIsActive()) {
            if ((robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle + 180) < 270) {
                rotateClockwise(270);
            }
            else if ((robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle + 180) > 270) {
                rotateCounterClockwise(270);
            }
        }
        sleep(500);
        if (opModeIsActive()) {
            if ((robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle + 180) < 270) {
                rotateClockwise(270);
            }
            else if ((robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle + 180) > 270) {
                rotateCounterClockwise(270);
            }
        }
        sleep(500);
        //Drive Forwards for 2 seconds and Intake Glyphs
        robot.drive(0.5);
        robot.intakeGlyph(0.9);
        sleep(2500);
        robot.between();

        robot.drive(-0.6);
        sleep(600);
        robot.between();

        if (opModeIsActive()) {
            if ((robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle + 180) < 270) {
                rotateClockwise(270);
            }
            else if ((robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle + 180) > 270) {
                rotateCounterClockwise(270);
            }
        }

        robot.between();

        //Drive Backwards
        robot.drive(-0.6);
        sleep(3000);
        robot.stopDrive();

        //Drive Forwards
        robot.drive(0.25);
        sleep(180);//was 100
        robot.stopDrive();

        // Check Rotation, then set to 270
        if (opModeIsActive()) {
            if ((robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle + 180) < 270) {
                rotateClockwise(270);
            } else if ((robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle + 180) > 270) {
                rotateCounterClockwise(270);
            }
        }
        sleep(500);
        //Strafe to Correct Column
        robot.strafeRight(0.52);// was 0.4
        while (!(robot.getDR() < 15 && robot.getDR() > 5 && robot.getDL() < 15 && robot.getDL() > 5) && opModeIsActive()) {
            telemetry.addLine("Looking for Right");
            telemetry.update();
        }

        if (robot.vuMarkData == 'C' || robot.vuMarkData == 'L') {
            sleep(400);
            while (!(robot.getDR() < 15 && robot.getDR() > 5 && robot.getDL() < 15 && robot.getDL() > 5) && opModeIsActive()) {
                telemetry.addLine("Looking for Center");
                telemetry.update();
            }
        }
        if (robot.vuMarkData == 'L') {
            sleep(400);
            while (!(robot.getDR() < 15 && robot.getDR() > 5 && robot.getDL() < 15 && robot.getDL() > 5) && opModeIsActive()) {
                telemetry.addLine("Looking for Left");
                telemetry.update();
            }
        }

        //Dump Blocks
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

        ///////////////////////////////////////////////////////
    /*    //GRAB 2
        robot.leftLift.setPosition(robot.RAMP_LEFT_DOWN);
        robot.rightLift.setPosition(robot.RAMP_RIGHT_DOWN);

        robot.drive(0.6);
        robot.intakeGlyph(0.9);
        sleep(3000);
        robot.stopDrive();


        robot.drive(-0.6);
        sleep(3500);
        robot.stopDrive();

        if (opModeIsActive()) {
            if ((robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle + 180) < 270) {
                robot.rotateClockwise(270);
            } else if ((robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle + 180) > 270) {
                robot.rotateCounterClockwise(270);
            }
        }

        robot.drive(0.25);
        sleep(100);
        robot.stopDrive();
        robot.stopDrive();
        robot.drive(0.25);
        sleep(500);
        robot.stopDrive();
        robot.leftLift.setPosition(robot.RAMP_LEFT_UP);
        robot.rightLift.setPosition(robot.RAMP_RIGHT_UP);
        sleep(1000);
        robot.drive(0.25);
        sleep(500);
        robot.stopDrive();*/
    }
    public void rotateClockwise(double target) {
        double angle = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle + 180;
        while (angle > (target + 2.5) || angle < (target -2.5) && opModeIsActive()) {
            //Clockwise
            angle = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle + 180;
            robot.leftDrive.setPower(0.25);
            robot.rightDrive.setPower(-0.25);
        }
        robot.stopDrive();
    }
    public void rotateCounterClockwise(double target) {
        double angle = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle + 180;
        while (angle > (target + 2) || angle < (target -2) && opModeIsActive()) {
            //Clockwise
            angle = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle + 180;
            robot.leftDrive.setPower(-0.25);
            robot.rightDrive.setPower(0.25);
        }
        robot.stopDrive();
    }
}
