package sink.studio.panel;

public enum AssetType {
	None,
	Font,
	Texture,
	Animation,
	Music,
	Sound,
	Particle,
	
	Button,
	TextButton;
	
	public static String getString(AssetType at){
		switch(at){
			case Font: return "Font";
			case Texture: return "Texture";
			case Animation: return "Animation";
			case Music: return "Music";
			case Sound: return "Sound";
			case Particle: return "Particle";
			
			case Button: return "Button";
			case TextButton: return "TextButton";
			default: return "";
		}
	}
	
	public static AssetType getType(String name){
		switch(name){
			case "Font": return AssetType.Font;
			case "Texture": return AssetType.Texture;
			case "Animation": return AssetType.Animation;
			case "Music": return AssetType.Music;
			case "Sound": return AssetType.Sound;
			case "Particle": return AssetType.Particle;
			
			case "Button": return Button;
			case "TextButton": return TextButton;
			default: return AssetType.None;
		}
	}
}