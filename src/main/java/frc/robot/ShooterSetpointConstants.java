package frc.robot;

public class ShooterSetpointConstants {
    private final static ShooterSetpoint[] setpoints = {
        new ShooterSetpoint(0,16,0.8,58.5), //subwoofer
        new ShooterSetpoint(0, 59, 0.8, 16), //AMP
        new ShooterSetpoint(1,28,0.8,78), //podium
        new ShooterSetpoint(2, 34, 1, 94), //wing 
        new ShooterSetpoint(3, 48, 0, 94)  //bank
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
