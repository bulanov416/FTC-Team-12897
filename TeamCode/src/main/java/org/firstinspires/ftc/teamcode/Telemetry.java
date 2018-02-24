package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Telemetry")
public class Telemetry extends LinearOpMode {

    public Robot robot;

    public void runOpMode() throws InterruptedException {

        this.robot = new Robot(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Column Distance Left: ", robot.getDL());
            telemetry.addData("Column Distance Right: ", robot.getDR());
            telemetry.addData("Jewel Color: ", robot.jewelColor.red());
            telemetry.addData("Jewel Color: ", robot.jewelColor.blue());
            telemetry.update();
        }
    }
}