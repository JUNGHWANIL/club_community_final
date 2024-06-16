package web.term.club.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import web.term.club.domain.Enum.ClubApprovalStatus;
import web.term.club.domain.Enum.ClubType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;

    @Enumerated(EnumType.STRING)
    private ClubType clubType;

    @Enumerated(EnumType.STRING)
    private ClubApprovalStatus status;

    @Column(columnDefinition = "TEXT")
    private String refuseInfo;

    @Builder.Default
    @OneToMany
    private List<ClubMember> clubMembers = new ArrayList<>();;

    @OneToOne
    @JoinColumn(name = "club_info_id")
    private ClubInfo clubInfo;

    @ManyToOne
    @JoinColumn(name = "request_student_id", referencedColumnName = "student_id")
    private Member reqStudent;

    @ManyToOne
    @JoinColumn(name = "request_professor_id", referencedColumnName = "student_id")
    private Member reqProfessor;

}