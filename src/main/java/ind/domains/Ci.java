package ind.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.annotation.Generated;
import java.util.List;

/**
 * @author BJQXDN0626
 * @create 2018/3/13
 */
@Document(indexName = "ci_song_910521")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ci {

    @Id
    private String id;

    private String author;

    private List<String> paragraphs;

    private String rhythmic;//  xingshi

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<String> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public String getRhythmic() {
        return rhythmic;
    }

    public void setRhythmic(String rhythmic) {
        this.rhythmic = rhythmic;
    }
}
