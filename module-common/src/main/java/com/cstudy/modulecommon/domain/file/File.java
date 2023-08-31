package com.cstudy.modulecommon.domain.file;

import com.cstudy.modulecommon.domain.member.Member;
import com.cstudy.modulecommon.domain.workbook.Workbook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@Table(name = "File", uniqueConstraints = {
        @UniqueConstraint(name = "fileName", columnNames = {"fileName"})
})
@AllArgsConstructor
public class File {

    /********************************* PK 필드 *********************************/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /********************************* PK가 아닌 필드 *********************************/
    private String fileName;

    /********************************* 연관관계 매핑 *********************************/
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "workbook_id")
    private Workbook workbook;


    /********************************* 빌더 *********************************/

}
