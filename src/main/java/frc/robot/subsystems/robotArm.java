package frc.robot.subsystems;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class robotArm extends SubsystemBase {
    private TalonFX talonfxOne;
    private TalonFX talonfxTwo;
    private CANSparkMax sparkMax;
    private double ArmoutSpeed;

    public robotArm(){
        talonfxOne = new TalonFX(5);
        talonfxTwo = new TalonFX(6);
            talonfxTwo.setInverted(true);
        sparkMax = new CANSparkMax(5, MotorType.kBrushless);
            sparkMax.setInverted(true);
    }

    @Override
    public void periodic(){
        ArmoutSpeed = SmartDashboard.getNumber("outtake speed",0.3 );
    }
    public void enableOuttake(){
        sparkMax.set(ArminSpeed);
        talonfxOne.set(ArmoutSpeed);
        talonfxTwo.set(ArmoutSpeed);
    }
    public void disableOuttake(){
        sparkMax.set(0);
        talonfxOne.set(0);
        talonfxTwo.set(0);
    }
    public void enableArmOutAmp(){
        talonfxOne.set(0);
        talonfxTwo.set(0);
        sparkMax.set(0);
    }
    public void disableArmOutAmp(){
        talonfxOne.set(0);
        talonfxTwo.set(0);
        sparkMax.set(0);
    }

}
