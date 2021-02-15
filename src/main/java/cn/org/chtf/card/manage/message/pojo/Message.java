package cn.org.chtf.card.manage.message.pojo;

import java.util.Date;

public class Message {
    private Integer messageId;

    private Integer menuId;

    private String messageCompanyName;

    private String messageAddress;

    private String messageContactor;

    private String messagePosition;

    private String messageTel;

    private String messageEmail;

    private Date messageAddtime;

    private String messageContent;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMessageCompanyName() {
        return messageCompanyName;
    }

    public void setMessageCompanyName(String messageCompanyName) {
        this.messageCompanyName = messageCompanyName == null ? null : messageCompanyName.trim();
    }

    public String getMessageAddress() {
        return messageAddress;
    }

    public void setMessageAddress(String messageAddress) {
        this.messageAddress = messageAddress == null ? null : messageAddress.trim();
    }

    public String getMessageContactor() {
        return messageContactor;
    }

    public void setMessageContactor(String messageContactor) {
        this.messageContactor = messageContactor == null ? null : messageContactor.trim();
    }

    public String getMessagePosition() {
        return messagePosition;
    }

    public void setMessagePosition(String messagePosition) {
        this.messagePosition = messagePosition == null ? null : messagePosition.trim();
    }

    public String getMessageTel() {
        return messageTel;
    }

    public void setMessageTel(String messageTel) {
        this.messageTel = messageTel == null ? null : messageTel.trim();
    }

    public String getMessageEmail() {
        return messageEmail;
    }

    public void setMessageEmail(String messageEmail) {
        this.messageEmail = messageEmail == null ? null : messageEmail.trim();
    }

    public Date getMessageAddtime() {
        return messageAddtime;
    }

    public void setMessageAddtime(Date messageAddtime) {
        this.messageAddtime = messageAddtime;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent == null ? null : messageContent.trim();
    }
}