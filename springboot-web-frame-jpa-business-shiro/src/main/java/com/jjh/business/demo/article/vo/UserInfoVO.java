package com.jjh.business.demo.article.vo;

import lombok.*;

import javax.persistence.SqlResultSetMapping;
import java.util.Date;

/**
 * lkyygxxVO
 *
 * @author jjh
 * @date 2019/11/15
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {

    private Date createTime;
    private String username;

}
