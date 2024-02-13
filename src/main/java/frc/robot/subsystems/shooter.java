package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.OperatorConstants;

public class shooter extends SubsystemBase{
    private TalonFX talonFXOne;
    private TalonFX talonFXTwo;

    private CANSparkMax sparkMax;

    private double outtakeSpeed;
    private double intakeSpeed;

    private double ampOuttakeSpeed;

    private double slowIntakeSpeed;

    public DigitalInput StopSensor;
    public DigitalInput SlowSensor;

    public boolean rumbling;

    public Timer timer = new Timer();

    private final GenericHID m_driverController = new GenericHID(OperatorConstants.kDriverControllerPort);

    public shooter(){
        // Sets up the two sensors, telling them what channel to pull from
        StopSensor = new DigitalInput(0);
        SlowSensor = new DigitalInput(1);

        // Sets up the two TalonFX's
        talonFXOne = new TalonFX(5);
        talonFXTwo = new TalonFX(6);

        // Sets sparkMAX to be a new CANSparkMax at device ID of 5 with the type of motor being brushless
        sparkMax = new CANSparkMax(5, MotorType.kBrushless);
    }
    
    @Override
    public void periodic(){
        // Gets motor speed
        outtakeSpeed = SmartDashboard.getNumber("Outtake Speed", 0.75);
        intakeSpeed = SmartDashboard.getNumber("Intake speed", 0.6);

        slowIntakeSpeed = SmartDashboard.getNumber("Slowed intake speed", 0.15);

        ampOuttakeSpeed = SmartDashboard.getNumber("Amp outtake speed", 0.2);

        if(rumbling){
            if (timer.hasElapsed(1)){
                timer.stop();
                timer.reset();
                m_driverController.setRumble(RumbleType.kBothRumble, 0);
            }
        }
    }

    public void enableShooter() {
        talonFXOne.set(outtakeSpeed);

        talonFXTwo.set(outtakeSpeed);
        talonFXTwo.setInverted(true);

        sparkMax.set(outtakeSpeed);
    }
    public void disableShooter() {
        talonFXOne.set(0);
        talonFXTwo.set(0);

        sparkMax.set(0);
    }
    public void enableIntake() {
        sparkMax.set(intakeSpeed);
    }
    public void enableIntakeSlow() {
        sparkMax.set(slowIntakeSpeed);
    }
    public void clearJam(){
        sparkMax.set(0.2);
        sparkMax.setInverted(true);
        
        talonFXOne.set(0.2);
        talonFXOne.setInverted(true);

        talonFXTwo.set(0.2);
        talonFXTwo.setInverted(false);
    }
    public void DisableClearJam(){
        sparkMax.set(0);
        sparkMax.setInverted(false);
        
        talonFXOne.set(0);
        talonFXOne.setInverted(false);
        talonFXTwo.set(0);
        talonFXOne.setInverted(true);
    }
    public void disableIntake() {
        sparkMax.set(0);
    }
    public void ampOuttake() {
        talonFXOne.set(ampOuttakeSpeed);
        talonFXTwo.set(ampOuttakeSpeed);
        talonFXTwo.setInverted(true);
    }
}
