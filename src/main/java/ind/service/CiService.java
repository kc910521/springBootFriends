package ind.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ind.domains.Ci;
import ind.repository.CiRepository;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author BJQXDN0626
 * @create 2018/3/13
 */
@Service
public class CiService {

    @Autowired
    private CiRepository ciRepository;

    int a = 1;

    public List<Ci> findByParas(String str){
//        List<String> strings = new LinkedList<>();
//        strings.add(str);
        return ciRepository.findAllByParagraphsMatches(str);
    }

    public int loadFromFiles(String absDirPath) throws IOException {
        Collection<File> files = FileUtils.listFiles(new File(absDirPath),
                FileFilterUtils.suffixFileFilter("json"), null);
        if (files != null && !files.isEmpty()){
            files.stream().forEach(file -> {
                if (file.isFile() && file.exists() && file.getName().startsWith("ci.song")){
                    System.out.println(file.getName());
                    ObjectMapper mapper = new ObjectMapper();
                    List<Ci> cis = null;
                    try {
                        cis = mapper.readValue(file, new TypeReference<List<Ci>> (){});

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cis.stream().forEach(ci -> {
                        ci.setId("ci_"+(a++));
                        System.out.println(a);
                    });
                    ciRepository.saveAll(cis);
                }
            });
            return 0;
        }
        return -1;
    }

//    public static void main(String[] args) {
////        CiService ciService = new CiService();
//        Collection<File> files = FileUtils.listFiles(new File("C:\\Users\\BJQXDN0626\\Downloads\\chinese-poetry-master\\chinese-poetry-master\\ci"),
//                FileFilterUtils.suffixFileFilter("json"), null);
//        if (files != null && !files.isEmpty()){
//            files.stream().forEach(file -> {
//                if (file.isFile() && file.getName().startsWith("ci.song")){
//                    System.out.println(file.getName());
//                }
//            });
//        }
//    }

}
