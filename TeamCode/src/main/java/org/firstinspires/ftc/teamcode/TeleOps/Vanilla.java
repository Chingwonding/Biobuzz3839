package org.firstinspires.ftc.teamcode.TeleOps;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.configuration.Hardware;

@TeleOp(name = "Vanilla Teleop")
public class Vanilla extends LinearOpMode {

    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {
        robot.initialization(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        }
    }

    public void drive(double forward, double right, double rotate) {
        double frontLeftPower = forward + right + rotate;
        double frontRightPower = forward - right - rotate;
        double backRightPower = forward + right - rotate;
        double backLeftPower = forward - right + rotate;

        double max = Math.max(Math.abs(frontRightPower),
                Math.max(Math.abs(backLeftPower),
                        Math.max(Math.abs(frontLeftPower),
                                Math.abs(backRightPower))));

        double scaleFactor = (max > 1) ? (1.0 / max) : 1.0;
        scaleFactor *= Math.max(Math.abs(1 - gamepad1.right_trigger), 0.3);

        robot.setPower(frontRightPower * scaleFactor, backRightPower * scaleFactor, backLeftPower * scaleFactor, frontLeftPower * scaleFactor);
    }
}
