package cn.org.chtf.card.manage.basicsetting.pojo;

public class BasicSetting {
    private Integer bsId;

    private String bsSitename;

    private String bsKeyword;

    private String bsDescription;

    private String bsDomain;

    private String bsAddress;

    private String bsTel;

    private String bsIcp;

    private String bsGaNum;

    private String bsEamil;

    private String bsMailEmail;

    private String bsMailPassword;

    private String bsPop3;

    private String bsSmtp;

    private String bsImap;
    
    private String bsMusic;

    public Integer getBsId() {
        return bsId;
    }

    public void setBsId(Integer bsId) {
        this.bsId = bsId;
    }

    public String getBsSitename() {
        return bsSitename;
    }

    public void setBsSitename(String bsSitename) {
        this.bsSitename = bsSitename == null ? null : bsSitename.trim();
    }

    public String getBsKeyword() {
        return bsKeyword;
    }

    public void setBsKeyword(String bsKeyword) {
        this.bsKeyword = bsKeyword == null ? null : bsKeyword.trim();
    }

    public String getBsDescription() {
        return bsDescription;
    }

    public void setBsDescription(String bsDescription) {
        this.bsDescription = bsDescription == null ? null : bsDescription.trim();
    }

    public String getBsDomain() {
        return bsDomain;
    }

    public void setBsDomain(String bsDomain) {
        this.bsDomain = bsDomain == null ? null : bsDomain.trim();
    }

    public String getBsAddress() {
        return bsAddress;
    }

    public void setBsAddress(String bsAddress) {
        this.bsAddress = bsAddress == null ? null : bsAddress.trim();
    }

    public String getBsTel() {
        return bsTel;
    }

    public void setBsTel(String bsTel) {
        this.bsTel = bsTel == null ? null : bsTel.trim();
    }

    public String getBsIcp() {
        return bsIcp;
    }

    public void setBsIcp(String bsIcp) {
        this.bsIcp = bsIcp == null ? null : bsIcp.trim();
    }

    public String getBsGaNum() {
        return bsGaNum;
    }

    public void setBsGaNum(String bsGaNum) {
        this.bsGaNum = bsGaNum == null ? null : bsGaNum.trim();
    }

    public String getBsEamil() {
        return bsEamil;
    }

    public void setBsEamil(String bsEamil) {
        this.bsEamil = bsEamil == null ? null : bsEamil.trim();
    }

    public String getBsMailEmail() {
        return bsMailEmail;
    }

    public void setBsMailEmail(String bsMailEmail) {
        this.bsMailEmail = bsMailEmail == null ? null : bsMailEmail.trim();
    }

    public String getBsMailPassword() {
        return bsMailPassword;
    }

    public void setBsMailPassword(String bsMailPassword) {
        this.bsMailPassword = bsMailPassword == null ? null : bsMailPassword.trim();
    }

    public String getBsPop3() {
        return bsPop3;
    }

    public void setBsPop3(String bsPop3) {
        this.bsPop3 = bsPop3 == null ? null : bsPop3.trim();
    }

    public String getBsSmtp() {
        return bsSmtp;
    }

    public void setBsSmtp(String bsSmtp) {
        this.bsSmtp = bsSmtp == null ? null : bsSmtp.trim();
    }

    public String getBsImap() {
        return bsImap;
    }

    public void setBsImap(String bsImap) {
        this.bsImap = bsImap == null ? null : bsImap.trim();
    }

	public String getBsMusic() {
		return bsMusic;
	}

	public void setBsMusic(String bsMusic) {
		this.bsMusic = bsMusic;
	}
}