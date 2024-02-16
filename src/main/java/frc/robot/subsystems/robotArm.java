package frc.robot.subsystems;
import frc.robot.Constants;
import frc.robot.commands.ArmOut;
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
    private double ArminSpeed;
    private double ArmoutSpeed;
    public DigitalInput StopSensor;
    public DigitalInput SlowSensor;
    private double SlowIntake;

    public robotArm(){
        StopSensor = new DigitalInput(3);
        SlowSensor = new DigitalInput(1);
        talonfxOne = new TalonFX(5);
        talonfxTwo = new TalonFX(6);
            talonfxTwo.setInverted(true);
        sparkMax = new CANSparkMax(5, MotorType.kBrushless);
            sparkMax.setInverted(true);
    }

    @Override
    public void periodic(){
        ArminSpeed = SmartDashboard.getNumber("intake speed", 0.5);
        ArmoutSpeed = SmartDashboard.getNumber("outtake speed",0.3 );
        SlowIntake = SmartDashboard.getNumber("slow intake", 0.15);
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

    public void enableIntake(){
        sparkMax.set(ArminSpeed);

    }

    public void disableIntake(){
        sparkMax.set(0);
    }
    
    public void enableIntakeslow(){
        sparkMax.set(SlowIntake);
    }

    public void enableClearJam(){
        sparkMax.set(0.2);
        sparkMax.setInverted(false);

    }

    public void disableClearJam(){
        sparkMax.set(0);
        sparkMax.setInverted(true);

    }

    public void enableArmOutAmp(){

    }

    public void disableArmOutAmp(){
        
    }
    public void enableLimt(){

    }

    public void disableLimt(){
        
    }
}