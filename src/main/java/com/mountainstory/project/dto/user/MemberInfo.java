package com.mountainstory.project.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class MemberInfo {

    private String memberEmail;

    private String memberType;

    private String memberName;

    private String memberJoinDate;

    //TODO: 여기서 엔티티의 reviewList 를 정의, 가져와서 사용 가능 (응용)

    public MemberInfo(String memberEmail, String memberType, String memberName, String memberJoinDate) {
        this.memberEmail = memberEmail;
        this.memberType = memberType;
        this.memberName = memberName;
        this.memberJoinDate = memberJoinDate;
    }
}
