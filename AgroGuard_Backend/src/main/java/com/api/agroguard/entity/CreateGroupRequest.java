package com.api.agroguard.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupRequest {
    private String groupName;
    private ArrayList<String> usernames;
}
