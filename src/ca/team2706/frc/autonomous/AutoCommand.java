package ca.team2706.frc.autonomous;

public interface AutoCommand {
	public void initialize();
	public boolean tick();
	public void cleanup();
}
