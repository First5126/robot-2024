package frc.robot;

public class ShooterSetpointConstants {
    private final static ShooterSetpoint[] setpoints = {
        new ShooterSetpoint(0,16,0.8,58.48333),
        new ShooterSetpoint(1,1,1,1)
    };

    public static ShooterSetpointConstants getInstance(){
        return new ShooterSetpointConstants();
    }

    public ShooterSetpoint[] getShooterSetPoints(){
        return setpoints;
    }

    public ShooterSetpoint getShooterSetpointElement(int element){
        return setpoints[element];
        
    }
}
