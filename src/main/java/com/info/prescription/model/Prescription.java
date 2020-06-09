package com.info.prescription.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
/*
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;*/
import java.util.Date;

//@Validated
@Data
@Entity
@Table(name = "prescription")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "patient_name", nullable = false, length = 100)
    private String patientName;

//    @Max(150)
//    @NotBlank(message = "Patient Gender is mandatory")
    @Column(name = "patient_age", nullable = false, length = 3)
    private Integer patientAge;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "medicines")
    private String medicines;

//    @NotBlank(message = "Prescription Date is mandatory")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
// or use
//    @DateTimeFormat(pattern = DateTimeFormat.ISO.DATE)
    @Column(name = "prescription_date")
    private Date prescriptionDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "next_visit_date")
    private Date nextVisitDate;

//    @NotBlank(message = "Patient Gender is mandatory")
    @Column(name = "gender")
    private String gender;

    @Column(name = "create_date")
    private Date createDate;

    @Transient
    private int count;
}
