package com.crazynerve.chatter.ipblockerservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BlackListedIPAddressEntity
{
    @Id
    @Column (name = "ip_address_id")
    @GeneratedValue (generator = "system-uuid")
    @GenericGenerator (name="system-uuid",
        strategy = "uuid")
    private String ipAddressId;
    @Column(name = "ip_address")
    private String ipAddress;
}
