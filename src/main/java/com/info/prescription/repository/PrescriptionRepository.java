package com.info.prescription.repository;

import com.info.prescription.model.Prescription;
import com.info.prescription.service.PrescriptionCountReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findByPatientName(String title);

    String str = "SELECT  p.prescription_date AS prescriptionDate, count(*) as count " +
            "FROM  Prescription p GROUP BY p.prescription_date";
    @Query(value = str, nativeQuery = true)
    List<PrescriptionCountReport> findAllPrescriptions();

    List<Prescription> findByPrescriptionDateLessThanEqualAndPrescriptionDateGreaterThanEqualOrderByPrescriptionDate(Date start, Date end);


    String qry = "SELECT id, diagnosis, gender, medicines, patient_age, patient_name, " +
            " create_date, prescription_date, next_visit_date\n" +
            " FROM prescription \n" +
            " WHERE prescription_date BETWEEN :start AND :end \n" +
            " ORDER BY prescription_date DESC";
    @Query(value = qry, nativeQuery = true)
    List<Prescription> findByPrescriptionDateBetweenOrderByPrescriptionDateDesc(@Param("start") Date start, @Param("end") Date end);


}
