package ices.fashion.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShareWorkCriteria {
    private Integer id; //要被分享的work id
    private String title;
    private String wordDescription;
}
