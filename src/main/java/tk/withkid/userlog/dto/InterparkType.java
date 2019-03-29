package tk.withkid.userlog.dto;

public enum InterparkType {
	Mu("Fam_M"), Pl("Fam_P"), 
	Cl("Fam_C"), Ex("Fam_L");
	
	final private String subCa;
	
	private InterparkType(String subCa) {
		this.subCa = subCa;
	}

	public String getSubCa() {
		return subCa;
	}
}
