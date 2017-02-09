package communiaction;

public class DataTransfer {
	
	private static DataTransfer dataTransfer;
	
	
	private DataTransfer() {
		
	}
	
	public static DataTransfer getInstance() {
		if (dataTransfer == null) {
			dataTransfer = new DataTransfer();
		}
		
		return dataTransfer;
	}

}
