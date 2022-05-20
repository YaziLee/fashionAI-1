package ices.fashion.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TShare {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String creatorUserName;
    private String creatorPhone;
    private String creatorCoverUrl;
    private Integer wid;
    private String phone;
    private String userName;
    private Integer deleted;
    private String workCategory;

    public TWork share2Work() {
        TWork work = new TWork();
        work.setId(this.wid);
        work.setUserName(this.creatorUserName);
        work.setCoverUrl(this.creatorCoverUrl);
        work.setCategory(this.workCategory);
        return work;
    }
}
