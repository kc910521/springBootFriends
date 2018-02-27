package ind.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author BJQXDN0626
 * @create 2018/2/11
 */
@Data
public class LombokTest {

    @NonNull private Integer uid;

    @NonNull private String name;

}
