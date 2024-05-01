package frc.robot;

public class ShooterSetpoint {

    private double Distance;
    private double ArmPosition;
    private double IntakeSpeed;
    private double ShooterVelo;


    public ShooterSetpoint(double Distance, double ArmPosition, double IntakeSpeed, double ShooterVelo){
        
        this.Distance = Distance;
        this.ArmPosition = ArmPosition;
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
        return ArmPosition;
    }

    public void setArmPosition(double armPosition) {
        ArmPosition = armPosition;
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
