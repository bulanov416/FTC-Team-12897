package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Telemetry")
public class Telemetry extends LinearOpMode {

    public Robot robot;

    public void runOpMode() throws InterruptedException {
        this.robot = new Robot(hardwareMap);

        waitForStart();
        robot.encoder.resetDeviceConfigurationForOpMode();
        while (opModeIsActive()) {
            robot.encoderAngle = robot.encoder.getVoltage()/3.26*360;
            telemetry.addLine("Angle: " + robot.encoderAngle);
            telemetry.update();
        }
    }
}
