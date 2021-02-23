import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Rectangle;

public class Bot_Settings_Class
{
	public Color Text_Color_To_Serch_For = new Color(197, 194, 158);
	
	public Color Reelin_Marker_Color_To_Search_For = new Color(255, 0, 0);
	public int Reelin_Marker_RGB_Threshold[] = {20, 20, 20};
	
	public int Frame_Capture_Delay_Min = 10;
	public int Frame_Capture_Delay_Max = 10;
	
	public int Next_Step_Delay_Min = 500;
	public int Next_Step_Delay_Max = 1500;
	
	public int Key_To_Use = KeyEvent.VK_E;
	public int Key_Press_Release_Delay_Min = 100;
	public int Key_Press_Release_Delay_Max = 200;
	
	public int Start_Fishing_To_Reelin_State_Delay_Min = 3500;
	public int Start_Fishing_To_Reelin_State_Delay_Max = 3500;
	
	public int Reelin_To_Start_Fishing_State_Delay_Min = 1500;
	public int Reelin_To_Start_Fishing_State_Delay_Max = 1500;
	
	
	public Rectangle Capture_Zone = null;
	public int Fish_Start_Pixel_Count = 0;
	public int Fish_Reelin_Pixel_Count = 0;
}
