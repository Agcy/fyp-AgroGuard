package com.api.agroguard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Syf200208161018
 * @date 2022/12/11 14:10
 * @ClassName:Greeting
 * @Effect:Greeting is used for
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Greeting  implements Serializable {
    private String content;
}
