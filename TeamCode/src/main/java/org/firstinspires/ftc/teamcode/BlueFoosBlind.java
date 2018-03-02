// Get the FIRST Team Code Package
package org.firstinspires.ftc.teamcode;

//Import all necessary classes
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
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
import java.util.Date;

@Autonomous(name="BlueFoosBlind")
public class BlueFoosBlind extends LinearOpMode {

    // Creates a variable of type robot, titled robot.
    public Robot robot;
    public BNO055IMU gyro;

    //Creates Vuforia variables
    public static final String TAG = "Vuforia VuMark Sample";
    public VuforiaLocalizer vuforia;

    //Runs Op Mode
    public void runOpMode() throws InterruptedException {

        gyro = hardwareMap.get(BNO055IMU.class, "imu");

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parametersVision = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parametersVision.vuforiaLicenseKey = "ASjaom3/////AAAAGVJ2W0zahEYxrq20N+eyldpgD2tJvXupPF1olTLbfRpR9gyG57/8kjOBt9AFnYyLPRTYPY/+yHB2tSnK3cmivLv384s7lcjA6GzeLXp3YaSyaYhILd1IJxhH6Lvp9z7i1RD4AfA01gS0fe5JX9wm/BmPZsf19fo2TmdBLepHwfGTKw2EZweW/hP5L8QjaFXEZn1kA8u0eOceu6HChXBOvHCQDVYsi54M92VcG7I8sihw8Xlb5NxIj8ek5NPVd13cKzUYbbtWsQfYV4pJ5dbRXw3f3p+i6Zmg3D4mVV8QpVhfMuzEQwO5O34aa5EX4oppAKcaA/569gFcveowLb+NR6CXxmdwm56c1KSY+Pln1gPP";
        parametersVision.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parametersVision);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
        relicTrackables.activate();

        //Sets the robot constructor equal to a new
        this.robot = new Robot(hardwareMap);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        gyro.initialize(parameters);
        robot.init();
        CameraDevice.getInstance().setFlashTorchMode(true);
        waitForStart();
        //sleep(500);
        // Creates a new vuMark variable called vuMark
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        telemetry.addData("VuMark", "%s visible", vuMark);
        telemetry.update();
        sleep(700);

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
            robot.vuMarkData = 'R';
        }
        //Set Right Wing Down
        robot.wing.setPosition(robot.RIGHT_WING_DOWN);
        sleep(500);

        //Scan Jewel
        if (robot.jewelColor.blue() < robot.jewelColor.red()) {
            robot.rotateRight(0.23); // was 0.25
            sleep(250);
            robot.stopDrive();
            robot.wing.setPosition(robot.RIGHT_WING_UP);
            sleep(250);
            robot.rotateLeft(0.23); // was  0.25
            sleep(250);
            robot.stopDrive();
        } else if (robot.jewelColor.blue() > robot.jewelColor.red()) {
            robot.rotateLeft(0.23);
            sleep(250);
            robot.stopDrive();
            robot.wing.setPosition(robot.RIGHT_WING_UP);
            sleep(250);
            robot.rotateRight(0.23);
            sleep(250);
            robot.stopDrive();
        }
        robot.wing.setPosition(robot.RIGHT_WING_UP);
        sleep(500);

        // Drive forward for 2 Seconds
        robot.backward(0.56); // was 0.6
        sleep(1000);//was 2000
        robot.stopDrive();

        if (opModeIsActive()) {
            rotate(0, 0.2); // was 0.45
        }

        //Drive Backwards to stone
        long startTime = System.currentTimeMillis();
        long timeElapsed = 0L;
        robot.backward(0.4); //was 0.7
        while (!(robot.getDR() < 15 && robot.getDR() > 5 && robot.getDL() < 15 && robot.getDL() > 5) && opModeIsActive() && timeElapsed < 2000) {
            timeElapsed = (new Date()).getTime() - startTime;
            telemetry.addLine( "Looking for Balancing Stone");
            telemetry.update();
        }
        robot.stopDrive();

        //Drive Forward Half Second
        robot.forward(0.6); //was 0.6
        sleep(200);
        robot.stopDrive();

        if (opModeIsActive()) {
            rotate(90, 0.4); // was 0.45
        }
        robot.between();
        if (opModeIsActive()) {
            rotate(90, 0.2); // was 0.25
        }
        robot.between();

        //GRAB
        robot.forward(0.6);
        robot.intakeGlyph(0.95);
        long startTime2 = System.currentTimeMillis();
        long timeElapsed2 = 0L;
        while (!(robot.getGlyph() < 10 && robot.getGlyph() > 5) && timeElapsed2 < 2000) {
            timeElapsed2 = (new Date()).getTime() - startTime2;
            telemetry.addData("Time: ", timeElapsed2);
            telemetry.update();
        }
        robot.stopDrive();

        robot.intakeGlyph(-0.95);
        sleep(20);
        robot.intakeGlyph(0);

        if (opModeIsActive()) {
            rotate(90, 0.21); // was 0.45
        }

        robot.backward(0.6);
        sleep(300);

        robot.intakeGlyph(0.95);

        robot.backward(0.6);
        sleep(2300);
        robot.stopDrive();

        if (opModeIsActive()) {
            rotate(90,0.2);
        }

        robot.strafeLeft(0.2);
        sleep(800);
        robot.stopDrive();

/*        robot.forward(0.36);
        sleep(120);
        robot.between();
*/
        if (opModeIsActive()) {
            rotate(90,0.2);
        }
        //Strafe to Correct Column
        robot.strafeRight(0.18);// was 0.14
        while (!(robot.getDR() < 15 && robot.getDR() > 5 && robot.getDL() < 15 && robot.getDL() > 5) && opModeIsActive()) {
            telemetry.addLine("Looking for Right");
            telemetry.update();
        }
        if (robot.vuMarkData == 'C' || robot.vuMarkData == 'L') {
            sleep(500);
            while (!(robot.getDR() < 15 && robot.getDR() > 5 && robot.getDL() < 15 && robot.getDL() > 5) && opModeIsActive()) {
                telemetry.addLine("Looking for Center");
                telemetry.update();
            }
        }
        if (robot.vuMarkData == 'L') {
            sleep(500);
            while (!(robot.getDR() < 15 && robot.getDR() > 5 && robot.getDL() < 15 && robot.getDL() > 5) && opModeIsActive()) {
                telemetry.addLine("Looking for Left");
                telemetry.update();
            }
        }
        robot.between();
        robot.strafeLeft(0.12);//  was 0.1
        while (!(robot.getDR() < 15 && robot.getDR() > 5 && robot.getDL() < 15 && robot.getDL() > 5) && opModeIsActive()) {
            telemetry.addLine("Correction");
            telemetry.update();
        }
        robot.stopDrive();

        robot.forward(0.25); // was 0.5
        sleep(500);
        robot.stopDrive();
        robot.leftLift.setPosition(robot.RAMP_LEFT_UP);
        robot.rightLift.setPosition(robot.RAMP_RIGHT_UP);
        sleep(1300);
        robot.forward(0.6);
        sleep(300);
        robot.backward(0.6);
        sleep(580);
        robot.forward(0.4);//0.5
        sleep(300);
        robot.leftLift.setPosition(robot.RAMP_LEFT_DOWN);
        robot.rightLift.setPosition(robot.RAMP_RIGHT_DOWN);
        sleep(800);
        ///////////////////////////////////////////////////////
        //REDO if the glyphs haven't been placed
        if (robot.getGlyph() > 5 && robot.getGlyph() < 10) {
            robot.strafeLeft(0.2);
            sleep(500);
            robot.stopDrive();

            if (opModeIsActive()) {
                rotate(90,0.16);
            }
            //Strafe to Correct Column
            robot.strafeRight(0.18);// was 0.14
            while (!(robot.getDR() < 15 && robot.getDR() > 5 && robot.getDL() < 15 && robot.getDL() > 5) && opModeIsActive()) {
                telemetry.addLine("Looking for Right");
                telemetry.update();
            }
            if (robot.vuMarkData == 'C' || robot.vuMarkData == 'L') {
                sleep(500);
                while (!(robot.getDR() < 15 && robot.getDR() > 5 && robot.getDL() < 15 && robot.getDL() > 5) && opModeIsActive()) {
                    telemetry.addLine("Looking for Center");
                    telemetry.update();
                }
            }
            if (robot.vuMarkData == 'L') {
                sleep(500);
                while (!(robot.getDR() < 15 && robot.getDR() > 5 && robot.getDL() < 15 && robot.getDL() > 5) && opModeIsActive()) {
                    telemetry.addLine("Looking for Left");
                    telemetry.update();
                }
            }
            robot.between();
            robot.strafeLeft(0.12);//  was 0.1
            while (!(robot.getDR() < 15 && robot.getDR() > 5 && robot.getDL() < 15 && robot.getDL() > 5) && opModeIsActive()) {
                telemetry.addLine("Correction");
                telemetry.update();
            }
            robot.stopDrive();
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //GRAB 2
        robot.forward(0.6);
        robot.intakeGlyph(0.95);
        long startTime3 = System.currentTimeMillis();
        long timeElapsed3 = 0L;
        while (!(robot.getGlyph() < 10 && robot.getGlyph() > 5) && timeElapsed3 < 2000) {
            timeElapsed3 = (new Date()).getTime() - startTime3;
            telemetry.addData("Time for second grab: ", timeElapsed3);
            telemetry.update();
        }
        robot.stopDrive();

        robot.intakeGlyph(-0.95);
        sleep(20);
        robot.intakeGlyph(0);

        if (opModeIsActive()) {
            rotate(90, 0.2); // was 0.45
        }

        robot.backward(0.6);
        sleep(300);

        robot.intakeGlyph(0.95);

        robot.backward(0.6);
        sleep(2300);
        robot.stopDrive();

        if (opModeIsActive()) {
            rotate(90,0.2);
        }

        robot.strafeLeft(0.2);
        sleep(500);
        robot.stopDrive();

/*        robot.forward(0.36);
        sleep(120);
        robot.between();
*/
        if (opModeIsActive()) {
            rotate(90,0.2);
        }
        // DUMP BLOCKS 3 & 4
        robot.forward(0.25); // was 0.5
        sleep(500);
        robot.stopDrive();
        robot.leftLift.setPosition(robot.RAMP_LEFT_UP);
        robot.rightLift.setPosition(robot.RAMP_RIGHT_UP);
        sleep(600);
        robot.forward(0.5);//0.5
        sleep(200);
        robot.stopDrive();
        robot.leftLift.setPosition(robot.RAMP_LEFT_DOWN);
        robot.rightLift.setPosition(robot.RAMP_RIGHT_DOWN);
        robot.backward(0.5);
        sleep(400);
    }

    public void rotate(double target, double power) {
        double angle = (gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle);
        if (angle > target && opModeIsActive()) {
            while (angle > target && opModeIsActive()) {
                angle = (gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle);
                robot.rotateRight(power);
            }
            robot.stopDrive();
        } else if (angle < target && opModeIsActive()) {
            while (angle < target && opModeIsActive()) {
                angle = (gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle);
                robot.rotateLeft(power);
            }
            robot.stopDrive();
        }
    }
}
