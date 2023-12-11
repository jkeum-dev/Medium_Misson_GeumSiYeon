package com.ll.medium.domail.post.post.entity;

import com.ll.medium.domail.member.member.entity.SiteMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 200)
	private String subject;

	@Column(columnDefinition = "TEXT")
	private String content;

	@ManyToOne
	private SiteMember author;

	private LocalDateTime createDate;

	private LocalDateTime modifyDate;

	@ColumnDefault("true")
	private boolean published;
}
