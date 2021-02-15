package cn.org.chtf.card.manage.person.pojo;

public class Person {
    private Integer personId;

    private Integer personOrder;

    private String personName;

    private String personPinyin;

    private Integer menuId;

    private String personIco;

    private String personPosition;

    private String personProfession;

    private String personQualifications;

    private String personWorkplace;

    private String personLanguage;

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getPersonOrder() {
        return personOrder;
    }

    public void setPersonOrder(Integer personOrder) {
        this.personOrder = personOrder;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName == null ? null : personName.trim();
    }

    public String getPersonPinyin() {
        return personPinyin;
    }

    public void setPersonPinyin(String personPinyin) {
        this.personPinyin = personPinyin == null ? null : personPinyin.trim();
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getPersonIco() {
        return personIco;
    }

    public void setPersonIco(String personIco) {
        this.personIco = personIco == null ? null : personIco.trim();
    }

    public String getPersonPosition() {
        return personPosition;
    }

    public void setPersonPosition(String personPosition) {
        this.personPosition = personPosition == null ? null : personPosition.trim();
    }

    public String getPersonProfession() {
        return personProfession;
    }

    public void setPersonProfession(String personProfession) {
        this.personProfession = personProfession == null ? null : personProfession.trim();
    }

    public String getPersonQualifications() {
        return personQualifications;
    }

    public void setPersonQualifications(String personQualifications) {
        this.personQualifications = personQualifications == null ? null : personQualifications.trim();
    }

    public String getPersonWorkplace() {
        return personWorkplace;
    }

    public void setPersonWorkplace(String personWorkplace) {
        this.personWorkplace = personWorkplace == null ? null : personWorkplace.trim();
    }

    public String getPersonLanguage() {
        return personLanguage;
    }

    public void setPersonLanguage(String personLanguage) {
        this.personLanguage = personLanguage == null ? null : personLanguage.trim();
    }
}