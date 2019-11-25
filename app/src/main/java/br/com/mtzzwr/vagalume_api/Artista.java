package br.com.mtzzwr.vagalume_api;

public class Artista {

    private Integer id;
    private Integer langId;
    private String url;
    private String title;
    private String band;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLangId() {
        return langId;
    }

    public void setLangId(Integer langId) {
        this.langId = langId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    @Override
    public String toString() {
        return "id: " + getId()
                + " landID: " + getLangId()
                + " url: " + getUrl()
                + " title: " + getTitle()
                + " band: " + getBand();
    }
}
