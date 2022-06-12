package ices.fashion.service.space.dto;

import ices.fashion.entity.space.TComment;
import ices.fashion.entity.space.TWork;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkDetailDto {

    private Integer id;
    private String phone;
    private String userName;
    private String workDescription;
    private String category;
    private String coverUrl;
    private Integer workShared;
    private Timestamp createTime;
    private String title;
    private String wordDescription;
    private List<TComment> commentList;

    private Integer isVisitorShared;
    private Integer shareId;

    public WorkDetailDto(TWork work) {
        this.id = work.getId();
        this.phone = work.getPhone();
        this.userName = work.getUserName();
        this.workDescription = work.getWorkDescription();
        this.category = work.getCategory();
        this.coverUrl = work.getCoverUrl();
        this.workShared = work.getWorkShared();
        this.createTime = work.getCreateTime();
        this.title = work.getTitle();
        this.wordDescription = work.getWordDescription();
    }
}
