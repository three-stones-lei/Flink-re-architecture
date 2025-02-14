package org.lei;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: ClamBean
 * Package: org.lei
 * Description:
 *
 * @Author Lei
 * @Create 2/8/2024 12:22 pm
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClamBean {
    private String start;
    private String end;
    private String listingId;
    private Long videoPlayed;
    private Long expandMap;

}
