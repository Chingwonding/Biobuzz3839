package org.firstinspires.ftc.teamcode.experimentalCode.parts;
import org.firstinspires.ftc.teamcode.configuration.Hardware;

public class intake {
    private Hardware hardware;
    private double defaultPower = 1.0;

    public intake() {}

    public intake(Hardware hardware) {
        this.hardware = hardware;
    }



    // --- Speed Adjustment Methods ---

    public void setDefaultPower(double power) {
        this.defaultPower = power;
    }

    public double getDefaultPower() {
        return defaultPower;
    }

    // --- Intake Actions ---

    /**
     * Starts intaking at default power.
     */
    public void startIntake() {
        startIntake(defaultPower);
    }

    /**
     * Starts intaking at specified power.
     */
    public void startIntake(double power) {
        if (hardware != null && hardware.intake != null) {
            hardware.intake.setPower(power);
        }
    }

    /**
     * Reverses the intake (outtake) at default power.
     */
    public void reverseIntake() {
        reverseIntake(defaultPower);
    }

    /**
     * Reverses the intake at specified power.
     */
    public void reverseIntake(double power) {
        if (hardware != null && hardware.intake != null) {
            hardware.intake.setPower(-power);
        }
    }

    /**
     * Stops the intake motor.
     */
    public void stop() {
        if (hardware != null && hardware.intake != null) {
            hardware.intake.setPower(0);
        }
    }
}
