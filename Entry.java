
public class Entry{
	public static Bot_Settings_Class Running_Settings;
	
	public static void main(String[] args)
	{	
		Running_Settings = new Bot_Settings_Class();
		UI.Initialize_Status_Window();
		if(Running_Settings.Capture_Zone == null){UI.Calibrate_Window(true);}
		else{new Thread(new Fish_Bot()).start();}
	}
}
