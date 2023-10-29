package com.aw.arbanware.domain.common.attachfile.entity;

import com.aw.arbanware.domain.common.embedded.AttachFileInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@IdClass(AttachFileKey.class)
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class AttachFile {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "attach_file_seq")
    @SequenceGenerator(name = "attach_file_seq",sequenceName = "ATTACH_FILE_SEQUENCE",allocationSize = 1)
    @Column(name = "ATTACH_FILE_ID")
    private Long id;    // 첨부파일번호

    @Id
    private int sequence;   // 순번

    @Embedded
    private AttachFileInfo attachFileInfo;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTime;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AttachFile that = (AttachFile) o;
        return sequence == that.sequence && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sequence);
    }
}