package frc.robot.subsystems;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class robotArm extends SubsystemBase {
    private CANSparkMax sparkMax;
    private double ArminSpeed;
    public robotArm(){
        sparkMax = new CANSparkMax(5, MotorType.kBrushless);
            sparkMax.setInverted(true);
    }
    public void enableClearJam(){
        sparkMax.set(0.2);
        sparkMax.setInverted(false);
    }
    public void disableClearJam(){
        sparkMax.set(0);
        sparkMax.setInverted(true);
    }
}
