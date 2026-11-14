package org.firstinspires.ftc.teamcode.experimentalCode.parts;

import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.configuration.Hardware;

public class controlMethods {
    public Hardware robot;
    public shooter shooter;
    public intake intake;
    public gate gate;
    public ElapsedTime shootTimer = new ElapsedTime();

    // State Flags
    public boolean isShooting = false;
    public boolean isIntaking = false;
    public boolean isGating = false;
    public boolean isReversing = false;
    public boolean isGateReversing = false;
    public boolean timerShot = false;

    public controlMethods(Hardware hardware) {
        this.robot = hardware;
        this.shooter = new shooter(hardware);
        this.intake = new intake(hardware);
        this.gate = new gate(hardware);
    }

    public void updateSubsystems() {
        if (isShooting) {
            shooter.setVelocity(1700);
        } else if (!timerShot) {
            shooter.stop();
        }

        if (isIntaking) {
            intake.startIntake(430);
        } else {
            intake.stop();
        }

        if (isGating) {
            gate.setVelocity(430);
        } else if (!isGateReversing) {
            robot.gate.setPower(0);
        }

        if (isReversing) {
            intake.reverseIntake(1);
        } else if (!isIntaking) {
            intake.reverseIntake(0);
        }

        if (isGateReversing) {
            robot.gate.setPower(-1);
        } else if (!isGating) {
            robot.gate.setVelocity(0);
        }

        if (timerShot) {
            timedShooter();
        }
    }

    public void timedShooter() {
        robot.shooter.setVelocity(1700);

        if (shootTimer.seconds() < 3.0) {
            robot.gate.setVelocity(0);
        } else if (shootTimer.seconds() > 3.0 && shootTimer.seconds() < 4.5) {
            robot.gate.setVelocity(430);
        } else if (shootTimer.seconds() > 4.5 && shootTimer.seconds() <= 5.5) {
            robot.gate.setVelocity(0);
        } else if (shootTimer.seconds() > 5.5 && shootTimer.seconds() <= 10.0) {
            robot.shooter.setVelocity(1450);
            robot.intake.setVelocity(430);
            robot.gate.setVelocity(430);
        } else {
            stopAll();
        }
    }

    public void stopAll() {
        robot.shooter.setVelocity(0);
        robot.intake.setVelocity(0);
        robot.gate.setVelocity(0);

        isShooting = false;
        isIntaking = false;
        isGating = false;
        isGateReversing = false;
        isReversing = false;
        timerShot = false;
    }

    public void shootAll() {
        robot.shooter.setVelocity(1700);
        robot.intake.setVelocity(430);
        robot.gate.setVelocity(430);
    }

    public void drive(double forward, double right, double rotate, float rightTrigger) {
        double frontLeftPower = forward + right + rotate;
        double frontRightPower = forward - right - rotate;
        double backRightPower = forward + right - rotate;
        double backLeftPower = forward - right + rotate;

        double max = Math.max(Math.abs(frontRightPower),
                Math.max(Math.abs(backLeftPower),
                        Math.max(Math.abs(frontLeftPower),
                                Math.abs(backRightPower))));

        double scaleFactor = (max > 1) ? (1.0 / max) : 1.0;
        scaleFactor *= Math.max(Math.abs(1 - rightTrigger), 0.3);

        robot.setPower(frontRightPower * scaleFactor, backRightPower * scaleFactor, backLeftPower * scaleFactor, frontLeftPower * scaleFactor);
    }
}
