package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.configuration.Hardware;

@Disabled
@Autonomous(name = "meet1 AUTO placeholder")
public class StraightLineAuto extends LinearOpMode {

    // This will be the auto we use for the competition
    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {
        // Initialize hardware
        robot.initialization(hardwareMap);

        telemetry.addData("Status", "Hello Drivers");
        telemetry.update();

        waitForStart();

        // Example autonomous sequence:
        move(7.5, 0.67);
        // shot(1700);
    }

    public void move(double distance, double speed) {
        double wheelCircumference = 4 * Math.PI;
        double motorTicksPerRev = 560;
        double ticks = (distance * (motorTicksPerRev / wheelCircumference));

        robot.frontRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontRightWheel.setTargetPosition((int) Math.round(ticks));
        robot.frontLeftWheel.setTargetPosition((int) Math.round(ticks));
        robot.backRightWheel.setTargetPosition((int) Math.round(ticks));
        robot.backLeftWheel.setTargetPosition((int) Math.round(ticks));

        robot.frontRightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontLeftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.setPower(speed, speed, speed, speed);

        while (opModeIsActive() && robot.frontLeftWheel.isBusy()) {
            telemetry.addData("Target Ticks", (int) Math.round(ticks));
            telemetry.addData("FL Pos", robot.frontLeftWheel.getCurrentPosition());
            telemetry.update();
        }

        robot.setPower(0, 0, 0, 0);

        robot.frontRightWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontLeftWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void turning(int ticks, double speed) {
        robot.frontRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Account for hardware motor direction configuration:
        // Positive ticks = turn right (left wheels +, right wheels -)
        // Negative ticks = turn left (left wheels -, right wheels +)
        robot.frontLeftWheel.setTargetPosition(ticks);
        robot.backLeftWheel.setTargetPosition(ticks);
        robot.frontRightWheel.setTargetPosition(-ticks);
        robot.backRightWheel.setTargetPosition(-ticks);

        robot.frontRightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontLeftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.setPower(speed, speed, speed, speed);

        while (opModeIsActive() && (robot.frontLeftWheel.isBusy() || robot.frontRightWheel.isBusy())) {
            telemetry.addData("Turning Ticks", ticks);
            telemetry.update();
        }

        robot.setPower(0, 0, 0, 0);

        robot.frontRightWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontLeftWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void shot(double velocity) {
        shot(velocity, 3.0);
    }

    public void shot(double velocity, double durationSeconds) {
        robot.shooter.setVelocity(velocity);
        sleep(3000); // Spin-up time

        robot.gate.setVelocity(430);
        robot.intake.setVelocity(430);
        sleep((long) (durationSeconds * 1000));

        robot.shooter.setVelocity(0);
        robot.gate.setVelocity(0);
        robot.intake.setVelocity(0);
    }
}
