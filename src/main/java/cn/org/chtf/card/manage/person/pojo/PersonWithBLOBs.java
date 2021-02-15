package cn.org.chtf.card.manage.person.pojo;

public class PersonWithBLOBs extends Person {
    private String personExperience;

    private String personBackground;

    public String getPersonExperience() {
        return personExperience;
    }

    public void setPersonExperience(String personExperience) {
        this.personExperience = personExperience == null ? null : personExperience.trim();
    }

    public String getPersonBackground() {
        return personBackground;
    }

    public void setPersonBackground(String personBackground) {
        this.personBackground = personBackground == null ? null : personBackground.trim();
    }
}