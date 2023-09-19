package com.aw.arbanware.domain.inquiry;

import com.aw.arbanware.domain.common.DeleteYn;
import com.aw.arbanware.domain.user.Admin;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class InquiryResp {

    @Id @GeneratedValue
    @Column(name = "INQUIRY_ID")
    private Long id; //문의번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADMIN_ID")
    private Admin admin; //답변자

    private String content; //내용
    private Long attachFileId; //첨부파일번호
    private LocalDateTime respTime; //답변시간

    @Enumerated(EnumType.STRING)
    private DeleteYn deleteYn; //삭제여부

}
