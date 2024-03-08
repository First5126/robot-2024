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

public class shooter extends SubsystemBase{
    private TalonFX FalconLeftShooter;
    private TalonFX FalconRightShooter;

    private CANSparkMax IntakeSpark;

    private double outtakeSpeed;
    private double intakeSpeed;

    private double slowIntakeSpeed;

    private GenericHID m_driverController;

    public DigitalInput StopSensor;
    public DigitalInput SlowSensor;

    public boolean rumbling;

    public Timer timer = new Timer();

    public shooter(GenericHID controller){
        // Sets up the two sensors, telling them what channel to pull from
        StopSensor = new DigitalInput(2);
        SlowSensor = new DigitalInput(3);

        // Sets up the two TalonFX's - DEVICE ID'S MAY NOT BE CORRECT!
        FalconLeftShooter = new TalonFX(11);
        FalconRightShooter = new TalonFX(10);

        // Sets up the IntakeSpark motor controller for the intake - DEVICE ID MAY NOT BE CORRECT!
        // IntakeSpark = new CANSparkMax(5, MotorType.kBrushless);

        m_driverController = controller;
    }
    @Override
    public void periodic(){
        // Gets motor speed - soon to be hardcoded
        outtakeSpeed = SmartDashboard.getNumber("Outtake Speed", 1);
        intakeSpeed = SmartDashboard.getNumber("Intake speed", 0.6);

        slowIntakeSpeed = SmartDashboard.getNumber("Slowed intake speed", 0.15);

        if(rumbling){
            if (timer.hasElapsed(1)){
                timer.stop();
                timer.reset();
                m_driverController.setRumble(RumbleType.kBothRumble, 0);
            }
        }
    }

    public void enableShooter() {
        FalconLeftShooter.set(outtakeSpeed);

        FalconRightShooter.set(outtakeSpeed);
        FalconRightShooter.setInverted(true);

        // IntakeSpark.set(outtakeSpeed);
    }
    public void disableShooter() {
        FalconLeftShooter.set(0);
        FalconRightShooter.set(0);

        //IntakeSpark.set(0);
    }
    public void enableIntake() {
        IntakeSpark.set(intakeSpeed);;
    }
    public void enableIntakeSlow() {
        IntakeSpark.set(slowIntakeSpeed);
    }
    public void disableIntake() {
        IntakeSpark.set(0);
    }
}
