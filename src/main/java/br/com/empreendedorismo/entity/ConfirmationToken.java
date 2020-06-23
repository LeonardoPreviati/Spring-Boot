package br.com.empreendedorismo.entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CONFIRMATION_TOKEN")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TOKEN_ID")
    private long tokenId;

    @Column(name="CONFIRMATON_TOKEN")
    private String confirmationToken;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @OneToOne(targetEntity = DPUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "USER_ID")
    private DPUser user;
    
    public ConfirmationToken() {
        creationDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }

    
}
