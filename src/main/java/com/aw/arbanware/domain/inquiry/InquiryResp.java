package com.aw.arbanware.domain.inquiry;

import com.aw.arbanware.domain.common.DeleteYn;
import com.aw.arbanware.domain.common.baseentity.BaseEntity;
import com.aw.arbanware.domain.user.entity.Admin;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter @Setter
public class InquiryResp extends BaseEntity implements Serializable {

    @Id // @GeneratedValue
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INQUIRY_ID")
    private Inquiry inquiry; //문의번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADMIN_ID")
    private Admin admin; //답변자

    private String content; //내용
    private Long attachFileId; //첨부파일번호

    @Enumerated(EnumType.STRING)
    private DeleteYn deleteYn; //삭제여부

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final InquiryResp that = (InquiryResp) o;
        return Objects.equals(inquiry, that.inquiry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inquiry);
    }
}
