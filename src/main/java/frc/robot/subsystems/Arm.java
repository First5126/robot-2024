package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;

public class Arm extends SubsystemBase {
    private Slot0Configs slot0Configs;
    private final PositionVoltage positionVoltage;
    private final TalonFX FXLeftArm;
    private final TalonFX FXRightArm;
    private final Encoder encoder;
  public Arm() {
        FXLeftArm = new TalonFX(0);
        FXRightArm = new TalonFX(0);
        FXLeftArm.setInverted(true);
        FXRightArm.setInverted(false);
        positionVoltage = new PositionVoltage(0).withSlot(0);
        FXRightArm.getConfigurator().apply(slot0Configs);
        FXLeftArm.getConfigurator().apply(slot0Configs);
        encoder = new Encoder(0, 0);
        slot0Configs = new Slot0Configs();
          slot0Configs.kP = Constants.ArmPID.kP;
          slot0Configs.kI = Constants.ArmPID.kI;
          slot0Configs.kD = Constants.ArmPID.kD;
          slot0Configs.kA = Constants.ArmPID.kA;
          slot0Configs.kS = Constants.ArmPID.kS;
          slot0Configs.kV = Constants.ArmPID.kV;
          slot0Configs.kG = Constants.ArmPID.kG;
    }
    @Override
    public void periodic(){
        SmartDashboard.putNumber("Rev Encoder", encoder.getDistance());
    }
    public void ArmMoveOn(double position){
        FXLeftArm.setControl(positionVoltage.withPosition(position));
        FXRightArm.setControl(new Follower(FXLeftArm.getDeviceID(), false));
   }
   public void ArmMoveOff() {
    FXLeftArm.set(0);
   }
   public void Reset(){
    encoder.reset();
  }
}