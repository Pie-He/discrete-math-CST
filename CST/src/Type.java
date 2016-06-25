public enum Type {
	IMPLY("/imply"), AND("/and"), OR("/or"),NOT("/not"),EQ("/eq");
	private String str;
	Type(String str){
		this.str=str;
	}

	public String getStr(){
		return str;
	}
}
