package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.configuration.Hardware;
import org.firstinspires.ftc.teamcode.experimentalCode.parts.controlMethods;

@TeleOp(name = "Vanilla Teleop")
public class Vanilla extends LinearOpMode {

    private Hardware robot = new Hardware();
    private controlMethods controls;

    private boolean lastXState = false;
    private boolean lastAState = false;
    private boolean lastGateState = false;
    private boolean lastReverseState = false;
    private boolean lastReverseGateState = false;
    private boolean lastCircleState = false;

    @Override
    public void runOpMode() {
        robot.initialization(hardwareMap);
        controls = new controlMethods(robot);

        waitForStart();

        while (opModeIsActive()) {
            // 1. Drive Controls
            controls.drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_trigger);

            // 2. Button Toggle Controls
            if (gamepad1.right_trigger > 0.2 && !lastXState) {
                controls.isShooting = !controls.isShooting;
            }
            lastXState = gamepad1.right_trigger_pressed;

            if (gamepad1.right_bumper && !lastAState) {
                controls.isIntaking = !controls.isIntaking;
            }
            lastAState = gamepad1.right_bumper;

            if (gamepad1.x && !lastGateState) {
                controls.isGating = !controls.isGating;
                controls.isGateReversing = false;
            }
            lastGateState = gamepad1.x;

            if (gamepad1.left_bumper && !lastReverseState) {
                controls.isReversing = !controls.isReversing;
            }
            lastReverseState = gamepad1.left_bumper;

            if (gamepad1.square && !lastReverseGateState) {
                controls.isGateReversing = !controls.isGateReversing;
                controls.isReversing = false;
            }
            lastReverseGateState = gamepad1.square;

            if (gamepad1.triangle) {
                controls.stopAll();
            }

            if (gamepad1.circle && !lastCircleState) {
                controls.timerShot = !controls.timerShot;
                if (controls.timerShot) {
                    controls.shootTimer.reset();
                }
            }
            lastCircleState = gamepad1.circle;

            // 3. Update Subsystems
            controls.updateSubsystems();

            // 4. Telemetry Outputs
            telemetry.addData("Shooter Velocity", robot.shooter.getVelocity());
            telemetry.addData("Gate Velocity", robot.gate.getVelocity());
            telemetry.update();
        }
    }
}
