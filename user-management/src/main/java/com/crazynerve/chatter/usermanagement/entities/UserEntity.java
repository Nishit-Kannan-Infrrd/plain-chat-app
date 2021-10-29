package com.crazynerve.chatter.usermanagement.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class UserEntity
{
    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator (name="system-uuid",
        strategy = "uuid")
    private String userId;
    private String emailAddress;
    private String password;
}
