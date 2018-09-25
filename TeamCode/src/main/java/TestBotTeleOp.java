import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Alex Bulanov on 9/18/2018.
 */
@TeleOp(name = "TestBotTeleOp")
public class TestBotTeleOp extends LinearOpMode {

    DcMotor intakeMotor;
    DcMotor leftDrive;
    DcMotor rightDrive;

    public void runOpMode() throws InterruptedException {

        intakeMotor = hardwareMap.dcMotor.get("intake");
        leftDrive = hardwareMap.dcMotor.get("left");
        rightDrive = hardwareMap.dcMotor.get("right");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.x) {
                intakeMotor.setPower(1);
            } else if (gamepad1.y) {
                intakeMotor.setPower(-1);
            } else {
                intakeMotor.setPower(0);
            }

            leftDrive.setPower(gamepad1.left_stick_y);
            rightDrive.setPower(gamepad1.right_stick_y);

        }
    }
}
