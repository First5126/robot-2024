package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Volts;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.SignalLogger;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.Follower;

import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Constants;

public class SystemIdentification extends SubsystemBase {
    private final TalonFX m_TalonLeft = new TalonFX(Constants.TALON_FX_ID_LEFT, Constants.CANBUS);
    private final TalonFX m_TalonRight = new TalonFX(Constants.TALON_FX_ID_RIGHT, Constants.CANBUS);

    private final VoltageOut m_sysidControl = new VoltageOut(0);

    //private ForwardQuasistatic m_ForwardQuasistatic = new ForwardQuasistatic();

    private SysIdRoutine m_SysIdRoutine =
        new SysIdRoutine(
            new SysIdRoutine.Config(
                null,         // Default ramp rate is acceptable
                Volts.of(4), // Reduce dynamic voltage to 4 to prevent motor brownout
                null,          // Default timeout is acceptable
                                       // Log state with Phoenix SignalLogger class
                (state)->SignalLogger.writeString("state", state.toString())),
            new SysIdRoutine.Mechanism(
                (Measure<Voltage> volts)-> m_TalonLeft.setControl(m_sysidControl.withOutput(volts.in(Volts))),
                null,
                this));

    public SystemIdentification() {
        setName("Flywheel");

        m_TalonRight.setControl(new Follower(m_TalonLeft.getDeviceID(), false));

        TalonFXConfiguration cfg = new TalonFXConfiguration();
        m_TalonLeft.getConfigurator().apply(cfg);

        /* Speed up signals for better charaterization data */
        BaseStatusSignal.setUpdateFrequencyForAll(250,
            m_TalonLeft.getPosition(),
            m_TalonLeft.getVelocity(),
            m_TalonLeft.getMotorVoltage());

        /* Optimize out the other signals, since they're not particularly helpful for us */
        m_TalonLeft.optimizeBusUtilization();

        SignalLogger.start();
    }
    public Command sysIdQuasistatic(SysIdRoutine.Direction direction) {
        return m_SysIdRoutine.quasistatic(direction);
    }
    public Command sysIdDynamic(SysIdRoutine.Direction direction) {
        return m_SysIdRoutine.dynamic(direction);
    }
}
