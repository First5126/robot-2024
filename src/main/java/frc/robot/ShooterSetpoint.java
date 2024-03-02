package frc.robot;

public class ShooterSetpoint {

    private double Distance;
    private double ArmEncoderPosition;
    private double IntakeSpeed;
    private double ShooterVelo;


    public ShooterSetpoint(double Distance, double ArmEncoderPosition, double IntakeSpeed, double ShooterVelo){
        
        this.Distance = Distance;
        this.ArmEncoderPosition = ArmEncoderPosition;
        this.IntakeSpeed = IntakeSpeed;
        this.ShooterVelo = ShooterVelo;
    }

    public double getDistance() {
        return Distance;
    }

    public void setDistance(double distance) {
        Distance = distance;
    }

    public double getArmEncoderPosition() {
        return ArmEncoderPosition;
    }

    public void setArmEncoderPosition(double armEncoderPosition) {
        ArmEncoderPosition = armEncoderPosition;
    }

    public double getIntakeSpeed() {
        return IntakeSpeed;
    }

    public void setIntakeSpeed(double intakeSpeed) {
        IntakeSpeed = intakeSpeed;
    }

    public double getShooterVelo() {
        return ShooterVelo;
    }

    public void setShooterVelo(double shooterVelo) {
        ShooterVelo = shooterVelo;
    }
}
