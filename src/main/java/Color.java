import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Color {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
