package ind.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.annotation.Generated;
import java.util.List;

/**
 * @author BJQXDN0626
 * @create 2018/3/13
 */
@Document(indexName = "song_ci_idx1")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ci {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String oCode;

    private String author;

    private List<String> paragraphs;

    private String rhythmic;//  xingshi

    private List<String> tags;

    @Field(type = FieldType.Keyword)
    private String fromFileIndex;

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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getFromFileIndex() {
        return fromFileIndex;
    }

    public void setFromFileIndex(String fromFileIndex) {
        this.fromFileIndex = fromFileIndex;
    }

    public String getoCode() {
        return oCode;
    }

    public void setoCode(String oCode) {
        this.oCode = oCode;
    }
}
