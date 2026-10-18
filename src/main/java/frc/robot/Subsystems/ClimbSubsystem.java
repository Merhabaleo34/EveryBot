package frc.robot.Subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimbSubsystem extends SubsystemBase{
    SparkMax climbMotor;

    public ClimbSubsystem(){
        climbMotor = new SparkMax(8, MotorType.kBrushless);

        SparkMaxConfig climbConfig = new SparkMaxConfig();

        climbConfig.smartCurrentLimit(40);
        climbConfig.idleMode(IdleMode.kBrake);

        climbMotor.configure(climbConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void set(double power){
        climbMotor.setVoltage(12*power);
    }

    public void stop(){
        climbMotor.set(0);
    }

    public Command runClimbCommand() {
        return this.runEnd(() -> this.set(0.8), this::stop);
    }

    public Command runClimbDownCommand() {
        return this.runEnd(() -> this.set(-0.8), this::stop);
    }
}
