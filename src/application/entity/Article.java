package application.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Article {

    private String title;
    private String content;


}
