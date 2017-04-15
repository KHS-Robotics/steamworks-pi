package org.usfirst.frc.team4342.vision.steamworks;

import org.usfirst.frc.team4342.vision.DemonVision;
import org.usfirst.frc.team4342.vision.api.cameras.Camera;
import org.usfirst.frc.team4342.vision.api.cameras.USBCamera;
import org.usfirst.frc.team4342.vision.api.listeners.Listener;
import org.usfirst.frc.team4342.vision.api.pipelines.parameters.Blur;
import org.usfirst.frc.team4342.vision.api.pipelines.parameters.PipelineParameters;
import org.usfirst.frc.team4342.vision.api.pipelines.parameters.RGBBounds;
import org.usfirst.frc.team4342.vision.api.pipelines.parameters.Resolution;

/**
 * Main class
 */
public class Main  {
	// Network Tables
	private static final int TEAM_NUMBER = 4342;
	
	// Microsoft LifeCam HD 3000
	private static final int USB_PORT = 0;
	private static final double FIELD_OF_VIEW = 68.5;
	
	// 360x240, Gaussian blur, and keep green targets
	private static final Resolution RESOLUTION = new Resolution(360, 240);
	private static final Blur BLUR = new Blur(Blur.Type.GAUSSIAN, 1.88);
	private static final RGBBounds RGB = new RGBBounds(0, 70, 84, 255, 0, 45);
	
	/**
	 * The main entry point
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		// Camera
		Camera camera = new USBCamera(USB_PORT, FIELD_OF_VIEW);
		
		// Pipeline Parameters
		PipelineParameters parameters = new PipelineParameters(RESOLUTION, BLUR, RGB);
		
		// Listener
		Listener listener = new SteamworksListener(camera);
		
		// DemonVision
		DemonVision.setTeamNumber(TEAM_NUMBER);
		DemonVision dv = new DemonVision(camera, parameters, listener);
		
		// Let's do this
		dv.runForever();
	}
}