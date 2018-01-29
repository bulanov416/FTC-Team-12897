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
            telemetry.addData("Column Left Red: ", robot.columnColorLeft.red());
            telemetry.addData("Column Left Blue: ", robot.columnColorLeft.blue());
            telemetry.addData("Column Right Red: ", robot.columnColorRight.red());
            telemetry.addData("Column Right Blue: ", robot.columnColorRight.blue());
            telemetry.addData("Jewel Left Red: ", robot.jewelColorLeft.red());
            telemetry.addData("Jewel Left Blue: ", robot.jewelColorLeft.blue());
            telemetry.addData("Jewel Right Red: ", robot.jewelColorRight.red());
            telemetry.addData("Jewel Right Blue: ", robot.jewelColorRight.blue());
            telemetry.update();
        }}

}