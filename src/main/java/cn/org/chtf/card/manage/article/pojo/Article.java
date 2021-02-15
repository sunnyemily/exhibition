package cn.org.chtf.card.manage.article.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Article {
    private Integer articleId;

    private Integer menuId;

    private String articleTitle;

    private String articleSource;

    private String articleAuthor;

    private String articleFile;

    private String articlePicture;
	
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date articleUpdatetime;

    private Integer articleOrder;

    private String articleOperator;

    private Byte articleIsHome;

    private Integer exhibitionId;

    private String articleContent;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle == null ? null : articleTitle.trim();
    }

    public String getArticleSource() {
        return articleSource;
    }

    public void setArticleSource(String articleSource) {
        this.articleSource = articleSource == null ? null : articleSource.trim();
    }

    public String getArticleAuthor() {
        return articleAuthor;
    }

    public void setArticleAuthor(String articleAuthor) {
        this.articleAuthor = articleAuthor == null ? null : articleAuthor.trim();
    }

    public String getArticleFile() {
        return articleFile;
    }

    public void setArticleFile(String articleFile) {
        this.articleFile = articleFile == null ? null : articleFile.trim();
    }

    public String getArticlePicture() {
        return articlePicture;
    }

    public void setArticlePicture(String articlePicture) {
        this.articlePicture = articlePicture == null ? null : articlePicture.trim();
    }

    public Date getArticleUpdatetime() {
        return articleUpdatetime;
    }

    public void setArticleUpdatetime(Date articleUpdatetime) {
        this.articleUpdatetime = articleUpdatetime;
    }

    public Integer getArticleOrder() {
        return articleOrder;
    }

    public void setArticleOrder(Integer articleOrder) {
        this.articleOrder = articleOrder;
    }

    public String getArticleOperator() {
        return articleOperator;
    }

    public void setArticleOperator(String articleOperator) {
        this.articleOperator = articleOperator == null ? null : articleOperator.trim();
    }

    public Byte getArticleIsHome() {
        return articleIsHome;
    }

    public void setArticleIsHome(Byte articleIsHome) {
        this.articleIsHome = articleIsHome;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent == null ? null : articleContent.trim();
    }

	public Integer getExhibitionId() {
		return exhibitionId;
	}

	public void setExhibitionId(Integer exhibitionId) {
		this.exhibitionId = exhibitionId;
	}
}