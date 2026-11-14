package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.configuration.Hardware;

@Autonomous(name = "Close leave and Park")
public class closeStraightAuto extends LinearOpMode {


    private static final double WHEEL_DIAMETER_INCHES = 4.094;
    //change based on whether its 435 or 312 RPM motors CHECK TOMORROW
    private static final double DRIVE_TICKS_PER_REVOLUTION = 560.0;
    private static final double MOVE_TIMEOUT_SECONDS = 4.0;

    // This will be the auto we use for the competition
    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {
        // Initialize hardware
        robot.initialization(hardwareMap);

        telemetry.addData("Status", "Hello Drivers");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) {
            return;
        }
        driveStraight(0.70, 20, 0);
        turning(-700, 0.50);
        driveStraight(0.70, 48, 0);
    }
    public void driveStraight(double speed, double distance, double heading) {
        double wheelCircumference = WHEEL_DIAMETER_INCHES * Math.PI;
        double ticks = distance * (DRIVE_TICKS_PER_REVOLUTION / wheelCircumference);
        ElapsedTime moveTimer = new ElapsedTime();

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

        moveTimer.reset();
        while (opModeIsActive()
                && moveTimer.seconds() < MOVE_TIMEOUT_SECONDS
                && (robot.frontLeftWheel.isBusy()
                || robot.frontRightWheel.isBusy()
                || robot.backLeftWheel.isBusy()
                || robot.backRightWheel.isBusy())) {
            telemetry.addData("Target Ticks", (int) Math.round(ticks));
            telemetry.addData("Drive Positions", "FL %d FR %d BL %d BR %d",
                    robot.frontLeftWheel.getCurrentPosition(),
                    robot.frontRightWheel.getCurrentPosition(),
                    robot.backLeftWheel.getCurrentPosition(),
                    robot.backRightWheel.getCurrentPosition());
            telemetry.update();
            idle();
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

        robot.frontRightWheel.setTargetPosition(ticks);
        robot.backRightWheel.setTargetPosition(ticks);
        robot.frontLeftWheel.setTargetPosition(-ticks);
        robot.backLeftWheel.setTargetPosition(-ticks);

        robot.frontRightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontLeftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // RUN_TO_POSITION gets direction from each target; power is a magnitude.
        double turnPower = Math.abs(speed);
        robot.setPower(turnPower, turnPower, turnPower, turnPower);

        while (opModeIsActive()
                && (robot.frontLeftWheel.isBusy()
                || robot.frontRightWheel.isBusy()
                || robot.backLeftWheel.isBusy()
                || robot.backRightWheel.isBusy())) {
            telemetry.addData("Turn Target", "%d ticks", ticks);
            telemetry.addData("Turn Positions", "FL %d FR %d BL %d BR %d",
                    robot.frontLeftWheel.getCurrentPosition(),
                    robot.frontRightWheel.getCurrentPosition(),
                    robot.backLeftWheel.getCurrentPosition(),
                    robot.backRightWheel.getCurrentPosition());
            telemetry.update();
            idle();
        }

        robot.setPower(0, 0, 0, 0);

        robot.frontRightWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontLeftWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


}