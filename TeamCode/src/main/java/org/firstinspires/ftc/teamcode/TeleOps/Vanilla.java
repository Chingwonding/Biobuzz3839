package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.configuration.Hardware;
import org.firstinspires.ftc.teamcode.experimentalCode.parts.intake;
import org.firstinspires.ftc.teamcode.experimentalCode.parts.shooter;
import com.acmerobotics.dashboard.config.Config;


//import com.acmerobotics.dashboard.config.Config;

//@Config
@TeleOp(name = "Vanilla Teleop")
public class Vanilla extends LinearOpMode {

    Hardware robot = new Hardware();
    double velocity;
    public static double SHOOTER_VELOCITY = 2000;
    public static double GATE_VELOCITY = 3000;

    @Override
    public void runOpMode() {
        robot.initialization(hardwareMap);
        shooter shooter = new shooter(robot);
        intake intake = new intake(robot);

        boolean isShooting = false;
        boolean isIntaking = false;
        boolean isGating = false;
        boolean isReversing = false;

        boolean lastXState = false;
        boolean lastAState = false;
        boolean lastGateState = false;
        boolean lastReverseState = false;

        waitForStart();

        while (opModeIsActive()) {
            drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

            // Toggle shooting state when X / right trigger is pressed
            if (gamepad1.right_trigger > 0.2 && !lastXState) {
                isShooting = !isShooting;
            }
            lastXState = gamepad1.right_trigger_pressed;

            // Toggle intaking state when right bumper is pressed
            if (gamepad1.right_bumper && !lastAState) {
                isIntaking = !isIntaking;
            }
            lastAState = gamepad1.right_bumper;

            if (gamepad1.left_trigger > 0.2 && !lastGateState)
            {
                isGating = !isGating;
            }
            lastGateState = gamepad1.left_trigger_pressed;

            if (gamepad1.left_bumper && !lastReverseState)
            {
                isReversing = !isReversing;
            }
            lastReverseState = gamepad1.left_bumper;


            if (isShooting) {
                velocity = 0.60 * 6000 * 28 / 60;
                shooter.setVelocity(velocity);
                //robot.shooter.setPower(0.99);
                telemetry.addData("Shooter velocity: ", robot.shooter.getVelocity());

            } else {
                shooter.stop();
            }

            if (isIntaking) {
                intake.startIntake(0.99);
            } else {
                intake.stop();
            }

            if (isGating)
            {
                double velo = 0.50 * 6000 * 28/60;
                robot.gate.setVelocity(velo);
                //robot.gate.setPower(0.99);
                telemetry.addData("Gate Velocity: ", robot.gate.getVelocity());

            }
            else
            {
                robot.gate.setPower(0);
            }


            if (isReversing)
            {
                intake.reverseIntake(1);

            }
            else
            {
                intake.reverseIntake(0);
            }

            telemetry.update();
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
