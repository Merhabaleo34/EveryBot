package frc.robot.Subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FuelSubsystem extends SubsystemBase{
    SparkMax leftMotor;
    SparkMax rightMotor;
    SparkMax indexer;

    public FuelSubsystem(){
        leftMotor = new SparkMax(5,MotorType.kBrushless);
        rightMotor = new SparkMax(6, MotorType.kBrushless);
        indexer = new SparkMax(7, MotorType.kBrushless);

        SparkMaxConfig leftMotorConfig = new SparkMaxConfig();
        SparkMaxConfig rightMotorConfig = new SparkMaxConfig();
        SparkMaxConfig indexerConfig = new SparkMaxConfig();

        leftMotorConfig.smartCurrentLimit(80);
        rightMotorConfig.smartCurrentLimit(80);
        indexerConfig.smartCurrentLimit(80);

        leftMotorConfig.inverted(true);

        leftMotorConfig.idleMode(IdleMode.kCoast);
        rightMotorConfig.idleMode(IdleMode.kCoast);
        indexerConfig.idleMode(IdleMode.kCoast);

        leftMotor.configure(leftMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        rightMotor.configure(rightMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        indexer.configure(indexerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void setIntakeLaunch(double power){
        leftMotor.setVoltage(12*power);
        rightMotor.setVoltage(12*power);
    }

    public void setIndexer(double power){
        indexer.setVoltage(12*power);
    }
    
    public void stop(){
        leftMotor.set(0);
        rightMotor.set(0);
        indexer.set(0);
    }

    public Command runIntakeCommand(){
        return this.startEnd(
                () -> {this.setIntakeLaunch(0.6);
                        this.setIndexer(-0.8);},

                this::stop);
    }

    public Command runLaunchCommand(){
        return this.run(
                () -> {this.setIntakeLaunch(-0.8);
                        this.setIndexer(0.6);});
    }

    public Command runEjectCommand(){
                return this.startEnd(
                () -> {this.setIntakeLaunch(0.6);
                        this.setIndexer(-0.8);},

                this::stop);
    }
}
