package frc.robot;

public class ShooterSetpointConstants {
    private final static ShooterSetpoint[] setpoints = {
        new ShooterSetpoint(0,0,0,0),
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
