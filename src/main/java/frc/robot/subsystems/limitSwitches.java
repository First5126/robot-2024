package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class limitSwitches {
    public DigitalInput limitSwitch = new DigitalInput(0);

    public limitSwitches(){}

    public boolean getOut(){
        boolean out = limitSwitch.get();
        SmartDashboard.putBoolean("Limit Swich Output", out);
        return out;
    }
}
