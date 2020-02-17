import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class ReqresResponse {

    Integer total_pages;
    Color[] data;

    public Integer getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public Color[] getData() {
        return data;
    }

    public void setData(Color[] data) {
        this.data = data;
    }

}


