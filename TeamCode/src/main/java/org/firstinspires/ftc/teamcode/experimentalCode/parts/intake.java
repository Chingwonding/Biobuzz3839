package org.firstinspires.ftc.teamcode.experimentalCode.parts;
import org.firstinspires.ftc.teamcode.configuration.Hardware;

public class intake {
    private Hardware hardware;
    private double defaultVelocity = 430;

    public intake() {}

    public intake(Hardware hardware) {
        this.hardware = hardware;
    }



    // --- Speed Adjustment Methods ---

    public void setDefaultPower(double velocity) {
        this.defaultVelocity = velocity;
    }

    public double getDefaultPower() {
        return defaultVelocity;
    }

    // --- Intake Actions ---
    public void startIntake() {
        startIntake(defaultVelocity);
    }
    public void startIntake(double velocity) {
        if (hardware != null && hardware.intake != null) {
            hardware.intake.setVelocity(velocity);
        }
    }

    public void reverseIntake() {
        reverseIntake(defaultVelocity);
    }

    public void reverseIntake(double velocity) {
        if (hardware != null && hardware.intake != null) {
            hardware.intake.setVelocity(-velocity);
        }
    }
    public void stop() {
        if (hardware != null && hardware.intake != null) {
            hardware.intake.setVelocity(0);
        }
    }
}
