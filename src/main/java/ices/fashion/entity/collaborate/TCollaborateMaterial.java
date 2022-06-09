package ices.fashion.entity.collaborate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TCollaborateMaterial {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String fileName;
    private String category;
    private Integer deleted;
}
