package com.rapipay.commonapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "TERMINAL_MASTER", schema = "MST")
@Entity
@Getter
@ToString
@Setter
public class TerminalMaster {

    @JsonIgnore
    @Id
    @Column(name = "TM_ID")

    private String tmId;

    @Column(name = "TERMINAL_SERIAL_NO")
    @JsonProperty("terminalSerialNo")
    private String terminalSerialNo;

    @JsonIgnore
    @Column(name = "TERMINAL_STATUS")
    @JsonProperty("terminalStatus")
    private String terminalStatus;

    @JsonIgnore
    @Column(name = "TERMINAL_VERSION")
    @JsonProperty("terminalVersion")
    private String terminalVersion;



//    @JsonProperty(namespace = "terminalOnBoardingForSwitch",access = JsonProperty.Access.WRITE_ONLY)
//    @OneToOne(mappedBy = "terminalMaster")
//    private TerminalOnBoardingForSwitch terminalOnBoardingForSwitch;
}
