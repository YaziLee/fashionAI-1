package ices.fashion.entity.space;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TLike {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String uid;
    private Integer deleted;
    private Integer wid;
}
