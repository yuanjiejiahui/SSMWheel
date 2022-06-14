package com.vector.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName：
 * Description：
 *
 * @author：yuanjie
 * @date：2022/6/14 8:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Emp {
    private int eid;
    private String ename;
    private String esex;
    private String etel;
    private String etx;
    private String username;
    private String passwd;
}
