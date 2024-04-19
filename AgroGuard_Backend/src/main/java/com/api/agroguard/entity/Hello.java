package com.api.agroguard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Syf200208161018
 * @date 2022/12/11 14:10
 * @ClassName:Hello
 * @Effect:Hello is used for
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hello implements Serializable {
    private String name;
}
