package Model;

public class Area {
	private String title;
	private String code;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Area(String title, String code) {
		super();
		this.title = title;
		this.code = code;
	}

}